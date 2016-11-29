package com.king.king_lens;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.LinearLayout;

public class AddToCart extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
int user_id;
    LinearLayout llparentcart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);

        llparentcart=(LinearLayout)findViewById(R.id.llparentcart);

        int i=10;
        while (i>0){

            View inflatedLayout= getLayoutInflater().inflate(R.layout.cartitem, null, false);
            llparentcart.addView(inflatedLayout);
            i=i-1;

        }



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences prefs = getSharedPreferences("ADASAT", MODE_PRIVATE);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        user_id = prefs.getInt("id",0);
        //Toast.makeText(getApplicationContext(),""+user_id,Toast.LENGTH_SHORT).show();

        //hiding login logout programatically
        if(user_id!=0)
        {
            //Toast.makeText(getApplicationContext(),"use id"+user_id,Toast.LENGTH_SHORT).show();
            navigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);


        }
        else
        {
            navigationView.getMenu().findItem(R.id.nav_myaccount).setVisible(false);
        }


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
        getMenuInflater().inflate(R.menu.add_to_cart, menu);
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

        if (id == R.id.nav_login) {
            // Handle the camera action
            Intent i=new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(i);
        }
        else if(id == R.id.nav_logout)
        {
            SharedPreferences.Editor editor = getSharedPreferences("ADASAT", MODE_PRIVATE).edit();
            editor.putInt("id", 0);
            if(editor.commit())
            {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        }
        else if (id == R.id.nav_home) {
            Intent intent=new Intent(getApplicationContext(),Home_adslot.class);
            startActivity(intent);
        }

        else if (id == R.id.nav_lenscare) {

        } else if (id == R.id.nav_myaccount) {


            Intent intent=new Intent(getApplicationContext(),My_Account.class);
            startActivity(intent);

        } else if (id == R.id.nav_selectlang) {

            Intent intent = new Intent(getApplicationContext(),Select_Language.class);
            startActivity(intent);

        }else if (id == R.id.nav_search) {

        } else if (id == R.id.nav_shoppingcart) {

        } else if (id == R.id.nav_wishlist) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
