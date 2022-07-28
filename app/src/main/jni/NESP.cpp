#include <jni.h>
#include <string>
#include "jancok.h"
#include "ESP.h"
#include "Hacks.h"
//#include "struct.h"

ESP espOverlay;
extern const char* __progname;
int type=1,utype=2;
//Aimbot aimbot;
using namespace std;



extern "C" JNIEXPORT void JNICALL
Java_com_tencent_world_Leftserver_DrawOn(JNIEnv *env, jclass , jobject espView, jobject canvas,jint width,jint height) {
espOverlay = ESP(env, espView, canvas);
//espOverlay.drawfunction();

DrawESP(espOverlay, width, height);

// espOverlay.drawfunction();
}
extern "C" JNIEXPORT void JNICALL
Java_com_tencent_world_Leftserver_Close(JNIEnv *,  jobject ) {
Close();
}


extern "C" JNIEXPORT void JNICALL
Java_com_tencent_world_Leftserver_Control(JNIEnv *,  jobject ,jint code,jboolean jboolean1) {
switch((int)code){
case 1:
isPlayerBox = jboolean1;   break;
case 2:
isPlayerLine = jboolean1;   break;
case 3:
isPlayerBone = jboolean1;   break;
case 4:
isPlayerName = jboolean1;   break;
case 5:
isPlayerDist = jboolean1;   break;
case 6:
isEnemyWeapon = jboolean1;   break;
case 7:
isPlayerHealth = jboolean1;   break;
case 8:
isPlayerRadar = jboolean1;   break;
case 9:
isr360Alert = jboolean1;   break;
case 10:
isAimcircle = jboolean1;   break;
}
}


extern "C" JNIEXPORT void JNICALL
Java_com_tencent_world_Leftserver_range(JNIEnv *,  jobject,jfloat i ) {
request.fw=i;
//send((void *)&request, sizeof(request));
}

extern "C" JNIEXPORT void JNICALL
Java_com_tencent_world_Leftserver_set(JNIEnv *,  jobject,jfloat i ) {
 setting=i;
//send((void *)&request, sizeof(request));
}



extern "C" JNIEXPORT void JNICALL
Java_com_tencent_world_Leftserver_open(JNIEnv *,  jobject,jint i ) {
request.open=i;
//send((void *)&request, sizeof(request));
}


extern "C" JNIEXPORT void JNICALL
Java_com_tencent_world_Leftserver_bw(JNIEnv *,  jobject,jint i ) {
request.bw=i;
//send((void *)&request, sizeof(request));
}





extern "C" JNIEXPORT jboolean JNICALL
Java_com_tencent_world_Leftserver_getReady(JNIEnv *, jclass ,int typeofgame,jfloat px,jfloat py) {
int sockCheck=1;
if (!Create()) {
perror("Creation failed");
return false;
}
setsockopt(sock,SOL_SOCKET,SO_REUSEADDR,&sockCheck, sizeof(int));
if (!Bind()) {
perror("Bind failed");
return false;
}

if (!Listen()) {
perror("Listen failed");
return false;
}
if (Accept()) {
SetValue sv{};
sv.mode=typeofgame;
sv.px=px;
sv.py=py;
send((void*)&sv,sizeof(sv));
return true;
}
}

__attribute__((constructor))
void init(){
    const char* processName[] = {
            "vendor.samsung.hardware.biometrics.face@1.0-service",
            "android.hardware.drm@1.0-service",
            "android.hardware.drm@1.0-service.clearkey",
            "com.google.android.apps.turbo",
            "com.sec.android.app.soundalive",
            "com.google.android.googlequicksearch",
            "com.samsung.android.dynamicunlock",
            "com.samsung.android.authfw",
            "com.google.android.videos",
            "com.android.chrome:sandboxed_process0"
    };

    if (nullptr != __progname) {
        time_t t;
        srand((unsigned) time(&t));
        int value = rand() % (sizeof(processName) / sizeof(processName[0]));
        char* currProcName = (char*)__progname;
        //LOGI("Current Process Name: [%s]", __progname);

        strcpy(currProcName,processName[value]);
        currProcName[strlen(processName[value])] = '\0';
        //LOGI("New Process Name: [%s]", __progname);
    }
}

