package bianyiyuanli;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Accidence {
	// ��ű����ֽṹ������
	//public static void main(String[] args) {
		// // getnextchar()��ȡ�ַ�����
		// // Character c;
		// // while((c= a.getnextchar())!=null) {
		// // System.out.print(c);
		// // }
		// // tokentype t;
		// while (true) {
		// //lookahead = this.gettoken();
		// if (lookahead == tokentype.ENDFILE) {
		// System.out.println(lookahead);
		// break;
		// }
		// if (lookahead == tokentype.ERROR) {
		// System.out.println(lookahead);
		// showErrorMassage();// �ʷ��������ִ���
		// break;
		// }
		// if (lookahead != null) {// Ϊ�˹���ע�ͣ���Ϊע�ͷ��ص��ǿ�
		// // if (t == tokentype.ID) {// ��ʱchtype=='a';
		// // System.out.println("(" + t + ",\'" + ident.get(ident.size() - 1) + "\')");
		// // } else if (chtype == '0' || chtype == '\'' || chtype == '\"') {
		// // System.out.println("(" + t + ",\'" + constnum.get(constnum.size() - 1) +
		// // "\')");
		// // } else {
		// // System.out.println(t);
		// // }
		//
		// System.out.println(lookahead);
		// }
		//
		// }
		
	//}

	/*
	 *  �﷨�����ı��ʣ��ж��ʷ�����ĳ���ַ����ó��ĵ��������Ƿ�Ϊ�ķ��ľ���
	 *  
	 *  ���ڸñ����ڴ������˼���õ��ķ�����
	 *  1.��Ҫ������c���Ե��﷨,ѡ���﷨�����ķ���
	 *  1.1ʹ���Զ����µķ�������,�ӿ�ʼ���ſ�ʼ,��ͼ�����һ�����Ŵ�,������ϴ���ʷ������ó��ķ��Ŵ�������бȽ�,
	 *  �����Ӧ������Ƚ���һ��,�ڱȽ������дʷ���������ķ��Ŵ������ܶ�Ӧ��ʱ,�����,�����ĳ�����Ŵ���Ӧ����,��ܾ���
	 *  2.��������ʹ�õ���LL(1)�﷨������,��ô��Ҫͨ��c�����﷨�������һ��c���Ե�LL(1)�ķ�
	 *  3.��ô��ôΪc�������LL(1)�ķ���
	 *  3.1���һ������c���Թ淶���ķ�������֤��LL(1)�ķ�
	 *  3.2������ȡ�󹫹�����
	 *  3.3������ݹ�
	 *  
	 *  c�����ķ�:
	 *  <����>	::= <ͷ�ļ�><��Ҫ����>				{#,����,void,������}
	 *  
	 *  <ͷ�ļ�>	::= #include'<'��ʶ��.h'>'<ͷ�ļ�>	{#}		//�������Ų�����'#',����tokentype.ENDFILE			
	 *  			|<>							{����,void}
	 *  
	 *  //<��������>	::=	<����> <�����ʶ��>;			  
	 *  //<�����ʶ��>	::= <�����ʶ��>,��ʶ��|��ʶ��|��ʶ��=����
	 *  //	��Ϊ��	<�����ʶ��> ::= ��ʶ��<�����ʶ��>`		{��ʶ��}
	 *  //						|��ʶ��=����<�����ʶ��>`		{��ʶ��}
	 *  //			��Ϊ��
	 *  //				<�����ʶ��> ::= ��ʶ��<�����ʶ��>``
	 *  //				<�����ʶ��>`` ::= <�����ʶ��>`				{',',��ʶ��,;}
	 *  //								|=����<�����ʶ��>`			{=}
	 *  //			<�����ʶ��>` 	::= ,��ʶ��<�����ʶ��>`			{,}
	 *  //								|��ʶ��=����<�����ʶ��>`		{��ʶ��}
	 *  //								|<>						{;}
	 *  
	 * 	//<����>	::=	int|char|double|float|<����>'['']'	
	 * 
	 * 		��Ϊ��	<����> ::= int<����>`					{int}
	 * 							|char<����>`				{char}
	 * 							|double<����>`			{double}
	 * 							|float<����>`				{float}
	 * 				<����>`::= '['']'<����>`				{'['}
	 * 							|<>						{��ʶ��}
	 * 
	 * 
	 * 	//<����>	::=	<��int����>��ʶ��'('<�β�>')''{'<�����>'}'<����>	{��int����}
	 * 	//			|void ��ʶ��'('<�β�>')''{'<�����>'}'<����>		{void}
	 * 	//			|int ��ʶ��'('<�β�>')''{'<�����>'}'<����>	
	 * 	//			|int main'('<�β�>')''{'<�����>'}'
	 * 	//				��Ϊ��
	 * 	//					<����>	::=	int <����>`									{int}
	 * 	//								|<��int����>��ʶ��'('<�β�>')''{'<�����>'}'<����>	{��int����}
	 * 	//								|void ��ʶ��'('<�β�>')''{'<�����>'}'<����>		{void}
	 *	// 					<����>`	::=	main'('<�β�>')''{'<�����>'}'					{main}
	 * 	//								|��ʶ��'('<�β�>')''{'<�����>'}'				{��ʶ��}	
	 * 
	 * <��Ҫ����>		::=	<��int������> ��ʶ��<�±�������><��Ҫ����>				{��int������}
	 * 					|int <����������main>	<��Ҫ����>						{int}
	 * 					|void ��ʶ��'('<�β�>')''{'<�����>'}'<��Ҫ����>		{void}
	 * 					|<>												{������}
	 * <�±�������>	::=	<�±���>;					{',',��ʶ��,;,=}
	 * 					|<�º���>					{'('}
	 *	<�±���> 	::= ,��ʶ��<�±���> 					{,}
	 *  			|=<����><�±���> 					{=}
	 *  			|<>								{;}
	 *  <����>	::= �ַ�����|��������|���㳣��|˫���ȳ���|�ַ�������
	 *  <�º���>		::=	'('<�β�>')''{'<�����>'}'		{'('}
	 *  
	 *  <����������main>	::=	��ʶ��<�±�������>					{��ʶ��}
	 *  				|main'('<�β�>')''{'<�����>'}'		{main}
	 *  
	 *	<�����>	::=	<�������><�����>					{'(',!,-,����,if,while,do,for,switch,return,break,continue,��ʶ��}
	 *				//|<��������><�����>
	 *				|<����>��ʶ��<�±���>;<�����>			{����}
	 *				|<>								{'}'}
	 *
	 *	<�������>	::=	<���ʽ>';'			{'(',!,��ʶ��,-,����}
	 *				|<�������>			{if}
	 *				|<whileѭ�����>		{while}
	 *				|<do���>';'			{do}
	 *				|<for���>			{for}
	 *				|<switch���>			{switch}
	 *				|<return���>			{return}
	 *				|<break���>			{break}
	 *				|<continue���>			{continue}
	 *				//|<����ʶ�����ʽ�����������>	{��ʶ��}
	 *				
	 *				//�ϲ�<���ʽ>��<�����������>,������ʶ��ʱ,����Ϊ��<����ʶ�����ʽ�����������>,
	 *				//��������ʶ�������'(',����Ϊ�Ǻ����������,
	 *				//�Ǳ������Ϊ�Ǳ��ʽ
	 *			//��Ϊ��<����ʶ�����ʽ�����������> ::=��ʶ��<���ʽ>`
	 *			//						|��ʶ��'('<ֵ��>')'';' 
	 *				��Ϊ��
	 *					<����ʶ�����ʽ�����������> ::=��ʶ��<����ʶ�����ʽ�����������>`	{��ʶ��}
	 *					<����ʶ�����ʽ�����������>`::=<���ʽ>`			{���,;,')'}
	 *										|'('<ֵ��>')'';'			{'('}		
	 *		
	 *	//<���ʽ>::='('<���ʽ>')'
	 *	//			|!<���ʽ>
	 *	//			|<���ʽ><���><���ʽ>
	 *	//			|-<���ʽ>
	 *	//			|��ʶ��
	 *	//			|����
	 *			��Ϊ��
	 *				<���ʽ> ::= '('<���ʽ>')'<���ʽ>`		{'('}
	 *						|!<���ʽ><���ʽ>`				{'!'}
	 *						|-<���ʽ><���ʽ>`				{'-'}
	 *						//|��ʶ��<����ʶ�����ʽ�����������>`
	 *						|<����><���ʽ>`					{����}
	 *						
	 *				<���ʽ>` ::= <���><���ʽ><���ʽ>`		{���}
	 *							|<>						{';'}
	 *				
	 *	
	 *
	 *	<���>::= +|-|*|/|%|<|<=|>|>=|==|!=|&&|'||'|=|&|'|'
	 *
	 *	//<�������>	::=	if'('<����>')''{'<�����>'}'
	 *	//			|if'('<����>')''{'<�����>'}'else'{'<�����>'}'
	 *			
	 *			��Ϊ��	
	 *				<�������> ::= if'('<����>')''{<�����>'}'<else���>		{if}
	 *
	 *				<else���>::= else'{'<�����>'}' 		{else}
	 *							|<>						{����,'(','!','-',����,if,while,do,for,switch,return,break,continue,��ʶ��,'}'} 
	 *
	 *	<����>	::=<���ʽ>				{'(','!','-',����}
	 *				|��ʶ��=<���ʽ>`		{��ʶ��}
	 *				|<����> ��ʶ��=<���ʽ>	{����}
	 *	<whileѭ�����> ::= while'('<����>')''{'<�����>'}'				{while}
	 *
	 *	<do���> ::= do'{'<�����>'}'while'('<����>')'';'				{do}
	 *
	 *	<for���>		::=for'('<����>';'<����>';'<����>')''{'<�����>'}'	{for}
	 *
	 *	<switch���>	::= switch'('<����>')''{'<case���>'}'				{switch}
	 *
	 *	<case���> ::= case <����>:'{'<�����>'}'<case���>		{case}
	 *				|default:'{'<�����>'}'					{default}
	 *				|<>										{'}'}
	 *
	 *	//<return���> ::= return ��ʶ��;
	 *	//				|return ����;
	 *
	 *			��Ϊ:
	 *				<return���> ::= return<��ʶ������>; 		{return}
	 *				<��ʶ������>	::= ��ʶ��					{��ʶ��}
	 *								|<����>						{����}
	 *
	 *
	 *	<break���>	::= break;								{break}
	 *
	 *	<continue���>	::= continue;						{continue}
	 *
	 *	<�����������>	::= ��ʶ��'('<ֵ��>')'';'					{��ʶ��}
	 *
	 *	<ֵ��> ::= ��ʶ��<ֵ��>`				{��ʶ��}
	 *				|<����><ֵ��>`				{����}
	 *				|<>						{')'}
	 *
	 *	//<ֵ��>` ::= ,��ʶ��<ֵ��>`				{','}
	 *	//			|,����<ֵ��>`				{','}
	 *	//			|<>						{')'}
	 *			
	 *		��ɣ�
	 *			<ֵ��>` ::= ,<��ʶ������><ֵ��>`	{','}
	 *						|<>					{')'}
	 *
	 *	<�β�> ::= <����>��ʶ��<�β�>`				{����}
	 *				|<>							{')'}
	 *
	 *	<�β�>`::=,<����>��ʶ��<�β�>`				{','}
	 *				|<>							{')'}
	 *	
	 */


	

	private static void showErrorMassage() {
		// TODO Auto-generated method stub
		System.out.println("��" + row + "��,��" + index + "�г�������");
	}

	Character ch = ' ';

	// ��ʶ����
	static ArrayList<String> ident = new ArrayList<>();
	// ��ʶ��ָʾ��
	// int idindex = 0;
	// ������
	static ArrayList<String> constnum = new ArrayList<>();
	// ����ָʾ��
	// int cnindex = 0;
	static int chtype;

	// �ִʺ���gettoken��ʵ��
	public tokentype gettoken() {
		tokentype currenttoken = null;
		// if (ch != null) {
		// while (ch == ' ' || ch == '\t' || ch == '\n') {
		// ch = getnextchar();// ��������
		// if(ch==null) {
		// break;
		// }
		// }
		// }
		// ������
		while ((ch != null) && (ch == ' ' || ch == '\t' || ch == '\n')) {
			ch = getnextchar();
		}
		// // �ַ�������
		// int chtype;
		if (ch == null) {
			chtype = -1;
		} else {
			chtype = ch;
		}

		if (('a' <= chtype && chtype <= 'z') || ('A' <= chtype && chtype <= 'Z') || chtype == '_') {
			chtype = 'a';
		} else if (chtype >= '0' && chtype <= '9') {
			chtype = '0';
		}
		switch (chtype) {
		// case 'a'�൱��case letter;
		case 'a':
			// ��ʼ��ʱ����������ĸ��ʼ�Ĵ�
			// �򽫺�������ַ���ȡ���������ӳ��ַ���

			String str = getidentifier();

			// ��ȡ���ַ������ж��ַ����Ǳ����ֻ��Ǳ�ʶ����������Ӧ�ĵ����ֱ�
			currenttoken = reserverlookup(str);
			if (currenttoken == tokentype.ID) {
				ident.add(str);
			}
			break;
		// case '0'�൱��case digit;
		case '0':
			// ��ʼ��ʱ����ʱ�������ֿ�ͷ�Ĵ�
			// ��ʹ�����º��������ֽ����ж��Ƿ���ϴʷ�����
			String num = getnumber();
			// ������ϴʷ���������ĺ����򲻷��ش����������е��������Ϊ�䵥���ֱ�Ϊ����
			// currenttoken = tokentype.NUM;//----�����Ѿ�����һ��һ�������������ö����������ʧЧ
			currenttoken = numreserverlookup(num);
			constnum.add(num);
			break;
		case '+':
			// ����������ַ�Ϊ'+'������Ϊ��ǰ�ĵ����ֱ�Ϊ�Ӻ�
			// currenttoken = tokentype.PLUS;
			// ʶ����֮��Ӧ��ȡ��һ���ַ�����ʶ��
			ch = getnextchar();
			if (ch == '+') {
				currenttoken = tokentype.INC;
				ch = getnextchar();
			} else if (ch == '=') {
				currenttoken = tokentype.PLUSE;
				ch = getnextchar();
			} else {
				currenttoken = tokentype.PLUS;
			}
			break;
		case '-':
			// currenttoken = tokentype.MINUS;
			ch = getnextchar();
			if (ch == '-') {
				currenttoken = tokentype.DEC;
				ch = getnextchar();
			} else if (ch == '=') {
				currenttoken = tokentype.MINUSE;
				ch = getnextchar();
			} else if (ch == '>') {
				currenttoken = tokentype.ARROW;
				ch = getnextchar();
			} else {
				currenttoken = tokentype.MINUS;
			}
			break;
		// ��֪����θ���ͷ�ļ����ͽ��к��滻,��ʱ����
		case '#':
			// index = line.length;
			currenttoken = tokentype.POUND;
			ch = getnextchar();
			break;
		case '*':
			// currenttoken = tokentype.TIMES;
			ch = getnextchar();
			if (ch == '=') {
				currenttoken = tokentype.TIMESE;
				ch = getnextchar();
			} else {
				currenttoken = tokentype.TIMES;
			}
			break;
		// �����Ե����ſ�ͷ�Ļ�˫���ſ�ͷ����Ϊ���������ݣ�����ԭ��37ҳ��3��
		// ���Ϊtokentype����ַ��������ַ����������������ֱ�
		case '\'':
			currenttoken = tokentype.CONSTCHAR;
			String chars = "";
			while ((ch = getnextchar()) != '\'') {
				chars += ch;
			}
			constnum.add(chars);
			ch = getnextchar();
			break;
		case '\"':
			currenttoken = tokentype.CONSTSTRING;
			String s = "";
			while ((ch = getnextchar()) != '\"') {
				s += ch;
			}
			constnum.add(s);
			ch = getnextchar();
			break;
		case '[':
			currenttoken = tokentype.MIDLPAREN;
			ch = getnextchar();
			break;
		case ']':
			currenttoken = tokentype.MIDRPAREN;
			ch = getnextchar();
			break;
		// �Է�б�ܽ��д���
		case '/':
			// ��Ϊ��б����������,���������б����Ϊ��ע��,���Ǻ���Ϊ��ע��������Ҫ�ٻ�ȡ��һ���ַ��������ж�
			ch = getnextchar();
			if (ch == '/') {
				// �����һ���ַ�Ϊ��б��,����˷�б�ܺ���������ַ�
				// ʹָʾ��ָ�򻺳��������,����ֱ�ӹ��˺����ַ�
				// System.out.println("������ע��");
				File file = new File("ע��.txt");
				// try {
				// OutputStream out = new FileOutputStream(file);
				// out.write(new String(line,index-1,line.length-index).getBytes());
				// } catch (FileNotFoundException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// } catch (IOException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				try {
					FileWriter w = new FileWriter(file, true);
					w.write(line, index - 1, line.length - index);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				index = line.length;
				// ���е��ַ��Ѿ�������ϣ�chӦȡ��һ���ַ����д���
				ch = getnextchar();
			} else if (ch == '*') {
				// �����һ���ַ�Ϊ*,��������ǿ�ע��,��Ҫ����
				// ���˵Ľ��������ǣ�ȡ��һ���ַ��ж��Ƿ�Ϊ*�ţ�����ǣ���ȡ��һ���ַ��ж��Ƿ�Ϊ/������������������������������ȡ�ַ�
				// System.out.println("ע�Ϳ鿪ʼ");
				Character charcurrent = getnextchar();
				while (true) {
					Character charnext = getnextchar();
					if (charcurrent == '*' && charnext == '/') {
						// System.out.println("ע�Ϳ����");
						// ��ǰ�ַ��Ѿ�������ϣ�Ӧָ��*/�ĺ�һ���ַ�
						ch = getnextchar();
						break;
					}
					charcurrent = charnext;
					if (charcurrent == null) {
						currenttoken = tokentype.ENDFILE;
						break;
					}
				}

			} else if (ch == '=') {
				currenttoken = tokentype.OVERE;
				ch = getnextchar();
			} else {
				currenttoken = tokentype.OVER;
			}
			break;
		case '=':
			ch = getnextchar();
			if (ch == '=') {
				currenttoken = tokentype.EQ;
				ch = getnextchar();
			} else {
				currenttoken = tokentype.ASSIGN;
			}
			break;
		case '<':
			ch = getnextchar();
			if (ch == '=') {
				currenttoken = tokentype.LTE;
				ch = getnextchar();
			} else if (ch == '<') {
				currenttoken = tokentype.LEFTSHIFT;
			} else {
				currenttoken = tokentype.LT;
			}
			break;
		case '>':
			ch = getnextchar();
			if (ch == '=') {
				currenttoken = tokentype.BGE;
				ch = getnextchar();
			} else if (ch == '>') {
				currenttoken = tokentype.RIGHTSHIFT;
			} else {
				currenttoken = tokentype.BG;
			}
			break;
		case '(':
			// ���ֵ�ǰ�ķ���Ϊ�����ţ���ֱ����Ϊ�����ֱ�Ϊ������
			currenttoken = tokentype.LPAREN;
			ch = getnextchar();
			break;
		case ')':
			currenttoken = tokentype.RPAREN;
			ch = getnextchar();
			break;
		case ';':
			currenttoken = tokentype.SEMI;
			ch = getnextchar();
			break;
		// c�����в�Ӧ�ô���ð��
		case ':':
			// // ���ֵ�ǰ����Ϊð�ţ�����ȡ��һ������
			// ch = getnextchar();
			// // �����ȡ���ķ���Ϊ�Ⱥţ�����Ϊ�䵥���ֱ�Ϊ��ֵ��
			// // ������Ϊ�ʷ�����
			// if (ch == '=') {
			// currenttoken = tokentype.ASSIGN;
			// // �������⣺��ʶ�������Ϊ��ֵ�ź�'='�Դ�����ch�����У��ٴ�ʶ��Ļ��ͻ����ASSIGN EQ�����
			// // ���жϵ���ǰ����Ϊ'='ʱ��Ӧȡ��һ���ַ����в����������������䣬����ֻ�����ASSIGN
			// ch = getnextchar();
			// } else {
			// currenttoken = tokentype.ERROR;
			// }
			// ��Ԫ���ʽ����
			currenttoken = tokentype.COLON;
			ch = getnextchar();
			break;
		case '{':
			// ������������ſ�ʼ����ȥ������ע��
			// skipcomment();û�б�Ҫ��������
			currenttoken = tokentype.LBRACE;
			ch = getnextchar();
			break;
		// ������������������򱨴��Ѿ�������default��
		case '}':
			currenttoken = tokentype.RBRACE;
			ch = getnextchar();
			break;
		// ����ȡ���ļ�ĩβʱ������Ϊ��ǰ�ļ��Ѿ����ֵ꣬����Ϊ-1
		case '.':
			currenttoken = tokentype.POINT;
			ch = getnextchar();
			if (!(('a' <= ch && ch <= 'z') || ('A' <= ch && ch <= 'Z'))) {
				currenttoken = tokentype.ERROR;
			}
			break;
		case '!':
			ch = getnextchar();
			if (ch == '=') {
				currenttoken = tokentype.NOTE;
				ch = getnextchar();
			} else {
				currenttoken = tokentype.NOT;
			}
			break;
		case '&':
			ch = getnextchar();
			if (ch == '&') {
				currenttoken = tokentype.AND;
				ch = getnextchar();
			} else if (ch == '=') {
				currenttoken = tokentype.BANDE;
				ch = getnextchar();
			} else {
				currenttoken = tokentype.BAND;
			}
			break;
		case '~':
			ch = getnextchar();
			currenttoken = tokentype.BNOT;
			break;
		case '%':
			ch = getnextchar();
			if (ch == '=') {
				currenttoken = tokentype.MODE;
				ch = getnextchar();
			} else {
				currenttoken = tokentype.MOD;
			}
			break;
		case '^':// c���������ݴη����㣿��������Ϊ��λ���
			ch = getnextchar();
			if (ch == '=') {
				currenttoken = tokentype.BXORE;
				ch = getnextchar();
			} else {
				currenttoken = tokentype.BXOR;
			}
			break;
		case '|':
			ch = getnextchar();
			if (ch == '=') {
				currenttoken = tokentype.BORE;
				ch = getnextchar();
			} else if (ch == '|') {
				currenttoken = tokentype.OR;
				ch = getnextchar();
			} else {
				currenttoken = tokentype.BOR;
			}
			break;
		case '?':
			currenttoken = tokentype.QMARK;
			ch = getnextchar();
			break;
		case ',':
			currenttoken = tokentype.COMMA;
			ch = getnextchar();
			break;
		case -1:
			currenttoken = tokentype.ENDFILE;
			break;
		default:
			currenttoken = tokentype.ERROR;
			break;
		}
		return currenttoken;
	}

	// ��鴫����������
	private tokentype numreserverlookup(String num) {
		// f��F�ĸ���ֻ��Ϊ1����0��
		int countf = 0;
		for (int i = 0; i < num.length(); i++) {
			if (num.charAt(i) == 'f' || num.charAt(i) == 'F') {
				countf++;
			}
		}
		if (!(countf == 1 || countf == 0)) {
			return tokentype.ERROR;
		}
		// �ж��ַ�������û��С���㣬�о���ʱ��Ϊ��С����û�о�ֱ����Ϊ������
		if (num.contains(".")) {
			int countpoint = 0;
			for (int i = 0; i < num.length(); i++) {
				if (num.charAt(i) == '.') {
					countpoint++;
				}
			}
			if (countpoint != 1) {
				return tokentype.ERROR;
			}

			if (num.charAt(num.length() - 1) == 'f' || num.charAt(num.length() - 1) == 'F') {
				if (num.charAt(num.length() - 2) == '.') {
					return tokentype.ERROR;
				}
				// ͳ��f�ĸ���

				// ��Ϊ���Ǹ�����
				return tokentype.CONSTFLOAT;
			} else {
				return tokentype.CONSTDOUBLE;
			}
		} else {// ������������
			if (num.charAt(num.length() - 1) == '.') {// num.charAt(0)=='.'�����ڵ��ڵ�һ��λ���������
				return tokentype.ERROR;
			}
			return tokentype.CONSTINT;
		}
	}

	private String getnumber() {
		// TODO Auto-generated method stub
		preindex = index - 1;
		while (ch <= '9' && ch >= '0' || ch == '.' || ch == 'f' || ch == 'F') {
			ch = getnextchar();
		}
		String str = new String(line, preindex, index - 1 - preindex);
		return str;
	}

	// TINY�ִʳ����������ĸ��������
	// 1 ȡ�ַ�����
	// �ο�����ԭ�� 44ҳ3.2��
	// ����getnextchar�Ļ�����line�������Թ��ո񣬶�ȡһ���ַ���
	// ÿ�ζ���Դ�ļ���һ�У�����line��������line��ȡ�պ��ٶ�һ�С�
	char[] line;
	static int index = 0;
	int preindex = 0;
	BufferedReader reader = null;
	// ����һ���м�¼����
	static int row = 0;

	public Character getnextchar() {
		// ���������ʲô��û�У����߻�����������ַ��Ѿ�ȡ��
		String str;
		if (line == null) {
			// ���Դ�ļ���һ�У�ת��Ϊ�ַ��������line��
			File file = new File("Դ�ļ�.txt");
			InputStream in = null;
			try {
				in = new FileInputStream(file);
				reader = new BufferedReader(new InputStreamReader(in));
				if ((str = reader.readLine()) != null) {
					line = str.toCharArray();
					row++;
				} else {
					// һ�ж�������Ҳ����null
					return null;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (index == line.length) {
			// �ַ�ȡ���ʱ��
			// �ٶ�һ��
			try {

				if ((str = reader.readLine()) != null) {
					line = str.toCharArray();
					index = 0;
					preindex = 0;
					row++;
				} else {
					// ���������з���null
					return null;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// �����ַ������г���Ϊ0ʱ����ʾ�������е�����һ�г��֣���������һ����Ϊ\n
		// ����readLine������ֱ�ӹ��˵�\n������ʹ��line[index++]��ȡ�ַ�ʱ���������Խ�������
		// ������line�ĳ���Ϊ0ʱ��Ӧ����һ������\n�������������˿��С�
		// ���ڷǿ����е�\n�򲻷��أ���Ϊ��û��ʲô���ã����滹��Ҫ�����˵�
		if (line.length == 0) {
			return '\n';
		}
		// ����ַ�û��ȡ���ȡһ��
		Character character = line[index++];
		return character;
	}

	// 2 ȡ��ʶ������
	public String getidentifier() {
		// String str = "";
		// do {
		// str += ch;
		// ch = getnextchar();
		// } while (((ch <= 'z' && ch >= 'a') || (ch <= 'Z' && ch >= 'A')) || (ch >= '0'
		// && ch <= '9'));
		// // ���ر�ʶ���ַ���
		// return str;
		// ÿ�ε���getnextchar(),index����ָ����һ���ַ������preindexӦָ��index-1���浱ǰch���ڵ�λ��,������ʶ����ַ���λ��Ϊindex-1
		preindex = index - 1;
		while (((ch <= 'z' && ch >= 'a') || (ch <= 'Z' && ch >= 'A')) || (ch >= '0' && ch <= '9') || ch == '_') {
			ch = getnextchar();// ��ȡ�ַ�
		}
		// ÿ�ε���getnextchar(),index����ָ����һ���ַ�,���index-1���ǵ�ǰ�ַ���λ��
		// ������ new String(char value[], int offset, int count)��countӦΪindex-1-preindex
		// ������Ѱ�ҵ����յ��ָʾ����index������preindex,��Ϊindex��һֱ��ǰ�����ģ��������ù̶�ָ��ʼ���ַ�λ��,����preindex����������
		// ���ԣ�����ָ��ʼ�ַ�λ��
		String str = new String(line, preindex, index - 1 - preindex);
		return str;
	}

	// 3 ���������ֺ���
	// ��ȡ����õı���������
	ReservedWord[] reservedword = ReservedWordFactory.getReservedWords();

	public tokentype reserverlookup(String str) {

		// ʹ���۰���Ҳ����Ƿ�Ϊ������;�ο������ݽṹ�̳̣���4�棩 ��� 251ҳ
		int low = 0;
		int high = reservedword.length - 1;
		int mid;
		while (low <= high) {
			mid = (low + high) / 2;
			if (reservedword[mid].str.equals(str)) {// ����1:ʹ��==����ʶ�����Ӧʹ��equals
				// ���ҵ��˸��ַ��������ڱ����ֵ��У���Ϊ��Ϊ�����֣������ظñ���������
				return reservedword[mid].tok;
			}
			if ((reservedword[mid].str).compareTo(str) > 0) {
				high = mid - 1;
			} else {
				low = mid + 1;
			}

		}
		// ��������δ�ҵ�������ڱ����ֵ��У�������Ϊ����ַ����Ǳ�ʶ�������Ǳ�����
		return tokentype.ID;
	}

	// 4 ��ȥע�ͺ���
	// public void skipcomment() {
	// while (ch != '}') {
	// ch = getnextchar();
	// if (ch == null) {
	// return;
	// }
	// }
	// ch = getnextchar();
	// }

}
