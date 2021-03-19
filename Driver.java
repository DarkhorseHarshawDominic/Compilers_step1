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
	neohash neo;
	String scope;

	symLis(){
		arr = new hashmap();
		neo = new neohash();
	}//symLis

	hashmap arr(){
		return arr;
	}//arr
	@Override
	public void enterProg(LittleParser.ProgContext ctx) {
		System.out.println("GLOBAL");
		//arr.scope("GLOBAL");
	}//enterProg
	@Override public void exitProg(LittleParser.ProgContext ctx) {
		//arr.print();
		System.out.println("GLOBAL");
		//neo.num(1);
	}//exitProg
	/*@Override public void enterId(LittleParser.IdContext ctx) { }
	@Override public void exitId(LittleParser.IdContext ctx) { }
	@Override public void enterPgm_body(LittleParser.Pgm_bodyContext ctx) { }
	@Override public void exitPgm_body(LittleParser.Pgm_bodyContext ctx) { }
	@Override public void enterDecl(LittleParser.DeclContext ctx) { }
	@Override public void exitDecl(LittleParser.DeclContext ctx) { }
	@Override public void enterString_decl(LittleParser.String_declContext ctx) {}*/
	@Override
	public void exitString_decl(LittleParser.String_declContext ctx) {
		System.out.println("STR");
		//System.out.println(/*ctx.str().getText() +*/ "name " + ctx.id().getText()/* + "\ttype STRING\tvalue " + ctx.str().getText()*/);
		//arr.insert(/*scope, */"STRING", ctx.id().getText(), ctx.str().getText());
		neo.insert("STRING", ctx.id().getText(), ctx.str().getText());
	}//exitString_decl
	/*@Override public void enterStr(LittleParser.StrContext ctx) { }
	@Override public void exitStr(LittleParser.StrContext ctx) { }
	@Override public void enterVar_decl(LittleParser.Var_declContext ctx) { }*/
	@Override
	public void exitVar_decl(LittleParser.Var_declContext ctx) {
		System.out.println("VAR");
		//System.out.println(ctx.var_type().getText() + " " + ctx.id_list().id().getText());
		//Captures only one id_tail
		//Must find out how to capture n id_tail
		//if(ctx.id_list().id_tail() != null)
			//System.out.println(ctx.var_type().getText() + " " + ctx.id_list().id_tail().id().getText());
		//System.out.println(ctx.getChildCount());
		//System.out.println(ctx.getChild(1)/*.getChild(1).getChild(2).getChild(1)*/.getText());
		/*if(ctx.id_list().id_tail() != null){
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
						System.out.println("name " + new String(buffer)/* + "\ttype " + ctx.var_type().getText());//verify
						arr.insert(ctx.var_type().getText(), new String(buffer), "");
						buffer = new char[lim-x];
						loss = 0;
					}//else
				}//if
			}//for
			buffer[loss] = ctx.getChild(1).getText().charAt(x-1);
			//System.out.println("name " + new String(buffer)/* + " type " + ctx.var_type().getText());
			arr.insert(ctx.var_type().getText(), new String(buffer), null);
		}//if
		else{//1 declaration
			//System.out.println(ctx.var_type().getText() + " " + ctx.id_list().id().getText());
		}//else	
		*/
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
	@Override public void exitParam_decl_tail(LittleParser.Param_decl_tailContext ctx) { }*/
	//@Override public void enterFunc_declarations(LittleParser.Func_declarationsContext ctx) { arr.up(ctx.func_decl().id().getText()); }
	//@Override public void exitFunc_declarations(LittleParser.Func_declarationsContext ctx) { arr.up(ctx.func_decl().id().getText());}
	//@Override public void enterFunc_decl(LittleParser.Func_declContext ctx) { arr.up(ctx.id().getText()); }
	@Override
	public void exitFunc_decl(LittleParser.Func_declContext ctx) {
		neo.num(1);
		System.out.println("FUNCTIONNAME");
		/*arr.up(ctx.id().getText());//change scope
		
		//rip&submit until it is done
		int lim = ctx.param_decl_list().getText().length();
		//System.out.println(lim);
		char []type;
		char []type0;

		int loss = 0;

		if(lim > 0){
			while(loss < lim){
				//System.out.println(lim + " " + loss);
				//System.out.println(loss < lim);
				//System.out.println(lim);

				type  = new char[lim];
				type0 = new char[lim];
				int z = 0;
				while(z != 1 && loss < lim){//first word
					char tmp = ctx.param_decl_list().getText().charAt(loss);
					type[loss] = tmp;
					loss++;
					//System.out.println(loss);
					if(tmp == 'T' || tmp == 'G'){
						z = 1;//break inner loop
						//System.out.println(z);
					}//if

				}//while

				//System.out.println("OUT");
				//System.out.println(type);

				while(z != 0 && loss < lim){//second word
					//System.out.println("IN");
					char tmp = ctx.param_decl_list().getText().charAt(loss);
					//System.out.println(tmp);
					type0[loss] = tmp;
					loss++;
					if(tmp == 'F' || tmp == 'I' || tmp == 'S')
						z = 0;//break inner loop
				}//for

				//System.out.println(type0);
				//loss++;
				//System.out.println(loss);
				arr.insert(new String(type), new String(type0), null);
			}//while
		}//if
		*/
	}//exitFunc_decl
	@Override public void enterFunc_body(LittleParser.Func_bodyContext ctx) { 
		System.out.println("FI");
		neo.num(0);
		//place dummy
	}
	@Override public void exitFunc_body(LittleParser.Func_bodyContext ctx) {
		System.out.println("FL");
		//Update dummy, and variable scopeType
		//neo.num(1);
		//arr.down();
	}//exitFunc_body
	/*@Override public void enterStmt_list(LittleParser.Stmt_listContext ctx) { }
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
		//arr.up(null);//change scope
		System.out.println("IFI");
		neo.num(0);
	}//enterIf_stmt
	@Override
	public void exitIf_stmt(LittleParser.If_stmtContext ctx) {
		System.out.println("IFL");
		neo.num(1);
		//System.out.println("IF" + ctx.decl().getText());
		//arr.down();//change scope
	}//exitIf_stmt
	@Override public void enterElse_part(LittleParser.Else_partContext ctx) {
		System.out.println("EI");
		neo.num(0);
		//arr.up(null);//change scope
	}//enterElse_part
	@Override
	public void exitElse_part(LittleParser.Else_partContext ctx) {
		System.out.println("EL");
		neo.num(1);
		//arr.down();//change scope
	}//exitElse_part
	/*@Override public void enterCond(LittleParser.CondContext ctx) { }
	@Override public void exitCond(LittleParser.CondContext ctx) { }
	@Override public void enterCompop(LittleParser.CompopContext ctx) { }
	@Override public void exitCompop(LittleParser.CompopContext ctx) { }*/
	@Override
	public void enterWhile_stmt(LittleParser.While_stmtContext ctx) {
		System.out.println("WI");
		neo.num(0);
		//arr.up(null);//change scope
	}//enterWhile_stmt
	@Override
	public void exitWhile_stmt(LittleParser.While_stmtContext ctx) {
		System.out.println("WL");
		neo.num(1);

		//arr.down();//change scope
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
			//System.out.println("insertbegin");
			node tmp = descend(this);
			tmp.setNxt(in);
			in.setPrv(tmp);
			//stats();
			//System.out.println(this.id + " " + tmp.id + " " + in.id);

			//System.out.println("insertdone");
		}//insert


		node descend(node nxt){
			//System.out.println("descending");
			if(nxt.nxt() == null)
				return nxt;
			return descend(nxt.nxt());
		}//descend

		int descend(boolean f, node nxt){//tree height
			//System.out.println("descending..");
			if(nxt == null)
				return 0;
			//System.out.println("returning");
			return descend(true, nxt.nxt()) + 1;
		}//descend

		node ascend(node prv){
			if(prv == null)
				return this;
			return ascend(prv.prv());
		}//ascend

		void stats(){
			System.out.println("scopeName " + scopeName + "\ntype " + type + "\nid " + id);
			if(value != null)
			System.out.println("value " + value);
		}//stats
	}//node

	class hashmap{
		private node[] arr;
		private int length;
		private int size;
		private int scopeCount;
		private String currScope;
		private int blockCount;
		private int scopeLevel;

		hashmap(){
			length = 4;
			size = 0;
			scopeCount = 0;
			arr = new node[4];
			blockCount = 1;
			scopeLevel = 0;
			//for(int x = 0;x < length;x++)
				//arr[x] = new node();
		}//hashmap

		hashmap(int newLength){
			length = newLength;
			scopeCount = 0;
			//for(int x = 0;x < length;x++)
				//arr[x] = new node();
			arr = new node[newLength];
		}//hashmap

		int F(){//hash function
			return scopeCount;
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
			return scopeCount >= length-1;
		}//L

		void insert(/*String newScopeName, */String newType, String newId, String newValue){//flag indicates new scope
			int dst = F();
			if(size == 0){//best case
				//System.out.println("inserting0");
				arr[dst] = new node(currScope/*newScopeName*/, newType, newId, newValue);
			}//if
			else{
				if(search(newId, newId) == true){//duplicate found;end compilation
					System.out.println("DECLARATION ERROR " + newId);
					System.exit(1);
				}//if

				if(arr[dst] != null)
					arr[dst].insert(new node(currScope/*newScopeName*/, newType, newId, newValue));
				else
					arr[dst] = new node(currScope/*newScopeName*/, newType, newId, newValue);
				//System.out.println("CONNECTING" + dst);
				//if(F() != 0 && arr[dst].descend(true,arr[dst].nxt()) == 0)//0 == GLOBAL SCOPE and empty tree at arr[dst]
					//arr[dst].setPrv(arr[F()-1]);//Connect to preceding scope
			}//else
			size++;//inc size
			grow();
			//stats();
			//print();
			//for(int a = 0; a < size; a++)
				//System.out.println(arr[a].scopeName);
		}//insert

		void up(String newScope){
			scopeCount++;
			if(newScope == null)
				newScope = new String("BLOCK" + blockCount++);
			scope(newScope);//new scope name
			System.out.println("UPscopeCount " + scopeCount + "currScope " + currScope);
			grow();
		}//up

		void down(){
			//scopeCount--;
			//System.out.println(arr[0].scopeName);
			//System.out.println(scopeCount);
			//while(arr[--scopeCount].scopeName == null)
				//currScope = new String("BLOCK" + --blockCount);
			//if(arr[--scopeCount] == null && scopeCount > 0){
				//currScope = new String("BLOCK" + scopeCount);
			//System.out.println(arr[0].scopeName);
				//System.out.println(arr[scopeCount] == null && scopeCount > 0);
				//System.out.println(scopeCount);
			try{
				while(arr[scopeCount] == null && scopeCount > 0){
					//System.out.println("WHILE");
					arr[scopeCount--] = new node(currScope, "DUMMY", "DUMMY", "DUMMY");
					//System.out.println(scopeCount--);
				}//while
					currScope = arr[scopeCount].scopeName;
					//System.out.println(currScope);
			}catch(NullPointerException e){
				System.out.println("No");
			}

			//}//if
			//if(scopeCount > 0){
				//scope(arr[scopeCount].scopeName);//previous scope name
				//System.out.println("DOscopeCount " + scopeCount + "currScope " + currScope);
			/*}//if
			else
				currScope = "GLOBAL";*/
			System.out.println("DOscopeCount " + scopeCount + "currScope " + currScope);
		}//down

		void grow(){
			if(L() == true){
				//System.out.println("GROW");
				node[] newArr = new node[length*2];
				//int y = F();
				for(int x = 0;x <= scopeCount; x++)//fill new array with old elements
					newArr[x] = arr[x];
				arr = newArr;
				length = length*2;
				//stats();
			}//if
		}//grow

		void stats(){
			System.out.println("length " + length + "\nsize " + size + "\nscopeCount " + scopeCount + "\ncurrScope " + currScope);
			print();
		}//stats

		void print(){
			//System.out.println("PRINTHERE");
			for(int x = 0; x <= scopeCount; x++){
				//System.out.println(x);
				//if(arr[x] == null)
					//System.out.println("shit");
				System.out.println("\nSymbol Table " + arr[x].scopeName);
				print(arr[x]);
			}//for
		}//print

		private void print(node N){
			if(N != null){
				if(N.type.equals("DUMMY"))
					return;
				if(N.type.equals("INT") || N.type.equals("FLOAT") || N.type.equals("FUNCTION"))
					System.out.println("name " + N.id + " type " + N.type);
				else if(N.type.equals("STRING"))
					System.out.println("name " + N.id + " type " + N.type + " value " + N.value);
				print(N.nxt());
			}//if
			//else
				//System.out.println("\n");
		}//print


		boolean search(String id, String type){
			int x = F();
			if(x == 0)//GLOBAL SCOPE
				return searchR(arr[x], id, type);

			//int dst = F();
			boolean L,R;
			L = R = false;
			//System.out.println("Searching");
			/*if(arr[dst] != null){
				L = searchL(arr[dst].prv(), id);
				R = searchR(arr[dst].nxt(), id);
			}if*/
			//System.out.println(searchL(arr[x], id) || searchR(arr[x], id));
				return searchL(arr[x], id, type) || searchR(arr[x], id, type);
		}//search

		boolean searchL(node left, String id, String type){
			//System.out.println("LEFT");
			if(left != null){
				int x = 0;
				return searchR(arr[F()-++x], id, type);
			}//else if
			return false;
		}//searchL

		boolean searchR(node right, String id, String type){
			//System.out.println("RIGHT");
			if(right != null){
				if(right.type.equals(type)&& right.id.equals(id))//duplicate found
					return true;
				return searchR(right.nxt(), id, type);
			}//if
			//System.out.println("RIGHTO");
			return false;
		}//searchR

		String scope(String scope){
			currScope = scope;
			return scope;
		}//scope

	}//hashmap
	
	class neonode{
		String type;//node ID
		String dataType;
		String id;
		String value;
		int scopeNum;

		neonode(String newType, String newId, String newValue, String newDataType, int newScopeNum){
			type = newType;
			dataType = newDataType;
			id = newId;
			value = newValue;
			scopeNum = newScopeNum;
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
			arr = new neonode[2];
			size = 0;
			length = 2;
		}//neohash

		void insert(String dataType, String id, String value){//level provided by neohash

			if(size == 0){//BEST CASE EMPTY
				arr[0] = new neonode("GLOBAL", id, value, dataType, 0);//insert
			}//if
			else{//AVERAGE CASE
				++size;
				if(!(dataType.equals("DUMMY"))){
					if(search(id, dataType)){//find duplicates
						System.out.println("DECLARATION ERROR " + id);
						System.exit(1);
					}//if
					/*int tmp;//stores intended scopeLvl
					for(int x = size - 1;,boolean flag = false;x >= 0 && flag == false;x--)//ignore dummys and obtains scope type
						if((arr[x].type.equals("DUMMY"))){
							flag = true;
							tmp = arr[x].lvl;
							//x += 1;//hold on to that feeling
						}//if*/
					arr[size] = new neonode(null, id, value, dataType, scopeNum);//no scopeType node
				}//if
				else{//insert dummy marker;updated when function listener occurs
					arr[size] = new neonode("DUMMY", "DUMMY", null, "DUMMY", scopeNum);
				}//else
						
				//arr[size] = new neonode(arr[x].type, id, value, dataType, scopeNum);//insert
			}//else

			grow();//check for fill
			stats();
		}//insert

		boolean search(String id, String dataType){
			for(int x = size-1;x >= 0;x--){
				neonode tmp = arr[x];
				if(tmp.type){
					if(tmp.id.equals(id) && tmp.dataType.equals(dataType));
						return true;
				}
			}//for
			return false;
		}//search

		boolean L(){return size >= length;}//L

		void grow(){
			if(L()){
				int tmpLength = length*2;
				neonode tmp[] = new neonode[tmpLength];
				for(int x = 0; x <= length; x++)
					tmp[x] = arr[x];
				length = tmpLength;
			}//if
		}//grow

		void num(int choice){//up || down
			if(choice == 0){
				System.out.println("scopeLvl: " + ++scopeLvl);
				System.out.println("scopeNum: " + ++scopeNum);
			}//if
			else{
				System.out.println("scopeLvl: " + --scopeLvl);
			}//else
		}//lvl

		void stats(){
			System.out.println(
			"scopeLvl: " + scopeLvl
			+ "\nscopeNum:" + scopeNum
			+ "\nsize: " + size
			+ "\nlength: " + length);
		}//stats

		void print(){
			int tmp = size;
			for(int x = 0;x <= tmp;x++){
				if(arr[x].type.equals("FUNCTION")){//FUNCTION logic
					String tmp0;
					for(int z = x-1;z >= 0;z--){
						if(arr[z].type.equals("DUMMY")){// DUMMY found
							tmp0 = arr[z].id;//Grab function name
						}//if
					}//for
				}//if
			}//for
		}//print

	}//neohash
}//symLis

