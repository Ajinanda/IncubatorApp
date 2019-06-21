package com.example.myapplication2;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

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

    EditText tanggalInkubasiEditText;
    Calendar c;
    DatePickerDialog dpd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incubation_form);
        setTitle("Form Inkubasi Baru");

        EditText formNamaInkubasi = (EditText) findViewById(R.id.formNamaInkubasi);
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

        EditText formJumlahTelur = (EditText) findViewById(R.id.formJumlahTelur);

        String namaInkubasi = formNamaInkubasi.getText().toString();
        String tanggalInkubasi = tanggalInkubasiEditText.getText().toString();
        String jenisUnggas = "";
        String jumlahTelur = formJumlahTelur.getText().toString();

        /*Button untuk memulai proses inkubasi*/
        Button buttonStartIncubation = (Button) findViewById(R.id.buttonStartIncubation);
        buttonStartIncubation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(startIntent);
            }
        });
        /*Button*/
    }
}
