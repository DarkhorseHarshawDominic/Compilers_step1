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
		parser.removeErrorListeners();
		parser.addErrorListener(new ADListener());
		parser.addParseListener(new symLis());
		ParseTree tree = parser.prog();
		//System.out.println("Accepted");
		//System.out.println("Not Accepted");
		//System.out.println(tree.toStringTree(parser));
		//spaceWalk(tokens);
	}//main

	public static class ADListener extends BaseErrorListener{

		@Override
		public void syntaxError(Recognizer<?, ?> recognizer,
					Object offendingSymbol,
					int line,
					int charPostitionInLine,
					String msg,
					RecognitionException e)
		{
			System.out.println("Not Accepted");
			System.exit(1);
		}//syntaxError
	}//ADListener

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
					default:
						break;
				}//switch
					System.out.println("Value: " + tok.LT(x).getText());
			}//if
		}//for

	}//spaceWalk

}//Driver.java

class symLis extends LittleBaseListener{
	neohash neo;
	String scope;
	AST ast;
	converter con;
	symLis(){
		neo = new neohash();
		ast = new AST();
		con = new converter(ast, neo);
	}//symLis

	@Override
	public void enterProg(LittleParser.ProgContext ctx) {
		//System.out.println("GLOBAL");
		neo.globalSet();
	}//enterProg
	@Override public void exitProg(LittleParser.ProgContext ctx) {
		//System.out.println("GLOBAL");
		//neo.print();
		//neo.num(1);
		//ast.print();
		con.convert();
		con.print();
	}//exitProg
	@Override
	public void exitString_decl(LittleParser.String_declContext ctx) {
		neo.insert(new String("STRING"), ctx.id().getText(), ctx.str().getText());
	}//exitString_decl
	@Override
	public void exitVar_decl(LittleParser.Var_declContext ctx) {
		//System.out.println("VAR");

		if(ctx.id_list().id_tail().getText().length() > 0){
			neo.insert(ctx.var_type().getText(), ctx.id_list().id().getText(), null);
			//System.out.println("WITNESS ME " + ctx.id_list().id_tail().getText());
			//System.out.println("TELL ME " + ctx.id_list().id_tail().getText().length());

			int lim = ctx.id_list().id_tail().getText().length()-1;
			char buffer[] = new char[lim];
			int loss = 1;

			buffer[0] = ctx.id_list().id_tail()/*getChild(1)*/.getText().charAt(1);
			//System.out.println(buffer);
			char tmp;
			int x = 1;

			//rip&submit until it is done.
			//no commas allowed
			for(; x < lim; x++){
				//System.out.println("x :" + x);
				//if(x < lim){//get next char
						tmp = ctx.id_list().id_tail()/*getChild(1)*/.getText().charAt(x+1);
						//System.out.println(tmp);
					if(tmp != ','){
						buffer[loss] = tmp/*ctx.getChild(1).getText().charAt(x)*/;
						loss++;
						//System.out.println(buffer);
					}//if
					else{
						//System.out.println("name " + new String(buffer) + "\ttype " + ctx.var_type().getText());//verify
						neo.insert(ctx.var_type().getText(), new String(buffer), null);
						buffer = new char[lim-x];
						loss = 0;
					}//else
				//}//if
			}//for
			//buffer[loss] = ctx.getChild(1).getText().charAt(x);
			//System.out.println("name " + new String(buffer) + " type " + ctx.var_type().getText());
			neo.insert(ctx.var_type().getText(), new String(buffer), null);
			//arr.insert(ctx.var_type().getText(), new String(buffer), null);
		}//if
		else{//1 declaration
			neo.insert(ctx.var_type().getText(), ctx.id_list().id().getText(), null);
			//System.out.println(ctx.var_type().getText() + " " + ctx.id_list().id().getText());
		}//else	
		
	}//Var_decl
	/*@Override public void enterVar_type(LittleParser.Var_typeContext ctx) { }
	@Override public void exitVar_type(LittleParser.Var_typeContext ctx) { }
	@Override public void enterAny_type(LittleParser.Any_typeContext ctx) { }
	@Override public void exitAny_type(LittleParser.Any_typeContext ctx) { }
	@Override public void enterId_list(LittleParser.Id_listContext ctx) { }
	@Override public void exitId_list(LittleParser.Id_listContext ctx) { }
	@Override public void enterId_tail(LittleParser.Id_tailContext ctx) { }
	@Override public void exitId_tail(LittleParser.Id_tailContext ctx) { }exitId_tail
	@Override public void enterParam_decl_list(LittleParser.Param_decl_listContext ctx) { }
	@Override public void exitParam_decl_list(LittleParser.Param_decl_listContext ctx) { }
	@Override public void enterParam_decl(LittleParser.Param_declContext ctx) { }
	@Override public void exitParam_decl(LittleParser.Param_declContext ctx) { }
	@Override public void enterParam_decl_tail(LittleParser.Param_decl_tailContext ctx) { }
	@Override public void exitParam_decl_tail(LittleParser.Param_decl_tailContext ctx) { }
	@Override public void enterFunc_declarations(LittleParser.Func_declarationsContext ctx) { }
	@Override public void exitFunc_declarations(LittleParser.Func_declarationsContext ctx) {}
	@Override public void enterFunc_decl(LittleParser.Func_declContext ctx) { arr.up(ctx.id().getText()); }*/
	@Override
	public void exitFunc_decl(LittleParser.Func_declContext ctx) {
		//System.out.println("FUNCTIONNAME " + ctx.id().getText() + " " + ctx.param_decl_list().getText());

		neo.functionSet(ctx.id().getText(), ctx.any_type().getText(), ctx.param_decl_list().getText());
		neo.num(1);
		ast.function(ctx.id().getText());//activate funciton protocol
	}//exitFunc_decl
	@Override public void enterFunc_body(LittleParser.Func_bodyContext ctx) { 
		//System.out.println("FI");
		neo.num(0);
		neo.insert("D", "D", null);
		ast.ins(0,1, new String("D"));
		//place dummy
	}
	/*@Override public void exitFunc_body(LittleParser.Func_bodyContext ctx) {}//exitFunc_body
	@Override public void enterStmt_list(LittleParser.Stmt_listContext ctx) { }
	@Override public void exitStmt_list(LittleParser.Stmt_listContext ctx) { }
	@Override public void enterStmt(LittleParser.StmtContext ctx) { }
	@Override public void exitStmt(LittleParser.StmtContext ctx) { }
	@Override public void enterBase_stmt(LittleParser.Base_stmtContext ctx) { }
	@Override public void exitBase_stmt(LittleParser.Base_stmtContext ctx) { }
	@Override public void enterAssign_stmt(LittleParser.Assign_stmtContext ctx) { }
	@Override public void exitAssign_stmt(LittleParser.Assign_stmtContext ctx) { }
	@Override public void enterAssign_expr(LittleParser.Assign_exprContext ctx) { }*/
	@Override
	public void exitAssign_expr(LittleParser.Assign_exprContext ctx) {
		//generate := node
		ast.ins(0, 2, "=");

		//submit left id
		String left = ctx.id().getText();//left id
		ast.ins(1, 3, left);
		//System.out.println("left " + left);

		String str = ctx.expr().getText();//right side
		//System.out.println("expr " + ctx.expr().getText());
		int lim = str.length();

		if(lim > 1 && (str.charAt(0) > 57 || str.charAt(0) < 48)){
			//System.out.println(" afsafas "  + ((str.charAt(1) > 57 || str.charAt(1) < 48) && str.charAt(1) != 46));
			if((str.charAt(1) > 57 || str.charAt(1) < 48) && str.charAt(1) != 46){//not 0-9 and not .
				int x = 1;//curr charAt location
				char[]c = new char[1];//buffer size 1
				if(str.charAt(0) == '('){//dodges (
					c[0] = str.charAt(1);
					x++;//inc x
				}//if
				else{//no dodge
					c[0] = str.charAt(0);
				}//else

				String tmp = new String(c);//c[0] -> tmp

				//rip and tear until it is done
				//int x = 1;
				for(;x < lim && (c[0] > 66 && c[0] < 91 || c[0] > 96 && c[0] < 122);x++){//first id a-zAz
					tmp.concat(new String(c));//update tmp
					c[0] = str.charAt(x);//new c[0]
				}//for
	
				//System.out.println("test " + tmp);//SUBMIT

				char[] opr = new char[1];//operand

				if(str.charAt(x-1) > 41 && str.charAt(x-1) < 48)//operand here
					opr[0] = str.charAt(x-1);
				else//or operand is here
					opr[0] = str.charAt(x++);//and inc x
				ast.ins(2,4,new String(opr));//SUBMIT OPERAND
				ast.ins(2,3,tmp);//SUBMIT TMP

				//System.out.println("opr " + new String(opr));

				if(x < lim){//prevents arrayOutOfBounds
					c[0] = str.charAt(x);//begin next id
					tmp = new String(c);//c[0] -> tmp
					x++;//update x
					
					for(;x < lim && ((c[0] > 66 && c[0] < 91 || c[0] > 96 && c[0] < 122) || (c[0] > 47 && c[0] < 58) || c[0] == 46);x++){//second id
						//System.out.println(c);
						if(str.charAt(x) != ')'){//dodges )
							c[0] = str.charAt(x);
							tmp = new String(tmp + c[0]);
						}//if
						//System.out.println(tmp);
					}//for
				}//if
				//System.out.println("test2 " + tmp);//SUBMIT
				ast.ins(2,3,tmp);
			}//if
			else if(str.charAt(1) > 47 && str.charAt(1) < 58 || str.charAt(1) == 46){//number that isn't single digit || .
				char c[] = new char[1];
				c[0] = str.charAt(0);
				String tmp = new String(c);

				for(int x = 1;x < lim;x++){
					c[0] = str.charAt(x);
					tmp = (new String(tmp + c[0]));
				}//for

				System.out.println("test3 " + tmp);
				ast.ins(2,3,tmp);//SUBMIT tmp
			}//else if
		}//if
		else{//single assignment
			//System.out.println("test4 " + str);
			ast.ins(3,3,str);
		}//else

	}//exitAssign_expr
	//@Override public void enterRead_stmt(LittleParser.Read_stmtContext ctx) { }
	@Override
	public void exitRead_stmt(LittleParser.Read_stmtContext ctx){
		//System.out.println("READ " + ctx.id_list().id().getText());//SUBMIT
		ast.ins(0,6, ctx.id_list().id().getText());

		if(ctx.id_list().id_tail().getText().length() > 0){
			String str = ctx.id_list().id_tail().getText();
			//System.out.println("ALSO READ " + str);

			int lim = str.length();
			char []buffer = new char[lim];//size of str length
			int x = 1;//1 because 0 == ','
			int y = 0;//buffer current
			//rip and tear until it is done
			while(x != lim){
				if(str.charAt(x) != ','){
					buffer[y++] = str.charAt(x++);
				}//if
				else{
					//System.out.println("ALSO READ " + new String(buffer));
					ast.ins(0,6, new String(buffer));
					x++;//skip ,
					buffer = new char[lim - x];//shrink buffer
					y = 0;//reset buffer current
				}//else
			}//while
			//System.out.println("ALSO READ " + new String(buffer));
			ast.ins(0,6, new String(buffer));
		}//if
	}//exitRead_stmt
	//@Override public void enterWrite_stmt(LittleParser.Write_stmtContext ctx) { }
	@Override
	public void exitWrite_stmt(LittleParser.Write_stmtContext ctx){
		//System.out.println("WRITE " + ctx.id_list().id().getText());//SUBMIT
		ast.ins(0,7, ctx.id_list().id().getText());

		if(ctx.id_list().id_tail().getText().length() > 0){
			String str = ctx.id_list().id_tail().getText();
			//System.out.println("ALSO WRITE " + str);

			int lim = str.length();
			char []buffer = new char[lim];//size of str length
			int x = 1;//1 because 0 == ','
			int y = 0;//buffer current
			//rip and tear until it is done
			while(x != lim){
				if(str.charAt(x) != ','){
					buffer[y++] = str.charAt(x++);
				}//if
				else{
					//System.out.println("ALSO WRITE " + new String(buffer));
					ast.ins(0,7, new String(buffer));
					x++;//skip ,
					buffer = new char[lim - x];//shrink buffer
					y = 0;//reset buffer current
				}//else
			}//while
			//System.out.println("ALSO WRITE " + new String(buffer));
			ast.ins(0,7, new String(buffer));
		}//if
	}//exitWrite_stmt
	//@Override public void enterReturn_stmt(LittleParser.Return_stmtContext ctx) { }
	@Override
	public void exitReturn_stmt(LittleParser.Return_stmtContext ctx){
	}//exitReturn_stmt
	/*@Override public void enterExpr(LittleParser.ExprContext ctx) { }
	@Override public void exitExpr(LittleParser.ExprContext ctx) { }
	@Override public void enterExpr_prefix(LittleParser.Expr_prefixContext ctx) { }
	@Override public void exitExpr_prefix(LittleParser.Expr_prefixContext ctx) { }
	@Override public void enterFactor(LittleParser.FactorContext ctx) { }
	@Override public void exitFactor(LittleParser.FactorContext ctx) { }
	@Override public void enterFactor_prefix(LittleParser.Factor_prefixContext ctx) { }
	@Override public void exitFactor_prefix(LittleParser.Factor_prefixContext ctx) { }
	@Override public void enterPostfix_expr(LittleParser.Postfix_exprContext ctx) { }
	@Override public void exitPostfix_expr(LittleParser.Postfix_exprContext ctx) { }
	@Override public void enterCall_expr(LittleParser.Call_exprContext ctx) { }
	@Override public void exitCall_expr(LittleParser.Call_exprContext ctx) { }
	@Override public void enterExpr_list(LittleParser.Expr_listContext ctx) { }
	@Override public void exitExpr_list(LittleParser.Expr_listContext ctx) { }
	@Override public void enterExpr_list_tail(LittleParser.Expr_list_tailContext ctx) { }
	@Override public void exitExpr_list_tail(LittleParser.Expr_list_tailContext ctx) { }
	@Override public void enterPrimary(LittleParser.PrimaryContext ctx) { }
	@Override public void exitPrimary(LittleParser.PrimaryContext ctx) { }
	@Override public void enterAddop(LittleParser.AddopContext ctx) { }
	@Override public void exitAddop(LittleParser.AddopContext ctx) { }
	@Override public void enterMulop(LittleParser.MulopContext ctx) { }
	@Override public void exitMulop(LittleParser.MulopContext ctx) { }*/
	@Override
	public void enterIf_stmt(LittleParser.If_stmtContext ctx) {
		//System.out.println("IF IN");
		neo.scopeSet();
		//neo.num(0);
	}//enterIf_stmt
	@Override
	public void exitIf_stmt(LittleParser.If_stmtContext ctx) {
		//System.out.println("IF OUT");
		neo.num(1);
		//arr.down();//change scope
	}//exitIf_stmt
	@Override public void enterElse_part(LittleParser.Else_partContext ctx) {
		//System.out.println("ELSE IN");
		neo.scopeSet();
		//neo.num(0);
	}//enterElse_part
	@Override
	public void exitElse_part(LittleParser.Else_partContext ctx) {
		//System.out.println("ELSE OUT");
		if(ctx.decl() != null || ctx.stmt_list() != null){
			//neo.num(0);
			neo.num(1);
		}//if
	}//exitElse_part
	/*@Override public void enterCond(LittleParser.CondContext ctx) { }
	@Override public void exitCond(LittleParser.CondContext ctx) { }
	@Override public void enterCompop(LittleParser.CompopContext ctx) { }
	@Override public void exitCompop(LittleParser.CompopContext ctx) { }*/
	@Override
	public void enterWhile_stmt(LittleParser.While_stmtContext ctx) {
		//System.out.println("WI");
		neo.scopeSet();
		//neo.num(0);
		//arr.up(null);//change scope
	}//enterWhile_stmt
	@Override
	public void exitWhile_stmt(LittleParser.While_stmtContext ctx) {
		//System.out.println("WL");
		neo.num(1);

		//arr.down();//change scope
	}//exitWhile_stmt
	/*@Override public void enterEveryRule(ParserRuleContext ctx) { }
	@Override public void exitEveryRule(ParserRuleContext ctx) { }
	@Override public void visitTerminal(TerminalNode node) { }
	@Override public void visitErrorNode(ErrorNode node) { }*/

