
#ifndef JACE_PEER_SHARPX_JNI_BEAN_H
#define JACE_PEER_SHARPX_JNI_BEAN_H

#ifndef JACE_OS_DEP_H
#include "jace/os_dep.h"
#endif

#ifndef JACE_NAMESPACE_H
#include "jace/namespace.h"
#endif

#ifndef JACE_JOBJECT_H
#include "jace/proxy/JObject.h"
#endif

#ifndef JACE_JENLISTER_H
#include "jace/JEnlister.h"
#endif

#ifndef JACE_JARRAY_H
#include "jace/JArray.h"
#endif

#ifndef JACE_JFIELD_PROXY_H
#include "jace/JFieldProxy.h"
#endif


/**
 * The Peer class from which this class derives.
 *
 */
#include "jace/Peer.h"

/**
 * The super class for this class.
 *
 */
#ifndef JACE_PROXY_JAVA_LANG_OBJECT_H
#include "jace/proxy/java/lang/Object.h"
#endif

/**
 * Classes which this class is fully dependent upon.
 *
 */
#ifndef JACE_TYPES_JINT_H
#include "jace/proxy/types/JInt.h"
#endif


#ifndef JACE_PROXY_SHARPX_JNI_BEAN_H
#include "jace/proxy/sharpx/jni/Bean.h"
#endif

BEGIN_NAMESPACE_4( jace, peer, sharpx, jni )

/**
 * Bean
 * 
 * This header provides the declaration for the Jace Peer, Bean.
 * To complete this Peer, you must create a new source file containing the
 * definitions for all native methods declared for this Peer.
 * 
 * You may also override initialize() and destroy(), if your Peer requires
 * custom initialization or destruction.
 *
 */
class Bean : public ::jace::Peer, public virtual ::jace::proxy::java::lang::Object {

public: 

// Methods which must be implemented by the Developer
// --------------------------------------------------


// Methods made available by Jace
// ------------------------------

/**
 * getName
 *
 */
::jace::proxy::java::lang::String getName();

// Fields made available by Jace
// -----------------------------

/**
 * public id
 *
 */
::jace::JFieldProxy< ::jace::proxy::types::JInt > id();

// Methods internal to Jace
// ------------------------

/**
 * Called when the VM instantiates a new Bean.
 *
 */
Bean( jobject obj );

/**
 * Called when the the user explicitly collects a Bean
 * or when the VM garbage collects a Bean.
 *
 */
virtual ~Bean() throw ();

virtual const JClass* getJavaJniClass() const throw ( JNIException );
static const JClass* staticGetJavaJniClass() throw ( JNIException );

};

END_NAMESPACE_4( jace, peer, sharpx, jni )
#endif // #ifndef JACE_PEER_SHARPX_JNI_BEAN_H

