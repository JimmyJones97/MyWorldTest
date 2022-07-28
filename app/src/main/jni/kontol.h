#ifndef KONTOL_H
#define KONTOL_H

#include <jni.h>
#include <string>
#include <cstdlib>
#include <unistd.h>
#include <sys/mman.h>
#include <android/log.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>
#include <cerrno>
#include <sys/un.h>
#include <cstring>
#include <string>
#include <cmath>


bool isPlayerBox = false;
bool isPlayerLine = false;
bool isPlayerDist = false;
bool isPlayerHealth = false;
bool isPlayerName = false;
bool isPlayerBone = false;
bool isr360Alert = false;
bool isVehicle = false;
bool isEnemyWeapon= false;
bool isPlayerRadar = false;
bool isAimcircle = false;
bool isAimbot = false;

//bool isCameraTracking = false;


#endif //KONTOL_H
