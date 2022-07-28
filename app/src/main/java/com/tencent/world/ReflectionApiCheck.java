package com.tencent.world;

import android.os.Build;
import android.util.Log;

import java.lang.reflect.Method;

import static android.os.Build.VERSION.SDK_INT;

public class ReflectionApiCheck {

    private static final String TAG = "ReflectionApiCheck";

    private static Object sVmRuntime;
    private static Method setHiddenApiExemptions;

    static {
        if (SDK_INT >= Build.VERSION_CODES.P) {
            try {
                Method forName = Class.class.getDeclaredMethod("forName", String.class);
                Method getDeclaredMethod = Class.class.getDeclaredMethod("getDeclaredMethod", String.class, Class[].class);

                Class<?> vmRuntimeClass = (Class<?>) forName.invoke(null, "dalvik.system.VMRuntime");
                Method getRuntime = (Method) getDeclaredMethod.invoke(vmRuntimeClass, "getRuntime", null);
                setHiddenApiExemptions = (Method) getDeclaredMethod.invoke(vmRuntimeClass, "setHiddenApiExemptions", new Class[]{String[].class});
                sVmRuntime = getRuntime.invoke(null);
            } catch (Throwable e) {
                Log.e(TAG, "reflect bootstrap failed:", e);
            }
        }
    }


    private static final int ERROR_EXEMPT_FAILED = -21;

    public static int unseal() {
        if (SDK_INT < 28) {
            return 0;
        }
        if (exemptAll()) {
            return 0;
        } else {
            return ERROR_EXEMPT_FAILED;
        }
    }
    public static boolean exempt(String method) {
        return exempt(new String[]{method});
    }
    public static boolean exempt(String... methods) {
        if (sVmRuntime == null || setHiddenApiExemptions == null) {
            return false;
        }

        try {
            setHiddenApiExemptions.invoke(sVmRuntime, new Object[]{methods});
            return true;
        } catch (Throwable e) {
            return false;
        }
    }
    public static boolean exemptAll() {
        return exempt(new String[]{"L"});
    }
}
