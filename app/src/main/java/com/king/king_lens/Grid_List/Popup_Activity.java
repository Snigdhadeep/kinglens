package com.king.king_lens.Grid_List;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.king.king_lens.R;

import java.util.zip.Inflater;

public class Popup_Activity extends AppCompatActivity {

    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_);

     /*   DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width=dm.widthPixels;
        int hight=dm.heightPixels;

        getWindow().setLayout((int) (width*0.4),(int)(hight*0.4));*/


      //  linearLayout=(LinearLayout)findViewById(R.id.loop_popup);

        int i=6;
        while (i>0)
        {

            View inflatedlayout=getLayoutInflater().inflate(R.layout.listview_popup,null,false);
            linearLayout.addView(inflatedlayout);
            i--;

        }




    }


}
