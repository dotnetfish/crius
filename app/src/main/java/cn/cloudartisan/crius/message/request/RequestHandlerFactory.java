/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package cn.cloudartisan.crius.message.request;

import java.util.HashMap;
import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;

public class RequestHandlerFactory {
    static RequestHandlerFactory factory;
    HashMap<String, RequestHandler> parsers = new HashMap();
    Properties properties = new Properties();
    
    private RequestHandlerFactory() {
        InputStream in = RequestHandlerFactory.class.getResourceAsStream("request.properties");
        try {
            properties.load(in);
            return;
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    public static RequestHandlerFactory getFactory() {
        if(factory == null) {
            factory = new RequestHandlerFactory();
        }
        return factory;
    }
    
    public RequestHandler getMessageHandler(String msgType) {
        if(parsers.get(msgType) == null) {
            try {
                parsers.put(msgType, (RequestHandler)Class.forName(properties.getProperty(msgType)).newInstance());
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        return (RequestHandler)parsers.get(msgType);
    }
}