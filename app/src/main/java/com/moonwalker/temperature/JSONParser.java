package com.moonwalker.temperature;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.IOException;
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

            }
            else if(method == Request.Method.GET)
            {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                     (Request.Method.GET,
                             url,
                             null,
                             new Response.Listener<JSONObject>()
                     {
                         @Override
                            public void onResponse(JSONObject response)
                         {
                             Log.d("JsonParser.onResponse",response.toString());
                                //textView.setText("Response: " + response.toString());
                         }
                     }, new Response.ErrorListener()
                            {
                                @Override
                                public void onErrorResponse(VolleyError error)
                                {
                                    // TODO: Handle error
                                    Log.d("JsonParser.onErrorResponse",error.toString());
                                }
                            });

                Singletonclass.getInstance(context).addToRequestQueue(jsonObjectRequest);
            }
        }//try
        catch (Exception e)
        {
            e.printStackTrace();
        }


        // try parse the string to a JSON object
        try
        {
            jObj = new JSONObject(json);
        }
        catch (JSONException e)
        {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON String
        return jObj;

    }
}