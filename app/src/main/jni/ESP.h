#ifndef ESP_H
#define ESP_H
#include "struct.h"
static jmethodID drawcircle;
static jmethodID drawplayerbox;
static jmethodID drawplayerline;
static jmethodID drawplayername;
static jmethodID drawplayerdistance;
static jmethodID drawtext;
static jmethodID drawplayerbone;
static jmethodID drawplayering;
static jmethodID draw_ic_bot;
static jmethodID drawplayerhealth;
static jmethodID drawplayer360alert;
static jmethodID drawplayer360;
static jmethodID drawplayer360distance;
static jmethodID drawplayerradarxy;
static jmethodID drawplayerradar;
static jmethodID drawvehiclesname;
static jmethodID drawpictures;

class ESP {
private:
    JNIEnv *_env;
    jobject _cvsView;
    jobject _cvs;


public:
    ESP() {
        _env = nullptr;
        _cvsView = nullptr;
        _cvs = nullptr;
    }

    ESP(JNIEnv *env, jobject cvsView, jobject cvs) {
        this->_env = env;
        this->_cvsView = cvsView;
        this->_cvs = cvs;
    }

     bool isValid() const {
        return (_env != nullptr && _cvsView != nullptr && _cvs != nullptr);
    }

    
    void drawfunction(){
       
        jclass canvasView = _env->GetObjectClass(_cvsView);
        drawcircle = _env->GetMethodID(canvasView, "DrawCircle","(Landroid/graphics/Canvas;IFFF)V");
        drawplayerbox = _env->GetMethodID(canvasView, "DrawPlayerBox","(Landroid/graphics/Canvas;FFFF)V");
        drawplayerline = _env->GetMethodID(canvasView, "DrawPlayerLine","(Landroid/graphics/Canvas;FFFF)V");
        drawplayername = _env->GetMethodID(canvasView, "DrawPlayerName","(Landroid/graphics/Canvas;Ljava/lang/String;FF)V");
        drawplayerdistance = _env->GetMethodID(canvasView, "DrawPlayerDistance","(Landroid/graphics/Canvas;Ljava/lang/String;FF)V");
        drawtext = _env->GetMethodID(canvasView, "DrawText","(Landroid/graphics/Canvas;Ljava/lang/String;FF)V");
        drawplayerbone = _env->GetMethodID(canvasView, "DrawPlayerBone","(Landroid/graphics/Canvas;IFFFF)V");
        drawplayering = _env->GetMethodID(canvasView, "DrawPlayerIng","(Landroid/graphics/Canvas;I)V");
        draw_ic_bot = _env->GetMethodID(canvasView, "Draw_ic_Bot","(Landroid/graphics/Canvas;FF)V");
        drawplayerhealth = _env->GetMethodID(canvasView, "DrawPlayerHealth","(Landroid/graphics/Canvas;IFFF)V");
        drawplayer360alert = _env->GetMethodID(canvasView, "DrawPlayer360Alert","(Landroid/graphics/Canvas;IFFF)V");
        drawplayer360 = _env->GetMethodID(canvasView, "DrawPlayer360","(Landroid/graphics/Canvas;FFFF)V");
        drawplayer360distance = _env->GetMethodID(canvasView, "DrawPlayer360Text","(Landroid/graphics/Canvas;Ljava/lang/String;FF)V");
        drawplayerradarxy = _env->GetMethodID(canvasView, "DrawPlayerRadarXY","(Landroid/graphics/Canvas;IFF)V");
        drawplayerradar = _env->GetMethodID(canvasView, "DrawPlayerRadar","(Landroid/graphics/Canvas;)V");
        drawvehiclesname = _env->GetMethodID(canvasView, "DrawVehiclesName","(Landroid/graphics/Canvas;ILjava/lang/String;FFF)V");
        drawpictures = _env->GetMethodID(canvasView, "Drawpictures","(Landroid/graphics/Canvas;IFF)V");

        }
    int getWidth() const {
      jclass canvas = _env->GetObjectClass(_cvs);
     jmethodID width = _env->GetMethodID(canvas, "screenWidth", "()F");
            
            return _env->CallIntMethod(_cvs, width);
        
       
    }

