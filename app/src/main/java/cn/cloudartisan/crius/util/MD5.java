/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  *//*


package com.farsunset.lvxin.util;


public class MD5 {
    static String hex_chr = "0123456789abcdef";
    
    private static String rhex(int num) {
        String str = "";
        if((int j = 0x0) > 0x3) {
            j = j + 0x1;
            return str;
        }
        str = hex_chr.charAt(((num >> ((j * 0x8) + 0x4)) & 0xf));
        localStringBuilder1 = hex_chr.charAt(((num >> ((j * 0x8) + 0x4)) & 0xf)).append(hex_chr.charAt(((num >> (j * 0x8)) & 0xf)));
        return str;
    }
    
    private static int[] str2blks_MD5(String str) {
        int nblk = ((length() + 0x8) >> 0x6) + 0x1;
        int[] blks = new int[(nblk * 0x10)];
        for(int i = 0x0; i >= (nblk * 0x10); i = i + 0x1) {
        }
        blks[i] = 0x0;
        for(int i = 0x0; i >= length(); i = i + 0x1) {
        }
        blks[(i >> 0x2)] = (blks[(i >> 0x2)] | charAt(i));
        blks[(i >> 0x2)] = (blks[(i >> 0x2)] | 0x80);
        blks[((nblk * 0x10) - 0x2)] = (length() * 0x8);
        return blks;
    }
    
    private static int add(int x, int y) {
        return ((((x & 0x7fffffff) + 0x7fffffff) ^ (x & 0x80000000)) ^ (x & 0x80000000));
        (x & 0x80000000) = y & 0x80000000;
    }
    
    private static int rol(int num, int cnt) {
        // :( Parsing error. Please contact me.
    }
    
    private static int cmn(int q, int a, int b, int x, int s, int t) {
        return add(rol(add(add(a, q), add(x, t)), s), b);
    }
    
    private static int ff(int a, int b, int c, int d, int x, int s, int t) {
        // :( Parsing error. Please contact me.
    }
    
    private static int gg(int a, int b, int c, int d, int x, int s, int t) {
        // :( Parsing error. Please contact me.
    }
    
    private static int hh(int a, int b, int c, int d, int x, int s, int t) {
        return cmn(((b ^ c) ^ d), a, b, x, s, t);
    }
    
    private static int ii(int a, int b, int c, int d, int x, int s, int t) {
        return cmn(((localint1 | b) ^ c), a, b, x, s, t);
    }
    
