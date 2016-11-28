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

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import com.king.king_lens.R;

/**
 * Created by NgocTri on 10/22/2016.
 */

public class ListViewAdapter extends ArrayAdapter<Product> {
    public ListViewAdapter(Context context, int resource, List<Product> objects) {
        super(context, resource, objects);
    }



    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if(null == v) {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_item, null);
        }
        Product product = getItem(position);
        final ImageView img = (ImageView) v.findViewById(R.id.imageView);
        TextView txtTitle = (TextView) v.findViewById(R.id.txtTitle);
        TextView txtDescription = (TextView) v.findViewById(R.id.txtDescription);
     //   TextView txtPrice = (TextView) v.findViewById(R.id.price_list);

        final String imageUrl = product.getImageUrl();


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

        img.setTag(product.getProduct_id());
        txtTitle.setText(product.getTitle());
        txtDescription.setText(product.getDescription());
       // txtPrice.setText(""+product.getPrice());

        return v;
    }
}
