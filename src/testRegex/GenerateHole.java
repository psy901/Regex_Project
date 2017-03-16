package testRegex;

import java.util.ArrayList;
import java.util.List;

import RegexParser.*;

public class GenerateHole {
	
	static List<RegexNode> generate(RegexNode regex) {
		List<RegexNode> generatedList = new ArrayList<RegexNode>();
		
		if(regex instanceof ConcatenationNode) {
			generatedList = generateConcat((ConcatenationNode) regex);
		} else if(regex instanceof UnionNode) {
			generatedList = generateUnion((UnionNode) regex);
		} else if(regex instanceof StarNode) {
			generatedList = generateStar((StarNode) regex);
		} else if(regex instanceof CharNode) {
			generatedList = generateChar((CharNode) regex);

		}
		
		return generatedList;
	}
	
	
	



	
/*
	static List<RegexNode> generateConcat(ConcatenationNode regex) {
		
		int replaceRegexSize = 1;	// size of replacing sub-regex
		List<RegexNode> generatedRegexList = new ArrayList<RegexNode>();	// list containing all generated regex
		List<RegexNode> regexList = regex.getList();	// refers to all sub regex (e.g. in a*bc, divide into a*, b, c)
		System.out.println("RegexList Size : " + regexList.size());
		
		// Generating new regex with replacing with Hole
		while(replaceRegexSize <= regexList.size()) {
			
			for(int i = 0; i < regexList.size(); i ++) {	// iterate every RegexNode
				if(i+replaceRegexSize > regexList.size())
					continue;
				
				System.out.println("start index: " + i + "\n size: " + replaceRegexSize);
				
				List<RegexNode> prevSubreg = regexList.subList(0, i);
				List<RegexNode> postSubreg = null;
				if(i+replaceRegexSize < regexList.size()) {
					postSubreg = regexList.subList(i+replaceRegexSize, regexList.size());
				}

				// if replacing sub-regex size is 1 recurse,
				List<RegexNode> replaceSubregList;
				if(replaceRegexSize == 1) {
					RegexNode recurseNode = regexList.get(i);
					replaceSubregList = generate(recurseNode);
				}

				// else if replacing size > 1, make it HoleNode
				else {
					replaceSubregList = new ArrayList<RegexNode>();
					replaceSubregList.add(new HoleNode());
				}

				// iterate all possible replaceSubregList and merge with prev and sub regex
				for(int j = 0; j < replaceSubregList.size(); j ++) {
					RegexNode replaceNode = replaceSubregList.get(j); 
					List<RegexNode> allSubregex = new ArrayList<RegexNode>();
					if(prevSubreg.size() != 0) { allSubregex.addAll(prevSubreg); } 
					allSubregex.add(replaceNode);
					if(postSubreg != null)	{ allSubregex.addAll(postSubreg); }
					
					ConcatenationNode generatedRegex = new ConcatenationNode(allSubregex);
					generatedRegexList.add(generatedRegex);

					// TODO -- testing
					StringBuilder s = new StringBuilder();
					generatedRegex.toString(s);
					System.out.println(s.toString());
				}
			}
			replaceRegexSize++;
			
		}	// while loop ends here
		return generatedRegexList;
	}

*/

	
	
	static List<RegexNode> generateConcat(ConcatenationNode regex) {
		System.out.println("Concat call");
		List<RegexNode> generatedRegexList = new ArrayList<RegexNode>();	// list containing all generated regex
		List<RegexNode> regexList = regex.getList();	// refers to all sub regex (e.g. in a*bc, divide into a*, b, c)
		
		for(int i = 1; i < regexList.size() + 1; i ++) {	// loop for replacing regex size 
			for(int j = 0; j < regexList.size() + 1 - i; j ++) {
				List<RegexNode> tmpList = new ArrayList<RegexNode>();	// tmp list for collecting
				List<RegexNode> preRegex = regexList.subList(0, j);
				List<RegexNode> replaceRegex = regexList.subList(j, j+i);
				List<RegexNode> postRegex = regexList.subList(j+i, regexList.size());
				
				// if replacing regex size is 1, execute recursive call
				if(i == 1) {
					RegexNode onlyRegex = replaceRegex.get(0);
					for(RegexNode tmpNode : generate(onlyRegex) ) {
						
						tmpList = new ArrayList<RegexNode>();
						tmpList.addAll(preRegex);
						tmpList.add(tmpNode);
						tmpList.addAll(postRegex);
						generatedRegexList.add(new ConcatenationNode(tmpList));
					}
				}
				// else, just replace with HoleNode
				else {
					tmpList.addAll(preRegex);
					tmpList.add(new HoleNode());
					tmpList.addAll(postRegex);
					generatedRegexList.add(new ConcatenationNode(tmpList));
				}
			}
		}
		return generatedRegexList;
	}
	
	static List<RegexNode> generateUnion(UnionNode regex) {
		// UnionNode contains two children
		
		return null;
	}
	
	static List<RegexNode> generateStar(StarNode regex) {
		System.out.println("Star call");

		List<RegexNode> generatedRegexList = new ArrayList<RegexNode>();	// list containing all generated regex
		List<RegexNode> replaceRegex = generate(regex.getMyRegex1());

		for(RegexNode tmpNode : replaceRegex) {
			generatedRegexList.add(new StarNode(tmpNode));
		}
		// entire star becomes hole
		generatedRegexList.add(new HoleNode());
//		System.out.println("/////");
//		printAll(generatedRegexList);
//		System.out.println("/////");
		return generatedRegexList;
	}
	
	static List<RegexNode> generatePlus(PlusNode regex) {
		
	}
	
	static List<RegexNode> generateChar(CharNode regex) {
		System.out.println("Char call");
		List<RegexNode> generatedRegexList = new ArrayList<>();
		generatedRegexList.add(new HoleNode());
		return generatedRegexList;
	}
	
	static void printAll(List<RegexNode> list) {
		
		for(RegexNode node : list) {
			StringBuilder s = new StringBuilder();
			node.toString(s);
			System.out.println(s.toString());
		}
	}
	static void printNode(RegexNode regex) {
		StringBuilder s = new StringBuilder();
		regex.toString(s);
		System.out.println(s.toString());
	}
	
	
	
	
}
