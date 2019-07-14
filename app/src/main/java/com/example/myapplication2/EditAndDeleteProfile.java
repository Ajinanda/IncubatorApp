package com.example.myapplication2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class EditAndDeleteProfile extends AppCompatActivity {

    private FirebaseDatabase mDatabaseProfile;
    private DatabaseReference myRef;
    private ListView mListView;
    private static final String TAG = "ViewDatabase";
    private String name;

    private EditText nameEditText;
    private EditText minTempEditText;
    private EditText maxTempEditText;
    private EditText moistEditText;
    private EditText timeIncubationEditText;
    private EditText timeRotationEditText;
    private EditText rotationCycleEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_and_delete_profile);
        name = getIntent().getStringExtra("name");

        nameEditText = (EditText) findViewById(R.id.formNameEdit);
        minTempEditText = (EditText) findViewById(R.id.formMinTempEdit);
        maxTempEditText = (EditText) findViewById(R.id.formMaxTempEdit);
        moistEditText = (EditText) findViewById(R.id.formMinMoistEdit);
        timeIncubationEditText = (EditText) findViewById(R.id.formTimeIncubationEdit);
        timeRotationEditText = (EditText) findViewById(R.id.formTimeRotationEdit);
        rotationCycleEditText = (EditText) findViewById(R.id.formRotationCycleEdit);

        mDatabaseProfile = FirebaseDatabase.getInstance();
        myRef = mDatabaseProfile.getReference();
        Query testRef = myRef.child("Profile").orderByChild("name").equalTo(name);

        testRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    String nama = String.valueOf(ds.getValue(ProfileData.class).getNama());
                    String minTemp = String.valueOf(ds.getValue(ProfileData.class).getMinTemp());
                    String maxTemp = String.valueOf(ds.getValue(ProfileData.class).getMaxTemp());
                    String moist = String.valueOf(ds.getValue(ProfileData.class).getMinMoist());
                    String timeIncubation = String.valueOf(ds.getValue(ProfileData.class).getTimeIncubation());
                    String timeRotation = String.valueOf(ds.getValue(ProfileData.class).getTimeRotation());
                    String rotationCycle = String.valueOf(ds.getValue(ProfileData.class).getRotationCycle());

                    //String value = dataSnapshot.getValue(String.class);

                    nameEditText.setText(nama);
                    minTempEditText.setText(minTemp);
                    maxTempEditText.setText(maxTemp);
                    moistEditText.setText(moist);
                    timeIncubationEditText.setText(timeIncubation);
                    timeRotationEditText.setText(timeRotation);
                    rotationCycleEditText.setText(rotationCycle);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
