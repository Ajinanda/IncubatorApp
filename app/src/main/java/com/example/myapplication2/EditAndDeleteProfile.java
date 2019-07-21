package com.example.myapplication2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class EditAndDeleteProfile extends AppCompatActivity {

    private FirebaseDatabase mDatabaseProfile;
    private DatabaseReference myRef;
    private ListView mListView;
    private String name;

    private EditText nameEditText;
    private EditText minTempEditText;
    private EditText maxTempEditText;
    private EditText moistEditText;
    private EditText timeIncubationEditText;
    private EditText timeRotationEditText;
    private EditText rotationCycleEditText;
    private Button editProfileButton;
    private Button deleteProfileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_and_delete_profile);
        name = getIntent().getStringExtra("name");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        nameEditText = (EditText) findViewById(R.id.formNameEdit);
        minTempEditText = (EditText) findViewById(R.id.formMinTempEdit);
        maxTempEditText = (EditText) findViewById(R.id.formMaxTempEdit);
        moistEditText = (EditText) findViewById(R.id.formMinMoistEdit);
        timeIncubationEditText = (EditText) findViewById(R.id.formTimeIncubationEdit);
        timeRotationEditText = (EditText) findViewById(R.id.formTimeRotationEdit);
        rotationCycleEditText = (EditText) findViewById(R.id.formRotationCycleEdit);
        editProfileButton = (Button) findViewById(R.id.editProfileButton);
        deleteProfileButton = (Button) findViewById(R.id.deleteProfileButton);

        mDatabaseProfile = FirebaseDatabase.getInstance();
        myRef = mDatabaseProfile.getReference();
        Query testRef = myRef.child("Profile").orderByChild("name").equalTo(name);

        testRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog("Edit Profile","Anda Yakin Ingin Mengedit Profile Ini?", "cancelEdit", "okEdit");
            }
        });

        deleteProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog("Delete Profile","Anda Yakin Ingin Menghapus Profile Ini?", "cancelDelete", "okDelete");
            }
        });



    }

    private void cancelEdit(){
        toastMessage("Edit Canceled");
    }

    private void cancelDelete(){
        toastMessage("Delete Canceled");
    }

    private void okEdit(){
        int minTemp = Integer.valueOf(minTempEditText.getText().toString());
        int maxTemp = Integer.valueOf(maxTempEditText.getText().toString());
        int moist = Integer.valueOf(moistEditText.getText().toString());
        int timeIncubation = Integer.valueOf(timeIncubationEditText.getText().toString());
        int timeRotation = Integer.valueOf(timeRotationEditText.getText().toString());
        int rotationCycle = Integer.valueOf(rotationCycleEditText.getText().toString());
        try {
            myRef.child("Profile").child(name).child("minTemp").setValue(minTemp);
            myRef.child("Profile").child(name).child("maxTemp").setValue(maxTemp);
            myRef.child("Profile").child(name).child("minMoist").setValue(moist);
            myRef.child("Profile").child(name).child("timeIncubation").setValue(timeIncubation);
            myRef.child("Profile").child(name).child("timeRotation").setValue(timeRotation);
            myRef.child("Profile").child(name).child("rotationCycle").setValue(rotationCycle);

            Intent startIntent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(startIntent);
            toastMessage("Profile Edited");
        } catch (Exception e){

        }
    }

    private void okDelete(){
        try {
            myRef.child("Profile").child(name).removeValue();
            Intent startIntent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(startIntent);
            toastMessage("Profile Deleted");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     *
     * @param title
     * @param message
     * @param cancelMethod
     * @param okMethod
     */
    public void customDialog(String title, String message, final String cancelMethod, final String okMethod){
        final AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
        builderSingle.setTitle(title);
        builderSingle.setMessage(message);

        builderSingle.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (cancelMethod.equals("cancelEdit")){
                            cancelEdit();
                        }
                        else if (cancelMethod.equals("cancelDelete")){
                            cancelDelete();
                        }
                    }
                }
        );

        builderSingle.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if(okMethod.equals("okEdit")){
                            okEdit();
                        }
                        else if(okMethod.equals("okDelete")){
                            okDelete();
                        }
                    }
                });


        builderSingle.show();
    }

    private void showData(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()){
            String nama = String.valueOf(ds.getValue(ProfileData.class).getNama());
            String minTemp = String.valueOf(ds.getValue(ProfileData.class).getMinTemp());
            String maxTemp = String.valueOf(ds.getValue(ProfileData.class).getMaxTemp());
            String moist = String.valueOf(ds.getValue(ProfileData.class).getMinMoist());
            String timeIncubation = String.valueOf(ds.getValue(ProfileData.class).getTimeIncubation());
            String timeRotation = String.valueOf(ds.getValue(ProfileData.class).getTimeRotation());
            String rotationCycle = String.valueOf(ds.getValue(ProfileData.class).getRotationCycle());

            nameEditText.setText(nama);
            minTempEditText.setText(minTemp);
            maxTempEditText.setText(maxTemp);
            moistEditText.setText(moist);
            timeIncubationEditText.setText(timeIncubation);
            timeRotationEditText.setText(timeRotation);
            rotationCycleEditText.setText(rotationCycle);
        }

    }

    public void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivityForResult(myIntent,0);
        return true;
    }
}
