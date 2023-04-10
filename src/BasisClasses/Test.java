package BasisClasses;

import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;

import BasisClasses.BibParser.Tokens.ArticleToken;
import BasisClasses.BibParser.Tokens.AssignToken;
import BasisClasses.BibParser.Tokens.CloseBracketToken;
import BasisClasses.BibParser.Tokens.IDToken;
import BasisClasses.BibParser.Tokens.IToken;
import BasisClasses.BibParser.Tokens.KeyToken;
import BasisClasses.BibParser.Tokens.OpenBracketToken;
import BasisClasses.BibParser.Tokens.SeparatorToken;
import BasisClasses.BibParser.Tokens.TokenType;
import BasisClasses.BibParser.Tokens.ValueToken;

public class Test {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		Scanner scanner = new Scanner(new FileInputStream("./Final_Project_420-PA3-AS/Latex10.bib"));
		
		StringBuilder myStringBuiilder = new StringBuilder();
		
		while (scanner.hasNext()) {
			myStringBuiilder.append(scanner.nextLine() +  "\n");
			
		}
		scanner.close();
		
		//([a-z]*)={(.*)}
		
		String myString = new String(myStringBuiilder);
		
		System.out.println(myString);
		
		
		  String val = """
		  		@ARTICLE{
				8114607, 
				author={X. Li and X. Wei and W. Zhou}, 
				journal={IET Circuits, Devices Systems}, 
				title={Heuristic thermal sensor allocation methods for overheating detection of real microprocessors}, 
				year={2017}, 
				volume={11}, 
				number={6}, 
				pages={559-567}, 
				keywords={circuit optimisation;cooling;genetic algorithms;infrared imaging;microprocessor chips;probability;temperature measurement;temperature sensors;chip temperature monitoring;embedded thermal sensors;fabrication fluctuations;genetic algorithm;heuristic method;heuristic thermal sensor allocation methods;hybrid algorithm;infrared thermal imaging techniques;near-optimal thermal sensor allocation solution;oil-based cooling system;on-chip thermal sensor readings;overheating detection probability;real dual-core microprocessor;thermal monitoring}, 
				doi={10.1049/iet-cds.2016.0529}, 
				ISSN={1751-858X}, 
				month={Jan},
				}""";
		 

        String delim = "{},=\n";
        StringTokenizer st = new StringTokenizer(myString, delim, true);

        Stack<IToken> tokenStack = new Stack<IToken>();
        Stack<Boolean> bracketStack = new Stack<Boolean>();

        HashMap<String, HashMap<String, String>> rawData = new HashMap<String, HashMap<String, String>>();
        
        String tempToken = null;
        
        String id = null;
        String interimKey = null;
        
