package BasisClasses;

public class Article {

	private int id;
	private String author;
	private String journal;
	private String title;
	private int year;
	private String volume;
	private int number;
	private int pages;
	private String[] keywords;
	private String doi;
	
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
	public Article(int id, String author, String journal, String title, int year, String volume, int number, int pages,
			String doi, String ... keywords) {
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
	public Article(int id, String author, String journal, String title, int year, String volume, int number, int pages,
			String[] keywords, String doi) {
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
	}
	
	
}
