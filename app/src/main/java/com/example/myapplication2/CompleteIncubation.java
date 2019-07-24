package com.example.myapplication2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CompleteIncubation extends AppCompatActivity {
    private FirebaseDatabase mDatabaseInkubasi;
    private DatabaseReference myRef;
    private ListView lView;
    private static final String TAG = "ViewDatabase";
    private Button completeIncubationButton;
    private Button editScreenButton;
    public String nama, namaInkubasi, jenisUnggas, tanggalInkubasi;
    public long jumlahTelur, masaInkubasi, masaMembalikTelur, siklusPembalikanTelur,
            minTemp, maxTemp, moist, temp;
    public long[][] jadwal = new long[3][2];
    public long[][] tanggalPembalikan = new long[2][3];
    String[] data;
    String[] labelData = {"Nama Inkubasi", "Nama Unggas", "Tanggal Mulai"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_incubation);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        lView = findViewById(R.id.mListView);
        nama = getIntent().getStringExtra("namaInkubasi");
        mDatabaseInkubasi = FirebaseDatabase.getInstance();
        myRef = mDatabaseInkubasi.getReference();


        /*myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                namaInkubasi = (String) dataSnapshot.child("CONTROLLING").child("Inkubasi").child("namaInkubasi").getValue();
                jenisUnggas = (String) dataSnapshot.child("CONTROLLING").child("Inkubasi").child("unggas").getValue();
                tanggalPembalikan[0][0] = (long) dataSnapshot.child("CONTROLLING").child("RTC").child("tgl1").child("tanggal").getValue();
                tanggalPembalikan[0][1] = (long) dataSnapshot.child("CONTROLLING").child("RTC").child("tgl1").child("bulan").getValue();
                tanggalPembalikan[0][2] = (long) dataSnapshot.child("CONTROLLING").child("RTC").child("tgl1").child("tahun").getValue();
                tanggalInkubasi = tanggalPembalikan[0][0]+"/"+tanggalPembalikan[0][1]+"/"+tanggalPembalikan[0][2];

                data[0] = namaInkubasi;
                data[1] = jenisUnggas;
                data[2] = tanggalInkubasi;

                Log.i("TEST CHILD LISTENER", "TEST"+namaInkubasi+", "+jenisUnggas+", "+tanggalInkubasi);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        /*CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), data, labelData);
        lView.setAdapter(customAdapter);*/

        editScreenButton = findViewById(R.id.editScreenButton);
        editScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editScreen = new Intent(getApplicationContext(), EditAndDeleteInkubasi.class);
                startActivity(editScreen);
            }
        });

        completeIncubationButton = findViewById(R.id.completeIncubationButton);

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
