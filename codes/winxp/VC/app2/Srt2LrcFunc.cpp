#include "StdAfx.h"
#include "afx.h"
#include "Srt2LrcFunc.h"

Srt2LrcFunc::Srt2LrcFunc(void)
{
}

Srt2LrcFunc::~Srt2LrcFunc(void)
{
}

static int toTxt(LPCTSTR lpszFileName,LPCTSTR lpszFileName2){
	
	CStdioFile srtFile(lpszFileName,CFile::modeCreate|CFile::modeReadWrite|CFile::typeText),
		txtFile(lpszFileName2,CFile::modeCreate|CFile::modeReadWrite|CFile::typeText);

	CString srtLine;
	while(srtFile.ReadString(srtLine)){
		if(srtLine.Find (_T("-->")) != -1){
			while(srtFile.ReadString(srtLine)){
				if(srtLine != _T("\n")){
					txtFile.WriteString (srtLine);
				}else{
					txtFile.WriteString(_T("----\n"));
					srtFile.ReadString (srtLine);
					break;
				}
			}
		}else{
			;
		}
	}

	srtFile.Close();
	txtFile.Close();
}