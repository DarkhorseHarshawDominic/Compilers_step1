import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.io.FileInputStream;
import java.io.*;
import java.util.Scanner;

public class Driver{

	public static void main(String args[])throws Exception {
		String inputFile = null;
		if(args.length > 0)
			inputFile = args[0];
		InputStream is = System.in;
		if(inputFile != null)
			is = new FileInputStream(inputFile);
		ANTLRInputStream input = new ANTLRInputStream(is);
		LittleLexer lexer = new LittleLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		spaceWalk(tokens);
	}

	static void spaceWalk(CommonTokenStream tok){
		int lim = tok.getNumberOfOnChannelTokens();

		int tmp = 0;
		for(int x=1;tmp!=-1;x++){
			tmp = tok.LT(x).getType();
			if(tmp>= 1 && tmp <= 7){
				switch(tmp){
					case 1:
						System.out.println("TokenType: KEYWORD");
						break;
					case 2:
						System.out.println("TokenType: INDENTIFIER");
						break;
					case 3:
						System.out.println("TokenType: INTLITERAL");
						break;
					case 5:
						System.out.println("TokenType: STRINGLITERAL");
						break;
					case 4:
						System.out.println("TokenType: FLOATLITERAL");
						break;
					case 6:
						System.out.println("TokenType: OPERATOR");
						break;
					default :
						break;
				}
					System.out.println("Value: " + tok.LT(x).getText());
			}
		}

	}

}
