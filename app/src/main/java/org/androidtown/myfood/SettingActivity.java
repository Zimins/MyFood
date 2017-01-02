package org.androidtown.myfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {

    Button notifySetButton ;
    Button idSetButton ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        notifySetButton = (Button)findViewById(R.id.notify_button);
        idSetButton = (Button)findViewById(R.id.id_set);

        initSetButtons();

        notifySetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(System.getProperty("isNotifyOn")=="on"||System.getProperty("isNotifyOn")==null){
                    System.setProperty("isNotifyOn","off");
                    notifySetButton.setText("알림 켜기");
                    Toast.makeText(getApplicationContext(),"앱 알림을 끕니다",Toast.LENGTH_LONG).show();
                    return;
                }else {
                    System.setProperty("isNotifyOn", "on");
                    notifySetButton.setText("알림 끄기");
                    Toast.makeText(getApplicationContext(),"앱 알림을 켭니다",Toast.LENGTH_LONG).show();
                }
            }

        });


        idSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this,SigninActivity.class);
                startActivity(intent);
            }

        });

    }


    void initSetButtons(){
        if(System.getProperty("isNotifyOn")=="on"){
            notifySetButton.setText("알림 끄기");
        }else{
            notifySetButton.setText("알림 켜기");
        }

        if(System.getProperty("userID")!=null){
            idSetButton.setText("현재이름: "+ System.getProperty("userID")+"입니다.변경원하면클릭");
        }
    }
}
