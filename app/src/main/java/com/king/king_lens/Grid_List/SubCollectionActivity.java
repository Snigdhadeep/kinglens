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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.king.king_lens.Product.SubGridListActivity;
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

public class SubCollectionActivity extends AppCompatActivity implements AsyncResponse.Response, AsyncResponse2.Response2{

    //
    ImageView ivCollectionBanner;
    LinearLayout llParent;

    //server variables
    RegisterUser registerUser = new RegisterUser("POST");
    private String route = "api/v1/collectionbycategoryandbrand";
    HashMap<String,String> data = new HashMap<>();

    //object variables
    ArrayList<AsyncTask> imageLoadingThread= new ArrayList<AsyncTask>();


    //server variables
    RegisterUser2 registerUser2 = new RegisterUser2("POST");
    private String route2 = "api/v1/get-brand-image";
    HashMap<String,String> data2 = new HashMap<>();

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

        llParent=(LinearLayout)findViewById(R.id.llParent);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        registerUser.delegate = this;
        registerUser2.delegate = this;


            //getting data of intent
            brand_id = UserConstants.collection_Brand_id;
            category_id = UserConstants.collection_Category_id;



        //calling to get the collection for brand and category
        data.put("brand_id",brand_id);
        data.put("category_id",category_id);

        registerUser.register(data,route);
        loading = ProgressDialog.show(this, "", "Please wait...", true);

        data2.put("brand_id",brand_id);
        registerUser2.register(data2,route2);



        Toast.makeText(this, brand_id+" AND "+category_id, Toast.LENGTH_SHORT).show();

       // listView = (ListView)findViewById(R.id.mylistview);

        ivCollectionBanner = (ImageView) findViewById(R.id.ivCollectionBanner);




        //get list of product
      /*  listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
                Toast.makeText(SubCollectionActivity.this, imageView.getTag().toString(), Toast.LENGTH_SHORT).show();
                UserConstants.product_Collection_id = imageView.getTag().toString();
                Intent i=new Intent(getApplicationContext(),SubGridList_Activity.class);
                startActivity(i);

            }
        });*/

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
                    String id = String.valueOf(response.getInt("id"));
                    final String name = response.getString("name");
                    final String image = response.getString("image");
                    final String imageUrl = UserConstants.BASE_URL+UserConstants.IMAGE_FOLDER+image;
                  //  collectionProduct.add(new CollectionProduct(imageUrl, id, name));
                    View inflatedLayout= getLayoutInflater().inflate(R.layout.collection_listitem, null, false);
                    TextView txt_collection=(TextView)inflatedLayout.findViewById(R.id.txt_collection);
                    final ImageView imageView=(ImageView)inflatedLayout.findViewById(R.id.imageView);
                    txt_collection.setText(name);
                    imageView.setTag(id);

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

                                imageView.setImageBitmap(bmp);
                        }
                    }.execute();
                    imageLoadingThread.add(asyncTask);


                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            UserConstants.product_Collection_id = imageView.getTag().toString();
                            Intent i=new Intent(getApplicationContext(),SubGridList_Activity.class);
                            startActivity(i);
                        }
                    });

                    llParent.addView(inflatedLayout);

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

    @Override
    public void processFinish2(String output) {
        //Toast.makeText(this, output, Toast.LENGTH_SHORT).show();
        try {
            JSONObject jsonObject = new JSONObject(output);
            if(jsonObject.getBoolean("status"))
            {
                JSONObject response = new JSONObject(jsonObject.getString("response"));
                String image = response.getString("image");
                final String imageUrl = UserConstants.BASE_URL+UserConstants.IMAGE_FOLDER+image;

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
                            ivCollectionBanner.setImageBitmap(bmp);
                        }
                    }
                }.execute();
            }
            else
            {
                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            Log.i("subcollection",e.toString());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("kingsukmajumder","pause");
        for(int i=0;i<imageLoadingThread.size();i++)
        {
            imageLoadingThread.get(i).cancel(true);
        }
    }

}
