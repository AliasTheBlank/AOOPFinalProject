package BasisClasses.BibParser.Tokens;

import java.util.Stack;

public class ValueToken implements IToken {

	private String value;
	
	public ValueToken(String value) {
		this.value = value;
	}
	
	public static ValueToken match(String curToken, Stack<IToken> tokenStack)  {
		
		if(tokenStack.size() < 2) return null;
		
		IToken prevToken = tokenStack.pop();
		IToken prevPrevToken = tokenStack.peek();
		tokenStack.push(prevToken);
		
		if (prevToken.GetTokenType() == TokenType.OpenBracket && prevPrevToken.GetTokenType() == TokenType.Assign) {
			return new ValueToken(curToken);
		} 
		
		else {
			return null;
		}
	}
	
	@Override
	public TokenType GetTokenType() {
		// TODO Auto-generated method stub
		return TokenType.Value;
	}

	@Override
	public String GetTokenMatch() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String GetValue() {
		// TODO Auto-generated method stub
		return value;
	}

	
	public void UpdateValue(String value) {
		this.value = value;
	}
}
