package com.king.king_lens.Grid_List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.king.king_lens.Product.ProductView;
import com.king.king_lens.R;
import com.king.king_lens.Select_Language;
import com.king.king_lens.WishList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import HelperClasses.AsyncResponse;
import HelperClasses.RegisterUser;
import HelperClasses.UserConstants;

public class SubGridList_Activity extends AppCompatActivity implements AsyncResponse.Response{



    private ViewStub stubGrid;
    private ViewStub stubList;
    private ListView listView;
    private GridView gridView;
    private GridView lvgridView;
    private ListViewAdapter listViewAdapter;
    private GridViewAdapter gridViewAdapter;
    private List<Product> productList = new ArrayList<>();;
    private int currentViewMode = 0;
    private int selected = 2;

    static final int VIEW_MODE_LISTVIEW = 0;
    static final int VIEW_MODE_GRIDVIEW = 1;

    static final int VIEW_MODE_SELECTED= 2;
    static final int VIEW_MODE_REMOVED= 3;


    ImageButton gridlistbtn;
    ImageButton filterbtn;
    ImageButton  firebtn;

    ImageView ivwishicon;
    LinearLayout parentProductLL;



    //server variables
    RegisterUser registerUser = new RegisterUser("POST");
    private String route = "api/v1/productbycategorybrandandcollection";
    HashMap<String,String> data = new HashMap<>();

    //page variables
    String brand_id="";
    String category_id="";
    String subcategory_id = "";

    ProgressDialog loading;

    boolean GridViewOn = false;


    String totalOutput="";

    ArrayList<AsyncTask> imageLoadingThread= new ArrayList<AsyncTask>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_fragmentgridlist_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        parentProductLL = (LinearLayout) findViewById(R.id.parentProductLL);
        //paddingView= getLayoutInflater().inflate(R.layout.paddingview, null, false);
        //parentProductLL.addView(paddingView);

        registerUser.delegate = this;

        brand_id = UserConstants.collection_Brand_id;
        category_id = UserConstants.collection_Category_id;
        subcategory_id = UserConstants.product_Collection_id;

        //calling to get the collection for brand and category
        data.put("brand_id",brand_id);
        data.put("category_id",category_id);
        data.put("subcategory_id",subcategory_id);

        registerUser.register(data,route);
        loading = ProgressDialog.show(this, "", "Please wait...", true);




        /*stubList = (ViewStub)findViewById(R.id.stub_list);
        stubGrid = (ViewStub)findViewById(R.id.stub_grid);

        //Inflate ViewStub before get view

        stubList.inflate();
        stubGrid.inflate();

        listView = (ListView) findViewById(R.id.mylistview);
        gridView = (GridView)findViewById(R.id.mygridview);




        //Get current view mode in share reference
        SharedPreferences sharedPreferences =getApplicationContext().getSharedPreferences("ViewMode", 0);
        currentViewMode = sharedPreferences.getInt("currentViewMode", VIEW_MODE_LISTVIEW);//Default is view listview
        selected = sharedPreferences.getInt("selected", VIEW_MODE_SELECTED);
        //Register item lick
        listView.setOnItemClickListener(onItemClick);
        gridView.setOnItemClickListener(onItemClick);


*/


        gridlistbtn=(ImageButton)findViewById(R.id.listicon);
        filterbtn=(ImageButton)findViewById(R.id.filter);
        firebtn=(ImageButton)findViewById(R.id.fire);


        //onclick listbtn

        gridlistbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(SubGridList_Activity.this, "here" +GridViewOn, Toast.LENGTH_SHORT).show();

