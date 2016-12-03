package com.king.king_lens;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import HelperClasses.AsyncResponse;
import HelperClasses.AsyncResponse2;
import HelperClasses.RegisterUser;
import HelperClasses.RegisterUser2;
import HelperClasses.UserConstants;

public class AddToCart extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AsyncResponse.Response, AsyncResponse2.Response2,View.OnClickListener {
    int user_id;
    LinearLayout llparentcart;

    TextView tvTotalPrducts;
    TextView tvTotalPrice;

    Button btnCheckout;

    //server variables
    RegisterUser registerUser = new RegisterUser("POST");
    private String route = "api/v1/get-cart-by-id";
    HashMap<String,String> data = new HashMap<>();

    //server variables
    RegisterUser2 registerUser2 = new RegisterUser2("POST");
    private String routeRemove = "api/v1/remove-cart-by-id";
    HashMap<String,String> data2 = new HashMap<>();

    ProgressDialog loading;
    ProgressDialog removeLoading;

    ArrayList<AsyncTask> imageLoadingThread= new ArrayList<AsyncTask>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);

        llparentcart=(LinearLayout)findViewById(R.id.llparentcart);

        tvTotalPrducts = (TextView) findViewById(R.id.tvTotalPrducts);
        tvTotalPrice = (TextView) findViewById(R.id.tvTotalPrice);

        btnCheckout = (Button) findViewById(R.id.btnCheckout);
        btnCheckout.setOnClickListener(this);

        registerUser.delegate=this;
        registerUser2.delegate=this;

        /*int i=10;
        while (i>0)
        {
            View inflatedLayout= getLayoutInflater().inflate(R.layout.cartitem, null, false);
            llparentcart.addView(inflatedLayout);
        }*/



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

        data.put("user_id",String.valueOf(user_id));
        registerUser.register(data,route);
        loading = ProgressDialog.show(this, "", "Please wait...", true);
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

    @Override
    public void processFinish(String output) {
        loading.dismiss();
        Log.i("kingsukmajumder",output);
        //Toast.makeText(this, output, Toast.LENGTH_SHORT).show();
        llparentcart.removeAllViews();
        try {
            JSONObject jsonObject = new JSONObject(output);
            if(jsonObject.getBoolean("status"))
            {
                JSONArray jsonArray = new JSONArray(jsonObject.getString("response"));

                tvTotalPrducts.setText(jsonArray.length()+" PRODUCTS");

                float totalPrice = (float) 0.00;
                for (int i=0;i<jsonArray.length();i++)
                {
                    JSONObject carts = jsonArray.getJSONObject(i);
                    JSONObject response = carts.getJSONObject("product");
                    //Log.i("kingsukmajumder",response.toString());
                    final int id = carts.getInt("id");
                    String name = response.getString("name");
                    String image = response.getString("image_one");
                    String sale_price = response.getString("sale_price");
                    totalPrice+=Integer.parseInt(sale_price);
                    String imageUrl = UserConstants.BASE_URL+UserConstants.IMAGE_FOLDER+image;

                    View inflatedLayout= getLayoutInflater().inflate(R.layout.cartitem, null, false);
                    TextView txtProductName = (TextView) inflatedLayout.findViewById(R.id.txtProductName);
                    TextView txtProductPrice = (TextView) inflatedLayout.findViewById(R.id.txtProductPrice);
                    ImageView productImageView = (ImageView) inflatedLayout.findViewById(R.id.productImageView);
                    LinearLayout remoteLL = (LinearLayout) inflatedLayout.findViewById(R.id.remoteLL);

                    remoteLL.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            data2.put("cart_id",String.valueOf(id));
                            //Toast.makeText(AddToCart.this, data2.toString(), Toast.LENGTH_SHORT).show();
                            registerUser2.register(data2,routeRemove);
                            //removeLoading = ProgressDialog.show(getApplicationContext(), "", "Removing product from cart...", true);
                            loading.show();
                        }
                    });

                    txtProductName.setText(name);
                    txtProductPrice.setText(sale_price+" KWD");
                    loadImage(imageUrl,productImageView);

                    llparentcart.addView(inflatedLayout);
                }

                tvTotalPrice.setText(String.format("%.2f", totalPrice)+" KWD");
            }
            else
            {
                Toast.makeText(this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                tvTotalPrice.setText("0.00 KWD");
                tvTotalPrducts.setText("0 PRODUCTS");
            }
        } catch (JSONException e) {
            Log.i("AddToCart",e.toString());
        }
    }

    public void loadImage(final String imageUrl,final ImageView theImageView)
    {
        AsyncTask asyncTask = new AsyncTask<Void, Void, Void>() {
            Bitmap bmp;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected Void doInBackground(Void... params) {

                try {
                    InputStream in = new URL(imageUrl).openStream();
                    bmp = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    //Toast.makeText(getContext(),"Some error occoured while loading images!",Toast.LENGTH_LONG).show();
                    Log.i("kingsukmajumder","error in loading images "+e.toString());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                //loading.dismiss();
                if (bmp != null)
                {
                    theImageView.setImageBitmap(bmp);
                }
            }
        }.execute();

        imageLoadingThread.add(asyncTask);

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("kingsukmajumder","pause");
        clearAllPendigAsync();
    }

    public void clearAllPendigAsync()
    {
        for(int i=0;i<imageLoadingThread.size();i++)
        {
            imageLoadingThread.get(i).cancel(true);
        }
    }

    @Override
    public void processFinish2(String output) {
        //Toast.makeText(this, output, Toast.LENGTH_SHORT).show();
        loading.dismiss();
        try
        {
            JSONObject jsonObject = new JSONObject(output);
            if(jsonObject.getBoolean("status"))
            {
                registerUser.register(data,route);
                loading.show();
            }
            else
            {
                Toast.makeText(this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {
            Log.i("Addtocart", e.toString());
        }
    }

    @Override
    public void onClick(View v) {
        if(tvTotalPrducts.getText().toString().equals("0 PRODUCTS"))
        {
            Toast.makeText(this, "Need to add at least one product", Toast.LENGTH_SHORT).show();
        }
        else
        {
            UserConstants.paymentAmount = tvTotalPrice.getText().toString();
            Intent i = new Intent(this,Payment_Activity.class);
            startActivity(i);
        }
    }
}
