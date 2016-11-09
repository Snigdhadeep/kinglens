package HelperClasses;

import android.os.AsyncTask;

import java.util.HashMap;

/**
 * Created by root on 15/7/16.
 */
public class Api {
    String method;
    public AsyncResponse.Response delegate = null;

    public Api(String method)
    {
        this.method = method;
    }


    public void call(final HashMap<String, String> data, String route) {
        final String FEED_URL = UserConstants.BASE_URL+route;
        class CallApiData extends AsyncTask<String, Void, String> {

            ConnectToServer ruc = new ConnectToServer(method);


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                delegate.processFinish(s);
            }

            @Override
            protected String doInBackground(String... params) {

                String result = ruc.sendPostRequest(FEED_URL,data);

                return  result;
            }
        }


        CallApiData ru = new CallApiData();

        ru.execute();

    }
}
