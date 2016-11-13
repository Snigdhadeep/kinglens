package com.king.king_lens.Home_Sliding;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.king.king_lens.Grid_List.Gridlist_Activity;
import com.king.king_lens.R;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;

/**
 * Created by apple on 18/03/16.
 */
public class TabFragment1 extends Fragment {




  private ExpandableHeightGridView gridview;
    private ArrayList<Beanclass> beanclassArrayList;
    private GridviewAdapter gridviewAdapter;
    private View view;
    CarouselView carouselView;

    LinearLayout linearLayout;


    int[] sampleImages = {
            R.drawable.airoptics_3, R.drawable.solotika_1, R.drawable.freshlook_2};
    private int[] IMAGEgrid = {R.drawable.layertwo, R.drawable.layerfour, R.drawable.layerfive, R.drawable.layersix, R.drawable.layerseven,R.drawable.brand1};
  /*  private String[] TITLeGgrid = {"Min 70% off", "Min 50% off", "Min 45% off",  "Min 60% off", "Min 70% off", "Min 50% off"};
    private String[] DIscriptiongrid = {"Wrist Watch", "Wrist Watch", "Wrist Watch","Wrist Watch", "Wrist Watch", "Wrist Watch"};
    private String[] Dategrid = {"Explore Now!","Grab Now!","Discover now!", "Great Savings!", "Explore Now!","Grab Now!"};*/




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragmenttab1, container, false);
        //frameLayout=(FrameLayout)view.findViewById(R.id.colorfrag1);
        //for carousel
        carouselView=(CarouselView) view.findViewById(R.id.carouselView1);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(sampleImages[position]);
            }
        });




        linearLayout=(LinearLayout)view.findViewById(R.id.hidelayout);


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



        /*    linearLayout.setVisibility(View.GONE);


           Color_Collectionfrag color_collectionfrag = new Color_Collectionfrag();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.colorfrag1, color_collectionfrag);

            fragmentTransaction.addToBackStack(null);

            fragmentTransaction.commit();

         // linearLayout.setVisibility(View.VISIBLE);

            Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();*/

        }
    };


}