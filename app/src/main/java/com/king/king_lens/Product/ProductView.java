package com.king.king_lens.Product;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.king.king_lens.AddToCart;
import com.king.king_lens.LoginActivity;
import com.king.king_lens.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import HelperClasses.AsyncResponse;
import HelperClasses.AsyncResponse2;
import HelperClasses.RegisterUser;
import HelperClasses.RegisterUser2;
import HelperClasses.UserConstants;

public class ProductView extends AppCompatActivity implements AsyncResponse.Response,View.OnClickListener, AsyncResponse2.Response2{

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


    TextView tvProductName;
    LinearLayout llAddtoCart;

    ImageView imgPre1;
    ImageView imgPre2;
    ImageView imgPre3;

    ImageView arrowRight;
    ImageView arrowLeft;


    //server variables
    RegisterUser registerUser = new RegisterUser("POST");
    private String route = "api/v1/get-product-by-id";
    HashMap<String,String> data = new HashMap<>();

    RegisterUser2 registerUser2 = new RegisterUser2("POST");
    private String routeAddToCart = "api/v1/post-cart-by-id";
    HashMap<String,String> data2 = new HashMap<>();


    RadioGroup radioEyegroup;
    LinearLayout lnlenspower;

    //page variables
    String product_id = "";
    ProgressDialog loading;

    ProgressDialog loadingForCart;

    List<String> spinnerArray =  new ArrayList<String>();
    List<Integer> spinnerValue = new ArrayList<Integer>();

    Spinner lefteye;
    Spinner righteye;
    Spinner botheye;

    String leftEyeValue;
    String rightEyeValue;
    String bothEyeValue;

    CheckBox checkleft;
    CheckBox checkboth;
    CheckBox checkright;


    JSONArray powerQty = new JSONArray();
    //JSONObject bothEye= new JSONObject();

    //EditText leftQty;
    //EditText rightQty;
    //EditText bothQty;

