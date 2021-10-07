package com.example.simplecalculator;

import android.content.Intent;
import android.util.Log;

public class Test {
    public boolean isTesting;
    Test(){

    }
    Test(Intent intent){
        if(intent.getAction().equals("android.intent.action.MAIN")) isTesting = false;
        else{
            isTesting = true;
        }
    }

    public void startTesting(){
    }

}
