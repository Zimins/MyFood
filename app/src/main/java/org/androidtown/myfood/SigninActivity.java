package org.androidtown.myfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SigninActivity extends AppCompatActivity {

    EditText idInput;
    Button commitButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        idInput =(EditText)findViewById(R.id.id_input);
        commitButton =(Button)findViewById(R.id.commit);

        commitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(idInput.getText()!=null){
                    String id = idInput.getText().toString();
                    System.setProperty("userID",id);
                    Toast.makeText(getApplicationContext(),id+"로 등록합니다",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(SigninActivity.this,MainActivity.class);
                    startActivity(intent);

                    finish();
                    return;
                }
                Toast.makeText(getApplicationContext(),"아이디를 입력하세요.",Toast.LENGTH_LONG).show();
            }
        });
    }
}
