package com.example.myapplication2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
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
    private FirebaseDatabase mDatabase;
    private DatabaseReference myRef;
    private EditText namaInkubasiEditText;
    private EditText jenisUnggasEditText;
    private EditText jumlahTelurEditText;
    private EditText masaInkubasiEditText;
    private EditText masaMembalikTelurEditText;
    private EditText siklusPembalikanTelurEditText;
    private EditText minTempEditText;
    private EditText maxTempEditText;
    private EditText moistEditText;
    private EditText jadwalPertamaEditText;
    private EditText jadwalKeduaEditText;
    private EditText jadwalKetigaEditText;
    private EditText tanggalAwalPembalikanEditText;
    private EditText tanggalAkhirPembalikanEditText;
    Calendar c;
    DatePickerDialog dpd;
    TimePickerDialog tpd;
    String namaInkubasi, jenisUnggas;
    int jumlahTelur, masaInkubasi, masaMembalikTelur, siklusPembalikanTelur,
            minTemp, maxTemp, moist;
    int[][] jadwal = new int[3][2];
    int[][] tanggalPembalikan = new int[2][3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incubation_form);
        setTitle("Form Inkubasi Baru");
        mDatabase = FirebaseDatabase.getInstance();
        myRef = mDatabase.getReference().child("CONTROLLING");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        /*Memanggil EditText dari form XML*/
        namaInkubasiEditText = (EditText) findViewById(R.id.namaInkubasiEditText);
        jenisUnggasEditText = (EditText) findViewById(R.id.jenisUnggasEditText);
        jumlahTelurEditText = (EditText) findViewById(R.id.jumlahTelurEditText);
        masaInkubasiEditText = (EditText) findViewById(R.id.masaInkubasiEditText);
        masaMembalikTelurEditText = (EditText) findViewById(R.id.masaMembalikTelurEditText);
        siklusPembalikanTelurEditText = (EditText) findViewById(R.id.siklusPembalikanTelurEditText);
        minTempEditText = (EditText) findViewById(R.id.minTempEditText);
        maxTempEditText = (EditText) findViewById(R.id.maxTempEditText);
        moistEditText = (EditText) findViewById(R.id.moistEditText);
        jadwalPertamaEditText = (EditText) findViewById(R.id.jadwalPertamaEditText);
        jadwalKeduaEditText = (EditText) findViewById(R.id.jadwalKeduaEditText);
        jadwalKetigaEditText = (EditText) findViewById(R.id.jadwalKetigaEditText);
        tanggalAwalPembalikanEditText = (EditText) findViewById(R.id.tanggalAwalPembalikanEditText);
        tanggalAkhirPembalikanEditText = (EditText) findViewById(R.id.tanggalAkhirPembalikanEditText);
        /*Memanggil EditText dari form XML*/

        /*memanggil DatePickerDialog dan TimePickerDialog*/
        jadwalPertama();
        jadwalKedua();
        jadwalKetiga();
        tanggalAwalPembalikanDatepicker();
        tanggalAkhirPembalikanDatepicker();
        /*memanggil DatePickerDialog dan TimePickerDialog*/


        /*DatePickerDialog
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
        });*/



        /*Button untuk memulai proses inkubasi*/
        Button buttonStartIncubation = (Button) findViewById(R.id.buttonStartIncubation);
        buttonStartIncubation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    /*Memasukkan data dari form kedalam variable*/
                    namaInkubasi = namaInkubasiEditText.getText().toString();
                    jenisUnggas = jenisUnggasEditText.getText().toString();
                    jumlahTelur = Integer.valueOf(jumlahTelurEditText.getText().toString());
                    masaInkubasi = Integer.valueOf(masaInkubasiEditText.getText().toString());
                    masaMembalikTelur = Integer.valueOf(masaMembalikTelurEditText.getText().toString());
                    siklusPembalikanTelur = Integer.valueOf(siklusPembalikanTelurEditText.getText().toString());
                    minTemp = Integer.valueOf(minTempEditText.getText().toString());
                    maxTemp = Integer.valueOf(maxTempEditText.getText().toString());
                    moist = Integer.valueOf(moistEditText.getText().toString());
                    /*Memasukkan data dari form kedalam variable*/

                    IncubationData startIncubation = new IncubationData(namaInkubasi, jenisUnggas, jumlahTelur, masaInkubasi, masaMembalikTelur, siklusPembalikanTelur, minTemp, maxTemp, moist, jadwal, tanggalPembalikan);
                    myRef.child("Atursuhu").updateChildren(startIncubation.atursuhuMap());
                    myRef.child("Inkubasi").updateChildren(startIncubation.inkubasiMap());
                    myRef.child("RTC").updateChildren(startIncubation.rtcMap());
                    Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(startIntent);
                } catch(Exception e) {

                }

            }
        });
        /*Button*/
    }

    /*Timepicker Untuk Memasukkan Jadwal Pertama Pemutaran Telur*/
    private void jadwalPertama(){
        jadwalPertamaEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c= Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                tpd = new TimePickerDialog(IncubationForm.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        jadwalPertamaEditText.setText(hourOfDay + ":" + minute);
                        jadwal[0][0] = hourOfDay;
                        jadwal[0][1] = minute;
                    }
                }, hour, minute, true);
                tpd.show();
            }
        });
    }

    /*Timepicker Untuk Memasukkan Jadwal Kedua Pemutaran Telur*/
    private void jadwalKedua(){
        jadwalKeduaEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c= Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                tpd = new TimePickerDialog(IncubationForm.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        jadwalKeduaEditText.setText(hourOfDay + ":" + minute);
                        jadwal[1][0] = hourOfDay;
                        jadwal[1][1] = minute;
                    }
                }, hour, minute, true);
                tpd.show();
            }
        });
    }

    /*Timepicker Untuk Memasukkan Jadwal Ketiga Pemutaran Telur*/
    private void jadwalKetiga(){
        jadwalKetigaEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c= Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                tpd = new TimePickerDialog(IncubationForm.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        jadwalKetigaEditText.setText(hourOfDay + ":" + minute);
                        jadwal[2][0] = hourOfDay;
                        jadwal[2][1] = minute;
                    }
                }, hour, minute, true);
                tpd.show();
            }
        });
    }

    /*DatePickerDialog Untuk Memasukkan Tanggal Awal Pembalikan Telur*/
    private void tanggalAwalPembalikanDatepicker(){
        tanggalAwalPembalikanEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c= Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                dpd = new DatePickerDialog(IncubationForm.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tanggalAwalPembalikanEditText.setText(dayOfMonth + "/"+month+"/"+year);
                        tanggalPembalikan[0][0]= dayOfMonth;
                        tanggalPembalikan[0][1]= month;
                        tanggalPembalikan[0][2]= year;
                    }
                }, year, month, day);
                dpd.show();
            }
        });
    }

    /*DatePickerDialog Untuk Memasukkan Tanggal Akhir Pembalikan Telur*/
    private void tanggalAkhirPembalikanDatepicker(){
        tanggalAkhirPembalikanEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c= Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                dpd = new DatePickerDialog(IncubationForm.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tanggalAkhirPembalikanEditText.setText(dayOfMonth + "/"+month+"/"+year);
                        tanggalPembalikan[1][0]= dayOfMonth;
                        tanggalPembalikan[1][1]= month;
                        tanggalPembalikan[1][2]= year;
                    }
                }, year, month, day);
                dpd.show();
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
