package apps.dabinu.com.piggysmart.activities;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

import apps.dabinu.com.piggysmart.R;
import apps.dabinu.com.piggysmart.activities.HomeActivity;

public class SplashActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mAuth = FirebaseAuth.getInstance();

        new CountDownTimer(4000, 1000){
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if(mAuth.getCurrentUser() == null){
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
                else{
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                }
            }
        }.start();
    }
}