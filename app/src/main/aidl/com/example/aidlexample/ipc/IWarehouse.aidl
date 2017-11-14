// IWarehouse.aidl
package com.example.aidlexample.ipc;

// Declare any non-default types here with import statements
import com.example.aidlexample.warehouse.chemical.TNT;
import com.example.aidlexample.ipc.IWarehouseServiceResponseListener;

interface IWarehouse {
    String addToContainer(in TNT tnt);
    oneway void buildSummaryReport(in IWarehouseServiceResponseListener listener);
}
