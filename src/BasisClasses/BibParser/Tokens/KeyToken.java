package BasisClasses.BibParser.Tokens;

import java.util.Stack;

public class KeyToken implements IToken {
	
	private String keyName;
	
	public KeyToken(String keyName) {
		this.keyName = keyName;
	}

	public static IToken match(String rawToken, Stack<IToken> tokenStack, Stack<Boolean> bracketStack, String nextRawToken) {
		
		if (nextRawToken.equals("=") && 
			!tokenStack.isEmpty() && 
			tokenStack.peek().GetTokenType() == TokenType.Separator &&
			bracketStack.size() == 1) {
			
		}
		
		return null;
	}

	@Override
	public TokenType GetTokenType() {
		// TODO Auto-generated method stub
		return TokenType.Key;
	}

	@Override
	public String GetTokenMatch() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String GetValue() {
		// TODO Auto-generated method stub
		return keyName;
	}
	
	

}
