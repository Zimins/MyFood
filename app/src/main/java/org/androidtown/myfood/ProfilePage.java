package org.androidtown.myfood;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.io.InputStream;
import java.net.URL;

public class ProfilePage extends AppCompatActivity {

    CallbackManager callbackManager;
    Bitmap profileBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        if(Profile.getCurrentProfile()!=null){
            setProfileBitmap();
        }


        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton)findViewById(R.id.flogin_button);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getApplicationContext(),"login succeed",Toast.LENGTH_LONG).show();
                setProfileBitmap();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(),"로그인 취소",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(),"로그인 실패",Toast.LENGTH_LONG).show();
            }
        });

    }

    public void setProfileBitmap(){

        Profile profile = Profile.getCurrentProfile();
        final String link = profile.getProfilePictureUri(800, 800).toString();

        Log.i("profile","b thread");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    URL url = new URL(link);
                    InputStream is =url.openStream();
                    profileBitmap= BitmapFactory.decodeStream(is);
                    Log.i("profile","get");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        });

        thread.start();
        try{
            thread.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }


        ImageView profileImg = (ImageView)findViewById(R.id.profile_image);
        RoundedBitmapDrawable rProfile = RoundedBitmapDrawableFactory.create(getResources(),profileBitmap);
        rProfile.setCornerRadius(Math.max(profileBitmap.getWidth(), profileBitmap.getHeight()) / 2.0f);
        rProfile.setAntiAlias(true);
        profileImg.setImageDrawable(rProfile);
        TextView idView = (TextView)findViewById(R.id.user_id);
        idView.setText(profile.getName());

    }
}
