
package cn.cloudartisan.crius.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.util.CrashLogUtils;
import cn.cloudartisan.crius.util.FileUtil;
import cn.cloudartisan.crius.util.GlobalExceptionListener;

//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.util.*;


public class CriusApplication extends Application {
    private static CriusApplication _context;
    public static List<String> emoticonList = new ArrayList();
    public static Map<String, Integer> emoticonsIdMap = new HashMap();
    public static List<String> emoticonKeyList = new ArrayList();
    public static String GLOBAL_MODEL_KEY = "GLOBAL_MODEL_KEY";
    public static String API_AUTH_KEY = "API_AUTH_KEY";
    public static String OSS_ACCESS_ID = "OSS_ACCESS_ID";
    public static String OSS_ACCESS_KEY = "OSS_ACCESS_KEY";
    //private  ApplicationContext bbitContext=null;
    public void onCreate() {
        super.onCreate();
       // SDKInitializer.initialize(this);
        GlobalExceptionListener.getInstance().init(this);
        FileUtil.creatSDDir(Constant.CACHE_DIR_IMAGE);
        FileUtil.creatSDDir(Constant.DOWNLOAD_DIR);
        FileUtil.creatSDDir(Constant.LOG_DIR);
        FileUtil.creatSDDir(Constant.CACHE_DIR_VOICE);
        _context = this;
     /* JNI jni = new JNI();
        setApiAuthKey(jni.getAuthKey(_context));
        setOssAccessID(jni.getOSSAccessID(_context));
        setOssAccessKey(jni.getOSSAccessKey(_context));
        setApiAuthKey("0CC175B9C0F1B6A831C399E269772661");
        setOssAccessID("2yKc1XA77H0LxdFx");
        setOssAccessKey("6xiwP6D5wVkpOIcjlttnjLYdNBeMVL");
        initOSSConfig();*/
        initImageLoader();
        initEmotions();
        CrashLogUtils.getInstace().uploadCrashLogFile();
       // bbitContext = new ClassPathXmlApplicationContext("biz-sao-client.xml");

    }
   // public  ApplicationContext getBBitContext(){
     //   return bbitContext;
   // }
    private void initOSSConfig() {
       /* OSSClient.setGlobalDefaultTokenGenerator(new TokenGenerator() {

            public String generateToken(String httpMethod, String md5, String type, String date, String ossHeaders, String resource) {
                String content = "\n" + md5 + "\n" + type + "\n" + date;
                localStringBuilder1 = "\n" + md5 + "\n" + type + "\n" + date.append("\n").append(ossHeaders).append(resource);
                String ossAccessID = getOssAccessID();
                String ossAccessKey = getOssAccessKey();
                String token = OSSToolKit.generateToken(ossAccessID, ossAccessKey, content);
                return token;
            }
        });
        OSSClient.setGlobalDefaultACL(AccessControlList.PUBLIC_READ);
        OSSClient.setGlobalDefaultHostId("oss-cn-hangzhou.alihttp://developer.yljr888.com/cs.com");
        OSSClient.setApplicationContext(getApplicationContext());*/
    }
    
    public void initImageLoader() {

       ImageLoaderConfiguration.Builder localImageLoader1 = new ImageLoaderConfiguration.Builder(this);
        localImageLoader1 = localImageLoader1.threadPriority(0x3);
        localImageLoader1 = localImageLoader1.diskCache(new UnlimitedDiskCache(new File(Constant.CACHE_DIR_IMAGE),
                new File(Constant.CACHE_DIR_IMAGE), new Md5FileNameGenerator()));
        localImageLoader1 = localImageLoader1.denyCacheImageMultipleSizesInMemory();
        localImageLoader1 = localImageLoader1.diskCacheFileCount(0x2710);
        localImageLoader1 = localImageLoader1.tasksProcessingOrder(QueueProcessingType.FIFO);
        localImageLoader1 = localImageLoader1.memoryCache(new WeakMemoryCache());
        localImageLoader1 = localImageLoader1.threadPoolSize(0x3);
        ImageLoaderConfiguration config = localImageLoader1.build();
        ImageLoader.getInstance().init(config);
    }
    
    public static void setApiAuthKey(String key) {
        SharedPreferences sp = _context.getSharedPreferences(GLOBAL_MODEL_KEY, 0x0);
        sp.edit().putString(API_AUTH_KEY, key).commit();
    }
    
    public static String getApiAuthKey() {
        SharedPreferences sp = _context.getSharedPreferences(GLOBAL_MODEL_KEY, 0x0);
        return sp.getString(API_AUTH_KEY, null);
    }
    
    public static void setOssAccessID(String key) {
        SharedPreferences sp = _context.getSharedPreferences(GLOBAL_MODEL_KEY, 0x0);
        sp.edit().putString(OSS_ACCESS_ID, key).commit();
    }
    
    public static String getOssAccessID() {
        SharedPreferences sp = _context.getSharedPreferences(GLOBAL_MODEL_KEY, 0x0);
        return sp.getString(OSS_ACCESS_ID, null);
    }
    
    public static void setOssAccessKey(String key) {
        SharedPreferences sp = _context.getSharedPreferences(GLOBAL_MODEL_KEY, 0x0);
        sp.edit().putString(OSS_ACCESS_KEY, key).commit();
    }
    
    public static String getOssAccessKey() {
        SharedPreferences sp = _context.getSharedPreferences(GLOBAL_MODEL_KEY, 0x0);
        return sp.getString(OSS_ACCESS_KEY, null);
    }
    public static List<Activity> aliveActivitys = new ArrayList();
    
    public static void finishAllActivity() {
        for(int var0 = 0; var0 < aliveActivitys.size(); ++var0) {
            if(aliveActivitys.get(var0) != null) {
                aliveActivitys.get(var0).finish();
            }
        }
    }
    
    public static void finishTarget(Class<?> clzs) {
        for(int var1 = 0; var1 < aliveActivitys.size(); ++var1) {
            if(((Activity)aliveActivitys.get(var1)).getClass() == clzs) {
               aliveActivitys.get(var1).finish();
                return;
            }
        }
    }
    
    public static String getTopActivity() {
        ActivityManager activityManager = (ActivityManager)_context.getSystemService(Context.ACTIVITY_SERVICE);
        String runningActivity = (activityManager.getRunningTasks(0x1).get(0x0)).topActivity.getClassName();
        return runningActivity;
    }
    
    private void initEmotions() {
        emoticonList.addAll(Arrays.asList(getResources().getStringArray(R.array.emoticos)));
        emoticonKeyList.addAll(Arrays.asList(getResources().getStringArray(R.array.emoticoKeys)));
int index=0;
        for(String emotions:emoticonKeyList){
    int emoticonsId = getResources().getIdentifier(emoticonList.get(index), "drawable", getPackageName());
    emoticonsIdMap.put(emotions,emoticonsId);
            index++;
}

    }
    
    public static CriusApplication getInstance() {
        return _context;
    }
}
