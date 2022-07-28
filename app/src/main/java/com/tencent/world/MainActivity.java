package com.tencent.world;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;
import com.tencent.world.Leftserver;
import java.io.DataOutputStream;
import java.io.IOException;
import org.w3c.dom.Text;
import android.widget.TextView;
import java.net.URL;
import java.net.URLConnection;
import okhttp3.Response;
import java.io.File;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import android.widget.EditText;

 
//System.loadLibrary("Rubel");
    

public class MainActivity extends Activity { 

    
    public Button 开启辅助,关闭辅助;
    public static String GameVersion;
    public static boolean StartMode;
    public String StartName;
    public CheckBox Root模式;
    public TextView 验证;
    
    public static String 验证内容;
    public String 验证密码;
    public String 密码内容;
    public String 电报验证;
    public String Name;
    public RadioButton 和平精英;
   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        绑定资源ID();
       
        点击事件();
        
        getReadPermissions();
          
       
       // 密码内容=Tool.读取文件("/sdcard/imei");
      
        Tool.写出资源文件(this,getFilesDir().getPath()+"/","yz");
     //   验证卡密("https://wds.ecsxs.com/2273.html");
     //   new File("/sdcard/imei").delete();
       
        
        if(Integer.parseInt(Build.VERSION.SDK) >=31){
            Toast.makeText(getApplication(), "已检测到您为安卓12 已为您修改信用触摸点！", Toast.LENGTH_SHORT).show();
            try {
                Runtime.getRuntime().exec("settings put global block_untrusted_touches 0");
            } catch (Exception e) {
                e.printStackTrace();
            }
            
         
     }
     
     
        
           
       

           Tool.runExecutable(this,"/yz",1);
            // Tool.调用二进制(this,1,"/yz");
            // Tool.runExecutable(MainActivity.this,1,"");
       
        
       
        
        /*
         Tool.写出资源文件(this, getFilesDir() + "/assets", "Son_non_fish");
         if (!Settings.canDrawOverlays(this)) {
         Toast.makeText(this, "请授权应用悬浮窗权限", Toast.LENGTH_LONG).show();
         startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), 0);
         }
         try {//申请root权限
         Runtime.getRuntime().exec("su", null, null);
         } catch (IOException e) {}
         //打开悬浮窗
         this.startService(new Intent(this, FloatingModMenuService.class));
         */
    }
    
    public void 点击事件(){
        Root模式.setChecked(getConfig("StartMode"));
        Root模式.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        if (checkRoot()) {
                            StartMode = true;
                            setValue("StartMode", true);
                            Toast.makeText(getApplication(), "已获取Root权限", 0).show();
                        } else {
                            StartMode = false;
                            setValue("StartMode", false);
                            Toast.makeText(getApplication(), "未获取Root权限玩你妈逼xxs", 0).show();
                        }
                    } else {
                        setValue("StartMode", false);
                        StartMode = false;
                    }
                }
            }); 
    }
    public void Click(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.开启辅助:  
              
                      
                
                    开启悬浮窗();
                    break;
                    
              
            case R.id.关闭辅助:
                Leftserver.isDraw =false;
                Leftserver.Close();
                
                stopService(new Intent(this, Leftserver.class));
                break;
            /*case R.id.和平精英:
                GameVersion = "和平精英";  
                setValue(String.valueOf(GameVersion), 和平精英.isChecked());
                
                break;*/

        }
    }

    public void 开启悬浮窗() {
        电报验证=Tool.读取文件("/sdcard/xml.dat");
      //  验证内容 = Tool.读取文件("/sdcard/Android/data/org.telegram.messenger.web/cache/-6136233442837376537_99.jpg");
        if (!Settings.canDrawOverlays(this)) {
            Toast.makeText(this, "请授权应用悬浮窗权限", Toast.LENGTH_LONG).show();
            startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), 0);
        }
       
        //打开悬浮窗
       if (fileIsExists("/sdcard/xml.dat") && 电报验证.equals("success")){
                
           Toast.makeText(getApplication(), "验证已通过", Toast.LENGTH_SHORT).show();  
            验证.setText("验证:成功");
          //  Tool.写入("/sdcard/imei","");
            Toast.makeText(getApplication(), "下次验证请重新打开群聊再次验证！！", Toast.LENGTH_SHORT).show();
            this.startService(new Intent(this, Leftserver.class));
            
        }
        else{

            Toast.makeText(getApplication(), "验证未通过或者请重新打开再试！", Toast.LENGTH_SHORT).show();  
            验证.setText("验证:失败");
            


        }

        
                    
       
        
    }
    
    
    
    
    
    private boolean checkRoot() {
        Process process=null;
        DataOutputStream os=null;//声明执行结果
        int exitValue=1;
        try {
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes("exit\n");//退出
            os.flush();
            exitValue = process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (os != null) {
                try {
                    os.close();//关闭输出流
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            process.destroy();
        }
        return exitValue == 0;
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
    
    public boolean fileIsExists(String strFile) {
        try {
            File f=new File(strFile);
            if(!f.exists()) {
                return false;
            }
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
    
    public static void deleteFile(String path){
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }
    
    public void 绑定资源ID() {
        开启辅助 = findViewById(R.id.开启辅助);
        关闭辅助 = findViewById(R.id.关闭辅助);  
        Root模式 = findViewById(R.id.Root模式);
        验证 = findViewById(R.id.验证);
     
        
        //和平精英 = findViewById(R.id.和平精英);
        
    }
    private void getReadPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED
                | ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS) | ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {//是否请求过该权限
                    ActivityCompat.requestPermissions(this,
                                                      new String[]{Manifest.permission.RECEIVE_SMS,
                                                          Manifest.permission.READ_SMS,
                                                          Manifest.permission.READ_PHONE_STATE,
                                                          Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                                          Manifest.permission.READ_EXTERNAL_STORAGE}, 10001);
                } else {//没有则请求获取权限，示例权限是：存储权限和短信权限，需要其他权限请更改或者替换
                    ActivityCompat.requestPermissions(this,
                                                      new String[]{Manifest.permission.RECEIVE_SMS,
                                                          Manifest.permission.READ_SMS,
                                                          Manifest.permission.READ_PHONE_STATE,
                                                          Manifest.permission.READ_EXTERNAL_STORAGE,
                                                          Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10001);
                }
            } else {//如果已经获取到了权限则直接进行下一步操作
                //   Log.e(TAG, "onRequestPermissionsResult");
            }
        }

    }
    
    
    
    
   
         
    
    
    
    
    
    
}
