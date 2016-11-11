package com.king.king_lens;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;

import HelperClasses.AsyncResponse;
import HelperClasses.CheckNetwork;
import HelperClasses.EmailValidator;
import HelperClasses.RegisterUser;

public class My_Account extends AppCompatActivity implements AsyncResponse.Response {

    EditText firstname;
    Button save_btn;
    ProgressDialog loading;

    RegisterUser registerUser = new RegisterUser("POST");
    private String route = "api/v1/user/profile";
    HashMap<String, String> data = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);





     //   firstname.setText(name);

        registerUser.delegate = this;

        firstname=(EditText)findViewById(R.id.firstname);
        save_btn=(Button)findViewById(R.id.save_btn);




        final SharedPreferences prefs = getSharedPreferences("ADASAT", MODE_PRIVATE);

        int id=prefs.getInt("id",0);

        data.put("user_id",String.valueOf(id));

        loading = ProgressDialog.show(My_Account.this, "","Validating user...", true, false);

        registerUser.register(data,route);



    }








    @Override
    public void processFinish(String output) {

        Log.i("kingsukmajumder","output of login api call is: "+output);

        loading.dismiss();
        try
        {
            JSONObject jsonObject = new JSONObject(output);
            if(jsonObject.getBoolean("status"))
            {
                JSONObject response = new JSONObject(jsonObject.getString("response"));
                String name=response.getString("name");
                firstname.setText(name);
                Log.i("kmajumdar1",""+response.toString());


            }
            else
            {
                Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {
            Log.i("kingsukmajumder","error in login response "+e.toString());
        }


    }
}