    //Bitmap[] allBitmaps;
    int imagePosition = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //final Drawable upArrow = getResources().getDrawable();
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_cross_sign);


        product_id = UserConstants.product_Product_id;

        //initializing variables
        registerUser.delegate = this;
        registerUser2.delegate = this;

        data.put("product_id",product_id);
        loading = ProgressDialog.show(this, "", "Please wait...", true);
        registerUser.register(data,route);

        lnlenspower=(LinearLayout)findViewById(R.id.lnlenspower) ;
        lefteye=(Spinner)findViewById(R.id.lefteye);
        righteye=(Spinner)findViewById(R.id.righteye);
        botheye=(Spinner)findViewById(R.id.botheye);

        checkleft = (CheckBox) findViewById(R.id.checkleft);
        checkright = (CheckBox) findViewById(R.id.checkright);
        checkboth = (CheckBox) findViewById(R.id.checkboth);

        // radioEyegroup = (RadioGroup) findViewById(R.id.radioEyegroup);

        frontImage = (ImageView) findViewById(R.id.frontImage);
        productPrice = (TextView) findViewById(R.id.productPrice);

        //leftQty = (EditText) findViewById(R.id.textView6);
        //rightQty = (EditText) findViewById(R.id.textView622);
        //bothQty = (EditText) findViewById(R.id.textView5);


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

        arrowRight = (ImageView) findViewById(R.id.arrowRight);
        arrowLeft = (ImageView) findViewById(R.id.arrowLeft);

        imgPre1 = (ImageView) findViewById(R.id.imgPre1);
        imgPre2 = (ImageView) findViewById(R.id.imgPre2);
        imgPre3 = (ImageView) findViewById(R.id.imgPre3);

        tvProductName = (TextView) findViewById(R.id.tvProductName);
        llAddtoCart = (LinearLayout) findViewById(R.id.llAddtoCart);

        llAddtoCart.setOnClickListener(this);

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
        else if(id == R.id.product_menu_cart) {
            Intent i=new Intent(getApplicationContext(),AddToCart.class);
            startActivity(i);

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
                String image2 = response.getString("image_two");
                String image3 = response.getString("image_three");

                final String imageUrl = UserConstants.BASE_URL+UserConstants.IMAGE_FOLDER+image;
                final String imageUrl2 = UserConstants.BASE_URL+UserConstants.IMAGE_FOLDER+image2;
                final String imageUrl3 = UserConstants.BASE_URL+UserConstants.IMAGE_FOLDER+image3;

                String sale_price = response.getString("sale_price");
                String name = response.getString("name");
                getSupportActionBar().setTitle(name);
                tvProductName.setText(name);

                String power_ranges = response.getString("power_ranges");
                JSONArray power_array = new JSONArray(power_ranges);


                spinnerArray.add("Select");
                if(power_array.length()>0)
                {
                    for (int j = 0; j<power_array.length();j++)
                    {
                        JSONObject currentObject = power_array.getJSONObject(j);
                        String power = currentObject.getString("power");
                        //Toast.makeText(this, power, Toast.LENGTH_SHORT).show();
                        spinnerArray.add(power);
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getApplicationContext(), R.layout.simple_spinner_item_custm, spinnerArray);

                    lefteye.setAdapter(adapter);
                    righteye.setAdapter(adapter);
                    botheye.setAdapter(adapter);

                    lefteye.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            leftEyeValue = spinnerArray.get(position);
                            //Toast.makeText(ProductView.this, leftEyeValue, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            leftEyeValue = "";
                        }
                    });

                    righteye.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            rightEyeValue = spinnerArray.get(position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            rightEyeValue = "";
                        }
                    });

                    botheye.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            bothEyeValue = spinnerArray.get(position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            bothEyeValue = "";
                        }
                    });

                }



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
                    Bitmap bmp2;
                    Bitmap bmp3;
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                    }
                    @Override
                    protected Void doInBackground(Void... params) {

                        try {
                            InputStream in = new URL(imageUrl).openStream();
                            bmp = BitmapFactory.decodeStream(in);

                            InputStream in2 = new URL(imageUrl2).openStream();
                            bmp2 = BitmapFactory.decodeStream(in2);

                            InputStream in3 = new URL(imageUrl3).openStream();
                            bmp3 = BitmapFactory.decodeStream(in3);
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
                            imgPre1.setImageBitmap(bmp);
                            imgPre2.setImageBitmap(bmp2);
                            imgPre3.setImageBitmap(bmp3);

                            Bitmap[] allBitmaps = new Bitmap[]{bmp,bmp2,bmp3};

                            setUpAllImageViews(allBitmaps);
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
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void setUpAllImageViews(final Bitmap[] allBitmaps)
    {
        imgPre1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap previewImage = allBitmaps[0];
                frontImage.setImageBitmap(previewImage);
                imagePosition=1;
            }
        });

        imgPre2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bitmap previewImage = allBitmaps[1];
                frontImage.setImageBitmap(previewImage);
                imagePosition=2;
            }
        });

        imgPre3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bitmap previewImage = allBitmaps[2];
                frontImage.setImageBitmap(previewImage);
                imagePosition=3;
            }
        });

        arrowRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imagePosition==2)
                {
                    Bitmap previewImage = allBitmaps[2];
                    frontImage.setImageBitmap(previewImage);

                    imagePosition=3;
                }
                else if(imagePosition==1)
                {
                    Bitmap previewImage = allBitmaps[1];
                    frontImage.setImageBitmap(previewImage);
                    imagePosition=2;
                }
            }
        });

        arrowLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imagePosition==3)
                {
                    Bitmap previewImage = allBitmaps[1];
                    frontImage.setImageBitmap(previewImage);

                    imagePosition=2;
                }
                else if(imagePosition==2)
                {
                    Bitmap previewImage = allBitmaps[0];
                    frontImage.setImageBitmap(previewImage);
                    imagePosition=1;
                }
            }
        });

        /*arrowLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProductView.this, "clicked", Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {

        for (int l=0;l<powerQty.length();l++)
        {
            powerQty.remove(l);
        }


        //Toast.makeText(this, powerQty.toString(), Toast.LENGTH_LONG).show();
        Log.i("kingsuk",powerQty.toString());

        SharedPreferences prefs = getSharedPreferences("ADASAT", MODE_PRIVATE);
        int user_id = prefs.getInt("id",0);
        int gueat_id = prefs.getInt("guest_id",0);

        if(user_id==0 && gueat_id==0)
        {
            Toast.makeText(this, "Please Login To Continue", Toast.LENGTH_SHORT).show();
            UserConstants.returnToProductView = true;
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
        }
        else
        {
            if(checkboth.isChecked() || checkleft.isChecked() || checkright.isChecked())
            {
                if(checkboth.isChecked() && (checkleft.isChecked() || checkright.isChecked()))
                {

                    Toast.makeText(this, "Left or Right and Both cannot be selected at the same time!", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    if((checkboth.isChecked()&&!bothEyeValue.equals("Select")) || (checkleft.isChecked()&&!leftEyeValue.equals("Select")) || (checkright.isChecked()&&!rightEyeValue.equals("Select")))
                    {
                        data2.put("user_id",String.valueOf(user_id));
                        data2.put("product_id",product_id);

                        addPowerOfProduct();
                        data2.put("power_json",powerQty.toString());
                        //Toast.makeText(this, data2.toString(), Toast.LENGTH_SHORT).show();
                        loadingForCart = ProgressDialog.show(this, "", "Adding product to cart...", true);
                        registerUser2.register(data2,routeAddToCart);
                    }
                    else
                    {
                        Toast.makeText(this, "Please Select a proper value", Toast.LENGTH_SHORT).show();
                    }

                }

            }
            else
            {
                Toast.makeText(this, "Atleast one eye needs to be selected", Toast.LENGTH_SHORT).show();
            }


        }
    }

    public void addPowerOfProduct()
    {
        if(checkboth.isChecked())
        {
            JSONObject qtyPower = new JSONObject();
            try {
                qtyPower.put("name","both");
                qtyPower.put("value",bothEyeValue);
                qtyPower.put("quantity","1");
                JSONObject bothEye= new JSONObject();
                bothEye.put("bothEye",qtyPower);

                powerQty.put(0,qtyPower);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(checkleft.isChecked())
        {
            JSONObject qtyPower = new JSONObject();
            try {
                qtyPower.put("name","left");
                qtyPower.put("value",leftEyeValue);
                qtyPower.put("quantity","1");
                JSONObject bothEye= new JSONObject();
                bothEye.put("leftEye",qtyPower);

                powerQty.put(1,qtyPower);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        if(checkright.isChecked())
        {
            JSONObject qtyPower = new JSONObject();
            try {
                qtyPower.put("name","right");
                qtyPower.put("value",rightEyeValue);
                qtyPower.put("quantity","1");
                JSONObject bothEye= new JSONObject();
                bothEye.put("rightEye",qtyPower);

                powerQty.put(2,qtyPower);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void processFinish2(String output) {
        loadingForCart.dismiss();
        //Toast.makeText(this, output, Toast.LENGTH_SHORT).show();
        try
        {
            JSONObject jsonObject = new JSONObject(output);
            if(jsonObject.getBoolean("status"))
            {
                Intent intent = new Intent(this, AddToCart.class);
                startActivity(intent);
            }
            else
            {
                Toast.makeText(this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {
            Log.i("ProductView",e.toString());
        }
    }
}