        while (st.hasMoreTokens() || tempToken != null) {
        	String nextToken = (tempToken != null ? tempToken : st.nextToken()).strip();
        	tempToken = null;
        	
        	// Ignore whitespace
        	if (nextToken == null || nextToken.isEmpty() || nextToken.equals("\n")) continue;
        	
        	IToken articleToken = ArticleToken.match(nextToken, tokenStack);
        	if (articleToken != null) {
        		tokenStack.push(articleToken);
        		continue;
        	}
        	
        	IToken idToken = IDToken.match(nextToken, tokenStack);
        	if (idToken != null) {
        		tokenStack.push(idToken);
        		id = idToken.GetValue();
        		
        		rawData.put(id, new HashMap<String, String>());
        		continue;
        	}
        
        	IToken openBracket = OpenBracketToken.match(nextToken, tokenStack);
        	if (openBracket != null) {
        			
        			
        		tokenStack.push(openBracket);
        		bracketStack.push(true);
        		
        			
        		continue;
        	}
        	
        	IToken closeBracket = CloseBracketToken.match(nextToken, bracketStack);
        	if (closeBracket != null) {
        		
        		if (tokenStack.peek().GetTokenType() == TokenType.OpenBracket)
        			throw new Exception("No value for key");
        		
        		bracketStack.pop();
        		tokenStack.push(closeBracket);
        		
        		
        		if (bracketStack.empty()) {
        			tokenStack =  new Stack<IToken>();
        			id = null;
        		}
        		

        		continue;
        	}
        	
        	
        	IToken assingToken = AssignToken.match(nextToken, tokenStack);
        	if (assingToken != null) {
        		tokenStack.push(assingToken);
        		continue;
        	}
        	
        	IToken separatorToken = SeparatorToken.match(nextToken, tokenStack);
        	if (separatorToken != null) {
        		tokenStack.push(separatorToken);
        		
        		continue;
        	}
        	
        	ValueToken valueToken = ValueToken.match(nextToken, tokenStack);
        	if (valueToken != null) {
        		
        		StringBuilder sb = new StringBuilder();
        		sb.append(nextToken);
        		while  (st.hasMoreTokens()) {
        			tempToken = st.nextToken();
        			if (tempToken.equals("}")) {
        				break;
        			}
        			sb.append(tempToken);
        		}
        		
        		valueToken.UpdateValue(new String(sb));
        		tokenStack.push(valueToken);
        	
        		if (interimKey == null) {
        			throw new Exception("Ruh roh");
        		}
        		
        		rawData.get(id).put(interimKey, valueToken.GetValue());
        		interimKey = null;
        		
        		continue;
        	}
        	
        	if(st.hasMoreTokens()) {
	        	tempToken = st.nextToken().strip();
	        	IToken keyToken = KeyToken.match(nextToken, tokenStack, bracketStack, tempToken);
	        	if (keyToken != null) {
	        		tokenStack.push(keyToken);
	        		interimKey = keyToken.GetValue();
	        		continue;
	        	}
        	}	
        	
        	throw new Exception("Unexpected token: " + nextToken);
        }
        
        for (Boolean boolean1 : bracketStack) {
			System.out.println(boolean1);
		}
        
        for (IToken token : tokenStack) {
        	System.out.println(token.GetTokenType() + " \t " + token.GetValue());
			
		} 
        
        List<Article> listOfArticles = new ArrayList<Article>();
        
        rawData.forEach((key, value) -> {
        	System.out.println(key);
        	value.forEach((key1, value1) -> {
        		System.out.println(key1 + " " + value1);
        		
        	});
        	System.out.println();
        });
        
        System.out.println();
        
        rawData.forEach((key, value) -> { 
        	
        	HashMap<String, String> ArticleMap = value;
        	String[] authors = ArticleMap.get("author").split("and");
        	for (int i = 0; i < authors.length; i++) {
				authors[i] = authors[i].strip();
			}
        	String[] keywords = ArticleMap.get("keywords").split(";");
        	
        	listOfArticles.add(new Article(
        			Integer.valueOf(key),
        			authors,
        			ArticleMap.get("journal"),
        			ArticleMap.get("title"),
        			Integer.valueOf(ArticleMap.get("year")),
        			ArticleMap.get("volume"), 
        			Integer.valueOf(ArticleMap.get("number")), 
        			ArticleMap.get("pages"),
        			keywords,
        			ArticleMap.get("doi"),
        			ArticleMap.get("month"),
        			ArticleMap.get("issn")));
        });
        
        PrintWriter pw1 = new PrintWriter("test1.json");
        
        for (Article article : listOfArticles) {
			System.out.println(article.Format(FormatType.IEEE));
			pw1.append(article.Format(FormatType.IEEE) +  "\n\n");
		}
        System.out.println();
        
        PrintWriter pw2 = new PrintWriter("test2.json");
        
        int counter = 0;
        for (Article article : listOfArticles) {
			System.out.println("[" + ++counter + "] " + article.Format(FormatType.ACM));
			pw2.append("[" + counter + "] " + article.Format(FormatType.ACM)  + "\n\n");
			
		}
        
        System.out.println();
        PrintWriter pw3 = new PrintWriter("test3.json");
        
        for (Article article : listOfArticles) {
			System.out.println(article.Format(FormatType.NJ));
			pw3.append(article.Format(FormatType.NJ) + "\n\n");
		}
        
        pw1.close();
        pw2.close();
        pw3.close();
	}

}
