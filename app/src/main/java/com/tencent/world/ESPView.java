package com.tencent.world;

import android.R;
import com.tencent.world.R.drawable;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

public class ESPView extends View implements Runnable {

	Path path1= new Path();
	Path path2 = new Path();

    Paint mStrokePaint;
    Paint mStrokePaint2;
    Paint mFilledPaint;
    Paint mFilledPaint2;
	Paint mFillCPaint;
    Paint mTextPaint;
    Paint mTextPaint2;
    Paint mTextPaint3;
	Paint mFpsPaint;
	Paint NamePaint;
	Paint BonePaint;
	Paint VehiclesPaint;
	Paint mRadarPaint;
	Paint 雷达红点;
	Paint 雷达绿点;
	Paint 雷达线;
    Paint 自瞄圈画笔;
    Paint 三角形画笔;
    Paint 路径画笔;
    Path 路径;
    RectF rectf;
    Paint 血条画笔2;
    Paint 血条画笔3;
    Paint 路径画笔1;
    Paint 距离画笔;
    Paint 距离画笔1;
    Paint 随机线;
    Paint 背敌画笔;
    Paint 背敌文字;
    Paint 背敌文字1;
    Paint 人数画笔哦1;
    Paint 人数画笔哦2;
    
    Thread mThread;
    
    Bitmap[] OTHER = new Bitmap[3];

    private static final int[] OTH_NAME = {
       drawable.back1,//0
       drawable.back2,//1
       drawable.ic_boot//2
       
        
    };
    
    
     int FPS = 120;
	public int itemPosition=0;

    private int mFPS = 0;
    private int mFPSCounter = 0;
    private long mFPSTime = 0;
    static long sleepTime;

    Date time;
    SimpleDateFormat formatter;
    static Context ctx;
	public float width;
	public float height;
	public float radarLeft=115,radarTop=115;

    Bitmap bitmap,out,bitmap2,out2;
	private static WindowManager wm;
    private static Point point;
	public static String color[];


    public ESPView(Context context) {
        super(context, null, 0);
        InitializePaints();
        setFocusableInTouchMode(false);
        setBackgroundColor(Color.TRANSPARENT);
		wm = (WindowManager) (context.getSystemService(Context.WINDOW_SERVICE));
        point = new Point();
		color = new String[2000];
		for (int i=0;i < 2000;i++) {
			color[i] = Leftserver.getRandColor();
		}
        time = new Date();
        formatter = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        sleepTime = 1000 / FPS;
        mThread = new Thread(this);
        mThread.start();
        ctx = context;
        
    }

    @Override
    protected void onDraw(Canvas canvas) {
		if (Leftserver.isDraw == true) {                      
			if (canvas != null && getVisibility() == VISIBLE) {
                ClearCanvas(canvas);
                time.setTime(System.currentTimeMillis());
				wm.getDefaultDisplay().getRealSize(point);
				FPSCounter(canvas, 0.5f, 250, 37, 25);
                
				Leftserver.DrawOn(this, canvas, point.x, point.y);
			}
		}
    }


    public static void ChangeFps(int fps) {
        sleepTime = 1000 / fps;
    }

	private static String getColor(int id) {
		return color[id - 1] == null ?"#303030": color[id - 1];
	}

    public void setFooter(Canvas cvs, int color, float stroke, String txt, float posX, float posY, float size) {
        mTextPaint.setColor(color);
        mTextPaint.setStrokeWidth(stroke);
        mTextPaint.setTextSize(size);
        cvs.drawText(txt, posX, posY, mTextPaint);
    }

    @Override
    public void run() {
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
        while (mThread.isAlive() && !mThread.isInterrupted()) {
            try {
                long t1 = System.currentTimeMillis();
                postInvalidate();
                long td = System.currentTimeMillis() - t1;
                Thread.sleep(Math.max(Math.min(0, sleepTime - td), sleepTime));
            } catch (InterruptedException it) {
                Log.e("OverlayThread", it.getMessage());
            }
        } 
    }


    static boolean getConfig(String key) {
        SharedPreferences sp=ctx.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        return  sp.getBoolean(key, false);
        // return !key.equals("");
    }

    public void InitializePaints() {


        mStrokePaint = new Paint();
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setAntiAlias(true);
        mStrokePaint.setColor(0xffff0000);
		mStrokePaint.setStrokeWidth(1.5f);

		BonePaint = new Paint();
        BonePaint.setStyle(Paint.Style.STROKE);
        BonePaint.setAntiAlias(true);
        BonePaint.setColor(0xffff0000);
		BonePaint.setStrokeWidth(1.5f);

        mFilledPaint = new Paint();
        mFilledPaint.setStyle(Paint.Style.FILL);
        mFilledPaint.setAntiAlias(true);
        mFilledPaint.setColor(Color.rgb(0, 0, 0));

		mRadarPaint = new Paint();
        mRadarPaint.setStyle(Paint.Style.FILL);
        mRadarPaint.setAntiAlias(true);
        mRadarPaint.setColor(Color.rgb(0, 0, 0));

		mFillCPaint = new Paint();
        mFillCPaint.setStyle(Paint.Style.FILL);
        mFillCPaint.setColor(Color.rgb(0, 0, 0));

        mStrokePaint2 = new Paint();
        mStrokePaint2.setStyle(Paint.Style.STROKE);
        mStrokePaint2.setAntiAlias(true);
        mStrokePaint2.setColor(0x9A000000);
		mStrokePaint2.setStrokeWidth(1.5f);

        mFilledPaint2 = new Paint();
        mFilledPaint2.setStyle(Paint.Style.FILL);
        mFilledPaint2.setAntiAlias(true);
        mFilledPaint2.setColor(Color.rgb(0, 0, 0)); 

        mTextPaint = new Paint();
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.rgb(0, 0, 0));
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setStrokeWidth(1.1f);

