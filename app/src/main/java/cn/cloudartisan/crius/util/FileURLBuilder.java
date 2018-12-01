/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package cn.cloudartisan.crius.util;


public class FileURLBuilder {
    public static final String BUCKET_CRASH_LOG = "lvxin-crash-log";
    public static final String BUCKET_FILES = "lvxin-files";
    public static final String BUCKET_GROUP_ICON = "lvxin-group-icon";
    public static final String BUCKET_PUBACCOUNT_ICON = "lvxin-pubaccount-logo";
    public static final String BUCKET_USER_ICON = "api/sqcp/user/getAvatar?userUid=";
    public static final String OSS_FILE_URL = "http://202.127.25.114:8081/%s";

    public static String getFileUrl(String account) {
        return String.format(OSS_FILE_URL, BUCKET_FILES) + account;
    }

    public static String getFileUrl(String var0, String var1) {
        return String.format(OSS_FILE_URL, var0) + var1;
    }

    public static String getGroupIconUrl(String var0) {
        return String.format(OSS_FILE_URL, BUCKET_GROUP_ICON) + var0;
    }

    public static String getPubAccountLogoUrl(String var0) {
        return String.format(OSS_FILE_URL, BUCKET_PUBACCOUNT_ICON) + var0;
    }

    public static String getUserIconUrl(String var0) {
        return String.format(OSS_FILE_URL, BUCKET_USER_ICON) + var0;
    }
}
