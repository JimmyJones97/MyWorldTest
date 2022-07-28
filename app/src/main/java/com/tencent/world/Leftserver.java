package com.tencent.world;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Build;
import android.os.IBinder;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;
import com.tencent.world.R;
import com.tencent.world.Tool;
import com.tencent.world.ShellUtils;
import com.tencent.world.MainActivity;
import android.widget.SeekBar;
import java.io.FileWriter;
import java.io.IOException;
import java.io.DataOutputStream;
import org.apache.http.conn.*;
import android.os.Handler;


public class Leftserver extends Service {

    public static String TITLE;
    public static final String TAG = "Mod_Menu";

    public LinearLayout itemsLayout;
    public RelativeLayout relativeLayout;
    public RelativeLayout iconLayout;
    public ScrollView scrollView;
    public TextView textTitle;

    public WindowManager windowManager,xfqManager;
    public WindowManager.LayoutParams g_layoutParams;

	
	private static Leftserver Instance;
    public int type;
    public int width=50;
    public int GLITCH_DELAY = 175;
    public int GLITCH_LEN = 2;
    public float density;
    public int dpi;
    public int height=30;
	public boolean iswin=false;

    
	public static boolean isDraw=false;
	public static int 真实宽;//分辨率x
	public static int 真实高;//分辨率y
    public static float 真实范围;
	
    int ToggleON = Color.GREEN;
    int ToggleOFF = Color.parseColor("#FFFF5252");
    int BtnON = Color.parseColor("#1b5e20");
    int BtnOFF = Color.parseColor("#7f0000");
    int CategoryBG = Color.parseColor("#2F3D4C");
    int SeekBarColor = Color.BLUE;
    int SeekBarProgressColor = Color.parseColor("#80CBC4");
    int CheckBoxColor = Color.parseColor("#80CBC4");
    int RadioColor = Color.parseColor("#FFFFFF");
    String NumberTxtColor = "#41c300";
    public LinearLayout xfq,initlayout,Playerlayout,住布局,布局初始化,AimLocationlayout,Aimbotlayout,Settinglayout;
    public ESPView espLayout;
    public static Context ctx;
    public TextView initswitch,Playerswitch,游戏版本,启动模式,Aimbotswitch,AimLocationswitch,Aimfovtv,Settingswitch,fpstv,方框左右;
    public ImageView 图标;
    public CheckBox 方框,射线,骨骼,昵称,距离,手持,血量,雷达,预警,初始化,自瞄开关,自瞄圈;
    public RadioButton 和平精英,使命召唤,穿越火线,未来之役,APEX,刺激战场,头部,脚部,胸部;
    private WindowManager.LayoutParams layoutParams;
    public String GameVersion;
    public String StartName;
    private View contentView; 
    public SeekBar Fps,方框调节;
    public SeekBar Aimfov;
	public static native void DrawOn(ESPView espView, Canvas canvas, int width, int height);
	public static native void Control(int i, boolean k);
	public static native void getReady(int typeofgame,float px,float py);
	public static native void Close();
    public static native void range(float i);
    public static native void bw(int i);
    public static native void open(int i);
    public static native void set(float i);
    
