package com.king.king_lens;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import java.util.HashMap;

import HelperClasses.EmailValidator;
import HelperClasses.RegisterUser;

public class LoginActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button signin;
    Button signup_btn;
    EditText user_email;
    EditText user_password;

    //server variables
    RegisterUser registerUser = new RegisterUser("POST");
    private String route = "api/v1/user/auth";
    HashMap<String,String> data = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        signup_btn=(Button) findViewById(R.id.signup_btn);

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),Register_Activity.class);
                startActivity(i);
            }
        });

        user_email=(EditText)findViewById(R.id.user_email);
        user_password=(EditText)findViewById(R.id.user_password);

        //log in button



        signin=(Button)findViewById(R.id.signin);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(user_email.getText().equals("") || user_password.getText().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Email and Password cannot be empty!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    EmailValidator emailValidator = new EmailValidator();
                    if(emailValidator.validate(user_email.getText().toString()))
                    {

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

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
