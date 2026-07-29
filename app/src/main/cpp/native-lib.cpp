
#include <jni.h>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_voiceai_translator_MainActivity_status(
        JNIEnv* env,
        jobject) {
    return env->NewStringUTF("Native layer ready");
}
