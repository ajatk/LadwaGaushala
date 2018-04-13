package com.example.ragasoft.ladwagaushala;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ragasoft.ladwagaushala.model.RegisteredModel;
import com.example.ragasoft.ladwagaushala.retrofit.ApiClient;
import com.example.ragasoft.ladwagaushala.retrofit.ApiInterface;
import com.example.ragasoft.ladwagaushala.utills.MySharedData;
import com.example.ragasoft.ladwagaushala.utills.SharedPreference;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener
{
    EditText email,password;
    String email_,password_, publicProfile, emailfb, sex, profilePic;
    CallbackManager callbackManager;
    LoginButton fbBtn;
    Activity context = this;
    Button loginbtn;
    TextView textView;
    private ImageView facebookLogin,googlePlusLogin, twitterLogin;
    //private SharedPreference sharedPreference;
    //private FacebookCallback id = fa;
     private String id = "604703796595054";
     private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_login);
      //  sharedPreference =new SharedPreference();

        loginbtn = findViewById(R.id.login);
        textView = findViewById(R.id.signup_link);
        textView.setOnClickListener(this);
        loginbtn.setOnClickListener(this);
        email= findViewById(R.id.email);
        password= findViewById(R.id.password);
        callbackManager = CallbackManager.Factory.create();
        fbBtn =findViewById(R.id.fb_loginBtn);fbBtn.setOnClickListener(this);
        facebookLogin = findViewById(R.id.fb_login); facebookLogin.setOnClickListener(this);
        googlePlusLogin = findViewById(R.id.googlePlus_login); googlePlusLogin.setOnClickListener(this);
        twitterLogin = findViewById(R.id.twitter_login); twitterLogin.setOnClickListener(this);
        getFacebookLogin();
    }

    @Override
    public void onClick(View v)
    {
        email_ =email.getText().toString().trim();
        password_ = password.getText().toString().trim();

        switch (v.getId()) {

            case R.id.login:
                addLoginMethod();
                LoginManager.getInstance().logOut();

                 break;
            case R.id.signup_link :
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
               break;
            case R.id.fb_login:
                fbBtn.performClick();
               /*if(id!=null)
               {
                   Intent fbIn = new Intent(getApplicationContext(), HomePage.class);
                   startActivity(fbIn);
                   finish();
               } else
               {
                   fbBtn.performClick();
               }*/
              //
                break;
            case R.id.googlePlus_login:

                break;

            case R.id.twitter_login:

                break;
        }
        return;
    }

    public void addLoginMethod()
    {

        if(email_.isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email_).matches())
        {
            email.setError("please fill valid email");
            setFocusableInput(email);
        }
        else if ( password_.isEmpty())
        {
            password.setError("Not a valid adress");
            setFocusableInput(password);
        }

        else {

            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<RegisteredModel> call = apiInterface.getLoginCall(email_, password_);
            call.enqueue(new Callback<RegisteredModel>() {
                @Override
                public void onResponse(Call<RegisteredModel> call, Response<RegisteredModel> response) {
                    if (response.isSuccessful()) {
                        String status = response.body().getMessage();
                       /* SharedPreference.save(context, email_);
                        SharedPreference.save(context, password_);*/

                        MySharedData.setGeneralSaveSession("Email",email_);
                        MySharedData.setGeneralSaveSession("password",password_);
                        Toast.makeText(context, "<<<<<<< email and password>>" +email_ +" " +password_, Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(LoginActivity.this, HomePage.class);
                        startActivity(in);

                       // Toast.makeText(LoginActivity.this, "Status" + response.body().getStatus(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<RegisteredModel> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Status not Found", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void setFocusableInput(View view)
    {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    public void getFacebookLogin()
    {
        List< String > permissionNeeds;
        permissionNeeds = Arrays.asList("user_photos", "email",
                "user_birthday", "public_profile", "AccessToken");
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
                public void onSuccess(LoginResult loginResult) {

                    System.out.println("onSuccess");

                    String accessToken = loginResult.getAccessToken().getToken();
                    Log.i("accessToken", accessToken);

                    GraphRequest request = GraphRequest.newMeRequest(
                            loginResult.getAccessToken(),
                            new GraphRequest.GraphJSONObjectCallback() {@Override
                            public void onCompleted(JSONObject object,
                                                    GraphResponse response) {

                                Log.i("LoginActivity",
                                        response.toString());
                                try {
                                    id = object.getString("id");
                                    try {
                                        URL profile_pic = new URL(
                                                "http://graph.facebook.com/" + id + "/picture?type=large");
                                        Log.i("profile_pic",
                                                profile_pic + "");

                                    } catch (MalformedURLException e) {
                                        e.printStackTrace();
                                    }

                                    name = object.getString("name");
                                    emailfb = object.getString("email");
                                    publicProfile = object.getString("public_profile");
                                    sex = object.getString("gender");
                                   /*profilePic = object.getString("profile_pic");

                                    Bitmap immage = image;
                                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                    immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
                                    byte[] b = baos.toByteArray();
                                    String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

                                    Log.d("Image Log:", imageEncoded);
                                    return imageEncoded;*/

                                    MySharedData.setGeneralSaveSession("email",emailfb);
                                    MySharedData.setGeneralSaveSession("name",name);
                                    MySharedData.setGeneralSaveSession("gender",sex);
                                    MySharedData.setGeneralSaveSession("profile_pic",profilePic);
                                    MySharedData.setGeneralSaveSession("public_profile",publicProfile);


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            });
                Intent fbIn = new Intent(LoginActivity.this, HomePage.class);
                startActivity(fbIn);
                finish();
                    Bundle parameters = new Bundle();
                    parameters.putString("fields",
                            "id,name,email,gender, birthday");
                    request.setParameters(parameters);
                    request.executeAsync();
                }
                    @Override
                    public void onCancel() {
                        System.out.println("onCancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        System.out.println("onError");
                        Log.v("LoginActivity", exception.getCause().toString());
                    }

                });
    }
    @Override
    protected void onActivityResult(int requestCode, int responseCode,
                                    Intent data) {
        super.onActivityResult(requestCode, responseCode, data);
        callbackManager.onActivityResult(requestCode, responseCode, data);
    }

}
