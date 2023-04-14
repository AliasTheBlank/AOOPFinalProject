package BasisClasses.BibParser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;

import BasisClasses.FileInvalidException;
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

public class BibCreator {

	public static void main(String[] args) {
		
		System.out.println("Welcome to BibCreator!\n");
		
		String[] myUserProvidedPaths = new String[10];
		myUserProvidedPaths[0] = "Latex1.bib";
		myUserProvidedPaths[1] = "Latex2.bib";
		myUserProvidedPaths[2] = "Latex3.bib";
		myUserProvidedPaths[3] = "Latex4.bib";
		myUserProvidedPaths[4] = "Latex5.bib";
		myUserProvidedPaths[5] = "Latex6.bib";
		myUserProvidedPaths[6] = "Latex7.bib";
		myUserProvidedPaths[7] = "Latex8.bib";
		myUserProvidedPaths[8] = "Latex9.bib";
		myUserProvidedPaths[9] = "Latex10.bib";

		StringBuilder[] sb = new StringBuilder[myUserProvidedPaths.length];


		for(int i = 0; i < myUserProvidedPaths.length; i++) {
		  Scanner scanner;
		  try {
		    scanner = new Scanner(new FileInputStream(myUserProvidedPaths[i]));
		    sb[i] = catchData(scanner);
		  } catch (Exception e) {
		    System.out.printf("""
		    		Could not open input file %s for reading.
		    		
		    		Please check if file exist! Program will terminate after closing any opened files.
		    		""", myUserProvidedPaths[i]);
		  }
		}
		
		
		try {
			HashMap<String, HashMap<String, String>> data = processFilesForValidation(new String(sb[1]));
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}
	
	
	//TODO
	public static HashMap<String, HashMap<String, String>> processFilesForValidation(String file) throws FileInvalidException {
		
		String delim = "{},=\n";
        StringTokenizer st = new StringTokenizer(file, delim, true);

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
        	
        	
        	if (processArticleToken(nextToken, tokenStack)) {
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
						 throw new FileInvalidException("No value for key");
					
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
        			throw new FileInvalidException("Ruh roh");
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
        	
        	throw new FileInvalidException("Unexpected token: " + nextToken);
        }
        
        return rawData;
	}
	
	public static StringBuilder catchData(Scanner file) {
		StringBuilder sb = new StringBuilder();
		
		while (file.hasNext()) {
			sb.append(file.nextLine() +  "\n");
		}
		
		file.close();
		
		return sb;
	}
	
	public static Boolean processArticleToken(String nextToken, Stack<IToken> tokenStack) {
		IToken articleToken = ArticleToken.match(nextToken, tokenStack);
		if (articleToken != null) {
			tokenStack.push(articleToken);
			return true;
		}
		
		return false;
	}
}
