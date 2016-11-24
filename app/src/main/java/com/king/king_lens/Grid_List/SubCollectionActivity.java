package com.king.king_lens.Grid_List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.king.king_lens.Product.SubGridListActivity;
import com.king.king_lens.R;

import java.util.ArrayList;
import java.util.List;

public class SubCollectionActivity extends AppCompatActivity {


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


        listView = (ListView)findViewById(R.id.mylistview);

        getProductList();
        collectionListAdapter = new CollectionListAdapter(getApplicationContext(), R.layout.collection_listitem,collectionProduct);
        listView.setAdapter(collectionListAdapter);

        //get list of product
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent i=new Intent(getApplicationContext(),SubGridList_Activity.class);
                startActivity(i);
            /*  final int i=position;

                Button btnReadMore=(Button)findViewById(R.id.btnReadMore);
                btnReadMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(NewCollectionActivity.this, ""+i, Toast.LENGTH_SHORT).show();

               }
             });
*/


            }
        });
    }

    public List<CollectionProduct> getProductList() {
        //pseudo code to get product, replace your code to get real product here
        collectionProduct = new ArrayList<>();
        collectionProduct.add(new CollectionProduct(R.drawable.freshlook1, "FRESHLOOK COLORBLENDS"));
        collectionProduct.add(new CollectionProduct(R.drawable.freshlook2, "FRESHLOOK COLORS"));
        collectionProduct.add(new CollectionProduct(R.drawable.freshlook3, "FRESHLOOK ONE DAY COLORED LENS"));
        collectionProduct.add(new CollectionProduct(R.drawable.airoptix4, "AIR OPTICS"));

        return collectionProduct;
    }

}
