package cn.cloudartisan.crius.service.adapter;

public class ServiceAdapterFactory {
    public  static Adapter  getUserAdapter(){

        return new CMSUserAdapter();
    }

    public static   Adapter getModuleAdapter(){
        return  new CMSModuleAdapter();
    }

    public static  Adapter getProductAdapter(){
        return  new ProductAdapter();
    }
}
