// IWarehouseServiceResponseListener.aidl
package com.example.aidlexample.ipc;

// Declare any non-default types here with import statements

interface IWarehouseServiceResponseListener {
   oneway void onResponse(String response);
}
