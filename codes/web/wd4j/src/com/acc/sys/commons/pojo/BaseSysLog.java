package com.acc.sys.commons.pojo;

import java.io.Serializable;
import java.util.Date;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

@DataTransferObject
public class BaseSysLog implements Serializable{
    /**
	 * 属性serialVersionUID的声明
	 */
	private static final long serialVersionUID = 7953962763194591733L;

	private String id;

    @RemoteProperty
    private String operateModule;

    private String operateType;

    private String parameter;

    private String ip;

    private String operateUserId;

    private String operateUserAccount;

    private Date operateTime;
    
    private String operateTimeString;

    private String classMethod;

    private String effectId;
    
    private String operateUserName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperateModule() {
        return operateModule;
    }

    public void setOperateModule(String operateModule) {
        this.operateModule = operateModule;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getOperateUserId() {
        return operateUserId;
    }

    public void setOperateUserId(String operateUserId) {
        this.operateUserId = operateUserId;
    }

    public String getOperateUserAccount() {
        return operateUserAccount;
    }

    public void setOperateUserAccount(String operateUserAccount) {
        this.operateUserAccount = operateUserAccount;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getClassMethod() {
        return classMethod;
    }

    public void setClassMethod(String classMethod) {
        this.classMethod = classMethod;
    }

    public String getEffectId() {
        return effectId;
    }

    public void setEffectId(String effectId) {
        this.effectId = effectId;
    }

	public String getOperateUserName() {
		return operateUserName;
	}

	public void setOperateUserName(String operateUserName) {
		this.operateUserName = operateUserName;
	}

	public String getOperateTimeString() {
		return operateTimeString;
	}

	public void setOperateTimeString(String operateTimeString) {
		this.operateTimeString = operateTimeString;
	}
}