        mTextPaint2 = new Paint();
        mTextPaint2.setStyle(Paint.Style.FILL_AND_STROKE);
        mTextPaint2.setAntiAlias(true);
        mTextPaint2.setColor(Color.rgb(0, 0, 0));
        mTextPaint2.setTextAlign(Paint.Align.CENTER);
        mTextPaint2.setStrokeWidth(1.1f);

        mTextPaint3 = new Paint();
        mTextPaint3.setStyle(Paint.Style.FILL_AND_STROKE);
        mTextPaint3.setColor(0x90000000);
        mTextPaint3.setTextAlign(Paint.Align.CENTER);
		mTextPaint3.setFakeBoldText(true);
        mTextPaint3.setStrokeWidth(3f);
		mTextPaint3.setTextSize(19);

		mFpsPaint = new Paint();
        mFpsPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mFpsPaint.setAntiAlias(true);
        mFpsPaint.setColor(0xFFFF0000);
        mFpsPaint.setTextAlign(Paint.Align.LEFT);
		mFpsPaint.setTextSize(25f);
		mFpsPaint.setShadowLayer(3, 0, 0, Color.BLACK);

		NamePaint = new Paint();
        NamePaint.setStyle(Paint.Style.FILL);
        NamePaint.setAntiAlias(true);
        NamePaint.setColor(Color.rgb(255, 255, 255));
        NamePaint.setTextAlign(Paint.Align.CENTER);
		NamePaint.setFakeBoldText(true);
		NamePaint.setShadowLayer(2, 0, 0, Color.BLACK);
		NamePaint.setTextSize(23);

		VehiclesPaint = new Paint();
        VehiclesPaint.setStyle(Paint.Style.FILL);
        VehiclesPaint.setAntiAlias(true);
        VehiclesPaint.setColor(0xFFFFFFFF);
        VehiclesPaint.setTextAlign(Paint.Align.CENTER);
		VehiclesPaint.setFakeBoldText(true);
		VehiclesPaint.setTextSize(18.5f);
		VehiclesPaint.setShadowLayer(2, 0, 0, Color.BLACK);

		雷达红点 = new Paint();
		雷达红点.setStyle(Paint.Style.FILL);
		雷达红点.setStrokeWidth(1);
		雷达红点.setTextSize(14);
		雷达红点.setColor(0xFFFF0000);

		雷达绿点 = new Paint();
		雷达绿点.setStyle(Paint.Style.FILL);
		雷达绿点.setStrokeWidth(1);
		雷达绿点.setTextSize(14);
		雷达绿点.setColor(0xFF00F700);
		
		雷达线 = new Paint();
		雷达线.setStyle(Paint.Style.STROKE);
		雷达线.setAntiAlias(true);
		雷达线.setStrokeWidth(1.5f);
		雷达线.setColor(0xFFFFFFFF);
		
            自瞄圈画笔=new Paint();
            自瞄圈画笔.setStrokeCap(Paint.Cap.ROUND);
            自瞄圈画笔.setStyle(Paint.Style.STROKE);
            自瞄圈画笔.setStrokeWidth(1);
            自瞄圈画笔.setColor(0xFFFF0000);
        
        三角形画笔=new Paint();
        三角形画笔.setDither(true);
        三角形画笔.setAntiAlias(true);
        三角形画笔.setStrokeCap(Paint.Cap.ROUND);
        三角形画笔.setTextSize(20);
        三角形画笔.setTextScaleX(2);
        三角形画笔.setFakeBoldText(true);
        三角形画笔.setTextAlign(Paint.Align.CENTER);
        路径=new Path();
        rectf=new RectF();
      //  路径.addRoundRect(rectf,0,0,Path.Direction.CW);
        
        路径画笔=new Paint();
        路径画笔.setDither(true);
        路径画笔.setAntiAlias(true);
        路径画笔.setStrokeCap(Paint.Cap.ROUND);
        
            血条画笔2=new Paint();
            血条画笔2.setDither(true);
            血条画笔2.setAntiAlias(true);
            血条画笔2.setStrokeCap(Paint.Cap.ROUND);

            血条画笔3=new Paint();
            血条画笔3.setAntiAlias(true);
            血条画笔3.setStrokeCap(Paint.Cap.ROUND);
            血条画笔3.setFakeBoldText(true);
            血条画笔3.setTextSize(20);
            血条画笔3.setColor(0xFFFFFFFF);
            血条画笔3.setStrokeWidth(1.5f);
            血条画笔3.setStyle(Paint.Style.STROKE);
            
