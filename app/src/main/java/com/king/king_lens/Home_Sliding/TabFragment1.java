package com.king.king_lens.Home_Sliding;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import HelperClasses.AsyncResponse2;
import HelperClasses.RegisterUser;
import HelperClasses.RegisterUser2;
import HelperClasses.UserConstants;

/**
 * Created by apple on 18/03/16.
 */
public class TabFragment1 extends Fragment implements AsyncResponse.Response, AsyncResponse2.Response2 {




  private ExpandableHeightGridView gridview;
    private ArrayList<Beanclass> beanclassArrayList;
    private GridviewAdapter gridviewAdapter;
    private View view;
    CarouselView carouselView;

    LinearLayout linearLayout;
    LinearLayout llNoBrand;


    //server variable
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
    private int[] IMAGEgrid = {R.drawable.freshlooknew, R.drawable.layerfour, R.drawable.layerfive, R.drawable.layersix, R.drawable.layerseven,R.drawable.brand1};


    int carouselCount = 0;
    Bitmap bit1;
    Bitmap bit2;
    Bitmap bit3;


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

        registerUser2.delegate = this;

        registerUser2.register(data2,route2);

        if (UserConstants.tab1ExecutionDone) {
            // Restore last state for checked position.
            setBitMapArray();
            ArrayList<Beanclass> savedImageData = UserConstants.tab1ImageArray;
            for(int k = 0; k<savedImageData.size();k++)
            {

                setUpImages(savedImageData.get(k).getBmp(),savedImageData.get(k).getImgaeId());
            }

        }
        else
        {
            registerUser.delegate = this;
            registerUser2.delegate = this;

            registerUser2.register(data2,route2);

            data.put("category_id",String.valueOf(3));
            registerUser.register(data,route);
            UserConstants.tab1ExecutionDone = true;
        }

        llNoBrand = (LinearLayout) view.findViewById(R.id.llNoBrand);


     return view;

    }

    AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //Do any thing when user click to item
            ImageView imageView =(ImageView) view.findViewById(R.id.image1);
            String tag = imageView.getTag().toString();
            //Toast.makeText(getActivity(),tag, Toast.LENGTH_SHORT).show();

            Intent i=new Intent(getActivity(), SubCollectionActivity.class);

            UserConstants.collection_Brand_id = tag;
            UserConstants.collection_Category_id = String.valueOf(3);
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
                                setUpImages(bmp,brandId);
                            }


                        }
                    }.execute();
                }

            }
            else
            {
                //Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                llNoBrand.setVisibility(View.VISIBLE);
            }

            //setUpImages();

        } catch (JSONException e) {
            Log.i("tabfrag1",e.toString());
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //outState.putSerializable("curChoice", beanclassArrayList);
        UserConstants.tab1ImageArray = beanclassArrayList;
        UserConstants.tab1ExecutionDone = true;
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
                String color1 = response.getString("color1");
                String color1Url = UserConstants.BASE_URL+UserConstants.IMAGE_FOLDER+color1;
                //loadImage(color1Url);

                String color2 = response.getString("color2");
                String color2Url = UserConstants.BASE_URL+UserConstants.IMAGE_FOLDER+color2;

                String color3 = response.getString("color3");
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
                        UserConstants.colorBit1=bmp;
                    /*}
                    else if(carouselCount==1)
                    {*/
                    UserConstants.colorBit2=bmp2;
                    UserConstants.colorBit3=bmp3;
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
                UserConstants.colorBit1,  UserConstants.colorBit2,  UserConstants.colorBit3};
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