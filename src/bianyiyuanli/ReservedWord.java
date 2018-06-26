package bianyiyuanli;

import java.util.Arrays;

//存放保留字的结构reservedword

public class ReservedWord {
	// 单词自身的值
	public String str;
	// 单词中别
	public tokentype tok;

	public ReservedWord(String str, tokentype tok) {
		this.str = str;
		this.tok = tok;
	}

	public ReservedWord() {

	}
}
