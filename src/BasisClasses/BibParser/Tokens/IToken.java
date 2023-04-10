package BasisClasses.BibParser.Tokens;

public interface IToken {
	
	public TokenType GetTokenType();
	public String GetTokenMatch();
	public String GetValue() ;
	
	
}
