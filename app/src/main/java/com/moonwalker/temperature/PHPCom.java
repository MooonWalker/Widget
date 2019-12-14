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

    public IoTData execute()
    {
        jsonParser= new JSONParser(ctx);
        IoTData ioTData = new IoTData();

        try
        {
            JSONObject json = jsonParser.makeHttpRequest(url_get_data, Request.Method.GET);
            if (json == null)
            {
                throw new Exception("PHPCom.json is null");
            }
            //Log.d("PHPCom.Create Response", json.toString());
            String success = json.getString(QUERY_RESULT);
            if (success.equals("OK"))
            {
                double tempHalo = json.getDouble(TEMP_HALO);
                double humidity = json.getDouble(HUMIDITY);
                String timeStampHalo = json.getString(TIMESTAMP_HALO);
                double tempErkely = json.getDouble(TEMP_ERKELY);
                String timeStampErkely= json.getString(TIMESTAMP_ERKELY);

                ioTData.setTemphalo(tempHalo);
                ioTData.setHumidity(humidity);
                ioTData.setTimestamphalo(timeStampHalo);
                ioTData.setTempErkely(tempErkely);
                ioTData.setTimestampErkely(timeStampErkely);
                ioTData.setMessage(success);
            }
            else
            {
                throw new JSONException("PHPCom");
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ioTData;
    }

}
