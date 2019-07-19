package com.example.myapplication2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CompleteIncubation extends AppCompatActivity {
    private FirebaseDatabase mDatabaseInkubasi;
    private DatabaseReference myRef;
    private ListView mListView;
    private static final String TAG = "ViewDatabase";
    private String namaInkubasi;
    private Button completeIncubationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_incubation);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mListView = (ListView) findViewById(R.id.mListView);
        namaInkubasi = getIntent().getStringExtra("namaInkubasi");
        mDatabaseInkubasi = FirebaseDatabase.getInstance();
        myRef = mDatabaseInkubasi.getReference();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        completeIncubationButton = (Button) findViewById(R.id.completeIncubationButton);

        completeIncubationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog("Complete Incubation","Anda Yakin Ingin Menghentikan Proses Inkubasi Ini?", "cancelInkubasi", "okInkubasi");
            }
        });
    }

    private void cancelInkubasi(){
        toastMessage("Stop Inkubasi Canceled");
    }

    private void okInkubasi(){
        toastMessage("Inkubasi Stopped");
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
                        if (cancelMethod.equals("cancelInkubasi")){
                            cancelInkubasi();
                        }
                    }
                }
        );

        builderSingle.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if(okMethod.equals("okInkubasi")){
                            okInkubasi();
                        }
                    }
                });


        builderSingle.show();
    }

    private void showData(DataSnapshot dataSnapshot) {

        for(DataSnapshot ds : dataSnapshot.getChildren()){
            IncubationData id = new IncubationData();


        }
    }

    public void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent,0);
        return true;
    }

}
