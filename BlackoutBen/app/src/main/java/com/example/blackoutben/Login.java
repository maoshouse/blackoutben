package com.example.blackoutben;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.maoshouse.myapplication.backend.locApi.model.Loc;


public class Login extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_login);
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPref = getSharedPreferences("user.xml", 0);
        String userName = sharedPref.getString(Constants.USER_NAME, "");
        String phoneNumber = sharedPref.getString(Constants.PHONE_NUMBER,"");
        String groupID = sharedPref.getString(Constants.GROUP_ID, "");

        EditText user =   ((EditText)findViewById(R.id.username));
        ((EditText)findViewById(R.id.phonenumber)).setText(phoneNumber);
        ((EditText)findViewById(R.id.groupname)).setText(groupID);

        user.setText(userName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Called when user clicks the Join Group button
     */
    public void enterGroup(View view) {
        Intent intent = new Intent(this, ConfirmLogin.class);
        String userName = ((EditText)findViewById(R.id.username)).getText().toString();
        String phoneNumber = ((EditText)findViewById(R.id.phonenumber)).getText().toString();
        String groupID = ((EditText)findViewById(R.id.groupname)).getText().toString();

        Bundle userData = new Bundle();
        userData.putString(Constants.USER_NAME, userName);
        userData.putString(Constants.PHONE_NUMBER, phoneNumber);
        userData.putString(Constants.GROUP_ID, groupID);

        intent.putExtras(userData);

        SharedPreferences sharedPref = getSharedPreferences("user.xml", 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Constants.USER_NAME, userName);
        editor.putString(Constants.PHONE_NUMBER, phoneNumber);
        editor.putString(Constants.GROUP_ID, groupID);
        editor.commit();

        Loc userLoc = new Loc();
        userLoc.setGroup(groupID);
        userLoc.setUser(userName);
        userLoc.setLatitude(-118.285);
        userLoc.setLongitude(34.020);

        new SetUserAsyncTask().execute(new Pair<Context, Loc>(this, userLoc));
        startActivity(intent);
    }
}
