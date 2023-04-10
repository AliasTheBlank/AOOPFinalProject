package BasisClasses.BibParser.Tokens;

import java.util.Stack;

public class ArticleToken implements IToken{
		
	public static IToken match(String curToken, Stack<IToken> tokenStack) {
		
		if (curToken.equals(GetTokenMatchStatic()) && tokenStack.empty()) {
			return new ArticleToken();
		}
		return null;
	}

	@Override
	public  TokenType GetTokenType() {
		return TokenType.Article;
	}

	@Override
	public String GetTokenMatch() {
		return GetTokenMatchStatic();
	}
	

	@Override
	public String GetValue() {
		return null;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o || ((TokenType)o) == GetTokenType()) {
			return true;
		}
		return false;
	}

	private static String GetTokenMatchStatic() {
		return "@ARTICLE";
	}
	
}
