package com.example.ragasoft.ladwagaushala;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ragasoft.ladwagaushala.model.RegisteredModel;
import com.example.ragasoft.ladwagaushala.retrofit.ApiClient;
import com.example.ragasoft.ladwagaushala.retrofit.ApiInterface;
import com.example.ragasoft.ladwagaushala.utills.MySharedData;
import com.example.ragasoft.ladwagaushala.utills.SharedPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    EditText name,email,password,mobileNo;
    Button signUp;
     String name_,email_,password_,mobileNo_;

    Activity context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.name);
        email= findViewById(R.id.email);
        password= findViewById(R.id.password);
        mobileNo = findViewById(R.id.mobileno);
        signUp = findViewById(R.id.sign_in); signUp.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(v==signUp)
        {
            addRetroMethod();
        }
    }
    public void addRetroMethod()
    {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(signUp.getWindowToken(), 0);

        name_ = name.getText().toString();
        email_ =email.getText().toString().trim();
        password_ = password.getText().toString();
        mobileNo_ = mobileNo.getText().toString();


            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<RegisteredModel> call = apiInterface.getRegisterCall(name_,email_,password_,mobileNo_);
        call.enqueue(new Callback<RegisteredModel>() {
            @Override
            public void onResponse(Call<RegisteredModel> call, Response<RegisteredModel> response) {
                if (response.isSuccessful())
                {
                    String status = response.body().getMessage();

                    MySharedData.setGeneralSaveSession("Email",email_);
                    MySharedData.setGeneralSaveSession("password",password_);
                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);

                    Toast.makeText(SignUpActivity.this, "Status"+response.body().getStatus(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisteredModel> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, "Status not Found", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
