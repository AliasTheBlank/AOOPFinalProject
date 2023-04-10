package BasisClasses.BibParser.Tokens;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IDToken implements IToken{
	
	private String id;
	
	public IDToken(String id) {
		this.id = id;
	}
	
	public static IToken match(String curToken, Stack<IToken> tokenStack) {
		
		Pattern pattern = Pattern.compile(GetTokenMatchStatic());
		Matcher matcher = pattern.matcher(curToken);
		if (matcher.find()) {
			if (tokenStack.size() < 2) return null;
			
			// Fetch second element
			IToken prevToken = tokenStack.pop();
			IToken prevPrevToken = tokenStack.peek();
			tokenStack.push(prevToken);
		
			if (prevToken.GetTokenType() == TokenType.OpenBracket && prevPrevToken.GetTokenType() == TokenType.Article) {
				return new IDToken(curToken);
			} else {
				return null;
			}
		}
		
		return null;
		
			
	}

	@Override
	public TokenType GetTokenType() {
		// TODO Auto-generated method stub
		return TokenType.ID;
	}

	@Override
	public String GetTokenMatch() {
		// TODO Auto-generated method stub
		return GetTokenMatchStatic();
	}

	@Override
	public String GetValue() {
		// TODO Auto-generated method stub
		return id;
	}

	private static String GetTokenMatchStatic() {
		return "^[0-9]*$";		
	}
}
