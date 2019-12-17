package com.moonwalker.temperature;



import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;


public class PHPCom
{
    // Progress Dialog
    private ProgressDialog pDialog;
    public static final String TEMP_HALO = "temphalo";
    public static final String HUMIDITY = "humidity";
    public static final String TIMESTAMP_HALO = "timestamphalo";
    public static final String TEMP_ERKELY = "temperkely";
    public static final String TIMESTAMP_ERKELY = "timestamperkely";
    public static final String QUERY_RESULT = "message";

    Context ctx;
    JSONParser jsonParser;


    //private static String url_get_data = "http://localhost/insertsession.php";
    private static String url_get_data = "https://somejourney.info/iot_webfiles/read_temps.php?gettemperatures";


    public PHPCom(Context _ctx)
    {
        super();
        ctx = _ctx;
    }

    public void execute()
    {
        jsonParser = new JSONParser( ctx );
        jsonParser.makeHttpRequest( url_get_data, Request.Method.GET );

        //return ioTData;
    }

}
