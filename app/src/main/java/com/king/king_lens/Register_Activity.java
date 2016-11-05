package com.king.king_lens;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;

import HelperClasses.AsyncResponse;
import HelperClasses.EmailValidator;
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

    //loading variables
    ProgressDialog loading;

    //page variables
    String givenGender = "";

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

        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==1)
                {
                    givenGender = "Male";
                }
                else if(position==2)
                {
                    givenGender="Female";
                }
                else
                {
                    givenGender="";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                givenGender = "";
            }
        });
        /*data.put("name",firstname.getText().toString());
        registerUser.register(data,route);*/

        registerbtn=(Button)findViewById(R.id.reg_btn);
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EmailValidator emailValidator = new EmailValidator();
                String emailText = email.getText().toString();
                if(!firstname.getText().toString().equals("")&&!emailText.equals("")&&!password.getText().toString().equals("")&&!givenGender.equals("")&& !contact_no.getText().toString().equals(""))
                {
                    if(emailValidator.validate(emailText))
                    {
                        String passwordText = password.getText().toString();
                        if(passwordText.length()>5)
                        {
                            loading = ProgressDialog.show(Register_Activity.this, "","Validating user...", true, false);

                            data.put("name",firstname.getText().toString());
                            data.put("email",emailText);
                            data.put("password",passwordText);
                            data.put("country_id","1");
                            data.put("contact_number",contact_no.getText().toString());
                            data.put("user_type","regular");
                            data.put("gender",givenGender);
                            registerUser.register(data,route);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Password needs to be atleast 6 charecter long",Toast.LENGTH_SHORT).show();
                        }

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Please enter a valid email!",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"All fields are mandatory",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    @Override
    public void processFinish(String output) {
        loading.dismiss();
        Log.i("kingsukmajumder","output of reg api call is: "+output);
        try
        {
            JSONObject jsonObject = new JSONObject(output);
            if(jsonObject.getBoolean("status"))
            {
                JSONObject response = new JSONObject(jsonObject.getString("response"));
                SharedPreferences.Editor editor = getSharedPreferences("ADASAT", MODE_PRIVATE).edit();
                editor.putInt("id", response.getInt("id"));
                if(editor.commit())
                {
                    Intent intent = new Intent(Register_Activity.this,Home_adslot.class);
                    startActivity(intent);
                }
            }
            else
            {
                Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {
            Log.i("kingsukmajumder","error in register response "+e.toString());
        }
    }
}
