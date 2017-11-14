package com.example.aidlexample.ipc;

import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;

import com.example.aidlexample.warehouse.Container;
import com.example.aidlexample.warehouse.ContainerFeature;
import com.example.aidlexample.warehouse.Warehouse;
import com.example.aidlexample.warehouse.chemical.TNT;

public class WarehouseImpl extends IWarehouse.Stub {
    @Override
    public String addToContainer(TNT tnt) throws RemoteException {

        Log.d("Warehouse", "Packing very carefully...");
        SystemClock.sleep(1000);

        Container container = Warehouse.getInstance().addToContainer(tnt);
        if (container.isSafelyPacked()) {
            return "Your TNT is safely packed";
        }
        return "Ooops!";
    }

    @Override
    public void buildSummaryReport(IWarehouseServiceResponseListener listener) throws RemoteException {
        SystemClock.sleep(1000);

        Warehouse warehouse = Warehouse.getInstance();

        String summary = "";
        summary += "Containers: " + warehouse.getContainersTotalCount() + " \n\n";
        summary += "Armored: " + warehouse.getContainersCount(ContainerFeature.ARMORED) + " \n";
        summary += "Ventilated: " + warehouse.getContainersCount(ContainerFeature.VENTILATED) + " \n";
        summary += "Inert: " + warehouse.getContainersCount(ContainerFeature.INERT);

        listener.onResponse(summary);
    }
}
