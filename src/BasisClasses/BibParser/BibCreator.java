package BasisClasses.BibParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;

import BasisClasses.Article;
import BasisClasses.FileInvalidException;
import BasisClasses.FormatType;
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
	
	static HashMap<Integer, List<PrintWriter>> pendingFiles;

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

		int numberOfInvalidFiles = 0;

		for(int i = 0; i < myUserProvidedPaths.length; i++) {
		  Scanner scanner;
		  try {
		    scanner = new Scanner(new FileInputStream(myUserProvidedPaths[i]));
		    sb[i] = catchData(scanner);
		    
		  } catch (Exception e) {
			  numberOfInvalidFiles++;
		    System.out.printf("""
		    		Could not open input file %s for reading.
		    		
		    		Please check if file exist! Program will terminate after closing any opened files.
		    		""", myUserProvidedPaths[i]);
		  }
		}
		
		if (numberOfInvalidFiles > 0) {
			System.out.println(String.format("A total of %d files were invalid, and could not be processed. All other %d \"Valid\" files have been created", numberOfInvalidFiles, myUserProvidedPaths.length - numberOfInvalidFiles));
		}
		
		pendingFiles = new HashMap<Integer, List<PrintWriter>>();
		for (int i = 0; i < myUserProvidedPaths.length; i++) {
			pendingFiles.put((i + 1), new ArrayList<PrintWriter>());
			List<PrintWriter> list = pendingFiles.get(i + 1);
            try {
            	list.add(new PrintWriter(String.format("IEEE%d.json", i + 1))); 
				list.add(new PrintWriter(String.format("ACM%d.json", i + 1)));
				list.add(new PrintWriter(String.format("NJ%d.json", i + 1)));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			} 
		}
		
		for (int i = 0; i < myUserProvidedPaths.length; i++) {
			try {
				processFilesForValidation(new String(sb[i]), i + 1, myUserProvidedPaths[i]);
			}
			catch (Exception e) {
				// TODO: handle exception
				System.out.println(e.getMessage());
			}
		}
		
		int tries = 0;
		while (tries < 3) {
			Scanner scanner = new Scanner(System.in);
			System.out.print("Pleasse enter the name of one of the files that you need to review:");
			String fileToReview = scanner.next();
			System.out.println();
			scanner.close();
			
			FileReader fr;
			BufferedReader bf;
			try {
				fr = new FileReader(fileToReview);
				bf = new BufferedReader(fr);
				while (bf.ready()) {
					System.out.print((char)bf.read());
				}
				break;
			} catch (Exception e) {
				tries++;
				if (tries == 2) {
					System.out.println("Could not open input file agail. File does not exist or could not be created!");
					System.out.println("Sorry! I am unable to display your desired files! Program will exit!");
					return;
				}
				System.out.println("Could not open input file. File does not exist; possibly it could not be created!");
			}
			
		}
	}
	
	
	//TODO
	public static void processFilesForValidation(String fileInformation, int number, String fileName) throws Exception {
		
		String tempToken = null;
		String id = null;
		String interimKey = null;
		String delim = "{},=\n";
		
		String[] files = {String.format("IEEE%d.json", number), String.format("ACM%d.json", number), String.format("NJ%d.json", number)};
		
        StringTokenizer st = new StringTokenizer(fileInformation, delim, true);

        Stack<IToken> tokenStack = new Stack<IToken>();
        Stack<Boolean> bracketStack = new Stack<Boolean>();
        
        HashMap<String, HashMap<String, String>> rawData = new HashMap<String, HashMap<String, String>>();
        
        
        
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
        		
					if (tokenStack.peek().GetTokenType() == TokenType.OpenBracket) {
						
						List<PrintWriter> list = pendingFiles.get(number);
						for (PrintWriter printWriter : list) {
							printWriter.close();
						}
				        for (String string : files) {
							File file = new File(string);
							file.delete();
						}
						
						 throw new FileInvalidException(String.format("""
						 		Error: Detected Empty Field!
						 		============================
						 		
						 		Problem detected with input file: %s
						 		File is Invalid: Field "%s" is Empty. Processing stopped at this point. Other empty fields may be present as well!
						 		""", fileName, interimKey));
					}
					
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
        			if (tempToken.equals("}") && bracketStack.size() == 2) {
        				break;
        			}
        			if (tempToken.equals("{"))
        				bracketStack.add(true);
        			
        			if (tempToken.equals("}"))
        				bracketStack.pop();
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
        
        List<Article> listOfArticles = new ArrayList<Article>();

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
        
        PrintWriter pw1 = new PrintWriter(files[0]);
        
        for (Article article : listOfArticles) {
			pw1.append(article.Format(FormatType.IEEE) +  "\n\n");
		}
        //System.out.println();
        
        PrintWriter pw2 = new PrintWriter(files[1]);
        
        int counter = 0;
        for (Article article : listOfArticles) {
			pw2.append("[" + counter + "] " + article.Format(FormatType.ACM)  + "\n\n");
			
		}
        
        //System.out.println();
        PrintWriter pw3 = new PrintWriter(files[2]);
        
        for (Article article : listOfArticles) {
			pw3.append(article.Format(FormatType.NJ) + "\n\n");
		}
        
        pw1.close();
        pw2.close();
        pw3.close();
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
