package com.king.king_lens;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;

import HelperClasses.Api;
import HelperClasses.AsyncResponse;
import HelperClasses.CheckNetwork;
import HelperClasses.EmailValidator;
import HelperClasses.RegisterUser;

public class LoginActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AsyncResponse.Response {

    Button signin;
    Button signup_btn;
    EditText user_email;
    EditText user_password;

    Button register_here;

    TextView forgotpass;

    EditText email_guest;
    Button guest_button;

    //server variables
    RegisterUser registerUser = new RegisterUser("POST");
    private String route = "api/v1/user/auth";
    HashMap<String,String> data = new HashMap<>();


   // Api api=new Api("POST");
    private String route2="api/v1/guest/register";
    HashMap<String,String> data2 = new HashMap<>();


    //loading variables
    ProgressDialog loading;
    boolean guestApiCallin=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        registerUser.delegate = this;
      //  api.delegate=this;

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //for forgetpass
        forgotpass=(TextView)findViewById(R.id.forgotpass);
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showForgetPassword();
            }
        });


        register_here=(Button)findViewById(R.id.register);
        register_here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Register_Activity.class);
                startActivity(intent);
            }
        });




        signup_btn=(Button) findViewById(R.id.signup_btn);

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               newslettersignup();
            }
        });

        user_email=(EditText)findViewById(R.id.user_email);
        user_password=(EditText)findViewById(R.id.user_password);




        //log in button



        signin=(Button)findViewById(R.id.signin);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(user_email.getText().toString().equals("") || user_password.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Email and Password cannot be empty!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    EmailValidator emailValidator = new EmailValidator();
                    if(emailValidator.validate(user_email.getText().toString()))
                    {
                        if(CheckNetwork.isInternetAvailable(getApplicationContext()))
                        {
                            data.put("email",user_email.getText().toString());
                            data.put("password",user_password.getText().toString());
                            loading = ProgressDialog.show(LoginActivity.this, "","Validating user...", true, false);
                            registerUser.register(data,route);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Please enter a valid email!",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        //for guest login
        email_guest=(EditText)findViewById(R.id.email_guest);
        guest_button=(Button)findViewById(R.id.guest_button);

        guest_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(email_guest.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Email cannot be empty!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    EmailValidator emailValidator=new EmailValidator();
                    if(emailValidator.validate(email_guest.getText().toString()))

                    {
                        if(CheckNetwork.isInternetAvailable(getApplicationContext()))

                        {
                            SharedPreferences prefs = getSharedPreferences("ADASAT", MODE_PRIVATE);
                            int language=prefs.getInt("language",0);
                            int country=prefs.getInt("country",0);
                            String languageString = "";
                            if(language==1)
                            {
                                languageString="English";
                            }
                            else {
                                languageString="Urdu";
                            }

                            data2.put("email",email_guest.getText().toString());
                            data2.put("country_id",String.valueOf(country));
                            data2.put("language",languageString);

                            loading = ProgressDialog.show(LoginActivity.this, "","Validating user...", true, false);
                         //  api.call(data2,route2);
                              registerUser.register(data2,route2);
                            guestApiCallin=true;



                        }

                        else
                        {
                            Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_LONG).show();
                        }

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Please enter a valid email!",Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            Intent i=new Intent(getApplicationContext(), Home_adslot.class);
            startActivity(i);

            // Handle the camera action
        } else if (id == R.id.nav_home) {
            Intent i=new Intent(getApplicationContext(), Home_adslot.class);
            startActivity(i);


        } else if (id == R.id.nav_search) {

        } else if (id == R.id.nav_shoppingcart) {

        } else if (id == R.id.nav_wishlist) {

        }else if (id == R.id.nav_selectlang) {

            Intent intent = new Intent(getApplicationContext(),Select_Language.class);
            startActivity(intent);

        }

        else if(id == R.id.nav_myaccount){

            Intent intent=new Intent(getApplicationContext(),My_Account.class);
            startActivity(intent);

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void processFinish(String output) {
      //  Log.i("kingsukmajumder","output of login api call is: "+output);
        loading.dismiss();

        if(guestApiCallin){

            //Toast.makeText(LoginActivity.this, ""+output, Toast.LENGTH_SHORT).show();
            try
            {
                JSONObject jsonObject = new JSONObject(output);
                if(jsonObject.getBoolean("status"))
                {
                    JSONObject response = new JSONObject(jsonObject.getString("response"));
                   //Toast.makeText(getApplicationContext(),""+response.getInt("id"),Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = getSharedPreferences("ADASAT", MODE_PRIVATE).edit();
                    editor.putInt("guest_id", response.getInt("id"));


                    if(editor.commit())
                    {
                        Intent intent = new Intent(LoginActivity.this,Home_adslot.class);
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
                Log.i("kingsukmajumder","error in login response "+e.toString());
            }



        }else{

            try
            {
                JSONObject jsonObject = new JSONObject(output);
                if(jsonObject.getBoolean("status"))
                {
                    JSONObject response = new JSONObject(jsonObject.getString("response"));
                    //Log.i("kingsukmajumder",""+response.getInt("id"));
                    SharedPreferences.Editor editor = getSharedPreferences("ADASAT", MODE_PRIVATE).edit();
                    editor.putInt("id", response.getInt("id"));
                    if(editor.commit())
                    {
                        Intent intent = new Intent(LoginActivity.this,Home_adslot.class);
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
                Log.i("kingsukmajumder","error in login response "+e.toString());
            }

        }

    }


    public void showForgetPassword()
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.popup_date, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.etCoupone);

        dialogBuilder.setTitle("Enter your Email:");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
                Toast.makeText(LoginActivity.this, "API for this is not done yet", Toast.LENGTH_SHORT).show();



            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }


    public void newslettersignup()
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.popup_date, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.etCoupone);

        dialogBuilder.setTitle("Enter your Email:");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();

                Toast.makeText(LoginActivity.this, "API for this is not done yet", Toast.LENGTH_SHORT).show();

            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

 /*   @Override
    public void processFinish2(String output) {
        Log.i("snigdho","output of login api call is: "+output);
        loading.dismiss();
        try {

            }
        catch (Exception e)
        {
            Log.i("kingsukmajumder","error in guest login response "+e.toString());
        }

    }*/
}
