/* DO NOT EDIT THIS FILE - it is machine generated */

#ifndef __java_io_File__
#define __java_io_File__

#include "jni.h"

#ifdef __cplusplus
extern "C"
{
#endif

extern jboolean Java_java_io_File_createInternal (JNIEnv *env, jclass, jstring);
extern jboolean Java_java_io_File_canReadInternal (JNIEnv *env, jobject, jstring);
extern jboolean Java_java_io_File_canWriteInternal (JNIEnv *env, jobject, jstring);
extern jboolean Java_java_io_File_setReadOnlyInternal (JNIEnv *env, jobject, jstring);
extern jboolean Java_java_io_File_existsInternal (JNIEnv *env, jobject, jstring);
extern jboolean Java_java_io_File_isFileInternal (JNIEnv *env, jobject, jstring);
extern jboolean Java_java_io_File_isDirectoryInternal (JNIEnv *env, jobject, jstring);
extern jlong Java_java_io_File_lengthInternal (JNIEnv *env, jobject, jstring);
extern jlong Java_java_io_File_lastModifiedInternal (JNIEnv *env, jobject, jstring);
extern jboolean Java_java_io_File_setLastModifiedInternal (JNIEnv *env, jobject, jstring, jlong);
extern jboolean Java_java_io_File_deleteInternal (JNIEnv *env, jobject, jstring);
extern jboolean Java_java_io_File_mkdirInternal (JNIEnv *env, jobject, jstring);
extern jboolean Java_java_io_File_renameToInternal (JNIEnv *env, jobject, jstring, jstring);
extern jobjectArray Java_java_io_File_listInternal (JNIEnv *env, jobject, jstring);

#ifdef __cplusplus
}
#endif

#endif /* __java_io_File__ */
