package BasisClasses.BibParser.Tokens;

import java.util.Stack;

public class CloseBracketToken implements IToken{

	
	public static IToken match(String curToken, Stack<Boolean> bracketStack) {
		if (curToken.equals(GetTokenMatchStatic()) && !bracketStack.empty())
			return new CloseBracketToken();
		
		return null;
	}
	
	@Override
	public TokenType GetTokenType() {
		// TODO Auto-generated method stub
		return TokenType.CloseBracket;
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
		return "}";
	}
}
