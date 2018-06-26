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
	// 存放保留字结构的数组
	//public static void main(String[] args) {
		// // getnextchar()获取字符正常
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
		// showErrorMassage();// 词法分析发现错误
		// break;
		// }
		// if (lookahead != null) {// 为了过滤注释，因为注释返回的是空
		// // if (t == tokentype.ID) {// 此时chtype=='a';
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
	 *  语法分析的本质：判定词法分析某个字符串得出的单词序列是否为文法的句子
	 *  
	 *  基于该本质在此情况下思考得到的方法：
	 *  1.需要深刻理解c语言的语法,选择语法分析的方法
	 *  1.1使用自顶向下的分析方法,从开始符号开始,试图构造出一个符号串,这个符合串与词法分析得出的符号串逐个进行比较,
	 *  如果对应得上则比较下一个,在比较完所有词法分析输出的符号串并都能对应上时,则接受,如果有某个符号串对应不上,则拒绝。
	 *  2.由于这里使用的是LL(1)语法分析器,那么需要通过c语言语法规则设计一个c语言的LL(1)文法
	 *  3.那么怎么为c语言设计LL(1)文法？
	 *  3.1设计一个符合c语言规范的文法，不保证是LL(1)文法
	 *  3.2接着提取左公共因子
	 *  3.3消除左递归
	 *  
	 *  c语言文法:
	 *  <程序>	::= <头文件><主要声明>				{#,类型,void,结束符}
	 *  
	 *  <头文件>	::= #include'<'标识符.h'>'<头文件>	{#}		//结束符号不再是'#',而是tokentype.ENDFILE			
	 *  			|<>							{类型,void}
	 *  
	 *  //<变量声明>	::=	<类型> <多个标识符>;			  
	 *  //<多个标识符>	::= <多个标识符>,标识符|标识符|标识符=常量
	 *  //	变为：	<多个标识符> ::= 标识符<多个标识符>`		{标识符}
	 *  //						|标识符=常量<多个标识符>`		{标识符}
	 *  //			变为：
	 *  //				<多个标识符> ::= 标识符<多个标识符>``
	 *  //				<多个标识符>`` ::= <多个标识符>`				{',',标识符,;}
	 *  //								|=常量<多个标识符>`			{=}
	 *  //			<多个标识符>` 	::= ,标识符<多个标识符>`			{,}
	 *  //								|标识符=常量<多个标识符>`		{标识符}
	 *  //								|<>						{;}
	 *  
	 * 	//<类型>	::=	int|char|double|float|<类型>'['']'	
	 * 
	 * 		变为：	<类型> ::= int<类型>`					{int}
	 * 							|char<类型>`				{char}
	 * 							|double<类型>`			{double}
	 * 							|float<类型>`				{float}
	 * 				<类型>`::= '['']'<类型>`				{'['}
	 * 							|<>						{标识符}
	 * 
	 * 
	 * 	//<函数>	::=	<非int类型>标识符'('<形参>')''{'<代码块>'}'<函数>	{非int类型}
	 * 	//			|void 标识符'('<形参>')''{'<代码块>'}'<函数>		{void}
	 * 	//			|int 标识符'('<形参>')''{'<代码块>'}'<函数>	
	 * 	//			|int main'('<形参>')''{'<代码块>'}'
	 * 	//				变为：
	 * 	//					<函数>	::=	int <函数>`									{int}
	 * 	//								|<非int类型>标识符'('<形参>')''{'<代码块>'}'<函数>	{非int类型}
	 * 	//								|void 标识符'('<形参>')''{'<代码块>'}'<函数>		{void}
	 *	// 					<函数>`	::=	main'('<形参>')''{'<代码块>'}'					{main}
	 * 	//								|标识符'('<形参>')''{'<代码块>'}'				{标识符}	
	 * 
	 * <主要声明>		::=	<非int的类型> 标识符<新变量或函数><主要声明>				{非int的类型}
	 * 					|int <变量或函数或main>	<主要声明>						{int}
	 * 					|void 标识符'('<形参>')''{'<代码块>'}'<主要声明>		{void}
	 * 					|<>												{结束符}
	 * <新变量或函数>	::=	<新变量>;					{',',标识符,;,=}
	 * 					|<新函数>					{'('}
	 *	<新变量> 	::= ,标识符<新变量> 					{,}
	 *  			|=<常量><新变量> 					{=}
	 *  			|<>								{;}
	 *  <常量>	::= 字符常量|整数常量|浮点常量|双精度常量|字符串常量
	 *  <新函数>		::=	'('<形参>')''{'<代码块>'}'		{'('}
	 *  
	 *  <变量或函数或main>	::=	标识符<新变量或函数>					{标识符}
	 *  				|main'('<形参>')''{'<代码块>'}'		{main}
	 *  
	 *	<代码块>	::=	<语句声明><代码块>					{'(',!,-,常量,if,while,do,for,switch,return,break,continue,标识符}
	 *				//|<变量声明><代码块>
	 *				|<类型>标识符<新变量>;<代码块>			{类型}
	 *				|<>								{'}'}
	 *
	 *	<语句声明>	::=	<表达式>';'			{'(',!,标识符,-,常量}
	 *				|<条件语句>			{if}
	 *				|<while循环语句>		{while}
	 *				|<do语句>';'			{do}
	 *				|<for语句>			{for}
	 *				|<switch语句>			{switch}
	 *				|<return语句>			{return}
	 *				|<break语句>			{break}
	 *				|<continue语句>			{continue}
	 *				//|<含标识符表达式或函数调用语句>	{标识符}
	 *				
	 *				//合并<表达式>和<函数调用语句>,遇到标识符时,则认为是<含标识符表达式或函数调用语句>,
	 *				//接下来标识符后面跟'(',则认为是函数调用语句,
	 *				//是别的则认为是表达式
	 *			//变为：<含标识符表达式或函数调用语句> ::=标识符<表达式>`
	 *			//						|标识符'('<值表>')'';' 
	 *				变为：
	 *					<含标识符表达式或函数调用语句> ::=标识符<含标识符表达式或函数调用语句>`	{标识符}
	 *					<含标识符表达式或函数调用语句>`::=<表达式>`			{算符,;,')'}
	 *										|'('<值表>')'';'			{'('}		
	 *		
	 *	//<表达式>::='('<表达式>')'
	 *	//			|!<表达式>
	 *	//			|<表达式><算符><表达式>
	 *	//			|-<表达式>
	 *	//			|标识符
	 *	//			|常量
	 *			变为：
	 *				<表达式> ::= '('<表达式>')'<表达式>`		{'('}
	 *						|!<表达式><表达式>`				{'!'}
	 *						|-<表达式><表达式>`				{'-'}
	 *						//|标识符<含标识符表达式或函数调用语句>`
	 *						|<常量><表达式>`					{常量}
	 *						
	 *				<表达式>` ::= <算符><表达式><表达式>`		{算符}
	 *							|<>						{';'}
	 *				
	 *	
	 *
	 *	<算符>::= +|-|*|/|%|<|<=|>|>=|==|!=|&&|'||'|=|&|'|'
	 *
	 *	//<条件语句>	::=	if'('<条件>')''{'<代码块>'}'
	 *	//			|if'('<条件>')''{'<代码块>'}'else'{'<代码块>'}'
	 *			
	 *			变为：	
	 *				<条件语句> ::= if'('<条件>')''{<代码块>'}'<else语句>		{if}
	 *
	 *				<else语句>::= else'{'<代码块>'}' 		{else}
	 *							|<>						{类型,'(','!','-',常量,if,while,do,for,switch,return,break,continue,标识符,'}'} 
	 *
	 *	<条件>	::=<表达式>				{'(','!','-',常量}
	 *				|标识符=<表达式>`		{标识符}
	 *				|<类型> 标识符=<表达式>	{类型}
	 *	<while循环语句> ::= while'('<条件>')''{'<代码块>'}'				{while}
	 *
	 *	<do语句> ::= do'{'<代码块>'}'while'('<条件>')'';'				{do}
	 *
	 *	<for语句>		::=for'('<条件>';'<条件>';'<条件>')''{'<代码块>'}'	{for}
	 *
	 *	<switch语句>	::= switch'('<整数>')''{'<case语句>'}'				{switch}
	 *
	 *	<case语句> ::= case <整数>:'{'<代码块>'}'<case语句>		{case}
	 *				|default:'{'<代码块>'}'					{default}
	 *				|<>										{'}'}
	 *
	 *	//<return语句> ::= return 标识符;
	 *	//				|return 常量;
	 *
	 *			变为:
	 *				<return语句> ::= return<标识符或常量>; 		{return}
	 *				<标识符或常量>	::= 标识符					{标识符}
	 *								|<常量>						{常量}
	 *
	 *
	 *	<break语句>	::= break;								{break}
	 *
	 *	<continue语句>	::= continue;						{continue}
	 *
	 *	<函数调用语句>	::= 标识符'('<值表>')'';'					{标识符}
	 *
	 *	<值表> ::= 标识符<值表>`				{标识符}
	 *				|<常量><值表>`				{常量}
	 *				|<>						{')'}
	 *
	 *	//<值表>` ::= ,标识符<值表>`				{','}
	 *	//			|,常量<值表>`				{','}
	 *	//			|<>						{')'}
	 *			
	 *		变成：
	 *			<值表>` ::= ,<标识符或常量><值表>`	{','}
	 *						|<>					{')'}
	 *
	 *	<形参> ::= <类型>标识符<形参>`				{类型}
	 *				|<>							{')'}
	 *
	 *	<形参>`::=,<类型>标识符<形参>`				{','}
	 *				|<>							{')'}
	 *	
	 */


	

	private static void showErrorMassage() {
		// TODO Auto-generated method stub
		System.out.println("第" + row + "行,第" + index + "列出现问题");
	}

	Character ch = ' ';

	// 标识符表
	static ArrayList<String> ident = new ArrayList<>();
	// 标识符指示器
	// int idindex = 0;
	// 常量表
	static ArrayList<String> constnum = new ArrayList<>();
	// 常量指示器
	// int cnindex = 0;
	static int chtype;

	// 分词函数gettoken的实现
	public tokentype gettoken() {
		tokentype currenttoken = null;
		// if (ch != null) {
		// while (ch == ' ' || ch == '\t' || ch == '\n') {
		// ch = getnextchar();// 初步测试
		// if(ch==null) {
		// break;
		// }
		// }
		// }
		// 由上面
		while ((ch != null) && (ch == ' ' || ch == '\t' || ch == '\n')) {
			ch = getnextchar();
		}
		// // 字符的类型
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
		// case 'a'相当于case letter;
		case 'a':
			// 开始的时候发现是以字母开始的串
			// 则将后继所有字符获取出来，连接成字符串

			String str = getidentifier();

			// 获取完字符串后，判断字符串是保留字还是标识符并返回相应的单词种别
			currenttoken = reserverlookup(str);
			if (currenttoken == tokentype.ID) {
				ident.add(str);
			}
			break;
		// case '0'相当于case digit;
		case '0':
			// 开始的时候发现时候以数字开头的串
			// 则使用以下函数对数字进行判断是否符合词法规则
			String num = getnumber();
			// 如果符合词法规则，上面的函数则不返回错误，则能运行到这里，则认为其单词种别为数字
			// currenttoken = tokentype.NUM;//----常数已经采用一类一符，所以因弃用而保错，此语句失效
			currenttoken = numreserverlookup(num);
			constnum.add(num);
			break;
		case '+':
			// 发现输入的字符为'+'，则认为当前的单词种别为加号
			// currenttoken = tokentype.PLUS;
			// 识别完之后应获取下一个字符进行识别
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
		// 不知道如何复制头文件，和进行宏替换,暂时跳过
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
		// 发现以单引号开头的或双引号开头的视为常数，依据：编译原理37页（3）
		// 因此为tokentype添加字符常量和字符串常量两个单词种别
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
		// 对反斜杠进行处理
		case '/':
			// 因为反斜杠自身代表除,而后面跟反斜杠则为行注释,跟星号则为块注释所以需要再获取下一个字符来进行判断
			ch = getnextchar();
			if (ch == '/') {
				// 如果下一个字符为反斜杠,则过滤反斜杠后面的所有字符
				// 使指示器指向缓冲区的最后,表明直接过滤后续字符
				// System.out.println("出现行注释");
				File file = new File("注释.txt");
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
				// 该行的字符已经处理完毕，ch应取下一个字符进行处理
				ch = getnextchar();
			} else if (ch == '*') {
				// 如果下一个字符为*,则表明这是块注释,需要过滤
				// 过滤的结束条件是：取下一个字符判断是否为*号，如果是，再取下一个字符判断是否为/，如果是则结束，如果不是则继续向下取字符
				// System.out.println("注释块开始");
				Character charcurrent = getnextchar();
				while (true) {
					Character charnext = getnextchar();
					if (charcurrent == '*' && charnext == '/') {
						// System.out.println("注释块结束");
						// 当前字符已经处理完毕，应指向*/的后一个字符
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
			// 发现当前的符号为左括号，则直接认为单词种别为左括号
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
		// c语言中不应该存在冒号
		case ':':
			// // 发现当前符号为冒号，并获取下一个符号
			// ch = getnextchar();
			// // 如果获取到的符号为等号，则认为其单词种别为赋值号
			// // 否则，认为词法错误
			// if (ch == '=') {
			// currenttoken = tokentype.ASSIGN;
			// // 出现问题：在识别出符号为赋值号后，'='仍存在于ch变量中，再次识别的话就会出现ASSIGN EQ的情况
			// // 在判断到当前符号为'='时，应取下一个字符进行操作，故添加以下语句，所以只会出现ASSIGN
			// ch = getnextchar();
			// } else {
			// currenttoken = tokentype.ERROR;
			// }
			// 三元表达式必须
			currenttoken = tokentype.COLON;
			ch = getnextchar();
			break;
		case '{':
			// 发现以左大括号开始，则去除所有注释
			// skipcomment();没有必要继续存在
			currenttoken = tokentype.LBRACE;
			ch = getnextchar();
			break;
		// 如果单独出现右括号则报错，已经包含在default中
		case '}':
			currenttoken = tokentype.RBRACE;
			ch = getnextchar();
			break;
		// 当读取到文件末尾时，则认为当前文件已经读完，值设置为-1
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
		case '^':// c语言中无幂次方运算？？？则视为安位异或
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

	// 检查传入数字类型
	private tokentype numreserverlookup(String num) {
		// f或F的个数只能为1个或0个
		int countf = 0;
		for (int i = 0; i < num.length(); i++) {
			if (num.charAt(i) == 'f' || num.charAt(i) == 'F') {
				countf++;
			}
		}
		if (!(countf == 1 || countf == 0)) {
			return tokentype.ERROR;
		}
		// 判断字符串中有没有小数点，有就暂时认为是小数，没有就直接认为是整数
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
				// 统计f的个数

				// 认为它是浮点型
				return tokentype.CONSTFLOAT;
			} else {
				return tokentype.CONSTDOUBLE;
			}
		} else {// 不包含点的情况
			if (num.charAt(num.length() - 1) == '.') {// num.charAt(0)=='.'不存在点在第一个位置这种情况
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

	// TINY分词程序由以下四个函数组成
	// 1 取字符函数
	// 参考编译原理 44页3.2节
	// 函数getnextchar的缓冲区line，它能略过空格，读取一个字符，
	// 每次读入源文件的一行，存入line缓冲区，line被取空后再读一行。
	char[] line;
	static int index = 0;
	int preindex = 0;
	BufferedReader reader = null;
	// 定义一个行记录符号
	static int row = 0;

	public Character getnextchar() {
		// 如果缓冲区什么都没有，或者缓冲区里面的字符已经取完
		String str;
		if (line == null) {
			// 则读源文件的一行，转换为字符数组存入line中
			File file = new File("源文件.txt");
			InputStream in = null;
			try {
				in = new FileInputStream(file);
				reader = new BufferedReader(new InputStreamReader(in));
				if ((str = reader.readLine()) != null) {
					line = str.toCharArray();
					row++;
				} else {
					// 一行都读不到也返回null
					return null;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (index == line.length) {
			// 字符取完的时候
			// 再读一行
			try {

				if ((str = reader.readLine()) != null) {
					line = str.toCharArray();
					index = 0;
					preindex = 0;
					row++;
				} else {
					// 读完所有行返回null
					return null;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// 发现字符数组中长度为0时，表示代码中有单独的一行出现，而单独的一行则为\n
		// 由于readLine函数会直接过滤掉\n，导致使用line[index++]中取字符时会出现数组越界的问题
		// 所以在line的长度为0时，应返回一个空行\n表明现在遇到了空行。
		// 至于非空行中的\n则不返回，因为并没有什么作用，上面还是要被过滤掉
		if (line.length == 0) {
			return '\n';
		}
		// 如果字符没有取完就取一个
		Character character = line[index++];
		return character;
	}

	// 2 取标识符函数
	public String getidentifier() {
		// String str = "";
		// do {
		// str += ch;
		// ch = getnextchar();
		// } while (((ch <= 'z' && ch >= 'a') || (ch <= 'Z' && ch >= 'A')) || (ch >= '0'
		// && ch <= '9'));
		// // 返回标识符字符串
		// return str;
		// 每次调用getnextchar(),index都会指向下一个字符，因此preindex应指向index-1保存当前ch所在的位置,即正在识别的字符的位置为index-1
		preindex = index - 1;
		while (((ch <= 'z' && ch >= 'a') || (ch <= 'Z' && ch >= 'A')) || (ch >= '0' && ch <= '9') || ch == '_') {
			ch = getnextchar();// 获取字符
		}
		// 每次调用getnextchar(),index都会指向下一个字符,因此index-1才是当前字符的位置
		// 所以在 new String(char value[], int offset, int count)中count应为index-1-preindex
		// 在这里寻找单词终点的指示器是index而不是preindex,因为index是一直向前自增的，不能设置固定指向开始的字符位置,但是preindex不是自增的
		// 所以，可以指向开始字符位置
		String str = new String(line, preindex, index - 1 - preindex);
		return str;
	}

	// 3 检索保留字函数
	// 获取排序好的保留字数组
	ReservedWord[] reservedword = ReservedWordFactory.getReservedWords();

	public tokentype reserverlookup(String str) {

		// 使用折半查找查找是否为保留字;参考：数据结构教程（第4版） 李春葆 251页
		int low = 0;
		int high = reservedword.length - 1;
		int mid;
		while (low <= high) {
			mid = (low + high) / 2;
			if (reservedword[mid].str.equals(str)) {// 问题1:使用==产生识别错误，应使用equals
				// 查找到了该字符串存在于保留字当中，认为其为保留字，并返回该保留字类型
				return reservedword[mid].tok;
			}
			if ((reservedword[mid].str).compareTo(str) > 0) {
				high = mid - 1;
			} else {
				low = mid + 1;
			}

		}
		// 经过查找未找到其存在于保留字当中，于是认为这个字符串是标识符而不是保留字
		return tokentype.ID;
	}

	// 4 略去注释函数
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
