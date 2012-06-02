#ifndef TYPE_H_INCLUDED
#define TYPE_H_INCLUDED

#include <string>

#define W
#undef	W

#ifdef W
	#define STRING wstring
	#define T(str) L##str
	#define TO_T(str) (wchar_t*)str
#else
	#define STRING string
	#define T(str) str
	#define TO_T(str) (char_t*)str
#endif

#define UINT usigned
#define INT int
#define SINT signed

#endif // TYPE_H_INCLUDED
