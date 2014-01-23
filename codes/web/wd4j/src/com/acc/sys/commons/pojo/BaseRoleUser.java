package com.acc.sys.commons.pojo;

import java.io.Serializable;

public class BaseRoleUser implements Serializable{
    /**
	 * 属性serialVersionUID的声明
	 */
	private static final long serialVersionUID = 8461435583222007774L;

	private String id;

    private String roleId;

    private String userId;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}