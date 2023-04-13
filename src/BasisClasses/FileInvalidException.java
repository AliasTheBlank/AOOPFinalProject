package BasisClasses;

public class FileInvalidException extends Exception{
	
	public FileInvalidException() {
		System.out.println("Error: Input file cannot be parsed due to missing information\r\n"
				+ "(i.e. month={}, title={}, etc.)");
	}
	
	public FileInvalidException(String message) {
		System.out.println(message);
	}

}
