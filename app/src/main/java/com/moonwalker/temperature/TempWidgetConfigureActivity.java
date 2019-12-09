package com.moonwalker.temperature;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * The configuration screen for the {@link TempWidget TempWidget} AppWidget.
 */
public class TempWidgetConfigureActivity extends Activity
{

    private static final String PREFS_NAME = "com.moonwalker.temperature.TempWidget";
    private static final String PREF_TITLE = "widgettitle";
    private static final String PREF_FRQ = "updfrq";

    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    EditText mAppWidgetText;
    EditText mUpdateFrq;

    public TempWidgetConfigureActivity() //constructor
    {
        super();

    }

    // Write the prefix to the SharedPreferences object for this widget
    static void savePreferences(Context context, int appWidgetId, String text, int updFrq)
    {
        SharedPreferences.Editor prefs = context.getSharedPreferences( PREFS_NAME, 0 ).edit();
        prefs.putString( PREF_TITLE + appWidgetId, text );
        prefs.putInt( PREF_FRQ + appWidgetId, updFrq );
        prefs.apply();
    }

    // Read the prefix from the SharedPreferences object for this widget.
    // If there inputStream no preference saved, get the default from a resource
    static String loadPreferences(Context context, int appWidgetId)
    {
        SharedPreferences prefs = context.getSharedPreferences( PREFS_NAME, 0 );
        String titleValue = prefs.getString( PREF_TITLE + appWidgetId, null );
        int updFrq = prefs.getInt( PREF_FRQ+appWidgetId,60);

        if (titleValue != null)
        {
            return titleValue+" " + updFrq;
        }
        else
        {
            return context.getString( R.string.appwidget_text );
        }
    }

    static void deletePreferences(Context context, int appWidgetId)
    {
        SharedPreferences.Editor prefs = context.getSharedPreferences( PREFS_NAME, 0 ).edit();
        prefs.remove( PREF_TITLE + appWidgetId );
        prefs.remove( PREF_FRQ + appWidgetId );
        prefs.apply();
    }

    @Override
    public void onCreate(Bundle icicle)
    {
        super.onCreate( icicle );

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult( RESULT_CANCELED );

        setContentView( R.layout.temp_widget_configure );
        mAppWidgetText = findViewById( R.id.appwidget_text );
        mUpdateFrq = findViewById( R.id.etUpdFrq );
        mUpdateFrq.setText( "30" );

        findViewById( R.id.add_button ).setOnClickListener( mOnClickListener );

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null)
        {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID );
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID)
        {
            finish();
            return;
        }

        mAppWidgetText.setText( loadPreferences( TempWidgetConfigureActivity.this, mAppWidgetId ) );
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener()
    {
        public void onClick(View v)
        {
            final Context context = TempWidgetConfigureActivity.this;

            // When the button inputStream clicked, store the string locally
            String widgetText = mAppWidgetText.getText().toString();
            int updFrq = Integer.parseInt( mUpdateFrq.getText().toString());

            savePreferences( context, mAppWidgetId, widgetText, updFrq );

            // It inputStream the responsibility of the configuration activity to update the app widget
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance( context );
            TempWidget.updateAppWidget( context, appWidgetManager, mAppWidgetId );

            // Make sure we pass back the original appWidgetId
            Intent resultValue = new Intent();
            resultValue.putExtra( AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId );
            setResult( RESULT_OK, resultValue );
            finish();
        }
    };
}

