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

    EditText inputName;
    EditText inputPrice;
    EditText inputDesc;
    Context ctx;
    JSONParser jsonParser;
    String sendwascorrect= Boolean.TRUE.toString();

    //private static String url_get_data = "http://localhost/insertsession.php";
    private static String url_get_data = "https://somejourney.info/iot_webfiles/read_temps.php?gettemperatures";


    // JSON Node names
    private static final String TAG_SUCCESS = "success";

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
        Log.d("Create Response", json.toString());

        try
        {
            int success = json.getInt(TAG_SUCCESS);
            if (success == 1)
            {

            }
            else
            {
                sendwascorrect = "false";
            }
        }
        catch (JSONException e)
        {
            sendwascorrect = "false";
            e.printStackTrace();

        }
        //new GetIoTDataTask().execute(stufftosend);
        return sendwascorrect;
    }

}
