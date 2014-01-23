package com.acc.sys.commons.pojo;

import java.io.Serializable;

public class BaseRoleResource implements Serializable{
	/**
	 * 属性serialVersionUID的声明
	 */
	private static final long serialVersionUID = -3847560888608181768L;
	private String id;
	private String roleId;
	private String resourceId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
}
