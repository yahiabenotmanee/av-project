package com.monstertechno.moderndashbord;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class Splash extends AppCompatActivity {
    private FirebaseAuth mauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mauth= FirebaseAuth.getInstance();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//startActivity(new Intent(Splash.this,StartActivity.class));
//finish();

                if(mauth.getCurrentUser() != null){
                    startActivity(new Intent(Splash.this,Fingerprint_Activity.class));
                }else{
                    startActivity(new Intent(Splash.this,StartActivity.class));
                }
                finish();
            }
        },1000);
    }
}