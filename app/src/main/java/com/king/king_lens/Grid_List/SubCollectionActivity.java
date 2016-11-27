package com.king.king_lens.Grid_List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.king.king_lens.Product.SubGridListActivity;
import com.king.king_lens.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import HelperClasses.AsyncResponse;
import HelperClasses.RegisterUser;
import HelperClasses.UserConstants;

public class SubCollectionActivity extends AppCompatActivity implements AsyncResponse.Response{


    //server variables
    RegisterUser registerUser = new RegisterUser("POST");
    private String route = "api/v1/collectionbycategoryandbrand";
    HashMap<String,String> data = new HashMap<>();

    //loading variables
    ProgressDialog loading;

    //page variables
    String brand_id="";
    String category_id="";


    private ListView listView;
    private CollectionListAdapter collectionListAdapter;
    private List<CollectionProduct> collectionProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_collection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        registerUser.delegate = this;


            //getting data of intent
            brand_id = UserConstants.collection_Brand_id;
            category_id = UserConstants.collection_Category_id;



        //calling to get the collection for brand and category
        data.put("brand_id",brand_id);
        data.put("category_id",category_id);

        registerUser.register(data,route);
        loading = ProgressDialog.show(this, "", "Please wait...", true);



        Toast.makeText(this, brand_id+" AND "+category_id, Toast.LENGTH_SHORT).show();

        listView = (ListView)findViewById(R.id.mylistview);




        //get list of product
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent i=new Intent(getApplicationContext(),SubGridList_Activity.class);
                startActivity(i);

            }
        });

        collectionProduct = new ArrayList<>();


    }

    public void settingUpListAdapter()
    {
        //getProductList();
        collectionListAdapter = new CollectionListAdapter(getApplicationContext(), R.layout.collection_listitem, this.collectionProduct);
        listView.setAdapter(collectionListAdapter);
    }



    @Override
    public void processFinish(String output) {
        loading.dismiss();
        //Toast.makeText(this, output, Toast.LENGTH_SHORT).show();
        try
        {
            JSONObject jsonObject = new JSONObject(output);
            if(jsonObject.getBoolean("status"))
            {
                JSONArray jsonArray = new JSONArray(jsonObject.getString("response"));

                for (int i=0;i<jsonArray.length();i++)
                {
                    JSONObject response = new JSONObject(jsonArray.get(i).toString());
                    final String name = response.getString("name");
                    String image = response.getString("image");
                    final String imageUrl = UserConstants.BASE_URL+UserConstants.IMAGE_FOLDER+image;
                    collectionProduct.add(new CollectionProduct(imageUrl, name));



                }

                settingUpListAdapter();
            }
            else
            {
                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {
            Log.i("subcollection",e.toString());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("brand_id", brand_id);
        outState.putString("category_id",category_id);

    }
}
