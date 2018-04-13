package com.example.ragasoft.ladwagaushala;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.ragasoft.ladwagaushala.utills.MySharedData;
import com.facebook.login.LoginManager;

public class HomePage extends AppCompatActivity implements View.OnClickListener
{
    ConstraintLayout layoutList;
   private ImageView logout_btn, profilImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        layoutList = findViewById(R.id.listView);
        layoutList.setOnClickListener(this);
        logout_btn = findViewById(R.id.logout_btn);
        logout_btn.setOnClickListener(this);
        profilImg =findViewById(R.id.profile_img);
       // profilImg.setImageResource(Integer.valueOf(MySharedData.getGeneralSaveSession("profile_pic")));
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.logout_btn:


                LoginManager.getInstance().logOut();
                Intent intent = new Intent(HomePage.this,LoginActivity.class);
                startActivity(intent);
                break;

          /* */
        }


    }
}
