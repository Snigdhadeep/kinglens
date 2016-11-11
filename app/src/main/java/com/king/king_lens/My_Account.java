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
    EditText gender;
    EditText ph_no;
    EditText password;

    //page
    boolean updateFunctionCalling = false;

    ProgressDialog loading;

    RegisterUser registerUser = new RegisterUser("POST");
    private String route = "api/v1/user/profile";
    HashMap<String, String> data = new HashMap<>();

    private String route2 = "api/v1/user/update";
    HashMap<String, String> data2 = new HashMap<>();

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
        gender = (EditText) findViewById(R.id.gender);
        ph_no = (EditText) findViewById(R.id.ph_no);
        password = (EditText) findViewById(R.id.password);




        final SharedPreferences prefs = getSharedPreferences("ADASAT", MODE_PRIVATE);

        final int id=prefs.getInt("id",0);

        data.put("user_id",String.valueOf(id));

        loading = ProgressDialog.show(My_Account.this, "","Getting profile detalis...", true, false);

        registerUser.register(data,route);


        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(getApplicationContext(),"clicked",Toast.LENGTH_LONG).show();

                if(!firstname.getText().toString().equals("")&&!ph_no.getText().toString().equals("")&&!password.getText().toString().equals(""))
                {
                    //Toast.makeText(getApplicationContext(),"conditon satisfied",Toast.LENGTH_LONG).show();
                    if(password.getText().length()>5)
                    {
                        loading = ProgressDialog.show(My_Account.this, "","Updating profile...", true, false);
                        updateFunctionCalling=true;
                        data2.put("id",String.valueOf(id));
                        data2.put("name",firstname.getText().toString());
                        data2.put("contact_number",ph_no.getText().toString());
                        data2.put("password",password.getText().toString());
                        //Toast.makeText(getApplicationContext(),"APi calling",Toast.LENGTH_LONG).show();
                        registerUser.register(data2,route2);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Password needs to be atleast 6 charecter!",Toast.LENGTH_LONG).show();
                    }

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"All fields are mandatory!",Toast.LENGTH_LONG).show();
                }


            }
        });
    }








    @Override
    public void processFinish(String output) {

        Log.i("kingsukmajumder","output of login api call is: "+output);

        loading.dismiss();
        if(updateFunctionCalling)
        {
            Toast.makeText(getApplicationContext(),output,Toast.LENGTH_LONG).show();
        }
        else
        {
            try
            {
                JSONObject jsonObject = new JSONObject(output);
                if(jsonObject.getBoolean("status"))
                {
                    JSONObject response = new JSONObject(jsonObject.getString("response"));
                    String name=response.getString("name");
                    firstname.setText(name);
                    gender.setText(response.getString("gender"));
                    ph_no.setText(response.getString("contact_number"));

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
}