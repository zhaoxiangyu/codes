package org.sharp.android.autils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.sharp.android.base.BaseGestureListener;
import org.sharp.android.intf.PackageChangeHandler;
import org.sharp.intf.TextConsumer;
import org.sharp.utils.IOUtils;
import org.sharp.utils.Utils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Parcelable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.GestureDetector.OnGestureListener;
import android.view.View;
import android.view.View.OnTouchListener;


public class AUtils {

	public static interface FieldValueFetcher {
		void fieldValue(String key,Object value);
	}

	public static interface FieldValueProvider {
		Object fieldValue(String key,Class<?> type);
	}

	public static String tag = Utils.class.getCanonicalName();
	
	public static void showInputMethod(Context ctx){
		
	}
	
	public static void attachGesturesSensor(Context ctx, View view, 
			float minDistanceRatio, float maxSeconds,
			Direction4Sensor ds){
		
		int minDistance = (int)(view.getWidth() < view.getHeight() ? 
				view.getWidth()*minDistanceRatio : view.getHeight()*minDistanceRatio);
		float minVelocity = minDistance/maxSeconds;
		OnGestureListener gl = newGestureListener(minDistance,minVelocity, ds);
		GestureDetector gd = new GestureDetector(ctx,gl);
		OnTouchListener tl = newTouchListener(gd);
		view.setOnTouchListener(tl);
		view.setLongClickable(true);
	}
	
