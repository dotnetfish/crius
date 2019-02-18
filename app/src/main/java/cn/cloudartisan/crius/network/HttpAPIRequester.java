package cn.cloudartisan.crius.network;

import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import cn.cloudartisan.crius.app.Global;

//import org.apache.http.HttpException;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class HttpAPIRequester {
    public final static String METHOD_POST="POST";
    public final static String METHOD_GET="GET";
    private static String accessToken = "";
    HttpAPIResponser responser;
    static final String TAG = HttpAPIRequester.class.getSimpleName();
    private static BlockingQueue<Runnable> queue = new LinkedBlockingQueue();
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(0x3, 0x5, 0x14, TimeUnit.SECONDS, queue);

    public HttpAPIRequester(HttpAPIResponser responser) {
        this.responser = responser;
    }

    public HttpAPIRequester() {
    }

    public static void execute(final HashMap<String, Object> params, final String url) {
        executor.execute(new Runnable() {


            public void run() {
                try {
                    httpPost(url, params);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void execute(final Type dataType, final Type dataListType, final String url) {
        execute(dataType, dataListType, url, null);
    }

    public void login(final Type dataType, final Type dataListType, final String strUrl) {
        executor.execute(new Runnable() {
            public void run() {
                Message message = handler.obtainMessage();
                HashMap<String, Object> data = new HashMap<String, Object>();
                try {
                    String dataString = httpPost(strUrl, responser.getRequestParams("Login"));

                 /*   URL url = new URL(strUrl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");// 提交模式
                    // conn.set
                    conn.setConnectTimeout(10000);//连接超时 单位毫秒
                    conn.setReadTimeout(5000);//读取超时 单位毫秒
                    conn.setDoOutput(true);// 是否输入参数

                    StringBuffer params = new StringBuffer();
                    // 表单参数与get形式一样
                    Map<String,String> map=responser.getRequestParams("Login");
                    for (Map.Entry obj :map.entrySet()
                            ) {
                        params.append("&"+obj.getKey()+"="+obj.getValue());
                    }

                    byte[] bypes = params.toString().getBytes();
                    conn.getOutputStream().write(bypes);// 输入参数
                    InputStream inStream=conn.getInputStream();
                  String query=conn.getURL().getQuery();
                    if(StringUtils.isNotEmpty(query)){

                    }*/
                    // String responseUrl=conn.getURL().getQuery().split("=")[1];
                    // accessToken=responseUrl;
                    // String dataString="{success:true,code:\"Login\",data:{ticket:\""+accessToken+"\",userId:\""+map.get("userUid")+"\"}}";
                    JSONObject json = JSON.parseObject(dataString);
                    message.getData().putSerializable("code", "Login");
                    message.getData().putSerializable("data", json);
                    message.what = 0;
                } catch (Exception e) {

                    e.printStackTrace();
                    message.getData().putSerializable("exception", e);
                    message.what = 0x1;

                }
                handler.sendMessage(message);

            }
        });
    }

    public  void execute(final Type dataType, final Type dataListType, final String url, final String code)
    {
        execute(dataType,  dataListType,url,code,"POST");
        }

    public void execute(final Type dataType, final Type dataListType, final String url, final String code, final String method) {
        responser.onRequest();
        executor.execute(new Runnable() {
            public void run() {
                Message message = handler.obtainMessage();
                HashMap<String, Object> data = new HashMap<String, Object>();
                try {
                    String dataString="";
                    if(method=="POST"){
                        dataString = httpPost(url, responser.getRequestParams(code));

                    }else{
                        dataString=httpGet(url,responser.getRequestParams(code));
                    }

                    JSONObject json = JSON.parseObject(dataString);
                    //JSONArray array=JSON.parseArray(dataString);
                    message.getData().putSerializable("code", code);
                    message.getData().putSerializable("data", json);
                    message.what = 0;
                } catch (Exception e) {

                    e.printStackTrace();
                    message.getData().putSerializable("exception", e);
                    message.what = 0x1;

                }
                handler.sendMessage(message);

            }
        });
    }

    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();//网页的二进制数据
        System.out.println(new String(data));
        outStream.close();
        inStream.close();
        return data;
    }

    public static String httpGet(String strUrl, Map<String, String> map) throws Exception {
        StringBuffer params = new StringBuffer();
        for (Map.Entry obj : map.entrySet()
                ) {
            params.append("&" + obj.getKey() + "=" + obj.getValue());
        }
        if (strUrl.indexOf('?') > -1) {
            strUrl = strUrl + params.toString();
        } else {
            strUrl = strUrl + "?1=1" + params;
        }
        URL url = new URL(strUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");// 提交模式
        // conn.set
        conn.setConnectTimeout(10000);//连接超时 单位毫秒
        conn.setReadTimeout(5000);//读取超时 单位毫秒
        conn.setDoOutput(true);// 是否输入参数
        if (Global.getCurrentUser() != null) {
            conn.setRequestProperty("usertoken", Global.getCurrentUser().getUserToken());
        }


        // 表单参数与get形式一样
        InputStream inStream = conn.getInputStream();

//conn.getRequestProperties("set-cookies");
        String json = new String(readInputStream(inStream), "UTF-8");
        return json;
    }

    public static String httpPost(String strUrl, Map<String, ?> map) throws Exception {
        //HttpPost httpPost = new HttpPost(url);//httpPost 已经被弃用，改用httpUrlconnection
        //TODO:change httppost to httpurlconnection or HttpConnection
        URL url = new URL(strUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");// 提交模式
        // conn.set
        conn.setConnectTimeout(10000);//连接超时 单位毫秒
        conn.setReadTimeout(5000);//读取超时 单位毫秒
        conn.setDoOutput(true);// 是否输入参数
        if (Global.getCurrentUser() != null) {
            conn.setRequestProperty("usertoken", Global.getCurrentUser().getUserToken());
        }

        StringBuffer params = new StringBuffer();
        // 表单参数与get形式一样
        for (Map.Entry obj : map.entrySet()
                ) {
            if(params.length()<=0){
                params.append(  obj.getKey() + "=" + obj.getValue());
            }else{
                params.append( "&"+ obj.getKey() + "=" + obj.getValue());
            }

        }

        byte[] bypes = params.toString().getBytes();
        conn.getOutputStream().write(bypes);// 输入参数
        InputStream inStream = conn.getInputStream();

//conn.getRequestProperties("set-cookies");
        String json = new String(readInputStream(inStream), "UTF-8");
        return json;
        // Parsing error may occure here :(
    }

    Handler handler = new Handler() {

        public void handleMessage(Message message) {
            switch (message.what) {
                case 0: {
                    responser.onSuccess(message.getData().get("data"), null, null, (String) message.getData().get("code"), "");
                    return;
                }
                case 1: {
                    responser.onFailed((Exception) message.getData().get("exception"));
                    break;
                }
            }
        }
    };
}
