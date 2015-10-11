package com.example.blackoutben;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import com.example.maoshouse.myapplication.backend.locApi.LocApi;
import com.example.maoshouse.myapplication.backend.locApi.model.Loc;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

/**
 * Created by maoshouse on 10/10/15.
 */
public class UpdateUserAsyncTask  extends AsyncTask<Pair<Context, Loc>, Void, String> {
    private static LocApi locApiService = null;
    private Context context;

    @Override
    protected String doInBackground(Pair<Context, Loc>... params) {
        if(locApiService == null) {  // Only do this once
            LocApi.Builder builder = new LocApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            locApiService = builder.build();
        }

        context = params[0].first;
        Loc userLoc = params[0].second;

        try {
            SharedPreferences sharedPref = context.getSharedPreferences("user.xml", 0);
            Long ID = sharedPref.getLong(Constants.ID, -1);

            Loc returnLoc = locApiService.update(ID, userLoc).execute();

            return "Update user!";
        } catch (IOException e) {
            Log.e("err", e.getMessage());
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {

        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
    }
}
