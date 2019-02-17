
package cn.cloudartisan.crius.app;


public class URLConstant {

    //public  static  final  String DOMAIN="http://www.cloudartisan.cn";
   private static  final String URL_TEMPLATE="%s/mvc/generalhandler.ashx?controller=%sController&action=%s";
   // private static  final String URL_TEMPLATE="http://www.cloudartisan.cn/mock/11/v1/%s/%s";

    public  static  final  String DOMAIN="http://www.cloudartisan.cn";
    public  static  final  String ADS_GET=String.format(URL_TEMPLATE,DOMAIN,"Live","hots");
    public  static  final  String NEWS_INDEX=String.format(URL_TEMPLATE,DOMAIN,"Live","Lives");;

    public static final String SHAKE_GET_URL = DOMAIN+"store/shakeforpoint";

    public  static  final  String APP_GETMODULES=String.format(URL_TEMPLATE,DOMAIN,"module","getmodules");

    public static final String getUrl(String controller,String action){
        return  String.format(URL_TEMPLATE,DOMAIN,controller,action);
    }






    public static final String API_URL = "http://developer.yljr888.com/";
    public static final String ARTICLE_DELETE_URL = "http://developer.yljr888.com/cgi/article_delete.api";
    public static final String ARTICLE_DETAILED_URL = "http://developer.yljr888.com/cgi/article_detailed.api";
    public static final String ARTICLE_MYLIST_URL = "http://developer.yljr888.com/cgi/article_myList.api";
    public static final String ARTICLE_PUBLISH_URL = "http://developer.yljr888.com/cgi/article_publish.api";
    public static final String ARTICLE_RELEVANTLIST_URL = "http://developer.yljr888.com/cgi/article_relevantList.api";
    public static final String BOTTLE_DELETE_URL = "http://developer.yljr888.com/cgi/bottle_delete.api";
    public static final String BOTTLE_DETAILED_URL = "http://developer.yljr888.com/cgi/bottle_detailed.api";
    public static final String BOTTLE_DISCARD_URL = "http://developer.yljr888.com/cgi/bottle_discard.api";
    public static final String BOTTLE_GET_URL = "http://developer.yljr888.com/cgi/bottle_get.api";
    public static final String BOTTLE_THROW_URL = "http://developer.yljr888.com/cgi/bottle_release.api";
    public static final String COMMENT_DELETE_URL = "http://developer.yljr888.com/cgi/comment_delete.api";
    public static final String COMMENT_PUBLISH_URL = "http://developer.yljr888.com/cgi/comment_publish.api";
    public static final String CONFIG_LIST_URL = "http://developer.yljr888.com/cgi/config_list.api";
    public static final String FEEDBACK_PUBLISH_URL = "http://developer.yljr888.com/cgi/feedback_publish.api";
    public static final String FRIEND_ADD_URL = "http://developer.yljr888.com/cgi/friend_add.api";
    public static final String FRIEND_DELETE_URL = "http://developer.yljr888.com/cgi/friend_delete.api";
    public static final String FRIEND_LIST_URL = "http://developer.yljr888.com/cgi/friend_list.api";
    public static final String GROUPMEMBER_ADD_URL = "http://developer.yljr888.com/cgi/groupMember_add.api";
    public static final String GROUPMEMBER_GETOUT_URL = "http://developer.yljr888.com/cgi/groupMember_getout.api";
    public static final String GROUPMEMBER_INVITE_URL = "http://developer.yljr888.com/cgi/groupMember_invite.api";
    public static final String GROUPMEMBER_LIST_URL = "http://developer.yljr888.com/cgi/groupMember_list.api";
    public static final String GROUPMEMBER_REMOVE_URL = "http://developer.yljr888.com/cgi/groupMember_remove.api";
    public static final String GROUP_CREATE_URL = "http://developer.yljr888.com/cgi/group_create.api";
    public static final String GROUP_DETAILED_URL = "http://developer.yljr888.com/cgi/group_detailed.api";
    public static final String GROUP_DISBAND_URL = "http://developer.yljr888.com/cgi/group_disband.api";
    public static final String GROUP_LIST_URL = "http://developer.yljr888.com/cgi/group_list.api";
    public static final String HOST_DISPENSE_URL = "http://developer.yljr888.com/cgi/host_dispense.api";
    public static final String MESSAGE_RECEIVED_URL = "http://developer.yljr888.com/cgi/message_received.api";
    public static final String MESSAGE_SEND_URL = "http://developer.yljr888.com/cgi/message_send.api";
    public static final String PUBACCOUNT_DETAILED_URL = "http://developer.yljr888.com/cgi/publicAccount_detailed.api";
    public static final String PUBACCOUNT_LIST_URL = "http://developer.yljr888.com/cgi/publicAccount_list.api";
    public static final String PUBACCOUNT_MENU_URL = "http://developer.yljr888.com/cgi/publicMenu_list.api";
    public static final String PUBACCOUNT_SUBSCRIBE_URL = "http://developer.yljr888.com/cgi/subscriber_subscribe.api";
    public static final String PUBACCOUNT_UNSUBSCRIBE_URL = "http://developer.yljr888.com/cgi/subscriber_unsubscribe.api";
    //public static final String SHAKE_GET_URL = "http://developer.yljr888.com/cgi/snsshake_get.api";
    public static final String USER_DETAILED_URL = "http://developer.yljr888.com/cgi/user_detailed.api";
    public static final String USER_DETECTONLINE_URL = "http://developer.yljr888.com/cgi/user_detectOnline.api";
    public static final String USER_LOGIN_URL = "http://developer.yljr888.com/cgi/user_login.api";
    public static final String USER_MODIFYPASSWORD_URL = "http://developer.yljr888.com/cgi/user_modifyPassword.api";
    public static final String USER_MODIFY_GENDER_URL = "http://developer.yljr888.com/cgi/user_modifyGender.api";
    public static final String USER_MODIFY_URL = "http://developer.yljr888.com/cgi/user_modify.api";
    public static final String USER_NEARBY_URL = "http://developer.yljr888.com/cgi/user_nearby.api";
    public static final String USER_REGISTER_URL = "http://developer.yljr888.com/cgi/user_register.api";
}
