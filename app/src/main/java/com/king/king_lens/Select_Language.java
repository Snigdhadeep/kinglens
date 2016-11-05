package com.king.king_lens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Select_Language extends AppCompatActivity  implements View.OnClickListener {

    ImageView country1;
    ImageView country2;
    ImageView country3;
    ImageView country4;
    ImageView country5;
    ImageView country6;

    Button english_lang;
    Button urdu_lang;

    //page variables

    int IntCountry=0;
    int IntLanguage=0;

    Button start;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select__language);


        //for start button

        start=(Button)findViewById(R.id.start);
        start.setOnClickListener(this);




        country1=(ImageView)findViewById(R.id.country1);
        country2=(ImageView)findViewById(R.id.country2);
        country3=(ImageView)findViewById(R.id.country3);
        country4=(ImageView)findViewById(R.id.country4);
        country5=(ImageView)findViewById(R.id.country5);
        country6=(ImageView)findViewById(R.id.country6);




        country1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                country1.setImageResource(R.drawable.countries_1_1);
                country2.setImageResource(R.drawable.country2);
                country3.setImageResource(R.drawable.country3);
                country4.setImageResource(R.drawable.country4);
                country5.setImageResource(R.drawable.country5);
                country6.setImageResource(R.drawable.country6);

                IntCountry=188;


            }
        });


        country2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                country2.setImageResource(R.drawable.countries_2_1);
                country1.setImageResource(R.drawable.country1);
                country3.setImageResource(R.drawable.country3);
                country4.setImageResource(R.drawable.country4);
                country5.setImageResource(R.drawable.country5);
                country6.setImageResource(R.drawable.country6);

                IntCountry=227;
            }
        });


        country3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                country3.setImageResource(R.drawable.countries_3_1);
                country1.setImageResource(R.drawable.country1);
                country2.setImageResource(R.drawable.country2);
                country4.setImageResource(R.drawable.country4);
                country5.setImageResource(R.drawable.country5);
                country6.setImageResource(R.drawable.country6);

                IntCountry=17;
            }
        });


        country4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                country4.setImageResource(R.drawable.countries_4_1);
                country1.setImageResource(R.drawable.country1);
                country2.setImageResource(R.drawable.country2);
                country3.setImageResource(R.drawable.country3);
                country5.setImageResource(R.drawable.country5);
                country6.setImageResource(R.drawable.country6);

                IntCountry=112;
            }
        });



        country5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                country5.setImageResource(R.drawable.countries_5_1);
                country1.setImageResource(R.drawable.country1);
                country2.setImageResource(R.drawable.country2);
                country3.setImageResource(R.drawable.country3);
                country4.setImageResource(R.drawable.country4);
                country6.setImageResource(R.drawable.country6);

                IntCountry=172;
            }
        });

        country6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                country6.setImageResource(R.drawable.countries_6_1);
                country1.setImageResource(R.drawable.country1);
                country2.setImageResource(R.drawable.country2);
                country3.setImageResource(R.drawable.country3);
                country4.setImageResource(R.drawable.country4);
                country5.setImageResource(R.drawable.country5);
                IntCountry=159;

            }
        });


        english_lang=(Button)findViewById(R.id.english_lang);
        urdu_lang=(Button)findViewById(R.id.urdu_lang);

        english_lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                english_lang.setBackgroundResource(R.drawable.button_back_selected);
                urdu_lang.setBackgroundResource(R.drawable.rect1);
                IntLanguage=1;
            }
        });

        urdu_lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urdu_lang.setBackgroundResource(R.drawable.button_back_selected);
                english_lang.setBackgroundResource(R.drawable.rect1);
                IntLanguage=2;
            }
        });



    }

    @Override
    public void onClick(View view) {
        if(IntLanguage!=0 && IntCountry!=0)
        {

            SharedPreferences.Editor editor = getSharedPreferences("ADASAT", MODE_PRIVATE).edit();
            editor.putInt("language", IntLanguage);
            editor.putInt("country",IntCountry);
            if(editor.commit())
            {
                Intent intent = new Intent(Select_Language.this,Home_adslot.class);
                startActivity(intent);
            }
        }
        else
        {
            Toast.makeText(Select_Language.this, "Country & Language needs to be selected!", Toast.LENGTH_SHORT).show();
        }
    }
}
