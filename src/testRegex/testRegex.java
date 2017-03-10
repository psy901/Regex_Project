package testRegex;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import RegexParser.CharacterClassNode;
import RegexParser.ConcatenationNode;
import RegexParser.IntervalNode;
import RegexParser.RegexListNode;
import RegexParser.RegexNode;
import RegexParser.RegexParserProvider;
import RegexParser.UnionNode;

public class testRegex {

	/*	old test for reference
	private void run(String input[][], String rightOutput) {
		for (String[] i : input) {
			RegexParserProvider test = new RegexParserProvider(i);
			RegexNode root = test.process();
			StringBuilder s = RegexParserProvider.toStringBuilder(root, false);
			String output = s.toString();
			System.out.print("Output of test: " + output);
			System.out.println("RightOutput: " + rightOutput);
			assertTrue(output.equals(rightOutput + System.getProperty("line.separator")));
		}
	}
	*/

	public List<RegexNode> population(String regex1, String regex2) {

		// parse each string into RegexNode
		RegexParserProvider test1 = new RegexParserProvider(regex1);
		RegexNode root1 = (RegexNode)test1.process();
		RegexParserProvider test2 = new RegexParserProvider(regex2);
		RegexNode root2 = (RegexNode)test2.process();

		StringBuilder s = new StringBuilder();

		System.out.println(root1.getClass());
		
		// container for all sub RegexNode retrieved from each regEx
		List<RegexNode> populatedSet = new ArrayList<RegexNode>();
		
		System.out.println("Populating Begins");


		return populatedSet;
	}

	@Test
	public void testPopulation() {

		String input1 = "01";
		String input2 = "2|3";
		List<RegexNode> populatedSet = population(input1, input2);
		StringBuilder s = new StringBuilder();
		for(RegexNode node : populatedSet) {
			node.toString(s);
			s.append(", " + node.getClass() + "\n");
		}
		System.out.println(s);

	}

}
