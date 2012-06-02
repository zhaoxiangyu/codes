
#ifndef JACE_PROXY_SHARPX_JNI_TEST_H
#include "jace/proxy/sharpx/jni/Test.h"
#endif

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


/**
 * Headers for the classes that this class uses.
 *
 */
#ifndef JACE_TYPES_JVOID_H
#include "jace/proxy/types/JVoid.h"
#endif
#ifndef JACE_PROXY_JAVA_LANG_STRING_H
#include "jace/proxy/java/lang/String.h"
#endif
#ifndef JACE_TYPES_JINT_H
#include "jace/proxy/types/JInt.h"
#endif

BEGIN_NAMESPACE_4( jace, proxy, sharpx, jni )

/**
 * The Jace C++ proxy class source for sharpx/jni/Test.
 * Please do not edit this source, as any changes you make will be overwritten.
 * For more information, please refer to the Jace Developer's Guide.
 *
 */
#ifndef VIRTUAL_INHERITANCE_IS_BROKEN
  #define Test_INITIALIZER : ::jace::proxy::java::lang::Object( NO_OP )
#else
  #define Test_INITIALIZER
#endif

Test::Test() Test_INITIALIZER {
  ::jace::JArguments arguments;
  jobject localRef = newObject( arguments );
  setJavaJniObject( localRef );
  JNIEnv* env = ::jace::helper::attach();
  ::jace::helper::deleteLocalRef( env, localRef );
}

::jace::proxy::java::lang::String Test::testJniCpp( ::jace::proxy::types::JInt p0 ) {
  ::jace::JArguments arguments;
  arguments << p0;
  return ::jace::JMethod< ::jace::proxy::java::lang::String >( "testJniCpp" ).invoke( *this, arguments );
}

void Test::callBack( ::jace::proxy::java::lang::String p0 ) {
  ::jace::JArguments arguments;
  arguments << p0;
  ::jace::JMethod< ::jace::proxy::types::JVoid >( "callBack" ).invoke( *this, arguments );
}

/**
 * The following methods are required to integrate this class
 * with the Jace framework.
 *
 */
Test::Test( jvalue value ) Test_INITIALIZER {
  setJavaJniValue( value );
}

Test::Test( jobject object ) Test_INITIALIZER {
  setJavaJniObject( object );
}

Test::Test( const Test& object ) Test_INITIALIZER {
  setJavaJniObject( object.getJavaJniObject() );
}

Test::Test( const NoOp& noOp ) Test_INITIALIZER {
}

const JClass* Test::staticGetJavaJniClass() throw ( JNIException ) {
  static JClassImpl javaClass( "sharpx/jni/Test" );
  return &javaClass;
}

const JClass* Test::getJavaJniClass() const throw ( JNIException ) {
  return Test::staticGetJavaJniClass();
}

JEnlister< Test> Test::enlister;

END_NAMESPACE_4( jace, proxy, sharpx, jni )

BEGIN_NAMESPACE( jace )

#ifndef PUT_TSDS_IN_HEADER
  template <> inline ElementProxy< ::jace::proxy::sharpx::jni::Test>::ElementProxy( jarray array, jvalue element, int index ) : 
    ::jace::proxy::sharpx::jni::Test( element ), Object( NO_OP ), mIndex( index ) {
    JNIEnv* env = ::jace::helper::attach();
    parent = static_cast<jarray>( ::jace::helper::newGlobalRef( env, array ) );
  }
  template <> inline ElementProxy< ::jace::proxy::sharpx::jni::Test>::ElementProxy( const jace::ElementProxy< ::jace::proxy::sharpx::jni::Test>& proxy ) : 
    ::jace::proxy::sharpx::jni::Test( proxy.getJavaJniObject() ), Object( NO_OP ), mIndex( proxy.mIndex ) {
    JNIEnv* env = ::jace::helper::attach();
    parent = static_cast<jarray>( ::jace::helper::newGlobalRef( env, proxy.parent ) );
  }
#endif
#ifndef PUT_TSDS_IN_HEADER
  template <> inline JFieldProxy< ::jace::proxy::sharpx::jni::Test>::JFieldProxy( jfieldID fieldID_, jvalue value, jobject parent_ ) : 
    ::jace::proxy::sharpx::jni::Test( value ), Object( NO_OP ), fieldID( fieldID_ ) {
    JNIEnv* env = ::jace::helper::attach();

    if ( parent_ ) {
      parent = ::jace::helper::newGlobalRef( env, parent_ );
    }
    else {
      parent = parent_;
    }

    parentClass = 0;
  }
  template <> inline JFieldProxy< ::jace::proxy::sharpx::jni::Test>::JFieldProxy( jfieldID fieldID_, jvalue value, jclass parentClass_ ) : 
    ::jace::proxy::sharpx::jni::Test( value ), Object( NO_OP ), fieldID( fieldID_ ) {
    JNIEnv* env = ::jace::helper::attach();

    parent = 0;
    parentClass = static_cast<jclass>( ::jace::helper::newGlobalRef( env, parentClass_ ) );
  }
  template <> inline JFieldProxy< ::jace::proxy::sharpx::jni::Test>::JFieldProxy( const ::jace::JFieldProxy< ::jace::proxy::sharpx::jni::Test>& object ) : 
    ::jace::proxy::sharpx::jni::Test( object.getJavaJniValue() ), Object( NO_OP ) {

    fieldID = object.fieldID; 

    if ( object.parent ) {
      JNIEnv* env = ::jace::helper::attach();
      parent = ::jace::helper::newGlobalRef( env, object.parent );
    }
    else {
      parent = 0;
    }

    if ( object.parentClass ) {
      JNIEnv* env = ::jace::helper::attach();
      parentClass = static_cast<jclass>( ::jace::helper::newGlobalRef( env, object.parentClass ) );
    }
    else {
      parentClass = 0;
    }
  }
#endif

END_NAMESPACE( jace )