	class converter{
		String[] reg;//in-use register table
		int regsize;
		String[] arr;//holds converted code
		neonode var[];//in-use variables
		int varcurr;
		AST ast;//abstract syntax tree
		neohash neo;//symbol table
		int size;

		converter(AST newAst, neohash newNeo){
			reg = new String[600];//600 registers(200 Float, 200 Int, 200 String) and little extra for variables
			regsize = 0;
			var = new neonode[600];//var values
			varcurr = 0;
			arr = new String[1024];//Addressable memory limit unknown;1K lines of code oughta do it
			ast = newAst;
			neo = newNeo;
			size = 0;
		}//converter

		void convert(){
			//System.out.println("CONVERT");
			convert(ast.root);
		}//convert

		void convert(node root){//converts ast into tiny code w/ use of reg and neo
			//System.out.println("HERE");
			String str = null;
				switch(root.t()){
					case -1://GLOBAL; needs symbol table
						str = "GLOBAL";
					case 1://function
						//obtain function's symbol table
						if(str == null)
							str = root.v();
						request(str);
						//convert function's symbol table
						break;
					case 2://Assignment
						//System.out.println("Ass. " + root.getR().v());
						ass(root);
						break;
					case 6://read
						read(root);
						break;
					case 7://write
						write(root);
						break;
					default:
						if(root.getN() == null)//end of tree
							arr[size++] = new String("sys halt");
						break;
				}//switch
			if(root.getN() != null)
				convert(root.getN());//recursive call
		}//convert

