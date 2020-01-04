package com.technology.givol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    Button sign_upBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        sign_upBtn=(Button)findViewById(R.id.sign_upBtn);
        sign_upBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.sign_upBtn:
                Intent intent=new Intent(getApplicationContext(),CurrentLocationActivity.class);
                startActivity(intent);
                break;
        }

    }
}
