package com.example.aidlexample.ipc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.aidlexample.R;
import com.example.aidlexample.warehouse.ContainerFeature;
import com.example.aidlexample.warehouse.chemical.TNT;

import java.io.Serializable;

import static com.example.aidlexample.ipc.WarehouseService.REQUEST;
import static com.example.aidlexample.ipc.WarehouseService.REQUEST_DATA;
import static com.example.aidlexample.ipc.WarehouseService.RESPONSE_ADD_TO_CONTAINER;
import static com.example.aidlexample.ipc.WarehouseService.RESPONSE_DATA;

public class UsingIntentsActivity extends AppCompatActivity implements View.OnClickListener {

  WarehouseServiceReceiver serviceReceiver = new WarehouseServiceReceiver();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_warehouse);
    getSupportActionBar().setTitle("Warehouse Intents");

    findViewById(R.id.add_tnt_to_container).setOnClickListener(this);
  }

  @Override
  protected void onStart() {
    super.onStart();
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction(WarehouseService.RESPONSE_ADD_TO_CONTAINER);
    registerReceiver(serviceReceiver, intentFilter);
  }

  @Override
  protected void onStop() {
    super.onStop();
    unregisterReceiver(serviceReceiver);
  }

  @Override
  public void onClick(View view) {
    Intent intent = new Intent(this, WarehouseService.class);
    if (view.getId() == R.id.add_tnt_to_container) {
      intent.putExtra(REQUEST, WarehouseService.REQUEST_ADD_TO_CONTAINER);
      intent.putExtra(REQUEST_DATA, (Serializable) new TNT(400, ContainerFeature.ARMORED));
    }
    startService(intent);
  }

  static class WarehouseServiceReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
      if (intent == null || intent.getAction() == null) {
        return;
      }

      switch (intent.getAction()) {
        case RESPONSE_ADD_TO_CONTAINER:
          String response = intent.getStringExtra(RESPONSE_DATA);
          if (response != null) {
            Toast.makeText(context, response, Toast.LENGTH_LONG).show();
          } else {
            Toast.makeText(context, "Ooops!", Toast.LENGTH_LONG).show();
          }
          break;
      }
    }
  }
}
