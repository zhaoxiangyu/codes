package com.acc.sys.commons.pojo;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class BaseDicType implements Serializable{
    /**
	 * 属性serialVersionUID的声明
	 */
	private static final long serialVersionUID = 5043650374799773515L;

	private String id;

    private String dicTypeCode;

    private String dicTypeName;

    private String dicTypeDesc;

    private String isEffective;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDicTypeCode() {
        return dicTypeCode;
    }

    public void setDicTypeCode(String dicTypeCode) {
        this.dicTypeCode = dicTypeCode;
    }

    public String getDicTypeName() {
        return dicTypeName;
    }

    public void setDicTypeName(String dicTypeName) {
        this.dicTypeName = dicTypeName;
    }

    public String getDicTypeDesc() {
        return dicTypeDesc;
    }

    public void setDicTypeDesc(String dicTypeDesc) {
        this.dicTypeDesc = dicTypeDesc;
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

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}