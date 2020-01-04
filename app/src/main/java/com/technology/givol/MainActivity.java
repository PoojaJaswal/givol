package com.technology.givol;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView move_sign_up_txt;
    private int passwordNotVisible=1;
    ImageButton password_show_hide;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        move_sign_up_txt=(TextView)findViewById(R.id.move_sign_up_txt);
        move_sign_up_txt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.move_sign_up_txt:
                Intent intent=new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(intent);
                break;
        }

    }
}
