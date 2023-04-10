package BasisClasses.BibParser.Tokens;

import java.util.Stack;

public class SeparatorToken implements IToken {

	public static IToken match(String curToken, Stack<IToken> tokenStack) {
		
		if (curToken.equals(GetTokenMatchStatic()) && tokenStack.peek().GetTokenType() != TokenType.Value)
			return new SeparatorToken();
		
		return null;
	}
	
	@Override
	public TokenType GetTokenType() {
		// TODO Auto-generated method stub
		return TokenType.Separator;
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
		return ",";
	}

}
