#include "stdafx.h"
#include "SrtLine.h"

CSrtLine::CSrtLine(void)
{
}

CSrtLine::~CSrtLine(void)
{
}

void CSrtLine::Serialize( CArchive& archive )
{
    // call base class function first
    // base class is CObject in this case
    CObject::Serialize( archive );

    // now do the stuff for our specific class
    if( archive.IsStoring() )
        archive << m_seq << m_times << m_subtitle;
    else
        archive >> m_seq >> m_times >> m_subtitle;

}
