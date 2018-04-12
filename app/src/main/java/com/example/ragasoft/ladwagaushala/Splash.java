package com.example.ragasoft.ladwagaushala;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ragasoft.ladwagaushala.utills.MySharedData;
import com.example.ragasoft.ladwagaushala.utills.SharedPreference;

public class Splash extends AppCompatActivity
{
    ImageView splash;
    // private SharedPreference sharedPreference;
    Activity context = this;
    String email_, password_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splash = findViewById(R.id.splashimage);
        //sharedPreference = new SharedPreference();

       /* email_ =sharedPreference.getValue(context);
         password_ = sharedPreference.getValue(context);*/

            Thread th =new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                        if (MySharedData.getGeneralSaveSession("Email").equals("") && MySharedData.getGeneralSaveSession("password").equals("")) {
                            Intent in = new Intent(Splash.this, LoginActivity.class);
                            startActivity(in);
                            finish();
                        } else /*if(sharedPreference.getValue(context).equals(""))*/ {
                            {
                                Intent iin = new Intent(Splash.this, HomePage.class);
                                startActivity(iin);
                                finish();
                            }

                        }/*else{
                            Toast.makeText(Splash.this, "Status", Toast.LENGTH_SHORT).show();
                        }*/
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }

                }
            }); th.start();

        }

}
