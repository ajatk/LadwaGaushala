package com.example.ragasoft.ladwagaushala;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.facebook.login.LoginManager;

public class HomePageTemp extends AppCompatActivity implements View.OnClickListener {
    ConstraintLayout layoutList;
    private ImageView logout_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_temp);
        layoutList = findViewById(R.id.listView);
        layoutList.setOnClickListener(this);
        logout_btn = findViewById(R.id.logout_btn);
        logout_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logout_btn:


                LoginManager.getInstance().logOut();
                Intent intent = new Intent(HomePageTemp.this, LoginActivity.class);
                startActivity(intent);
                break;

          /* */
        }


    }
}
