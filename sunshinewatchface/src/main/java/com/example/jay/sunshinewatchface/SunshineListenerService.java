package com.example.jay.sunshinewatchface;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.WearableListenerService;

public class SunshineListenerService extends WearableListenerService {

    private static final String TAG = SunshineListenerService.class.getSimpleName();

    @Override
    public void onDataChanged(DataEventBuffer dataEventBuffer) {

        for (DataEvent dataEvent : dataEventBuffer) {
            DataMap dataMap = DataMapItem.fromDataItem(dataEvent.getDataItem()).getDataMap();
            String path = dataEvent.getDataItem().getUri().getPath();
            Log.d(TAG, path);
            if (path.equals("/weather-data")) {
                Log.d(TAG, "Data successfully received");
                String high = dataMap.getString("temp-high");
                String low = dataMap.getString("temp-low");
                byte[] weathericon = dataMap.getByteArray("temp-icon");
                Log.d(TAG, high + " " + low + String.valueOf(weathericon.length));

                Intent weatherData = new Intent("weather");
                weatherData.putExtra("temp-high", high);
                weatherData.putExtra("temp-low", low);
                weatherData.putExtra("temp-icon", weathericon);

                sendBroadcast(weatherData);
                Log.d(TAG, "sending weather data broadcast...");
            }
        }

    }
}
