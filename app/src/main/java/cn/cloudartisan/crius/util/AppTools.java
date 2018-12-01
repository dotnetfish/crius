package cn.cloudartisan.crius.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.*;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AppTools
{
    private static int computeInitialSampleSize(BitmapFactory.Options paramOptions, int paramInt1, int paramInt2)
    {
        double d1 = paramOptions.outWidth;
        double d2 = paramOptions.outHeight;
        int i;
        int j=0;
        if (paramInt2 == -1)
        {
            i = 1;
            if (paramInt1 != -1) {
                i = (int)Math.ceil(Math.sqrt(d1 * d2 / paramInt2));
            }
            j = 128;
            if ((paramInt2 == -1) && (paramInt1 == -1)) {
                return 1;
            }
            if (j >= i) {
                j = (int)Math.min(Math.floor(d1 / paramInt1), Math.floor(d2 / paramInt1));
            }

        }

        return j;
    }

    public static int computeSampleSize(BitmapFactory.Options paramOptions, int paramInt1, int paramInt2)
    {
        paramInt2 = computeInitialSampleSize(paramOptions, paramInt1, paramInt2);
        if (paramInt2 <= 8)
        {
            paramInt1 = 1;
            for (;;)
            {
                if (paramInt1 >= paramInt2) {
                    return paramInt1;
                }
                paramInt1 <<= 1;
            }
        }
        return (paramInt2 + 7) / 8 * 8;
    }

    public static boolean contains(int[] paramArrayOfInt, MotionEvent paramMotionEvent)
    {
        return (paramMotionEvent.getY() >= paramArrayOfInt[1]) && (paramMotionEvent.getY() <= paramArrayOfInt[3]) && (paramMotionEvent.getX() >= paramArrayOfInt[0]) && (paramMotionEvent.getX() <= paramArrayOfInt[2]);
    }

    public static String getDateTimeString(long paramLong)
    {
        return new SimpleDateFormat("yyy-MM-dd HH:mm:ss").format(new Date(paramLong));
    }

    public static String getDay(long paramLong)
    {
        return new SimpleDateFormat("dd").format(new Date(paramLong));
    }

    public static double getDistance(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
    {
        paramDouble2 = 3.141592653589793D * paramDouble2 / 180.0D;
        paramDouble4 = 3.141592653589793D * paramDouble4 / 180.0D;
        paramDouble1 = 3.141592653589793D * paramDouble1 / 180.0D;
        paramDouble3 = 3.141592653589793D * paramDouble3 / 180.0D;
        return Math.round(10000.0D * (2.0D * Math.asin(Math.sqrt(Math.pow(Math.sin((paramDouble2 - paramDouble4) / 2.0D), 2.0D) + Math.cos(paramDouble2) * Math.cos(paramDouble4) * Math.pow(Math.sin((paramDouble1 - paramDouble3) / 2.0D), 2.0D))) * 6378.137D)) / 10L;
    }

    public static String getIMEI(Context paramContext)
    {
        return ((TelephonyManager)paramContext.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
    }

    public static String getMonth(long paramLong)
    {
        return new SimpleDateFormat("MM").format(new Date(paramLong));
    }

    public static String getSignature(Context paramContext)
    {
        try
        {
            return paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 64).signatures[0].toCharsString();

        }
        catch (PackageManager.NameNotFoundException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    public static String getTimeString(long paramLong)
    {
        return new SimpleDateFormat("MM-dd HH:mm:ss").format(new Date(paramLong));
    }

    public static int getVersionCode(Context paramContext)
    {
        try
        {
            int i = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 0).versionCode;
            return i;
        }
        catch (PackageManager.NameNotFoundException ex)
        {
            ex.printStackTrace();
        }
        return 1;
    }

    public static String howTimeAgo(Context paramContext, long paramLong)
    {
        Time localTime1 = new Time();
        localTime1.set(paramLong);
        Time localTime2 = new Time();
        localTime2.setToNow();
        int i;
        if (localTime1.year != localTime2.year) {
            i = 0x80B00 | 0x14;
        }
        if (localTime1.yearDay != localTime2.yearDay) {
            i = 0x80B00 | 0x10;
        } else {
            i = 0x80B00 | 0x1;
        }

            return DateUtils.formatDateTime(paramContext, paramLong, i | 0x11);


    }

    public static void measureView(View paramView)
    {
        ViewGroup.LayoutParams var4 = paramView.getLayoutParams();
        ViewGroup.LayoutParams var3 = var4;
        if(var4 == null) {
            var3 = new ViewGroup.LayoutParams(-1, -2);
        }

        int var2 = ViewGroup.getChildMeasureSpec(0, 0, var3.width);
        int var1 = var3.height;
        if(var1 > 0) {
            var1 = View.MeasureSpec.makeMeasureSpec(var1, View.MeasureSpec.AT_MOST);
        } else {
            var1 = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.EXACTLY);
        }

        paramView.measure(var2, var1);
    }

    public static boolean netWorkAvailable(Context paramContext)
    {
        boolean bool = false;
        try
        {
            NetworkInfo networkInfo = ((ConnectivityManager)paramContext.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            if (networkInfo != null) {
                bool = true;
            }
            return bool;
        }
        catch (Exception ex) {}
        return false;
    }

    public static void startPhotoZoom(Activity var0, Uri var1) {
        Intent var2 = new Intent("com.android.camera.action.CROP");
        var2.setDataAndType(var1, "image/*");
        var2.putExtra("crop", "true");
        var2.putExtra("aspectX", 1);
        var2.putExtra("aspectY", 1);
        var2.putExtra("outputX", 256);
        var2.putExtra("outputY", 256);
        var2.putExtra("scale", true);
     var2.putExtra(MediaStore.EXTRA_OUTPUT,var1);
        var2.putExtra("return-data", false);
        var2.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        var2.putExtra("noFaceDetection", false);

        var0.startActivityForResult(var2, 11);
    }
  //  public static void startPhotoZoom(Activity paramActivity, Uri paramUri)
   // {

        /*Intent localIntent = new Intent("com.android.camera.action.CROP");
        localIntent.setDataAndType(paramUri, "image*//*");
        localIntent.putExtra("crop", "true");
        localIntent.putExtra("aspectX", 1);
        localIntent.putExtra("aspectY", 1);
        localIntent.putExtra("outputX", 300);
        localIntent.putExtra("outputY", 300);
        localIntent.putExtra("return-data", true);
        paramActivity.startActivityForResult(localIntent, 11);*/
   // }

    public static Bitmap toGrayscale(Bitmap paramBitmap)
    {
        Bitmap localBitmap = Bitmap.createBitmap(paramBitmap.getWidth(), paramBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas localCanvas = new Canvas(localBitmap);
        Paint localPaint = new Paint();
        ColorMatrix localColorMatrix = new ColorMatrix();
        localColorMatrix.setSaturation(0.0F);
        localPaint.setColorFilter(new ColorMatrixColorFilter(localColorMatrix));
        localCanvas.drawBitmap(paramBitmap, 0.0F, 0.0F, localPaint);
        return localBitmap;
    }

    public static String transformDistance(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
    {
        paramDouble1 = getDistance(paramDouble1, paramDouble2, paramDouble3, paramDouble4);
        DecimalFormat localDecimalFormat = new DecimalFormat("0.00");
        if (paramDouble1 > 1000.0D) {
            return localDecimalFormat.format(paramDouble1 / 1000.0D) + "公里";
        }
        return new BigDecimal(paramDouble1, new MathContext(0, RoundingMode.HALF_DOWN)).intValue() + "米";
    }
}

