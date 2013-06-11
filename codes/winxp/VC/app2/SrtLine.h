#pragma once
#include <afx.h>

class CSrtLine :
	public CObject
{
public:
	DECLARE_SERIAL(CSrtLine)
	CSrtLine(void);
	~CSrtLine(void);

	int m_seq;
	CString m_times;
	CString m_subtitle;

	void Serialize( CArchive& archive );
};
