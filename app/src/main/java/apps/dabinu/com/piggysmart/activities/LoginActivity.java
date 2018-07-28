package apps.dabinu.com.piggysmart.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import apps.dabinu.com.piggysmart.R;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

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
                    mAuth.signInWithEmailAndPassword(((EditText) findViewById(R.id.email)).getText().toString().trim(), ((EditText) findViewById(R.id.password)).getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            }
                            else{
                                findViewById(R.id.progressBar).setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
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