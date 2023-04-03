package BasisClasses;

import java.net.URI;
import java.time.Month;

public class Article {

	private int id;
	private String[] author;
	private String journal;
	private String title;
	private int year;
	private String volume;
	private int number;
	private String pages;
	private String[] keywords;
	private String doi;
	private String month;
	
	/**
	 * @param id
	 * @param author
	 * @param journal
	 * @param title
	 * @param year
	 * @param volume
	 * @param number
	 * @param pages
	 * @param keywords
	 * @param doi
	 */
	public Article(int id, String[] author, String journal, String title, int year, String volume, int number, String pages,
			String doi, String month,String ... keywords) {
		this.id = id;
		this.author = author;
		this.journal = journal;
		this.title = title;
		this.year = year;
		this.volume = volume;
		this.number = number;
		this.pages = pages;
		this.keywords = new String[keywords.length];
		
		for (int i = 0; i < keywords.length; i++) 
			this.keywords[i] = keywords[i];

		
		this.doi = doi;
		this.month = month;
	}
	

	/**
	 * @param id
	 * @param author
	 * @param journal
	 * @param title
	 * @param year
	 * @param volume
	 * @param number
	 * @param pages
	 * @param keywords
	 * @param doi
	 */
	public Article(int id, String[] author, String journal, String title, int year, String volume, int number, String pages,
			String[] keywords, String doi, String month) {
		this.id = id;
		this.author = author;
		this.journal = journal;
		this.title = title;
		this.year = year;
		this.volume = volume;
		this.number = number;
		this.pages = pages;
		this.keywords = keywords;
		this.doi = doi;
		this.month = month;
	}
	
	public String Format(FormatType type) {
			
			switch (type) {
			case IEEE: {
				
				StringBuilder string = new StringBuilder();
				
				
				string.append(listAuthors(','));
				string.append(". " + String.format("\"%i\"", title));
				string.append(", " + journal);
				string.append(", vol. " + volume);
				string.append(", no. " + String.valueOf(number));
				string.append(", p. " + pages);
				string.append(", " + month + " " + String.valueOf(year));
				
				return new String(string);
			}
			
			case ACM: {
				
				StringBuilder acmString = new StringBuilder();
				String doiLink = "https://doi.org/";
				
				if (author.length == 1) 
					acmString.append(author[0] + ".");
				else
					acmString.append(author[0] + "et al.");
				
				acmString.append(" " + year);
				acmString.append(". " + title);
				acmString.append(". " + journal);
				acmString.append(". PP, " + number);
				acmString.append(String.format(" (%i)", year));
				acmString.append(", " + pages);
				acmString.append(". DOI:" + doiLink + doi);
				
				return new String(acmString);
			}
			
			case NJ: {
				
				StringBuilder njString = new StringBuilder();
				njString.append(listAuthors('&'));
				njString.append(". " + title);
				njString.append(". " + journal);
				njString.append(". " + volume);
				njString.append(", " + pages);
				njString.append(String.format("(%i)." , year));
				
				return new String(njString);
			}
			
			
			default:
				throw new IllegalArgumentException("Unexpected value: " + type);
			}
		}
	
	private String listAuthors(char delimiter) {
		
		StringBuilder string = new StringBuilder();
		
		for (int i = 0; i < author.length; i++) {
			if (i == 0) 
				string.append(author[i]);
			else
				string.append(delimiter + " " + author[i]);
		}
		
		return new String(string);
	}
}
