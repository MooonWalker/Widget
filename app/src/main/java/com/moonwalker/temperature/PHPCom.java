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

    JSONParser jsonParser = new JSONParser();
    EditText inputName;
    EditText inputPrice;
    EditText inputDesc;
    Context ctx;
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
        IoTData stufftosend = new IoTData();
        new GetIoTDataTask().execute(stufftosend);
        return sendwascorrect;
    }
//=============================================================================================
    //class GetIoTDataTask extends AsyncTask<List<SessionH>, String, String>
    class GetIoTDataTask extends AsyncTask<IoTData, String, String>
    {
        Boolean running;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(ctx);
            pDialog.setMessage("Uploading statistics...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            // pDialog.show();
            running=true;
        }

        @Override
        protected void onCancelled()
        {
            running=false;
            //pDialog.dismiss();
            super.onCancelled();
        }

        // protected String doInBackground(List<SessionH>... sessions)
        protected String doInBackground(IoTData... ioTData)
        {

           JSONObject json = jsonParser.makeHttpRequest(url_get_data, Request.Method.GET);
                if (json == null)
                {
                    cancel(true);
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

            if(isCancelled())return sendwascorrect="false";

            return sendwascorrect;
        }

        protected void onPostExecute(String file_url)
        {

            // pDialog.dismiss();
        }
    }
}
