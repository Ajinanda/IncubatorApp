package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
/**
 *<h1>Layar Form Profil</h1>
 * layar ini dibuat untuk menampilkan form untuk menambah profil unggas baru.
 *
 * @author Ajinanda Nugraha Putra
 * @version 0.1 (alpha)
 * @since 2019-5-27
 */

public class ProfileForm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_form);
        setTitle("Form Profile Baru");
        Button buttonSubmitProfile = (Button) findViewById(R.id.buttonSubmitProfile);
        /*
        EditText formName = (EditText) findViewById(R.id.formName);
        EditText formMinTemp = (EditText) findViewById(R.id.formMinTemp);
        EditText formMaxTemp = (EditText) findViewById(R.id.formMaxTemp);
        EditText formMinMoist = (EditText) findViewById(R.id.formMaxMoist);
        EditText formTimeIncubation = (EditText) findViewById(R.id.formTimeIncubation);
        EditText formTimeRotation = (EditText) findViewById(R.id.formTimeRotation);
        EditText formRotationCycle = (EditText) findViewById(R.id.formRotationCycle);


        String name = formName.getText().toString();
        String minTemp = formMinTemp.getText().toString();
        String maxTemp = formMaxTemp.getText().toString();
        String minMoist = formMinMoist.getText().toString();
        String timeIncubation = formTimeIncubation.getText().toString();
        String timeRotation = formTimeRotation.getText().toString();
        String rotationCycle = formRotationCycle.getText().toString();
        */
        buttonSubmitProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(startIntent);
            }
        });
    }
}