		void read(node root){
			arr[size++] = new String("sys read" + type(root.v()) + " " + root.v());
		}//read

		void write(node root){
			arr[size++] = new String("sys write" + type(root.v()) + " " + root.v());
		}//write

		void ass(node root){
			//System.out.println(root.getR().v());
			if(root.getR().t() == 3){//assignment == a = b || j = 3.14
				if(type(root.getL().v()) == 'r' && regsize == 0)//unknown reasoning but no floats on r0
					regsize++;
				arr[size++] = new String("move " + root.getR().v() + " r" + regsize + "\nmove " + "r" + regsize + " " + root.getL().v());//move num into reg
				reg[regsize++] = root.getR().v();//store in register || update regsize
			}//if
			else if(root.getR().t() == 4){//operator detected
				arr[size++] = new String("move " + root.getR().getL().v() + " r" + regsize + "\nmul" + type(root.getL().v()) + " " + root.getR().getR().v() + " r" + regsize + "\nmove r" + regsize++ + " " + root.getL().v());
				reg[regsize-1] = new String(root.getR().getL().v() + " " + root.getR().v() + " " + root.getR().getR().v());//ex rN g - a
			}//else if
		}//ass

		char type(String str){//returns appropriate dataType
			int x = 0;
			while(x < varcurr){
				if(var[x].id.equals(str)){//find right variable
					if(var[x].dataType.charAt(0) == 'S')
						return 's';
					else if(var[x].dataType.equals("INT"))
						return 'i';
					else if(var[x].dataType.equals("FLOAT"))
						return 'r';
				}//if
				x++;
			}//while
			return 'i';//you shouldn't be here
		}//type

