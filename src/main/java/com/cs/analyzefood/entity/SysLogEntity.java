package com.cs.analyzefood.entity;

import java.util.Date;
import lombok.Data;

@Data
public class SysLogEntity{

	private Long id;
	//用户名
	private String username;
	//用户操作
	private String operation;
	//请求方法
	private String method;
	//请求参数
	private String params;
	//执行时长(毫秒)
	private Long time;
	//创建时间
	private Date createDate;
}
