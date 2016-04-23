/**
 * 
 */
package com.lz.xiaohong.Utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import javax.net.ssl.HttpsURLConnection;

/**
 * @author L.Z.
 *
 */
public class HttpUtils {
	/*******API接入地址***********/
	private static final String URL = "http://www.tuling123.com/openapi/api";
	/*******API接入密钥***********/
	private static final String API_KEY = "20c920ef739be07adaf63269182dd35c";
	
	private static String doGet(String msg) {
		String result = "";
		InputStream is = null;
		ByteArrayOutputStream baos = null;
		/*******获取完整的url*************/
		String url = setParams(msg);
		try {
			java.net.URL urlNet = new java.net.URL(url);
			/*******建立url连接*************/
			HttpsURLConnection conn = (HttpsURLConnection) urlNet.openConnection();
			/*******设置conn参数*************/
			conn.setReadTimeout(5*1000);
			conn.setConnectTimeout(5*1000);
			conn.setRequestMethod("GET");
			
			/*******拿到返回的数据*************/
			is = conn.getInputStream();
			int len = -1;
			/******声明缓冲区*************/
			byte[] buf = new byte[128];
			baos = new ByteArrayOutputStream();
			
			while((len = is.read(buf)) != -1) {
				baos.write(buf, 0, len);
			}
/*****清除缓冲区*****有点不明白需要在深入一点********/
			baos.flush();
			result = new String(baos.toByteArray());
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if(baos != null)
					baos.close();
				if(is != null)
					is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
		
	}

	/**设置并返回完整的 url
	 * @param msg 
	 * @return
	 */
	private static String setParams(String msg) {
		String url = URL+"?key="+ API_KEY +"&info="+ msg;
		return url;
	}
}


