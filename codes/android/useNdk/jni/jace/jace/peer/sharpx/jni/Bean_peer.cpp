
/**
 * This is the source for the implementation of the Jace Peer for sharpx.jni.Bean.
 * Please do not edit this source. Any changes made will be overwritten.
 * 
 * For more information, please refer to the Jace Developer's Guide.
 *
 */

/**
 * Standard Jace headers needed to implement this class.
 *
 */
#ifndef JACE_JARGUMENTS_H
#include "jace/JArguments.h"
#endif
using jace::JArguments;

#ifndef JACE_JMETHOD_H
#include "jace/JMethod.h"
#endif
using jace::JMethod;

#ifndef JACE_JFIELD_H
#include "jace/JField.h"
#endif
using jace::JField;

#ifndef JACE_JCLASS_IMPL_H
#include "jace/JClassImpl.h"
#endif
using jace::JClassImpl;

#ifndef JACE_TYPES_JVOID_H
#include "jace/proxy/types/JVoid.h"
#endif
#ifndef JACE_PROXY_JAVA_LANG_STRING_H
#include "jace/proxy/java/lang/String.h"
#endif
#ifndef JACE_PROXY_SHARPX_JNI_BEAN_H
#include "jace/proxy/sharpx/jni/Bean.h"
#endif

#ifndef JACE_PEER_SHARPX_JNI_BEAN_H
#include "jace/peer/sharpx/jni/Bean.h"
#endif

BEGIN_NAMESPACE_4( jace, peer, sharpx, jni )

#ifndef VIRTUAL_INHERITANCE_IS_BROKEN
  #define Bean_INITIALIZER : ::jace::proxy::java::lang::Object( NO_OP ), ::jace::Peer( jPeer )
#else
  #define Bean_INITIALIZER : ::jace::Peer( jPeer )
#endif

::jace::proxy::java::lang::String Bean::getName() {
  ::jace::JArguments arguments;
  return ::jace::JMethod< ::jace::proxy::java::lang::String >( "getName" ).invoke( *this, arguments );
}

/**
 * public id
 *
 */
::jace::JFieldProxy< ::jace::proxy::types::JInt > Bean::id() {
  return ::jace::JField< ::jace::proxy::types::JInt >( "id" ).get( *this );
}

/**
 * The following methods are required to integrate this class
 * with the Jace framework.
 *
 */
Bean::Bean( jobject jPeer ) Bean_INITIALIZER {
  setJavaJniObject( jPeer );
}

Bean::~Bean() throw () {
}

const JClass* Bean::staticGetJavaJniClass() throw ( JNIException ) {
  static JClassImpl javaClass( "sharpx/jni/Bean" );
  return &javaClass;
}

const JClass* Bean::getJavaJniClass() const throw ( JNIException ) {
  return Bean::staticGetJavaJniClass();
}

END_NAMESPACE_4( jace, peer, sharpx, jni )

