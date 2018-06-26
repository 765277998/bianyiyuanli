package bianyiyuanli;

/*
 * 保留字类型
 * */
public enum tokentype {
	ENDFILE, ERROR, // 结束文件、错误
	// 保留字
	//IF, THEN, ELSE, END, REPEAT, UNTIL, READ, WRITE, // 如果、然后、否则、结束、重复、直到、读、写
	//C语言保留字来自百度百科“保留字”
	
	
	//根据第3章PPT		3.5.2 单词表示和词法分析程序的实现
	
	//1.关键字可以将其全体视为一类，也可以一字一类。采用一字一类的方法
	//实际处理起来比较方便，所以以下关键字将采用一字一类的方法
	AUTO,DOUBLE,INT,STRUCT,BREAK,ELSE,LONG,SWITCH,CASE,ENUM,REGISTER,
	TYPEDEF,CHAR,EXTERN,RETURN,UNION,CONST,FLOAT,SHORT,UNSIGNED,CONTINUE,
	FOR,SIGNED,VOID,DEFAULT,GOTO,SIZEOF,VOLATILE,DO,WHILE,STATIC,IF,
	// 特殊符号
	PLUS, MINUS, TIMES, OVER, EQ, LT, LPAREN, RPAREN, SEMI, ASSIGN, // 加、减、乘、除、等于号、小于号、左括号、右括号、分号、赋值号
	//其他运算符%,++,--
	MOD,INC,DEC,
	// 其他
	//2.标识符一般统归一类
	ID,
	//NUM, // 数(1个或多个数字)、标识符(1个或多个字母)------弃用
	
	
	//3.常数则宜按类型（整型、实型、布尔型等）分类，为与保留字区别，并与相应的保留字同步，则在保留字前面添加CONST
	//根据百度百科《c语言》中的基本数据类型条目得
	//void,char,int,float,double,_Bool,_Complex,_Imaginary,_Generic
	//所以补充常量,void不存在常量所以不设置
	//常用
	CONSTCHAR,CONSTINT,CONSTFLOAT,CONSTDOUBLE,CONSTSTRING
	//不常用
	,CONST_BOOL,CONST_COMPLEX,CONST_IMAGINARY,_CONST_GENERIC,
	
	
	//4.运算符可采用一符一类的方法，但也可以把具有一定共性的运算符 (如所有关系运算)视为一类。
	//c语言中的关系运算符，在这里都是使用的一符一类的方法
	//其他关系运算符，大于号，大于等于，小于等于，不等于
	BG,BGE,LTE,NOTE
	//逻辑运算：与，或，非
	,AND,OR,NOT
	//位操作运算符，&，|，~，^，<<，>>
	,BAND,BOR,BNOT,BXOR,LEFTSHIFT,RIGHTSHIFT
	//其他赋值运算符+=,-=,*=,/=,%=和
	//复合位运算赋值&=,|=,^=,>>=,<<=
	,PLUSE,MINUSE,TIMESE,OVERE,MODE
	,BANDE,BORE,BONTE,LSE,RSE
	//条件运算符中的?与:
	,QMARK,COLON
	//逗号表达式中的逗号,
	,COMMA
	//指针运算符*和&
	
	
	//5.界符一般用一符一类的分法。
	//参考百度知道的“c语言中的界符有哪些？”
	//这里的界符都是采用一符一类的方法，而且关系运算符中也是一符一类的方法，所以界符尖括号<>，采用关系运算符中的大于BG小于LT表示
	//中括号[]，右箭头->，点运算.
	,MIDLPAREN,MIDRPAREN,ARROW,POINT
	//左右花括{}
	,LBRACE,RBRACE,MAIN
	
	//井号
	,POUND
	//include和define
	,INCLUDE,DEFINE
	//C语言的运算符号参考百度百科的“C语言运算符号”
	//补充,单引号,双引号
	,SQUOTES,DQUOTES
	,BXORE
}
