package testRegex;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import RegexParser.CharNode;
import RegexParser.ConcatenationNode;
import RegexParser.HoleNode;
import RegexParser.PlusNode;
import RegexParser.RegexNode;
import RegexParser.RegexParserProvider;
import RegexParser.StarNode;
import RegexParser.UnionNode;

public class testHole {

	// GLOBAL Returning List
	public List<RegexNode> RESULT_LIST;

	public RegexNode parseRegex(String regex) {
		RegexParserProvider test = new RegexParserProvider(regex);
		RegexNode root = (RegexNode)test.process();
		return root;
	}


	public void findAllHoles(RegexNode regex1) {
		findAllHoles(regex1, null);
	}

	public void findAllHoles(RegexNode node1, RegexNode node2) {

		//		List<RegexNode> resultList = new ArrayList<RegexNode>();

		// base cases 
		if(node1 instanceof CharNode) {
			RESULT_LIST.add(new HoleNode());
			return;
		}
		else if(node1 instanceof StarNode) {

		} 


		// base cases end here

		// Concatenation Node  
		else if(node1 instanceof ConcatenationNode) {
			List<RegexNode> concatList = ((ConcatenationNode)node1).getList();
			for(int i = 0; i < concatList.size(); i++) {
				RegexNode tmpNode = concatList.get(i);
				List<RegexNode> tmpList = new ArrayList<RegexNode>();

				// add recurlively returned list to result list
				findAllHoles(tmpNode);

				// 
				for(int j = i; j < concatList.size(); j++) {

					tmpList = new ArrayList<RegexNode>();	// initialize every time

					if(i > 0) {	// add preceding nodes
						tmpList.addAll(concatList.subList(0, i));
					}
					tmpList.add(new HoleNode());	// add hole
					if(i < concatList.size() - 1) {	// add following nodes
						tmpList.addAll(concatList.subList(j+1, concatList.size()));
					}
					RESULT_LIST.add(new ConcatenationNode(tmpList));

				}
			}
			return;
		}
		// Union node
		else if(node1 instanceof UnionNode) {

		}

		RESULT_LIST.add(new HoleNode());
		return;
	}

	public void printConcate(RegexNode node) {
		if(node instanceof ConcatenationNode) {
			StringBuilder s = new StringBuilder();
			for(RegexNode tmpNode : ((ConcatenationNode)node).getList()) {
				tmpNode.toString(s);
				System.out.println(s.toString());
			}
		}
	}

	public void findTypeInConcate(RegexNode node) {
		StringBuilder s = new StringBuilder();

		printClass(node);	// node is ConcatenationNode
		for(RegexNode tmp : ((ConcatenationNode)node).getList()) {
			printClass(tmp);
			RegexNode tmp2 = ((PlusNode)tmp).getMyRegex1();
			printClass(tmp2);

			List<RegexNode> tmp2List = ((ConcatenationNode)tmp2).getList();
			for(RegexNode tmp3 : tmp2List) {
				printClass(tmp3);
			}

		}

	}

	public void findTypeInUnion(RegexNode node) {
		StringBuilder s = new StringBuilder();

		RegexNode node1= ((UnionNode)node).getMyRegex1();
		RegexNode node2= ((UnionNode)node).getMyRegex2();

		printClass(node1);
		printClass(node2);

		findTypeInConcate(node1);
		findTypeInConcate(node2);


	}

	public void printClass(RegexNode node) {
		System.out.println(node.getClass());
	}


	public List<RegexNode> generateAllRegex(RegexNode regex) {

		System.out.println("func call");
		List<RegexNode> allGeneratedRegex = new ArrayList<RegexNode>();
		
		// base case? -- case of char for now 
		if(regex instanceof CharNode) {
			List<RegexNode> returnList = new ArrayList<>();
			returnList.add(new HoleNode());
			return returnList;
		}
		
		// if not base case, iterate within range of 1 regex.length
		else {
			int replaceRegexSize = 1;	// size of replacing sub-regex
			List<RegexNode> regexList = new ArrayList<RegexNode>();	// refers to all sub regex (e.g. in a*bc, divide into a*, b, c)
			if(regex instanceof ConcatenationNode) {
				System.out.println("Concate");
				regexList = ((ConcatenationNode)regex).getList();
			} 
			
			System.out.println("RegexList Size : " + regexList.size());
			
			// Generating new regex with replacing with Hole
			while(replaceRegexSize < regexList.size()) {
				for(int i = 0; i < regexList.size(); i ++) {
					if(i+replaceRegexSize > regexList.size())
						continue;
					
					System.out.println("start index: " + i + "\n size: " + replaceRegexSize);
					
					List<RegexNode> prevSubreg = regexList.subList(0, i);
					List<RegexNode> postSubreg = null;
					if(i+replaceRegexSize < regexList.size()) {
						postSubreg = regexList.subList(i+replaceRegexSize, regexList.size());
					}
					// List<RegexNode> replaceSubregList = concatList.subList(i, i+replaceRegexSize);

					// if replacing sub-regex size is 1 recurse,
					List<RegexNode> replaceSubregList;
					
					if(replaceRegexSize == 1) {
						RegexNode recurseNode = regexList.get(i);
						replaceSubregList = generateAllRegex(recurseNode);
					}

					// else make it HoleNode
					else {
						replaceSubregList = new ArrayList<RegexNode>();
						replaceSubregList.add(new HoleNode());
					}

					// iterate all possible replaceSubregList and merge with prev and sub regex
					for(int j = 0; j < replaceSubregList.size(); j ++) {
						RegexNode replaceNode = replaceSubregList.get(j); 
						List<RegexNode> allSubregex = new ArrayList<RegexNode>();
						allSubregex.addAll(prevSubreg);
						allSubregex.add(replaceNode);

						if(postSubreg != null)
							allSubregex.addAll(postSubreg);

						ConcatenationNode generatedRegex = new ConcatenationNode(allSubregex);
						allGeneratedRegex.add(generatedRegex);
						// TODO -- testing
						StringBuilder s = new StringBuilder();
						generatedRegex.toString(s);
						System.out.println(s.toString());
					}

				}
				replaceRegexSize++;
			}	// while loop ends here

		}

		return allGeneratedRegex;
	}

	@Test
	public void testHole() {

		StringBuilder s = new StringBuilder(); 
		String regex1 = "ab*";
		RegexNode regex = parseRegex(regex1);
		
		RESULT_LIST = GenerateHole.generate(regex);
		GenerateHole.printAll(RESULT_LIST);


	}

}
