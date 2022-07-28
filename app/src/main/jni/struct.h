#include <string>
#define maxplayerCount 100
#define maxvehicleCount 50
#define maxitemsCount 400
#define maxgrenadeCount 10
using namespace std;

class Color {
public:
    int colour;
    Color() {
        this->colour = 0;
    }
    Color(int colour) {
    this->colour = colour;
    }
};

struct Vector3A {
    float x, y, z;
};
class Vector2A {
        public:
        float x;
        float y;

        Vector2A() {
            this->x = 0;
            this->y = 0;
        }

        Vector2A(float x, float y) {
            this->x = x;
            this->y = y;
        }

        static Vector2A Zero() {
            return Vector2A(0.0f, 0.0f);
        }

        bool operator!=(const Vector2A &src) const {
            return (src.x != x) || (src.y != y);
        }

        Vector2A &operator+=(const Vector2A &v) {
            x += v.x;
            y += v.y;
            return *this;
        }

        Vector2A &operator-=(const Vector2A &v) {
            x -= v.x;
            y -= v.y;
            return *this;
        }
};

enum Mode {
	InitMode = 1,
	ESPMode = 2,
	HackMode = 3,
	StopMode = 4,
};

struct Request {
	int Mode;
	int ScreenWidth;
	int ScreenHeight;
    float fw;   
    int open;
    int bw;
};

struct SetValue {
	int mode;
	int type;
    float px;
    float py;
};

struct ItemData {
    char ItemName[50];
    float x;
	float y;
	float w;
    float Distance;
};





struct VehicleData {
	char VehicleName[50];
	float x;
	float y;
	float w;
	float Distance;
};

struct PlayerData {
	char PlayerName[100]; 
	float x;
	float y;
	float w;
	float h;
	int TeamID;
	int State;
	int isBot;
	float Unhealthy;
	float Health;
	float Distance;
	Vector2A Radar;
	Vector2A Head;
	Vector2A Chest;
	Vector2A Pelvis;
	Vector2A Left_Shoulder;
	Vector2A Right_Shoulder;
	Vector2A Left_Elbow;
	Vector2A Right_Elbow;
	Vector2A Left_Wrist;
	Vector2A Right_Wrist;
	Vector2A Left_Thigh;
	Vector2A Right_Thigh;
	Vector2A Left_Knee;
	Vector2A Right_Knee;
	Vector2A Left_Ankle;
	Vector2A Right_Ankle;
	int handheld;
	int bullet;
};



struct Response {
	bool Success;
	int PlayerCount;
	int VehicleCount;
	int ItemsCount;
	PlayerData Players[maxplayerCount];
	VehicleData Vehicles[maxvehicleCount];
	ItemData Items[maxitemsCount];
    
};


struct Aimbot {
    float fw;
    int open;
    int bw;
 };

