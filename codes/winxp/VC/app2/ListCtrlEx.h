#pragma once

// CListCtrlEx

class ListDataRowItem{
public:
	ListDataRowItem(){;};
	ListDataRowItem(CString displayValue,void* value){
		m_displayValue = displayValue;
		m_value = value;
	};
	~ListDataRowItem(){;};
	CString m_displayValue;
	void* m_value;
};

class ListDataHeader
{
public:
	ListDataHeader(){;};
	ListDataHeader(CString name,int width){
		m_displayName = name;
		m_displayWidth = width;
	};
	~ListDataHeader(){;};
public:
	CString m_displayName;
	int m_displayWidth;
};

class CListCtrlEx : public CListCtrl
{
	DECLARE_DYNAMIC(CListCtrlEx)

public:
	CListCtrlEx();
	virtual ~CListCtrlEx();

protected:
	DECLARE_MESSAGE_MAP()

public:
	int initListHeader(CArray<ListDataHeader>& dataHearders);
	int initListRowData(CArray<CArray<ListDataRowItem>*>& listData);
public:
	afx_msg void OnLvnItemActivateListPortmapping(NMHDR *pNMHDR, LRESULT *pResult);
};