	private static OnTouchListener newTouchListener(final GestureDetector gd){
		return new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return gd.onTouchEvent(event);
			}
		};
	}
	
	private static OnGestureListener newGestureListener(final int minDistance,
			final float minVelocity, final Direction4Sensor ds){
		return new BaseGestureListener(){
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float vx,
					float vy) {
				float xDistance = Math.abs(e1.getX() - e2.getX());
				float yDistance = Math.abs(e1.getY() - e2.getY());
				if(xDistance > yDistance){
					if (e1.getX() - e2.getX() > minDistance
							&& Math.abs(vx) > minVelocity) {
						ds.left();
					} else if (e2.getX() - e1.getX() > minDistance
							&& Math.abs(vx) > minVelocity) {
						ds.right();
					}
				}else{
					if (e1.getY() - e2.getY() > minDistance
							&& Math.abs(vy) > minVelocity) {
						ds.up();
					} else if (e2.getY() - e1.getY() > minDistance
							&& Math.abs(vy) > minVelocity) {
						ds.down();
					}
				}
				return false;
			}
		};
	}
	
	public static Notification newNotification(Context ctx, int iconResId, 
			String tickerText, String contentTitle, String contentText,
			Class<? extends Activity> clazz){
        Notification noti = new Notification();  
        
        noti.icon = iconResId;  
        noti.tickerText = tickerText;  
        noti.when = System.currentTimeMillis();
  
        noti.defaults |= Notification.DEFAULT_SOUND;  
        noti.flags = Notification.FLAG_AUTO_CANCEL ;  
  
        PendingIntent contentIntent = PendingIntent.getActivity  
                           (ctx, 0, new Intent(ctx, clazz), PendingIntent.FLAG_UPDATE_CURRENT);  
        noti.setLatestEventInfo(ctx, contentTitle, contentText, contentIntent);  
		
		return noti;
	}
	
	public static void sendNotification(Context ctx, int iconResId, 
			String tickerText, String contentTitle, String contentText,
			Class<? extends Activity> clazz){
        Notification noti = newNotification(ctx, iconResId, 
    			tickerText, contentTitle, contentText,
    			clazz);  
          
        NotificationManager notiManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);  
        notiManager.notify(Utils.uniqueInt(), noti);  
	}
	
	public static byte[] drawableToBytes(Drawable drawable) {
		Bitmap bitmap = drawableToBitmap(drawable);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}
	
	public static Drawable loadDrawable(Context ctx, int resId){
		Resources resources = ctx.getResources();
		return resources.getDrawable(resId);
	}
	
	private static Bitmap drawableToBitmap(Drawable drawable) {
		if(drawable instanceof BitmapDrawable){
			return ((BitmapDrawable)drawable).getBitmap();
		}else{
			Bitmap bitmap = Bitmap.createBitmap(
				drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight(),
				drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888: Bitmap.Config.RGB_565);
			return bitmap;
		}
	}

	public static Drawable bytesToDrawable(byte[] bytes) {
		Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
		return new BitmapDrawable(bitmap);
	}

	public static void startActivity(Context ctx,Class<? extends Activity> acti){
		Intent intent = new Intent(ctx,acti);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		ctx.startActivity(intent);
	}
	
	public static void startService(Context ctx,Class<? extends Service> service){
		Intent intent = new Intent(ctx,service);
		ctx.startService(intent);
	}
	
	public static void registerPackageChangeReceiver(Context ctx, final PackageChangeHandler pch){
		IntentFilter filter = new IntentFilter(Intent.ACTION_PACKAGE_ADDED);  
        filter.addAction(Intent.ACTION_PACKAGE_REMOVED);  
        filter.addAction(Intent.ACTION_PACKAGE_CHANGED);
        filter.addDataScheme("package");
        ctx.registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				final String action = intent.getAction();  
		        final String packageName = intent.getData().getSchemeSpecificPart();  
		        final boolean replacing = intent.getBooleanExtra(Intent.EXTRA_REPLACING, false);
		        if(action.equals(Intent.ACTION_PACKAGE_ADDED)){
		        	pch.added(packageName, replacing);
		        }else if(action.equals(Intent.ACTION_PACKAGE_REMOVED)){
		        	pch.removed(packageName, replacing);
		        }else if(action.equals(Intent.ACTION_PACKAGE_CHANGED)){
		        	pch.changed(packageName, replacing);
		        }
			}
		}, filter);
	}
	
	public PackageChangeHandler newPackageChangeHandler(){
		return new PackageChangeHandler() {
			//TODO
			@Override
			public void removed(String packageName, boolean replacing) {
			}
			
			@Override
			public void changed(String packageName, boolean replacing) {
			}
			
			@Override
			public void added(String packageName, boolean replacing) {
			}
		};
	}
	
	public static Intent browseIntent(String url){
		return new Intent(Intent.ACTION_VIEW, Uri.parse(url));
	}

	/** */
	public static Intent shortCutIntent(Activity act) {
		try {
			Intent addShortcut = new Intent();
			PackageManager pm = act.getPackageManager();
			ActivityInfo ai = pm.getActivityInfo(act.getComponentName(),
					PackageManager.GET_META_DATA);
			if (ai.labelRes != 0) {
				addShortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME,
						ai.loadLabel(pm).toString());
			}
			if (ai.icon != 0) {
				Parcelable icon = Intent.ShortcutIconResource.fromContext(act,
						ai.icon);
				addShortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
			}
			Intent intent = new Intent(act, act.getClass());
			AIOUtils.log("Shortcut of new Intent(" + act.getClass()
					+ ") returned.");
			addShortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
			return addShortcut;
		} catch (NameNotFoundException e) {
			AIOUtils.log("", e);
		}
		return null;
	}

	public static void logMessage(TextConsumer tc, String text) {
		if (tc != null)
			tc.appendText("["
					+ new SimpleDateFormat("hh:mm:ss").format(System
							.currentTimeMillis()) + "]:" + text + "\n");
	}

	public static void logMessage(TextConsumer tc, String text, Throwable t) {
		if (tc != null)
			tc.appendText("["
					+ new SimpleDateFormat("hh:mm:ss").format(System
							.currentTimeMillis()) + "]:" + text + " "+t.getMessage()+"\n");
	}

	public static void log(String text) {
		if(text!=null)
			Log.i(tag, text);
		else
			Log.i(tag,"unknown message.");
	}

	public static void log(String text, Throwable t) {
		if(text!=null)
			Log.i(tag, text, t);
		else
			Log.i(tag,"unknown message.",t);
	}

	private static void iterateFields(Object o,FieldValueFetcher fvf){
		Class clazz = o.getClass();
		Field[] fds = clazz.getDeclaredFields();
		ArrayList<String> ret = new ArrayList<String>();
		for (Field field : fds) {
			try {
				fvf.fieldValue(field.getName(),field.get(o));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static void iterateFields(Object o,FieldValueProvider fvp){
		Class<?> clazz = o.getClass();
		Field[] fds = clazz.getDeclaredFields();
		for (Field field : fds) {
			try {
				Object v = fvp.fieldValue(field.getName(),field.getType());
				field.set(o, v);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String appVersionName(Context ctx){
		String versionName = "";
		try {
			PackageManager pm = ctx.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), 0);
			//pi.applicationInfo.
			versionName = pi.versionName;
			if (versionName == null || versionName.length() <= 0) {
				return "";
			}
		} catch (Exception e) {
			Log.e(tag, "Exception", e);
		}
		return versionName;		
	}

	static Object fromJsonObject(final Object jo, Class<?> clazz){
		Object t = null;
		//Log.d("test", "fromJsonObject("+jo.getClass()+","+clazz+")");
		try {
			if(Utils.isSimpleType(clazz)){
				return jo;
			}else if(jo instanceof JSONArray && clazz.isArray()){
				JSONArray ja = (JSONArray)jo;
				Object ret = Array.newInstance(clazz.getComponentType(), ja.length());
				for (int i = 0; i < ja.length(); i++) {
					Object o = ja.get(i);
					Object o2 = fromJsonObject(o, clazz.getComponentType());
					Array.set(ret, i, o2);
				}
				return ret;
			}else if(jo instanceof JSONObject){
				final JSONObject jo2 = (JSONObject)jo;
				t = clazz.newInstance();
				iterateFields(t, new FieldValueProvider(){
					public Object fieldValue(String key, Class<?> type) {
						try {
							//Log.d("test", "(key,type)("+key+","+type+") about to call;");
							Object ret = null;
							ret = fromJsonObject(jo2.get(key), type);
							//Log.d("test", "return "+ret);
							return ret;
						} catch (JSONException e) {
							e.printStackTrace();
						}
						return null;
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	public static Object fromString(String str,Class<?> clazz){
		try{
			if(clazz.isArray()){
				return fromJsonObject(new JSONArray(str), clazz);
			}else if(Utils.isSimpleType(clazz)){
				return IOUtils.fromString(str, clazz);
			}else{
				return fromJsonObject(new JSONObject(str), clazz);
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Deprecated
	public static Object saveJsonFile(Object obj, String file) {
		FileOutputStream fileOut;
		try {
			File f = new File(file);
			if(!f.exists()){
				f.getParentFile().mkdirs();
				f.createNewFile();
			}
			fileOut = new FileOutputStream(f);
			ObjectOutputStream oos = new ObjectOutputStream(fileOut);
			oos.writeUTF(toString(obj));
			fileOut.close();
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	@Deprecated
	public static Object loadJsonFile(String file,Class<?> clazz) {
		FileInputStream in;
		try {
			File f = new File(file);
			if (!f.exists())
				return null;
			in = new FileInputStream(f);
			ObjectInputStream ois = new ObjectInputStream(in);
			String str = ois.readUTF();
			return fromString(str, clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> T[] fromXmlString(final TextConsumer tc, InputStream inStream, final Class<T> clazz){
		final List<T> list = new ArrayList<T>();
		try {
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser saxParser = spf.newSAXParser();
			DefaultHandler handler = new DefaultHandler(){
				Stack<Object> os = new Stack<Object>();
				Stack<String> ts = new Stack<String>();
				Stack<Field> fs = new Stack<Field>();
				@Override
				public void startElement(String uri, String localName,
						String name, Attributes attributes) throws SAXException {
	
					super.startElement(uri, localName, name, attributes);
					if(localName.equals(clazz.getSimpleName())){
						try {
							Object obj = clazz.newInstance();
							os.push(obj);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}else if(!os.isEmpty()){
						Field[] fields = os.peek().getClass().getFields();
						for(Field f:fields){
							if(f.getName().equals(localName)){
								ts.push(localName);
								fs.push(f);
								if(!Utils.isSimpleType(f.getType())){
									try {
										os.push(f.getType().newInstance());
									} catch (Exception e) {
										e.printStackTrace();
									}
									break;
								}
							}
						}
					}
				}
				
				@Override
				public void endElement(String uri, String localName, String name)
						throws SAXException {
					
					if(localName.equals(clazz.getSimpleName())){
						try {
							list.add((T)os.pop());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}else if(!ts.isEmpty()){
						ts.pop();
						Field f = fs.pop();
						if(!Utils.isSimpleType(f.getType())){
							Object o = os.pop();
							try {
								f.set(os.peek(), o);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
					super.endElement(uri, localName, name);
				}
	
				@Override
				public void characters(char[] ch, int start, int length)
						throws SAXException {
					super.characters(ch, start, length);
					String data = new String(ch, start, length); 
					if(!ts.isEmpty()){
						Field f = fs.peek();
						if(ts.peek().equals(f.getName()) && os.peek()!=null && Utils.isSimpleType(f.getType()) ){
							try {
								Object value = IOUtils.fromString(data,f.getType());
								f.set(os.peek(), value);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
	
			};
			saxParser.parse(inStream, handler);
			inStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list.toArray((T[])Array.newInstance(clazz, 0));
	}

	static Object toJsonObject(Object object){
		if(object == null)
			return null;
		
		Class<?> clazz = object.getClass();
		if(Utils.isSimpleType(clazz) || clazz.equals(JSONObject.class) || clazz.equals(JSONArray.class) )
			return object;
		else if(clazz.isArray()){
			JSONArray jo = new JSONArray();
			int len = Array.getLength(object);
			for (int i = 0; i < len; i++) {
				Object el = Array.get(object, i);
				jo.put(toJsonObject(el));
			}
			return jo;
		}else if(!Utils.isSimpleType(clazz)){			
			final JSONObject jo = new JSONObject();
			iterateFields(object, new FieldValueFetcher(){
				public void fieldValue(String key, Object value) {
					try {
						jo.put(key, toJsonObject(value));
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});
			return jo;
		}else{
			throw new RuntimeException("unsupported type.");
		}
	}

	public static String toString(Object object){
		if(object == null)
			return null;
		return toJsonObject(object).toString();
	}

}
