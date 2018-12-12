package com.oracle.Chat.model;

import java.io.Serializable;

public class user implements Serializable{
	private String username;
	private String password;
	private String zhanghu;
	private String touxiangpath;
	private Boolean status=false;
	
	
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public user(String zhanghu) {
		super();
		this.zhanghu = zhanghu;
	}
	public user(String username, String password, String zhanghu, String touxiangpath) {
		super();
		this.username = username;
		this.password = password;
		this.zhanghu = zhanghu;
		this.touxiangpath = touxiangpath;
	}
	
	public String getZhanghu() {
		return zhanghu;
	}
	public void setZhanghu(String zhanghu) {
		this.zhanghu = zhanghu;
	}
	public user() {
		super();
	}
	public user(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	public user(String username, String password, String touxiangpath) {
		super();
		this.username = username;
		this.password = password;
		this.touxiangpath = touxiangpath;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTouxiangpath() {
		return touxiangpath;
	}
	public void setTouxiangpath(String touxiangpath) {
		this.touxiangpath = touxiangpath;
	}
	@Override
	public String toString() {
		return "user [username=" + username + ", password=" + password + ", zhanghu=" + zhanghu + ", touxiangpath="
				+ touxiangpath + "]";
	}
	
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((password == null) ? 0 : password.hashCode());
//		result = prime * result + ((touxiangpath == null) ? 0 : touxiangpath.hashCode());
//		result = prime * result + ((username == null) ? 0 : username.hashCode());
//		result = prime * result + ((zhanghu == null) ? 0 : zhanghu.hashCode());
//		return result;
//	}
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		user other = (user) obj;
//		if (password == null) {
//			if (other.password != null)
//				return false;
//		} else if (!password.equals(other.password))
//			return false;
//		if (touxiangpath == null) {
//			if (other.touxiangpath != null)
//				return false;
//		} else if (!touxiangpath.equals(other.touxiangpath))
//			return false;
//		if (username == null) {
//			if (other.username != null)
//				return false;
//		} else if (!username.equals(other.username))
//			return false;
//		if (zhanghu == null) {
//			if (other.zhanghu != null)
//				return false;
//		} else if (!zhanghu.equals(other.zhanghu))
//			return false;
//		return true;
//	}
	
}
