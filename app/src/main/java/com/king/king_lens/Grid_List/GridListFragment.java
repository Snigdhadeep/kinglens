package com.king.king_lens.Grid_List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.king.king_lens.Product.ProductView;
import com.king.king_lens.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GridListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GridListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GridListFragment extends Fragment {



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


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public GridListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GridListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GridListFragment newInstance(String param1, String param2) {
        GridListFragment fragment = new GridListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.app_bar_fragmentgridlist_, container, false);



         //stubList = (ViewStub)view.findViewById(R.id.stub_list);
        //stubGrid = (ViewStub)view.findViewById(R.id.stub_grid);

        //Inflate ViewStub before get view

        stubList.inflate();
        stubGrid.inflate();

        listView = (ListView)view.findViewById(R.id.mylistview);
        gridView = (GridView)view.findViewById(R.id.mygridview);

        //get list of product
        getProductList();

        //Get current view mode in share reference
        SharedPreferences sharedPreferences =getActivity().getSharedPreferences("ViewMode", 0);
        currentViewMode = sharedPreferences.getInt("currentViewMode", VIEW_MODE_LISTVIEW);//Default is view listview
        //Register item lick
        listView.setOnItemClickListener(onItemClick);
        gridView.setOnItemClickListener(onItemClick);

        switchView();



        gridlistbtn=(ImageButton)view.findViewById(R.id.listicon);
        filterbtn=(ImageButton)view.findViewById(R.id.filter);
        firebtn=(ImageButton)view.findViewById(R.id.fire);


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
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("ViewMode", 0);
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


        return view;
    }
    //on create




    public List<Product> getProductList() {
        //pseudo code to get product, replace your code to get real product here
        productList = new ArrayList<>();
        /*productList.add(new Product(R.drawable.eyepic1,"HONEY", "FRESHLOOK COLORBLENDS"));
        productList.add(new Product(R.drawable.eyepic2,"HAZELNUT", "FRESHLOOK COLORBLENDS"));
        productList.add(new Product(R.drawable.eyepic3, "GREY", "FRESHLOOK COLORBLENDS"));
        productList.add(new Product(R.drawable.eyepic8,"GREEN", "FRESHLOOK COLORBLENDS"));
        productList.add(new Product(R.drawable.eyepic5, "AMETHYST", "FRESHLOOK COLORBLENDS"));
        productList.add(new Product(R.drawable.listlens4,"GEMSTONE GREEN", "FRESHLOOK COLORBLENDS"));
        productList.add(new Product(R.drawable.listpic3, "TRUE SAPPHIRE", "FRESHLOOK COLORBLENDS"));
        productList.add(new Product(R.drawable.listlens2, "STERLING GREY", "FRESHLOOK COLORBLENDS"));*/


        return productList;
    }



    AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //Do any thing when user click to item
            Toast.makeText(getActivity(), productList.get(position).getTitle() + " - " + productList.get(position).getDescription(), Toast.LENGTH_SHORT).show();

            Intent intent=new Intent(getActivity(),ProductView.class);
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
            listViewAdapter = new ListViewAdapter(getActivity(), R.layout.list_item, productList);
            listView.setAdapter(listViewAdapter);
        } else {
            gridViewAdapter = new GridViewAdapter(getActivity(), R.layout.grid_item, productList);
            gridView.setAdapter(gridViewAdapter);
        }
    }





    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
