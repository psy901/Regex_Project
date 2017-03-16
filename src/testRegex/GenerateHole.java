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
		} else if(regex instanceof PlusNode) {
			generatedList = generatePlus((PlusNode) regex);
		} else if(regex instanceof CharNode) {
			generatedList = generateChar((CharNode) regex);
		} else if(regex instanceof AnchorNode) {
			generatedList = generateAnchor((AnchorNode) regex);
		} else if(regex instanceof DotNode) {
			generatedList = generateDot((DotNode) regex);
		}
		
		return generatedList;
	}
	

	static List<RegexNode> generateConcat(ConcatenationNode regex) {
		System.out.println("Concat call");
		List<RegexNode> generatedRegexList = new ArrayList<RegexNode>();	// list containing all generated regex
		List<RegexNode> regexList = regex.getList();	// refers to all sub regex (e.g. in a*bc, divide into a*, b, c)
		
		System.out.println("regexlist size :" + regexList.size());
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
//						System.out.println("Adding in size 1: ");
						ConcatenationNode node = new ConcatenationNode(tmpList);
//						printNode(node);
						generatedRegexList.add(node );
					}
				}
				// else, just replace with HoleNode
				else {
//					System.out.println("Adding in size " + i + ": ");
					tmpList.addAll(preRegex);
					tmpList.add(new HoleNode());
					tmpList.addAll(postRegex);
					ConcatenationNode node = new ConcatenationNode(tmpList);
//					printNode(node);
					generatedRegexList.add(node);
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
		return generatedRegexList;
	}
	
	static List<RegexNode> generatePlus(PlusNode regex) {
		System.out.println("Plus call");

		List<RegexNode> generatedRegexList = new ArrayList<RegexNode>();	// list containing all generated regex
		List<RegexNode> replaceRegex = generate(regex.getMyRegex1());

		for(RegexNode tmpNode : replaceRegex) {
			generatedRegexList.add(new PlusNode(tmpNode));
		}
		// entire star becomes hole
		generatedRegexList.add(new HoleNode());
		return generatedRegexList;
	}
	
	static List<RegexNode> generateChar(CharNode regex) {
		System.out.println("Char call");
		List<RegexNode> generatedRegexList = new ArrayList<>();
		generatedRegexList.add(new HoleNode());
		return generatedRegexList;
	}
	
	private static List<RegexNode> generateDot(DotNode regex) {
		// TODO Auto-generated method stub
		return null;
	}


	private static List<RegexNode> generateAnchor(AnchorNode regex) {
		// TODO Auto-generated method stub
		return null;
	}


	
	static void printAll(List<RegexNode> list) {
		
		for(RegexNode node : list) {
			StringBuilder s = new StringBuilder();
			node.toString(s);
			System.out.println(s.toString());
		}
	}
	
	static void printAllClean(List<RegexNode> list) {
		for(RegexNode node : list) {
			System.out.println(node.toCleanString());
		}
	}
	
	static void printNode(RegexNode regex) {
		StringBuilder s = new StringBuilder();
		regex.toString(s);
		System.out.println(s.toString());
	}
	
	
	
	
	
}
