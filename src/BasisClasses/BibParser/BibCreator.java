package BasisClasses.BibParser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;

import BasisClasses.FileInvalidException;
import BasisClasses.BibParser.Tokens.IToken;

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
		
	}
	
	//TODO
	public static void processFilesForValidation(String file) {
		
		String delim = "{},=\n";
        StringTokenizer st = new StringTokenizer(file, delim, true);

        Stack<IToken> tokenStack = new Stack<IToken>();
        Stack<Boolean> bracketStack = new Stack<Boolean>();

        HashMap<String, HashMap<String, String>> rawData = new HashMap<String, HashMap<String, String>>();
	}
	
	public static StringBuilder catchData(Scanner file) {
		StringBuilder sb = new StringBuilder();
		
		while (file.hasNext()) {
			sb.append(file.nextLine() +  "\n");
		}
		
		file.close();
		
		return sb;
	}
}
