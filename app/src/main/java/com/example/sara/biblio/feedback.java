package com.example.sara.biblio;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class feedback extends AppCompatActivity {

    ConnectionClass connectionClass;
    EditText titl,user;
    Button invia;
    ProgressBar pbar;
    RatingBar rbar;
    ResultSet rs,rl,rt;
    EditText icom;
    int id = 0,id_libro = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(getApplication());
        setContentView(R.layout.activity_feedback);
        listenerForRatingBar();

        connectionClass = new ConnectionClass();
        user = (EditText) findViewById(R.id.user);
        invia = (Button) findViewById(R.id.invia);
        icom = (EditText) findViewById(R.id.commento);
        ShareButton fbShareButton = (ShareButton) findViewById(R.id.share_btn);
        pbar = (ProgressBar) findViewById(R.id.progressBar2);
        rbar = (RatingBar) findViewById(R.id.ratingBar);
        LayerDrawable stars= (LayerDrawable)rbar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.parseColor("#32CD32"), PorterDuff.Mode.SRC_ATOP);
        rbar.setRating(3.67f);


        invia.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                feedback.DoLogin doLogin = new feedback.DoLogin();
                doLogin.execute("");


            }
        });


        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://www.facebook.com/appbiblio/?fref=ts"))
                .build();
        fbShareButton.setShareContent(content);

    }

    public void listenerForRatingBar () {

        rbar = (RatingBar) findViewById(R.id.ratingBar);
        rbar.setOnRatingBarChangeListener(
                new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    }
                }
        );
    }

    public class DoLogin extends AsyncTask<String, String, String>
    {
        String z = "";
        Boolean isSuccess = false;



        String username = user.getText().toString();
        String icommento = icom.getText().toString();
        Float ratingBar = rbar.getRating();

        @Override
        protected void onPreExecute() {
            pbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String r) {
            pbar.setVisibility(View.GONE);
            Toast.makeText(feedback.this, r, Toast.LENGTH_SHORT).show();

            if (isSuccess) {
                setContentView(R.layout.feedback);
            }

        }

        @Override
        protected String doInBackground(String... params)
        {
            if (username.trim().equals("") )
                z = "Per favore inserisci username";
            else
            {
                if ( icommento.trim().equals(""))
                    z="Per favore lascia un commento";
                else {
                    try {
                        Connection con = connectionClass.CONN();
                        if (con == null) {
                            z = "Errore nella connessione con SQL server";
                        } else {

                            String que= "select * from utenti where username='" + username + "'";
                            Statement r = con.createStatement();
                            rl = r.executeQuery(que);
                            if(rl.next()){
                                id= Integer.parseInt(rl.getString("id").toString());
                            }

                            String query = "select * from prenotazione where id_utente = '"+id+"' and data = (select max(data)from prenotazione)";
                            Statement statement = con.createStatement();
                            rs = statement.executeQuery(query);

                            if (rs.next()) {
                                id_libro = Integer.parseInt(rs.getString("id_libro").toString());
                                String quer = "select * from utenti where username='" + username + "'";
                                Statement st = con.createStatement();
                                rt = st.executeQuery(quer);

                                if (rt.next()) {
                                    String query_1 = "insert into feedback (id_libro,id_utente,commento,ratings)" + "values ('" + id_libro + "','" + id + "','" + icommento + "', '" + ratingBar + "')";
                                    Statement s = con.createStatement();
                                    int rt = s.executeUpdate(query_1);


                                    if (rt > 0) {

                                        z = "Inviato con successo";
                                        isSuccess = true;

                                    } else {
                                        z = "Insuccesso";
                                        isSuccess = false;
                                    }

                                } else {
                                    z = "Insuccesso";
                                    isSuccess = false;
                                }
                            }else {
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
            return z;
        }
    }
}
