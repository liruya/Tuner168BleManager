package com.liruya.tuner168blemanager;

import java.util.ArrayList;

public interface BleCommunicateListener {
    void onDataValid ( String mac );

    void onDataInvalid ( String mac );

    void onReadMfr ( String mac, String s );

    void onReadPassword ( String mac, int psw );

    void onDataReceived( String mac, ArrayList< Byte > list);
}
