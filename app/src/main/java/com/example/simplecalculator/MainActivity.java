package com.example.simplecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    protected TextView screen;

    protected float caller=0, called=0;
    protected StringBuffer stored, showed;
    protected boolean signed = false;
    protected char op = 0;

    protected int eqCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stored = new StringBuffer();
        showed = new StringBuffer();

        screen = findViewById(R.id.textView);
        screen.setText("0");

        Float.parseFloat((screen.getText().toString()));
    }

    public void clear(View view) {
        screen.setText("0");
        showed.delete(0,showed.length());
        stored.delete(0,stored.length());
    }

    public void sign(View view){
        if(signed) signed = false;
        else signed = true;
    }

    public void operate(View view){

        if(view == findViewById(R.id.btn_eq)) {
            prepare();

            if (eqCount == 0) {
                stored.delete(0, stored.length());
                stored.append(showed.toString());
            }
            float res;
            if (eqCount == 0){
                res = calculate(called, caller, op);

            }else{
                res = calculate(caller, called, op);
            }
            update(res);
            showed.delete(0, showed.length());


            eqCount++;
        }else{

            if(view == findViewById(R.id.btn_add)) op = '+';
            if(view == findViewById(R.id.btn_sub)) op = '-';
            if(view == findViewById(R.id.btn_mul)) op = '*';
            if(view == findViewById(R.id.btn_div)) op = '/';


            if(eqCount == 0) {
                if (showed.length() != 0 && stored.length() != 0) {
                    prepare();

                    float res = calculate(called, caller, op );
                    update(res);

                }
            }
                stored.delete(0, showed.length());
                stored.append(showed.toString());
                showed.delete(0, showed.length());

                eqCount = 0;
        }


    }

    public void prepare(){
        if(showed.length() == 0) caller = 0; else
            caller = Float.parseFloat(showed.toString());
        if(stored.length() == 0) called = 0; else
            called = Float.parseFloat(stored.toString());
    }

    public void update(float res){
        showed.delete(0, showed.length());
        showed.append(res);
        screen.setText(showed.toString());
    }

    public float calculate(float a, float b, char op){
        if(op == '+')
            return a+b;
        if(op == '-')
            return a-b;
        if(op == '*')
            return a*b;
        if(op == '/')
            return a/b;

        return a;
    }

    public void number(View view){
        showed.append(((Button)view).getText().toString());
        screen.setText(showed.toString());
    }


    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();

        Handler h = new Handler();


        Test test = new Test(intent);
        if(test.isTesting){
            test.startTesting();

            h.postDelayed(new JustRun(findViewById(R.id.btn7)), 1000);
            h.postDelayed(new JustRun(findViewById(R.id.btn_add)), 2000);
            h.postDelayed(new JustRun(findViewById(R.id.btn8)), 3000);
            h.postDelayed(new JustRun(findViewById(R.id.btn_eq)), 4000);


        }
    }

    class JustRun implements Runnable {
        Button btn;
        JustRun(Button btn){
            this.btn = btn;
        }

        @Override
        public void run() {
            btn.performClick();
            btn.setTextColor(Color.RED);
            Handler h = new Handler();
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    btn.setTextColor(Color.BLACK);
                }
            };
            h.postDelayed(r,1000);
        }
    }
}