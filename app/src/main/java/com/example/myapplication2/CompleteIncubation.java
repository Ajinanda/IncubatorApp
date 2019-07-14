package com.example.myapplication2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_incubation);

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
    }

    private void showData(DataSnapshot dataSnapshot) {

        for(DataSnapshot ds : dataSnapshot.getChildren()){
            IncubationData id = new IncubationData();


        }
    }


}
