package HelperClasses;

import android.os.AsyncTask;

import java.util.HashMap;

/**
 * Created by root on 25/8/16.
 */
public class RegisterUser2 {
    String method;
    public AsyncResponse2.Response2 delegate = null;

    public RegisterUser2(String method)
    {
        this.method = method;
    }


    public void register(final HashMap<String, String> data, String route) {
        final String FEED_URL = UserConstants.BASE_URL+route;
        class RegisterUserData extends AsyncTask<String, Void, String> {

            ConnectToServer ruc = new ConnectToServer(method);


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                delegate.processFinish2(s);
            }

            @Override
            protected String doInBackground(String... params) {

                String result = ruc.sendPostRequest(FEED_URL,data);

                return  result;
            }
        }


        RegisterUserData ru = new RegisterUserData();

        ru.execute();

    }
}
