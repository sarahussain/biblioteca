package com.example.sara.biblio;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class prenotazione extends Activity implements OnItemSelectedListener{

    ConnectionClass connectionClass;
    EditText username;
    Button b2;
    ResultSet rs,rt;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prenotazione);
        connectionClass = new ConnectionClass();
        // Spinner element
        b2= (Button)findViewById(R.id.button2);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);



        try {
            Connection con = connectionClass.CONN();
            if (con == null) {
                System.out.println("Errore nella connessione");
            } else {
                List titoli = new ArrayList();

                String query = "select * from libri";
                Statement st = con.createStatement();
                rs = st.executeQuery(query);
                while(rs.next()){

                    String [] id = {rs.getString("titolo")} ;

                    titoli.add(Arrays.toString(id));

                }

                ArrayAdapter NoCoreAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, titoli);
                NoCoreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // attaching data adapter to spinner
                spinner.setAdapter(NoCoreAdapter);
            }
        }catch (SQLException e)
        {
            e.printStackTrace();
        }



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        final String item = parent.getItemAtPosition(position).toString().replace("[", "").replace("\"", "").replace("]", "");
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    username = (EditText) findViewById(R.id.editText5);
                    String user = username.getText().toString();
                    int id_libro=0,id_utente=0;
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        System.out.println("Errore nella connessione");
                    } else {

                        String quer ="select * from libri where titolo='" + item + "'";
                        Statement sp = con.createStatement();
                        rs = sp.executeQuery(quer);
                        if(rs.next()){
                            id_libro = Integer.parseInt(rs.getString("id").toString());
                            String que = "select * from utenti where username='"+user+"'";
                            Statement s=con.createStatement();
                            rt=s.executeQuery(que);
                            if(rt.next()) {
                                id_utente = Integer.parseInt(rt.getString("id").toString());
                                String query = "Insert into prenotazione(id_libro,id_utente,data)" + "values('" + id_libro + "','"+id_utente+"',GETDATE())";
                                Statement st = con.createStatement();
                                int rt = st.executeUpdate(query);
                                if (rt > 0) {

                                    setContentView(R.layout.prenotazion);
                                }
                            }
                        }
                        else{
                            System.out.println("Errore nell'insrire dati");
                        }


                    }
                }catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        });




}
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}
