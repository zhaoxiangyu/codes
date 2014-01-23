package com.acc.sys.commons.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class BaseDicValue implements Serializable{
    /**
	 * 属性serialVersionUID的声明
	 */
	private static final long serialVersionUID = 7142093558033524010L;

	private String id;

    private String dicTypeId;

    private String dicValueName;

    private String dicValueCode;

    private String isEffective;

    private BigDecimal displayOrder;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;

    private String chSpell;

    private String parentId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDicTypeId() {
        return dicTypeId;
    }

    public void setDicTypeId(String dicTypeId) {
        this.dicTypeId = dicTypeId;
    }

    public String getDicValueName() {
        return dicValueName;
    }

    public void setDicValueName(String dicValueName) {
        this.dicValueName = dicValueName;
    }


    public String getDicValueCode() {
        return dicValueCode;
    }

    public void setDicValueCode(String dicValueCode) {
        this.dicValueCode = dicValueCode;
    }

    public String getIsEffective() {
        return isEffective;
    }

    public void setIsEffective(String isEffective) {
        this.isEffective = isEffective;
    }

    public BigDecimal getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(BigDecimal displayOrder) {
        this.displayOrder =displayOrder;
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

	public String getChSpell() {
        return chSpell;
    }

    public void setChSpell(String chSpell) {
        this.chSpell = chSpell;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}