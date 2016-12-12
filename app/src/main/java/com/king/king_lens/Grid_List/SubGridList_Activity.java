package com.king.king_lens.Grid_List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.king.king_lens.AddToCart;
import com.king.king_lens.LoginActivity;
import com.king.king_lens.My_Account;
import com.king.king_lens.Product.ProductView;
import com.king.king_lens.R;
import com.king.king_lens.Select_Language;

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

public class SubGridList_Activity extends AppCompatActivity implements AsyncResponse.Response,NavigationView.OnNavigationItemSelectedListener{



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
    int user_id;




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
        setContentView(R.layout.activity_subgridlist);
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



        // setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,drawer,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


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

        SharedPreferences prefs = getSharedPreferences("ADASAT", MODE_PRIVATE);

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
        clearAllPendigAsync();

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
                    String description = response.getString("description");
                    String sale_price = response.getString("sale_price");
                    final String imageUrl = UserConstants.BASE_URL+UserConstants.IMAGE_FOLDER+image;
                    //productList.add(new Product(id,name,imageUrl, "FRESHLOOK COLORBLENDS"));
                    View inflatedLayout= getLayoutInflater().inflate(R.layout.list_item, null, false);
                    ImageView imageView = (ImageView) inflatedLayout.findViewById(R.id.imageView);
                    TextView txtDescription = (TextView) inflatedLayout.findViewById(R.id.txtDescription);
                    TextView txtTitle = (TextView) inflatedLayout.findViewById(R.id.txtTitle);
                    TextView tvPrice = (TextView) inflatedLayout.findViewById(R.id.tvPrice);
                    tvPrice.setText(sale_price+" KWD");
                    txtTitle.setText(name);
                    txtDescription.setText(description);


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
        clearAllPendigAsync();

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
                        String description1 = response1.getString("description");
                        String sale_price1 = response1.getString("sale_price");
                        final String imageUrl1 = UserConstants.BASE_URL+UserConstants.IMAGE_FOLDER+image1;


                        JSONObject response2 = new JSONObject(jsonArray.get(i+1).toString());
                        final String id2 = String.valueOf(response2.getInt("id"));
                        String name2 = response2.getString("name");
                        String image2 = response2.getString("image_one");
                        String description2 = response2.getString("description");
                        String sale_price2 = response2.getString("sale_price");
                        final String imageUrl2 = UserConstants.BASE_URL+UserConstants.IMAGE_FOLDER+image2;
                        //productList.add(new Product(id,name,imageUrl, "FRESHLOOK COLORBLENDS"));


                        View inflatedLayout= getLayoutInflater().inflate(R.layout.grid_item_2, null, false);
                        ImageView imageViewGrid1 = (ImageView) inflatedLayout.findViewById(R.id.imageViewGrid1);
                        TextView txtTitleGrid1 = (TextView) inflatedLayout.findViewById(R.id.txtTitleGrid1);
                        TextView txtDescriptionGrid1 = (TextView) inflatedLayout.findViewById(R.id.txtDescriptionGrid1);
                        TextView tvPriceGrid1 = (TextView) inflatedLayout.findViewById(R.id.tvPriceGrid1);
                        txtDescriptionGrid1.setText(description1);
                        tvPriceGrid1.setText(sale_price1+" KWD");
                        txtTitleGrid1.setText(name1);

                        TextView txtTitleGrid2 = (TextView) inflatedLayout.findViewById(R.id.txtTitleGrid2);
                        TextView txtDescriptionGrid2 = (TextView) inflatedLayout.findViewById(R.id.txtDescriptionGrid2);
                        TextView tvPriceGrid2 = (TextView) inflatedLayout.findViewById(R.id.tvPriceGrid2);
                        txtDescriptionGrid2.setText(description2);
                        tvPriceGrid2.setText(sale_price2+" KWD");
                        txtTitleGrid2.setText(name2);


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
                        String description1 = response1.getString("description");
                        String sale_price1 = response1.getString("sale_price");
                        final String imageUrl1 = UserConstants.BASE_URL+UserConstants.IMAGE_FOLDER+image1;

                        View inflatedLayout= getLayoutInflater().inflate(R.layout.grid_item_2, null, false);
                        ImageView imageViewGrid1 = (ImageView) inflatedLayout.findViewById(R.id.imageViewGrid1);


                        TextView txtTitleGrid1 = (TextView) inflatedLayout.findViewById(R.id.txtTitleGrid1);
                        TextView txtDescriptionGrid1 = (TextView) inflatedLayout.findViewById(R.id.txtDescriptionGrid1);
                        TextView tvPriceGrid1 = (TextView) inflatedLayout.findViewById(R.id.tvPriceGrid1);
                        txtDescriptionGrid1.setText(description1);
                        tvPriceGrid1.setText(sale_price1+" KWD");
                        txtTitleGrid1.setText(name1);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.subgridlist_menu, menu);
        return true;
    }//optionsmenu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
      /*  if (id == R.id.home_menu_heart) {

           *//* Intent intent=new Intent(getApplicationContext(),WishList.class);
            startActivity(intent);*//*

        }*/
         if(id == R.id.subgridlist_menu_search){

        }
      else if(id == R.id.subgridlist_menu_cart){
            Intent i=new Intent(getApplicationContext(),AddToCart.class);
            startActivity(i);

        }



        return super.onOptionsItemSelected(item);
    }//optionsitemSelected

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

        else if (id == R.id.nav_lenscare) {

        } else if (id == R.id.nav_myaccount) {


            Intent intent=new Intent(getApplicationContext(),My_Account.class);
            startActivity(intent);

        } else if (id == R.id.nav_selectlang) {

            Intent intent = new Intent(getApplicationContext(),Select_Language.class);
            startActivity(intent);

        }else if (id == R.id.nav_search) {

        } else if (id == R.id.nav_shoppingcart) {
            Intent intent = new Intent(getApplicationContext(),AddToCart.class);
            startActivity(intent);

        } else if (id == R.id.nav_wishlist) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
