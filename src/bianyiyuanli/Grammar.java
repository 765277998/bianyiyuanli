package bianyiyuanli;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class Grammar {
	public static void main(String[] args) {
		Grammar grammar = new Grammar();
		grammar.predictiveParsing();
	}
	//根据课本93页编写预测分析程序
	//需要一个预测分析表//String[][] predictiveTable=new String[33][45];
	HashMap<String, HashMap<String,ArrayList<String>>> predictiveTable=new HashMap<>();
	
	public void createTable() {
//		ArrayList<String>[] arrayList = new ArrayList[10];
//		HashMap<String, ArrayList<String>> hashMap1= new HashMap<>();
//		arrayList[0].add("<头文件>");
//		arrayList[0].add("<主要说明>");
//		hashMap1.put("char", arrayList1);
//		predictiveTable.put("<程序>",hashMap1);
//		System.out.println(predictiveTable.get("<程序>").get("1"));
		ArrayList<String>[] arrayLists = new ArrayList[200];
		HashMap<String,ArrayList<String>>[] hashMap = new HashMap[34];
		//<程序>	::= <头文件><主要声明>		{#,类型,void,结束符}
		arrayLists[0]=new ArrayList<>();
		arrayLists[0].add("<头文件>");
		arrayLists[0].add("<主要声明>");
		hashMap[0] = new HashMap<>();
		hashMap[0].put(tokentype.POUND.toString(), arrayLists[0]);
		hashMap[0].put(tokentype.INT.toString(), arrayLists[0]);
		hashMap[0].put(tokentype.CHAR.toString(), arrayLists[0]);
		hashMap[0].put(tokentype.FLOAT.toString(), arrayLists[0]);
		hashMap[0].put(tokentype.DOUBLE.toString(), arrayLists[0]);
		hashMap[0].put(tokentype.VOID.toString(), arrayLists[0]);
		hashMap[0].put(tokentype.ENDFILE.toString(), arrayLists[0]);
		predictiveTable.put("<程序>", hashMap[0]);
		//<头文件>	::= #include'<'标识符.h'>'<头文件>  {#}
		arrayLists[1]=new ArrayList<>();
		arrayLists[1].add(tokentype.POUND.toString());
		arrayLists[1].add(tokentype.INCLUDE.toString());
		arrayLists[1].add(tokentype.LT.toString());
		arrayLists[1].add(tokentype.ID.toString());
		arrayLists[1].add(tokentype.POINT.toString());
		arrayLists[1].add(tokentype.ID.toString());
		arrayLists[1].add(tokentype.BG.toString());
		arrayLists[1].add("<头文件>");
		hashMap[1] = new HashMap<>();
		hashMap[1].put(tokentype.POUND.toString(), arrayLists[1]);
		//|<>	  {类型,void}
		arrayLists[2]=new ArrayList<>();
		arrayLists[2].add("");
		hashMap[1].put(tokentype.INT.toString(), arrayLists[2]);
		hashMap[1].put(tokentype.CHAR.toString(), arrayLists[2]);
		hashMap[1].put(tokentype.FLOAT.toString(), arrayLists[2]);
		hashMap[1].put(tokentype.DOUBLE.toString(), arrayLists[2]);
		hashMap[1].put(tokentype.VOID.toString(), arrayLists[2]);
		predictiveTable.put("<头文件>", hashMap[1]);
		//<类型> ::= int<类型>` {int}
		arrayLists[3]=new ArrayList<>();
		arrayLists[3].add(tokentype.INT.toString());
		arrayLists[3].add("<类型>`");
		hashMap[2]=new HashMap<>();
		hashMap[2].put(tokentype.INT.toString(), arrayLists[3]);
		//|char<类型>`		{char}
		arrayLists[4]=new ArrayList<>();
		arrayLists[4].add(tokentype.CHAR.toString());
		arrayLists[4].add("<类型>`");
		hashMap[2].put(tokentype.CHAR.toString(), arrayLists[4]);
		//|double<类型>`		{double}
		arrayLists[5]=new ArrayList<>();
		arrayLists[5].add(tokentype.DOUBLE.toString());
		arrayLists[5].add("<类型>`");
		hashMap[2].put(tokentype.DOUBLE.toString(), arrayLists[5]);
		//|float<类型>`		{float}	
		arrayLists[6]=new ArrayList<>();
		arrayLists[6].add(tokentype.FLOAT.toString());
		arrayLists[6].add("<类型>`");
		hashMap[2].put(tokentype.FLOAT.toString(), arrayLists[6]);
		predictiveTable.put("<类型>", hashMap[2]);
		//<类型>`::= '['']'<类型>` {'['}
		arrayLists[7]=new ArrayList<>();
		arrayLists[7].add(tokentype.MIDLPAREN.toString());
		arrayLists[7].add(tokentype.MIDRPAREN.toString());
		arrayLists[7].add("<类型>`");
		hashMap[3] = new HashMap<>();
		hashMap[3].put(tokentype.MIDLPAREN.toString(), arrayLists[7]);
		//|<>  {标识符}
		arrayLists[8]=new ArrayList<>();
		arrayLists[8].add("");
		hashMap[3].put(tokentype.ID.toString(), arrayLists[8]);
		predictiveTable.put("<类型>`", hashMap[3]);
		//<主要声明>		::=	<非int的类型> 标识符<新变量或函数><主要声明>
		arrayLists[9]=new ArrayList<>();
		arrayLists[9].add(tokentype.CHAR.toString());
		arrayLists[9].add(tokentype.ID.toString());
		arrayLists[9].add("<新变量或函数>");
		arrayLists[9].add("<主要声明>");
		hashMap[4] = new HashMap<>();
		hashMap[4].put(tokentype.CHAR.toString(), arrayLists[9]);
		arrayLists[10]=new ArrayList<>();
		arrayLists[10].add(tokentype.DOUBLE.toString());
		arrayLists[10].add(tokentype.ID.toString());
		arrayLists[10].add("<新变量或函数>");
		arrayLists[10].add("<主要声明>");
		hashMap[4].put(tokentype.DOUBLE.toString(), arrayLists[10]);
		arrayLists[11]=new ArrayList<>();
		arrayLists[11].add(tokentype.FLOAT.toString());
		arrayLists[11].add(tokentype.ID.toString());
		arrayLists[11].add("<新变量或函数>");
		arrayLists[11].add("<主要声明>");
		hashMap[4].put(tokentype.FLOAT.toString(), arrayLists[11]);
		//|int <变量或函数或main>	<主要声明>		{int}
		arrayLists[12]=new ArrayList<>();
		arrayLists[12].add(tokentype.INT.toString());
		arrayLists[12].add("<变量或函数或main>");
		arrayLists[12].add("<主要声明>");
		hashMap[4].put(tokentype.INT.toString(), arrayLists[12]);
		//|void 标识符'('<形参>')''{'<代码块>'}'<主要声明>		{void}
		arrayLists[13]=new ArrayList<>();
		arrayLists[13].add(tokentype.VOID.toString());
		arrayLists[13].add(tokentype.ID.toString());
		arrayLists[13].add(tokentype.LPAREN.toString());
		arrayLists[13].add("<形参>");
		arrayLists[13].add(tokentype.RPAREN.toString());
		arrayLists[13].add(tokentype.LBRACE.toString());
		arrayLists[13].add("<代码块>");
		arrayLists[13].add(tokentype.RBRACE.toString());
		arrayLists[13].add("<主要声明>");
		hashMap[4].put(tokentype.VOID.toString(), arrayLists[13]);
		//|<>	{结束符}
		arrayLists[14]=new ArrayList<>();
		arrayLists[14].add("");
		hashMap[4].put(tokentype.ENDFILE.toString(), arrayLists[14]);
		predictiveTable.put("<主要声明>", hashMap[4]);
		//<新变量或函数>	::=	<新变量>;	  {',',标识符,;,=}
		arrayLists[15]=new ArrayList<>();
		arrayLists[15].add("<新变量>");
		arrayLists[15].add(tokentype.SEMI.toString());
		hashMap[5] = new HashMap<>();
		hashMap[5].put(tokentype.COMMA.toString(), arrayLists[15]);
		hashMap[5].put(tokentype.ID.toString(), arrayLists[15]);
		hashMap[5].put(tokentype.SEMI.toString(), arrayLists[15]);
		hashMap[5].put(tokentype.ASSIGN.toString(), arrayLists[15]);
		//|<新函数>		{'('}
		arrayLists[16]=new ArrayList<>();
		arrayLists[16].add("<新函数>");
		hashMap[5].put(tokentype.LPAREN.toString(), arrayLists[16]);
		predictiveTable.put("<新变量或函数>", hashMap[5]);
		//<新变量> 	::= ,标识符<新变量>  {,}
		arrayLists[17]=new ArrayList<>();
		arrayLists[17].add(tokentype.COMMA.toString());
		arrayLists[17].add(tokentype.ID.toString());
		arrayLists[17].add("<新变量>");
		hashMap[6] = new HashMap<>();
		hashMap[6].put(tokentype.COMMA.toString(), arrayLists[17]);
		//|=<常量><新变量> 	{=}
		arrayLists[18]=new ArrayList<>();
		arrayLists[18].add(tokentype.ASSIGN.toString());
		arrayLists[18].add("<常量>");
		arrayLists[18].add("<新变量>");
		hashMap[6].put(tokentype.ASSIGN.toString(), arrayLists[18]);
		//|<>		{;}
		arrayLists[19]=new ArrayList<>();
		arrayLists[19].add("");
		hashMap[6].put(tokentype.SEMI.toString(), arrayLists[19]);
		predictiveTable.put("<新变量>", hashMap[6]);
		//<常量>	::= 字符常量|整数常量|浮点常量|双精度常量	|字符串常量			
		arrayLists[20]=new ArrayList<>();
		arrayLists[20].add(tokentype.CONSTCHAR.toString());
		hashMap[7] = new HashMap<>();
		hashMap[7].put(tokentype.CONSTCHAR.toString(), arrayLists[20]);
		arrayLists[21]=new ArrayList<>();
		arrayLists[21].add(tokentype.CONSTINT.toString());
		hashMap[7].put(tokentype.CONSTINT.toString(), arrayLists[21]);
		arrayLists[22]=new ArrayList<>();
		arrayLists[22].add(tokentype.CONSTFLOAT.toString());
		hashMap[7].put(tokentype.CONSTFLOAT.toString(), arrayLists[22]);
		arrayLists[23]=new ArrayList<>();
		arrayLists[23].add(tokentype.CONSTDOUBLE.toString());
		hashMap[7].put(tokentype.CONSTDOUBLE.toString(), arrayLists[23]);
		arrayLists[107] = new ArrayList<>();
		arrayLists[107].add(tokentype.CONSTSTRING.toString());
		hashMap[7].put(tokentype.CONSTSTRING.toString(), arrayLists[107]);
		predictiveTable.put("<常量>", hashMap[7]);
		//<新函数>		::=	'('<形参>')''{'<代码块>'}'    {'('}
		arrayLists[24]=new ArrayList<>();
		arrayLists[24].add(tokentype.LPAREN.toString());
		arrayLists[24].add("<形参>");
		arrayLists[24].add(tokentype.RPAREN.toString());
		arrayLists[24].add(tokentype.LBRACE.toString());
		arrayLists[24].add("<代码块>");
		arrayLists[24].add(tokentype.RBRACE.toString());
		hashMap[8] = new HashMap<>();
		hashMap[8].put(tokentype.LPAREN.toString(), arrayLists[24]);
		predictiveTable.put("<新函数>", hashMap[8]);
		 //<变量或函数或main>	::=	标识符<新变量或函数>	 {标识符}
		arrayLists[25]=new ArrayList<>();
		arrayLists[25].add(tokentype.ID.toString());
		arrayLists[25].add("<新变量或函数>");
		hashMap[9] = new HashMap<>();
		hashMap[9].put(tokentype.ID.toString(), arrayLists[25]);
		//|main'('<形参>')''{'<代码块>'}'   {main}
		arrayLists[26]=new ArrayList<>();
		arrayLists[26].add(tokentype.MAIN.toString());
		arrayLists[26].add(tokentype.LPAREN.toString());
		arrayLists[26].add("<形参>");
		arrayLists[26].add(tokentype.RPAREN.toString());
		arrayLists[26].add(tokentype.LBRACE.toString());
		arrayLists[26].add("<代码块>");
		arrayLists[26].add(tokentype.RBRACE.toString());
		hashMap[9].put(tokentype.MAIN.toString(), arrayLists[26]);
		predictiveTable.put("<变量或函数或main>", hashMap[9]);
		//<代码块>	::=	<语句声明><代码块>    {'(',!,-,常量,if,while,do,for,switch,return,break,continue,标识符}
		arrayLists[27]=new ArrayList<>();
		arrayLists[27].add("<语句声明>");
		arrayLists[27].add("<代码块>");
		hashMap[10] = new HashMap<>();
		hashMap[10].put(tokentype.LPAREN.toString(), arrayLists[27]);
		hashMap[10].put(tokentype.NOT.toString(), arrayLists[27]);
		hashMap[10].put(tokentype.MINUS.toString(), arrayLists[27]);
		hashMap[10].put(tokentype.CONSTINT.toString(), arrayLists[27]);
		hashMap[10].put(tokentype.CONSTCHAR.toString(), arrayLists[27]);
		hashMap[10].put(tokentype.CONSTSTRING.toString(), arrayLists[27]);
		hashMap[10].put(tokentype.CONSTDOUBLE.toString(), arrayLists[27]);
		hashMap[10].put(tokentype.CONSTFLOAT.toString(), arrayLists[27]);
		hashMap[10].put(tokentype.IF.toString(), arrayLists[27]);
		hashMap[10].put(tokentype.WHILE.toString(), arrayLists[27]);
		hashMap[10].put(tokentype.DO.toString(), arrayLists[27]);
		hashMap[10].put(tokentype.FOR.toString(), arrayLists[27]);
		hashMap[10].put(tokentype.SWITCH.toString(), arrayLists[27]);
		hashMap[10].put(tokentype.RETURN.toString(), arrayLists[27]);
		hashMap[10].put(tokentype.BREAK.toString(), arrayLists[27]);
		hashMap[10].put(tokentype.CONTINUE.toString(), arrayLists[27]);
		hashMap[10].put(tokentype.ID.toString(), arrayLists[27]);
		//|<类型>标识符<新变量>;<代码块>    {类型}
		arrayLists[28]=new ArrayList<>();
		arrayLists[28].add("<类型>");
		arrayLists[28].add(tokentype.ID.toString());
		arrayLists[28].add("<新变量>");
		arrayLists[28].add(tokentype.SEMI.toString());
		arrayLists[28].add("<代码块>");
		hashMap[10].put(tokentype.INT.toString(), arrayLists[28]);
		hashMap[10].put(tokentype.CHAR.toString(), arrayLists[28]);
		hashMap[10].put(tokentype.DOUBLE.toString(), arrayLists[28]);
		hashMap[10].put(tokentype.FLOAT.toString(), arrayLists[28]);
		//|<>		{'}'}
		arrayLists[29]=new ArrayList<>();
		arrayLists[29].add("");
		hashMap[10].put(tokentype.RBRACE.toString(), arrayLists[29]);
		predictiveTable.put("<代码块>", hashMap[10]);
		//<语句声明>	::=	<表达式>';'    {'(',!,-,常量}
		arrayLists[30]=new ArrayList<>();
		arrayLists[30].add("<表达式>");
		arrayLists[30].add(tokentype.SEMI.toString());
		hashMap[11]=new HashMap<>();
		hashMap[11].put(tokentype.LPAREN.toString(), arrayLists[30]);
		hashMap[11].put(tokentype.NOT.toString(), arrayLists[30]);
		hashMap[11].put(tokentype.MINUS.toString(), arrayLists[30]);
		hashMap[11].put(tokentype.CONSTINT.toString(), arrayLists[30]);
		hashMap[11].put(tokentype.CONSTDOUBLE.toString(), arrayLists[30]);
		hashMap[11].put(tokentype.CONSTFLOAT.toString(), arrayLists[30]);
		hashMap[11].put(tokentype.CONSTCHAR.toString(), arrayLists[30]);
		hashMap[11].put(tokentype.CONSTSTRING.toString(), arrayLists[30]);
		//|<条件语句>	    {if}
		arrayLists[31]=new ArrayList<>();
		arrayLists[31].add("<条件语句>");
		hashMap[11].put(tokentype.IF.toString(), arrayLists[31]);
		//|<while循环语句>  {while}
		arrayLists[32]=new ArrayList<>();
		arrayLists[32].add("<while循环语句>");
		hashMap[11].put(tokentype.WHILE.toString(), arrayLists[32]);
		//|<do语句>';'   {do}
		arrayLists[33]=new ArrayList<>();
		arrayLists[33].add("<do语句>");
		arrayLists[33].add(tokentype.SEMI.toString());
		hashMap[11].put(tokentype.DO.toString(), arrayLists[33]);
		//|<for语句>   {for}
		arrayLists[34]=new ArrayList<>();
		arrayLists[34].add("<for语句>");
		hashMap[11].put(tokentype.FOR.toString(), arrayLists[34]);
		//|<switch语句>	{switch}
		arrayLists[35]=new ArrayList<>();
		arrayLists[35].add("<switch语句>");
		hashMap[11].put(tokentype.SWITCH.toString(), arrayLists[35]);
		//|<return语句>		{return}	
		arrayLists[36]=new ArrayList<>();
		arrayLists[36].add("<return语句>");
		hashMap[11].put(tokentype.RETURN.toString(), arrayLists[36]);
		//|<break语句>	{break}
		arrayLists[37]=new ArrayList<>();
		arrayLists[37].add("<break语句>");
		hashMap[11].put(tokentype.BREAK.toString(), arrayLists[37]);
		//|<continue语句>		{continue}
		arrayLists[38]=new ArrayList<>();
		arrayLists[38].add("<continue语句>");
		hashMap[11].put(tokentype.CONTINUE.toString(), arrayLists[38]);
		//|<含标识符表达式或函数调用语句>		{标识符}
		arrayLists[39]=new ArrayList<>();
		arrayLists[39].add("<含标识符表达式或函数调用语句>");
		hashMap[11].put(tokentype.ID.toString(), arrayLists[39]);
		predictiveTable.put("<语句声明>", hashMap[11]);
		//<含标识符表达式或函数调用语句> ::=标识符<含标识符表达式或函数调用语句>`		{标识符}
		arrayLists[40]=new ArrayList<>();
		arrayLists[40].add(tokentype.ID.toString());
		arrayLists[40].add("<含标识符表达式或函数调用语句>`");
		hashMap[12]=new HashMap<>();
		hashMap[12].put(tokentype.ID.toString(), arrayLists[40]);
		predictiveTable.put("<含标识符表达式或函数调用语句>", hashMap[12]);
		//<含标识符表达式或函数调用语句>`::=<表达式>`		{算符,;,)}
		arrayLists[41]=new ArrayList<>();
		arrayLists[41].add("<表达式>`");
		hashMap[13] = new HashMap<>();
		hashMap[13].put(tokentype.RPAREN.toString(), arrayLists[41]);
		hashMap[13].put(tokentype.SEMI.toString(), arrayLists[41]);
		hashMap[13].put(tokentype.PLUS.toString(), arrayLists[41]);
		hashMap[13].put(tokentype.MINUS.toString(), arrayLists[41]);
		hashMap[13].put(tokentype.TIMES.toString(), arrayLists[41]);
		hashMap[13].put(tokentype.OVER.toString(), arrayLists[41]);
		hashMap[13].put(tokentype.EQ.toString(), arrayLists[41]);
		hashMap[13].put(tokentype.LT.toString(), arrayLists[41]);
		hashMap[13].put(tokentype.ASSIGN.toString(), arrayLists[41]);
		hashMap[13].put(tokentype.LTE.toString(), arrayLists[41]);
		hashMap[13].put(tokentype.BG.toString(), arrayLists[41]);
		hashMap[13].put(tokentype.BGE.toString(), arrayLists[41]);
		hashMap[13].put(tokentype.INC.toString(), arrayLists[41]);
		hashMap[13].put(tokentype.DEC.toString(), arrayLists[41]);
		hashMap[13].put(tokentype.MOD.toString(), arrayLists[41]);
		//|'('<值表>')'';'		{'('}
		arrayLists[42]=new ArrayList<>();
		arrayLists[42].add(tokentype.LPAREN.toString());
		arrayLists[42].add("<值表>");
		arrayLists[42].add(tokentype.RPAREN.toString());
		arrayLists[42].add(tokentype.SEMI.toString());
		hashMap[13].put(tokentype.LPAREN.toString(), arrayLists[42]);
		predictiveTable.put("<含标识符表达式或函数调用语句>`", hashMap[13]);
		//<表达式> ::= '('<表达式>')'<表达式>`	{'('}
		arrayLists[43]=new ArrayList<>();
		arrayLists[43].add(tokentype.LPAREN.toString());
		arrayLists[43].add("<表达式>");
		arrayLists[43].add(tokentype.RPAREN.toString());
		arrayLists[43].add("<表达式>`");
		hashMap[14] = new HashMap<>();
		hashMap[14].put(tokentype.LPAREN.toString(),arrayLists[43]);
		//|!<表达式><表达式>`				{'!'}
		arrayLists[44]=new ArrayList<>();
		arrayLists[44].add(tokentype.NOT.toString());
		arrayLists[44].add("<表达式>");
		arrayLists[44].add("<表达式>`");
		hashMap[14].put(tokentype.LPAREN.toString(),arrayLists[44]);
		//|-<表达式><表达式>`				{'-'}
		arrayLists[45]=new ArrayList<>();
		arrayLists[45].add(tokentype.MINUS.toString());
		arrayLists[45].add("<表达式>");
		arrayLists[45].add("<表达式>`");
		hashMap[14].put(tokentype.MINUS.toString(),arrayLists[45]);
		//|<常量><表达式>`					{常量}={INT,CHAR,DOUBLE,FLOAT}
		arrayLists[46]=new ArrayList<>();
		arrayLists[46].add("<常量>");
		arrayLists[46].add("<表达式>`");
		hashMap[14].put(tokentype.CONSTINT.toString(),arrayLists[46]);
		hashMap[14].put(tokentype.CONSTCHAR.toString(),arrayLists[46]);
		hashMap[14].put(tokentype.CONSTDOUBLE.toString(),arrayLists[46]);
		hashMap[14].put(tokentype.CONSTFLOAT.toString(),arrayLists[46]);
		predictiveTable.put("<表达式>", hashMap[14]);
		//<表达式>` ::= <算符><表达式><表达式>`	{算符}=+|-|*|/|%|<|<=|>|>=|==|!=|&&|'||'|=|&|'|'
		arrayLists[47]=new ArrayList<>();
		arrayLists[47].add("<算符>");
		arrayLists[47].add("<表达式>");
		arrayLists[47].add("<表达式>`");
		hashMap[15] = new HashMap<>();
		hashMap[15].put(tokentype.PLUS.toString(),arrayLists[47]);
		hashMap[15].put(tokentype.MINUS.toString(),arrayLists[47]);
		hashMap[15].put(tokentype.TIMES.toString(),arrayLists[47]);
		hashMap[15].put(tokentype.OVER.toString(),arrayLists[47]);
		hashMap[15].put(tokentype.MOD.toString(),arrayLists[47]);
		hashMap[15].put(tokentype.LT.toString(),arrayLists[47]);
		hashMap[15].put(tokentype.LTE.toString(),arrayLists[47]);
		hashMap[15].put(tokentype.BG.toString(),arrayLists[47]);
		hashMap[15].put(tokentype.BGE.toString(),arrayLists[47]);
		hashMap[15].put(tokentype.EQ.toString(),arrayLists[47]);
		hashMap[15].put(tokentype.NOTE.toString(),arrayLists[47]);
		hashMap[15].put(tokentype.AND.toString(),arrayLists[47]);
		hashMap[15].put(tokentype.OR.toString(),arrayLists[47]);
		hashMap[15].put(tokentype.ASSIGN.toString(),arrayLists[47]);
		hashMap[15].put(tokentype.BAND.toString(),arrayLists[47]);
		hashMap[15].put(tokentype.BOR.toString(),arrayLists[47]);
		
		//|<>				{';'}
		arrayLists[48]=new ArrayList<>();
		arrayLists[48].add("");
		hashMap[15].put(tokentype.SEMI.toString(),arrayLists[48]);
		predictiveTable.put("<表达式>`", hashMap[15]);
		//<算符>::= +|-|*|/|%|<|<=|>|>=|==|!=|&&|'||'|=|&|'|'		
		arrayLists[49]=new ArrayList<>();
		arrayLists[49].add(tokentype.PLUS.toString());
		hashMap[16] = new HashMap<>();
		hashMap[16].put(tokentype.PLUS.toString(),arrayLists[49]);
		arrayLists[50]=new ArrayList<>();
		arrayLists[50].add(tokentype.MINUS.toString());		
		hashMap[16].put(tokentype.MINUS.toString(),arrayLists[50]);
		arrayLists[51]=new ArrayList<>();
		arrayLists[51].add(tokentype.TIMES.toString());
		hashMap[16].put(tokentype.TIMES.toString(),arrayLists[51]);
		arrayLists[52]=new ArrayList<>();
		arrayLists[52].add(tokentype.OVER.toString());
		hashMap[16].put(tokentype.OVER.toString(),arrayLists[52]);
		arrayLists[53]=new ArrayList<>();
		arrayLists[53].add(tokentype.MOD.toString());
		hashMap[16].put(tokentype.MOD.toString(),arrayLists[53]);
		arrayLists[54]=new ArrayList<>();
		arrayLists[54].add(tokentype.LT.toString());
		hashMap[16].put(tokentype.LT.toString(),arrayLists[54]);
		arrayLists[55]=new ArrayList<>();
		arrayLists[55].add(tokentype.LTE.toString());
		hashMap[16].put(tokentype.LTE.toString(),arrayLists[55]);
		arrayLists[56]=new ArrayList<>();
		arrayLists[56].add(tokentype.BG.toString());
		hashMap[16].put(tokentype.BG.toString(),arrayLists[56]);
		arrayLists[57]=new ArrayList<>();
		arrayLists[57].add(tokentype.BGE.toString());
		hashMap[16].put(tokentype.BGE.toString(),arrayLists[57]);
		arrayLists[58]=new ArrayList<>();
		arrayLists[58].add(tokentype.EQ.toString());
		hashMap[16].put(tokentype.EQ.toString(),arrayLists[58]);
		arrayLists[59]=new ArrayList<>();
		arrayLists[59].add(tokentype.NOTE.toString());
		hashMap[16].put(tokentype.NOTE.toString(),arrayLists[59]);
		arrayLists[60]=new ArrayList<>();
		arrayLists[60].add(tokentype.AND.toString());
		hashMap[16].put(tokentype.AND.toString(),arrayLists[60]);
		arrayLists[61]=new ArrayList<>();
		arrayLists[61].add(tokentype.OR.toString());
		hashMap[16].put(tokentype.OR.toString(),arrayLists[61]);
		arrayLists[62]=new ArrayList<>();
		arrayLists[62].add(tokentype.BAND.toString());
		hashMap[16].put(tokentype.BAND.toString(),arrayLists[62]);
		arrayLists[63]=new ArrayList<>();
		arrayLists[63].add(tokentype.BOR.toString());
		hashMap[16].put(tokentype.BOR.toString(),arrayLists[63]);
		arrayLists[64]=new ArrayList<>();
		arrayLists[64].add(tokentype.BNOT.toString());
		hashMap[16].put(tokentype.BNOT.toString(),arrayLists[64]);
		arrayLists[65]=new ArrayList<>();
		arrayLists[65].add(tokentype.ASSIGN.toString());
		hashMap[16].put(tokentype.ASSIGN.toString(),arrayLists[65]);
		arrayLists[66]=new ArrayList<>();
		arrayLists[66].add(tokentype.INC.toString());
		hashMap[16].put(tokentype.INC.toString(),arrayLists[66]);
		arrayLists[67]=new ArrayList<>();
		arrayLists[67].add(tokentype.DEC.toString());
		hashMap[16].put(tokentype.DEC.toString(),arrayLists[67]);
		arrayLists[68]=new ArrayList<>();
		arrayLists[68].add(tokentype.LEFTSHIFT.toString());
		hashMap[16].put(tokentype.LEFTSHIFT.toString(),arrayLists[68]);
		arrayLists[69]=new ArrayList<>();
		arrayLists[69].add(tokentype.RIGHTSHIFT.toString());
		hashMap[16].put(tokentype.RIGHTSHIFT.toString(),arrayLists[69]);
		arrayLists[70]=new ArrayList<>();
		arrayLists[70].add(tokentype.PLUSE.toString());
		hashMap[16].put(tokentype.PLUSE.toString(),arrayLists[70]);
		arrayLists[71]=new ArrayList<>();
		arrayLists[71].add(tokentype.MINUSE.toString());
		hashMap[16].put(tokentype.MINUSE.toString(),arrayLists[71]);
		arrayLists[72]=new ArrayList<>();
		arrayLists[72].add(tokentype.TIMESE.toString());
		hashMap[16].put(tokentype.TIMESE.toString(),arrayLists[72]);
		arrayLists[73]=new ArrayList<>();
		arrayLists[73].add(tokentype.OVERE.toString());
		hashMap[16].put(tokentype.OVERE.toString(),arrayLists[73]);
		arrayLists[74]=new ArrayList<>();
		arrayLists[74].add(tokentype.MODE.toString());
		hashMap[16].put(tokentype.MODE.toString(),arrayLists[74]);
		arrayLists[75]=new ArrayList<>();
		arrayLists[75].add(tokentype.BANDE.toString());
		hashMap[16].put(tokentype.BANDE.toString(),arrayLists[75]);
		arrayLists[76]=new ArrayList<>();
		arrayLists[76].add(tokentype.BORE.toString());
		hashMap[16].put(tokentype.BORE.toString(),arrayLists[76]);
		arrayLists[77]=new ArrayList<>();
		arrayLists[77].add(tokentype.BONTE.toString());
		hashMap[16].put(tokentype.BONTE.toString(),arrayLists[77]);
		arrayLists[78]=new ArrayList<>();
		arrayLists[78].add(tokentype.LSE.toString());
		hashMap[16].put(tokentype.LSE.toString(),arrayLists[78]);
		arrayLists[79]=new ArrayList<>();
		arrayLists[79].add(tokentype.RSE.toString());
		hashMap[16].put(tokentype.RSE.toString(),arrayLists[79]);
		predictiveTable.put("<算符>", hashMap[16]);
		//<条件语句> ::= if'('<条件>')''{<代码块>'}'<else语句>	{if}			
		arrayLists[80] = new ArrayList<>();
		arrayLists[80].add(tokentype.IF.toString());
		arrayLists[80].add(tokentype.LPAREN.toString());
		arrayLists[80].add("<条件>");
		arrayLists[80].add(tokentype.RPAREN.toString());
		arrayLists[80].add(tokentype.LBRACE.toString());
		arrayLists[80].add("<代码块>");
		arrayLists[80].add(tokentype.RBRACE.toString());
		arrayLists[80].add("<else语句>");
		hashMap[17] = new HashMap<>();
		hashMap[17].put(tokentype.IF.toString(),arrayLists[80]);
		predictiveTable.put("<条件语句>", hashMap[17]);
		//<else语句>::= else'{'<代码块>'}'		{else}
		arrayLists[81] = new ArrayList<>();
		arrayLists[81].add(tokentype.ELSE.toString());
		arrayLists[81].add(tokentype.LBRACE.toString());
		arrayLists[81].add("<代码块>");
		arrayLists[81].add(tokentype.RBRACE.toString());
		hashMap[18] = new HashMap<>();
		hashMap[18].put(tokentype.ELSE.toString(),arrayLists[81]);
		//|<>			{类型,'(','!','-',常量,if,while,do,for,switch,return,break,continue,标识符,'}'} 
		arrayLists[82] = new ArrayList<>();
		arrayLists[82].add("");
		hashMap[18].put(tokentype.INT.toString(),arrayLists[82]);
		hashMap[18].put(tokentype.CHAR.toString(),arrayLists[82]);
		hashMap[18].put(tokentype.FLOAT.toString(),arrayLists[82]);
		hashMap[18].put(tokentype.DOUBLE.toString(),arrayLists[82]);
		hashMap[18].put(tokentype.LPAREN.toString(),arrayLists[82]);
		hashMap[18].put(tokentype.NOT.toString(),arrayLists[82]);
		hashMap[18].put(tokentype.MINUS.toString(),arrayLists[82]);
		hashMap[18].put(tokentype.CONSTINT.toString(),arrayLists[82]);
		hashMap[18].put(tokentype.CONSTFLOAT.toString(),arrayLists[82]);
		hashMap[18].put(tokentype.CONSTDOUBLE.toString(),arrayLists[82]);
		hashMap[18].put(tokentype.CONSTCHAR.toString(),arrayLists[82]);
		hashMap[18].put(tokentype.CONSTSTRING.toString(),arrayLists[82]);
		hashMap[18].put(tokentype.IF.toString(),arrayLists[82]);
		hashMap[18].put(tokentype.WHILE.toString(),arrayLists[82]);
		hashMap[18].put(tokentype.DO.toString(),arrayLists[82]);
		hashMap[18].put(tokentype.FOR.toString(),arrayLists[82]);
		hashMap[18].put(tokentype.SWITCH.toString(),arrayLists[82]);
		hashMap[18].put(tokentype.RETURN.toString(),arrayLists[82]);
		hashMap[18].put(tokentype.BREAK.toString(),arrayLists[82]);
		hashMap[18].put(tokentype.CONTINUE.toString(),arrayLists[82]);
		hashMap[18].put(tokentype.ID.toString(),arrayLists[82]);
		hashMap[18].put(tokentype.RBRACE.toString(),arrayLists[82]);
		
		predictiveTable.put("<else语句>", hashMap[18]);
		//<条件>	::=<表达式>		{'(','!','-',常量}
		arrayLists[83] = new ArrayList<>();
		arrayLists[83].add("<表达式>");
		hashMap[19] = new HashMap<>();
		hashMap[19].put(tokentype.INT.toString(),arrayLists[83]);
		hashMap[19].put(tokentype.LPAREN.toString(),arrayLists[83]);
		hashMap[19].put(tokentype.NOT.toString(),arrayLists[83]);
		hashMap[19].put(tokentype.MINUS.toString(),arrayLists[83]);
		hashMap[19].put(tokentype.CONSTINT.toString(),arrayLists[83]);
		hashMap[19].put(tokentype.CONSTSTRING.toString(),arrayLists[83]);
		hashMap[19].put(tokentype.CONSTCHAR.toString(),arrayLists[83]);
		hashMap[19].put(tokentype.CONSTFLOAT.toString(),arrayLists[83]);
		hashMap[19].put(tokentype.CONSTDOUBLE.toString(),arrayLists[83]);
		//|标识符<表达式>`			{标识符}
		arrayLists[84] = new ArrayList<>();
		arrayLists[84].add(tokentype.ID.toString());
		arrayLists[84].add("<表达式>`");
		hashMap[19].put(tokentype.ID.toString(),arrayLists[84]);
		//|<类型> 标识符=<表达式>	{类型}
		arrayLists[109]=new ArrayList<>();
		arrayLists[109].add(tokentype.INT.toString());
		arrayLists[109].add(tokentype.ID.toString());
		arrayLists[109].add(tokentype.ASSIGN.toString());
		arrayLists[109].add("<表达式>");
		hashMap[19].put(tokentype.INT.toString(),arrayLists[109]);
		arrayLists[110]=new ArrayList<>();
		arrayLists[110].add(tokentype.CHAR.toString());
		arrayLists[110].add(tokentype.ID.toString());
		arrayLists[110].add(tokentype.ASSIGN.toString());
		arrayLists[110].add("<表达式>");
		hashMap[19].put(tokentype.CHAR.toString(),arrayLists[110]);
		arrayLists[111]=new ArrayList<>();
		arrayLists[111].add(tokentype.FLOAT.toString());
		arrayLists[111].add(tokentype.ID.toString());
		arrayLists[111].add(tokentype.ASSIGN.toString());
		arrayLists[111].add("<表达式>");
		hashMap[19].put(tokentype.FLOAT.toString(),arrayLists[111]);
		arrayLists[112]=new ArrayList<>();
		arrayLists[112].add(tokentype.DOUBLE.toString());
		arrayLists[112].add(tokentype.ID.toString());
		arrayLists[112].add(tokentype.ASSIGN.toString());
		arrayLists[112].add("<表达式>");
		hashMap[19].put(tokentype.DOUBLE.toString(),arrayLists[112]);
		predictiveTable.put("<条件>", hashMap[19]);
		//<while循环语句> ::= while'('<条件>')''{'<代码块>'}'		{while}
		arrayLists[85] = new ArrayList<>();
		arrayLists[85].add(tokentype.WHILE.toString());
		arrayLists[85].add(tokentype.LPAREN.toString());
		arrayLists[85].add("<条件>");
		arrayLists[85].add(tokentype.RPAREN.toString());
		arrayLists[85].add(tokentype.LBRACE.toString());
		arrayLists[85].add("<代码块>");
		arrayLists[85].add(tokentype.RBRACE.toString());
		hashMap[20] = new HashMap<>();
		hashMap[20].put(tokentype.WHILE.toString(),arrayLists[85]);
		predictiveTable.put("<while循环语句>", hashMap[20]);
		//<do语句> ::= do'{'<代码块>'}'while'('<条件>')'';'			{do}
		arrayLists[86]=new ArrayList<>();
		arrayLists[86].add(tokentype.DO.toString());
		arrayLists[86].add(tokentype.LBRACE.toString());
		arrayLists[86].add("<代码块>");
		arrayLists[86].add(tokentype.RBRACE.toString());
		arrayLists[86].add(tokentype.WHILE.toString());
		arrayLists[86].add(tokentype.LPAREN.toString());
		arrayLists[86].add("<条件>");
		arrayLists[86].add(tokentype.RPAREN.toString());
		arrayLists[86].add(tokentype.SEMI.toString());
		hashMap[21] = new HashMap<>();
		hashMap[21].put(tokentype.DO.toString(),arrayLists[86]);
		predictiveTable.put("<do语句>", hashMap[21]);
		//<for语句>		::=for'('<条件>';'<条件>';'<条件>')''{'<代码块>'}'	{for}
		arrayLists[87]=new ArrayList<>();
		arrayLists[87].add(tokentype.FOR.toString());
		arrayLists[87].add(tokentype.LPAREN.toString());
		arrayLists[87].add("<条件>");
		arrayLists[87].add(tokentype.SEMI.toString());
		arrayLists[87].add("<条件>");
		arrayLists[87].add(tokentype.SEMI.toString());
		arrayLists[87].add("<含标识符表达式或函数调用语句>");
		arrayLists[87].add(tokentype.RPAREN.toString());
		arrayLists[87].add(tokentype.LBRACE.toString());
		arrayLists[87].add("<代码块>");
		arrayLists[87].add(tokentype.RBRACE.toString());
		hashMap[22] = new HashMap<>();
		hashMap[22].put(tokentype.FOR.toString(),arrayLists[87]);
		predictiveTable.put("<for语句>", hashMap[22]);
		//<switch语句>	::= switch'('<整数>')''{'<case语句>'}'			{switch}
		arrayLists[88] = new ArrayList<>();
		arrayLists[88].add(tokentype.SWITCH.toString());
		arrayLists[88].add(tokentype.LPAREN.toString());
		arrayLists[88].add("<整数>");
		arrayLists[88].add(tokentype.RPAREN.toString());
		arrayLists[88].add(tokentype.LBRACE.toString());
		arrayLists[88].add("<case语句>");
		arrayLists[88].add(tokentype.RBRACE.toString());
		hashMap[23] = new HashMap<>();
		hashMap[23].put(tokentype.SWITCH.toString(),arrayLists[88]);
		predictiveTable.put("<switch语句>", hashMap[23]);
		//<case语句> ::= case <整数>:'{'<代码块>'}'<case语句>	{case}
		arrayLists[89] = new ArrayList<>();
		arrayLists[89].add(tokentype.CASE.toString());
		arrayLists[89].add(tokentype.CONSTINT.toString());
		arrayLists[89].add(tokentype.COLON.toString());
		arrayLists[89].add(tokentype.LBRACE.toString());
		arrayLists[89].add("<代码块>");
		arrayLists[89].add(tokentype.RBRACE.toString());
		arrayLists[89].add("<case语句>");
		hashMap[24] = new HashMap<>();
		hashMap[24].put(tokentype.CASE.toString(),arrayLists[89]);
		//|default:'{'<代码块>'}'			{default}
		arrayLists[90] = new ArrayList<>();
		arrayLists[90].add(tokentype.DEFAULT.toString());
		arrayLists[90].add(tokentype.COLON.toString());
		arrayLists[90].add(tokentype.LBRACE.toString());
		arrayLists[90].add("<代码块>");
		arrayLists[90].add(tokentype.RBRACE.toString());
		hashMap[24].put(tokentype.DEFAULT.toString(),arrayLists[90]);
		//|<>		{'}'}
		arrayLists[91] = new ArrayList<>();
		arrayLists[91].add("");
		hashMap[24].put(tokentype.RBRACE.toString(),arrayLists[91]);
		predictiveTable.put("<case语句>", hashMap[24]);
		//<return语句> ::= return<标识符或常量>; 	{return}
		arrayLists[92] = new ArrayList<>();
		arrayLists[92].add(tokentype.RETURN.toString());
		arrayLists[92].add("<标识符或常量>");
		hashMap[25] = new HashMap<>();
		hashMap[25].put(tokentype.RETURN.toString(),arrayLists[92]);
		predictiveTable.put("<return语句>", hashMap[25]);
		//<标识符或常量>	::= 标识符		{标识符}
		arrayLists[93] = new ArrayList<>();
		arrayLists[93].add(tokentype.ID.toString());
		hashMap[26] = new HashMap<>();
		hashMap[26].put(tokentype.ID.toString(),arrayLists[93]);
		//|<常量>				{常量}={CONSTINT,CONSTDOUBLE,CONSTCHAR,CONSTFLOAT}
		arrayLists[94] = new ArrayList<>();
		arrayLists[94].add("<常量>");
		hashMap[26].put(tokentype.CONSTINT.toString(),arrayLists[94]);
		hashMap[26].put(tokentype.CONSTDOUBLE.toString(),arrayLists[94]);
		hashMap[26].put(tokentype.CONSTCHAR.toString(),arrayLists[94]);
		hashMap[26].put(tokentype.CONSTFLOAT.toString(),arrayLists[94]);
		hashMap[26].put(tokentype.CONSTSTRING.toString(),arrayLists[94]);
		predictiveTable.put("<标识符或常量>", hashMap[26]);
		//<break语句>	::= break;		{break}
		arrayLists[95] = new ArrayList<>();
		arrayLists[95].add(tokentype.BREAK.toString());
		arrayLists[95].add(tokentype.SEMI.toString());
		hashMap[27] = new HashMap<>();
		hashMap[27].put(tokentype.BREAK.toString(),arrayLists[95]);
		predictiveTable.put("<break语句>", hashMap[27]);
		//<continue语句>	::= continue;  {continue}
		arrayLists[96] = new ArrayList<>();
		arrayLists[96].add(tokentype.CONTINUE.toString());
		arrayLists[96].add(tokentype.SEMI.toString());
		hashMap[28] = new HashMap<>();
		hashMap[28].put(tokentype.CONTINUE.toString(),arrayLists[96]);
		predictiveTable.put("<continue语句>", hashMap[28]);
		//<函数调用语句>	::= 标识符'('<值表>')'';'		{标识符}
		arrayLists[97]=new ArrayList<>();
		arrayLists[97].add(tokentype.ID.toString());
		arrayLists[97].add(tokentype.LPAREN.toString());
		arrayLists[97].add("<值表>");
		arrayLists[97].add(tokentype.RPAREN.toString());
		arrayLists[97].add(tokentype.SEMI.toString());
		hashMap[29] = new HashMap<>();
		hashMap[29].put(tokentype.ID.toString(),arrayLists[97]);
		predictiveTable.put("<函数调用语句>", hashMap[29]);
		//<值表> ::= 标识符<值表>`		{标识符}
		arrayLists[98] = new ArrayList<>();
		arrayLists[98].add(tokentype.ID.toString());
		arrayLists[98].add("<值表>`");
		hashMap[30] = new HashMap<>();
		hashMap[30].put(tokentype.ID.toString(),arrayLists[98]);
		//|<常量><值表>`			{常量}
		arrayLists[99]= new ArrayList<>();
		arrayLists[99].add("<常量>");
		hashMap[30].put(tokentype.CONSTINT.toString(),arrayLists[99]);
		hashMap[30].put(tokentype.CONSTDOUBLE.toString(),arrayLists[99]);
		hashMap[30].put(tokentype.CONSTCHAR.toString(),arrayLists[99]);
		hashMap[30].put(tokentype.CONSTFLOAT.toString(),arrayLists[99]);
		hashMap[30].put(tokentype.CONSTSTRING.toString(),arrayLists[99]);;
		//|<>			{')'}
		arrayLists[100] = new ArrayList<>();
		arrayLists[100].add("");
		hashMap[30].put(tokentype.RPAREN.toString(),arrayLists[100]);
		predictiveTable.put("<值表>", hashMap[30]);
		//<值表>` ::= ,<标识符或常量><值表>`	{','}
		arrayLists[101] = new ArrayList<>();
		arrayLists[101].add(tokentype.COMMA.toString());
		arrayLists[101].add("<标识符或常量>");
		arrayLists[101].add("<值表>`");
		hashMap[31] = new HashMap<>();
		hashMap[31].put(tokentype.COMMA.toString(),arrayLists[101]);
		//|<>		{')'}
		arrayLists[102]= new ArrayList<>();
		arrayLists[102].add("");
		hashMap[31].put(tokentype.RPAREN.toString(),arrayLists[102]);
		predictiveTable.put("<值表>`", hashMap[31]);
		//	<形参> ::= <类型>标识符<形参>`		{类型}={INT,CHAR,DOUBLE,FLOAT}	
		arrayLists[103] = new ArrayList<>();
		arrayLists[103].add("<类型>");
		arrayLists[103].add(tokentype.ID.toString());
		arrayLists[103].add("<形参>`");
		hashMap[32] = new HashMap<>();
		hashMap[32].put(tokentype.INT.toString(),arrayLists[103]);
		hashMap[32].put(tokentype.CHAR.toString(),arrayLists[103]);
		hashMap[32].put(tokentype.DOUBLE.toString(),arrayLists[103]);
		hashMap[32].put(tokentype.FLOAT.toString(),arrayLists[103]);
		//|<>			{')'}
		arrayLists[104] = new ArrayList<>();
		arrayLists[104].add("");
		hashMap[32].put(tokentype.RPAREN.toString(),arrayLists[104]);
		predictiveTable.put("<形参>", hashMap[32]);
		//<形参>`::=,<类型>标识符<形参>`		{','}
		arrayLists[105] = new ArrayList<>();
		arrayLists[105].add(tokentype.COMMA.toString());
		arrayLists[105].add("<类型>");
		arrayLists[105].add(tokentype.ID.toString());
		arrayLists[105].add("<形参>`");
		hashMap[33] = new HashMap<>();
		hashMap[33].put(tokentype.COMMA.toString(),arrayLists[105]);
		//|<>			{')'}
		arrayLists[106] = new ArrayList<>();
		arrayLists[106].add("");
		hashMap[33].put(tokentype.RPAREN.toString(),arrayLists[106]);
		predictiveTable.put("<形参>`", hashMap[33]);
	}
	
	//需要一个词法分析程序的对象
	Accidence accidence = new Accidence();
	//需要用到栈
	Stack<String> stack=new Stack<>();
	//需要变量a保存当前输入的终结符
	String a;

	//需要一个读入下一个符号的函数
	public String getNexttokent() {
		tokentype t = accidence.gettoken();
		while(t==null) {
			t = accidence.gettoken();
			if(t==tokentype.ENDFILE) {
				break;
			}
	}		
			return t.toString();
	}
	//判断是否为终结符的函数
	public boolean isTernal(String X) {
		for(tokentype s:tokentype.values()) {
			if(s.toString().equals(X)) {
				return true;
			}
		}
		return false;
	}
	
	//需要一个上托栈顶符号时保存字符串的变量X
	String X;
	//次数 
	int count=0;
	private void predictiveParsing() {
		createTable();
		//根据课本93页
		//'#'、'S'进栈,本程序分别对应tokentype.ENDFILE和<程序>
		stack.push(tokentype.ENDFILE.toString());
		stack.push("<程序>");
		a=getNexttokent();
		//System.out.println(tokentype.valueOf("aa"));
		String pipeide="";
	while(true) {
			count++;
			System.out.println("次数："+count);
			System.out.print("分析栈：");
			if(!stack.isEmpty()) {
				for(String s:stack) {
				System.out.print(s+" ");
			}
			}
			System.out.println();
			System.out.println("当前输入符号："+a);
			X=stack.pop();
			if(!isTernal(X)) {
				System.out.println("产生式："+predictiveTable.get(X).get(a));
			}else {
				System.out.println("匹配："+X);
				pipeide+=X+" ";
			}
			System.out.println("已经匹配的串："+pipeide);
			//X属于终结符？
			//遍历tokentype中的所有内容,查看是否有一个与X相等即可判断X中的内容是否为终结符
			if((!X.equals(tokentype.ENDFILE.toString()))&&isTernal(X)) {
				//属于终结符时,判断X='a'?判断X变量中的内容与a变量中的内容是否相等
				//如果相等,读入下一个符号
				if(X.equals(a)) {
					a=getNexttokent();
				}else {
					//如果不相等,就出错,因为出现了文法无法推出该符号串的情况
					System.out.println("出错,出现了文法无法推出该某个符号的情况");
					break;
				}
			}else {//说明不属于终结符
				//书中为：X='#'?  在程序这里应该是判断X是否等于tokentype.ENDFILE
				if(X.equals(tokentype.ENDFILE.toString())) {
					//说明文法已经出栈完了,文法已经无法继续向下推了
					//这时就需要看字符串是否也完全推完了
					if(X.equals(a)) {
						//如果X与a中保存的内容相同,说明字符串也推完了,与文法相适应
						//说明这个串可以由文法推出来,所以接受这个串
						System.out.println("接受");
						break;
					}else {
						//如果X中与a中保存的内容不同,说明字符串还没有推完,文法不能推出该串
						//所以拒绝
						System.out.println("出错,出现了文法不能推出所以字符的情况");
						break;
					}
				}else {
					//如果X不为tokentype.ENDFILE说明文法还能继续向下推导,所以要看预测分析表进行下一步操作
					//观察预测分析表
					//M[X,a]是产生式吗？
					//在本程序中,应该是取predictiveTable对应非终结符遇到某个终结符时是否为null
					//predictive.get(非终结符)可以获得对应非终结符的哈希表
					//predictive.get(非终结符).get(终结符)可以获得非终结符为行,终结符为列的位置的产生式
					//这个产生式是一个ArrayList,ArrayList的长度为产生式符号的个数
					//ArrayList的每个项保存一个非终结符或终结符
					//所以,当ArrayList不为空时,表明该位置有产生式
					ArrayList list;
					if((list = predictiveTable.get(X).get(a))!=null) {
						//是产生式
						//书本:若产生式为X→x1x2···xn,按逆序即xn···x2x1入栈
						//将ArrayList中的内容都逆序入栈
						for(int i = list.size()-1;i>=0;i--) {
							String str=list.get(i).toString();
							if(!str.equals("")) {
								stack.push(str);	
							}
						}
					}else {
						//不是产生式
						System.out.println("出错,因文法无相应的产生式而出错");
						break;
					}
				}
			}
		}
		
		
	}
	/*																				表达式文法的预测分析表
	* 	非int的类型	标识符	常量	结束符	(	)	,	;	{	}	=	!	[	]	#	+	-	*	/	<	<=	>	>=	!=	=='	void	main	if	else	while	do	for	switch	case	break	default	return	continue	int	&&	||	&	|
	*	<程序> 	<头文件><主要声明>			<头文件><主要声明>											<头文件><主要声明>																								<头文件><主要声明>				
	*	<头文件>	<>														#include'<'标识符.h'>'<头文件>											<>													<>				
	*	<主要声明>	<非int的类型> 标识符<新变量或函数><主要声明>			<>																						void 标识符'('<形参>')''{'<代码块>'}'<主要声明>													int <变量或函数或main>	<主要声明>			
	*	<类型>	char<类型>` double<类型>` float<类型>`																																						int<类型>`				
	*	<类型>`		<>											['']'<类型>`																														
	*	<新变量或函数>		<新变量>;					<新变量>;	<新变量>;			<新变量>;																																
	*	<变量或函数或main>		标识符<新变量或函数>																									main'('<形参>')''{'<代码块>'}'																
	*	<形参>	<类型>标识符<形参>`					<>																																	<类型>标识符<形参>`				
	*	<形参>`						<>	,<类型>标识符<形参>`																																				
	*	<代码块>	<类型>标识符<新变量><代码块>	<语句声明><代码块>	<语句声明><代码块>		<语句声明><代码块>					<>		<语句声明><代码块>					<语句声明><代码块>											<语句声明><代码块>		<语句声明><代码块>	<语句声明><代码块>	<语句声明><代码块>	<语句声明><代码块>		<语句声明><代码块>		<语句声明><代码块>	<语句声明><代码块>	<类型>标识符<新变量><代码块>				
	*	<新变量>							,标识符<新变量> 	<>			=常量<新变量>																																
	*	<新函数>					('<形参>')''{'<代码块>'}'																																						
	*	<语句声明>		<含标识符表达式或函数调用语句>	<表达式>';'		<表达式>';'							<表达式>';'					<表达式>';'											<条件语句>		<while循环语句>	<do语句>';'	<for语句>	<switch语句>		<break语句>		<return语句>	<continue>					
	*	<表达式>			常量<表达式>`		('<表达式>')'<表达式>`							!<表达式><表达式>`					-<表达式><表达式>`																										
	*	<表达式>`								<>			<算符><表达式><表达式>`					<算符><表达式><表达式>`	<算符><表达式><表达式>`	<算符><表达式><表达式>`	<算符><表达式><表达式>`	<算符><表达式><表达式>`	<算符><表达式><表达式>`	<算符><表达式><表达式>`	<算符><表达式><表达式>`	<算符><表达式><表达式>`	<算符><表达式><表达式>`															<算符><表达式><表达式>`	<算符><表达式><表达式>`	<算符><表达式><表达式>`	<算符><表达式><表达式>`
	*	<条件语句>																												if'('<条件>')''{<代码块>'}'<else语句>															
	*	<while循环语句>																														while'('<条件>')''{'<代码块>'}'													
	*	<do语句>																															do'{'<代码块>'}'while'('<条件>')'';'												
	*	<for语句>																																for'('<条件>';'<条件>';'<条件>')''{'<代码块>'}'											
	*	<switch语句>																																	switch'('<整数>')''{'<case语句>'}'										
	*	<return语句>																																					return<标识符或常量>; 						
	*	<break语句>																																			break;								
	*	<continue语句>																																						continue;					
	*	<含标识符表达式或函数调用语句>																																											
	*	<含标识符表达式或函数调用语句>`																																											
	*	<值表>		标识符<值表>`	常量<值表>`			<>																																					
	*	<值表>`						<>	,<标识符或常量><值表>`																																				
	*	<算符>											='					+	-	*	/	<	<=	>	>=	!=	=='															&&	||	&	|
	*	<条件>		标识符<表达式>`	<表达式>		<表达式>							<表达式>					<表达式>																										
	*	<else语句>	<>	<>	<>		<>					<>		<>					<>											<>	else'{'<代码块>'}' 	<>	<>	<>	<>		<>		<>	<>	<>				
	*	<case语句>										<>																								case <整数>:'{'<代码块>'}'<case语句>		default:'{'<代码块>'}'							
	*	<函数调用语句>		标识符'('<值表>')'';'																																									
	*	<标识符或常量>		标识符	常量																																								
	*/
	
}