		void request(String sym){
			int lim = neo.size;
			int x = 0;
			while(x < lim){
				//System.out.println(": " + neo.arr[x].type);
				if(neo.arr[x].type.equals(sym)){
					if(neo.arr[x].dataType.equals("STRING")){
						arr[size++] = new String("str " + neo.arr[x].id + " " + neo.arr[x].value);//assemble string
						var[varcurr++] = neo.arr[x];//place var in var
					}//if
					else if(neo.arr[x].dataType.equals("FLOAT") || neo.arr[x].dataType.equals("INT")){
						arr[size++] = new String("var " + neo.arr[x].id);
						var[varcurr++] = neo.arr[x];//place var in var
					}//else if
				}//if
				x++;
			}//while
			//System.out.println(size + " " + sym);
		}//request
		
		void print(){
			int x = 0;
			while(x < size){
				System.out.println(arr[x]);
				x++;//update sentinel
			}//while
		}//print

		void printVar(){
			int x = 0;
			while(x < varcurr){
				System.out.println(var[x]);
			}//while
		}//printVar
	}//converter

	class node{
		int t;//node type
			//-1 HEAD
			//0 Dummy
			//1 Function
			//2 assignment
			//3 var_names
			//4 operator
			//5 Function end
			//6 Read
			//7 Write
		String v;//payload
		node p;//parent
		node l;//left-child
		node r;//right-child
		node n;//next

