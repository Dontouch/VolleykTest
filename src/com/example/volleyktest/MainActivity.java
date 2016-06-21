package com.example.volleyktest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.app.Activity;
import android.os.Bundle;

import android.util.Log;
import android.widget.*;


public class MainActivity extends Activity {

	private static RequestQueue queues;
	private TextView location;
	private String jsonString = null;
	private JSONArray array = null;
	private JSONObject jsonObject = null;
	
	private String place = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		location = (TextView) findViewById(R.id.textview);
	
		queues = Volley.newRequestQueue(getApplicationContext());
		
	
		getLocation();
		
		location.setText(getPlace());

	}

	public static RequestQueue getHttpQueues() {
		return queues;
	}
	
	public  String getPlace(){
		return place;
	}

	private void  getLocation() {

		String url = "https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=13189075235";
		
		StringRequest request = new StringRequest(Method.GET, url,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {

						jsonString = response;

						// 替换掉“__GetZoneResult_ = ”，让它能转换为JSONArray对象
						jsonString = jsonString.replaceAll(
								"^[__]\\w{14}+[_ = ]+", "[");
						// System.out.println(jsonString+"]");
						String jsonString2 = jsonString + "]";

						// location.setText(jsonString2);

						try {
							array = new JSONArray(jsonString2);
							jsonObject = array.getJSONObject(0);
							// System.out.println(jsonObject.toString());
							location.setText(jsonObject.getString("province"));
							
							place = jsonObject.getString("province");
							
		
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {

						Log.e("GetLocation", error.toString());

					}
				});

		request.setTag("GetLocation");
		MainActivity.getHttpQueues().add(request);
	}

}
