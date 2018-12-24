package com.zhao.crawler.util;

import java.io.Serializable;
 
 
/**
 * 描        述：eneity
 * 创建时间：2016-9-5
 * @author Jibaole
 */
public class User implements Serializable {
 
    private Integer id;
 
 
	private String userName;
 
    private String password;
 
    private Integer age;
    
    private String createTime;
    
 
    public User(String userName, String password, Integer age) {
        super();
		this.userName = userName;
		this.password = password;
		this.age = age;
	}
 
	public Integer getId() {
        return id;
    }
 
    public void setId(Integer id) {
        this.id = id;
    }
 
    public String getUserName() {
        return userName;
    }
 
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }
 
    public Integer getAge() {
        return age;
    }
 
    public void setAge(Integer age) {
        this.age = age;
    }
 
	public String getCreateTime() {
		return createTime;
	}
 
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
    
    
}
