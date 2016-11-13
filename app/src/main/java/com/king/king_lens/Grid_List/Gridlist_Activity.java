package com.king.king_lens.Grid_List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.king.king_lens.Home_adslot;
import com.king.king_lens.LoginActivity;
import com.king.king_lens.My_Account;
import com.king.king_lens.R;
import com.king.king_lens.Select_Language;

import java.util.ArrayList;
import java.util.List;

public class Gridlist_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
        ,CollectionFragment.OnFragmentInteractionListener,
        GridListFragment.OnFragmentInteractionListener {


    private ViewStub stubGrid;
    private ViewStub stubList;
    private ListView listView;
    private GridView gridView;
    private ListViewAdapter listViewAdapter;
    private GridViewAdapter gridViewAdapter;
    private List<Product> productList;
    private int currentViewMode = 0;

    static final int VIEW_MODE_LISTVIEW = 0;
    static final int VIEW_MODE_GRIDVIEW = 1;


    ImageButton gridlistbtn;
    ImageButton filterbtn;
    ImageButton  firebtn;


    private ViewPager viewPager;



    //page variables
    int user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridlist_);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        viewPager = (ViewPager)findViewById(R.id.viewpager);
        setupViewPager(viewPager);

      /*  stubList = (ViewStub) findViewById(R.id.stub_list);
        stubGrid = (ViewStub) findViewById(R.id.stub_grid);

        //Inflate ViewStub before get view

        stubList.inflate();
        stubGrid.inflate();

        listView = (ListView) findViewById(R.id.mylistview);
        gridView = (GridView) findViewById(R.id.mygridview);

        //get list of product
        getProductList();

        //Get current view mode in share reference
        SharedPreferences sharedPreferences = getSharedPreferences("ViewMode", MODE_PRIVATE);
        currentViewMode = sharedPreferences.getInt("currentViewMode", VIEW_MODE_LISTVIEW);//Default is view listview
        //Register item lick
        listView.setOnItemClickListener(onItemClick);
        gridView.setOnItemClickListener(onItemClick);

        switchView();



        gridlistbtn=(ImageButton)findViewById(R.id.listicon);
        filterbtn=(ImageButton)findViewById(R.id.filter);
        firebtn=(ImageButton)findViewById(R.id.fire);


        //onclick listbtn

        gridlistbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (VIEW_MODE_LISTVIEW == currentViewMode) {
                    currentViewMode = VIEW_MODE_GRIDVIEW;
                } else {
                    currentViewMode = VIEW_MODE_LISTVIEW;
                }
                //Switch view
                switchView();
                //Save view mode in share reference
                SharedPreferences sharedPreferences = getSharedPreferences("ViewMode", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("currentViewMode", currentViewMode);
                editor.commit();
            }

        });




        //onclick  filter button


        filterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent i=new Intent(getApplicationContext(),Filter_Activity.class);
                startActivity(i);
            }
        });



        //setting background dim when showing popup
      final RelativeLayout back_dim_layout = (RelativeLayout) findViewById(R.id.bac_dim_layout);
        //onclick firebutton

       *//* firebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                *//**//*Intent i=new Intent(getApplicationContext(),Popup_Activity.class);
               // startActivity(i);

                LayoutInflater layoutInflater =
                        (LayoutInflater)getBaseContext()
                                .getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.activity_popup_, null);

                final PopupWindow popupWindow = new PopupWindow(
                       popupView, ViewGroup.LayoutParams.WRAP_CONTENT,  ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.showAsDropDown(firebtn);

                back_dim_layout.setVisibility(View.VISIBLE);*//**//*
            }
        });*//*

*/



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


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


    //fragment

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new CollectionFragment(), "COLLECTION");
        adapter.addFragment(new GridListFragment(), "GRIDLIST");

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
    }





    private void switchView() {

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
    }


    private void setAdapters() {
        if(VIEW_MODE_LISTVIEW == currentViewMode) {
            listViewAdapter = new ListViewAdapter(this, R.layout.list_item, productList);
            listView.setAdapter(listViewAdapter);
        } else {
            gridViewAdapter = new GridViewAdapter(this, R.layout.grid_item, productList);
            gridView.setAdapter(gridViewAdapter);
        }
    }

    public List<Product> getProductList() {
        //pseudo code to get product, replace your code to get real product here
        productList = new ArrayList<>();
        productList.add(new Product(R.drawable.eyepic1, "Title 1", "This is description 1","$10"));
        productList.add(new Product(R.drawable.eyepic2, "Title 2", "This is description 2","$20"));
        productList.add(new Product(R.drawable.eyepic3, "Title 3", "This is description 3","$45"));
        productList.add(new Product(R.drawable.eyepic6, "Title 4", "This is description 4","$52"));
        productList.add(new Product(R.drawable.eyepic5, "Title 5", "This is description 5","$61"));
        productList.add(new Product(R.drawable.listlens4, "Title 6", "This is description 6","$69"));
        productList.add(new Product(R.drawable.listpic3, "Title 7", "This is description 7","$87"));
        productList.add(new Product(R.drawable.listlens2, "Title 8", "This is description 8","$47"));


        return productList;
    }


    AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //Do any thing when user click to item
            Toast.makeText(getApplicationContext(), productList.get(position).getTitle() + " - " + productList.get(position).getDescription(), Toast.LENGTH_SHORT).show();



        }
    };






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
        getMenuInflater().inflate(R.menu.gridlist_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                /*if(VIEW_MODE_LISTVIEW == currentViewMode) {
                    currentViewMode = VIEW_MODE_GRIDVIEW;
                } else {
                    currentViewMode = VIEW_MODE_LISTVIEW;
                }
                //Switch view
                switchView();
                //Save view mode in share reference
                SharedPreferences sharedPreferences = getSharedPreferences("ViewMode", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("currentViewMode", currentViewMode);
                editor.commit();*/

                break;
        }
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_login) {
            Intent i=new Intent(getApplicationContext(), LoginActivity.class);
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
        }else if (id == R.id.nav_home) {

            Intent i=new Intent(getApplicationContext(), Home_adslot.class);
            startActivity(i);

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        else if (id == R.id.nav_selectlang) {

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
}