    int getHeight() const {
           jclass canvas = _env->GetObjectClass(_cvs);
           jmethodID height = _env->GetMethodID(canvas, "screenHeight", "()F");

            
            return _env->CallIntMethod(_cvs, height);
        
        
    }
    
    
    void DrawCircle(Color colour,float posX,float posY, float radius) {
        
           
          
                                                    
            _env->CallVoidMethod(_cvsView, drawcircle, _cvs,colour, posX, posY, radius);
        
    }
    
      


    void DrawPlayerBox(float x1,float y1,float x2,float y2) {
        
                 
            _env->CallVoidMethod(_cvsView, drawplayerbox, _cvs, x1, y1, x2, y2);
        
    }
    
    void DrawPlayerLine(float x1,float y1,float x2,float y2) {
        
           
                         _env->CallVoidMethod(_cvsView, drawplayerline, _cvs, x1, y1, x2, y2);
        
    }
    
    void DrawPlayerName(char *txt,float x,float y) {
        
           
            jstring s=_env->NewStringUTF(txt);
            _env ->CallVoidMethod(_cvsView, drawplayername, _cvs, s, x, y);
            _env->DeleteLocalRef(s);
        
    }
    
        void DrawPlayerDistance(char *txt,float x,float y) {
       
          
           
            jstring s=_env->NewStringUTF(txt);
           _env ->CallVoidMethod(_cvsView, drawplayerdistance, _cvs, s, x, y);
            _env->DeleteLocalRef(s);
        
    }
    
    
    void DrawText( const char *txt, float posX, float posY) {
        
          
            
            jstring s=_env->NewStringUTF(txt);
            _env->CallVoidMethod(_cvsView, drawtext, _cvs,s, posX, posY);
            _env->DeleteLocalRef(s);
        
    }
    
    void DrawPlayerBone(Color colour,float x1,float y1,float x2,float y2) {
        
              
                                                    
            _env->CallVoidMethod(_cvsView, drawplayerbone, _cvs,colour, x1, y1, x2, y2);
        
    }
    
     
    
    void DrawPlayerIng(int TeamID) {
        
            
                                                    
            _env->CallVoidMethod(_cvsView, drawplayering, _cvs, TeamID);
        
    }
    
    void Draw_ic_Bot(float x,float y) {
        
            
            
                                                    
            _env->CallVoidMethod(_cvsView, draw_ic_bot, _cvs, x,y);
        
    }
    
    void DrawPlayerHealth(int Health,float x,float y, float w) {
        
           
            
                                                    
            _env->CallVoidMethod(_cvsView, drawplayerhealth, _cvs,Health, x, y, w);
        
    }
    
    void DrawPlayer360Alert(Color colour,float x,float y, float d) {
        
            
            
                                                    
            _env->CallVoidMethod(_cvsView, drawplayer360alert, _cvs,colour, x, y, d);
        
    }
  
    
      void DrawPlayer360(float x,float y, float posX,float posY) {
        
            
            
                                                    
            _env->CallVoidMethod(_cvsView, drawplayer360, _cvs, x, y, posX,posY);
        
    }
  
    
    void DrawPlayer360Text(char *txt,float x,float y) {
        
         
           
            jstring s=_env->NewStringUTF(txt);
           _env ->CallVoidMethod(_cvsView, drawplayer360distance, _cvs, s, x, y);
            _env->DeleteLocalRef(s);
        
    }
    
    void DrawPlayerRadarXY(int isBot,float x,float y) {
        
         
          
                                                    
            _env->CallVoidMethod(_cvsView, drawplayerradarxy, _cvs,isBot, x, y);
        
    }
    
    void DrawPlayerRadar() {
        
          
                                                                
            _env->CallVoidMethod(_cvsView, drawplayerradar, _cvs);
        
    }
    
    void DrawVehiclesName(Color colour,char *txt,float distance,float x,float y) {
       
           
                                                               
            jstring s=_env->NewStringUTF(txt);
            _env->CallVoidMethod(_cvsView, drawvehiclesname, _cvs,colour, s,distance, x, y);
            _env->DeleteLocalRef(s);
        
    }
    
    void Drawpictures(int number,float x,float y) {
        
         
          
                                                    
            _env->CallVoidMethod(_cvsView, drawpictures, _cvs,number, x, y);
        
    }

    
};


#endif //ESP_H
