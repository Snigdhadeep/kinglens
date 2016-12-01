package com.king.king_lens.Product;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.king.king_lens.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;

import HelperClasses.AsyncResponse;
import HelperClasses.RegisterUser;
import HelperClasses.UserConstants;

public class ProductView extends AppCompatActivity implements AsyncResponse.Response{

    ImageView frontImage;
    TextView productPrice;

    //Initializing textviews of datails in productview
    TextView product_properties;
    TextView lens_color_glasses;
    TextView accessory_color;
    TextView packaging;
    TextView replcing;
    TextView lens_material;
    TextView lens_segment;
    TextView brand_lens;
    TextView cylinder_options;
    TextView axis_options;




    //server variables
    RegisterUser registerUser = new RegisterUser("POST");
    private String route = "api/v1/get-product-by-id";
    HashMap<String,String> data = new HashMap<>();


    RadioGroup radioEyegroup;
    LinearLayout lnlenspower;

    //page variables
    String product_id = "";
    ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        product_id = UserConstants.product_Product_id;

        //initializing variables
        registerUser.delegate = this;

        data.put("product_id",product_id);
        loading = ProgressDialog.show(this, "", "Please wait...", true);
        registerUser.register(data,route);

        lnlenspower=(LinearLayout)findViewById(R.id.lnlenspower) ;
        final Spinner lefteye=(Spinner)findViewById(R.id.lefteye);
        final Spinner righteye=(Spinner)findViewById(R.id.righteye);
        final Spinner botheye=(Spinner)findViewById(R.id.botheye);

       // radioEyegroup = (RadioGroup) findViewById(R.id.radioEyegroup);

        frontImage = (ImageView) findViewById(R.id.frontImage);
        productPrice = (TextView) findViewById(R.id.productPrice);

       /* radioEyegroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch(i){
                    case R.id.rbRighteye:
                        // do operations specific to this selection
                        lnlenspower.setVisibility(View.VISIBLE);
                        lefteye.setVisibility(View.VISIBLE);
                        righteye.setVisibility(View.VISIBLE);
                        botheye.setVisibility(View.INVISIBLE);


                        break;
                    case R.id.rbLefteye:
                        // do operations specific to this selection
                        lnlenspower.setVisibility(View.VISIBLE);
                        lefteye.setVisibility(View.VISIBLE);
                        righteye.setVisibility(View.VISIBLE);
                        botheye.setVisibility(View.INVISIBLE);


                        break;
                    case R.id.rbBotheye:
                        // do operations specific to this selection

                        lnlenspower.setVisibility(View.VISIBLE);
                        lefteye.setVisibility(View.INVISIBLE);
                        righteye.setVisibility(View.INVISIBLE);
                        botheye.setVisibility(View.VISIBLE);

                        break;
                }

            }
        });
*/

        product_properties = (TextView) findViewById(R.id.product_properties);
        lens_color_glasses = (TextView) findViewById(R.id.lens_color_glasses);
        accessory_color = (TextView) findViewById(R.id.accessory_color);
        packaging = (TextView) findViewById(R.id.packaging);
        replcing = (TextView) findViewById(R.id.replcing);
        lens_material = (TextView) findViewById(R.id.lens_material);
        lens_segment = (TextView) findViewById(R.id.lens_segment);
        brand_lens = (TextView) findViewById(R.id.brand_lens);
        cylinder_options = (TextView) findViewById(R.id.cylinder_options);
        axis_options = (TextView) findViewById(R.id.axis_options);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.product_menu, menu);
        return true;
    }//optionsmenu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.product_menu_heart) {

        }
        else if(id == R.id.product_menu_search) {

        }



        return super.onOptionsItemSelected(item);
    }//optionsitemSelected

    @Override
    public void processFinish(String output) {
        loading.dismiss();
        //Toast.makeText(this, output, Toast.LENGTH_SHORT).show();
        Log.i("kingsukmajumder",output);
        try {
            JSONObject jsonObject = new JSONObject(output);
            if(jsonObject.getBoolean("status"))
            {
                JSONObject response = new JSONObject(jsonObject.getString("response"));
                String id = String.valueOf(response.getInt("id"));
                String image = response.getString("image_one");
                final String imageUrl = UserConstants.BASE_URL+UserConstants.IMAGE_FOLDER+image;
                String sale_price = response.getString("sale_price");


                if(!response.isNull("product_details"))
                {
                    JSONObject product_details = new JSONObject(response.getString("product_details"));
                    product_properties.setText(product_details.getString("product_properties"));
                    lens_color_glasses.setText(product_details.getString("lens_color_glasses"));
                    accessory_color.setText(product_details.getString("accessory_color"));
                    packaging.setText(product_details.getString("packaging"));
                    replcing.setText(product_details.getString("replcing"));
                    lens_material.setText(product_details.getString("lens_material"));
                    lens_segment.setText(product_details.getString("lens_segment"));
                    brand_lens.setText(product_details.getString("brand_lens"));
                    cylinder_options.setText(product_details.getString("cylinder_options"));
                    axis_options.setText(product_details.getString("axis_options"));
                }
                else
                {
                    product_properties.setText("");
                    lens_color_glasses.setText("");
                    accessory_color.setText("");
                    packaging.setText("");
                    replcing.setText("");
                    lens_material.setText("");
                    lens_segment.setText("");
                    brand_lens.setText("");
                    cylinder_options.setText("");
                    axis_options.setText("");
                }


                productPrice.setText(sale_price+"KWD");

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
                            frontImage.setImageBitmap(bmp);
                        }


                    }
                }.execute();
            }
            else
            {
                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            Log.i("productview",e.toString());
        }
    }
}
