import java.util.*;
import java.nio.file.*;
import java.io.*;

/**
 * @author Henrique Rebelo
*/
enum TokenType {

	// Literals.
	NUM,

	// Single-character tokens for operations.
	MINUS, PLUS, SLASH, STAR,
	
	EOF

}

/**
 * @author Henrique Rebelo
 */
class Token {

	public final TokenType type; // token type
	public final String lexeme; // token value

	public Token (TokenType type, String value) {
		this.type = type;
		this.lexeme = value;
	}

	@Override
	public String toString() {
		return "Token [type=" + this.type + ", lexeme=" + this.lexeme + "]";
	}
}


public class reversePolishNotation{

    public static void main (String[] args) throws Exception{
        File file = new File(args[0]);
        List<Token> tokens = lexicalAnalyser(file);
        Stack<Token> stack = new Stack<Token>();
        Token num1, num2;
        
        for(Token t : tokens){
            if(t.type!=TokenType.NUM){
                num1 = stack.pop();
                num2 = stack.pop();
                stack.push(operation(num2,t,num1));
                
            }
            else
                stack.push(t);
        }
        
        System.out.println(stack.pop().lexeme);
    }
    public static List<Token> lexicalAnalyser(File file) throws Exception{
        Scanner sc = new Scanner(file);
        String token;
        List<Token> tokens = new ArrayList<Token>();
        while (sc.hasNextLine()){
          token = sc.nextLine().strip();
            if(token.matches("^-?\\d+$"))
                tokens.add(new Token(TokenType.NUM,token));

            else if(token.equals("*"))
                tokens.add(new Token(TokenType.STAR,token));

            else if(token.equals("/"))
                tokens.add(new Token(TokenType.SLASH,token));

            else if(token.equals("+"))
                tokens.add(new Token(TokenType.PLUS,token));

            else if(token.equals("-"))
                tokens.add(new Token(TokenType.MINUS,token));
                
            else
                throw new Exception("Error: Unexpected character: " + token);
        }
        sc.close();

        return tokens;
    }
    public static Token operation(Token num1,Token op,Token num2){
        int numInt1 = Integer.parseInt(num1.lexeme);
        int numInt2 = Integer.parseInt(num2.lexeme);
        int result = 0;

        if(op.type == TokenType.STAR)
            result = numInt1*numInt2;

        else if(op.type == TokenType.SLASH)
            result = numInt1/numInt2;

        else if(op.type == TokenType.PLUS)
            result = numInt1+numInt2;

        else if(op.type == TokenType.MINUS)
            result = numInt1-numInt2;

        return new Token(TokenType.NUM,Integer.toString(result));
    }
}