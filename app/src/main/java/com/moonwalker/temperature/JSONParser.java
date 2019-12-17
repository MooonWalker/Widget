package com.moonwalker.temperature;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import static com.moonwalker.temperature.PHPCom.HUMIDITY;
import static com.moonwalker.temperature.PHPCom.QUERY_RESULT;
import static com.moonwalker.temperature.PHPCom.TEMP_ERKELY;
import static com.moonwalker.temperature.PHPCom.TEMP_HALO;
import static com.moonwalker.temperature.PHPCom.TIMESTAMP_ERKELY;
import static com.moonwalker.temperature.PHPCom.TIMESTAMP_HALO;

public class JSONParser
{
    static JSONObject jObj;
    static String json = "";
    Context context;
    static String result="";

    // constructor
    public JSONParser(Context ctx)
    {
        super();
        context =ctx;
    }

    public void
    makeHttpRequest(String url, int method)
    {
        // Making HTTP request
            if(method == Request.Method.POST)
            {
            }
            else if(method == Request.Method.GET)
            {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                     (Request.Method.GET, url,null,
                      new Response.Listener<JSONObject>()
                         {
                             @Override
                                public void onResponse(JSONObject response)
                                 {
                                     Log.d("JsonParser.onResponse", response.toString());
                                     //jObj = new JSONObject();
                                     String success;
                                     double tempHalo;
                                     double humidity;
                                     String timeStampHalo;
                                     double tempErkely;
                                     String timeStampErkely;

                                     try
                                     {
                                         Calendar calendar = Calendar.getInstance();
                                         SimpleDateFormat df = new SimpleDateFormat("MMM. dd, HH:mm");
                                         String formattedDate = df.format(calendar.getTime());

                                         success = response.getString(QUERY_RESULT);
                                         tempHalo = response.getDouble(TEMP_HALO);
                                         humidity = response.getDouble(HUMIDITY);
                                         timeStampHalo = response.getString(TIMESTAMP_HALO);
                                         tempErkely = response.getDouble(TEMP_ERKELY);
                                         timeStampErkely = response.getString(TIMESTAMP_ERKELY).substring(5,16).replace(
                                                 "-", ".");


                                         String lastUpdate = context.getString( R.string.Bedroom) +
                                                 tempHalo+"C"+
                                                 "  "+
                                                 context.getString( R.string.Balcony)+
                                                 tempErkely+"C"
                                                 +"\n" +
                                                 context.getString(R.string.RH)+ ": " + humidity+"% "+
                                                 "  "+   timeStampErkely;

                                         RemoteViews view = new RemoteViews("com.moonwalker.temperature",R.layout.temp_widget);
                                         view.setTextViewText(R.id.appwidget_text, lastUpdate);

                                         ComponentName theWidget = new ComponentName(context, TempWidget.class);
                                         AppWidgetManager manager = AppWidgetManager.getInstance(context);
                                         manager.updateAppWidget(theWidget, view);

                                         Log.d("Fetchdata.onStartJob","after setting text");
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
                            }
                     );

                Singletonclass.getInstance(context).addToRequestQueue(jsonObjectRequest);
            }
        // return JSON String
        //return jObj;
    }
}