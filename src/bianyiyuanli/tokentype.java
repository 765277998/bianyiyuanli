package bianyiyuanli;

/*
 * ����������
 * */
public enum tokentype {
	ENDFILE, ERROR, // �����ļ�������
	// ������
	//IF, THEN, ELSE, END, REPEAT, UNTIL, READ, WRITE, // �����Ȼ�󡢷��򡢽������ظ���ֱ��������д
	//C���Ա��������԰ٶȰٿơ������֡�
	
	
	//���ݵ�3��PPT		3.5.2 ���ʱ�ʾ�ʹʷ����������ʵ��
	
	//1.�ؼ��ֿ��Խ���ȫ����Ϊһ�࣬Ҳ����һ��һ�ࡣ����һ��һ��ķ���
	//ʵ�ʴ��������ȽϷ��㣬�������¹ؼ��ֽ�����һ��һ��ķ���
	AUTO,DOUBLE,INT,STRUCT,BREAK,ELSE,LONG,SWITCH,CASE,ENUM,REGISTER,
	TYPEDEF,CHAR,EXTERN,RETURN,UNION,CONST,FLOAT,SHORT,UNSIGNED,CONTINUE,
	FOR,SIGNED,VOID,DEFAULT,GOTO,SIZEOF,VOLATILE,DO,WHILE,STATIC,IF,
	// �������
	PLUS, MINUS, TIMES, OVER, EQ, LT, LPAREN, RPAREN, SEMI, ASSIGN, // �ӡ������ˡ��������ںš�С�ںš������š������š��ֺš���ֵ��
	//���������%,++,--
	MOD,INC,DEC,
	// ����
	//2.��ʶ��һ��ͳ��һ��
	ID,
	//NUM, // ��(1����������)����ʶ��(1��������ĸ)------����
	
	
	//3.�������˰����ͣ����͡�ʵ�͡������͵ȣ����࣬Ϊ�뱣�������𣬲�����Ӧ�ı�����ͬ�������ڱ�����ǰ�����CONST
	//���ݰٶȰٿơ�c���ԡ��еĻ�������������Ŀ��
	//void,char,int,float,double,_Bool,_Complex,_Imaginary,_Generic
	//���Բ��䳣��,void�����ڳ������Բ�����
	//����
	CONSTCHAR,CONSTINT,CONSTFLOAT,CONSTDOUBLE,CONSTSTRING
	//������
	,CONST_BOOL,CONST_COMPLEX,CONST_IMAGINARY,_CONST_GENERIC,
	
	
	//4.������ɲ���һ��һ��ķ�������Ҳ���԰Ѿ���һ�����Ե������ (�����й�ϵ����)��Ϊһ�ࡣ
	//c�����еĹ�ϵ������������ﶼ��ʹ�õ�һ��һ��ķ���
	//������ϵ����������ںţ����ڵ��ڣ�С�ڵ��ڣ�������
	BG,BGE,LTE,NOTE
	//�߼����㣺�룬�򣬷�
	,AND,OR,NOT
	//λ�����������&��|��~��^��<<��>>
	,BAND,BOR,BNOT,BXOR,LEFTSHIFT,RIGHTSHIFT
	//������ֵ�����+=,-=,*=,/=,%=��
	//����λ���㸳ֵ&=,|=,^=,>>=,<<=
	,PLUSE,MINUSE,TIMESE,OVERE,MODE
	,BANDE,BORE,BONTE,LSE,RSE
	//����������е�?��:
	,QMARK,COLON
	//���ű��ʽ�еĶ���,
	,COMMA
	//ָ�������*��&
	
	
	//5.���һ����һ��һ��ķַ���
	//�ο��ٶ�֪���ġ�c�����еĽ������Щ����
	//����Ľ�����ǲ���һ��һ��ķ��������ҹ�ϵ�������Ҳ��һ��һ��ķ��������Խ��������<>�����ù�ϵ������еĴ���BGС��LT��ʾ
	//������[]���Ҽ�ͷ->��������.
	,MIDLPAREN,MIDRPAREN,ARROW,POINT
	//���һ���{}
	,LBRACE,RBRACE,MAIN
	
	//����
	,POUND
	//include��define
	,INCLUDE,DEFINE
	//C���Ե�������Ųο��ٶȰٿƵġ�C����������š�
	//����,������,˫����
	,SQUOTES,DQUOTES
	,BXORE
}