		node(int newT, String newV){ t = newT;	v = newV; }//node

		void l(node newL){ l = newL; }//l

		void p(node newP){p = newP; }//p

		void r(node newR){ r = newR; }//r

		void n(node newN){ n = newN; }//n

		node getP(){ return p; }//getP

		node getR(){ return r; }//getR

		node getN(){ return n; }//getN

		node getL(){ return l;}//getL

		String v(){ return v; }//v

		int t(){ return t; }//t

		void show(){
			if(this != null){
				if(l != null)
					l.show();
				System.out.println("type: " + t + " val : " + v + "\n");
				if(r != null)
					r.show();
				if(n != null)
					n.show();
			}//if
		}//show

		void update(String id){
			if(t == 1)
				v = id;
			else
				n.update(id);
		}//update
	}//node

	class AST{
		node curr;
		node root;
		int size;

		AST(){
			root = new node( -1, "root");
			curr = root;
			size = 0;
		}//AST

		void ins(int choice, int type, String value){
			value = fixer(value);
			switch(choice){
				case 0://next sequence in tree insertion
					curr.n(new node(type,value));
					curr = curr.n;//move curr
					break;
				case 1://left insertion at curr node
					curr.l(new node(type,value));
					break;
				case 2://right insertion at curr/oprerator node
					switch(type){
						case 4://operator
							curr.r(new node(type,value));
							break;
						case 3://operand
							if(curr.r.l == null)
								curr.r.l(new node(type,value));
							else
								curr.r.r(new node(type,value));
							break;
						default:break;
					}//switch
					break;
				case 3://right insertion at curr node; assignment expression
					curr.r(new node(type,value));
					break;
				default:
					break;
			}//switch
			size++;
		}//ins

