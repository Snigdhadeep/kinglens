package com.king.king_lens;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Splashscreen extends AppCompatActivity {

    Button entrbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        entrbtn = (Button) findViewById(R.id.entrbtn);
        entrbtn.setVisibility(View.INVISIBLE);
        entrbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Splashscreen.this,Home_adslot.class);
                startActivity(i);
            }
        });

        int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                /*startActivity(new Intent(Splashscreen.this, EnterScreen.class));
                finish();*/
                entrbtn.setVisibility(View.VISIBLE);
            }
        }, secondsDelayed * 2000);
    }
}