                if(GridViewOn)
                {
                    setUpListView(totalOutput);
                    gridlistbtn.setImageResource(R.drawable.gridlist_list2);


                }
                else
                {
                    setUpGridView(totalOutput);
                    gridlistbtn.setImageResource(R.drawable.gridlist_grid2);
                }
            }

        });


      /*  ivwishicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (VIEW_MODE_SELECTED == selected) {
                    ivwishicon.setImageResource(R.drawable.fav_2);
                    selected = VIEW_MODE_REMOVED;
                } else {
                    currentViewMode = VIEW_MODE_REMOVED;
                    ivwishicon.setImageResource(R.drawable.fav_1);
                }


                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("ViewMode", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("selected", selected);
                editor.commit();
            }
        });
*/




    }




    /*AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //Do any thing when user click to item
           // Toast.makeText(getApplicationContext(), productList.get(position).getTitle() + " - " + productList.get(position).getDescription(), Toast.LENGTH_SHORT).show();
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            final ImageView ivwishicon=(ImageView)view.findViewById(R.id.ivwishicon);



            String product_id = imageView.getTag().toString();

            UserConstants.product_Product_id = product_id;

            Intent intent=new Intent(getApplicationContext(),ProductView.class);
            startActivity(intent);



            ivwishicon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (VIEW_MODE_SELECTED == selected) {
                        ivwishicon.setImageResource(R.drawable.fav_2);
                        selected = VIEW_MODE_REMOVED;
                    } else {
                        selected = VIEW_MODE_REMOVED;
                        ivwishicon.setImageResource(R.drawable.fav_1);
                    }


                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("ViewMode", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("selected", selected);
                    editor.commit();
                }
            });

        }
    };*/


    /*private void switchView() {

        if(VIEW_MODE_LISTVIEW == currentViewMode) {
            //Display listview
            stubList.setVisibility(View.VISIBLE);
            //Hide gridview
            stubGrid.setVisibility(View.GONE);
        } else {
            //Hide listview
            stubList.setVisibility(View.GONE);
            //Display gridview
            stubGrid.setVisibility(View.VISIBLE);
        }
        setAdapters();
    }*/


    /*private void setAdapters() {
        if(VIEW_MODE_LISTVIEW == currentViewMode) {
         listViewAdapter = new ListViewAdapter(getApplicationContext(), R.layout.list_item, productList);
            listView.setAdapter(listViewAdapter);
            *//*gridViewAdapter = new GridViewAdapter(getApplicationContext(), R.layout.list_item, productList);
            listView.setAdapter(gridViewAdapter);*//*
        } else {
            gridViewAdapter = new GridViewAdapter(getApplicationContext(), R.layout.grid_item, productList);
            gridView.setAdapter(gridViewAdapter);
        }
    }*/


    @Override
    public void processFinish(String output) {
        loading.dismiss();
        //Toast.makeText(this, output, Toast.LENGTH_SHORT).show();
        Log.i("kingsukmajumder",output);

        totalOutput = output;

        setUpGridView(output);

        //getProductList();
        //switchView();
    }

    public void setUpListView(String output)
    {
        parentProductLL.removeAllViews();
        GridViewOn=false;
        try {
            JSONObject jsonObject = new JSONObject(output);
            if(jsonObject.getBoolean("status"))
            {
                JSONArray jsonArray = new JSONArray(jsonObject.getString("response"));
                for (int i=0;i<jsonArray.length();i++)
                {
                    if(i==0)
                    {
                        View inflatedLayout= getLayoutInflater().inflate(R.layout.paddingview, null, false);
                        parentProductLL.addView(inflatedLayout);
                    }
                    JSONObject response = new JSONObject(jsonArray.get(i).toString());
                    final String id = String.valueOf(response.getInt("id"));
                    String name = response.getString("name");
                    String image = response.getString("image_one");
                    final String imageUrl = UserConstants.BASE_URL+UserConstants.IMAGE_FOLDER+image;
                    //productList.add(new Product(id,name,imageUrl, "FRESHLOOK COLORBLENDS"));
                    View inflatedLayout= getLayoutInflater().inflate(R.layout.list_item, null, false);
                    ImageView imageView = (ImageView) inflatedLayout.findViewById(R.id.imageView);
                    loadImage(imageUrl,imageView);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Toast.makeText(SubGridList_Activity.this, "clicked", Toast.LENGTH_SHORT).show();


                            UserConstants.product_Product_id = id;

                            Intent intent=new Intent(getApplicationContext(),ProductView.class);
                            startActivity(intent);
                        }
                    });



                    parentProductLL.addView(inflatedLayout);
                }
            }
            else
            {
                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            Log.i("subgridlist",e.toString());
        }
    }

    public void setUpGridView(String output)
    {
        parentProductLL.removeAllViews();
        GridViewOn=true;
        try {
            JSONObject jsonObject = new JSONObject(output);
            if(jsonObject.getBoolean("status"))
            {
                JSONArray jsonArray = new JSONArray(jsonObject.getString("response"));
                for (int i=0;i<jsonArray.length();i=i+2)
                {
                    if(i==0)
                    {
                        View inflatedLayout= getLayoutInflater().inflate(R.layout.paddingview, null, false);
                        parentProductLL.addView(inflatedLayout);
                    }
                    try {
                        JSONObject response1 = new JSONObject(jsonArray.get(i).toString());
                        final String id1 = String.valueOf(response1.getInt("id"));
                        String name1 = response1.getString("name");
                        String image1 = response1.getString("image_one");
                        final String imageUrl1 = UserConstants.BASE_URL+UserConstants.IMAGE_FOLDER+image1;


                        JSONObject response2 = new JSONObject(jsonArray.get(i+1).toString());
                        final String id2 = String.valueOf(response2.getInt("id"));
                        String name2 = response2.getString("name");
                        String image2 = response2.getString("image_one");
                        final String imageUrl2 = UserConstants.BASE_URL+UserConstants.IMAGE_FOLDER+image2;
                        //productList.add(new Product(id,name,imageUrl, "FRESHLOOK COLORBLENDS"));


                        View inflatedLayout= getLayoutInflater().inflate(R.layout.grid_item_2, null, false);
                        ImageView imageViewGrid1 = (ImageView) inflatedLayout.findViewById(R.id.imageViewGrid1);
                        imageViewGrid1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                UserConstants.product_Product_id = id1;

                                Intent intent=new Intent(getApplicationContext(),ProductView.class);
                                startActivity(intent);
                            }
                        });
                        ImageView imageViewGrid2 = (ImageView) inflatedLayout.findViewById(R.id.imageViewGrid2);
                        imageViewGrid2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                UserConstants.product_Product_id = id2;

                                Intent intent=new Intent(getApplicationContext(),ProductView.class);
                                startActivity(intent);
                            }
                        });
                        loadImage(imageUrl1,imageViewGrid1);
                        loadImage(imageUrl2,imageViewGrid2);


                        parentProductLL.addView(inflatedLayout);
                    }
                    catch (Exception e)
                    {
                        //Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                        JSONObject response1 = new JSONObject(jsonArray.get(i).toString());
                        final String id1 = String.valueOf(response1.getInt("id"));
                        String name1 = response1.getString("name");
                        String image1 = response1.getString("image_one");
                        final String imageUrl1 = UserConstants.BASE_URL+UserConstants.IMAGE_FOLDER+image1;

                        View inflatedLayout= getLayoutInflater().inflate(R.layout.grid_item_2, null, false);
                        ImageView imageViewGrid1 = (ImageView) inflatedLayout.findViewById(R.id.imageViewGrid1);
                        imageViewGrid1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                UserConstants.product_Product_id = id1;

                                Intent intent=new Intent(getApplicationContext(),ProductView.class);
                                startActivity(intent);
                            }
                        });
                        LinearLayout llsecondLL = (LinearLayout) inflatedLayout.findViewById(R.id.llsecondLL);
                        llsecondLL.setVisibility(View.INVISIBLE);

                        loadImage(imageUrl1,imageViewGrid1);



                        parentProductLL.addView(inflatedLayout);
                    }

                }
            }
            else
            {
                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            Log.i("subgridlist",e.toString());
            //Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();

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
        for(int i=0;i<imageLoadingThread.size();i++)
        {
            imageLoadingThread.get(i).cancel(true);
        }
    }
}
