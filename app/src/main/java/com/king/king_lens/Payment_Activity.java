package com.king.king_lens;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;

import HelperClasses.AsyncResponse;
import HelperClasses.RegisterUser;
import HelperClasses.UserConstants;

public class Payment_Activity extends AppCompatActivity implements AsyncResponse.Response {

    EditText etTotalAmount;
    EditText etName;
    EditText etMobileno;
    EditText etAddress;
    EditText etCoupone;
    Button btnPlaceOrder;


    RegisterUser registerUser = new RegisterUser("POST");
    private String route = "api/v1/place-order";
    HashMap<String, String> data = new HashMap<>();


    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_wishfrag2);


        registerUser.delegate = this;

        etName=(EditText)findViewById(R.id.etName);
        etMobileno=(EditText)findViewById(R.id.etMobileno);
        etAddress=(EditText)findViewById(R.id.etAddress);
        etCoupone=(EditText)findViewById(R.id.etCoupone);
        btnPlaceOrder=(Button)findViewById(R.id.btnPlaceOrder);

        registerUser.register(data,route);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        etTotalAmount = (EditText) findViewById(R.id.etTotalAmount);
        etTotalAmount.setText(UserConstants.paymentAmount);

        final SharedPreferences prefs = getSharedPreferences("ADASAT", MODE_PRIVATE);

        int id=prefs.getInt("id",0);
        if(id==0)
        {
            id = prefs.getInt("guest_id",0);
            if(id==0)
            {
                Toast.makeText(this, "Please Login first!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this,LoginActivity.class);
                startActivity(i);
            }
        }



        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if(!etName.getText().toString().equals("")&&!etMobileno.getText().toString().equals("")&&!etAddress.getText().toString().equals(""))
                {

                    //final SharedPreferences prefs = getSharedPreferences("ADASAT", MODE_PRIVATE);

                    int id=prefs.getInt("id",0);
                    if(id==0)
                    {
                        id = prefs.getInt("guest_id",0);
                    }

                             data.put("user_id",String.valueOf(id));

                           // loading = ProgressDialog.show(My_Account.this, "","Updating profile...", true, false);
                          //  updateFunctionCalling=true;

                              data.put("ph_no",etMobileno.getText().toString());
                              data.put("address",etAddress.getText().toString());
                              data.put("coupon_applied",etCoupone.getText().toString());
                              data.put("total_amount",String.valueOf(UserConstants.paymentAmount));

                            /*data2.put("name",firstname.getText().toString());
                            data2.put("contact_number",ph_no.getText().toString());
                            data2.put("password",password.getText().toString());*/
                            //Toast.makeText(getApplicationContext(),"APi calling",Toast.LENGTH_LONG).show();
                               Log.i("data", String.valueOf(data));

                                //loading = ProgressDialog.show(getApplicationContext(), "", "Please Wait...", true);
                               registerUser.register(data,route);

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


        //loading.dismiss();
        Log.i("paymentpage","output of login api call is: "+output);

        try
        {
            JSONObject jsonObject = new JSONObject(output);
            if(jsonObject.getBoolean("status"))
            {
                Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                Intent i= new Intent(getApplicationContext(),Home_adslot.class);
                startActivity(i);


            }
            else
            {
                Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {
            Log.i("kingsukmajumder","error in update profile "+e.toString());
            //Toast.makeText(this, "Login or register to place a order!", Toast.LENGTH_SHORT).show();
        }

    }
}