		void print(){
			root.getN().show();
		}//print

		void function(String id){
			root.update(id);
			ast.ins(0,5,"END");
		}//function

		String fixer(String prob){
			int limit = prob.length();
			String sol = new String();
			int x = 0;
			for(;x < limit && prob.charAt(x) == 0x0;x++){}//for

			for(;x < limit;x++){//loops through entire prob
				if(prob.charAt(x) != 0x0)//checks for null characters
					sol = sol.concat(prob.substring(x,x+1));//appends non-nulls to clean string
			}//for
			return sol;
		}//fixer
	}//AST

	
	class neonode{
		String type;//node ID
		String dataType;
		String id;
		String value;
		int scopeNum;
		int scopeLvl;

		neonode(){
		}//neonode

		neonode(String newType, String newId, String newValue, String newDataType, int newScopeNum, int newScopeLevel){
			type = newType;
			dataType = newDataType;
			id = newId;
			value = newValue;
			scopeNum = newScopeNum;
			scopeLvl = newScopeLevel;
		}//neonode
	}//neonode

	class neohash{

		int scopeLvl;
		int scopeNum;
		neonode arr[];
		int size;
		int length;

		neohash(){
			scopeLvl = 0;
			scopeNum = 0;
			arr = new neonode[64];
			size = 0;
			length = 64;
		}//neohash

