package com.example.myapplication2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompleteIncubation extends AppCompatActivity {
    private FirebaseDatabase mDatabaseInkubasi;
    private DatabaseReference myRef;
    private ListView mListView;
    private static final String TAG = "ViewDatabase";
    private Button completeIncubationButton;
    private Button editScreenButton;
    public String nama, namaInkubasi, jenisUnggas, tanggalInkubasi, moist, temp;
    public long jumlahTelur, masaMembalikTelur, masaInkubasi, mst,
            minTemp, maxTemp;
    public long[][] jadwal = new long[3][2];
    public long[] tanggalPembalikan = new long[3];
    private TextView dataNama, dataUnggas, dataTanggal, dataJumlahTelur, dataMasaInkubasi, dataTemp, dataMoist;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_incubation);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        final List<Object> dataInkubasi = new ArrayList<>();
        //mListView = findViewById(R.id.mListView);
        nama = getIntent().getStringExtra("namaInkubasi");
        mDatabaseInkubasi = FirebaseDatabase.getInstance();
        myRef = mDatabaseInkubasi.getReference();

        Query query = myRef;
        query.addValueEventListener(new ValueEventListener() {

            @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                namaInkubasi = (String) dataSnapshot.child("CONTROLLING").child("Inkubasi").child("namaInkubasi").getValue();
                jenisUnggas = (String) dataSnapshot.child("CONTROLLING").child("Inkubasi").child("unggas").getValue();
                tanggalPembalikan[0] = (long) dataSnapshot.child("CONTROLLING").child("RTC").child("tgl1").child("tanggal").getValue();
                tanggalPembalikan[1] = (long) dataSnapshot.child("CONTROLLING").child("RTC").child("tgl1").child("bulan").getValue();
                tanggalPembalikan[2] = (long) dataSnapshot.child("CONTROLLING").child("RTC").child("tgl1").child("tahun").getValue();
                tanggalInkubasi = tanggalPembalikan[0]+"/"+tanggalPembalikan[1]+"/"+tanggalPembalikan[2];
                jumlahTelur = (long) dataSnapshot.child("CONTROLLING").child("Inkubasi").child("jumlahTelur").getValue();
                masaInkubasi = (long) dataSnapshot.child("CONTROLLING").child("Inkubasi").child("timeIncubation").getValue();

                dataNama = (TextView) findViewById(R.id.dataNama);
                dataUnggas = (TextView) findViewById(R.id.dataUngas);
                dataTanggal = (TextView) findViewById(R.id.dataTanggal);
                dataJumlahTelur = (TextView) findViewById(R.id.dataJumlahTelur);
                dataMasaInkubasi = (TextView) findViewById(R.id.dataMasaInkubasi);

                dataNama.setText(namaInkubasi);
                dataUnggas.setText(jenisUnggas);
                dataTanggal.setText(tanggalInkubasi);
                dataJumlahTelur.setText(String.valueOf(jumlahTelur));
                dataMasaInkubasi.setText(String.valueOf(masaInkubasi));



           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
        });

        Query cardViewTempRef = myRef.child("MONITORING").child("DHT").child("temperature").orderByKey().limitToLast(1);
        cardViewTempRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Map<String, String> mapTemp = (Map) dataSnapshot.getValue();
                String key = String.valueOf(mapTemp.keySet());
                String sub = key.substring(1, 21);
                temp = mapTemp.get(sub);
                Log.i("TEST", "onDataChange: "+temp);
                dataTemp = (TextView) findViewById(R.id.dataTemp);
                dataTemp.setText(temp);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Query cardViewMoistRef = myRef.child("MONITORING").child("DHT").child("humidity").orderByKey().limitToLast(1);
        cardViewMoistRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, String> mapMoist = (Map) dataSnapshot.getValue();
                String key = String.valueOf(mapMoist.keySet());
                String sub = key.substring(1, 21);
                moist = mapMoist.get(sub);
                Log.i("TEST", "onDataChange: "+moist);
                dataMoist = (TextView) findViewById(R.id.dataMoist);
                dataMoist.setText(moist);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






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
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("d/M/yyyy");

        String nama = (String) dataNama.getText();
        String unggas = (String) dataUnggas.getText();
        String tmInkubasi = (String) dataTanggal.getText();
        String taInkubasi = df.format(c);
        String jTelur = (String) dataJumlahTelur.getText();
        String mInkubasi = (String) dataMasaInkubasi.getText();
        addResult(nama, unggas, tmInkubasi, taInkubasi, jTelur, mInkubasi);

        clearFirebase();

        Intent mainScreen = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainScreen);
        toastMessage("Inkubasi Stopped");
    }

    private void clearFirebase(){
        String ni = "";
        String ju = "";
        long jt = 0;
        long mi = 0;
        long mmt = 0;
        long mit = 0;
        long mat = 0;
        long mst = 0;
        long[][] j = new long[3][2];
        long[][] t = new long[2][3];
        IncubationData startIncubation = new IncubationData(ni, ju, jt, mi, mmt, mit, mat, mst, j, t);
        myRef.child("CONTROLLING").child("Atursuhu").updateChildren(startIncubation.atursuhuMap());
        myRef.child("CONTROLLING").child("Inkubasi").updateChildren(startIncubation.inkubasiMap());
        myRef.child("CONTROLLING").child("RTC").updateChildren(startIncubation.rtcMap());

    }

    private  void addResult(String namaInkubasi, String jenisUnggas, String tanggalMulaiInkubasi, String tanggalAkhirInkubasi,  String jumlahTelur, String masaInkubasi){

        InsertFirebase insert = new InsertFirebase(namaInkubasi,  jenisUnggas,  tanggalMulaiInkubasi,  tanggalAkhirInkubasi, jumlahTelur, masaInkubasi);
        myRef.child("Result").push().setValue(insert);
    }


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
