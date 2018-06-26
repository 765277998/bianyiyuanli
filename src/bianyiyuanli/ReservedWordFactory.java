package bianyiyuanli;

public class ReservedWordFactory {
	public static ReservedWord[] getReservedWords() {

		// String[] str = {"if","then","else","end","repeat","until","read","write"};
		// Arrays.sort(str);
		// for(int i=0;i<str.length;i++)
		// {
		// System.out.println(str[i]);
		// }
		// 保留字顺序:else end if read repeat then until write

		// ReservedWord[] reservedword = new ReservedWord[8];
		// reservedword[0] = new ReservedWord("else", tokentype.ELSE);
		// reservedword[1] = new ReservedWord("end", tokentype.END);
		// reservedword[2] = new ReservedWord("if", tokentype.IF);
		// reservedword[3] = new ReservedWord("read", tokentype.READ);
		// reservedword[4] = new ReservedWord("repeat", tokentype.REPEAT);
		// reservedword[5] = new ReservedWord("then", tokentype.THEN);
		// reservedword[6] = new ReservedWord("until", tokentype.UNTIL);
		// reservedword[7] = new ReservedWord("write", tokentype.WRITE);
		// 以上是TINY关键字
		//关键字的顺序为
		//auto,break,case,char,const,continue,default
		//do,double,else,enum,extern,float,for,goto,
		//if,int,long,register,return,short,signed,
		//sizeof,static,struct,switch,typedef,union,
		//unsigned,void,volatile,while
		ReservedWord[] reservedword = new ReservedWord[35];
		reservedword[0]=new ReservedWord("auto",tokentype.AUTO);
		reservedword[1]=new ReservedWord("break",tokentype.BREAK);
		reservedword[2]=new ReservedWord("case",tokentype.CASE);
		reservedword[3]=new ReservedWord("char",tokentype.CHAR);
		reservedword[4]=new ReservedWord("const",tokentype.CONST);
		reservedword[5]=new ReservedWord("continue",tokentype.CONTINUE);
		reservedword[6]=new ReservedWord("default",tokentype.DEFAULT);
		reservedword[7]=new ReservedWord("define",tokentype.DEFINE);
		reservedword[8]=new ReservedWord("do",tokentype.DO);
		reservedword[9]=new ReservedWord("double",tokentype.DOUBLE);
		reservedword[10]=new ReservedWord("else",tokentype.ELSE);
		reservedword[11]=new ReservedWord("enum",tokentype.ENUM);
		reservedword[12]=new ReservedWord("extern",tokentype.EXTERN);
		reservedword[13]=new ReservedWord("float",tokentype.FLOAT);
		reservedword[14]=new ReservedWord("for",tokentype.FOR);
		reservedword[15]=new ReservedWord("goto",tokentype.GOTO);
		reservedword[16]=new ReservedWord("if",tokentype.IF);
		reservedword[17]=new ReservedWord("include",tokentype.INCLUDE);
		reservedword[18]=new ReservedWord("int",tokentype.INT);
		reservedword[19]=new ReservedWord("long",tokentype.LONG);
		reservedword[20]=new ReservedWord("main",tokentype.MAIN);
		reservedword[21]=new ReservedWord("register",tokentype.REGISTER);
		reservedword[22]=new ReservedWord("return",tokentype.RETURN);
		reservedword[23]=new ReservedWord("short",tokentype.SHORT);
		reservedword[24]=new ReservedWord("signed",tokentype.SIGNED);
		reservedword[25]=new ReservedWord("sizeof",tokentype.SIZEOF);
		reservedword[26]=new ReservedWord("static",tokentype.STATIC);
		reservedword[27]=new ReservedWord("struct",tokentype.STRUCT);
		reservedword[28]=new ReservedWord("switch",tokentype.SWITCH);
		reservedword[29]=new ReservedWord("typedef",tokentype.TYPEDEF);
		reservedword[30]=new ReservedWord("union",tokentype.UNION);//unsigned,void,volatile,while
		reservedword[31]=new ReservedWord("unsigned",tokentype.UNSIGNED);
		reservedword[32]=new ReservedWord("void",tokentype.VOID);
		reservedword[33]=new ReservedWord("volatitile",tokentype.VOLATILE);
		reservedword[34]=new ReservedWord("while",tokentype.WHILE);
		return reservedword;
	}
}
