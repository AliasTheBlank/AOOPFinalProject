package BasisClasses;

import java.util.Stack;
import java.util.StringTokenizer;

import BasisClasses.BibParser.Tokens.ArticleToken;
import BasisClasses.BibParser.Tokens.IToken;
import BasisClasses.BibParser.Tokens.KeyToken;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		 String val = """
			        ARTICLE{
			        2380090, 
			        author={T. Jackson and A. H. Peterson and N. Wang}, 
			        journal={IEEE Transactions on Computer Science}, 
			        title={Detecting Security Vulnerabilities in Binary Code}, 
			        year={2017}, 
			        volume={QQ}, 
			        number={85}, 
			        pages={1-12}, 
			        keywords={Security attacks;Binary code processing;Security error detection;Deep machine learning;Static analysis}, 
			        doi={14.2408/TCS.2017.4746889}, 
			        ISSN={0018-9545}, 
			        month={May},
			        }""";

			        String delim = "{},=";
			        StringTokenizer st = new StringTokenizer(val, delim, true);

			        Stack<IToken> tokenStack = new Stack<IToken>();
			        Stack<Boolean> bracketStack = new Stack<Boolean>();
			        
			        
			        System.out.println(st.nextToken());
			        
			        String tempToken = null;
			        
			        while (st.hasMoreTokens() || tempToken != null) {
			        	String nextToken = tempToken != null ? tempToken : st.nextToken();
			        	tempToken = null;
			        	
			        	IToken articleToken = ArticleToken.match(nextToken, tokenStack);
			        	if (articleToken != null) {
			        		tokenStack.push(articleToken);
			        	}
			        
			        	
			        	tempToken = st.nextToken();
			        	IToken keyToken = KeyToken.match(nextToken, tokenStack, bracketStack, tempToken);
			        	if (keyToken != null) {
			        		tokenStack.push(keyToken);
			        	}
			        }
	}

}
