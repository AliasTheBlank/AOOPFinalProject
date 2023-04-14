package BasisClasses.BibParser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

import BasisClasses.FileInvalidException;

public class BibCreator {

	public static void main(String[] args) {
		
		System.out.println("Welcome to BibCreator!");
		
		Scanner scanner1;
		Scanner scanner2;
		Scanner scanner3;
		Scanner scanner4;
		Scanner scanner5;
		Scanner scanner6;
		Scanner scanner7;
		Scanner scanner8;
		Scanner scanner9;
		Scanner scanner10;
		
		StringBuilder[] sb = new StringBuilder[10];
		
		try {
			scanner1 = new Scanner(new FileInputStream("./Final_Project_420-PA3-AS/Latex.bib"));
			sb[0] = catchData(scanner1);
			
			scanner2 = new Scanner(new FileInputStream("./Final_Project_420-PA3-AS/Latex2.bib"));
			sb[1] = catchData(scanner2);
			
			scanner3 = new Scanner(new FileInputStream("./Final_Project_420-PA3-AS/Latex3.bib"));
			sb[2] = catchData(scanner3);
			
			scanner4 = new Scanner(new FileInputStream("./Final_Project_420-PA3-AS/Latex4.bib"));
			sb[3] = catchData(scanner4);
			
			scanner5 = new Scanner(new FileInputStream("./Final_Project_420-PA3-AS/Latex5.bib"));
			sb[4] = catchData(scanner5);
			
			scanner6 = new Scanner(new FileInputStream("./Final_Project_420-PA3-AS/Latex6.bib"));
			sb[5] = catchData(scanner6);
			
			scanner7 = new Scanner(new FileInputStream("./Final_Project_420-PA3-AS/Latex7.bib"));
			sb[6] = catchData(scanner7);
			
			scanner8 = new Scanner(new FileInputStream("./Final_Project_420-PA3-AS/Latex8.bib"));
			sb[7] = catchData(scanner8);
			
			scanner9 = new Scanner(new FileInputStream("./Final_Project_420-PA3-AS/Latex9.bib"));
			sb[8] = catchData(scanner9);
			
			scanner10 = new Scanner(new FileInputStream("./Final_Project_420-PA3-AS/Latex10.bib"));
			sb[9] = catchData(scanner10);
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		
	}
	
	//TODO
	public static void processFilesForValidation(String file) throws FileInvalidException {
		
		
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