    //public static native void StartAimbot(int state);
    
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
		System.loadLibrary("Rubel");
        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		WindowManager 窗口=(WindowManager)getSystemService(Context.WINDOW_SERVICE);
		assert 窗口 != null;
		Display 分辨率=窗口.getDefaultDisplay();
		Point 输出分辨=new Point();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			分辨率.getRealSize(输出分辨);
		}
		真实宽 = 输出分辨.x;
		真实高 = 输出分辨.y;
		if (真实宽 > 真实高) {
			真实宽 = 输出分辨.y;
			真实高 = 输出分辨.x;
		}
        悬浮窗();
		//initView();
    }
    
  
    public void 悬浮窗() {
        
        type = getLayoutType();
        Display display = windowManager.getDefaultDisplay();
        Point point = new Point();
        display.getRealSize(point);
        width = point.x - 100;
        height = point.y;
        dpi = Resources.getSystem().getDisplayMetrics().densityDpi;
        density = Resources.getSystem().getDisplayMetrics().density;

		int Winwidth=width / 10 + width / 2;
        
        //iswin = true;
        windowManager = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
        layoutParams = new WindowManager.LayoutParams();
        // 设置宽高
        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.x = 0;
        layoutParams.y = -120;
       // RecorderFakeUtils.setFakeRecorderWindowLayoutParams(layoutParams);
        
        // 设置背景透明
        layoutParams.format = PixelFormat.TRANSPARENT;
        // 设置屏幕居中左边为起始点
        layoutParams.gravity = Gravity.CENTER | Gravity.LEFT;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            // 设置窗体显示类型(TYPE_TOAST:与toast一个级别)
            layoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        }
        // 设置布局弹出的动画
        layoutParams.windowAnimations = R.style.CustomCheckboxTheme;
        contentView = LayoutInflater.from(Leftserver.this).inflate(R.layout.server, null);
        InitBaseView();
        // 添加视图
        RecorderFakeUtils.setFakeRecorderWindowLayoutParams(layoutParams);
        windowManager.addView(contentView, layoutParams);
        
        CreateCanvas();
        
       
    }
    
    public void InitBaseView() {
        方框调节 = contentView.findViewById(R.id.方框调节);
        方框左右 = contentView.findViewById(R.id.方框左右);
        Fps = contentView.findViewById(R.id.fps);
        fpstv = contentView.findViewById(R.id.fpstv);
        Aimfov = contentView.findViewById(R.id.Aimfov);
        Aimfovtv = contentView.findViewById(R.id.Aimfovtv);
        自瞄开关 = contentView.findViewById(R.id.自瞄开关);
        //自瞄圈 = contentView.findViewById(R.id.自瞄圈);
        
        
        initswitch = contentView.findViewById(R.id.initswitch);
        initlayout = contentView.findViewById(R.id.initlayout);
        
        Playerswitch = contentView.findViewById(R.id.Playerswitch);
        Playerlayout = contentView.findViewById(R.id.Playerlayout);
        
        Aimbotswitch = contentView.findViewById(R.id.Aimbotswitch);
        Aimbotlayout = contentView.findViewById(R.id.Aimbotlayout);
        
        AimLocationswitch = contentView.findViewById(R.id.AimLocationswitch);
        AimLocationlayout = contentView.findViewById(R.id.AimLocationlayout);
        
        
        Settingswitch = contentView.findViewById(R.id.Settingswitch);
        Settinglayout = contentView.findViewById(R.id.Settinglayout);
        
        
        头部 = contentView.findViewById(R.id.头部);
        胸部 = contentView.findViewById(R.id.胸部);
        脚部 = contentView.findViewById(R.id.脚部);
        
        
        
        住布局 = contentView.findViewById(R.id.住布局);
        图标 = contentView.findViewById(R.id.图标);
        初始化 = contentView.findViewById(R.id.初始化);
        方框 = contentView.findViewById(R.id.方框);
        射线 = contentView.findViewById(R.id.射线);
        骨骼 = contentView.findViewById(R.id.骨骼);
        昵称 = contentView.findViewById(R.id.昵称);
        距离 = contentView.findViewById(R.id.距离);
        手持 = contentView.findViewById(R.id.手持);
        血量 = contentView.findViewById(R.id.血量);
        预警 = contentView.findViewById(R.id.预警);
        雷达 = contentView.findViewById(R.id.雷达);
        游戏版本 = contentView.findViewById(R.id.游戏版本);
        启动模式 = contentView.findViewById(R.id.启动模式);
        布局初始化 = contentView.findViewById(R.id.布局初始化);
        和平精英 = contentView.findViewById(R.id.和平精英);
        使命召唤 = contentView.findViewById(R.id.使命召唤);
        未来之役 = contentView.findViewById(R.id.未来之役);
        刺激战场 = contentView.findViewById(R.id.刺激战场);
        穿越火线 = contentView.findViewById(R.id.穿越火线);
        APEX = contentView.findViewById(R.id.APEX);
        
        if(getConfig("StartMode")){
            启动模式.setText("启动模式: Root");
        }
        else{
            启动模式.setText("启动模式: NoRoot");
        }
        和平精英.setOnClickListener(new View.OnClickListener() {
                boolean isChecked ;

                @Override
                public void onClick(View view) {
                    布局初始化.setVisibility(view.VISIBLE);
                    GameVersion = "和平精英";
                    StartName = "HPJY";
                    游戏版本.setText("游戏版本: "+GameVersion);
                    Aimbotswitch.setVisibility(View.GONE);
                    Aimbotlayout.setVisibility(View.GONE);
                    骨骼.setVisibility(View.VISIBLE);
                    open(0);
                    Control(10, false);
                    自瞄开关.setChecked(false);
                    //Tool.写出资源文件(getApplicationContext(), getFilesDir() + "/assets", "HPJY");
                    copyFile("HPJY");
                    
                }

            }); 
        刺激战场.setOnClickListener(new View.OnClickListener() {
                boolean isChecked ;

                @Override
                public void onClick(View view) {
                    布局初始化.setVisibility(view.VISIBLE);
                    GameVersion = "刺激战场";
                    StartName = "PUBG";
                    游戏版本.setText("游戏版本: "+GameVersion);
                    Aimbotswitch.setVisibility(View.GONE);
                    Aimbotlayout.setVisibility(View.GONE);
                    骨骼.setVisibility(View.VISIBLE);
                    open(0);
                    Control(10, false);
                    自瞄开关.setChecked(false);
                    //Tool.写出资源文件(getApplicationContext(), getFilesDir() + "/assets", "HPJY");
                    copyFile("PUBG");

                }

            }); 
        
        未来之役.setOnClickListener(new View.OnClickListener() {
                boolean isChecked ;

                @Override
                public void onClick(View view) {
                    布局初始化.setVisibility(view.VISIBLE);
                    GameVersion = "未来之役";
                    StartName = "WLZY";
                    游戏版本.setText("游戏版本: "+GameVersion);
                    Aimbotswitch.setVisibility(View.VISIBLE);
                    Aimbotlayout.setVisibility(View.VISIBLE);
                    骨骼.setVisibility(View.VISIBLE);
                    //AimbotTypeswitch.setVisibility(View.VISIBLE);
                    //Tool.写出资源文件(getApplicationContext(), getFilesDir() + "/assets", "HPJY");
                    copyFile("WLZY");

                }

            }); 
        使命召唤.setOnClickListener(new View.OnClickListener() {
                boolean isChecked ;

                @Override
                public void onClick(View view) {
                    布局初始化.setVisibility(view.VISIBLE);
                    GameVersion = "使命召唤";
                    StartName = "SMZH";
                    游戏版本.setText("游戏版本: "+GameVersion);
                    Aimbotswitch.setVisibility(View.VISIBLE);
                    Aimbotlayout.setVisibility(View.VISIBLE);
                    骨骼.setVisibility(View.GONE);
                    //AimbotTypeswitch.setVisibility(View.GONE);
                    //Tool.写出资源文件(getApplicationContext(), getFilesDir() + "/assets", "HPJY");
                    copyFile("SMZH");

                }

            }); 
        穿越火线.setOnClickListener(new View.OnClickListener() {
                boolean isChecked ;

                @Override
                public void onClick(View view) {
                    布局初始化.setVisibility(view.VISIBLE);
                    GameVersion = "穿越火线";
                    StartName = "CFM";
                    游戏版本.setText("游戏版本: "+GameVersion);
                    Aimbotswitch.setVisibility(View.VISIBLE);
                    Aimbotlayout.setVisibility(View.VISIBLE);
                    骨骼.setVisibility(View.GONE);
                    //AimbotTypeswitch.setVisibility(View.GONE);
                    //Tool.写出资源文件(getApplicationContext(), getFilesDir() + "/assets", "HPJY");
                    copyFile("CFM");

                }

            }); 
            
        APEX.setOnClickListener(new View.OnClickListener() {
                boolean isChecked ;

                @Override
                public void onClick(View view) {
                    布局初始化.setVisibility(view.VISIBLE);
                    GameVersion = "APEX";
                    StartName = "APEXM";
                    游戏版本.setText("游戏版本: "+GameVersion);
                    Aimbotswitch.setVisibility(View.VISIBLE);
                    Aimbotlayout.setVisibility(View.VISIBLE);
                    骨骼.setVisibility(View.VISIBLE);
                    //AimbotTypeswitch.setVisibility(View.GONE);
                    //Tool.写出资源文件(getApplicationContext(), getFilesDir() + "/assets", "HPJY");
                    copyFile("APEXM");

                }

            }); 
        
        图标.setOnClickListener(new View.OnClickListener() {
                boolean isChecked ;

                @Override
                public void onClick(View view) {
                    boolean z = !isChecked;
                    isChecked = z;
                    if (z) {
                        住布局.setVisibility(View.VISIBLE);
                        return;
                    }else{
                        住布局.setVisibility(View.GONE);                     
                    }

                }

            });
        initswitch.setOnClickListener(new View.OnClickListener() {
                boolean isChecked ;
                
                @Override
                public void onClick(View view) {
                    boolean z = !isChecked;
                    isChecked = z;
                    if (z) {
                        initswitch.setText("▲Initialization");  
                        initlayout.setVisibility(View.VISIBLE);
                        return;
                    }else{
                        initswitch.setText("▼Initialization"); 
                        initlayout.setVisibility(View.GONE);                     
                    }
                    
                }
                
          });
        Playerswitch.setOnClickListener(new View.OnClickListener() {
                boolean isChecked ;

                @Override
                public void onClick(View view) {
                    boolean z = !isChecked;
                    isChecked = z;
                    if (z) {
                        Playerswitch.setText("▲Player");  
                        Playerlayout.setVisibility(View.VISIBLE);
                        return;
                    }else{
                        Playerswitch.setText("▼Player"); 
                        Playerlayout.setVisibility(View.GONE);                     
                    }

                }

            });
            
        Aimbotswitch.setOnClickListener(new View.OnClickListener() {
                boolean isChecked ;

                @Override
                public void onClick(View view) {
                    boolean z = !isChecked;
                    isChecked = z;
                    if (z) {
                        Aimbotswitch.setText("▲Aimbot");  
                        //AimLocationlayout.setVisibility(View.VISIBLE);
                        
                        Aimbotlayout.setVisibility(View.VISIBLE);
                        return;
                    }else{
                        Aimbotswitch.setText("▼Aimbot"); 
                        //AimLocationlayout.setVisibility(View.GONE);                     
                        
                        Aimbotlayout.setVisibility(View.GONE);                     
                    }

                }

            });
        AimLocationswitch.setOnClickListener(new View.OnClickListener() {
                boolean isChecked ;

                @Override
                public void onClick(View view) {
                    boolean z = !isChecked;
                    isChecked = z;
                    if (z) {
                        AimLocationswitch.setText("▲AimLocation");  
                        AimLocationlayout.setVisibility(View.VISIBLE);
                        return;
                    }else{
                        AimLocationswitch.setText("▼AimLocation"); 
                        AimLocationlayout.setVisibility(View.GONE);                     
                    }

                }

            });
        
            
        Settingswitch.setOnClickListener(new View.OnClickListener() {
                boolean isChecked ;

                @Override
                public void onClick(View view) {
                    boolean z = !isChecked;
                    isChecked = z;
                    if (z) {
                        Settingswitch.setText("▲Setting");  
                        Settinglayout.setVisibility(View.VISIBLE);
                        return;
                    }else{
                        Settingswitch.setText("▼Setting"); 
                        Settinglayout.setVisibility(View.GONE);                     
                    }

                }

            });
        
            
            
        图标.setOnTouchListener(new View.OnTouchListener() {
                private int mTouchStartX, mTouchStartY;//手指按下时坐标
                private boolean isMove = false;

                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN://单击
                            isMove = false;
                            mTouchStartX = (int) event.getRawX();
                            mTouchStartY = (int) event.getRawY();
                            break;
                        case MotionEvent.ACTION_MOVE://拖动
                            int nowX = (int) event.getRawX();
                            int nowY = (int) event.getRawY();
                            int movedX = nowX - mTouchStartX;
                            int movedY = nowY - mTouchStartY;
                            if (movedX > 5 || movedY > 5) {
                                isMove = true;
                            }
                            mTouchStartX = nowX;
                            mTouchStartY = nowY;
                            layoutParams.x += movedX;
                            layoutParams.y += movedY;
                            RecorderFakeUtils.setFakeRecorderWindowLayoutParams(layoutParams);
                            windowManager.updateViewLayout(contentView, layoutParams);
                            
                            break;
                        case MotionEvent.ACTION_UP://抬起
                            break;
                        case MotionEvent.ACTION_CANCEL:
                            break;
                    }
                    return isMove;
                }
            });
        isDraw = false; 
        初始化.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        if(getConfig("StartMode")){
                            
                            
                            isDraw = true;
                            Start(ctx, 2);
                          //  Tool.调用二进制(Leftserver.this,1,"/"+StartName);
                         //Root("HPJY","assets","HPJY");
                          
                         
                            
                            Toast.makeText(getApplication(), "启动游戏: "+GameVersion, Toast.LENGTH_SHORT).show();
                            
                        
                       }else{

                           Toast.makeText(getApplication(), "没root玩你妈逼", 0).show(); 
                        }
                        
                  //      ESPView.ChangeFps(getFps("fps"));
                                                
                    } else {
                        isDraw = false;
                        Close();
                       
                        
                    }
                }
            });
        
        if(getConfig(方框.getText().toString()) == true){
            方框.setChecked(getConfig(方框.getText().toString()));   
            Control(1, true);
         }
        方框.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        setValue(方框.getText().toString(),方框.isChecked());
                        Control(1, true);
                        
                    } else {
                        setValue(方框.getText().toString(),方框.isChecked());
                        
                        Control(1, false);
                        
                    }
                }
            });
        if(getConfig(射线.getText().toString()) == true){
            射线.setChecked(getConfig(射线.getText().toString()));   
            Control(2, true);
        }
        
        射线.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        setValue(射线.getText().toString(),射线.isChecked());
                        
                        Control(2, true);
                        
                    } else {
                        setValue(射线.getText().toString(),射线.isChecked());
                        
                        Control(2, false);
                        
                    }
                }
            });
        if(getConfig(骨骼.getText().toString()) == true){
            骨骼.setChecked(getConfig(骨骼.getText().toString()));   
            Control(3, true);
        }
        
        骨骼.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        setValue(骨骼.getText().toString(),骨骼.isChecked());
                        
                        Control(3, true);
                        
                    } else {
                        setValue(骨骼.getText().toString(),骨骼.isChecked());
                        
                        Control(3, false);
                        
                    }
                }
            });
        if(getConfig(昵称.getText().toString()) == true){
            昵称.setChecked(getConfig(昵称.getText().toString()));   
            Control(4, true);
        }
        
        昵称.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        setValue(昵称.getText().toString(),昵称.isChecked());
                        
                        Control(4, true);
                        
                    } else {
                        setValue(昵称.getText().toString(),昵称.isChecked());
                        
                        Control(4, false);
                        
                    }
                }
            });
        if(getConfig(距离.getText().toString()) == true){
            距离.setChecked(getConfig(距离.getText().toString()));   
            Control(5, true);
        }
        
        距离.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        setValue(距离.getText().toString(),距离.isChecked());
                        
                        Control(5, true);
                        
                    } else {
                        setValue(距离.getText().toString(),距离.isChecked());
                        
                        Control(5, false);
                        
                    }
                }
            });
        if(getConfig(手持.getText().toString()) == true){
            手持.setChecked(getConfig(手持.getText().toString()));   
            Control(6, true);
        }
        
        手持.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        setValue(手持.getText().toString(),手持.isChecked());
                        
                        Control(6, true);
                        
                    } else {
                        setValue(手持.getText().toString(),手持.isChecked());
                        
                        Control(6, false);
                        
                    }
                }
            });
        if(getConfig(血量.getText().toString()) == true){
            血量.setChecked(getConfig(血量.getText().toString()));   
            Control(7, true);
        }
        
        血量.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        setValue(血量.getText().toString(),血量.isChecked());
                        
                        Control(7, true);
                        
                    } else {
                        setValue(血量.getText().toString(),血量.isChecked());
                        
                        Control(7, false);
                        
                    }
                }
            });
        if(getConfig(雷达.getText().toString()) == true){
            雷达.setChecked(getConfig(雷达.getText().toString()));   
            Control(8, true);
        }
        
        雷达.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        setValue(雷达.getText().toString(),雷达.isChecked());
                        
                        Control(8, true);
                        
                    } else {
                        setValue(雷达.getText().toString(),雷达.isChecked());
                        
                        Control(8, false);
                        
                    }
                }
            });
        if(getConfig(预警.getText().toString()) == true){
            预警.setChecked(getConfig(预警.getText().toString()));   
            Control(9, true);
        }
        预警.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        setValue(预警.getText().toString(),预警.isChecked());
                        
                        Control(9, true);
                        
                    } else {
                        setValue(预警.getText().toString(),预警.isChecked());
                        
                        Control(9, false);
                        
                    }
                }
            });
        if(getConfig(自瞄开关.getText().toString()) == true){
            自瞄开关.setChecked(getConfig(自瞄开关.getText().toString())); 
            //writeFile("/sdcard/zm", "1");
            open(1);           
             Control(10, true);
                   }
        
        自瞄开关.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        setValue(自瞄开关.getText().toString(),自瞄开关.isChecked());
                      //  writeFile("/sdcard/zm", "1");
                        open(1);
                        Control(10, true);
                        
                    } else {
                        setValue(自瞄开关.getText().toString(),自瞄开关.isChecked());
                         open(0);
                         Control(10, false);
                        
                    }
                }
            });
        /*if(getConfig(自瞄圈.getText().toString()) == true){
            自瞄圈.setChecked(getConfig(自瞄圈.getText().toString()));   
        }
        自瞄圈.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        setValue(自瞄圈.getText().toString(),自瞄圈.isChecked());

                    } else {
                        setValue(自瞄圈.getText().toString(),自瞄圈.isChecked());

                    }
                }
            });*/
        
           bw(0);
            头部.setChecked(true);
              头部.setOnClickListener(new View.OnClickListener() {
  
                @Override
                public void onClick(View view) {
                    bw(0);
                  //  writeFile("/sdcard/wz", "0");
                    
                    setFps("Aimlock",0);
                }

            });
        胸部.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                 //   writeFile("/sdcard/wz", "1");
                    bw(1);
                    setFps("Aimlock",1);

                }

            });
        脚部.setOnClickListener(new View.OnClickListener() {
  
                @Override
                public void onClick(View view) {
               //     writeFile("/sdcard/wz", "2");
                   bw(2);
                    setFps("Aimlock",2);
                    

                }

            });
            
        Fps.setProgress(getFps());
        ESPView.ChangeFps(getFps());
        fpstv.setText("FPS: "+getFps());
        Fps.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    int FPS = progress;
                    fpstv.setText("FPS: "+FPS);
                    setFps("fps",FPS);
                   ESPView.ChangeFps(FPS);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });
        setFps("方框调节",0);
        方框调节.setProgress(左右调节());
        方框左右.setText("方框左右: "+左右调节());
        set(get左右调节("方框调节"));
        方框调节.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    int 方框左右调节 = progress;
                    方框左右.setText("方框左右: "+方框左右调节);
                   setFps("方框调节",方框左右调节);
                    set(get左右调节("方框调节"));
                   
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });
        
        
        Aimfov.setProgress(getAim("Aimfov"));
        Aimfovtv.setText("自瞄范围: "+getAim("Aimfov"));
        //真实范围=getAim("Aimfov");
        range(getAim("Aimfov"));
       // writeFile("/sdcard/fw", ""+getAim("Aimfov"));
        Aimfov.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    int Aimfov = progress;
                    
                    Aimfovtv.setText("自瞄范围: "+Aimfov);
                    setFps("Aimfov",Aimfov);
                   // 真实范围=getAim("Aimfov");
                    //writeFile("/sdcard/fw", ""+Aimfov);
                  range(getAim("Aimfov"));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });
       
    }
    private void copyFile(String filename) {
        try {
            File file = new File(getFilesDir(), filename);

            InputStream in = getAssets().open(filename);
            OutputStream out = new FileOutputStream(file);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            out.flush();
            out.close();

            file.setExecutable(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 
  
    public static String getRandColor() {
        String R, G, B;
        Random random = new Random();
        R = Integer.toHexString(random.nextInt(256)).toUpperCase();
        G = Integer.toHexString(random.nextInt(256)).toUpperCase();
        B = Integer.toHexString(random.nextInt(256)).toUpperCase();
        R = R.length() == 1 ? "0" + R : R;
        G = G.length() == 1 ? "0" + G : G;
        B = B.length() == 1 ? "0" + B : B;
        return "#" + 80 + G + B;
    }  

    public void CreateCanvas() {
        ESPView eSPView;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, type, 56, -3);
        if (Build.VERSION.SDK_INT >= 28) {
            layoutParams.layoutInDisplayCutoutMode = 1;
        }
        layoutParams.x = 0;
        layoutParams.y = 0;
        layoutParams.gravity = 51;
        RecorderFakeUtils.setFakeRecorderWindowLayoutParams(layoutParams);
        espLayout = eSPView = new ESPView((Context)this);
        windowManager.addView((View)eSPView, (ViewGroup.LayoutParams)layoutParams);
        
    }
  
    @Override
    public void onDestroy() {
        if (contentView != null){
            windowManager.removeView(contentView);
        }
        windowManager.removeView(espLayout);
        
        stopSelf();
        super.onDestroy();
        
    }
	private int getLayoutType() {
        int LAYOUT_FLAG;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_TOAST;
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }
        return LAYOUT_FLAG;
    }

    public  void Start(final Context context, final int gametype) {
        if (Instance == null) {
            // Intent intent = new Intent(context, Overlay.class);
            
            Root(1);
            Thread t=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getReady(gametype,真实高,真实宽);
                       // 范围(真实范围);
                    }
                });
            t.start();
          }
      
    }
    
  
    public static void writeFile(String filePath, String f) {
        FileWriter fw;
        try {
            fw = new FileWriter(filePath);
            fw.write(f);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void delete(File f) {
        if (f.isDirectory()) {
            File[] subs = f.listFiles();
            for (int i=0;i < subs.length;i++) {
                File sub = subs[i]; 
                delete(sub);
            }
        }
        f.delete();
    }
/*	public void RunBinary() {  
        //Toast.makeText(getApplication(), ""+getConfig("StartMode"), Toast.LENGTH_SHORT).show();
        
        new Thread() {
            @Override

            public void run() {
                if(getConfig("StartMode")){
                    ShellUtils.execCommand("chmod 777 " + getFilesDir().getPath() + "/"+StartName, true);
                    ShellUtils.execCommand(getFilesDir().getPath() + "/"+StartName, true);
                }
                else{
                    ShellUtils.execCommand("chmod 777 " + getFilesDir().getPath() + "/"+StartName, false);
                    ShellUtils.execCommand(getFilesDir().getPath() + "/"+StartName, false);
                    
                }
            }
        }.start();
    }*/
    
    
    public void Root(int mode) {   
        if (mode==1){
    new Thread() {
            @Override

            public void run() {

                ShellUtils.execCommand("chmod 777 " + getFilesDir().getPath() + "/"+StartName, true);
                ShellUtils.execCommand(getFilesDir().getPath() + "/"+StartName, true);
            }
        }.start();
        }
        if (mode==2){
            new Thread() {
                @Override

                public void run() {

                    ShellUtils.execCommand("chmod 777 " + getFilesDir().getPath() + "/"+StartName, false);
                    ShellUtils.execCommand(getFilesDir().getPath() + "/"+StartName/* + " >/dev/null 2>&1"*/, false);
                }
            }.start();
            }
    }
    public void NoRoot() {   
        new Thread() {
            @Override

            public void run() {

                ShellUtils.execCommand("chmod 777 " + getFilesDir().getPath() + "/"+StartName, false);
                ShellUtils.execCommand(getFilesDir().getPath() + "/"+StartName/* + " >/dev/null 2>&1"*/, false);
            }
        }.start();
    }
    
    
    public  void 调用二进制(int ZTSZ) {
        if (ZTSZ == 1) {
            try {
                // 写出资源文件(this, getFilesDir() + "/", ""+wj);
                Runtime.getRuntime().exec("chmod 777 " + getFilesDir().getPath() +"/"+ StartName, null, null);
                Runtime.getRuntime().exec("su -c " + getFilesDir().getPath() +"/"+ StartName, null, null);
                Runtime.getRuntime().exec(getFilesDir().getPath() +"/"+ StartName, null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ZTSZ == 2) {
            try {
                Runtime.getRuntime().exec("chmod 777 " + getFilesDir().getPath() +"/"+ StartName, null, null);
                Runtime.getRuntime().exec(getFilesDir().getPath() +"/"+ StartName, null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

        }

    }
    

    
    
    private void setValue(String key, boolean value) {
        SharedPreferences sp = this.getSharedPreferences("Config", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putBoolean(key, value);
        ed.apply();
    }
    
    
    
    boolean getConfig(String key) {
        SharedPreferences sp = this.getSharedPreferences("Config", Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }
    public static interface OnListChoosedListener {
        public void onChoosed(int var1);
    }

    void setFps(String name, int fps) {
        SharedPreferences sp=this.getSharedPreferences("Config", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed= sp.edit();
        ed.putInt(name, fps);
        ed.apply();
    }

    int getFps() {
        SharedPreferences sp=this.getSharedPreferences("Config", Context.MODE_PRIVATE);
        return sp.getInt("fps", 120);
    }
    
    int 左右调节() {
        SharedPreferences sp=this.getSharedPreferences("Config", Context.MODE_PRIVATE);
        return sp.getInt("方框调节", 0);
    }
    
    
    int getAim(String name) {
        SharedPreferences sp=this.getSharedPreferences("Config", Context.MODE_PRIVATE);
        return sp.getInt(name, 50);
    }
    
    int get左右调节(String name) {
        SharedPreferences sp=this.getSharedPreferences("Config", Context.MODE_PRIVATE);
        return sp.getInt(name, 0);
    }
     
    
    

}
