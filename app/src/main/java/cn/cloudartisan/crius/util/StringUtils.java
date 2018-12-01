package cn.cloudartisan.crius.util;

import cn.cloudartisan.crius.app.Constant;
import com.nostra13.universalimageloader.utils.L;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class StringUtils
{
    public static String filterOffUtf8Mb4(String paramString)
            throws UnsupportedEncodingException
    {
        return paramString.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "(^_^)");
    }

    public static String generate(String paramString)
    {
        return new BigInteger(getMD5(paramString.getBytes())).abs().toString(36);
    }

    public static String getCurrentlyDate()
    {
        return new SimpleDateFormat("yyyyMMdd").format(new Date());
    }

    public static String getCurrentlyDateTime()
    {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    private static byte[] getMD5(byte[] paramArrayOfByte)
    {
        try
        {
            MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
            localMessageDigest.update(paramArrayOfByte);
            paramArrayOfByte = localMessageDigest.digest();
            return paramArrayOfByte;
        }
        catch (NoSuchAlgorithmException ex)
        {
            L.e(ex);
        }
        return null;
    }

    public static String getOSSFileURI(String paramString1, String paramString2)
    {
        File localFile = new File(Constant.CACHE_DIR_IMAGE, paramString2);
        if (localFile.exists()) {
            return "file://" + localFile.getAbsoluteFile();
        }
        return FileURLBuilder.getFileUrl(paramString1, paramString2);
    }

    public static String getSequenceId()
    {
        return String.valueOf(System.currentTimeMillis());
    }

    public static String getUUID()
    {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static boolean isBlank(Object paramObject)
    {
        return (paramObject == null) || ("".equals(paramObject.toString()));
    }

    public static boolean isEmpty(Object paramObject)
    {
        return (paramObject == null) || ("".equals(paramObject.toString().trim()));
    }

    public static boolean isNotEmpty(Object paramObject)
    {
        return !isEmpty(paramObject);
    }

    public static String transformDateTime(long paramLong)
    {
        Date localDate = new Date(paramLong);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(localDate);
    }

    public static String transformDateTime(long paramLong, String paramString)
    {
        Date localDate = new Date(paramLong);
        return new SimpleDateFormat(paramString).format(localDate);
    }

    public static String transformDateTime(String paramString1, String paramString2)
    {
        Date date = new Date(Long.valueOf(paramString1).longValue());
        return new SimpleDateFormat(paramString2).format(date);
    }
}

