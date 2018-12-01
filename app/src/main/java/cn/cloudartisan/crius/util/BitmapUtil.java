package cn.cloudartisan.crius.util;

import android.graphics.Bitmap;
import cn.cloudartisan.crius.app.Constant;
import org.apache.commons.io.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class BitmapUtil
{
    public static int MAX_SIDE = 1080;
    public static int MAX_SIZE = 204800;

    public static Bitmap compressionAndSavePhoto(Bitmap var0, File var1)
    {
        int var2 = var0.getRowBytes();
        int var4 = var0.getHeight();
        ByteArrayOutputStream var6 = new ByteArrayOutputStream();
        byte var3 = 100;
        Bitmap var5 = var0;
        if(var2 * var4 > MAX_SIZE) {
            if(var0.getWidth() > var0.getHeight()) {
                if(var0.getWidth() > MAX_SIDE) {
                    var2 = MAX_SIDE;
                } else {
                    var2 = var0.getWidth();
                }

                var0 = Bitmap.createScaledBitmap(var0, var2, var0.getHeight() * var2 / var0.getWidth(), true);
            } else {
                if(var0.getHeight() > MAX_SIDE) {
                    var2 = MAX_SIDE;
                } else {
                    var2 = var0.getHeight();
                }

                var0 = Bitmap.createScaledBitmap(var0, var0.getWidth() * var2 / var0.getHeight(), var2, true);
            }

            var0.compress(Bitmap.CompressFormat.JPEG, 100, var6);

            for(var2 = var3; var6.toByteArray().length > MAX_SIZE; var2 -= 5) {
                var6.reset();
                var0.compress(Bitmap.CompressFormat.JPEG, var2, var6);
            }

            var5 = var0;
        }

        try {
            var1.createNewFile();
            FileUtils.writeByteArrayToFile(var1, var6.toByteArray());
            return var5;
        } catch (Exception var7) {
            var7.printStackTrace();
            return var5;
        }
    }

    public static Bitmap getThumbnail(Bitmap var0, String var1)
    {
        int var3 = var0.getWidth();
        int var4 = var0.getHeight();
        if(var3 >= 100 && var4 >= 100) {
            int var2;
            if(var3 >= var4) {
                var2 = 100;
                var3 = var3 * 100 / var4;
            } else {
                var2 = var4 * 100 / var3;
                var3 = 100;
            }

            var0 = Bitmap.createScaledBitmap(var0, var3, var2, true);
            File var6 = new File(Constant.CACHE_DIR_IMAGE + "/" + var1);

            try {
                var0.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(var6));
                return var0;
            } catch (FileNotFoundException var5) {
                var5.printStackTrace();
                return var0;
            }
        } else {
            return var0;
        }
        }

}
