package com.king.king_lens.Grid_List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.king.king_lens.R;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by NgocTri on 10/22/2016.
 */

public class CollectionListAdapter extends ArrayAdapter<CollectionProduct> {
    public CollectionListAdapter(Context context, int resource, List<CollectionProduct> objects) {
        super(context, resource, objects);
    }



    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if(null == v) {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.collection_listitem, null);
        }
        CollectionProduct collectionProduct = getItem(position);
        final ImageView img = (ImageView) v.findViewById(R.id.imageView);
        TextView txt=(TextView)v.findViewById(R.id.txt_collection);

        final String imageUrl = collectionProduct.getImageUrl();

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
                    img.setImageBitmap(bmp);
                }


            }
        }.execute();

        img.setTag(collectionProduct.getId());
        txt.setText(collectionProduct.getTitle());

        return v;
    }
}
