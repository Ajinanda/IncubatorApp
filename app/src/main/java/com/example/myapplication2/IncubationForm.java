package com.example.myapplication2;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
/**
 *<h1>Layar Form Inkubasi</h1>
 * layar ini dibuat untuk menampilkan form untuk memulai inkubasi baru.
 *
 * @author Ajinanda Nugraha Putra
 * @version 0.1 (alpha)
 * @since 2019-5-27
 */

public class IncubationForm extends AppCompatActivity {
    private DatabaseReference databaseReference;
    EditText tanggalInkubasiEditText;
    Calendar c;
    DatePickerDialog dpd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incubation_form);
        setTitle("Form Inkubasi Baru");
        databaseReference = FirebaseDatabase.getInstance().getReference();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        final EditText formNamaInkubasi = (EditText) findViewById(R.id.formNamaInkubasi);
        /*DatePickerDialog Untuk Memasukkan Tanggal Mulai Inkubasi*/
        tanggalInkubasiEditText = (EditText) findViewById(R.id.tanggalInkubasiEditText);
        tanggalInkubasiEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c= Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                dpd = new DatePickerDialog(IncubationForm.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tanggalInkubasiEditText.setText(dayOfMonth + "/"+month+"/"+year);
                    }
                }, year, month, day);
                dpd.show();
            }
        });
        /*DatePickerDialog*/
        final Spinner jenisUnggasSpinner = (Spinner) findViewById(R.id.jenisUnggasSpinner);
        databaseReference.child("Profile").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> Profiles = new ArrayList<String>();

                for (DataSnapshot profileSnapshoot: dataSnapshot.getChildren()){
                    String name = profileSnapshoot.child("name").getValue(String.class);
                    Profiles.add(name);
                }

                ArrayAdapter<String> profilesAdapter = new ArrayAdapter<String>(IncubationForm.this, android.R.layout.simple_spinner_item, Profiles);
                profilesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                jenisUnggasSpinner.setAdapter(profilesAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final EditText formJumlahTelur = (EditText) findViewById(R.id.formJumlahTelur);

        /*Button untuk memulai proses inkubasi*/
        Button buttonStartIncubation = (Button) findViewById(R.id.buttonStartIncubation);
        buttonStartIncubation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String namaInkubasi = formNamaInkubasi.getText().toString();
                    String tanggalInkubasi = tanggalInkubasiEditText.getText().toString();
                    String jenisUnggas = jenisUnggasSpinner.getSelectedItem().toString();
                    String jTelur = formJumlahTelur.getText().toString();
                    int jumlahTelur = Integer.valueOf(jTelur);

                    IncubationData newIncubation = new IncubationData(namaInkubasi, tanggalInkubasi, jenisUnggas, jumlahTelur);
                    databaseReference.child("Inkubasi").child(newIncubation.namaInkubasi).setValue(newIncubation);
                    Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(startIntent);
                } catch(Exception e) {

                }

            }
        });
        /*Button*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(),MainActivity.class);
        startActivityForResult(myIntent,0);
        return true;
    }
}
