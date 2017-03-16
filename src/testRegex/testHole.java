package testRegex;

import java.util.List;
import java.util.regex.Pattern;

import org.junit.Test;

import RegexParser.RegexNode;
import RegexParser.RegexParserProvider;


public class testHole {

	// GLOBAL Returning List
	public List<RegexNode> RESULT_LIST;

	public RegexNode parseRegex(String regex) {
		RegexParserProvider test = new RegexParserProvider(regex);
		RegexNode root = (RegexNode)test.process();
		return root;
	}
	
	@Test
	public void testReplacement() {
		String regex1 = "a*bc";
		String[] posSet = {"a*bbc", "a*bbbc"};	// require 2 or more 'b's
		String[] negSet = {"a*bcc", "a*b"};		// reject if one c is not there
		String goalRegex = "a*bb+c";
		RegexNode regex = parseRegex(regex1);
		RESULT_LIST = GenerateHole.generate(regex);
		
		GenerateHole.printAllClean(RESULT_LIST);
		
		
		
		for(RegexNode tmpNode : RESULT_LIST) {
			String holeRegex = tmpNode.toCleanString();
			String tmpPosReplaced = holeRegex.replaceAll("\\[\\]", ".*");
			String posReplacedRegex = tmpPosReplaced.replaceAll("\\*\\*", "*");
			
			System.out.println("==============");
			System.out.println(posReplacedRegex);
			if(Pattern.matches(posReplacedRegex, posSet[0])) {
				System.out.println("MATCH " + posSet[0]);
			}
			if(Pattern.matches(posReplacedRegex, posSet[1])) {
				System.out.println("MATCH " + posSet[1]);
			}
			
			String negReplacedRegex = holeRegex.replaceAll("\\[\\]", "!");	// using ! for negative now
			System.out.println("==============");
			System.out.println(negReplacedRegex);
			if(!Pattern.matches(negReplacedRegex, negSet[0])) {
				System.out.println("NO MATCH " + negSet[0]);
			}
			if(!Pattern.matches(negReplacedRegex, negSet[1])) {
				System.out.println("NO MATCH " + negSet[1]);
			}
		}
		
	}
	
	public void testHole() {

		StringBuilder s = new StringBuilder(); 
		String regex1 = "a*b+";
		RegexNode regex = parseRegex(regex1);
		
		RESULT_LIST = GenerateHole.generate(regex);
//		GenerateHole.printAll(RESULT_LIST);

		for(RegexNode node : RESULT_LIST) {
			System.out.println(node.toCleanString());
		}
		
		

	}

}
