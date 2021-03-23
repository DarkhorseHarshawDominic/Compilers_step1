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

	symLis(){
		neo = new neohash();
	}//symLis

	@Override
	public void enterProg(LittleParser.ProgContext ctx) {
		//System.out.println("GLOBAL");
	}//enterProg
	@Override public void exitProg(LittleParser.ProgContext ctx) {
		//System.out.println("GLOBAL");
		neo.print();
		//neo.num(1);
	}//exitProg
	@Override
	public void exitString_decl(LittleParser.String_declContext ctx) {
		neo.insert(new String("STRING"), ctx.id().getText(), ctx.str().getText());
	}//exitString_decl
	@Override
	public void exitVar_decl(LittleParser.Var_declContext ctx) {
		//System.out.println("VAR");

		if(ctx.id_list().id_tail().getText().length() > 0){
			//System.out.println("WITNESS ME " + ctx.id_list().id_tail().getText());
			//System.out.println("TELL ME " + ctx.id_list().id_tail().getText().length());

			int lim = ctx.id_list().id_tail().getText().length();
			char buffer[] = new char[lim];
			int loss = 0;

			buffer[0] = ctx.getChild(1).getText().charAt(0);
			char tmp;
			int x = 1;

			//rip&submit until it is done.
			//no commas allowed
			for(; x < lim; x++){
				if(x < lim){//get next char
						tmp = ctx.getChild(1).getText().charAt(x);
					if(tmp != ','){
						buffer[loss] = ctx.getChild(1).getText().charAt(x);
						loss++;
					}//if
					else{
						//System.out.println("name " + new String(buffer) + "\ttype " + ctx.var_type().getText());//verify
						//arr.insert(ctx.var_type().getText(), new String(buffer), "");
						neo.insert(ctx.var_type().getText(), new String(buffer), null);
						buffer = new char[lim-x];
						loss = 0;
					}//else
				}//if
			}//for
			buffer[loss] = ctx.getChild(1).getText().charAt(x);
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
	}//exitFunc_decl
	@Override public void enterFunc_body(LittleParser.Func_bodyContext ctx) { 
		//System.out.println("FI");
		neo.num(0);
		neo.insert("DUMMY", "DUMMY", null);
		//place dummy
	}
	@Override public void exitFunc_body(LittleParser.Func_bodyContext ctx) {
		//System.out.println("FL");
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
		//System.out.println("IFI");
		neo.num(0);
	}//enterIf_stmt
	@Override
	public void exitIf_stmt(LittleParser.If_stmtContext ctx) {
		//System.out.println("IFL");
		neo.num(1);
		//arr.down();//change scope
	}//exitIf_stmt
	@Override public void enterElse_part(LittleParser.Else_partContext ctx) {
		//System.out.println("ELI");
		neo.num(0);
		//arr.up(null);//change scope
	}//enterElse_part
	@Override
	public void exitElse_part(LittleParser.Else_partContext ctx) {
		//System.out.println("ELL");
		neo.num(1);
		//arr.down();//change scope
	}//exitElse_part
	/*@Override public void enterCond(LittleParser.CondContext ctx) { }
	@Override public void exitCond(LittleParser.CondContext ctx) { }
	@Override public void enterCompop(LittleParser.CompopContext ctx) { }
	@Override public void exitCompop(LittleParser.CompopContext ctx) { }*/
	@Override
	public void enterWhile_stmt(LittleParser.While_stmtContext ctx) {
		//System.out.println("WI");
		neo.num(0);
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
			//System.out.println("INSERTING + " + id + " " + dataType);
			dataType = dataType.replaceAll("[^a-zA-Z0-9]","");
			id = id.replaceAll("^a-zA-Z0-9","");
			if(size == 0){//BEST CASE EMPTY
				arr[0] = new neonode();
				arr[0] = new neonode(new String("GLOBAL"), id, value, dataType, 0, 0);//insert
				size++;
			}//if
			else{//AVERAGE CASE
				if(!(eq(dataType,"DUMMY"))){
					if(search(id, dataType)){//find duplicates
						System.out.println("DECLARATION ERROR " + id);
						System.exit(1);
					}//if
					arr[size] = new neonode();
					arr[size++] = new neonode(arr[size-1].type, id, value, dataType, scopeNum, scopeLvl);//Even if DUMMY is adopted, functionSet method will check id and dataType for DUMMY identifiers
				}//if
				else{//insert dummy marker;updated when function listener occurs
					arr[size++] = new neonode("DUMMY", "DUMMY", null, "DUMMY", scopeNum, scopeLvl);
				}//else
						
			}//else

			//System.out.println("FINISHED" + arr[size-1].type);
			if(scopeLvl == 0)
				arr[size-1].type = new String("GLOBAL");
			grow();//check for fill
			//stats();
		}//insert

		boolean search(String id, String dataType){
				neonode tmp;
				//System.out.println("searching for id " + id + "dataType " + dataType);
				boolean flag = false;
				boolean flag0 = false;
			for(int x = size-1;x != -1 && flag == false;x--){
				//System.out.println("searching at " + x);
					tmp = arr[x];
					//System.out.println(tmp.id + " " + tmp.scopeLvl + " "  + tmp.dataType + " " + scopeLvl);
					//System.out.println(id + " " + dataType);
					//System.out.println(eq(tmp.id,id) && eq(tmp.dataType,dataType));
					if(tmp.scopeLvl < scopeLvl){
						flag = true;
					}//if
					else if(tmp.scopeLvl == scopeLvl && eq(tmp.id,id) && eq(tmp.dataType,dataType)){
						//System.out.println("Found at " + x +  " tmpid " + tmp.id + " dT " + tmp.dataType);
						flag0 = true;
						flag = true;
					}//else if
			}//while
			if(flag0 == true)
				return true;
			return false;
		}//search

		boolean eq(String a, String b){
			/*if(a == null){
				System.out.println("a is null b is " + b);
			}//if
			else if(b == null)
				System.out.println("b is null");
			*/
			a = a.replaceAll("[^a-zA-Z0-9]","");
			b = b.replaceAll("[^a-zA-Z0-9]","");
			int aa = a.length();
			int bb = b.length();
			//System.out.println(a + " " + b + " " + aa + " " + bb);
			//System.out.println("new a length " + a.length() + "new b length " + b.length());
			boolean flag = false;
			boolean flag0= true;
			int lim;
			if(aa < bb)
				lim = aa;
			else
				lim = bb;
			if(aa == bb){
				for(int x = 0;x < lim && flag == false;x++){
					if(a.charAt(x) != b.charAt(x)){
						flag = true;
						flag0= false;
					}//if
				}//for
			}//if
			else
				flag0 = false;

			if(flag0 == true)
				return true;
			return false;
		}//eq

		boolean L(){return size >= length/2;}//L

		void grow(){
			if(L()){
				//System.out.println("GROW");
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
				//System.out.println(x + " " + size);
				//System.out.println("moving " + arr[x].id);
				arr[x+1] = new neonode();
				arr[x+1] = arr[x];
				//System.out.println(arr[x].id);
			}//for
		}//

		void functionSet(String id, String dataType, String paramlist){//Completes function scope by promoting dummy markers;
			boolean flag = false;
			int x;
			for(x = size-1;x >= 0 && flag == false;x--){
				neonode tmp = arr[x];
				//System.out.println(tmp.id + " " + tmp.dataType);
				if(!(eq(tmp.id,"DUMMY")) && !(eq(tmp.dataType,"DUMMY")))//variable detected
					arr[x].type = id;
				else if(eq(tmp.type,"DUMMY")){//DUMMY detected;avoids BLOCKX dummies
					//System.out.println();
					arr[x].type = id;//promote dummy to function id scope
					arr[x].dataType = dataType;//save function's return type;use unknown
					flag = true;
				}//else if
			}//for

			//shuffle(x+1);

			if(paramlist != null){
				String tmp0 = paramlist;//insert Function parameters between declaration and exisintg declarations
				int lim = tmp0.length();
				//rip&submit until it is done
				//System.out.println("LIM " + lim);
				char []type;
				char []type0;
				int loss = 0;

				if(lim > 0){
					while(loss < lim){
						//System.out.println(loss + " " + lim + " " + loss < lim);

						type  = new char[lim];
						type0 = new char[lim];
						int z = 0;

						while(z != 1 && loss < lim){//first word
							String tmp1 = tmp0;
							char tmp = tmp1.charAt(loss);
							type[loss] = tmp;
							loss++;
							//System.out.println(loss);
							
							if(tmp == 'T' || tmp == 'G'){
								z = 1;//break inner loop
								//System.out.println(type);
							}//if

						}//while

							//System.out.println(type);

						while(z != 0 && loss < lim){//second word
							//System.out.println("IN");
							char tmp = tmp0.charAt(loss);
							//System.out.println(tmp);
							type0[loss] = tmp;
							loss++;

							if(tmp == 'F' || tmp == 'I' || tmp == 'S'){
								z = 0;//break inner loop
								//System.out.println(type0);
							}//if
						}//while

						//System.out.println("loss " + loss);
						//System.out.println(type0);
						if((search(new String(type), new String(type0)))){
							System.out.println("DECLARATION ERROR " + id);
							System.exit(1);
						}//if

						shuffle(x+2);//make room for insert
						//arr[x+1] = new neonode();
						//System.out.println("scopeNum " + arr[x+1].scopeNum);
						//System.out.println("inserting " + new String(type0) + " at " + (x+2));
						//System.out.println("ID " + arr[x+2].id);
						size++;//update size
						arr[x+2] = new neonode(id, new String(type0), null, new String(type), arr[x+1].scopeNum, 1);
						//neo.insert(new String(type), new String(type0), null);
						if(loss >= lim)
							return;
					}//while

				}//if
			}//if
			
			flag = false;
			for(x-= 1;x >= 0 && flag == false; x--){//locate duplicate declarations
				neonode tmp = arr[x];
				//System.out.println(x);
				//System.out.println("tmp type " + tmp.type + "tmpid " + tmp.id);
				if(eq(tmp.type,id) && eq(tmp.dataType,dataType)){//Functions with dupe names and types not accepted
					System.out.println("DECLARATION ERROR " + id /*+ " " + tmp.type + " " + tmp.dataType*/);
					System.exit(1);						
				}//if
			}//for
			
		}//functionSet

		void stats(){
			System.out.println(
			"scopeLvl: " + scopeLvl
			+ "\nscopeNum:" + scopeNum
			+ "\nsize: " + size
			+ "\nlength: " + length);
		}//stats

		void print(){
			//System.out.println("PRINTING");
			//stats();
			int tmp = size;
			int tmp2 = scopeNum;
			int blockNum = 1;
			for(int x = 0;x <= tmp2;x++){
				boolean flag = false;
				for(int y = 0;flag == false && y <= tmp; y++){
					neonode tmp1 = arr[y];
					if(tmp1.scopeNum == x){
						if(tmp1.id.equals("DUMMY")){//Function marker
							System.out.println("Symbol table " + tmp1.type);
							flag = true;
						}//if
						else if(tmp1.scopeLvl > 1){//Block marker
							System.out.println("Symbol table " + "BlOCK" + blockNum++);
							flag = true;
						}//else if
						else{//GLOBAL
							System.out.println("Symbol table GLOBAL");
							flag = true;
						}//else
					}//if
				}//for

				neonode tmp0;

				for(int y = 0;y < tmp;y++){
					tmp0 = arr[y];
					//System.out.println(y + tmp0.id + tmp0.scopeNum);
					//System.out.println(!(eq(tmp0.dataType,"DUMMY")) && !(eq(tmp0.id,"DUMMY")));
					if(tmp0.scopeNum == x){
						if(!(eq(tmp0.id,"DUMMY")) && !(eq(tmp0.dataType,"DUMMY"))){//variable
							if(tmp0.value == null)
								System.out.println("name " + tmp0.id + " type " + tmp0.dataType);
							else
								System.out.println("name " + tmp0.id + " type " + tmp0.type + " value " + tmp0.value);
								
						}//if

					}//if
				}//for
			}//for
		}//print

	}//neohash

}//symLis

