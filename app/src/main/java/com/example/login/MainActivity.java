package com.example.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 7117;
    List<AuthUI.IdpConfig> lista;
    Button btn_loguear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_loguear = (Button) findViewById(R.id.btn_logear);

        btn_loguear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            AuthUI.getInstance()
                    .signOut(MainActivity.this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            btn_loguear.setEnabled(false);
                            mostraropciones();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

            }
        });



        lista = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build(),

                new AuthUI.IdpConfig.GoogleBuilder().build()

        );
        mostraropciones();
    }
    private void mostraropciones(){
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(lista)
                        .setTheme(R.style.Mytheme)
                .build(),MY_REQUEST_CODE


        );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    if(requestCode == MY_REQUEST_CODE){
        IdpResponse response = IdpResponse.fromResultIntent(data);
        if(resultCode == RESULT_OK){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            Toast.makeText(this, ""+user.getEmail(), Toast.LENGTH_SHORT).show();

            btn_loguear.setEnabled(true);
        }else{

            Toast.makeText(this, ""+response.getError().getMessage(), Toast.LENGTH_SHORT).show();

        }
    }

    }
}
