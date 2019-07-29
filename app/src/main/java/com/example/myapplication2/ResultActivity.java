package com.example.myapplication2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    private FirebaseDatabase mDatabase;
    private DatabaseReference myRef;
    private TableLayout resultTable;
    private TableRow tbRow;
    private TextView tv0;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private TextView tv6;
    private String namaInkubasi;
    private String jenisUnggas;
    private String tanggalMulai;
    private String tanggalSelesai;
    private String jumlahTelur;
    private String waktuInkubasi;
    private String berhasilMenetas;
    private String gagalMenetas;

    private ArrayList<String> list = new ArrayList<>();
    private ArrayAdapter<String> aAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        setTitle("Riwayat Inkubasi");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        mDatabase = FirebaseDatabase.getInstance();
        myRef = mDatabase.getReference();


        resultTable = (TableLayout) findViewById(R.id.resultTable);

        Query query = myRef.child("Result");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){

                    namaInkubasi = (String) ds.child("namaInkubasi").getValue();
                    jenisUnggas = (String) ds.child("jenisUnggas").getValue();
                    tanggalMulai = (String) ds.child("tanggalMulaiInkubasi").getValue();
                    waktuInkubasi = (String) ds.child("masaInkubasi").getValue();
                    jumlahTelur = (String) ds.child("jumlahTelur").getValue();
                    //berhasilMenetas = (String) ds.child("menetas").getValue();
                   // gagalMenetas = (String) ds.child("gagal").getValue();

                    tbRow = new TableRow(getApplicationContext());
                    tv0 = new TextView(getApplicationContext());
                    tv0.setText(namaInkubasi);
                    tv0.setTextColor(Color.BLACK);
                    tv0.setGravity(Gravity.LEFT);
                    tv0.setPadding(0,0,20,0);
                    tbRow.addView(tv0);
                    tv1 = new TextView(getApplicationContext());
                    tv1.setText(jenisUnggas);
                    tv1.setTextColor(Color.BLACK);
                    tv1.setGravity(Gravity.LEFT);
                    tv1.setPadding(0,0,20,0);
                    tbRow.addView(tv1);
                    tv2 = new TextView(getApplicationContext());
                    tv2.setText(tanggalMulai);
                    tv2.setTextColor(Color.BLACK);
                    tv2.setGravity(Gravity.LEFT);
                    tv2.setPadding(0,0,20,0);
                    tbRow.addView(tv2);
                    tv3 = new TextView(getApplicationContext());
                    tv3.setText(waktuInkubasi);
                    tv3.setTextColor(Color.BLACK);
                    tv3.setGravity(Gravity.RIGHT);
                    tv3.setPadding(0,0,20,0);
                    tbRow.addView(tv3);
                    tv4 = new TextView(getApplicationContext());
                    tv4.setText(jumlahTelur);
                    tv4.setTextColor(Color.BLACK);
                    tv4.setGravity(Gravity.RIGHT);
                    tv4.setPadding(0,0,20,0);
                    tbRow.addView(tv4);
                    /*tv5 = new TextView(getApplicationContext());
                    tv5.setText("Menetas");
                    tbRow.addView(tv5);
                    tv6 = new TextView(getApplicationContext());
                    tv6.setText("Gagal");
                    tbRow.addView(tv6);*/
                    resultTable.addView(tbRow);

                    Log.i("Test Output", "Result Activity : "+namaInkubasi+"/"+jenisUnggas+"/"+tanggalMulai+"/"+tanggalSelesai+"/"+jumlahTelur);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(),MainActivity.class);
        startActivityForResult(myIntent,0);
        return true;
    }

}
