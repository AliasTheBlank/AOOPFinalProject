package BasisClasses.BibParser.Tokens;

import java.util.Stack;

public class ArticleToken implements IToken{
		
	public static IToken match(String rawToken, Stack<IToken> tokenStack) {
		if (rawToken == GetTokenMatch() && tokenStack.empty()) {
			return new ArticleToken();
		}
		return null;
	}

	@Override
	public TokenType GetTokenType() {
		return TokenType.Article;
	}

	@Override
	public String GetTokenMatch() {
		return "@ARTICLE";
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

	
}
