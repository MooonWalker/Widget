package com.moonwalker.temperature;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import java.io.InputStream;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONParser
{
    public static final String TEMP_HÁLÓ = "temphalo";
    public static final String TIMESTAMP_HÁLÓ = "timestamphalo";
    public static final String TEMP_ERKÉLY = "temperkely";
    public static final String TIMESTAMP_ERKÉLY = "timestamperkely";
    public static final String QUERY_RESULT = "message";

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
                             try
                             {
                                 Log.d("JsonParser.onResponse", response.toString());
                                 Double tempHalo = response.getDouble(TEMP_HÁLÓ);
                                 String timestampHalo = response.getString(TIMESTAMP_HÁLÓ);
                                 Double tempErkely = response.getDouble(TEMP_ERKÉLY);
                                 String timestampErkely = response.getString(TIMESTAMP_ERKÉLY);
                                 String serverResult = response.getString(QUERY_RESULT);
                                 jObj = new JSONObject();
                                 jObj=response;

                             }
                             catch (JSONException e)
                             {
                                 e.printStackTrace();
                             }
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

        // return JSON String
        return jObj;

    }
}