        路径画笔1=new Paint();
        路径画笔1.setDither(true);
        路径画笔1.setAntiAlias(true);
        路径画笔1.setColor(0x45000000);
        
        
        距离画笔=new Paint();
        距离画笔.setDither(true);
        距离画笔.setAntiAlias(true);
        距离画笔.setStrokeCap(Paint.Cap.ROUND);
        距离画笔.setTextSize(17);
        距离画笔.setFakeBoldText(true);
        距离画笔.setTextAlign(Paint.Align.CENTER);
        距离画笔.setStyle(Paint.Style.STROKE);
        距离画笔.setStrokeWidth(3);
        距离画笔.setColor(0xff000000);

        距离画笔1=new Paint();
        距离画笔1.setDither(true);
        距离画笔1.setAntiAlias(true);
        距离画笔1.setStrokeCap(Paint.Cap.ROUND);
        距离画笔1.setTextSize(17);
        距离画笔1.setFakeBoldText(true);
        距离画笔1.setTextAlign(Paint.Align.CENTER);
        距离画笔1.setColor(0xFFFFA21A);
        
        随机线 = new Paint();
        随机线.setStyle(Paint.Style.STROKE);
        随机线.setAntiAlias(true);
        随机线.setColor(0xffff0000);
        随机线.setStrokeWidth(1.5f);
        
        背敌画笔=new Paint();
        背敌画笔.setAntiAlias(true);
        背敌画笔.setStrokeCap(Paint.Cap.ROUND);
        
        背敌文字=new Paint();
        背敌文字.setDither(true);
        背敌文字.setAntiAlias(true);
        背敌文字.setStrokeCap(Paint.Cap.ROUND);
        背敌文字.setTextSize(30);
        背敌文字.setFakeBoldText(true);
        背敌文字.setTextAlign(Paint.Align.CENTER);
        背敌文字.setStyle(Paint.Style.STROKE);
        背敌文字.setStrokeWidth(3);
        背敌文字.setColor(0xFF000000);

        背敌文字1=new Paint();
        背敌文字1.setDither(true);
        背敌文字1.setAntiAlias(true);
        背敌文字1.setStrokeCap(Paint.Cap.ROUND);
        背敌文字1.setTextSize(30);
        背敌文字1.setFakeBoldText(true);
        背敌文字1.setTextAlign(Paint.Align.CENTER);
        背敌文字1.setColor(0xFFFFFFFF);
        
        人数画笔哦1=new Paint();
        人数画笔哦1.setDither(true);
        人数画笔哦1.setAntiAlias(true);
        人数画笔哦1.setStrokeCap(Paint.Cap.ROUND);
        人数画笔哦1.setTextSize(30);
        人数画笔哦1.setFakeBoldText(true);
        人数画笔哦1.setTextAlign(Paint.Align.CENTER);
        人数画笔哦1.setStyle(Paint.Style.STROKE);
        人数画笔哦1.setStrokeWidth(0.5f);
        人数画笔哦1.setColor(0x35000000);

        人数画笔哦2=new Paint();
        人数画笔哦2.setDither(true);
        人数画笔哦2.setAntiAlias(true);
        人数画笔哦2.setStrokeCap(Paint.Cap.ROUND);
        人数画笔哦2.setTextSize(30);
        人数画笔哦2.setFakeBoldText(true);
        人数画笔哦2.setTextAlign(Paint.Align.CENTER);
        人数画笔哦2.setColor(0xFF000000);
        
        
        final int bitmap_count_oth = OTHER.length;

