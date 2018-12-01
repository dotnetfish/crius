package cn.cloudartisan.crius.db;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import cn.cloudartisan.crius.bean.ChatItem;
import cn.cloudartisan.crius.bean.Message;
import cn.cloudartisan.crius.bean.SystemMsg;
import cn.cloudartisan.crius.message.parser.MessageParser;
import cn.cloudartisan.crius.message.parser.MessageParserFactory;
import com.j256.ormlite.stmt.ArgumentHolder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MessageDBManager
        extends BaseDBManager<Message, String> {
    static MessageDBManager manager;

    private MessageDBManager() {
        super(Message.class);
        super.executeSQL("CREATE INDEX if not exists IDX_MESSAGE ON t_ichat_message(receiver,sender)");
    }

    public static void destory() {
        if (manager == null) {
            return;
        }
        manager.close();
        manager = null;
    }

    public static MessageDBManager getManager() {
        if (manager == null) {
            manager = new MessageDBManager();
        }
        return manager;
    }

    public void batchModifyAgree(String paramString1, String paramString2) {
        try {
            List<Message> list = (List<Message>) this.databaseDao.queryForEq("type", paramString2).iterator();
            for (Message localMessage : list) {

                JSONObject localJSONObject = JSON.parseObject(localMessage.content);
                if ((paramString1.equals(localJSONObject.get("sourceAccount"))) && (localJSONObject.get(SystemMsg.HANDLE_RESULT) == null)) {
                    localJSONObject.put(SystemMsg.HANDLE_RESULT, SystemMsg.RESULT_AGREE);
                    localMessage.content = JSON.toJSONString(localJSONObject);
                    modifyMsgContent(localMessage);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void batchReadMessage(String[] paramArrayOfString) {
        executeSQL("update t_ichat_message set status = ? where type in(" + TextUtils.join(",", paramArrayOfString) + ") ", new String[]{"1"});
    }

    public long countMessage(String paramString) {
        try {
            long l = this.databaseDao.queryBuilder().where().eq("type", paramString).and().eq("status", "0").countOf();
            return l;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0L;
    }

    public long countNewBySender(String paramString) {
        if (paramString == null) {
            return 0L;
        }
        try {
            long l = this.databaseDao.queryBuilder().where().eq("sender", paramString).and().eq("status", "0").countOf();
            return l;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0L;
    }

    public long countNewByType(String paramString) {
        try {
            long l = this.databaseDao.queryBuilder().where().eq("type", paramString).and().eq("type", "0").countOf();
            return l;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0L;
    }

    public long countNewExceptTypesBySender(String paramString, Object[] paramArrayOfObject) {
        try {
            long l = this.databaseDao.queryBuilder().where().eq("sender", paramString).and().eq("status", "0").and().notIn("type", paramArrayOfObject).countOf();
            return l;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0L;
    }

    public long countNewMessage(Object[] paramArrayOfObject) {
        try {
            long l = this.databaseDao.queryBuilder().where().eq("status", "0").and().in("type", paramArrayOfObject).countOf();
            return l;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0L;
    }

    public void deleteById(String paramString) {
        super.deleteById(paramString);
    }

    public void deleteBySender(String paramString) {
        executeSQL("delete from t_ichat_message where sender = ?  or receiver = ?", new String[]{paramString, paramString});
    }

    public void deleteBySenderAndType(String paramString1, String paramString2) {
        executeSQL("delete from t_ichat_message where (sender = ?  or receiver = ?) and type =?", new String[]{paramString1, paramString1, paramString2});
    }

    public void deleteBySenderAndType(String paramString, String[] paramArrayOfString) {
        String string = TextUtils.join(",", paramArrayOfString);
        executeSQL("delete from t_ichat_message where (sender = ?  or receiver = ?) and type in(" + string + ")", new String[]{paramString, paramString});
    }

    public void deleteByType(String paramString) {
        executeSQL("delete from t_ichat_message where   type = ?", new String[]{paramString});
    }

    public void deleteExceptSystemMessageList(Object[] paramArrayOfObject) {
        String string = TextUtils.join(",", paramArrayOfObject);
        executeSQL("delete from t_ichat_message   where sender = ? and  type not in(" + string + ") ", new String[]{"system"});
    }

    public String formatSQLString(String paramString, Object... paramVarArgs) {
        StringBuffer stringBuffer = new StringBuffer(paramString);

        for (int i = 0; i < paramVarArgs.length; i++) {
            int j = stringBuffer.indexOf("?");
            if (paramVarArgs[i].getClass() == String.class) {
                stringBuffer.replace(j, j + 1, "'" + paramVarArgs[i] + "'");
            } else {
                stringBuffer.replace(j, j + 1, paramVarArgs[i].toString());
            }
        }
        return stringBuffer.toString();

    }

    public List<ChatItem> getRecentMessage(String paramString, Object[] paramArrayOfObject) {
        ArrayList localArrayList = new ArrayList();
        HashSet localHashSet = new HashSet();
        List<Message> list = new ArrayList();
        try {
            list =  this.databaseDao.queryBuilder()
                    .groupByRaw("receiver,sender")
                    .orderBy("createTime", false)
                    .where()
                    .in("type", paramArrayOfObject).query();
            for (Message message : list) {
                if ((!localHashSet.contains(message.sender + message.receiver)) && (!localHashSet.contains(message.receiver + message.sender))) {
                    localHashSet.add(message.receiver + message.sender);
                  //  localHashSet.add(message.sender + message.receiver);
                    ChatItem localChatItem = new ChatItem();
                    MessageParser localMessageParser = MessageParserFactory.getFactory().getMessageParser(message.type);
                    if (localMessageParser != null) {
                        message.content = localMessageParser.getMessagePreview(message);
                        localChatItem.message = message;
                        localChatItem.source = MessageParserFactory.getFactory().getMessageParser(message.type).getMessageItemSource(message);
                        localArrayList.add(localChatItem);
                    }
                }
            }
        } catch (SQLException ex) {

            ex.printStackTrace();

        }
        return localArrayList;


    }

    public void modifyMsgContent(Message paramMessage) {
        executeSQL("update t_ichat_message set content = ? where   gid = ?", new String[]{paramMessage.content, paramMessage.gid});
    }

    public List<Message> queryExceptSystemMessageList(Object[] paramArrayOfObject) {
        try {
            return this.databaseDao.queryBuilder().orderBy("createTime", false).where().eq("sender", "system").and().notIn("type", paramArrayOfObject).query();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return new ArrayList();
    }

    public List<Message> queryExceptSystemMessageList(Object[] paramArrayOfObject, int paramInt) {
        try {
            return this.databaseDao.queryBuilder().orderBy("createTime", false).offset(Long.valueOf((paramInt - 1) * 20L)).limit(Long.valueOf(20L)).where().eq("sender", "system").and().notIn("type", paramArrayOfObject).query();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return new ArrayList();
    }

    public List<Message> queryExpectSystemMessageList(Object[] paramArrayOfObject) {
        try {
            return  this.databaseDao.queryBuilder().orderBy("createTime", false).where().eq("sender", "system").and().in("type", paramArrayOfObject).query();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return new ArrayList();
    }

    public List<Message> queryExpectSystemMessageList(Object[] paramArrayOfObject, int paramInt) {
        try {
            return this.databaseDao.queryBuilder().orderBy("createTime", false).offset(Long.valueOf((paramInt - 1) * 20L)).limit(Long.valueOf(20L)).where().eq("sender", "system").and().in("type", paramArrayOfObject).query();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return new ArrayList();
    }

    public int queryIncludedNewCount(Object[] paramArrayOfObject) {
        try {
            long l = this.databaseDao.queryBuilder().where().eq("status", "0").and().in("type", paramArrayOfObject).countOf();
            return (int) l;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public Message queryLastMessage(String paramString, Object[] paramArrayOfObject) {
        try {
            return  (Message) this.databaseDao.queryBuilder().offset(Long.valueOf(0L)).limit(Long.valueOf(1L)).orderBy("createTime", false).where().raw(formatSQLString("(sender=?   or  receiver=?)", new Object[]{paramString, paramString}), new ArgumentHolder[0]).and().in("type", paramArrayOfObject).queryForFirst();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<Message> queryMessage(String paramString) {
        try {
            return this.databaseDao.queryBuilder().orderBy("createTime", false).where().eq("sender", paramString).query();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return new ArrayList();
    }

    public List<Message> queryMessage(String paramString, Object[] paramArrayOfObject, int paramInt) {
        try {
           return this.databaseDao.queryBuilder().offset(Long.valueOf((paramInt - 1) * 20L)).limit(Long.valueOf(20L)).orderBy("createTime", false).where().raw(formatSQLString("(sender=?   or  receiver=?)", new Object[]{paramString, paramString}), new ArgumentHolder[0]).and().in("type", paramArrayOfObject).query();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return new ArrayList();
    }

    public Message queryMessageById(String paramString) {
        return (Message) queryById(paramString);
    }

    public List<Message> queryMessageByType(String paramString) {
        try {
           return this.databaseDao.queryBuilder().orderBy("createTime", false).where().eq("type", paramString).query();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return new ArrayList();
    }

    public List<Message> queryMessageByTypes(Object[] paramArrayOfObject) {
        try {
            return  this.databaseDao.queryBuilder().orderBy("createTime", false).where().in("type", paramArrayOfObject).query();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return new ArrayList();
    }

    public List<Message> queryMoments() {
        try {
            List localList = this.databaseDao.queryBuilder().orderBy("createTime", false).where().raw(formatSQLString("(type=?   or  type=?)", new Object[]{"801", "802"}), new ArgumentHolder[0]).query();
            return localList;
        } catch (SQLException localSQLException) {
            localSQLException.printStackTrace();
        }
        return new ArrayList();
    }

    public List<Message> queryNewMessage(String paramString) {
        try {
            return  this.databaseDao.queryBuilder().where().eq("receiver", paramString).and().eq("status", "0").query();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return new ArrayList();
    }

    public List<Message> queryNewMoments(long paramLong) {
        ArrayList localArrayList = new ArrayList();
        try {
            List localList = this.databaseDao.queryBuilder().offset(Long.valueOf(0L))
                    .limit(Long.valueOf(paramLong))
                    .orderBy("createTime", false)
                    .where().raw(formatSQLString("(type=?   or  type=?)", "801", "802"),
                            new ArgumentHolder[0]).and().eq("status", "0").query();
            return localList;
        } catch (SQLException localSQLException) {
            localSQLException.printStackTrace();
        }
        return localArrayList;
    }

    public List<Message> queryNewMoments(String paramString) {
        try {
            return this.databaseDao.queryBuilder().orderBy("createTime", false).where().eq("type", paramString).and().eq("status", "0").query();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return new ArrayList();
    }

    public void readBySender(String paramString) {
        executeSQL("update t_ichat_message set status = ? where sender = ? and status = ?", new String[]{"1", paramString, "0"});
    }

    public void readByType(String paramString) {
        executeSQL("update t_ichat_message set status = ? where type = ?  ", new String[]{"1", paramString});
    }

    public void readExceptSystemMessage(String[] paramArrayOfString) {
        String string = TextUtils.join(",", paramArrayOfString);
        executeSQL("update t_ichat_message set status = ? where sender = ? and type not in(" + string + ")", new String[]{"1", "system"});
    }

    public void readExpectSystemMessage(String[] paramArrayOfString) {
        String string = TextUtils.join(",", paramArrayOfString);
        executeSQL("update t_ichat_message set status = ? where sender = ? and  type in(" + string + ")", new String[]{"1", "system"});
    }

    public void resetSendingStatus() {
        executeSQL("update t_ichat_message set status = ? where status = ? ", new String[]{"-3", "-2"});
    }

    public void saveMessage(Message paramMessage) {
        if (paramMessage.isActionMessage()) {
            return;
        }
        save(paramMessage);
    }

    public void updateStatus(String paramString1, String paramString2) {
        executeSQL("update t_ichat_message set status = ? where gid = ? ", new String[]{paramString2, paramString1});
    }
}

