package BasisClasses.BibParser.Tokens;

import java.util.Stack;

import BasisClasses.FileInvalidException;

public class AssignToken implements IToken{

	public static IToken match(String curToken, Stack<IToken> tokenStack) throws FileInvalidException {
		
		if (curToken.equals(GetTokenMatchStatic()))
			if (tokenStack.peek().GetTokenType() == TokenType.Key) {
				return new AssignToken();
			} else {
				throw new FileInvalidException("Error, assignment token isn't related to a key");
			}
		
		return null;
	}
	
	@Override
	public TokenType GetTokenType() {
		
		return TokenType.Assign;
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
		return "=";
	}
}
