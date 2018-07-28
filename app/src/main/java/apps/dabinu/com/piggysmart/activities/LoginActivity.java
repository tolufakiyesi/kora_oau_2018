package apps.dabinu.com.piggysmart.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import apps.dabinu.com.piggysmart.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.submitCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((EditText) findViewById(R.id.email)).getText().toString().trim().equals("")){
                    ((EditText) findViewById(R.id.email)).setError("This field is required");
                    Toast.makeText(getApplicationContext(), "THis field is required", Toast.LENGTH_LONG).show();
                }
                else if(((EditText) findViewById(R.id.password)).getText().toString().trim().equals("")){
                    ((EditText) findViewById(R.id.password)).setError("This field is required");
                    Toast.makeText(getApplicationContext(), "THis field is required", Toast.LENGTH_LONG).show();
                }
                else{
                    findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
                    //log in with Firebase
                }
            }
        });


        findViewById(R.id.signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignupActivity.class));
            }
        });
    }


    @Override
    public void onBackPressed() {

    }
}