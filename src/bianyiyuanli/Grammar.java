package bianyiyuanli;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class Grammar {
	public static void main(String[] args) {
		Grammar grammar = new Grammar();
		grammar.predictiveParsing();
	}
	//���ݿα�93ҳ��дԤ���������
	//��Ҫһ��Ԥ�������//String[][] predictiveTable=new String[33][45];
	HashMap<String, HashMap<String,ArrayList<String>>> predictiveTable=new HashMap<>();
	
	public void createTable() {
//		ArrayList<String>[] arrayList = new ArrayList[10];
//		HashMap<String, ArrayList<String>> hashMap1= new HashMap<>();
//		arrayList[0].add("<ͷ�ļ�>");
//		arrayList[0].add("<��Ҫ˵��>");
//		hashMap1.put("char", arrayList1);
//		predictiveTable.put("<����>",hashMap1);
//		System.out.println(predictiveTable.get("<����>").get("1"));
		ArrayList<String>[] arrayLists = new ArrayList[200];
		HashMap<String,ArrayList<String>>[] hashMap = new HashMap[34];
		//<����>	::= <ͷ�ļ�><��Ҫ����>		{#,����,void,������}
		arrayLists[0]=new ArrayList<>();
		arrayLists[0].add("<ͷ�ļ�>");
		arrayLists[0].add("<��Ҫ����>");
		hashMap[0] = new HashMap<>();
		hashMap[0].put(tokentype.POUND.toString(), arrayLists[0]);
		hashMap[0].put(tokentype.INT.toString(), arrayLists[0]);
		hashMap[0].put(tokentype.CHAR.toString(), arrayLists[0]);
		hashMap[0].put(tokentype.FLOAT.toString(), arrayLists[0]);
		hashMap[0].put(tokentype.DOUBLE.toString(), arrayLists[0]);
		hashMap[0].put(tokentype.VOID.toString(), arrayLists[0]);
		hashMap[0].put(tokentype.ENDFILE.toString(), arrayLists[0]);
		predictiveTable.put("<����>", hashMap[0]);
		//<ͷ�ļ�>	::= #include'<'��ʶ��.h'>'<ͷ�ļ�>  {#}
		arrayLists[1]=new ArrayList<>();
		arrayLists[1].add(tokentype.POUND.toString());
		arrayLists[1].add(tokentype.INCLUDE.toString());
		arrayLists[1].add(tokentype.LT.toString());
		arrayLists[1].add(tokentype.ID.toString());
		arrayLists[1].add(tokentype.POINT.toString());
		arrayLists[1].add(tokentype.ID.toString());
		arrayLists[1].add(tokentype.BG.toString());
		arrayLists[1].add("<ͷ�ļ�>");
		hashMap[1] = new HashMap<>();
		hashMap[1].put(tokentype.POUND.toString(), arrayLists[1]);
		//|<>	  {����,void}
		arrayLists[2]=new ArrayList<>();
		arrayLists[2].add("");
		hashMap[1].put(tokentype.INT.toString(), arrayLists[2]);
		hashMap[1].put(tokentype.CHAR.toString(), arrayLists[2]);
		hashMap[1].put(tokentype.FLOAT.toString(), arrayLists[2]);
		hashMap[1].put(tokentype.DOUBLE.toString(), arrayLists[2]);
		hashMap[1].put(tokentype.VOID.toString(), arrayLists[2]);
		predictiveTable.put("<ͷ�ļ�>", hashMap[1]);
		//<����> ::= int<����>` {int}
		arrayLists[3]=new ArrayList<>();
		arrayLists[3].add(tokentype.INT.toString());
		arrayLists[3].add("<����>`");
		hashMap[2]=new HashMap<>();
		hashMap[2].put(tokentype.INT.toString(), arrayLists[3]);
		//|char<����>`		{char}
		arrayLists[4]=new ArrayList<>();
		arrayLists[4].add(tokentype.CHAR.toString());
		arrayLists[4].add("<����>`");
		hashMap[2].put(tokentype.CHAR.toString(), arrayLists[4]);
		//|double<����>`		{double}
		arrayLists[5]=new ArrayList<>();
		arrayLists[5].add(tokentype.DOUBLE.toString());
		arrayLists[5].add("<����>`");
		hashMap[2].put(tokentype.DOUBLE.toString(), arrayLists[5]);
		//|float<����>`		{float}	
		arrayLists[6]=new ArrayList<>();
		arrayLists[6].add(tokentype.FLOAT.toString());
		arrayLists[6].add("<����>`");
		hashMap[2].put(tokentype.FLOAT.toString(), arrayLists[6]);
		predictiveTable.put("<����>", hashMap[2]);
		//<����>`::= '['']'<����>` {'['}
		arrayLists[7]=new ArrayList<>();
		arrayLists[7].add(tokentype.MIDLPAREN.toString());
		arrayLists[7].add(tokentype.MIDRPAREN.toString());
		arrayLists[7].add("<����>`");
		hashMap[3] = new HashMap<>();
		hashMap[3].put(tokentype.MIDLPAREN.toString(), arrayLists[7]);
		//|<>  {��ʶ��}
		arrayLists[8]=new ArrayList<>();
		arrayLists[8].add("");
		hashMap[3].put(tokentype.ID.toString(), arrayLists[8]);
		predictiveTable.put("<����>`", hashMap[3]);
		//<��Ҫ����>		::=	<��int������> ��ʶ��<�±�������><��Ҫ����>
		arrayLists[9]=new ArrayList<>();
		arrayLists[9].add(tokentype.CHAR.toString());
		arrayLists[9].add(tokentype.ID.toString());
		arrayLists[9].add("<�±�������>");
		arrayLists[9].add("<��Ҫ����>");
		hashMap[4] = new HashMap<>();
		hashMap[4].put(tokentype.CHAR.toString(), arrayLists[9]);
		arrayLists[10]=new ArrayList<>();
		arrayLists[10].add(tokentype.DOUBLE.toString());
		arrayLists[10].add(tokentype.ID.toString());
		arrayLists[10].add("<�±�������>");
		arrayLists[10].add("<��Ҫ����>");
		hashMap[4].put(tokentype.DOUBLE.toString(), arrayLists[10]);
		arrayLists[11]=new ArrayList<>();
		arrayLists[11].add(tokentype.FLOAT.toString());
		arrayLists[11].add(tokentype.ID.toString());
		arrayLists[11].add("<�±�������>");
		arrayLists[11].add("<��Ҫ����>");
		hashMap[4].put(tokentype.FLOAT.toString(), arrayLists[11]);
		//|int <����������main>	<��Ҫ����>		{int}
		arrayLists[12]=new ArrayList<>();
		arrayLists[12].add(tokentype.INT.toString());
		arrayLists[12].add("<����������main>");
		arrayLists[12].add("<��Ҫ����>");
		hashMap[4].put(tokentype.INT.toString(), arrayLists[12]);
		//|void ��ʶ��'('<�β�>')''{'<�����>'}'<��Ҫ����>		{void}
		arrayLists[13]=new ArrayList<>();
		arrayLists[13].add(tokentype.VOID.toString());
		arrayLists[13].add(tokentype.ID.toString());
		arrayLists[13].add(tokentype.LPAREN.toString());
		arrayLists[13].add("<�β�>");
		arrayLists[13].add(tokentype.RPAREN.toString());
		arrayLists[13].add(tokentype.LBRACE.toString());
		arrayLists[13].add("<�����>");
		arrayLists[13].add(tokentype.RBRACE.toString());
		arrayLists[13].add("<��Ҫ����>");
		hashMap[4].put(tokentype.VOID.toString(), arrayLists[13]);
		//|<>	{������}
		arrayLists[14]=new ArrayList<>();
		arrayLists[14].add("");
		hashMap[4].put(tokentype.ENDFILE.toString(), arrayLists[14]);
		predictiveTable.put("<��Ҫ����>", hashMap[4]);
		//<�±�������>	::=	<�±���>;	  {',',��ʶ��,;,=}
		arrayLists[15]=new ArrayList<>();
		arrayLists[15].add("<�±���>");
		arrayLists[15].add(tokentype.SEMI.toString());
		hashMap[5] = new HashMap<>();
		hashMap[5].put(tokentype.COMMA.toString(), arrayLists[15]);
		hashMap[5].put(tokentype.ID.toString(), arrayLists[15]);
		hashMap[5].put(tokentype.SEMI.toString(), arrayLists[15]);
		hashMap[5].put(tokentype.ASSIGN.toString(), arrayLists[15]);
		//|<�º���>		{'('}
		arrayLists[16]=new ArrayList<>();
		arrayLists[16].add("<�º���>");
		hashMap[5].put(tokentype.LPAREN.toString(), arrayLists[16]);
		predictiveTable.put("<�±�������>", hashMap[5]);
		//<�±���> 	::= ,��ʶ��<�±���>  {,}
		arrayLists[17]=new ArrayList<>();
		arrayLists[17].add(tokentype.COMMA.toString());
		arrayLists[17].add(tokentype.ID.toString());
		arrayLists[17].add("<�±���>");
		hashMap[6] = new HashMap<>();
		hashMap[6].put(tokentype.COMMA.toString(), arrayLists[17]);
		//|=<����><�±���> 	{=}
		arrayLists[18]=new ArrayList<>();
		arrayLists[18].add(tokentype.ASSIGN.toString());
		arrayLists[18].add("<����>");
		arrayLists[18].add("<�±���>");
		hashMap[6].put(tokentype.ASSIGN.toString(), arrayLists[18]);
		//|<>		{;}
		arrayLists[19]=new ArrayList<>();
		arrayLists[19].add("");
		hashMap[6].put(tokentype.SEMI.toString(), arrayLists[19]);
		predictiveTable.put("<�±���>", hashMap[6]);
		//<����>	::= �ַ�����|��������|���㳣��|˫���ȳ���	|�ַ�������			
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
		predictiveTable.put("<����>", hashMap[7]);
		//<�º���>		::=	'('<�β�>')''{'<�����>'}'    {'('}
		arrayLists[24]=new ArrayList<>();
		arrayLists[24].add(tokentype.LPAREN.toString());
		arrayLists[24].add("<�β�>");
		arrayLists[24].add(tokentype.RPAREN.toString());
		arrayLists[24].add(tokentype.LBRACE.toString());
		arrayLists[24].add("<�����>");
		arrayLists[24].add(tokentype.RBRACE.toString());
		hashMap[8] = new HashMap<>();
		hashMap[8].put(tokentype.LPAREN.toString(), arrayLists[24]);
		predictiveTable.put("<�º���>", hashMap[8]);
		 //<����������main>	::=	��ʶ��<�±�������>	 {��ʶ��}
		arrayLists[25]=new ArrayList<>();
		arrayLists[25].add(tokentype.ID.toString());
		arrayLists[25].add("<�±�������>");
		hashMap[9] = new HashMap<>();
		hashMap[9].put(tokentype.ID.toString(), arrayLists[25]);
		//|main'('<�β�>')''{'<�����>'}'   {main}
		arrayLists[26]=new ArrayList<>();
		arrayLists[26].add(tokentype.MAIN.toString());
		arrayLists[26].add(tokentype.LPAREN.toString());
		arrayLists[26].add("<�β�>");
		arrayLists[26].add(tokentype.RPAREN.toString());
		arrayLists[26].add(tokentype.LBRACE.toString());
		arrayLists[26].add("<�����>");
		arrayLists[26].add(tokentype.RBRACE.toString());
		hashMap[9].put(tokentype.MAIN.toString(), arrayLists[26]);
		predictiveTable.put("<����������main>", hashMap[9]);
		//<�����>	::=	<�������><�����>    {'(',!,-,����,if,while,do,for,switch,return,break,continue,��ʶ��}
		arrayLists[27]=new ArrayList<>();
		arrayLists[27].add("<�������>");
		arrayLists[27].add("<�����>");
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
		//|<����>��ʶ��<�±���>;<�����>    {����}
		arrayLists[28]=new ArrayList<>();
		arrayLists[28].add("<����>");
		arrayLists[28].add(tokentype.ID.toString());
		arrayLists[28].add("<�±���>");
		arrayLists[28].add(tokentype.SEMI.toString());
		arrayLists[28].add("<�����>");
		hashMap[10].put(tokentype.INT.toString(), arrayLists[28]);
		hashMap[10].put(tokentype.CHAR.toString(), arrayLists[28]);
		hashMap[10].put(tokentype.DOUBLE.toString(), arrayLists[28]);
		hashMap[10].put(tokentype.FLOAT.toString(), arrayLists[28]);
		//|<>		{'}'}
		arrayLists[29]=new ArrayList<>();
		arrayLists[29].add("");
		hashMap[10].put(tokentype.RBRACE.toString(), arrayLists[29]);
		predictiveTable.put("<�����>", hashMap[10]);
		//<�������>	::=	<���ʽ>';'    {'(',!,-,����}
		arrayLists[30]=new ArrayList<>();
		arrayLists[30].add("<���ʽ>");
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
		//|<�������>	    {if}
		arrayLists[31]=new ArrayList<>();
		arrayLists[31].add("<�������>");
		hashMap[11].put(tokentype.IF.toString(), arrayLists[31]);
		//|<whileѭ�����>  {while}
		arrayLists[32]=new ArrayList<>();
		arrayLists[32].add("<whileѭ�����>");
		hashMap[11].put(tokentype.WHILE.toString(), arrayLists[32]);
		//|<do���>';'   {do}
		arrayLists[33]=new ArrayList<>();
		arrayLists[33].add("<do���>");
		arrayLists[33].add(tokentype.SEMI.toString());
		hashMap[11].put(tokentype.DO.toString(), arrayLists[33]);
		//|<for���>   {for}
		arrayLists[34]=new ArrayList<>();
		arrayLists[34].add("<for���>");
		hashMap[11].put(tokentype.FOR.toString(), arrayLists[34]);
		//|<switch���>	{switch}
		arrayLists[35]=new ArrayList<>();
		arrayLists[35].add("<switch���>");
		hashMap[11].put(tokentype.SWITCH.toString(), arrayLists[35]);
		//|<return���>		{return}	
		arrayLists[36]=new ArrayList<>();
		arrayLists[36].add("<return���>");
		hashMap[11].put(tokentype.RETURN.toString(), arrayLists[36]);
		//|<break���>	{break}
		arrayLists[37]=new ArrayList<>();
		arrayLists[37].add("<break���>");
		hashMap[11].put(tokentype.BREAK.toString(), arrayLists[37]);
		//|<continue���>		{continue}
		arrayLists[38]=new ArrayList<>();
		arrayLists[38].add("<continue���>");
		hashMap[11].put(tokentype.CONTINUE.toString(), arrayLists[38]);
		//|<����ʶ�����ʽ�����������>		{��ʶ��}
		arrayLists[39]=new ArrayList<>();
		arrayLists[39].add("<����ʶ�����ʽ�����������>");
		hashMap[11].put(tokentype.ID.toString(), arrayLists[39]);
		predictiveTable.put("<�������>", hashMap[11]);
		//<����ʶ�����ʽ�����������> ::=��ʶ��<����ʶ�����ʽ�����������>`		{��ʶ��}
		arrayLists[40]=new ArrayList<>();
		arrayLists[40].add(tokentype.ID.toString());
		arrayLists[40].add("<����ʶ�����ʽ�����������>`");
		hashMap[12]=new HashMap<>();
		hashMap[12].put(tokentype.ID.toString(), arrayLists[40]);
		predictiveTable.put("<����ʶ�����ʽ�����������>", hashMap[12]);
		//<����ʶ�����ʽ�����������>`::=<���ʽ>`		{���,;,)}
		arrayLists[41]=new ArrayList<>();
		arrayLists[41].add("<���ʽ>`");
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
		//|'('<ֵ��>')'';'		{'('}
		arrayLists[42]=new ArrayList<>();
		arrayLists[42].add(tokentype.LPAREN.toString());
		arrayLists[42].add("<ֵ��>");
		arrayLists[42].add(tokentype.RPAREN.toString());
		arrayLists[42].add(tokentype.SEMI.toString());
		hashMap[13].put(tokentype.LPAREN.toString(), arrayLists[42]);
		predictiveTable.put("<����ʶ�����ʽ�����������>`", hashMap[13]);
		//<���ʽ> ::= '('<���ʽ>')'<���ʽ>`	{'('}
		arrayLists[43]=new ArrayList<>();
		arrayLists[43].add(tokentype.LPAREN.toString());
		arrayLists[43].add("<���ʽ>");
		arrayLists[43].add(tokentype.RPAREN.toString());
		arrayLists[43].add("<���ʽ>`");
		hashMap[14] = new HashMap<>();
		hashMap[14].put(tokentype.LPAREN.toString(),arrayLists[43]);
		//|!<���ʽ><���ʽ>`				{'!'}
		arrayLists[44]=new ArrayList<>();
		arrayLists[44].add(tokentype.NOT.toString());
		arrayLists[44].add("<���ʽ>");
		arrayLists[44].add("<���ʽ>`");
		hashMap[14].put(tokentype.LPAREN.toString(),arrayLists[44]);
		//|-<���ʽ><���ʽ>`				{'-'}
		arrayLists[45]=new ArrayList<>();
		arrayLists[45].add(tokentype.MINUS.toString());
		arrayLists[45].add("<���ʽ>");
		arrayLists[45].add("<���ʽ>`");
		hashMap[14].put(tokentype.MINUS.toString(),arrayLists[45]);
		//|<����><���ʽ>`					{����}={INT,CHAR,DOUBLE,FLOAT}
		arrayLists[46]=new ArrayList<>();
		arrayLists[46].add("<����>");
		arrayLists[46].add("<���ʽ>`");
		hashMap[14].put(tokentype.CONSTINT.toString(),arrayLists[46]);
		hashMap[14].put(tokentype.CONSTCHAR.toString(),arrayLists[46]);
		hashMap[14].put(tokentype.CONSTDOUBLE.toString(),arrayLists[46]);
		hashMap[14].put(tokentype.CONSTFLOAT.toString(),arrayLists[46]);
		predictiveTable.put("<���ʽ>", hashMap[14]);
		//<���ʽ>` ::= <���><���ʽ><���ʽ>`	{���}=+|-|*|/|%|<|<=|>|>=|==|!=|&&|'||'|=|&|'|'
		arrayLists[47]=new ArrayList<>();
		arrayLists[47].add("<���>");
		arrayLists[47].add("<���ʽ>");
		arrayLists[47].add("<���ʽ>`");
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
		predictiveTable.put("<���ʽ>`", hashMap[15]);
		//<���>::= +|-|*|/|%|<|<=|>|>=|==|!=|&&|'||'|=|&|'|'		
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
		predictiveTable.put("<���>", hashMap[16]);
		//<�������> ::= if'('<����>')''{<�����>'}'<else���>	{if}			
		arrayLists[80] = new ArrayList<>();
		arrayLists[80].add(tokentype.IF.toString());
		arrayLists[80].add(tokentype.LPAREN.toString());
		arrayLists[80].add("<����>");
		arrayLists[80].add(tokentype.RPAREN.toString());
		arrayLists[80].add(tokentype.LBRACE.toString());
		arrayLists[80].add("<�����>");
		arrayLists[80].add(tokentype.RBRACE.toString());
		arrayLists[80].add("<else���>");
		hashMap[17] = new HashMap<>();
		hashMap[17].put(tokentype.IF.toString(),arrayLists[80]);
		predictiveTable.put("<�������>", hashMap[17]);
		//<else���>::= else'{'<�����>'}'		{else}
		arrayLists[81] = new ArrayList<>();
		arrayLists[81].add(tokentype.ELSE.toString());
		arrayLists[81].add(tokentype.LBRACE.toString());
		arrayLists[81].add("<�����>");
		arrayLists[81].add(tokentype.RBRACE.toString());
		hashMap[18] = new HashMap<>();
		hashMap[18].put(tokentype.ELSE.toString(),arrayLists[81]);
		//|<>			{����,'(','!','-',����,if,while,do,for,switch,return,break,continue,��ʶ��,'}'} 
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
		
		predictiveTable.put("<else���>", hashMap[18]);
		//<����>	::=<���ʽ>		{'(','!','-',����}
		arrayLists[83] = new ArrayList<>();
		arrayLists[83].add("<���ʽ>");
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
		//|��ʶ��<���ʽ>`			{��ʶ��}
		arrayLists[84] = new ArrayList<>();
		arrayLists[84].add(tokentype.ID.toString());
		arrayLists[84].add("<���ʽ>`");
		hashMap[19].put(tokentype.ID.toString(),arrayLists[84]);
		//|<����> ��ʶ��=<���ʽ>	{����}
		arrayLists[109]=new ArrayList<>();
		arrayLists[109].add(tokentype.INT.toString());
		arrayLists[109].add(tokentype.ID.toString());
		arrayLists[109].add(tokentype.ASSIGN.toString());
		arrayLists[109].add("<���ʽ>");
		hashMap[19].put(tokentype.INT.toString(),arrayLists[109]);
		arrayLists[110]=new ArrayList<>();
		arrayLists[110].add(tokentype.CHAR.toString());
		arrayLists[110].add(tokentype.ID.toString());
		arrayLists[110].add(tokentype.ASSIGN.toString());
		arrayLists[110].add("<���ʽ>");
		hashMap[19].put(tokentype.CHAR.toString(),arrayLists[110]);
		arrayLists[111]=new ArrayList<>();
		arrayLists[111].add(tokentype.FLOAT.toString());
		arrayLists[111].add(tokentype.ID.toString());
		arrayLists[111].add(tokentype.ASSIGN.toString());
		arrayLists[111].add("<���ʽ>");
		hashMap[19].put(tokentype.FLOAT.toString(),arrayLists[111]);
		arrayLists[112]=new ArrayList<>();
		arrayLists[112].add(tokentype.DOUBLE.toString());
		arrayLists[112].add(tokentype.ID.toString());
		arrayLists[112].add(tokentype.ASSIGN.toString());
		arrayLists[112].add("<���ʽ>");
		hashMap[19].put(tokentype.DOUBLE.toString(),arrayLists[112]);
		predictiveTable.put("<����>", hashMap[19]);
		//<whileѭ�����> ::= while'('<����>')''{'<�����>'}'		{while}
		arrayLists[85] = new ArrayList<>();
		arrayLists[85].add(tokentype.WHILE.toString());
		arrayLists[85].add(tokentype.LPAREN.toString());
		arrayLists[85].add("<����>");
		arrayLists[85].add(tokentype.RPAREN.toString());
		arrayLists[85].add(tokentype.LBRACE.toString());
		arrayLists[85].add("<�����>");
		arrayLists[85].add(tokentype.RBRACE.toString());
		hashMap[20] = new HashMap<>();
		hashMap[20].put(tokentype.WHILE.toString(),arrayLists[85]);
		predictiveTable.put("<whileѭ�����>", hashMap[20]);
		//<do���> ::= do'{'<�����>'}'while'('<����>')'';'			{do}
		arrayLists[86]=new ArrayList<>();
		arrayLists[86].add(tokentype.DO.toString());
		arrayLists[86].add(tokentype.LBRACE.toString());
		arrayLists[86].add("<�����>");
		arrayLists[86].add(tokentype.RBRACE.toString());
		arrayLists[86].add(tokentype.WHILE.toString());
		arrayLists[86].add(tokentype.LPAREN.toString());
		arrayLists[86].add("<����>");
		arrayLists[86].add(tokentype.RPAREN.toString());
		arrayLists[86].add(tokentype.SEMI.toString());
		hashMap[21] = new HashMap<>();
		hashMap[21].put(tokentype.DO.toString(),arrayLists[86]);
		predictiveTable.put("<do���>", hashMap[21]);
		//<for���>		::=for'('<����>';'<����>';'<����>')''{'<�����>'}'	{for}
		arrayLists[87]=new ArrayList<>();
		arrayLists[87].add(tokentype.FOR.toString());
		arrayLists[87].add(tokentype.LPAREN.toString());
		arrayLists[87].add("<����>");
		arrayLists[87].add(tokentype.SEMI.toString());
		arrayLists[87].add("<����>");
		arrayLists[87].add(tokentype.SEMI.toString());
		arrayLists[87].add("<����ʶ�����ʽ�����������>");
		arrayLists[87].add(tokentype.RPAREN.toString());
		arrayLists[87].add(tokentype.LBRACE.toString());
		arrayLists[87].add("<�����>");
		arrayLists[87].add(tokentype.RBRACE.toString());
		hashMap[22] = new HashMap<>();
		hashMap[22].put(tokentype.FOR.toString(),arrayLists[87]);
		predictiveTable.put("<for���>", hashMap[22]);
		//<switch���>	::= switch'('<����>')''{'<case���>'}'			{switch}
		arrayLists[88] = new ArrayList<>();
		arrayLists[88].add(tokentype.SWITCH.toString());
		arrayLists[88].add(tokentype.LPAREN.toString());
		arrayLists[88].add("<����>");
		arrayLists[88].add(tokentype.RPAREN.toString());
		arrayLists[88].add(tokentype.LBRACE.toString());
		arrayLists[88].add("<case���>");
		arrayLists[88].add(tokentype.RBRACE.toString());
		hashMap[23] = new HashMap<>();
		hashMap[23].put(tokentype.SWITCH.toString(),arrayLists[88]);
		predictiveTable.put("<switch���>", hashMap[23]);
		//<case���> ::= case <����>:'{'<�����>'}'<case���>	{case}
		arrayLists[89] = new ArrayList<>();
		arrayLists[89].add(tokentype.CASE.toString());
		arrayLists[89].add(tokentype.CONSTINT.toString());
		arrayLists[89].add(tokentype.COLON.toString());
		arrayLists[89].add(tokentype.LBRACE.toString());
		arrayLists[89].add("<�����>");
		arrayLists[89].add(tokentype.RBRACE.toString());
		arrayLists[89].add("<case���>");
		hashMap[24] = new HashMap<>();
		hashMap[24].put(tokentype.CASE.toString(),arrayLists[89]);
		//|default:'{'<�����>'}'			{default}
		arrayLists[90] = new ArrayList<>();
		arrayLists[90].add(tokentype.DEFAULT.toString());
		arrayLists[90].add(tokentype.COLON.toString());
		arrayLists[90].add(tokentype.LBRACE.toString());
		arrayLists[90].add("<�����>");
		arrayLists[90].add(tokentype.RBRACE.toString());
		hashMap[24].put(tokentype.DEFAULT.toString(),arrayLists[90]);
		//|<>		{'}'}
		arrayLists[91] = new ArrayList<>();
		arrayLists[91].add("");
		hashMap[24].put(tokentype.RBRACE.toString(),arrayLists[91]);
		predictiveTable.put("<case���>", hashMap[24]);
		//<return���> ::= return<��ʶ������>; 	{return}
		arrayLists[92] = new ArrayList<>();
		arrayLists[92].add(tokentype.RETURN.toString());
		arrayLists[92].add("<��ʶ������>");
		hashMap[25] = new HashMap<>();
		hashMap[25].put(tokentype.RETURN.toString(),arrayLists[92]);
		predictiveTable.put("<return���>", hashMap[25]);
		//<��ʶ������>	::= ��ʶ��		{��ʶ��}
		arrayLists[93] = new ArrayList<>();
		arrayLists[93].add(tokentype.ID.toString());
		hashMap[26] = new HashMap<>();
		hashMap[26].put(tokentype.ID.toString(),arrayLists[93]);
		//|<����>				{����}={CONSTINT,CONSTDOUBLE,CONSTCHAR,CONSTFLOAT}
		arrayLists[94] = new ArrayList<>();
		arrayLists[94].add("<����>");
		hashMap[26].put(tokentype.CONSTINT.toString(),arrayLists[94]);
		hashMap[26].put(tokentype.CONSTDOUBLE.toString(),arrayLists[94]);
		hashMap[26].put(tokentype.CONSTCHAR.toString(),arrayLists[94]);
		hashMap[26].put(tokentype.CONSTFLOAT.toString(),arrayLists[94]);
		hashMap[26].put(tokentype.CONSTSTRING.toString(),arrayLists[94]);
		predictiveTable.put("<��ʶ������>", hashMap[26]);
		//<break���>	::= break;		{break}
		arrayLists[95] = new ArrayList<>();
		arrayLists[95].add(tokentype.BREAK.toString());
		arrayLists[95].add(tokentype.SEMI.toString());
		hashMap[27] = new HashMap<>();
		hashMap[27].put(tokentype.BREAK.toString(),arrayLists[95]);
		predictiveTable.put("<break���>", hashMap[27]);
		//<continue���>	::= continue;  {continue}
		arrayLists[96] = new ArrayList<>();
		arrayLists[96].add(tokentype.CONTINUE.toString());
		arrayLists[96].add(tokentype.SEMI.toString());
		hashMap[28] = new HashMap<>();
		hashMap[28].put(tokentype.CONTINUE.toString(),arrayLists[96]);
		predictiveTable.put("<continue���>", hashMap[28]);
		//<�����������>	::= ��ʶ��'('<ֵ��>')'';'		{��ʶ��}
		arrayLists[97]=new ArrayList<>();
		arrayLists[97].add(tokentype.ID.toString());
		arrayLists[97].add(tokentype.LPAREN.toString());
		arrayLists[97].add("<ֵ��>");
		arrayLists[97].add(tokentype.RPAREN.toString());
		arrayLists[97].add(tokentype.SEMI.toString());
		hashMap[29] = new HashMap<>();
		hashMap[29].put(tokentype.ID.toString(),arrayLists[97]);
		predictiveTable.put("<�����������>", hashMap[29]);
		//<ֵ��> ::= ��ʶ��<ֵ��>`		{��ʶ��}
		arrayLists[98] = new ArrayList<>();
		arrayLists[98].add(tokentype.ID.toString());
		arrayLists[98].add("<ֵ��>`");
		hashMap[30] = new HashMap<>();
		hashMap[30].put(tokentype.ID.toString(),arrayLists[98]);
		//|<����><ֵ��>`			{����}
		arrayLists[99]= new ArrayList<>();
		arrayLists[99].add("<����>");
		hashMap[30].put(tokentype.CONSTINT.toString(),arrayLists[99]);
		hashMap[30].put(tokentype.CONSTDOUBLE.toString(),arrayLists[99]);
		hashMap[30].put(tokentype.CONSTCHAR.toString(),arrayLists[99]);
		hashMap[30].put(tokentype.CONSTFLOAT.toString(),arrayLists[99]);
		hashMap[30].put(tokentype.CONSTSTRING.toString(),arrayLists[99]);;
		//|<>			{')'}
		arrayLists[100] = new ArrayList<>();
		arrayLists[100].add("");
		hashMap[30].put(tokentype.RPAREN.toString(),arrayLists[100]);
		predictiveTable.put("<ֵ��>", hashMap[30]);
		//<ֵ��>` ::= ,<��ʶ������><ֵ��>`	{','}
		arrayLists[101] = new ArrayList<>();
		arrayLists[101].add(tokentype.COMMA.toString());
		arrayLists[101].add("<��ʶ������>");
		arrayLists[101].add("<ֵ��>`");
		hashMap[31] = new HashMap<>();
		hashMap[31].put(tokentype.COMMA.toString(),arrayLists[101]);
		//|<>		{')'}
		arrayLists[102]= new ArrayList<>();
		arrayLists[102].add("");
		hashMap[31].put(tokentype.RPAREN.toString(),arrayLists[102]);
		predictiveTable.put("<ֵ��>`", hashMap[31]);
		//	<�β�> ::= <����>��ʶ��<�β�>`		{����}={INT,CHAR,DOUBLE,FLOAT}	
		arrayLists[103] = new ArrayList<>();
		arrayLists[103].add("<����>");
		arrayLists[103].add(tokentype.ID.toString());
		arrayLists[103].add("<�β�>`");
		hashMap[32] = new HashMap<>();
		hashMap[32].put(tokentype.INT.toString(),arrayLists[103]);
		hashMap[32].put(tokentype.CHAR.toString(),arrayLists[103]);
		hashMap[32].put(tokentype.DOUBLE.toString(),arrayLists[103]);
		hashMap[32].put(tokentype.FLOAT.toString(),arrayLists[103]);
		//|<>			{')'}
		arrayLists[104] = new ArrayList<>();
		arrayLists[104].add("");
		hashMap[32].put(tokentype.RPAREN.toString(),arrayLists[104]);
		predictiveTable.put("<�β�>", hashMap[32]);
		//<�β�>`::=,<����>��ʶ��<�β�>`		{','}
		arrayLists[105] = new ArrayList<>();
		arrayLists[105].add(tokentype.COMMA.toString());
		arrayLists[105].add("<����>");
		arrayLists[105].add(tokentype.ID.toString());
		arrayLists[105].add("<�β�>`");
		hashMap[33] = new HashMap<>();
		hashMap[33].put(tokentype.COMMA.toString(),arrayLists[105]);
		//|<>			{')'}
		arrayLists[106] = new ArrayList<>();
		arrayLists[106].add("");
		hashMap[33].put(tokentype.RPAREN.toString(),arrayLists[106]);
		predictiveTable.put("<�β�>`", hashMap[33]);
	}
	
	//��Ҫһ���ʷ���������Ķ���
	Accidence accidence = new Accidence();
	//��Ҫ�õ�ջ
	Stack<String> stack=new Stack<>();
	//��Ҫ����a���浱ǰ������ս��
	String a;

	//��Ҫһ��������һ�����ŵĺ���
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
	//�ж��Ƿ�Ϊ�ս���ĺ���
	public boolean isTernal(String X) {
		for(tokentype s:tokentype.values()) {
			if(s.toString().equals(X)) {
				return true;
			}
		}
		return false;
	}
	
	//��Ҫһ������ջ������ʱ�����ַ����ı���X
	String X;
	//���� 
	int count=0;
	private void predictiveParsing() {
		createTable();
		//���ݿα�93ҳ
		//'#'��'S'��ջ,������ֱ��Ӧtokentype.ENDFILE��<����>
		stack.push(tokentype.ENDFILE.toString());
		stack.push("<����>");
		a=getNexttokent();
		//System.out.println(tokentype.valueOf("aa"));
		String pipeide="";
	while(true) {
			count++;
			System.out.println("������"+count);
			System.out.print("����ջ��");
			if(!stack.isEmpty()) {
				for(String s:stack) {
				System.out.print(s+" ");
			}
			}
			System.out.println();
			System.out.println("��ǰ������ţ�"+a);
			X=stack.pop();
			if(!isTernal(X)) {
				System.out.println("����ʽ��"+predictiveTable.get(X).get(a));
			}else {
				System.out.println("ƥ�䣺"+X);
				pipeide+=X+" ";
			}
			System.out.println("�Ѿ�ƥ��Ĵ���"+pipeide);
			//X�����ս����
			//����tokentype�е���������,�鿴�Ƿ���һ����X��ȼ����ж�X�е������Ƿ�Ϊ�ս��
			if((!X.equals(tokentype.ENDFILE.toString()))&&isTernal(X)) {
				//�����ս��ʱ,�ж�X='a'?�ж�X�����е�������a�����е������Ƿ����
				//������,������һ������
				if(X.equals(a)) {
					a=getNexttokent();
				}else {
					//��������,�ͳ���,��Ϊ�������ķ��޷��Ƴ��÷��Ŵ������
					System.out.println("����,�������ķ��޷��Ƴ���ĳ�����ŵ����");
					break;
				}
			}else {//˵���������ս��
				//����Ϊ��X='#'?  �ڳ�������Ӧ�����ж�X�Ƿ����tokentype.ENDFILE
				if(X.equals(tokentype.ENDFILE.toString())) {
					//˵���ķ��Ѿ���ջ����,�ķ��Ѿ��޷�������������
					//��ʱ����Ҫ���ַ����Ƿ�Ҳ��ȫ������
					if(X.equals(a)) {
						//���X��a�б����������ͬ,˵���ַ���Ҳ������,���ķ�����Ӧ
						//˵��������������ķ��Ƴ���,���Խ��������
						System.out.println("����");
						break;
					}else {
						//���X����a�б�������ݲ�ͬ,˵���ַ�����û������,�ķ������Ƴ��ô�
						//���Ծܾ�
						System.out.println("����,�������ķ������Ƴ������ַ������");
						break;
					}
				}else {
					//���X��Ϊtokentype.ENDFILE˵���ķ����ܼ��������Ƶ�,����Ҫ��Ԥ������������һ������
					//�۲�Ԥ�������
					//M[X,a]�ǲ���ʽ��
					//�ڱ�������,Ӧ����ȡpredictiveTable��Ӧ���ս������ĳ���ս��ʱ�Ƿ�Ϊnull
					//predictive.get(���ս��)���Ի�ö�Ӧ���ս���Ĺ�ϣ��
					//predictive.get(���ս��).get(�ս��)���Ի�÷��ս��Ϊ��,�ս��Ϊ�е�λ�õĲ���ʽ
					//�������ʽ��һ��ArrayList,ArrayList�ĳ���Ϊ����ʽ���ŵĸ���
					//ArrayList��ÿ�����һ�����ս�����ս��
					//����,��ArrayList��Ϊ��ʱ,������λ���в���ʽ
					ArrayList list;
					if((list = predictiveTable.get(X).get(a))!=null) {
						//�ǲ���ʽ
						//�鱾:������ʽΪX��x1x2������xn,������xn������x2x1��ջ
						//��ArrayList�е����ݶ�������ջ
						for(int i = list.size()-1;i>=0;i--) {
							String str=list.get(i).toString();
							if(!str.equals("")) {
								stack.push(str);	
							}
						}
					}else {
						//���ǲ���ʽ
						System.out.println("����,���ķ�����Ӧ�Ĳ���ʽ������");
						break;
					}
				}
			}
		}
		
		
	}
	/*																				���ʽ�ķ���Ԥ�������
	* 	��int������	��ʶ��	����	������	(	)	,	;	{	}	=	!	[	]	#	+	-	*	/	<	<=	>	>=	!=	=='	void	main	if	else	while	do	for	switch	case	break	default	return	continue	int	&&	||	&	|
	*	<����> 	<ͷ�ļ�><��Ҫ����>			<ͷ�ļ�><��Ҫ����>											<ͷ�ļ�><��Ҫ����>																								<ͷ�ļ�><��Ҫ����>				
	*	<ͷ�ļ�>	<>														#include'<'��ʶ��.h'>'<ͷ�ļ�>											<>													<>				
	*	<��Ҫ����>	<��int������> ��ʶ��<�±�������><��Ҫ����>			<>																						void ��ʶ��'('<�β�>')''{'<�����>'}'<��Ҫ����>													int <����������main>	<��Ҫ����>			
	*	<����>	char<����>` double<����>` float<����>`																																						int<����>`				
	*	<����>`		<>											['']'<����>`																														
	*	<�±�������>		<�±���>;					<�±���>;	<�±���>;			<�±���>;																																
	*	<����������main>		��ʶ��<�±�������>																									main'('<�β�>')''{'<�����>'}'																
	*	<�β�>	<����>��ʶ��<�β�>`					<>																																	<����>��ʶ��<�β�>`				
	*	<�β�>`						<>	,<����>��ʶ��<�β�>`																																				
	*	<�����>	<����>��ʶ��<�±���><�����>	<�������><�����>	<�������><�����>		<�������><�����>					<>		<�������><�����>					<�������><�����>											<�������><�����>		<�������><�����>	<�������><�����>	<�������><�����>	<�������><�����>		<�������><�����>		<�������><�����>	<�������><�����>	<����>��ʶ��<�±���><�����>				
	*	<�±���>							,��ʶ��<�±���> 	<>			=����<�±���>																																
	*	<�º���>					('<�β�>')''{'<�����>'}'																																						
	*	<�������>		<����ʶ�����ʽ�����������>	<���ʽ>';'		<���ʽ>';'							<���ʽ>';'					<���ʽ>';'											<�������>		<whileѭ�����>	<do���>';'	<for���>	<switch���>		<break���>		<return���>	<continue>					
	*	<���ʽ>			����<���ʽ>`		('<���ʽ>')'<���ʽ>`							!<���ʽ><���ʽ>`					-<���ʽ><���ʽ>`																										
	*	<���ʽ>`								<>			<���><���ʽ><���ʽ>`					<���><���ʽ><���ʽ>`	<���><���ʽ><���ʽ>`	<���><���ʽ><���ʽ>`	<���><���ʽ><���ʽ>`	<���><���ʽ><���ʽ>`	<���><���ʽ><���ʽ>`	<���><���ʽ><���ʽ>`	<���><���ʽ><���ʽ>`	<���><���ʽ><���ʽ>`	<���><���ʽ><���ʽ>`															<���><���ʽ><���ʽ>`	<���><���ʽ><���ʽ>`	<���><���ʽ><���ʽ>`	<���><���ʽ><���ʽ>`
	*	<�������>																												if'('<����>')''{<�����>'}'<else���>															
	*	<whileѭ�����>																														while'('<����>')''{'<�����>'}'													
	*	<do���>																															do'{'<�����>'}'while'('<����>')'';'												
	*	<for���>																																for'('<����>';'<����>';'<����>')''{'<�����>'}'											
	*	<switch���>																																	switch'('<����>')''{'<case���>'}'										
	*	<return���>																																					return<��ʶ������>; 						
	*	<break���>																																			break;								
	*	<continue���>																																						continue;					
	*	<����ʶ�����ʽ�����������>																																											
	*	<����ʶ�����ʽ�����������>`																																											
	*	<ֵ��>		��ʶ��<ֵ��>`	����<ֵ��>`			<>																																					
	*	<ֵ��>`						<>	,<��ʶ������><ֵ��>`																																				
	*	<���>											='					+	-	*	/	<	<=	>	>=	!=	=='															&&	||	&	|
	*	<����>		��ʶ��<���ʽ>`	<���ʽ>		<���ʽ>							<���ʽ>					<���ʽ>																										
	*	<else���>	<>	<>	<>		<>					<>		<>					<>											<>	else'{'<�����>'}' 	<>	<>	<>	<>		<>		<>	<>	<>				
	*	<case���>										<>																								case <����>:'{'<�����>'}'<case���>		default:'{'<�����>'}'							
	*	<�����������>		��ʶ��'('<ֵ��>')'';'																																									
	*	<��ʶ������>		��ʶ��	����																																								
	*/
	
}
