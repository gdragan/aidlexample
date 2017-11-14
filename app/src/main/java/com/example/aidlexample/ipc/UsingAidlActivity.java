package com.example.aidlexample.ipc;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aidlexample.R;
import com.example.aidlexample.warehouse.ContainerFeature;
import com.example.aidlexample.warehouse.chemical.TNT;

public class UsingAidlActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection {

    public static String TAG = UsingAidlActivity.class.getSimpleName();

    private Handler mHandler = new Handler();

    private IWarehouse warehouseService;
    private TextView reports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehouse);
        getSupportActionBar().setTitle("Warehouse AIDL");
        findViewById(R.id.add_tnt_to_container).setOnClickListener(this);
        findViewById(R.id.build_report).setOnClickListener(this);
        reports = findViewById(R.id.reports);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent service = new Intent(this, WarehouseService.class);
        if (!bindService(service, this, BIND_AUTO_CREATE)) {
            Log.w(TAG, "Failed to bind to service");
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        this.warehouseService = IWarehouse.Stub.asInterface(service);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        this.warehouseService = null;
    }


    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.build_report:
                    warehouseService.buildSummaryReport(new IWarehouseServiceResponseListener.Stub() {
                        @Override
                        public void onResponse(final String summary) throws RemoteException {
                            //Other process. We should run on UI Thread in order to interact with UI
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    reports.setText(summary);
                                }
                            });
                        }
                    });
                    break;
                case R.id.add_tnt_to_container:
                    String response = warehouseService.addToContainer(new TNT(400, ContainerFeature.ARMORED));
                    Toast.makeText(this, response, Toast.LENGTH_LONG).show();
                    break;

            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
