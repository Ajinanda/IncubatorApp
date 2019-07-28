package com.example.myapplication2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
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
    private String berhasilMenetas;
    private String gagalMenetas;

    private ArrayList<String> list = new ArrayList<>();
    private ArrayAdapter<String> aAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

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
                    tanggalSelesai = (String) ds.child("tanggalAkhirInkubasi").getValue();
                    jumlahTelur = (String) ds.child("jumlahTelur").getValue();
                    berhasilMenetas = (String) ds.child("menetas").getValue();
                    gagalMenetas = (String) ds.child("gagal").getValue();


                    TextView a = (TextView) findViewById(R.id.a);
                    a.setText(namaInkubasi);

                    Log.i("Test Output", "Result Activity : "+namaInkubasi+"/"+jenisUnggas+"/"+tanggalMulai+"/"+tanggalSelesai+"/"+jumlahTelur+"/"+berhasilMenetas+"/"+gagalMenetas+"/");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }


    public void init(){


        resultTable = (TableLayout) findViewById(R.id.resultTable);
        tbRow = new TableRow(this);
        tv0 = new TextView(this);
        tv0.setText("Nama");
        tbRow.addView(tv0);
        tv1 = new TextView(this);
        tv1.setText("Unggas");
        tbRow.addView(tv1);
        tv2 = new TextView(this);
        tv2.setText("Mulai");
        tbRow.addView(tv2);
        tv3 = new TextView(this);
        tv3.setText("Selesai");
        tbRow.addView(tv3);
        tv4 = new TextView(this);
        tv4.setText("Telur");
        tbRow.addView(tv4);
        tv5 = new TextView(this);
        tv5.setText("Menetas");
        tbRow.addView(tv5);
        tv6 = new TextView(this);
        tv6.setText("Gagal");
        tbRow.addView(tv6);


    }
}
