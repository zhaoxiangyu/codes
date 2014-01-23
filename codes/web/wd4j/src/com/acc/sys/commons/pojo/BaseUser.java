package com.acc.sys.commons.pojo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class BaseUser implements Serializable{
    /**
	 * 属性serialVersionUID的声明
	 */
	private static final long serialVersionUID = 2014945392589053441L;

	private String id;

    private String userAccount;

    private String userName;

    private String userPassword;

    private String userIdentity;

    private String userIdCard;

    private String userPhone;

    private String isEffective;

    private Date createTime;

    private Date modifyTime;

    private String orgId;

    private String chSpell;
    
    private String [] rolesId;
    
    private String orgCode;
    
    private String userSex;
    
    private String userAddress;

    public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * 取得rolesId(角色ID)的值
	 * @return rolesId
	 */
	public String[] getRolesId() {
		return rolesId;
	}

	/**
	 * 设置rolesId(角色ID)的值
	 * @param rolesId rolesId的值
	 */
	public void setRolesId(String[] rolesId) {
		this.rolesId = rolesId;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserIdentity() {
        return userIdentity;
    }

    public void setUserIdentity(String userIdentity) {
        this.userIdentity = userIdentity;
    }

    public String getUserIdCard() {
        return userIdCard;
    }

    public void setUserIdCard(String userIdCard) {
        this.userIdCard = userIdCard;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getIsEffective() {
        return isEffective;
    }

    public void setIsEffective(String isEffective) {
        this.isEffective = isEffective;
    }

    public Date getCreateTime() {
        return createTime;
    }


    /**
	 * 取得modifyTime(修改时间)的值
	 * @return modifyTime
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * 设置modifyTime(修改时间)的值
	 * @param modifyTime modifyTime的值
	 */
	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * 设置createTime(修改时间)的值
	 * @param createTime createTime的值
	 */
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getChSpell() {
        return chSpell;
    }

    public void setChSpell(String chSpell) {
        this.chSpell = chSpell;
    }

	public String getUserSex() {
		return userSex;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}
	
	
}