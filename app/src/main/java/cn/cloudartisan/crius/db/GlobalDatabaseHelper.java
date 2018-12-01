package cn.cloudartisan.crius.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import cn.cloudartisan.crius.bean.*;
import cn.cloudartisan.crius.util.AppTools;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class GlobalDatabaseHelper
        extends OrmLiteSqliteOpenHelper {
    private static GlobalDatabaseHelper instance;

    public GlobalDatabaseHelper(Context paramContext, String paramString) {
        super(paramContext, paramString, null, AppTools.getVersionCode(paramContext));
    }

    public static void destoryAll() {
        ConfigDBManager.destory();
        ArticleDBManager.destory();
        BottleDBManager.destory();
        FriendDBManager.destory();
        GroupDBManager.destory();
        GroupMemberDBManager.destory();
        MessageDBManager.destory();
        PublicAccountDBManager.destory();
        PublicMenuDBManager.destory();
        ShakeRecordDBManager.destory();
    }

    public static GlobalDatabaseHelper getHelper(Context paramContext, String paramString) {
        try {
            Context context = paramContext.getApplicationContext();
            if ((instance == null) || (!instance.isOpen())) {
                instance = new GlobalDatabaseHelper(paramContext, paramString);
            }
            return instance;
        } finally {
        }
    }

    public void onCreate(SQLiteDatabase paramSQLiteDatabase, ConnectionSource paramConnectionSource) {
        try {
            TableUtils.createTable(this.connectionSource, Message.class);
            TableUtils.createTable(this.connectionSource, Friend.class);
            TableUtils.createTable(this.connectionSource, Article.class);
            TableUtils.createTable(this.connectionSource, Group.class);
            TableUtils.createTable(this.connectionSource, GroupMember.class);
            TableUtils.createTable(this.connectionSource, PublicAccount.class);
            TableUtils.createTable(this.connectionSource, PublicMenu.class);
            TableUtils.createTable(this.connectionSource, Config.class);
            TableUtils.createTable(this.connectionSource, ShakeRecord.class);
            TableUtils.createTable(this.connectionSource, Bottle.class);
            return;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, ConnectionSource paramConnectionSource, int paramInt1, int paramInt2) {
        onCreate(paramSQLiteDatabase, paramConnectionSource);
    }
}
