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
		LittleParser parser = new LittleParser(tokens);
		
		ParseTree tree = parser.prog(); // parse; start at prog
		ParseTreeWalker walker = new ParseTreeWalker();
		System.out.println(tree.toStringTree(parser));

	/*try{
		FileWriter fileWriter = new FileWriter(System.getProperty("user.dir") + "/tmp.txt");
		Process p = Runtime.getRuntime().exec("java -Xmx500M -cp //home/50/n00851750/COP4620/lib/antlr-4.9-complete.jar:$CLASSPATH org.antlr.v4.gui.TestRig' grun Little prog " + args + "-tokens ");
		//BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;
		//System.out.println(reader.readLine());
		InputStream in = p.getInputStream();
		//System.out.println(in.available());
		for(int x=0;x<in.available();x++)/*while((line = reader.readLine()) != null){
			//fileWriter.write(in.read()/*line + "\n");
			System.out.println(in.read());
		}

		fileWriter.close();*/

		//walker.walk(new LilListener(), tree);
		
		//System.out.println(tree.getSymbol());
		//walker.walk(new tokenTyper(), tree);
		//System.out.println(voc.getSymbolicName());
		//
		//walk(tree);
	/*}catch (Exception e){
		e.printStackTrace();
	}*/

	/*	System.out.println(tokens.size());
	for(int x=0; x< tokens.size();x++){

	try{
		Token tmp = tokens.LT(x);
		if(tmp.getTokenIndex()!=-1)
			System.out.println(tmp.getText());
	}catch (Exception e){
		
	}
	}*/

		walker.walk(new typeVal(), tree);

	}


	static void walk(ParseTree t){
		//Object tmp = t.getPayload();
		//TerminalNodeImpl tmp0 = t.
		System.out.println("Token Type: " + /*tmp.getToken() +*/ "\nValue : " + t.getText());
		
		int lim = t.getChildCount();
		for(int i=0;i<lim;i++)
			walk(t.getChild(i));
	}



	/*static String walkA(ProgContext ctx){
		return ctx.getChild(0).getText();
	}*/


	public static class typeVal extends LittleBaseListener {
		public void enterProg(LittleParser.ProgContext ctx){
			System.out.println("Token Type: " + ctx.getPaylod() + "\nValue: " + ctx.getChild(0).getText());
		}
	}
}
