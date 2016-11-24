package com.king.king_lens.Grid_List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.king.king_lens.Product.ProductView;
import com.king.king_lens.R;

import java.util.ArrayList;
import java.util.List;

public class SubGridList_Activity extends AppCompatActivity {



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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_fragmentgridlist_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        stubList = (ViewStub)findViewById(R.id.stub_list);
        stubGrid = (ViewStub)findViewById(R.id.stub_grid);

        //Inflate ViewStub before get view

        stubList.inflate();
        stubGrid.inflate();

        listView = (ListView)findViewById(R.id.mylistview);
        gridView = (GridView)findViewById(R.id.mygridview);

        //get list of product
        getProductList();

        //Get current view mode in share reference
        SharedPreferences sharedPreferences =getApplicationContext().getSharedPreferences("ViewMode", 0);
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
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("ViewMode", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("currentViewMode", currentViewMode);
                editor.commit();
            }

        });




        //onclick  filter button


    /*    filterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               *//* Intent i=new Intent(getApplicationContext(),Filter_Activity.class);
                startActivity(i);*//*
            }
        });*/



        //setting background dim when showing popup
   /*   final RelativeLayout back_dim_layout = (RelativeLayout)view.findViewById(R.id.bac_dim_layout);
        //onclick firebutton

        firebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                *//*Intent i=new Intent(getApplicationContext(),Popup_Activity.class);
               // startActivity(i);

                LayoutInflater layoutInflater =
                        (LayoutInflater)getBaseContext()
                                .getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.activity_popup_, null);

                final PopupWindow popupWindow = new PopupWindow(
                       popupView, ViewGroup.LayoutParams.WRAP_CONTENT,  ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.showAsDropDown(firebtn);

                back_dim_layout.setVisibility(View.VISIBLE);*//*
            }
        });


*/
    }

    public List<Product> getProductList() {
        //pseudo code to get product, replace your code to get real product here
        productList = new ArrayList<>();
        productList.add(new Product(R.drawable.eyepic1,"Tiltle1", "This is description1","$10"));
        productList.add(new Product(R.drawable.eyepic2,"Tiltle2", "This is description2","$20"));
        productList.add(new Product(R.drawable.eyepic3, "Tiltle3", "This is description3","$30"));
        productList.add(new Product(R.drawable.eyepic6,"Tiltle4", "This is description4","$40"));
        productList.add(new Product(R.drawable.eyepic5, "Tiltle5", "This is description5","$40"));
        productList.add(new Product(R.drawable.listlens4,"Tiltle6", "This is description6","$50"));
        productList.add(new Product(R.drawable.listpic3, "Tiltle7", "This is description7","$80"));
        productList.add(new Product(R.drawable.listlens2, "Tiltle8", "This is description8","$90"));


        return productList;
    }


    AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //Do any thing when user click to item
           // Toast.makeText(getApplicationContext(), productList.get(position).getTitle() + " - " + productList.get(position).getDescription(), Toast.LENGTH_SHORT).show();

            Intent intent=new Intent(getApplicationContext(),ProductView.class);
            startActivity(intent);

        }
    };


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
            listViewAdapter = new ListViewAdapter(getApplicationContext(), R.layout.list_item, productList);
            listView.setAdapter(listViewAdapter);
        } else {
            gridViewAdapter = new GridViewAdapter(getApplicationContext(), R.layout.grid_item, productList);
            gridView.setAdapter(gridViewAdapter);
        }
    }



}
