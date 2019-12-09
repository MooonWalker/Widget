package com.moonwalker.temperature;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONParser
{
    public static final String TAG = "TempWidget";
    static InputStream inputStream = null;
    static JSONObject jObj = null;
    static String json = "";
    Context context;
    static String result="";

    // constructor
    public JSONParser()
    {
        super();
    }

    public JSONParser(Context ctx)
    {
        super();
        context =ctx;
    }

    // function get json from url
    // by making HTTP POST or GET mehtod
    public JSONObject makeHttpRequest(String url, int method)
    {
        // Making HTTP request
        try
        {
            if(method == Request.Method.POST)
            {
//                DefaultHttpClient httpClient = new DefaultHttpClient();
//                HttpPost httpPost = new HttpPost(url);
//                httpPost.setEntity(new UrlEncodedFormEntity(params));
//
//                HttpResponse httpResponse = httpClient.execute(httpPost);
//                HttpEntity httpEntity = httpResponse.getEntity();
//                inputStream = httpEntity.getContent();
            }
            else if(method == Request.Method.GET)
            {
                RequestQueue requestQueue= Volley.newRequestQueue( context );
                StringRequest stringRequest = new StringRequest( Request.Method.GET, url,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response)
                            {
                                // Display the first 500 characters of the response string.
                          result="Response inputStream: "+ response.substring(0,500);
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error)
                            {
                                result="That didn't work!";
                            }
                });

                stringRequest.setTag(TAG);
//              Add the request to the RequestQueue.
                requestQueue.add(stringRequest);

//                @Override
//                protected void onStop () {
//                super.onStop();
//                if (requestQueue != null) {
//                    requestQueue.cancelAll(TAG);
//                }
//            }

            }
        }//try
        catch (Exception e)
        {
            e.printStackTrace();
        }


        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader( inputStream ), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            inputStream.close();
            json = sb.toString();
        }
        catch (Exception e)
        {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        // try parse the string to a JSON object
        try
        {
            jObj = new JSONObject(json);
        } catch (JSONException e)
        {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON String
        return jObj;

    }
}