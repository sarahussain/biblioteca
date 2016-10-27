package com.example.sara.biblio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class main_page extends AppCompatActivity {

    Button catalogo,prenota,feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        catalogo = (Button)findViewById(R.id.catalogo);
        prenota = (Button)findViewById(R.id.prenota);
        feedback=(Button)findViewById(R.id.feedback);

        catalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent admin = new Intent(main_page.this, Catalogo.class);
                startActivity(admin);
            }
        });

        prenota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent admin = new Intent(main_page.this, prenotazione.class);
                startActivity(admin);
            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent admin = new Intent(main_page.this,feedback.class);
                startActivity(admin);
            }
        });
    }
}
