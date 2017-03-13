package testRegex;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import RegexParser.*;

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

	@Test
	public void testHole() {

		StringBuilder s = new StringBuilder(); 
		String regex1 = "123";
		RegexNode nodeRegex1 = parseRegex(regex1);
//		nodeRegex1.toString(s);
//		System.out.println(nodeRegex1.getClass());
//		System.out.println(s.toString());

		List<RegexNode> list;
		RESULT_LIST = new ArrayList<RegexNode>();
		findAllHoles(nodeRegex1);
		
//		findTypeInConcate(nodeRegex1);
//		findTypeInUnion(nodeRegex1);
		
		for(RegexNode node : RESULT_LIST) {
			s = new StringBuilder();
			node.toString(s);
			System.out.println(s.toString());
		}


	}

}
