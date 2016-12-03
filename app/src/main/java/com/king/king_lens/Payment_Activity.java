package com.king.king_lens;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import HelperClasses.UserConstants;

public class Payment_Activity extends AppCompatActivity {

    EditText etTotalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_wishfrag2);

       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etTotalAmount = (EditText) findViewById(R.id.etTotalAmount);
        etTotalAmount.setText(UserConstants.paymentAmount);*/
    }
}
