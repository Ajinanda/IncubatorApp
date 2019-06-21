package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_form);
        setTitle("Form Profile Baru");
        Button buttonSubmitProfile = (Button) findViewById(R.id.buttonSubmitProfile);

        final EditText formName = (EditText) findViewById(R.id.formName);
        final EditText formMinTemp = (EditText) findViewById(R.id.formMinTemp);
        final EditText formMaxTemp = (EditText) findViewById(R.id.formMaxTemp);
        final EditText formMinMoist = (EditText) findViewById(R.id.formMinMoist);
        final EditText formTimeIncubation = (EditText) findViewById(R.id.formTimeIncubation);
        final EditText formTimeRotation = (EditText) findViewById(R.id.formTimeRotation);
        final EditText formRotationCycle = (EditText) findViewById(R.id.formRotationCycle);



        databaseReference = FirebaseDatabase.getInstance().getReference();
        buttonSubmitProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    final String name = formName.getText().toString();
                    String minTemp = formMinTemp.getText().toString();
                    String maxTemp = formMaxTemp.getText().toString();
                    String minMoist = formMinMoist.getText().toString();
                    String timeIncubation = formTimeIncubation.getText().toString();
                    String timeRotation = formTimeRotation.getText().toString();
                    String rotationCycle = formRotationCycle.getText().toString();
                    final int minTempInt = Integer.valueOf(minTemp);
                    final int maxTempInt = Integer.valueOf(maxTemp);
                    final int minMoistInt = Integer.valueOf(minMoist);
                    final int timeIncubationInt = Integer.valueOf(timeIncubation);
                    final int timeRotationInt = Integer.valueOf(timeRotation);
                    final int rotationCycleInt = Integer.valueOf(rotationCycle);

                    ProfileData newProfile = new ProfileData(name, minTempInt, maxTempInt, minMoistInt, timeIncubationInt, timeRotationInt, rotationCycleInt);
                    databaseReference.child("Profile").child(newProfile.name).setValue(newProfile);
                    Intent startIntent = new Intent(getApplicationContext(), ProfileActivity.class);
                    startActivity(startIntent);
                } catch (Exception e){

                }

            }
        });
    }
    /*public void addProfile(View view){
        ProfileData newProfile = new ProfileData(name, minTempInt, maxTempInt, minMoistInt, timeIncubationInt, timeRotationInt, rotationCycleInt);
        databaseReference.child(newProfile.name).setValue(newProfile);
    }*/
}
