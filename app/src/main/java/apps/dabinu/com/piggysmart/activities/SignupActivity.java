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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import apps.dabinu.com.piggysmart.R;
import apps.dabinu.com.piggysmart.models.UserModel;

public class SignupActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();


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
                    mAuth.createUserWithEmailAndPassword(((EditText) findViewById(R.id.emailText)).getText().toString().trim(), ((EditText) findViewById(R.id.passwordText)).getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                databaseReference.child("users").setValue(new UserModel(((EditText) findViewById(R.id.usernameText)).getText().toString().trim(), ((EditText) findViewById(R.id.phoneNumberText)).getText().toString().trim(), ((EditText) findViewById(R.id.emailText)).getText().toString().trim())).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
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
                            else{
                                findViewById(R.id.progressBar).setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
