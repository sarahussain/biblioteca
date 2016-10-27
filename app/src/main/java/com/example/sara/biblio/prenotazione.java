package com.example.sara.biblio;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class prenotazione extends AppCompatActivity {

    ConnectionClass connectionClass;
    EditText aut,lib;
    Button prenota;
    ProgressBar pbar;
    ResultSet rs;
    int id =0;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_prenotazione);



            connectionClass = new ConnectionClass();
            prenota = (Button) findViewById(R.id.button);
            aut = (EditText)findViewById(R.id.autore);
            lib = (EditText)findViewById(R.id.titolo);
            pbar = (ProgressBar) findViewById(R.id.progressBar);


            prenota.setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick(View v) {
                        prenotazione.DoLogin doLogin = new prenotazione.DoLogin();
                        doLogin.execute("");

                    }
                });

            }

        public class DoLogin extends AsyncTask<String,String,String>
        {
            String z = "";
            Boolean isSuccess = false;


            String autore = aut.getText().toString();
            String titolo = lib.getText().toString();


            @Override
            protected void onPreExecute() {
                pbar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String r) {
                pbar.setVisibility(View.GONE);
                Toast.makeText(prenotazione.this,r,Toast.LENGTH_SHORT).show();

                if(isSuccess) {
                    setContentView(R.layout.prenotazion);
                }

            }

            @Override
            protected String doInBackground(String... params) {
                if(autore.trim().equals("")|| titolo.trim().equals(""))
                    z = "Per favore inserisci User Id e Password";
                else
                {
                    try {
                        Connection con = connectionClass.CONN();
                        if (con == null) {
                            z = "Errore nella connessione con SQL server";
                        } else {
                            String query = "select * from libri where titolo='" + titolo+ "' and autore='" + autore+ "'";
                            Statement statement = con.createStatement();
                            rs = statement.executeQuery(query);

                            if (rs.next()){

                                id = Integer.parseInt(rs.getString("id").toString());
                                String query_1 = "insert into prenotazione(id_libro)" + "values ('"+ id +"')";
                                Statement st = con.createStatement();
                                int rt = st.executeUpdate(query_1);

                                if(rt > 0){

                                    z= "Prenotato con successo";
                                    isSuccess=true;

                                }


                                else{
                                    z="Insuccesso";
                                    isSuccess=false;
                                }
                            }


                            else
                            {
                                z = "Insuccesso";
                                isSuccess = false;
                            }

                        }
                    }
                    catch (Exception ex)
                    {
                        isSuccess = false;
                        z = "Exceptions";
                    }
                }
                return z;
            }
        }
    }


