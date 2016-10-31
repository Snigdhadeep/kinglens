package com.king.king_lens.Home_Sliding;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.king.king_lens.Grid_List.Gridlist_Activity;
import com.king.king_lens.R;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;

/**
 * Created by apple on 18/03/16.
 */
public class TabFragment2 extends Fragment {




    private ExpandableHeightGridView gridview;
    private ArrayList<Beanclass> beanclassArrayList;
    private GridviewAdapter gridviewAdapter;
    private View view;
    CarouselView carouselView;




    int[] sampleImages = {
            R.drawable.brandontop, R.drawable.layertwo, R.drawable.brandontop};

    private int[] IMAGEgrid = {R.drawable.brand1, R.drawable.brand2, R.drawable.brand3, R.drawable.brand4, R.drawable.brand16,R.drawable.brand15};




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragmenttab2, container, false);


        //for carousel
        carouselView = (CarouselView)view.findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(sampleImages[position]);
            }
        });


        gridview = (ExpandableHeightGridView)view.findViewById(R.id.gridview);
        beanclassArrayList= new ArrayList<Beanclass>();

        for (int i= 0; i< IMAGEgrid.length; i++) {

            Beanclass beanclass = new Beanclass(IMAGEgrid[i]);
            beanclassArrayList.add(beanclass);

        }
        gridviewAdapter = new GridviewAdapter(getActivity(), beanclassArrayList);
        gridview.setExpanded(true);
       gridview.setOnItemClickListener(onItemClick);
        gridview.setAdapter(gridviewAdapter);
     return view;

    }


    AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //Do any thing when user click to item
          //  Toast.makeText(getActivity(),gridviewAdapter.getItemId(position) + " - " , Toast.LENGTH_SHORT).show();

            Intent i=new Intent(getActivity(), Gridlist_Activity.class);
            startActivity(i);

        }
    };
}