		void insert(String dataType, String id, String value){//level provided by neohash
			//System.out.println("INSERTING " + id + " " + dataType + " " + id.length() + " " + dataType.length());
			dataType = fixer(dataType);
			id = fixer(id);
			
			if(size == 0){//BEST CASE EMPTY
				arr[0] = new neonode();
				arr[0] = new neonode(new String("GLOBAL"), id, value, dataType, 0, 0);//insert
				size++;
			}//if
			else{//AVERAGE CASE
				if(!(eq(dataType,"D"))){
					if(search(id, dataType)){//find duplicates
						System.out.println("DECLARATION ERROR " + id);
						System.exit(1);
					}//if
					arr[size] = new neonode();
					arr[size++] = new neonode(arr[size-1].type, id, value, dataType, scopeNum, scopeLvl);//Even if DUMMY is adopted, functionSet method will check id and dataType for DUMMY identifiers
				}//if
				else{//insert dummy marker;updated when function listener occurs
					arr[size++] = new neonode("D", "D", null, "D", scopeNum, scopeLvl);
				}//else
						
			}//else

			if(scopeLvl == 0)
				arr[size-1].type = new String("GLOBAL");
			grow();//check for fill
			//stats();
		}//insert

		boolean search(String id, String dataType){
			neonode tmp;
			boolean flag = false;
			boolean flag0 = false;

			for(int x = size-1;x != -1 && flag == false;x--){
					tmp = arr[x];
					if(tmp.scopeLvl < scopeLvl){
						flag = true;
					}//if
					else if(tmp.scopeLvl == scopeLvl && eq(tmp.id,id) && eq(tmp.dataType,dataType)){
						flag0 = true;
						flag = true;
					}//else if
			}//while
			if(flag0 == true)
				return true;
			return false;
		}//search

		boolean eq(String a, String b){
			return a.equals(b);
		}//eq

		boolean L(){return size >= length/2;}//L

		void grow(){
			if(L()){
				int tmpLength = length*2;
				neonode tmp[] = new neonode[tmpLength];
				for(int x = 0; x <= length; x++){
					tmp[x] = arr[x];
				}//for
				length = tmpLength;
				arr = tmp;
			}//if
		}//grow

		void num(int choice){//up || down
			if(choice == 0){
				++scopeLvl;
				++scopeNum;
			}//if
			else{
				--scopeLvl;
			}//else
		}//lvl

		void shuffle(int dest){//opens spot in arr for functionSet manual insertion
			for(int x = size-1;x >= dest;x--){
				arr[x+1] = new neonode();
				arr[x+1] = arr[x];
			}//for
		}//

		void globalSet(){
			arr[size++] = new neonode(new String("G"), new String("G"), null, new String("G"), scopeNum, scopeLvl);
		}//globalSet

