package com.king.king_lens.Product;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;


import com.king.king_lens.R;

public class ProductView extends AppCompatActivity {


    RadioGroup radioEyegroup;
    LinearLayout lnlenspower;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lnlenspower=(LinearLayout)findViewById(R.id.lnlenspower) ;
        final Spinner lefteye=(Spinner)findViewById(R.id.lefteye);
        final Spinner righteye=(Spinner)findViewById(R.id.righteye);
        final Spinner botheye=(Spinner)findViewById(R.id.botheye);

        radioEyegroup = (RadioGroup) findViewById(R.id.radioEyegroup);

        radioEyegroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch(i){
                    case R.id.rbRighteye:
                        // do operations specific to this selection
                        lnlenspower.setVisibility(View.VISIBLE);
                        lefteye.setVisibility(View.INVISIBLE);
                        righteye.setVisibility(View.VISIBLE);
                        botheye.setVisibility(View.INVISIBLE);


                        break;
                    case R.id.rbLefteye:
                        // do operations specific to this selection
                        lnlenspower.setVisibility(View.VISIBLE);
                        lefteye.setVisibility(View.VISIBLE);
                        righteye.setVisibility(View.INVISIBLE);
                        botheye.setVisibility(View.INVISIBLE);


                        break;
                    case R.id.rbBotheye:
                        // do operations specific to this selection

                        lnlenspower.setVisibility(View.VISIBLE);
                        lefteye.setVisibility(View.INVISIBLE);
                        righteye.setVisibility(View.INVISIBLE);
                        botheye.setVisibility(View.VISIBLE);

                        break;
                }

            }
        });



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.product_menu, menu);
        return true;
    }//optionsmenu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.product_menu_heart) {

        }
        else if(id == R.id.product_menu_search) {

        }



        return super.onOptionsItemSelected(item);
    }//optionsitemSelected

}
