package com.tencent.world;
import android.content.Context;
import java.io.File;
import android.util.Log;
import java.io.InputStream;
import java.io.IOException;
import java.io.FileOutputStream;
import android.view.WindowManager;
import android.os.Build;
import android.view.Display;
import android.graphics.Point;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
public class Tool {

    public static void runExecutable(Context s, String url, int para) {
		if (para == 1) {
			try {
				Runtime.getRuntime().exec("chmod 777 " + s.getFilesDir() + url, null, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				//System.out.println("su -c " + url + " ");
				Runtime.getRuntime().exec("su -c " + s.getFilesDir() + url, null, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (para == 2) {
            try {
                Runtime.getRuntime().exec("chmod 777 " + s.getFilesDir() + url, null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                //System.out.println("su -c " + url + " ");
                Runtime.getRuntime().exec(s.getFilesDir() + url, null, null);
            } catch (Exception e) {
                e.printStackTrace();
			}  
            
            
            
            
		} else if (para == 3) {
		}
    }

	//写出资源文件
	public static boolean 写出资源文件(Context 上下文, String 输出路径, String 文件名) {
		File 文件=new File(输出路径);
		if (!文件.exists()) {
			if (!文件.mkdirs()) {
				Log.e("--Method--", "copyAssetsSingleFile:cannot create directory.");
				return false;
			}
		}
		try {
			InputStream 字节输入流=上下文.getAssets().open(文件名);
			File 输出数据=new File(文件, 文件名);
			FileOutputStream 输出数据流=new FileOutputStream(输出数据);
			byte[] 字符串=new byte[1024];
			int byteRead;
			while (-1 != (byteRead = 字节输入流.read(字符串))) {
				输出数据流.write(字符串, 0, byteRead);
			}
			字节输入流.close();
			输出数据流.flush();
			输出数据流.close();
			return true;
		} catch (IOException 异常捕捉) {
			异常捕捉.printStackTrace();
			return false;
		}
	}

	public static int 真实宽;//分辨率x
	public static int 真实高;//分辨率y
	public static void 获取手机分辨率(Context 上下文) {
		WindowManager 窗口=(WindowManager)上下文.getSystemService(上下文.WINDOW_SERVICE);
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
	}

	public static int 获取真实宽() {
		return 真实宽;
	}
	//获取屏幕分辨率y轴
	public static int 获取真实高() {
		return 真实高;
	}
    
    public static void 写入(String 路径,String 内容){
        try {
            FileWriter fw = new FileWriter(路径);
            fw.write(内容);
            fw.close();
        } catch (IOException e) {}
    }
	
    public static String 读取(String strFilePath)
    {
        String path = strFilePath;
        String content = ""; //文件内容字符串
        //打开文件
        File file = new File(path);
        //如果path是传递过来的参数，可以做一个非目录的判断
        if (file.isDirectory())
        {
        }
        else
        {
            try {
                InputStream instream = new FileInputStream(file); 
                if (instream != null) 
                {
                    InputStreamReader inputreader = new InputStreamReader(instream);
                    BufferedReader buffreader = new BufferedReader(inputreader);
                    String line;
                    //分行读取
                    while (( line = buffreader.readLine()) != null) {
                        content += line ;
                    }                
                    instream.close();
                }
            }
            catch (java.io.FileNotFoundException e) 
            {
            } 
            catch (IOException e) 
            {
            }
            
            
            
        }
        return content;
    }
    
    public static String 读取文件(String path) {
        String str = "";
        try {
            File urlFile = new File(path);
            InputStreamReader isr = new InputStreamReader(new FileInputStream(urlFile), "UTF-8");
            BufferedReader br = new BufferedReader(isr);

            String mimeTypeLine = null;
            while ((mimeTypeLine = br.readLine()) != null) {
                str = str + mimeTypeLine;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }
    
    public static void 调用二进制(Context context, int ZTSZ, String strshell) {
        if (ZTSZ == 1) {
            try {
               // 写出资源文件(this, getFilesDir() + "/", ""+wj);
                Runtime.getRuntime().exec("chmod 777 " + context.getFilesDir().getPath() +"/"+ strshell, null, null);
                Runtime.getRuntime().exec("su -c " + context.getFilesDir().getPath() +"/"+ strshell, null, null);
                Runtime.getRuntime().exec(context.getFilesDir().getPath() +"/"+ strshell, null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ZTSZ == 2) {
            try {
                Runtime.getRuntime().exec("chmod 777 " + context.getFilesDir().getPath() +"/"+ strshell, null, null);
                Runtime.getRuntime().exec(context.getFilesDir().getPath() +"/"+ strshell, null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

        }

    }
    
    
    
    
    
}
