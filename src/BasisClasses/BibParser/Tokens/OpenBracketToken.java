package BasisClasses.BibParser.Tokens;

import java.util.Stack;

public class OpenBracketToken implements IToken{

	public static IToken match(String curToken, Stack<IToken> tokenStack) {
		IToken prevToken = tokenStack.peek();
		TokenType prevTokenType =  prevToken.GetTokenType();
		if (curToken.equals(GetTokenMatchStatic()) && (prevTokenType == TokenType.Article || prevTokenType == TokenType.Assign))
			return new OpenBracketToken();
		
		return null;
	}
	
	@Override
	public TokenType GetTokenType() {
		// TODO Auto-generated method stub
		return TokenType.OpenBracket;
	}

	@Override
	public String GetTokenMatch() {
		// TODO Auto-generated method stub
		return GetTokenMatchStatic();
	}

	@Override
	public String GetValue() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private static String GetTokenMatchStatic() {
		return "{";
	}

}
