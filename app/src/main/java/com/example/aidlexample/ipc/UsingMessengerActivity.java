package com.example.aidlexample.ipc;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.aidlexample.R;
import com.example.aidlexample.warehouse.ContainerFeature;
import com.example.aidlexample.warehouse.chemical.TNT;

import java.io.Serializable;

import static com.example.aidlexample.ipc.WarehouseService.REQUEST;
import static com.example.aidlexample.ipc.WarehouseService.RESPONSE_DATA;

public class UsingMessengerActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehouse);
        getSupportActionBar().setTitle("Warehouse Messenger");

        findViewById(R.id.add_tnt_to_container).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.add_tnt_to_container) {
            Intent intent = new Intent(this, WarehouseService.class);

            final Messenger messenger = new Messenger(new IncomingHandler());
            intent.putExtra("WarehouseMessenger", messenger);

            TNT tnt = new TNT(400, ContainerFeature.ARMORED);
            intent.putExtra(REQUEST, WarehouseService.REQUEST_ADD_TO_CONTAINER);
            intent.putExtra(WarehouseService.REQUEST_DATA, (Serializable) tnt);

            startService(intent);
        }
    }

    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WarehouseService.MSG_ADD_TO_CONTAINER:
                    Bundle response = msg.getData();
                    String data = response.getString(RESPONSE_DATA);
                    if (data != null) {
                        Toast.makeText(getApplicationContext(), data, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Ooops!", Toast.LENGTH_LONG).show();
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }
}
