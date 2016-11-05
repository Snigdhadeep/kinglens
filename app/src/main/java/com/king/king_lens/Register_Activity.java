package com.king.king_lens;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;

import HelperClasses.AsyncResponse;
import HelperClasses.RegisterUser;

public class Register_Activity extends AppCompatActivity implements AsyncResponse.Response {



    EditText firstname;
    EditText email;
    EditText password;
    Spinner gender;
    EditText contact_no;
    Button registerbtn;

    //server variables

    RegisterUser registerUser = new RegisterUser("POST");
    private String route = "api/v1/user/register";
    HashMap<String,String> data = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        registerUser.delegate = this;

        firstname=(EditText)findViewById(R.id.firstname);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        gender=(Spinner)findViewById(R.id.gender);
        contact_no=(EditText)findViewById(R.id.contact_no);
        /*data.put("name",firstname.getText().toString());
        registerUser.register(data,route);*/

        registerbtn=(Button)findViewById(R.id.reg_btn);
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(firstname.getText().toString().equals(""))

                {
                    Toast.makeText(getApplicationContext(),"name can't be empty",Toast.LENGTH_SHORT).show();


                }
                else
                {
                    data.put("name",firstname.getText().toString());
                    data.put("email",email.getText().toString());
                    data.put("password",password.getText().toString());

                    data.put("gender",gender.toString());
                    data.put("contact_no",contact_no.getText().toString());

                    registerUser.register(data,route);


                }


            }
        });


    }

    @Override
    public void processFinish(String output) {
        Log.i("snigdho","output of reg api call is: "+output);
    }
}
