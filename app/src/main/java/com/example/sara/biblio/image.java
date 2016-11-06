package com.example.sara.biblio;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by abc on 13-Jul-15.
 */
public class image extends ActionBarActivity {
    public static final int requestcode = 1;
    ImageView img;
    TextView lblpath;
    Dialog m;
    EditText edtid;
    Button btnfetch;
    ProgressBar pbbar;

    byte[] byteArray;
    String id;

    ResultSet rs;
    ConnectionClass connectionClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        img = (ImageView) findViewById(R.id.imageview);

        lblpath = (TextView) findViewById(R.id.lblpath);
        edtid = (EditText) findViewById(R.id.edtid);
        btnfetch = (Button) findViewById(R.id.btnfetch);
        pbbar = (ProgressBar) findViewById(R.id.pbbar);
        pbbar.setVisibility(View.GONE);


        btnfetch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                id = edtid.getText().toString();
                Fetch f = new Fetch();
                f.execute("");

            }
        });

    }

    private class Fetch extends AsyncTask<String, String, String> {
        String z = " ";

        @Override
        protected void onPreExecute() {
            pbbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub

            String truckstring = "select * from  immagine where id=" + id;

            try {
                Connection con = connectionClass.CONN();
                Statement stmt = con.createStatement();

                rs = stmt.executeQuery(truckstring);
                if (rs != null && rs.next()) {
                    z = rs.getString("Images");

                }

            } catch (Exception e) {
                System.out.print("err");
                e.printStackTrace();
                z = "No such ID";

            }
            return z;
        }

        @Override
        protected void onPostExecute(String rs) {

            byte[] decodeString = Base64.decode(rs, Base64.DEFAULT);
            Bitmap decodebitmap = BitmapFactory.decodeByteArray(decodeString,
                    0, decodeString.length);
            img.setImageBitmap(decodebitmap);
            pbbar.setVisibility(View.GONE);

        }

    }

}
