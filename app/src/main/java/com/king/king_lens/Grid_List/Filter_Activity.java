package com.king.king_lens.Grid_List;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.king.king_lens.R;

public class Filter_Activity extends AppCompatActivity implements Animation.AnimationListener {
    Animation animMove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_activity);



        animMove = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.anim);

        animMove.setAnimationListener(Filter_Activity.this);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
