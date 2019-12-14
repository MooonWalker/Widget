package com.moonwalker.temperature;



import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import com.android.volley.Request;



public class PHPCom
{
    // Progress Dialog
    private  ProgressDialog pDialog;
    public static final String TEMP_HALO = "temphalo";
    public static final String HUMIDITY = "humidity";
    public static final String TIMESTAMP_HALO = "timestamphalo";
    public static final String TEMP_ERKELY = "temperkely";
    public static final String TIMESTAMP_ERKELY = "timestamperkely";
    public static final String QUERY_RESULT = "message";

    Context ctx;
    JSONParser jsonParser;
    String sendwascorrect= Boolean.TRUE.toString();

    //private static String url_get_data = "http://localhost/insertsession.php";
    private static String url_get_data = "https://somejourney.info/iot_webfiles/read_temps.php?gettemperatures";


    // JSON Node names
    private static final String TAG_SUCCESS = "message";

    public PHPCom(Context _ctx)
    {
        super();
        ctx=_ctx;
    }

    public String execute()
    {
        jsonParser= new JSONParser(ctx);
        JSONObject json = jsonParser.makeHttpRequest(url_get_data, Request.Method.GET);
        if (json == null)
        {
            return "wrong";
        }
        Log.d("PHPCom.Create Response", json.toString());

        try
        {
            String success = json.getString(TAG_SUCCESS);
            if (success.equals("OK"))
            {
                Double tempHalo = json.getDouble(TEMP_HALO);
                Double humidity = json.getDouble(HUMIDITY);
                String timeStampHalo = json.getString(TIMESTAMP_HALO);
                Double tempErkely = json.getDouble(TEMP_ERKELY);
                String timeStampErkely= json.getString(TIMESTAMP_ERKELY);


            }
            else
            {

            }
        }
        catch (JSONException e)
        {
            sendwascorrect = "false";
            e.printStackTrace();

        }
        return sendwascorrect;
    }

}
