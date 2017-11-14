package com.example.aidlexample.ipc;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import com.example.aidlexample.warehouse.Warehouse;
import com.example.aidlexample.warehouse.chemical.Chemical;
import com.example.aidlexample.warehouse.chemical.TNT;

import java.io.Serializable;

public class WarehouseService extends Service {
    public static final String REQUEST = "request";
    public static final String REQUEST_DATA = "request_data";
    public static final String RESPONSE_DATA = "response_data";
    public static final String REQUEST_ADD_TO_CONTAINER = "request_add_to_container";
    public static final String RESPONSE_ADD_TO_CONTAINER = "response_add_to_container";

    public static final int MSG_ADD_TO_CONTAINER = 3;

    private WarehouseImpl service;

    @Override
    public void onCreate() {
        super.onCreate();
        service = new WarehouseImpl();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return service;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        service = null;
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String request = intent.getStringExtra(REQUEST);
        Messenger messenger = intent.getParcelableExtra("WarehouseMessenger");

        switch (request) {
            case REQUEST_ADD_TO_CONTAINER:
                Serializable data = intent.getSerializableExtra(REQUEST_DATA);

                addToContainer((TNT) data);
                SystemClock.sleep(3000);

                if (messenger == null) {
                    Intent response = new Intent(RESPONSE_ADD_TO_CONTAINER);
                    response.putExtra(RESPONSE_DATA, "Your TNT is safely packed");
                    sendBroadcast(response);
                } else {
                    try {
                        Bundle response = new Bundle();
                        response.putString(RESPONSE_DATA, "Your TNT is safely packed");

                        Message message = Message.obtain(null, MSG_ADD_TO_CONTAINER);
                        message.setData(response);
                        messenger.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }

        stopSelf();
        return START_NOT_STICKY;
    }

    public void addToContainer(Chemical data) {
        Warehouse.getInstance().addToContainer(data);
    }
}
