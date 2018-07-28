package apps.dabinu.com.piggysmart.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import apps.dabinu.com.piggysmart.R;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });


        findViewById(R.id.signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(((EditText) findViewById(R.id.usernameText)).getText().toString().trim().equals("")){
                    ((EditText) findViewById(R.id.usernameText)).setError("This field is required");
                    Toast.makeText(getApplicationContext(), "THis field is required", Toast.LENGTH_LONG).show();
                }
                else if(((EditText) findViewById(R.id.emailText)).getText().toString().trim().equals("")){
                    ((EditText) findViewById(R.id.emailText)).setError("This field is required");
                    Toast.makeText(getApplicationContext(), "THis field is required", Toast.LENGTH_LONG).show();
                }
                else if(((EditText) findViewById(R.id.phoneNumberText)).getText().toString().trim().equals("")){
                    ((EditText) findViewById(R.id.phoneNumberText)).setError("This field is required");
                    Toast.makeText(getApplicationContext(), "THis field is required", Toast.LENGTH_LONG).show();
                }
                else if(((EditText) findViewById(R.id.passwordText)).getText().toString().trim().equals("")){
                    ((EditText) findViewById(R.id.passwordText)).setError("This field is required");
                    Toast.makeText(getApplicationContext(), "THis field is required", Toast.LENGTH_LONG).show();
                }
                else{
                    findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
                    //sign up with Firebase
                }
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
