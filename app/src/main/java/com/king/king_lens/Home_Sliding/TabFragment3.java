package com.king.king_lens.Home_Sliding;

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

import com.king.king_lens.Grid_List.Gridlist_Activity;
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

import HelperClasses.AsyncResponse;
import HelperClasses.AsyncResponse2;
import HelperClasses.RegisterUser;
import HelperClasses.RegisterUser2;
import HelperClasses.UserConstants;

/**
 * Created by apple on 18/03/16.
 */
public class TabFragment3 extends Fragment implements AsyncResponse.Response, AsyncResponse2.Response2 {

    private ExpandableHeightGridView gridview;
    private ArrayList<Beanclass> beanclassArrayList;
    private GridviewAdapter gridviewAdapter;
    private View view;
    CarouselView carouselView;

    LinearLayout llNoBrand;

    //server variables
    RegisterUser registerUser = new RegisterUser("POST");
    private String route = "api/v1/brandbycategory";
    HashMap<String,String> data = new HashMap<>();

    //server variable
    RegisterUser2 registerUser2 = new RegisterUser2("POST");
    private String route2 = "api/v1/banner-images";
    HashMap<String,String> data2 = new HashMap<>();

    //unused variables
    int[] sampleImages = {
            R.drawable.whiteback, R.drawable.whiteback, R.drawable.whiteback};

    private int[] IMAGEgrid = {R.drawable.brand20, R.drawable.brand21, R.drawable.brand22, R.drawable.brand23,R.drawable.brand26,R.drawable.brand12};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragmenttab3, container, false);

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

        registerUser2.delegate = this;

        registerUser2.register(data2,route2);

        if (UserConstants.tab3ExecutionDone) {

            setBitMapArray();
            ArrayList<Beanclass> savedImageData = UserConstants.tab3ImageArray;
            for(int k = 0; k<savedImageData.size();k++)
            {
                setUpImages(savedImageData.get(k).getBmp(),savedImageData.get(k).getImgaeId());
            }

        }
        else
        {
            registerUser2.delegate = this;

            registerUser2.register(data2,route2);

            registerUser.delegate = this;

            data.put("category_id",String.valueOf(1));
            registerUser.register(data,route);

            UserConstants.tab1ExecutionDone = true;
        }

        llNoBrand = (LinearLayout) view.findViewById(R.id.llNoBrand);

        return view;


    }

    AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ImageView imageView =(ImageView) view.findViewById(R.id.image1);
            String tag = imageView.getTag().toString();
            //Toast.makeText(getActivity(),tag, Toast.LENGTH_SHORT).show();

            Intent i=new Intent(getActivity(), SubCollectionActivity.class);
            UserConstants.collection_Brand_id = tag;
            UserConstants.collection_Category_id = String.valueOf(1);
            startActivity(i);

        }
    };

    public void setUpImages(Bitmap bmp, Integer brandId)
    {
        Beanclass beanclass = new Beanclass(IMAGEgrid[1]);
        beanclass.setBmp(bmp);
        beanclass.setImageId(brandId);

        beanclassArrayList.add(beanclass);
        gridviewAdapter = new GridviewAdapter(getActivity(), beanclassArrayList);
        gridview.setExpanded(true);
        gridview.setOnItemClickListener(onItemClick);

        gridview.setAdapter(gridviewAdapter);
    }

    @Override
    public void processFinish(String output) {
        try {
            JSONObject jsonObject=new JSONObject(output);
            Log.i("tab3",jsonObject.toString());

            if(jsonObject.getBoolean("status")){

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
                                setUpImages(bmp,brandId);
                            }


                        }
                    }.execute();
                }//for loop
            }//if statemnt
            else
            {
                //Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                llNoBrand.setVisibility(View.VISIBLE);
            }
        } catch (JSONException e) {
            e.toString();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //outState.putSerializable("curChoice", beanclassArrayList);
        UserConstants.tab3ImageArray = beanclassArrayList;
        UserConstants.tab3ExecutionDone = true;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void processFinish2(String output) {
        //Toast.makeText(getContext(), output, Toast.LENGTH_SHORT).show();

        try
        {
            JSONObject jsonObject = new JSONObject(output);
            if(jsonObject.getBoolean("status"))
            {
                JSONObject response = new JSONObject(jsonObject.getString("response"));
                String color1 = response.getString("accessories1");
                String color1Url = UserConstants.BASE_URL+UserConstants.IMAGE_FOLDER+color1;
                //loadImage(color1Url);

                String color2 = response.getString("accessories2");
                String color2Url = UserConstants.BASE_URL+UserConstants.IMAGE_FOLDER+color2;

                String color3 = response.getString("accessories3");
                String color3Url = UserConstants.BASE_URL+UserConstants.IMAGE_FOLDER+color3;
                loadImage(color1Url,color2Url,color3Url);


                //Toast.makeText(getContext(), color1Url, Toast.LENGTH_SHORT).show();

            }
            else
            {
                Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e) {
            Log.i("tabfrag1",e.toString());
            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
        }

    }


    public void loadImage(final String imageUrl, final String imageUrl2, final String imageUrl3)
    {
        AsyncTask asyncTask = new AsyncTask<Void, Void, Void>() {
            Bitmap bmp;
            Bitmap bmp2;
            Bitmap bmp3;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected Void doInBackground(Void... params) {

                try {
                    InputStream in = new URL(imageUrl).openStream();
                    bmp = BitmapFactory.decodeStream(in);

                    InputStream in2 = new URL(imageUrl2).openStream();
                    bmp2 = BitmapFactory.decodeStream(in2);

                    InputStream in3 = new URL(imageUrl3).openStream();
                    bmp3 = BitmapFactory.decodeStream(in3);
                } catch (Exception e) {
                    //Toast.makeText(getContext(),"Some error occoured while loading images!",Toast.LENGTH_LONG).show();
                    Log.i("kingsukmajumder","error in loading images "+e.toString());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                //loading.dismiss();
                if (bmp != null && bmp2!=null && bmp3!=null)
                {
                    /*if(carouselCount==0)
                    {*/
                    UserConstants.acceBit1=bmp;
                    /*}
                    else if(carouselCount==1)
                    {*/
                    UserConstants.acceBit2=bmp2;
                    UserConstants.acceBit3=bmp3;
                    //}
                    /*else if(carouselCount==2)
                    {
                        bit3=bmp;
                    }*/



                    setBitMapArray();

                }
            }
        }.execute();



    }

    public void setBitMapArray()
    {
        final Bitmap[] bitImages = {
                UserConstants.acceBit1,  UserConstants.acceBit2,  UserConstants.acceBit3};
        setUpCarousel(bitImages);
    }

    public void setUpCarousel(final Bitmap[] bitImages)
    {
        carouselView.setPageCount(bitImages.length);
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {

                imageView.setImageBitmap(bitImages[position]);
            }
        });
    }
}