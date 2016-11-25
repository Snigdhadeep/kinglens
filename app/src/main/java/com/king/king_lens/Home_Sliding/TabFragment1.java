package com.king.king_lens.Home_Sliding;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.king.king_lens.Grid_List.SubCollectionActivity;
import com.king.king_lens.R;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

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

/**
 * Created by apple on 18/03/16.
 */
public class TabFragment1 extends Fragment implements AsyncResponse.Response {




  private ExpandableHeightGridView gridview;
    private ArrayList<Beanclass> beanclassArrayList;
    private GridviewAdapter gridviewAdapter;
    private View view;
    CarouselView carouselView;

    LinearLayout linearLayout;


    //server variables
    RegisterUser registerUser = new RegisterUser("POST");
    private String route = "api/v1/brandbycategory";
    HashMap<String,String> data = new HashMap<>();

    //loading variables
    ProgressDialog loading;


    HashMap<Bitmap,Integer> imageList = new HashMap<>();
    int j=0;

    int[] sampleImages = {
            R.drawable.airoptics_3, R.drawable.solotika_1, R.drawable.freshlook_2};
    private int[] IMAGEgrid = {R.drawable.freshlooknew, R.drawable.layerfour, R.drawable.layerfive, R.drawable.layersix, R.drawable.layerseven,R.drawable.brand1};





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


        registerUser.delegate = this;
        //  api.delegate=this;
        data.put("category_id",String.valueOf(3));

        registerUser.register(data,route);


        linearLayout=(LinearLayout)view.findViewById(R.id.hidelayout);

        gridview = (ExpandableHeightGridView)view.findViewById(R.id.gridview);
        beanclassArrayList= new ArrayList<Beanclass>();



     return view;

    }

    AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //Do any thing when user click to item
          //  Toast.makeText(getActivity(),gridviewAdapter.getItemId(position) + " - " , Toast.LENGTH_SHORT).show();

            Intent i=new Intent(getActivity(), SubCollectionActivity.class);
            startActivity(i);


        }
    };

    public void setUpImages()
    {



        gridviewAdapter = new GridviewAdapter(getActivity(), beanclassArrayList);
        gridview.setExpanded(true);
        gridview.setOnItemClickListener(onItemClick);

        gridview.setAdapter(gridviewAdapter);
    }
    @Override
    public void processFinish(String output) {

        try {
            JSONObject jsonObject = new JSONObject(output);
            Log.i("tabfrag1",jsonObject.toString());

            if(jsonObject.getBoolean("status"))
            {

                JSONArray jsnArray = new JSONArray(jsonObject.getString("response"));
                for (int i=0;i<jsnArray.length();i++)
                {
                    JSONObject response = new JSONObject(jsnArray.get(i).toString());
                    final int brandId = response.getInt("id");
                    String image = response.getString("image");
                    final String imageUrl = UserConstants.BASE_URL+UserConstants.IMAGE_FOLDER+image;
                    //Toast.makeText(getContext(), image, Toast.LENGTH_SHORT).show();

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
                                Toast.makeText(getContext(),"downloaded "+j,Toast.LENGTH_SHORT).show();
                                j++;
                                imageList.put(bmp,brandId);
                                Beanclass beanclass = new Beanclass(IMAGEgrid[1]);
                                beanclass.setBmp(bmp);
                                beanclassArrayList.add(beanclass);
                                /*for (int i= 0; i< IMAGEgrid.length; i++) {

                                    Beanclass beanclass = new Beanclass(IMAGEgrid[i]);
                                    beanclassArrayList.add(beanclass);

                                }*/

                                gridviewAdapter = new GridviewAdapter(getActivity(), beanclassArrayList);
                                gridview.setExpanded(true);
                                gridview.setOnItemClickListener(onItemClick);

                                gridview.setAdapter(gridviewAdapter);
                            }


                        }
                    }.execute();
                }

            }
            else
            {
                Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
            }

            //setUpImages();

        } catch (JSONException e) {
            Log.i("tabfrag1",e.toString());
        }

    }
}