        for(int i = 0 ; i < bitmap_count_oth ; i++) {
            OTHER[i] = BitmapFactory.decodeResource(getResources(), OTH_NAME[i]);

           if (i==2){
             OTHER[i] = Bitmap.createScaledBitmap(OTHER[i],40,38,false);           
             
            }else{
                
             OTHER[i] = Bitmap.createScaledBitmap(OTHER[i],200,40,false);   
                 
             }
        }
    
    
       
    }

    public void ClearCanvas(Canvas cvs) {
        cvs.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
    }

    public void FPSCounter(Canvas cvs, float stroke, float posX, float posY, float size) {
        if (SystemClock.uptimeMillis() - mFPSTime > 1000) {
            mFPSTime = SystemClock.uptimeMillis();
            mFPS = mFPSCounter;
            mFPSCounter = 0;
        } else {
            mFPSCounter++;
        }
        cvs.drawText("World Esp", posX, posY, mFpsPaint);
        cvs.drawText("by: Dream ", posX, posY+40, mFpsPaint);
    }    


    public void DrawPlayerBox(Canvas cvs,  float x1, float y1, float x2, float y2) {
      //  mStrokePaint.setColor(colour);
		cvs.drawRect(x1, y1,  x2,  y2, 随机线);
    }

	public void DrawPlayerIng(Canvas cvs, int TeamID) {
		if ( TeamID > 0 && TeamID <=2000) {
            
			mFilledPaint.setColor(Color.parseColor(getColor(TeamID)));
            三角形画笔.setColor(Color.parseColor(getColor(TeamID)));
            路径画笔.setColor(Color.parseColor(getColor(TeamID)));
            随机线.setColor(Color.parseColor(getColor(TeamID)));
            背敌画笔.setColor(Color.parseColor(getColor(TeamID)));
			//mStrokePaint.setColor(Color.parseColor(getColor(TeamID)));
		} else {
			mFilledPaint.setColor(0xCB598434);
			三角形画笔.setColor(0xCB598434);
            路径画笔.setColor(0xCB598434);
            随机线.setColor(0xCB598434);
            背敌画笔.setColor(0xCB598434);
		}
    }

	public void DrawPlayerHealth(Canvas cvs, int hp , float x, float top, float w) {
		rectf.set(x-120,top-57,x+120,top-26);
        路径.addRoundRect(rectf,10,5,Path.Direction.CW);
        cvs.drawPath(路径,路径画笔);
        路径.reset();
        rectf.set(x-120,top-57,x-80,top-28);
        路径.addRoundRect(rectf,10,5,Path.Direction.CW);
        cvs.drawPath(路径,路径画笔1);
        路径.reset();
        
        //cvs.drawRect(x - 95, top - 20,  x - 95 + 1.9f * hp,  top - 57, mFilledPaint);
		//cvs.drawRect(x - 95, top - 20,  x + 95,  top - 57, mStrokePaint2);
        //cvs.drawText(hp+"HP",x+122,top-36,血条画笔3);
        cvs.drawRect(x-120,top-28,x-120+(2.4f*hp),top-27,血条画笔3);
        
        cvs.drawText("▼",x,top -7 , 三角形画笔);
	}

	public void DrawPlayerName(Canvas cvs, String txt, float posX, float posY) {
        cvs.drawText(txt, posX, posY, NamePaint);
    }

	public void DrawPlayerDistance(Canvas cvs, String txt, float posX, float posY) {
		cvs.drawText(txt, posX, posY, 距离画笔);
		cvs.drawText(txt, posX, posY, 距离画笔1);
    }

    
    
    
	public void Draw_ic_Bot(Canvas cvs, float posX, float posY) {
		bitmap = BitmapFactory.decodeResource(getResources(), getRes("ic_boot", "drawable"));
		out = Bitmap.createScaledBitmap(bitmap, 36, 25, false);
		cvs.drawBitmap(
			out, // Bitmap
			posX - 45, // Left
			posY - 52 ,
			null // Paint
		);
    }


	public void DrawPlayerLine(Canvas cvs,  float x1, float y1, float x2, float y2) {
        //mStrokePaint.setColor(colour);
		cvs.drawLine(x1, y1,  x2,  y2, 随机线);
    }

	public void DrawPlayerBone(Canvas cvs, int colour, float x1, float y1, float x2, float y2) {
		BonePaint.setColor(colour);
		cvs.drawLine(x1, y1,  x2,  y2, BonePaint);
    }
    public void DrawCircle(Canvas cvs, int colour, float posX, float posY, float radius) {
        自瞄圈画笔.setColor(colour);
       
        cvs.drawCircle(posX, posY, radius, 自瞄圈画笔);
    }
    

	public void DrawPlayer360Alert(Canvas cvs, int colour, float x, float y, float d) {
        mFillCPaint.setColor(colour);
        cvs.drawCircle(x, y, d, mFillCPaint);
    }
    
    
    public void DrawPlayer360(Canvas cvs,float x,float y,float posX,float posY) {
        //mFillCPaint.setColor(colour);
        //cvs.drawCircle(x, y, mFillCPaint);
        rectf.set(x,y,posX,posY);
        路径.addRoundRect(rectf,10,5,Path.Direction.CW);
        cvs.drawPath(路径,背敌画笔);
        路径.reset();

    }
    
    
    
	public void DrawPlayer360Text(Canvas cvs, String txt, float x, float y) {
		cvs.drawText(txt, x, y, 背敌文字);
		cvs.drawText(txt, x, y, 背敌文字1);
	}

	public void DrawPlayerRadarXY(Canvas cvs, int isBot, float x, float y) {
		if (isBot == 0) {
			cvs.drawText("●", 115+radarLeft + x/10, 115+radarTop + y/10, 雷达红点);
		} else {
			cvs.drawText("●", 115+radarLeft + x/10, 115+radarTop + y/10, 雷达绿点);
		}
	}
	
	public void DrawPlayerRadar(Canvas cvs) {
        cvs.drawCircle(225+radarLeft,225+radarTop,200,雷达线);
        cvs.drawLine(radarLeft+225-200,radarTop+225,radarLeft+225 + 200,radarTop+225,雷达线);
        cvs.drawLine(radarLeft+225,radarTop+225-200,radarLeft+225,radarTop+225+200,雷达线);
        cvs.drawCircle(225+radarLeft,225+radarTop,5,雷达线);
        cvs.drawCircle(225+radarLeft,225+radarTop,50,雷达线);
        cvs.drawCircle(225+radarLeft,225+radarTop,100,雷达线);
        cvs.drawCircle(225+radarLeft,225+radarTop,150,雷达线);
        cvs.drawCircle(225+radarLeft,225+radarTop,200,雷达线);
        
		}
        
        
    public void Drawpictures(Canvas cvs, int image_number, float x, float y) {
        cvs.drawBitmap(OTHER[image_number], x, y,null);
    }

    public void DebugText(String s) {
        System.out.println(s);
    }

    public void DrawText(Canvas cvs, String txt, float posX, float posY) {
      //  mTextPaint.setColor(colour);
       // mTextPaint.setTextSize(size);
        cvs.drawText(txt, posX, posY, 人数画笔哦2);
        cvs.drawText(txt, posX, posY, 人数画笔哦1);
    }

    public static int getRes(String nami, String ganteng) {
        return ctx.getResources().getIdentifier(nami, ganteng, ctx.getPackageName());
    }

	public void DrawVehiclesName(Canvas cvs, int colour, String itemName, float distance, float posX, float posY) {
        VehiclesPaint.setColor(colour);
		String realVehicleName = getVehicleName(itemName);
        if (realVehicleName != null && !realVehicleName.equals("")) {
			cvs.drawText(realVehicleName, posX, posY, VehiclesPaint);
			cvs.drawText("[" + Math.round(distance) + "m]", posX, posY + 20, VehiclesPaint);
		}
        //cvs.drawText(realVehicleName+": "+Math.round(distance)+"m", posX, posY, mTextPaint);
    }

    private String getItemName(String s) {
        //Scopes
        if (s.contains("MZJ_8X")) { mTextPaint.setARGB(255, 247, 99, 245);
            return "8x";
        }

        if (s.contains("MZJ_2X")) { mTextPaint.setARGB(255, 230, 172, 226);
            return "2x";
        }

        if (s.contains("MZJ_HD")) { mTextPaint.setARGB(255, 230, 172, 226);
            return "Red Dot";
        }

        if (s.contains("MZJ_3X")) { mTextPaint.setARGB(255, 247, 99, 245);
            return "3X";
        }

        if (s.contains("MZJ_QX")) { mTextPaint.setARGB(255, 153, 75, 152);
            return "Hollow Sight";
        }

        if (s.contains("MZJ_6X")) { mTextPaint.setARGB(255, 247, 99, 245);
            return "6x";
        }

        if (s.contains("MZJ_4X")) { mTextPaint.setARGB(255, 247, 99, 245);
            return "4x";
        }

        if (s.contains("MZJ_SideRMR")) { mTextPaint.setARGB(255, 153, 75, 152);
            return "Canted Sight";
        }


        //AR and smg
        if (s.contains("AUG")) { mTextPaint.setARGB(255, 52, 224, 63);
            return "AUG";
        }

        if (s.contains("M762")) { mTextPaint.setARGB(255, 43, 26, 28);
            return "M762";
        }

        if (s.contains("SCAR")) { mTextPaint.setARGB(255, 52, 224, 63);
            return "SCAR-L";
        }

        if (s.contains("M416")) { mTextPaint.setARGB(255, 115, 235, 223);
            return "M416";
        }

        if (s.contains("M16A4")) { mTextPaint.setARGB(255, 116, 227, 123);
            return "M16A-4";
        }

        if (s.contains("Mk47")) { mTextPaint.setARGB(255, 247, 99, 245);
            return "Mk47 Mutant";
        }

        if (s.contains("G36")) { mTextPaint.setARGB(255, 116, 227, 123);
            return "G36C";
        }

        if (s.contains("QBZ")) { mTextPaint.setARGB(255, 52, 224, 63);
            return "QBZ";
        }

        if (s.contains("AKM")) { mTextPaint.setARGB(255, 214, 99, 99);
            return "AKM";
        }

        if (s.contains("Groza")) { mTextPaint.setARGB(255, 214, 99, 99);
            return "Groza";
        }

        if (s.contains("PP19")) { mTextPaint.setARGB(255, 255, 246, 0);
            return "Bizon";
        }

        if (s.contains("TommyGun")) { mTextPaint.setARGB(255, 207, 207, 207);
            return "TommyGun";
        }

        if (s.contains("MP5K")) { mTextPaint.setARGB(255, 207, 207, 207);
            return "MP5K";
        }

        if (s.contains("UMP9")) { mTextPaint.setARGB(255, 207, 207, 207);
            return "UMP";
        }

        if (s.contains("Vector")) { mTextPaint.setARGB(255, 255, 246, 0);
            return "Vector";
        }

        if (s.contains("MachineGun_Uzi")) { mTextPaint.setARGB(255, 255, 246, 0);
            return "Uzi";
        }

        if (s.contains("DP28")) { mTextPaint.setARGB(255, 43, 26, 28);
            return "DP28";
        }

        if (s.contains("M249")) { mTextPaint.setARGB(255, 247, 99, 245);
            return "M249";
        }

        //snipers

        if (s.contains("AWM")) { mTextPaint.setColor(Color.BLACK);
            return "AWM";
        }

        if (s.contains("QBU")) { mTextPaint.setARGB(255, 207, 207, 207);
            return "QBU";
        }

        if (s.contains("SLR")) { mTextPaint.setARGB(255, 43, 26, 28);
            return "SLR";
        }

        if (s.contains("SKS")) { mTextPaint.setARGB(255, 43, 26, 28);
            return "SKS";
        }

        if (s.contains("Mini14")) { mTextPaint.setARGB(255, 247, 99, 245);
            return "Mini14";
        }

        if (s.contains("M24")) { mTextPaint.setARGB(255, 247, 99, 245);
            return "M24";
        }

        if (s.contains("Kar98k")) { mTextPaint.setARGB(255, 247, 99, 245);
            return "Kar98k";
        }

        if (s.contains("VSS")) { mTextPaint.setARGB(255, 255, 246, 0);
            return "VSS";
        }

        if (s.contains("Win94")) { mTextPaint.setARGB(255, 207, 207, 207);
            return "Win94";
        }

        if (s.contains("Mk14")) { mTextPaint.setColor(Color.BLACK);
            return "Mk14";
        }

//shotguns and hand weapons
        if (s.contains("S12K")) { mTextPaint.setARGB(255, 153, 109, 109);
            return "S12K";
        }

        if (s.contains("DBS")) { mTextPaint.setARGB(255, 153, 109, 109);
            return "DBS";
        }

        if (s.contains("S686")) { mTextPaint.setARGB(255, 153, 109, 109);
            return "S686";
        }

        if (s.contains("S1897")) { mTextPaint.setARGB(255, 153, 109, 109);
            return "S1897";
        }

        if (s.contains("Sickle")) { mTextPaint.setARGB(255, 102, 74, 74);
            return "Sickle";
        }

        if (s.contains("Machete")) { mTextPaint.setARGB(255, 102, 74, 74);
            return "Machete";
        }

        if (s.contains("Cowbar")) { mTextPaint.setARGB(255, 102, 74, 74);
            return "Cowbar";
        }

        if (s.contains("CrossBow")) { mTextPaint.setARGB(255, 102, 74, 74);
            return "CrossBow";
        }

        if (s.contains("Pan")) { mTextPaint.setARGB(255, 102, 74, 74);
            return "Pan";
        }

        //pistols

        if (s.contains("SawedOff")) { mTextPaint.setARGB(255, 156, 113, 81);
            return "SawedOff";
        }

        if (s.contains("R1895")) { mTextPaint.setARGB(255, 156, 113, 81);
            return "R1895";
        }

        if (s.contains("Vz61")) { mTextPaint.setARGB(255, 156, 113, 81);
            return "Vz61";
        }

        if (s.contains("P92")) { mTextPaint.setARGB(255, 156, 113, 81);
            return "P92";
        }

        if (s.contains("P18C")) { mTextPaint.setARGB(255, 156, 113, 81);
            return "P18C";
        }

        if (s.contains("R45")) { mTextPaint.setARGB(255, 156, 113, 81);
            return "R45";
        }

        if (s.contains("P1911")) { mTextPaint.setARGB(255, 156, 113, 81);
            return "P1911";
        }

        if (s.contains("DesertEagle")) { mTextPaint.setARGB(255, 156, 113, 81);
            return "DesertEagle";
        }


        //Ammo
        if (s.contains("Ammo_762mm")) { mTextPaint.setARGB(255, 92, 36, 28);
            return "7.62";
        }

        if (s.contains("Ammo_45AC")) { mTextPaint.setColor(Color.LTGRAY);
            return "45ACP";
        }

        if (s.contains("Ammo_556mm")) { mTextPaint.setColor(Color.GREEN);
            return "5.56";
        }

        if (s.contains("Ammo_9mm")) { mTextPaint.setColor(Color.YELLOW);
            return "9mm";
        }

        if (s.contains("Ammo_300Magnum")) { mTextPaint.setColor(Color.BLACK);
            return "300Magnum";
        }

        if (s.contains("Ammo_12Guage")) { mTextPaint.setARGB(255, 156, 91, 81);
            return "12 Guage";
        }

        if (s.contains("Ammo_Bolt")) { mTextPaint.setARGB(255, 156, 113, 81);
            return "Arrow";
        }

        //bag helmet vest
        if (s.contains("Bag_Lv3")) { mTextPaint.setARGB(255, 36, 83, 255);
            return "Bag lvl 3";
        }

        if (s.contains("Bag_Lv1")) { mTextPaint.setARGB(255, 127, 154, 250);
            return "Bag lvl 1";
        }

        if (s.contains("Bag_Lv2")) { mTextPaint.setARGB(255, 77, 115, 255);
            return "Bag lvl 2";
        }

        if (s.contains("Armor_Lv2")) { mTextPaint.setARGB(255, 77, 115, 255);
            return "Vest lvl 2";
        }


        if (s.contains("Armor_Lv1")) { mTextPaint.setARGB(255, 127, 154, 250);
            return "Vest lvl 1";
        }


        if (s.contains("Armor_Lv3")) { mTextPaint.setARGB(255, 36, 83, 255);
            return "Vest lvl 3";
        }


        if (s.contains("Helmet_Lv2")) { mTextPaint.setARGB(255, 77, 115, 255);
            return "Helmet lvl 2";
        }

        if (s.contains("Helmet_Lv1")) { mTextPaint.setARGB(255, 127, 154, 250);
            return "Helmet lvl 1";
        }

        if (s.contains("Helmet_Lv3")) { mTextPaint.setARGB(255, 36, 83, 255);
            return "Helmet lvl 3";
        }

        //Healthkits
        if (s.contains("Pills")) { mTextPaint.setARGB(255, 227, 91, 54);
            return "Painkiller";
        }

        if (s.contains("Injection")) { mTextPaint.setARGB(255, 204, 193, 190);
            return "Adrenaline";
        }

        if (s.contains("Drink")) { mTextPaint.setARGB(255, 54, 175, 227);
            return "Energy Drink";
        }

        if (s.contains("Firstaid")) { mTextPaint.setARGB(255, 194, 188, 109);
            return "FirstAidKit";
        }

        if (s.contains("Bandage")) { mTextPaint.setARGB(255, 43, 189, 48);
            return "Bandage";
        }

        if (s.contains("FirstAidbox")) { mTextPaint.setARGB(255, 0, 171, 6);
            return "Medkit";
        }

        //Throwables
        if (s.contains("Grenade_Stun")) { mTextPaint.setARGB(255, 204, 193, 190);
            return "Stung";
        }

        if (s.contains("Grenade_Shoulei")) { mTextPaint.setARGB(255, 2, 77, 4);
            return "Grenade";
        }

        if (s.contains("Grenade_Smoke")) { mTextPaint.setColor(Color.WHITE);
            return "Smoke";
        }

        if (s.contains("Grenade_Burn")) { mTextPaint.setARGB(255, 230, 175, 64);
            return "Molotov";
        }


        //others
        if (s.contains("Large_FlashHider")) { mTextPaint.setARGB(255, 255, 213, 130);
            return "Flash Hider Ar";
        }

        if (s.contains("QK_Large_C")) { mTextPaint.setARGB(255, 255, 213, 130);
            return "Ar Compensator";
        }

        if (s.contains("Mid_FlashHider")) { mTextPaint.setARGB(255, 255, 213, 130);
            return "Flash Hider SMG";
        }

        if (s.contains("QT_A_")) { mTextPaint.setARGB(255, 158, 222, 195);
            return "Tactical Stock";
        }

        if (s.contains("DuckBill")) { mTextPaint.setARGB(255, 158, 222, 195);
            return "DuckBill";
        }

        if (s.contains("Sniper_FlashHider")) { mTextPaint.setARGB(255, 158, 222, 195);
            return "Flash Hider Sniper";
        }

        if (s.contains("Mid_Suppressor")) { mTextPaint.setARGB(255, 158, 222, 195);
            return "Suppressor SMG";
        }

        if (s.contains("HalfGrip")) { mTextPaint.setARGB(255, 155, 189, 222);
            return "Half Grip";
        }


        if (s.contains("Choke")) { mTextPaint.setARGB(255, 155, 189, 222);
            return "Choke";
        }

        if (s.contains("QT_UZI")) { mTextPaint.setARGB(255, 155, 189, 222);
            return "Stock Micro UZI";
        }

        if (s.contains("QK_Sniper")) { mTextPaint.setARGB(255, 60, 127, 194);
            return "Sniper Compensator";
        }

        if (s.contains("Sniper_Suppressor")) { mTextPaint.setARGB(255, 60, 127, 194);
            return "Suppressor Sniper";
        }

        if (s.contains("Large_Suppressor")) { mTextPaint.setARGB(255, 60, 127, 194);
            return "Suppressor Ar";
        }


        if (s.contains("Sniper_EQ_")) { mTextPaint.setARGB(255, 193, 140, 222);
            return "Ex.Qd.Sniper";
        }

        if (s.contains("Mid_Q_")) { mTextPaint.setARGB(255, 193, 163, 209);
            return "Qd.SMG";
        }

        if (s.contains("Mid_E_")) { mTextPaint.setARGB(255, 193, 163, 209);
            return "Ex.SMG";
        }

        if (s.contains("Sniper_Q_")) { mTextPaint.setARGB(255, 193, 163, 209);
            return "Qd.Sniper";
        }

        if (s.contains("Sniper_E_")) { mTextPaint.setARGB(255, 193, 163, 209);
            return "Ex.Sniper";
        }

        if (s.contains("Large_E_")) { mTextPaint.setARGB(255, 193, 163, 209);
            return "Ex.Ar";
        }

        if (s.contains("Large_EQ_")) { mTextPaint.setARGB(255, 193, 140, 222);
            return "Ex.Qd.Ar";
        }

        if (s.contains("Large_Q_")) { mTextPaint.setARGB(255, 193, 163, 209);
            return "Qd.Ar";
        }

        if (s.contains("Mid_EQ_")) { mTextPaint.setARGB(255, 193, 140, 222);
            return "Ex.Qd.SMG";
        }

        if (s.contains("Crossbow_Q")) { mTextPaint.setARGB(255, 148, 121, 163);
            return "Quiver CrossBow";
        }

        if (s.contains("ZDD_Sniper")) { mTextPaint.setARGB(255, 148, 121, 163);
            return "Bullet Loop";
        }


        if (s.contains("ThumbGrip")) { mTextPaint.setARGB(255, 148, 121, 163);
            return "Thumb Grip";
        }

        if (s.contains("Lasersight")) { mTextPaint.setARGB(255, 148, 121, 163);
            return "Laser Sight";
        }

        if (s.contains("Angled")) { mTextPaint.setARGB(255, 219, 219, 219);
            return "Angled Grip";
        }

        if (s.contains("LightGrip")) { mTextPaint.setARGB(255, 219, 219, 219);
            return "Light Grip";
        }

        if (s.contains("Vertical")) { mTextPaint.setARGB(255, 219, 219, 219);
            return "Vertical Grip";
        }

        if (s.contains("GasCan")) { mTextPaint.setARGB(255, 255, 143, 203);
            return "Gas Can";
        }

        if (s.contains("Mid_Compensator")) { mTextPaint.setARGB(255, 219, 219, 219);
            return "Compensator SMG";
        }


        //special
        if (s.contains("Flare")) { mTextPaint.setARGB(255, 242, 63, 159);
            return "Flare Gun";
        }

        if (s.contains("Ghillie")) { mTextPaint.setARGB(255, 139, 247, 67);
            return "Ghillie Suit";
        }
        if (s.contains("CheekPad")) { mTextPaint.setARGB(255, 112, 55, 55);
            return "CheekPad";
        }
        if (s.contains("PickUpListWrapperActor")) { mTextPaint.setARGB(255, 132, 201, 66);
            return "Crate";
        }
        if ((s.contains("AirDropPlane"))) { mTextPaint.setARGB(255, 224, 177, 224);
            return "DropPlane";
        }
        if ((s.contains("AirDrop"))) { mTextPaint.setARGB(255, 255, 10, 255);
            return "AirDrop";
        }
        //return s;
        return null;

    }

    private String getWeapon(int id) {
        if (id == 101006)
            return "AUG";

        if (id == 101008)
            return "M762" ;

        if (id == 101003)
            return "SCAR-L";

        if (id == 101004)
            return "M416";

        if (id == 101002)
            return "M16A-4";

        if (id == 101009)
            return "Mk47 Mutant";

        if (id == 101010)
            return "G36C";

        if (id == 101007)
            return "QBZ";

        if (id == 101001)
            return "AKM";

        if (id == 101005)
            return "Groza";

        if (id == 102005)
            return "Bizon";

        if (id == 102004)
            return "TommyGun";

        if (id == 102007)
            return "MP5K";

        if (id == 102002)
            return "UMP";

        if (id == 102003)
            return "Vector";

        if (id == 102001)
            return "Uzi";

        if (id == 105002)
            return "DP28";

        if (id == 105001)
            return "M249";

        //snipers

        if (id == 103003)
            return "AWM";

        if (id == 103010)
            return "QBU";

        if (id == 103009)
            return "SLR";

        if (id == 103004)
            return "SKS";

        if (id == 103006)
            return "Mini14";

        if (id == 103002)
            return "M24";

        if (id == 103001)
            return "Kar98k";

        if (id == 103005)
            return "VSS";

        if (id == 103008)
            return "Win94";

        if (id == 103007)
            return "Mk14";

//shotguns and hand weapons
        if (id == 104003)
            return "S12K";

        if (id == 104004)
            return "DBS";

        if (id == 104001)
            return "S686";

        if (id == 104002)
            return "S1897";

        if (id == 108003)
            return "Sickle";

        if (id == 108001)
            return "Machete";

        if (id == 108002)
            return "Crowbar";

        if (id == 107001)
            return "CrossBow";

        if (id == 108004)
            return "Pan";

        //pistols

        if (id == 106006)
            return "SawedOff";

        if (id == 106003)
            return "R1895";

        if (id == 106008)
            return "Vz61";

        if (id == 106001)
            return "P92";

        if (id == 106004)
            return "P18C";

        if (id == 106005)
            return "R45";

        if (id == 106002)
            return "P1911";

        if (id == 106010)
            return "DesertEagle";

        return null;

    }

    private String getVehicleName(String s) {
        if (s.contains("Buggy"))
            return "蹦蹦";

        if (s.contains("UAZ"))
            return "吉普";

        if (s.contains("MotorcycleC"))
            return "三轮摩托";

        if (s.contains("Motorcycle"))
            return "摩托";

        if (s.contains("Dacia"))
            return "轿车";

        if (s.contains("AquaRail"))
            return "冲锋艇";

        if (s.contains("PG117"))
            return "船";

        if (s.contains("MiniBus"))
            return "巴士";

        if (s.contains("Mirado"))
            return "跑车";

        if (s.contains("Scooter"))
            return "小绵羊";

        if (s.contains("Rony"))
            return "货车";

        if (s.contains("Snowbike"))
            return "轻型雪地车";

        if (s.contains("Snowmobile"))
            return "重型雪地车";

        if (s.contains("Tuk"))
            return "三轮";

        if (s.contains("PickUp"))
            return "皮卡";

        if (s.contains("BRDM"))
            return "装甲车";

        if (s.contains("LadaNiva"))
            return "拉达";

        if (s.contains("Bigfoot"))
            return "大卡车";

        return "";
    }

    
      
    public static Bitmap scale(Bitmap bitmap, int maxWidth, int maxHeight) {
        // Determine the constrained dimension, which determines both dimensions.
        int width;
        int height;
        float widthRatio = (float)bitmap.getWidth() / maxWidth;
        float heightRatio = (float)bitmap.getHeight() / maxHeight;
        // Width constrained.
        if (widthRatio >= heightRatio) {
            width = maxWidth;
            height = (int)(((float)width / bitmap.getWidth()) * bitmap.getHeight());
        } else {
            height = maxHeight;
            width = (int)(((float)height / bitmap.getHeight()) * bitmap.getWidth());
        }
        Bitmap scaledBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        float ratioX = (float)width / bitmap.getWidth();
        float ratioY = (float)height / bitmap.getHeight();
        float middleX = width / 2.0f;
        float middleY = height / 2.0f;
        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap, middleX - bitmap.getWidth() / 2, middleY - bitmap.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));
        return scaledBitmap;
    }
}


