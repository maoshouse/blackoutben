package com.example.blackoutben;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class ConfirmLogin extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_login);

        SharedPreferences sharedPref = getSharedPreferences("user.xml", 0);
        String userName = sharedPref.getString(Constants.USER_NAME,"DEFAULT");
        String phoneNumber = sharedPref.getString(Constants.PHONE_NUMBER,"DEFAULT");
        String groupID = sharedPref.getString(Constants.GROUP_ID,"DEFAULT");


        TextView tv_userName = (TextView)findViewById(R.id.userName);
        tv_userName.setText(userName);
        TextView tv_groupID = (TextView)findViewById(R.id.groupID);
        tv_groupID.setText(groupID);

        TextView tv_phoneNumber = (TextView)findViewById(R.id.phoneNumber);
        tv_phoneNumber.setText(phoneNumber);



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
}
