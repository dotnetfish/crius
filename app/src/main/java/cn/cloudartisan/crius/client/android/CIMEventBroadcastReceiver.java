
package cn.cloudartisan.crius.client.android;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;
import cn.cloudartisan.crius.client.constant.CIMConstant;
import cn.cloudartisan.crius.client.exception.CIMSessionDisableException;
import cn.cloudartisan.crius.client.model.Message;
import cn.cloudartisan.crius.client.model.ReplyBody;
import cn.cloudartisan.crius.client.model.SentBody;

import java.util.List;
/**
 *  消息入口，所有消息都会经过这里
 */
public  abstract  class CIMEventBroadcastReceiver extends BroadcastReceiver implements CIMEventListener {
 

	public Context context;
	@Override
	public void onReceive(Context ctx, Intent it) {

		  context = ctx;

		  /*
		   * 操作事件广播，用于提高service存活率
		   */
		  if(it.getAction().equals(Intent.ACTION_USER_PRESENT)
			 ||it.getAction().equals(Intent.ACTION_POWER_CONNECTED)
			 ||it.getAction().equals(Intent.ACTION_POWER_DISCONNECTED)
			) 	  
          {
			  startPushService();
          }
		  

		  /*
           * 设备网络状态变化事件
           */
		  if(it.getAction().equals(CIMConnectorManager.ACTION_NETWORK_CHANGED))
          {
			  ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
	          android.net.NetworkInfo info = connectivityManager.getActiveNetworkInfo();
	          onDevicesNetworkChanged(info);
          }
		  
		  /*
           * cim断开服务器事件
           */
          if(it.getAction().equals(CIMConnectorManager.ACTION_CONNECTION_CLOSED))
          {
	        	if(CIMConnectorManager.netWorkAvailable(context))
	      		{
	      			CIMPushManager.init(context);
	      		}
	        	//onCIMConnectionClosed();
          }
          
          /*
           * cim连接服务器失败事件
           */
          if(it.getAction().equals(CIMConnectorManager.ACTION_CONNECTION_FAILED))
          {
        	  onConnectionFailed((Exception) it.getSerializableExtra("exception"));
          }
          
          /*
           * cim连接服务器成功事件
           */
          if(it.getAction().equals(CIMConnectorManager.ACTION_CONNECTION_SUCCESS))
          {
        	  
        	  
        	 CIMPushManager.bindAccount(context);
        	  
        	 // onCIMConnectionSucceed();
          }
          
          /*
           * 收到推送消息事件
           */
          if(it.getAction().equals(CIMConnectorManager.ACTION_MESSAGE_RECEIVED))
          {
			Message message=  (Message)it.getSerializableExtra("message");
        	  filterType999Message(message);
          }
          
          
          /*
           * 获取收到replybody成功事件
           */
          if(it.getAction().equals(CIMConnectorManager.ACTION_REPLY_RECEIVED))
          {
        	  onReplyReceived((ReplyBody)it.getSerializableExtra("replyBody"));
          }
          
          
          /*
           * 获取sendbody发送失败事件
           */
          if(it.getAction().equals(CIMConnectorManager.ACTION_SENT_FAILED))
          {
        	  onSentFailed((Exception) it.getSerializableExtra("exception"),(SentBody)it.getSerializableExtra("sentBody"));
          }
          
          /*
           * 获取sendbody发送成功事件
           */
          if(it.getAction().equals(CIMConnectorManager.ACTION_SENT_SUCCESS))
          {
        	  onSentSucceed((SentBody)it.getSerializableExtra("sentBody"));
          }
          
          
          /*
           * 获取cim数据传输异常事件
           */
          if(it.getAction().equals(CIMConnectorManager.ACTION_UNCAUGHT_EXCEPTION))
          {
        	  onUncaughtException((Exception)it.getSerializableExtra("exception"));
          }
          
          /*
           * 获取cim连接状态事件
           */
          if(it.getAction().equals(CIMConnectorManager.ACTION_CONNECTION_STATUS))
          {
        	  onConnectionStatus(it.getBooleanExtra(cn.cloudartisan.crius.client.android.CIMPushManager.KEY_CIM_CONNECTION_STATUS, false));
          }
	}

	protected boolean isInBackground(Context context) {
		List<RunningTaskInfo> tasksInfo = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getRunningTasks(1);
		if (tasksInfo.size() > 0) {

			if (context.getPackageName().equals(
					tasksInfo.get(0).topActivity.getPackageName())) {

				return false;
			}
		}
		return true;
	}

	
	private void startPushService()
	{
		Intent intent  = new Intent(context, cn.cloudartisan.crius.client.android.CIMPushService.class);
		intent.putExtra(cn.cloudartisan.crius.client.android.CIMPushManager.SERVICE_ACTION,
				cn.cloudartisan.crius.client.android.CIMPushManager.ACTION_ACTIVATE_PUSH_SERVICE);
		context.startService(intent);
	}
	
	
	 

	private   void onConnectionFailed(Exception e){
		
		if(CIMConnectorManager.netWorkAvailable(context))
		{
			//间隔30秒后重连
			connectionHandler.sendMessageDelayed(connectionHandler.obtainMessage(), 30*1000);
		}
	}

	 
	Handler connectionHandler = new Handler()
	{
		@Override
		public void handleMessage(android.os.Message message){
			
			cn.cloudartisan.crius.client.android.CIMPushManager.init(context);
		}
		
	};
	
	

	private void onUncaughtException(Throwable arg0) {}

	

	private  void onDevicesNetworkChanged(NetworkInfo info) {
		
		if(info !=null)
		{
			cn.cloudartisan.crius.client.android.CIMPushManager.init(context);
		} 
		
		onNetworkChanged(info);
	}

	private void filterType999Message(cn.cloudartisan.crius.client.model.Message message)
	{
		Log.e("mrdddgjdkf","cimeventbroadcastreceiver");
		if(CIMConstant.MessageType.TYPE_999.equals(message.getType()))
		{
			CIMCacheTools.putBoolean(context,CIMCacheTools.KEY_MANUAL_STOP,true);
		}
Log.e("mrdddgjdkf","cimeventbroadcastreceiver");
		onMessageReceived(message);
	}
	
	private   void onSentFailed(Exception e, SentBody body){
		
		//与服务端端开链接，重新连接
		if(e instanceof CIMSessionDisableException)
		{
			cn.cloudartisan.crius.client.android.CIMPushManager.init(context);
		}else
		{
			//发送失败 重新发送
			cn.cloudartisan.crius.client.android.CIMPushManager.sendRequest(context, body);
		}
		
	}

	private  void onSentSucceed(SentBody body){}
	
	@Override
	public abstract void onMessageReceived(cn.cloudartisan.crius.client.model.Message message);
	@Override
	public abstract void onReplyReceived(ReplyBody body);
	
	public abstract void onNetworkChanged(NetworkInfo info);
	
	//public abstract void onCIMConnectionSucceed();
	 
	//public abstract void onCIMConnectionClosed();
}
