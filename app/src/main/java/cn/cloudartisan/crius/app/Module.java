package cn.cloudartisan.crius.app;

/**
 * Created by kenqu on 2015/12/13.
 */
public class Module {
    private  String icon;

    private  String displayName;

    private  String code;

    private  String link;

    private  String sort;

    private  String showType;

    private  String vAlign;

    private int msgCount;


    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public String getvAlign() {
        return vAlign;
    }

    public void setvAlign(String vAlign) {
        this.vAlign = vAlign;
    }

    public  static Module fromJson(com.alibaba.fastjson.JSONObject json) {
        Module module=new Module();
        module.setCode((String) json.get("code"));
        module.setLink((String) json.get("link"));
        module.setIcon((String) json.get("icon"));
        module.setDisplayName((String) json.get("displayName"));
        module.setShowType((String) json.get("showType"));
        module.setSort((String) json.get("sort"));
        return module;
    }

    public int getMsgCount() {
        return msgCount;
    }

    public void setMsgCount(int msgCounte) {
        this.msgCount = msgCounte;
    }
}
