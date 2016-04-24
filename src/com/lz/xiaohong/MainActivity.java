package com.lz.xiaohong;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lz.xiaohong.Utils.HttpUtils;
import com.lz.xiaohong.adapter.ChatMessageAdapter;
import com.lz.xiaohong.bean.ChatMessage;
import com.lz.xiaohong.bean.ChatMessage.Type;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	/**一般ListView会绑定适配器，数据元**/
	private ListView mMsgs;
	private ChatMessageAdapter mAdapter;
	private List<ChatMessage> mDatas;
	
	private EditText mInputMsg;
	private Button mSendMsg;
	
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			//等待接收，子线程完成数据的返回
			ChatMessage froMessage = (ChatMessage) msg.obj;
			mDatas.add(froMessage);
			mAdapter.notifyDataSetChanged();
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		/****变量的初始化****/
		initView();
		/****初始化数据****/
		initDatas();
		/****初始化监听事件****/
		initListener(); 
	}

	/**
	 * 
	 */
	private void initListener() {
		mSendMsg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final String toMsg = mInputMsg.getText().toString();
				
				if(TextUtils.isEmpty(toMsg)) {
					Toast.makeText(MainActivity.this, "发送消息不能为空", Toast.LENGTH_SHORT);
					
					return ;
				}
				ChatMessage toMessage = new ChatMessage();
				toMessage.setDate(new Date());
				toMessage.setMsg(toMsg);
				toMessage.setType(Type.OUTCOMING);
				mDatas.add(toMessage);
				mAdapter.notifyDataSetChanged();
				
				mInputMsg.setText(""); 
				
				new Thread() {
					public void run() {
						ChatMessage fromMessage = HttpUtils.sendMessage(toMsg);
						Message m = Message.obtain();
						m.obj = fromMessage;
						mHandler.sendMessage(m);
					};
				}.start();
			}
		});
		
	}

	/**
	 * 初始化数据
	 */
	private void initDatas() {
		mDatas = new ArrayList<ChatMessage>();
		mDatas.add(new ChatMessage("你好，小红为您服务", Type.INCOMING, new Date()));
		//mDatas.add(new ChatMessage("你好", Type.OUTCOMING, new Date()));
		mAdapter = new ChatMessageAdapter(this, mDatas);
		
		mMsgs.setAdapter(mAdapter);
	}

	/**
	 * 变量的初始化
	 */
	private void initView() {
		mMsgs = (ListView) findViewById(R.id.id_listview_msgs);
		mInputMsg = (EditText) findViewById(R.id.id_input_msg);
		mSendMsg = (Button) findViewById(R.id.id_send_msg);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
