package org.sharpx.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.IterableMap;
import org.apache.commons.collections.MapIterator;
import org.apache.commons.collections.map.HashedMap;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.sharpx.utils.jdkex.Utils;

public class HttpCli {

	public static Reader reader(String uri) throws ClientProtocolException,
			IOException {

		HttpClient httpclient = new DefaultHttpClient();

		HttpGet httpget = new HttpGet(uri);
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			InputStream instream = entity.getContent();
			return new InputStreamReader(instream,
					EntityUtils.getContentCharSet(entity));
		}
		return null;

	}

	public static Reader post(String uri, Map<String, String> para) {
		HttpClient httpclient = new DefaultHttpClient();
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		IterableMap map = new HashedMap(para);
		MapIterator it = map.mapIterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			String value = (String) it.getValue();
			formparams.add(new BasicNameValuePair(key, value));
		}
		UrlEncodedFormEntity entity;
		try {
			entity = new UrlEncodedFormEntity(formparams, "UTF-8");
			HttpPost httppost = new HttpPost(uri);
			httppost.addHeader(CoreProtocolPNames.USER_AGENT, "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.8) Gecko/20100722 Firefox/3.6.8 GTB7.1 QQDownload/1.7");
			httppost.setEntity(entity);

			HttpResponse response = httpclient.execute(httppost);
			int statuscode = response.getStatusLine().getStatusCode();
			if(statuscode == 302){
				String location = response.getFirstHeader("Location").getValue();
				Utils.log.info("HTTP/1.1 302 Moved Temporarily,"+location);
				httppost.abort();
				HttpGet httpget = new HttpGet(location);
				response = httpclient.execute(httpget);
			}
			
			HttpEntity entity2 = response.getEntity();
			if (entity2 != null) {
				InputStream instream = entity2.getContent();
				return new InputStreamReader(instream,
						EntityUtils.getContentCharSet(entity2));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
