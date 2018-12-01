/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package cn.cloudartisan.crius.ui.helper;

import android.content.Context;
import cn.cloudartisan.crius.util.FileTypeIconBuilder;
import android.content.Intent;
import java.io.File;
import android.net.Uri;
import org.apache.commons.io.FilenameUtils;

public class FileViewHelper {
    static Context context;
    static FileViewHelper helper;
    
    public static FileViewHelper create(Context c) {
        if(helper == null) {
            context = c;
            helper = new FileViewHelper();
            FileTypeIconBuilder.create(c);
        }
        return helper;
    }
    
    public Intent openFile(String fileName) {
        String prefix = FilenameUtils.getExtension(fileName).toLowerCase();
        if(FileTypeIconBuilder.fileTypesOfDoc.contains(prefix)) {
            return getWordFileIntent(fileName);
        }
        if(FileTypeIconBuilder.fileTypesOfPPT.contains(prefix)) {
            return getPptFileIntent(fileName);
        }
        if(FileTypeIconBuilder.fileTypesOfExcel.contains(prefix)) {
            return getExcelFileIntent(fileName);
        }
        if(FileTypeIconBuilder.fileTypesOfPDF.contains(prefix)) {
            return getPdfFileIntent(fileName);
        }
        if(FileTypeIconBuilder.fileTypesOfAPK.contains(prefix)) {
            return getApkFileIntent(fileName);
        }
        if(FileTypeIconBuilder.fileTypesOfHtml.contains(prefix)) {
            return getHtmlFileIntent(fileName);
        }
        if(FileTypeIconBuilder.fileTypesOfText.contains(prefix)) {
            return getTextFileIntent(fileName);
        }
        if(FileTypeIconBuilder.fileTypesOfZIP.contains(prefix)) {
            return getZipFileIntent(fileName);
        }
        if(FileTypeIconBuilder.fileTypesOfImage.contains(prefix)) {
            return getImageFileIntent(fileName);
        }
        if(FileTypeIconBuilder.fileTypesOfVoice.contains(prefix)) {
            return getAudioFileIntent(fileName);
        }
        if(FileTypeIconBuilder.fileTypesOfVideo.contains(prefix)) {
            return getVideoFileIntent(fileName);
        }
        return getAllIntent(fileName);
    }
    
    public static Intent getAllIntent(String param) {
        Intent intent = new Intent();
        intent.addFlags(0x10000000);
        intent.setAction("android.intent.action.VIEW");
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "*/*");
        return intent;
    }
    
    public static Intent getApkFileIntent(String param) {
        Intent intent = new Intent();
        intent.addFlags(0x10000000);
        intent.setAction("android.intent.action.VIEW");
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        return intent;
    }
    
    public static Intent getAudioFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(0x4000000);
        intent.putExtra("oneshot", 0x0);
        intent.putExtra("configchange", 0x0);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "audio/*");
        return intent;
    }
    
    public static Intent getVideoFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(0x4000000);
        intent.putExtra("oneshot", 0x0);
        intent.putExtra("configchange", 0x0);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "video/*");
        return intent;
    }
    
    public static Intent getHtmlFileIntent(String param) {
        Uri uri = Uri.parse(param).buildUpon().encodedAuthority("com.android.htmlfileprovider").scheme("content").encodedPath(param).build();
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(uri, "text/html");
        return intent;
    }
    
    public static Intent getImageFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(0x10000000);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "image/*");
        return intent;
    }
    
    public static Intent getPptFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(0x10000000);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        return intent;
    }
    
    public static Intent getExcelFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(0x10000000);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/vnd.ms-excel");
        return intent;
    }
    
    public static Intent getWordFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(0x10000000);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/msword");
        return intent;
    }
    
    public static Intent getZipFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(0x10000000);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/zip");
        return intent;
    }
    
    public static Intent getTextFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(0x10000000);
        Uri uri2 = Uri.fromFile(new File(param));
        intent.setDataAndType(uri2, "text/plain");
        return intent;
    }
    
    public static Intent getPdfFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(0x10000000);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/pdf");
        return intent;
    }
}