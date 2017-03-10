package testRegex;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import RegexParser.*;

public class testHole {

	public RegexNode parseRegex(String regex) {
		RegexParserProvider test = new RegexParserProvider(regex);
		RegexNode root = (RegexNode)test.process();
		return root;
	}


	public List<RegexNode> findAllHoles(RegexNode regex1) {
		return(findAllHoles(regex1, null));
	}

	public List<RegexNode> findAllHoles(RegexNode node1, RegexNode node2) {

		List<RegexNode> resultList = new ArrayList<RegexNode>();

		// base case
		if(node1 instanceof CharNode || node1 instanceof StarNode) {
			resultList.add(new HoleNode());
			return resultList;
		}

		else if(node1 instanceof ConcatenationNode) {
			List<RegexNode> concatList = ((ConcatenationNode)node1).getList();
			for(int i = 0; i < concatList.size(); i++) {
				RegexNode tmpNode = concatList.get(i);
				List<RegexNode> tmpList = new ArrayList<RegexNode>();
				// add returned list to result list
				resultList.addAll(findAllHoles(tmpNode));	
				for(int j = i; j < concatList.size(); j++) {

					tmpList = new ArrayList<RegexNode>();	// initialize every time

					if(i > 0) {	// add preceding nodes
						tmpList.addAll(concatList.subList(0, i));
					}
					tmpList.add(new HoleNode());	// add hole
					if(i < concatList.size() - 1) {	// add following nodes
						tmpList.addAll(concatList.subList(j+1, concatList.size()));
					}
					resultList.add(new ConcatenationNode(tmpList));

				}


			}
			return resultList;
		}

		resultList.add(new HoleNode());
		return resultList;
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
	public void findNodetype(RegexNode node) {
		StringBuilder s = new StringBuilder();

		printClass(node);	// node is ConcatenationNode
		for(RegexNode tmp : ((ConcatenationNode)node).getList()) {
			printClass(tmp);
			printClass(((StarNode)tmp).getMyRegex1());
		}

	}
	public void printClass(RegexNode node) {
		System.out.println(node.getClass());
	}

	@Test
	public void testHole() {

		StringBuilder s = new StringBuilder(); 
		String regex1 = "a*";
		RegexNode nodeRegex1 = parseRegex(regex1);
		nodeRegex1.toString(s);
//		System.out.println(nodeRegex1.getClass());
//		System.out.println(s.toString());
//		findNodetype(nodeRegex1);

		List<RegexNode> list;
		list = findAllHoles(nodeRegex1);

		for(RegexNode node : list) {
			s = new StringBuilder();
			node.toString(s);
			System.out.println(s.toString());
		}


	}

}