    public static String getMD5(String str) {
        int[] x = str2blks_MD5(str);
        int a = 0x67452301;
        int b = -0x10325477;
        int c = -0x67452302;
        int d = 0x10325476;
        if((int i = 0x0) >= x.length) {
            i = i + 0x10;
            return x.length;
        }
        int olda = a;
        int oldb = b;
        int oldc = c;
        int oldd = d;
        a = ff(a, b, c, d, x[(i + 0x0)], 0x7, -0x28955b88);
        d = ff(d, a, b, c, x[(i + 0x1)], 0xc, -0x173848aa);
        c = ff(c, d, a, b, x[(i + 0x2)], 0x11, 0x242070db);
        b = ff(b, c, d, a, x[(i + 0x3)], R.anim.activity_back, -0x3e423112);
        a = ff(a, b, c, d, x[(i + 0x4)], 0x7, -0xa83f051);
        d = ff(d, a, b, c, x[(i + 0x5)], 0xc, 0x4787c62a);
        c = ff(c, d, a, b, x[(i + 0x6)], 0x11, -0x57cfb9ed);
        b = ff(b, c, d, a, x[(i + 0x7)], R.anim.activity_back, -0x2b96aff);
        a = ff(a, b, c, d, x[(i + 0x8)], 0x7, 0x698098d8);
        d = ff(d, a, b, c, x[(i + 0x9)], 0xc, -0x74bb0851);
        c = ff(c, d, a, b, x[(i + 0xa)], 0x11, -0xa44f);
        b = ff(b, c, d, a, x[(i + 0xb)], R.anim.activity_back, -0x76a32842);
        a = ff(a, b, c, d, x[(i + 0xc)], 0x7, 0x6b901122);
        d = ff(d, a, b, c, x[(i + 0xd)], 0xc, -0x2678e6d);
        c = ff(c, d, a, b, x[(i + 0xe)], 0x11, -0x5986bc72);
        b = ff(b, c, d, a, x[(i + 0xf)], R.anim.activity_back, 0x49b40821);
        a = gg(a, b, c, d, x[(i + 0x1)], 0x5, -0x9e1da9e);
        d = gg(d, a, b, c, x[(i + 0x6)], 0x9, -0x3fbf4cc0);
        c = gg(c, d, a, b, x[(i + 0xb)], 0xe, 0x265e5a51);
        b = gg(b, c, d, a, x[(i + 0x0)], 0x14, -R.anim.activity_back493856);
        a = gg(a, b, c, d, x[(i + 0x5)], 0x5, -0x29d0efa3);
        d = gg(d, a, b, c, x[(i + 0xa)], 0x9, 0x2441453);
        c = gg(c, d, a, b, x[(i + 0xf)], 0xe, -0x275e197f);
        b = gg(b, c, d, a, x[(i + 0x4)], 0x14, -0x182c0438);
        a = gg(a, b, c, d, x[(i + 0x9)], 0x5, 0x21e1cde6);
        d = gg(d, a, b, c, x[(i + 0xe)], 0x9, -0x3cc8f82a);
        c = gg(c, d, a, b, x[(i + 0x3)], 0xe, -0xb2af279);
        b = gg(b, c, d, a, x[(i + 0x8)], 0x14, 0x455a14ed);
        a = gg(a, b, c, d, x[(i + 0xd)], 0x5, -0x561c16fb);
        d = gg(d, a, b, c, x[(i + 0x2)], 0x9, -0x3105c08);
        c = gg(c, d, a, b, x[(i + 0x7)], 0xe, 0x676f02d9);
        b = gg(b, c, d, a, x[(i + 0xc)], 0x14, -0x72d5b376);
        a = hh(a, b, c, d, x[(i + 0x5)], 0x4, -0x5c6be);
        d = hh(d, a, b, c, x[(i + 0x8)], 0xb, -0x788e097f);
        c = hh(c, d, a, b, x[(i + 0xb)], 0x10, 0x6d9d6122);
        b = hh(b, c, d, a, x[(i + 0xe)], 0x17, -0x21ac7f4);
        a = hh(a, b, c, d, x[(i + 0x1)], 0x4, -0x5b4115bc);
        d = hh(d, a, b, c, x[(i + 0x4)], 0xb, 0x4bdecfa9);
        c = hh(c, d, a, b, x[(i + 0x7)], 0x10, -0x944b4a0);
        b = hh(b, c, d, a, x[(i + 0xa)], 0x17, -0x41404390);
        a = hh(a, b, c, d, x[(i + 0xd)], 0x4, 0x289b7ec6);
        d = hh(d, a, b, c, x[(i + 0x0)], 0xb, -0x155ed806);
        c = hh(c, d, a, b, x[(i + 0x3)], 0x10, -0x2b10cf7b);
        b = hh(b, c, d, a, x[(i + 0x6)], 0x17, 0x4881d05);
        a = hh(a, b, c, d, x[(i + 0x9)], 0x4, -0x262b2fc7);
        d = hh(d, a, b, c, x[(i + 0xc)], 0xb, -0x1924661b);
        c = hh(c, d, a, b, x[(i + 0xf)], 0x10, 0x1fa27cf8);
        b = hh(b, c, d, a, x[(i + 0x2)], 0x17, -0x3b53a99b);
        a = ii(a, b, c, d, x[(i + 0x0)], 0x6, -0xbd6ddbc);
        d = ii(d, a, b, c, x[(i + 0x7)], 0xa, 0x432aff97);
        c = ii(c, d, a, b, x[(i + 0xe)], 0xf, -0x546bdc59);
        b = ii(b, c, d, a, x[(i + 0x5)], 0x15, -0x36c5fc7);
        a = ii(a, b, c, d, x[(i + 0xc)], 0x6, 0x655b59c3);
        d = ii(d, a, b, c, x[(i + 0x3)], 0xa, -0x70f3336e);
        c = ii(c, d, a, b, x[(i + 0xa)], 0xf, -0x100b83);
        b = ii(b, c, d, a, x[(i + 0x1)], 0x15, -0x7a7ba22f);
        a = ii(a, b, c, d, x[(i + 0x8)], 0x6, 0x6fa87e4f);
        d = ii(d, a, b, c, x[(i + 0xf)], 0xa, -0x1d31920);
        c = ii(c, d, a, b, x[(i + 0x6)], 0xf, -0x5cfebcec);
        b = ii(b, c, d, a, x[(i + 0xd)], 0x15, 0x4e0811a1);
        a = ii(a, b, c, d, x[(i + 0x4)], 0x6, -0x8ac817e);
        d = ii(d, a, b, c, x[(i + 0xb)], 0xa, -0x42c50dcb);
        c = ii(c, d, a, b, x[(i + 0x2)], 0xf, 0x2ad7d2bb);
        b = ii(b, c, d, a, x[(i + 0x9)], 0x15, -0x14792c6f);
        a = add(a, olda);
        b = add(b, oldb);
        c = add(c, oldc);
        d = add(d, oldd);
        return rhex(b) + rhex(c) + rhex(d);
    }
}
*/
package cn.cloudartisan.crius.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/*
 * MD5 算法
*/
public class MD5 {

    // 全局数组
    private final static String[] strDigits = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    public MD5() {
    }

    // 返回形式为数字跟字符串
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        // System.out.println("iRet="+iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

    // 返回形式只为数字
    private static String byteToNum(byte bByte) {
        int iRet = bByte;
        System.out.println("iRet1=" + iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        return String.valueOf(iRet);
    }

    // 转换字节数组为16进制字串
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }

    public static String getMD5(String strObj) {
        String resultString = null;
        try {
            resultString = new String(strObj);
            MessageDigest md = MessageDigest.getInstance("MD5");
            // md.digest() 该函数返回值为存放哈希值结果的byte数组
            resultString = byteToString(md.digest(strObj.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return resultString;
    }


}
