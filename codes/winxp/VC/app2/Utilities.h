#pragma once
#include "PortMappingItem.h"

__declspec(dllexport) class CUtilities
{
public:
	CUtilities(void);
public:
	~CUtilities(void);
public:
	static int userAutoStart(CObject* obj);
public:
	static int openUpnpPort(void);
	static int openUpnpPort2(void);
};