		void functionSet(String id, String dataType, String paramlist){//Completes function scope by promoting dummy markers;
			boolean flag = false;
			int x;
			for(x = size-1;x >= 0 && flag == false;x--){
				neonode tmp = arr[x];
				if(/*!(eq(tmp.id,"DUMMY")) && */!(eq(tmp.dataType,"D")))//variable detected
					arr[x].type = id;
				else if(eq(tmp.type,"D")){//DUMMY detected;avoids BLOCKX dummies
					arr[x].type = id;//promote dummy to function id scope
					arr[x].dataType = dataType;//save function's return type;use unknown
					arr[x].scopeNum = arr[x-1].scopeNum+1;//get scopeNum from preceding scope
					flag = true;
				}//else if
			}//for


			if(paramlist != null){
				String tmp0 = paramlist;//insert Function parameters between declaration and exisintg declarations
				int lim = tmp0.length();
				//rip&submit until it is done
				char []type;
				char []type0;
				int loss = 0;

				if(lim > 0){
					while(loss < lim){

						type  = new char[lim];
						type0 = new char[lim];
						int z = 0;

						while(z != 1 && loss < lim){//first word
							String tmp1 = tmp0;
							char tmp = tmp1.charAt(loss);
							type[loss] = tmp;
							loss++;
							
							if(tmp == 'T' || tmp == 'G'){
								z = 1;//break inner loop
							}//if

						}//while


						while(z != 0 && loss < lim){//second word
							char tmp = tmp0.charAt(loss);
							type0[loss] = tmp;
							loss++;

							if(tmp == 'F' || tmp == 'I' || tmp == 'S'){
								z = 0;//break inner loop
							}//if
						}//while
				
						if((search(fixer(new String(type)), fixer(new String(type0))))){
							System.out.println("DECLARATION ERROR " + id);
							System.exit(1);
						}//if

						shuffle(x+2);//make room for insert
						size++;//update size
						arr[x+2] = new neonode(id, fixer(new String(type0)), null, fixer(new String(type)), arr[x+1].scopeNum, 1);
						if(loss >= lim)
							return;
					}//while

				}//if
			}//if
			
			flag = false;
			for(x-= 1;x >= 0 && flag == false; x--){//locate duplicate declarations
				neonode tmp = arr[x];
				if(eq(tmp.type,id) && eq(tmp.dataType,dataType)){//Functions with dupe names and types not accepted
					System.out.println("DECLARATION ERROR " + id /*+ " " + tmp.type + " " + tmp.dataType*/);
					System.exit(1);						
				}//if
			}//for
			
		}//functionSet

		void scopeSet(){//inserts new BLOCK symbol table
			num(0);//inc scopeNum and scopeLvl
			arr[size++] = new neonode(new String("S"), new String("S"), null, new String("S"), scopeNum, scopeLvl);
		}//scopeSet

		void stats(){
			System.out.println(
			"scopeLvl: " + scopeLvl
			+ "\nscopeNum:" + scopeNum
			+ "\nsize: " + size
			+ "\nlength: " + length);
		}//stats

		void print(){
			//stats();
			int tmp = size;
			int tmp2 = scopeNum;
			int blockNum = 1;
			String name[] = new String[tmp];

			for(int x = 0;x <= tmp2;x++){
				boolean flag = false;
				for(int y = 0;flag == false && y < tmp; y++){
					neonode tmp1 = arr[y];
					//System.out.println("x:" + x + "y:" + y + " " + tmp1.type + " " + tmp2);
					if(tmp1.scopeNum == x){
						if(tmp1.id.equals("D")){//Function marker
							System.out.println("\nSymbol table " + tmp1.type);
							flag = true;
						}//if
						else if(tmp1.scopeNum == 0){//GLOBAL
							System.out.println("Symbol table GLOBAL");
							flag = true;
						}//else if
						else if(tmp1.scopeLvl > arr[y-1].scopeLvl){//Block marker
							System.out.println("\nSymbol table BLOCK " + blockNum++);
							flag = true;
						}//else if
					}//if

				}//for

				neonode tmp0;

				for(int y = 0;y < tmp;y++){
					tmp0 = arr[y];
					if(tmp0.scopeNum == x){
						if(!(eq(tmp0.id,"D")) && !(eq(tmp0.dataType,"D")) && !(tmp0.dataType.equals("S")) && (!tmp0.id.equals("S")) && (!tmp0.id.equals("G") && (!tmp0.id.equals("G")))){//variable
							if(tmp0.value == null)
								System.out.println("name " + tmp0.id + " type " + tmp0.dataType);
							else
								System.out.println("name " + tmp0.id + " type " + tmp0.dataType + " value " + tmp0.value);
								
						}//if

					}//if
				}//for
			}//for
		}//print

		String fixer(String prob){
			int limit = prob.length();
			String sol = new String();
			int x = 0;
			for(;x < limit && prob.charAt(x) == 0x0;x++){}//for

			for(;x < limit;x++){//loops through entire prob
				if(prob.charAt(x) != 0x0)//checks for null characters
					sol = sol.concat(prob.substring(x,x+1));//appends non-nulls to clean string
			}//for
			return sol;
		}//fixer

	}//neohash

}//symLis

