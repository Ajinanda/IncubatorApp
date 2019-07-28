package com.example.myapplication2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class EditAndDeleteInkubasi extends AppCompatActivity {

    private FirebaseDatabase mDatabaseProfile;
    private DatabaseReference myRef, myRef2;
    private ListView mListView;
    private String name;

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
    private Button buttonEditIncubation;
    Calendar c;
    DatePickerDialog dpd;
    TimePickerDialog tpd;
    String namaInkubasi, jenisUnggas;
    long jumlahTelur, masaInkubasi, masaMembalikTelur,
            minTemp, maxTemp, moist;
    long[][] jadwal = new long[3][2];
    long[][] tanggalPembalikan = new long[2][3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_and_delete_inkubasi);
        //name = getIntent().getStringExtra("name");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mDatabaseProfile = FirebaseDatabase.getInstance();
        myRef = mDatabaseProfile.getReference().child("CONTROLLING");


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //showData(dataSnapshot);
                namaInkubasi = (String) dataSnapshot.child("Inkubasi").child("namaInkubasi").getValue();
                jenisUnggas = (String) dataSnapshot.child("Inkubasi").child("unggas").getValue();
                jumlahTelur = (long) dataSnapshot.child("Inkubasi").child("jumlahTelur").getValue();
                masaInkubasi = (long) dataSnapshot.child("Inkubasi").child("timeIncubation").getValue();
                masaMembalikTelur = (long) dataSnapshot.child("Inkubasi").child("timeRotation").getValue();
                minTemp = (long) dataSnapshot.child("Atursuhu").child("minsuhu").getValue();
                maxTemp = (long) dataSnapshot.child("Atursuhu").child("maxsuhu").getValue();
                moist = (long) dataSnapshot.child("Atursuhu").child("minlembab").getValue();
                jadwal[0][0] = (long) dataSnapshot.child("RTC").child("jadwal1").child("jam").getValue();
                jadwal[0][1] = (long) dataSnapshot.child("RTC").child("jadwal1").child("menit").getValue();
                jadwal[1][0] = (long) dataSnapshot.child("RTC").child("jadwal2").child("jam").getValue();
                jadwal[1][1] = (long) dataSnapshot.child("RTC").child("jadwal2").child("menit").getValue();
                jadwal[2][0] = (long) dataSnapshot.child("RTC").child("jadwal3").child("jam").getValue();
                jadwal[2][1] = (long) dataSnapshot.child("RTC").child("jadwal3").child("menit").getValue();
                tanggalPembalikan[0][0] = (long) dataSnapshot.child("RTC").child("tgl1").child("tanggal").getValue();
                tanggalPembalikan[0][1] = (long) dataSnapshot.child("RTC").child("tgl1").child("bulan").getValue();
                tanggalPembalikan[0][2] = (long) dataSnapshot.child("RTC").child("tgl1").child("tahun").getValue();
                tanggalPembalikan[1][0] = (long) dataSnapshot.child("RTC").child("tgl2").child("tanggal").getValue();
                tanggalPembalikan[1][1] = (long) dataSnapshot.child("RTC").child("tgl2").child("bulan").getValue();
                tanggalPembalikan[1][2] = (long) dataSnapshot.child("RTC").child("tgl2").child("tahun").getValue();

                String jadwal1, jadwal2, jadwal3, tanggalAwal, tanggalAkhir;
                jadwal1 = jadwal[0][0]+":"+jadwal[0][1];
                jadwal2 = jadwal[1][0]+":"+jadwal[1][1];
                jadwal3 = jadwal[2][0]+":"+jadwal[2][1];
                tanggalAwal = tanggalPembalikan[0][0]+"/"+tanggalPembalikan[0][1]+"/"+tanggalPembalikan[0][2];
                tanggalAkhir = tanggalPembalikan[1][0]+"/"+tanggalPembalikan[1][1]+"/"+tanggalPembalikan[1][2];


                namaInkubasiEditText.setText(namaInkubasi);
                jenisUnggasEditText.setText(jenisUnggas);
                jumlahTelurEditText.setText(String.valueOf(jumlahTelur));
                masaInkubasiEditText.setText(String.valueOf(masaInkubasi));
                masaMembalikTelurEditText.setText(String.valueOf(masaMembalikTelur));
                minTempEditText.setText(String.valueOf(minTemp));
                maxTempEditText.setText(String.valueOf(maxTemp));
                moistEditText.setText(String.valueOf(moist));
                jadwalPertamaEditText.setText(jadwal1);
                jadwalKeduaEditText.setText(jadwal2);
                jadwalKetigaEditText.setText(jadwal3);
                tanggalAwalPembalikanEditText.setText(tanggalAwal);
                tanggalAkhirPembalikanEditText.setText(tanggalAkhir);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*Memanggil EditText dari form XML*/
        namaInkubasiEditText = (EditText) findViewById(R.id.editNamaInkubasiEditText);
        jenisUnggasEditText = (EditText) findViewById(R.id.editJenisUnggasEditText);
        jumlahTelurEditText = (EditText) findViewById(R.id.editJumlahTelurEditText);
        masaInkubasiEditText = (EditText) findViewById(R.id.editMasaInkubasiEditText);
        masaMembalikTelurEditText = (EditText) findViewById(R.id.editMasaMembalikTelurEditText);
        minTempEditText = (EditText) findViewById(R.id.editMinTempEditText);
        maxTempEditText = (EditText) findViewById(R.id.editMaxTempEditText);
        moistEditText = (EditText) findViewById(R.id.editMoistEditText);
        jadwalPertamaEditText = (EditText) findViewById(R.id.editJadwalPertamaEditText);
        jadwalKeduaEditText = (EditText) findViewById(R.id.editJadwalKeduaEditText);
        jadwalKetigaEditText = (EditText) findViewById(R.id.editJadwalKetigaEditText);
        tanggalAwalPembalikanEditText = (EditText) findViewById(R.id.editTanggalAwalPembalikanEditText);
        tanggalAkhirPembalikanEditText = (EditText) findViewById(R.id.editTanggalAkhirPembalikanEditText);
        buttonEditIncubation = (Button) findViewById(R.id.buttonEditIncubation);
        /*Memanggil EditText dari form XML*/

        /*memanggil DatePickerDialog dan TimePickerDialog*/
        jadwalPertama();
        jadwalKedua();
        jadwalKetiga();
        tanggalAwalPembalikanDatepicker();
        tanggalAkhirPembalikanDatepicker();
        /*memanggil DatePickerDialog dan TimePickerDialog*/

        buttonEditIncubation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog("Edit Profile","Anda Yakin Ingin Mengedit Profile Ini?", "cancelEdit", "okEdit");
                Log.i("Test1", "onClick: Test On Click Button");
            }
        });

        /*deleteProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog("Delete Profile","Anda Yakin Ingin Menghapus Profile Ini?", "cancelDelete", "okDelete");
            }
        });*/



    }

    private void cancelEdit(){
        toastMessage("Edit Canceled");
    }

    private void cancelDelete(){
        toastMessage("Delete Canceled");
    }

    private void okEdit(){
        try{

            Log.i("Test2", "okEdit: Test Ok Edit Button");
            /*Memasukkan data dari form kedalam variable*/
            namaInkubasi = namaInkubasiEditText.getText().toString();
            jenisUnggas = jenisUnggasEditText.getText().toString();
            jumlahTelur = Long.valueOf(jumlahTelurEditText.getText().toString());
            masaInkubasi = Long.valueOf(masaInkubasiEditText.getText().toString());
            masaMembalikTelur = Long.valueOf(masaMembalikTelurEditText.getText().toString());
            minTemp = Long.valueOf(minTempEditText.getText().toString());
            maxTemp = Long.valueOf(maxTempEditText.getText().toString());
            moist = Long.valueOf(moistEditText.getText().toString());
            /*Memasukkan data dari form kedalam variable*/

            Log.i("Test3", "okEdit: Test Getting edittext value into Variable");
            IncubationData startIncubation = new IncubationData(namaInkubasi, jenisUnggas, jumlahTelur, masaInkubasi, masaMembalikTelur, minTemp, maxTemp, moist, jadwal, tanggalPembalikan);
            myRef.child("Atursuhu").updateChildren(startIncubation.atursuhuMap());
            myRef.child("Inkubasi").updateChildren(startIncubation.inkubasiMap());
            myRef.child("RTC").updateChildren(startIncubation.rtcMap());

            Log.i("Test4", "okEdit: Test to make sure data got sent into constructor");
            Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(startIntent);
            toastMessage("Incubation Data Edited");
        } catch (Exception e){

        }
    }

    private void okDelete(){
        try {
            //myRef.child("Profile").child(name).removeValue();
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




    public void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    /*Timepicker Untuk Memasukkan Jadwal Pertama Pemutaran Telur*/
    private void jadwalPertama(){
        jadwalPertamaEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c= Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                tpd = new TimePickerDialog(EditAndDeleteInkubasi.this, new TimePickerDialog.OnTimeSetListener() {
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

                tpd = new TimePickerDialog(EditAndDeleteInkubasi.this, new TimePickerDialog.OnTimeSetListener() {
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

                tpd = new TimePickerDialog(EditAndDeleteInkubasi.this, new TimePickerDialog.OnTimeSetListener() {
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

                dpd = new DatePickerDialog(EditAndDeleteInkubasi.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        int m =month+1;
                        tanggalAwalPembalikanEditText.setText(dayOfMonth + "/"+m+"/"+year);
                        tanggalPembalikan[0][0]= dayOfMonth;
                        tanggalPembalikan[0][1]= month+1;
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

                dpd = new DatePickerDialog(EditAndDeleteInkubasi.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        int m =month+1;
                        tanggalAkhirPembalikanEditText.setText(dayOfMonth + "/"+m+"/"+year);
                        tanggalPembalikan[1][0]= dayOfMonth;
                        tanggalPembalikan[1][1]= month+1;
                        tanggalPembalikan[1][2]= year;
                    }
                }, year, month, day);
                dpd.show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), CompleteIncubation.class);
        startActivityForResult(myIntent,0);
        return true;
    }
}
