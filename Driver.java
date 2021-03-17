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
		System.out.println("Accepted");
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
	hashmap arr;
	String scope;

	symLis(){
		arr = new hashmap();
	}//symLis
	@Override
	public void enterProg(LittleParser.ProgContext ctx) {
		System.out.println("Symbol table GLOBAL");
		scope = "GLOBAL";
	}//enterProg
	/*@Override public void exitProg(LittleParser.ProgContext ctx) { }
	@Override public void enterId(LittleParser.IdContext ctx) { }
	@Override public void exitId(LittleParser.IdContext ctx) { }
	@Override public void enterPgm_body(LittleParser.Pgm_bodyContext ctx) { }
	@Override public void exitPgm_body(LittleParser.Pgm_bodyContext ctx) { }
	@Override public void enterDecl(LittleParser.DeclContext ctx) { }
	@Override public void exitDecl(LittleParser.DeclContext ctx) { }
	@Override public void enterString_decl(LittleParser.String_declContext ctx) {}*/
	@Override
	public void exitString_decl(LittleParser.String_declContext ctx) {
		System.out.println(/*ctx.str().getText() +*/ "name " + ctx.id().getText() + "\ttype STRING\tvalue " + ctx.str().getText());
		arr.insert(scope, "STRING", ctx.id().getText(), ctx.str().getText());
	}//exitString_decl
	/*@Override public void enterStr(LittleParser.StrContext ctx) { }
	@Override public void exitStr(LittleParser.StrContext ctx) { }
	@Override public void enterVar_decl(LittleParser.Var_declContext ctx) { }*/
	@Override
	public void exitVar_decl(LittleParser.Var_declContext ctx) {
		//System.out.println(ctx.var_type().getText() + " " + ctx.id_list().id().getText());
		//Captures only one id_tail
		//Must find out how to capture n id_tail
		//if(ctx.id_list().id_tail() != null)
			//System.out.println(ctx.var_type().getText() + " " + ctx.id_list().id_tail().id().getText());
		//System.out.println(ctx.getChildCount());
		//System.out.println(ctx.getChild(1)/*.getChild(1).getChild(2).getChild(1)*/.getText());
		if(ctx.id_list().id_tail() != null){
			int lim = ctx.getChild(1).getText().length();
			char buffer[] = new char[lim];
			int loss = 0;

			buffer[0] = ctx.getChild(1).getText().charAt(0);
			char tmp;
			int x = 1;
			//rip&submit until it is done.
			//no commas allowed
			for(; x < lim; x++){
				if(x < lim-1){//get next char
						tmp = ctx.getChild(1).getText().charAt(x);
					if(tmp != ','){
						buffer[loss] = ctx.getChild(1).getText().charAt(x);
						loss++;
					}//if
					else{
						System.out.println("name " + new String(buffer) + "\ttype " + ctx.var_type().getText());//verify
						buffer = new char[lim-x];
						loss = 0;
					}//else
				}//if
			}//for
			buffer[loss] = ctx.getChild(1).getText().charAt(x-1);
			System.out.println("name " + new String(buffer) + "\ttype " + ctx.var_type().getText());
		}//if
		else{//1 declaration
			System.out.println(ctx.var_type().getText() + " " + ctx.id_list().id().getText());
		}//else		
	}//Var_decl
	/*@Override public void enterVar_type(LittleParser.Var_typeContext ctx) { }
	@Override public void exitVar_type(LittleParser.Var_typeContext ctx) { }
	@Override public void enterAny_type(LittleParser.Any_typeContext ctx) { }
	@Override public void exitAny_type(LittleParser.Any_typeContext ctx) { }
	@Override public void enterId_list(LittleParser.Id_listContext ctx) { }
	@Override public void exitId_list(LittleParser.Id_listContext ctx) { }
	@Override public void enterId_tail(LittleParser.Id_tailContext ctx) { }
	@Override public void exitId_tail(LittleParser.Id_tailContext ctx) {
	}//exitId_tail
	@Override public void enterParam_decl_list(LittleParser.Param_decl_listContext ctx) { }
	@Override public void exitParam_decl_list(LittleParser.Param_decl_listContext ctx) { }
	@Override public void enterParam_decl(LittleParser.Param_declContext ctx) { }
	@Override public void exitParam_decl(LittleParser.Param_declContext ctx) { }
	@Override public void enterParam_decl_tail(LittleParser.Param_decl_tailContext ctx) { }
	@Override public void exitParam_decl_tail(LittleParser.Param_decl_tailContext ctx) { }
	@Override public void enterFunc_declarations(LittleParser.Func_declarationsContext ctx) { }
	@Override public void exitFunc_declarations(LittleParser.Func_declarationsContext ctx) { }
	@Override public void enterFunc_decl(LittleParser.Func_declContext ctx) { }*/
	@Override
	public void exitFunc_decl(LittleParser.Func_declContext ctx) {
		System.out.println("name " + ctx.id().getText() + "\ttype " + ctx.any_type().getText() + "\tparam " + ctx.param_decl_list().getText());
		arr.up();//change scope
		arr.insert(ctx.id().getText(), "FUNCTION", ctx.id().getText(), ctx.param_decl_list().getText());
	}//exitFunc_decl
	/*@Override public void enterFunc_body(LittleParser.Func_bodyContext ctx) { }
	@Override public void exitFunc_body(LittleParser.Func_bodyContext ctx) { }
	@Override public void enterStmt_list(LittleParser.Stmt_listContext ctx) { }
	@Override public void exitStmt_list(LittleParser.Stmt_listContext ctx) { }
	@Override public void enterStmt(LittleParser.StmtContext ctx) { }
	@Override public void exitStmt(LittleParser.StmtContext ctx) { }
	@Override public void enterBase_stmt(LittleParser.Base_stmtContext ctx) { }
	@Override public void exitBase_stmt(LittleParser.Base_stmtContext ctx) { }
	@Override public void enterAssign_stmt(LittleParser.Assign_stmtContext ctx) { }
	@Override public void exitAssign_stmt(LittleParser.Assign_stmtContext ctx) { }
	@Override public void enterAssign_expr(LittleParser.Assign_exprContext ctx) { }
	@Override public void exitAssign_expr(LittleParser.Assign_exprContext ctx) { }
	@Override public void enterRead_stmt(LittleParser.Read_stmtContext ctx) { }
	@Override public void exitRead_stmt(LittleParser.Read_stmtContext ctx) { }
	@Override public void enterWrite_stmt(LittleParser.Write_stmtContext ctx) { }
	@Override public void exitWrite_stmt(LittleParser.Write_stmtContext ctx) { }
	@Override public void enterReturn_stmt(LittleParser.Return_stmtContext ctx) { }
	@Override public void exitReturn_stmt(LittleParser.Return_stmtContext ctx) { }
	@Override public void enterExpr(LittleParser.ExprContext ctx) { }
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
		arr.up();//change scope
	}//enterIf_stmt
	@Override
	public void exitIf_stmt(LittleParser.If_stmtContext ctx) {
		arr.down();//change scope
	}//exitIf_stmt
	@Override public void enterElse_part(LittleParser.Else_partContext ctx) {
		arr.up();//change scope
	}//enterElse_part
	@Override
	public void exitElse_part(LittleParser.Else_partContext ctx) {
		arr.down();//change scope
	}//exitElse_part
	/*@Override public void enterCond(LittleParser.CondContext ctx) { }
	@Override public void exitCond(LittleParser.CondContext ctx) { }
	@Override public void enterCompop(LittleParser.CompopContext ctx) { }
	@Override public void exitCompop(LittleParser.CompopContext ctx) { }*/
	@Override
	public void enterWhile_stmt(LittleParser.While_stmtContext ctx) {
		arr.up();//change scope
	}//enterWhile_stmt
	@Override
	public void exitWhile_stmt(LittleParser.While_stmtContext ctx) {
		arr.down();//change scope
	}//exitWhile_stmt
	/*@Override public void enterEveryRule(ParserRuleContext ctx) { }
	@Override public void exitEveryRule(ParserRuleContext ctx) { }
	@Override public void visitTerminal(TerminalNode node) { }
	@Override public void visitErrorNode(ErrorNode node) { }*/

	class node{
		//private int scope;//scope level
		String scopeName;//key for scope level classification
		String type;
		String id;
		String value;//not in-use for int, float declarations
		private node next;
		private node prev;

		node(){	}//node

		node(/*int newScope, */String newScopeName, String newType, String newId, String newValue){
			//scope = newScopeName;		
			scopeName = newScopeName;
			type = newType;
			id = newId;
			value = newValue;
		}//node

		void setNxt(node nxt){
			next = nxt;
		}//setNext

		node nxt(){
			return next;
		}//next

		void setPrv(node prv){
			prev = prv;
		}//setPrev

		node prv(){
			return prev;
		}//ascend

		void insert(node in){
			System.out.println("insertbegin");
			node tmp = descend(next);
			tmp.setNxt(in);
			if(tmp != null)
				in.setPrv(tmp);
			System.out.println("insertdone");
		}//insert


		node descend(node nxt){
			System.out.println("descending");
			if(nxt == null)
				return this;
			return descend(nxt.nxt());
		}//descend

		int descend(boolean f, node nxt){//tree height
			System.out.println("descending..");
			if(nxt == null)
				return 0;
			System.out.println("returning");
			return descend(true, nxt.nxt()) + 1;
		}//descend

		node ascend(node prv){
			if(prv == null)
				return this;
			return ascend(prv.prv());
		}//ascend
	}//node

	/*class table{
		private node head;
		private int size;
		private int scopeCount;

		table(node newHead){
			head = newHead;
			size = 0;
			scopeCount = 0;
		}//table

		insert(String newScopeName, String newType, String newId, String newValue){
			head.insert(String newScopeName, String newType, String newId, String newValue);
		}//insert

		insert(String newType, String newId, String newValue){
			head.insert(newType, newId, newValue);
		}//insert
	}table*/

	class hashmap{
		private node[] arr;
		private int length;
		private int size;
		private int scopeCount;

		hashmap(){
			length = 2;
			size = 0;
			scopeCount = 0;
			arr = new node[2];
			//for(int x = 0;x < length;x++)
				//arr[x] = new node();
		}//hashmap

		hashmap(int newLength){
			length = newLength;
			size = 0;
			scopeCount = 0;
			//for(int x = 0;x < length;x++)
				//arr[x] = new node();
			arr = new node[newLength];
		}//hashmap

		int F(){//hash function
			return size/*sym.charAt(0) % length*/;
		}//F

		int P(int lvl){//probe function
			return lvl+1;
		}//P

		int adjust(int src){//prevents out-of-bounds
			if(src >= length)
				return src % length;
			return src;
		}//adjust

		boolean L(){//Load function
			return F()/*scopeCount*/ >= length-1;
		}//L

		void insert(String newScopeName, String newType, String newId, String newValue){//flag indicates new scope
			int dst = F();
			if(size == 0){//best case
				System.out.println("inserting0");
				arr[dst] = new node(newScopeName, newType, newId, newValue);
				//arr[dst].insert(new node(newScopeName, newType, newId, newValue));
			}//if
			else{
				if(search(newId) == true){//duplicate found;end compilation
					System.out.println("DECLARATION ERROR <" + newId + ">");
					System.exit(1);
				}//if

				/*int x = 1;
				while(arr[dst] != null ){//occupied

					dst = dst + P(x++);
					dst = adjust(dst);
				}while*/
				if(arr[dst] != null)
					arr[dst].insert(new node(newScopeName, newType, newId, newValue));
				else
					arr[dst] = new node(newScopeName, newType, newId, newValue);
				System.out.println("CONNECTING" + dst);
				if(F() != 0 && arr[dst].descend(true,arr[dst].nxt()) == 0)//0 == GLOBAL SCOPE and empty tree at arr[dst]
					arr[dst].setPrv(arr[F()-1]);//Connect to preceding scope
			}//else
			size++;//inc size
			grow();
		}//insert

		void up(){
			scopeCount++;
			System.out.println("scopeCount " + scopeCount);
			grow();
		}//escalate

		void down(){
			scopeCount--;
			System.out.println("scopeCount " + scopeCount);
			grow();
		}//deescalate

		void grow(){
			if(L() == true){
				System.out.println("GROW");
				node[] newArr = new node[length*2];
				int y = F();
				for(int x = 0;x < y; x++)//fill new array with old elements
					arr[x] = arr[x];
				arr = newArr;
				length = length*2;
				stats();
			}//if
		}//grow

		void stats(){
			System.out.println("length " + length + "\nsize " + size + "\nscopeCount " + scopeCount);
			print();
		}//stats

		void print(){
			for(int x = 0; x <= scopeCount; x++)
				print(arr[x]);
		}//print

		void print(node N){
			if(N.nxt() == null){
				if(N.type.equals("INT") || N.type.equals("FLOAT"))
					System.out.println("name <" + N.id + ">\ttype <" + N.type + ">");
				else
					System.out.println("name <" + N.id + ">\ttype <" + N.type + ">\tvalue " + N.value);
			}//if
			else
				print(N.nxt());
		}//print


		boolean search(String id){
			int x = F();
			if(x == 0)//GLOBAL SCOPE
				return searchR(arr[x], id);

			//int dst = F();
			boolean L,R;
			L = R = false;
			System.out.println("Searching");
			/*if(arr[dst] != null){
				L = searchL(arr[dst].prv(), id);
				R = searchR(arr[dst].nxt(), id);
			}if*/
			System.out.println(searchL(arr[x], id) || searchR(arr[x], id));
				return searchL(arr[x], id) || searchR(arr[x], id);
		}//search

		boolean searchL(node left, String id){
			System.out.println("LEFT");
			if(left != null){
				int x = 0;
				return searchR(arr[F()-++x], id);
			}//else if
			return false;
		}//searchL

		boolean searchR(node right, String id){
			System.out.println("RIGHT");
			if(right != null){
				if(right.id.equals(id))//duplicate found
					return true;
				return searchR(right.nxt(), id);
			}//if
			return false;
		}//searchR

	}//hashmap
}//symLis

