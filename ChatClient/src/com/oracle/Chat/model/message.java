package com.oracle.Chat.model;

import java.io.Serializable;
import java.util.List;

public class message implements Serializable{
	private user from;
	private user to;
	private String content;
	private String time;
	private String type;
	private List<user> allUser;

	public List<user> getAllUser() {
		return allUser;
	}

	public void setAllUser(List<user> allUser) {
		this.allUser = allUser;
	}

	public message(user from, String type) {
		super();
		this.from = from;
		this.type = type;
	}

	public message() {
		super();
	}

	public message(String content) {
		super();
		this.content = content;
	}

	public message(user from, user to, String content, String time, String type) {
		super();
		this.from = from;
		this.to = to;
		this.content = content;
		this.time = time;
		this.type = type;
	}

	public user getFrom() {
		return from;
	}

	public void setFrom(user from) {
		this.from = from;
	}

	public user getTo() {
		return to;
	}

	public void setTo(user to) {
		this.to = to;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "message [from=" + from + ", to=" + to + ", content=" + content + ", time=" + time + ", type=" + type
				+ ", allUser=" + allUser + "]";
	}

}
