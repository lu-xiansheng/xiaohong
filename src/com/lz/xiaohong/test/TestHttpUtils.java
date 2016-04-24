/**
 * 
 */
package com.lz.xiaohong.test;



import com.lz.xiaohong.Utils.HttpUtils;

import android.test.AndroidTestCase;
import android.util.Log;

/**
 * @author L.Z.
 *
 */
public class TestHttpUtils extends AndroidTestCase{
	public void testSendInfo() {
		String res = HttpUtils.doGet("给我讲个笑话");
		Log.e("TAG", res);
		res = HttpUtils.doGet("给我讲个鬼故事");
		Log.e("TAG", res);
		res = HttpUtils.doGet("小红，好漂亮");
		Log.e("TAG", res);  
	}
}
