package com.example.sara.biblio;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

    public class Catalogo extends AppCompatActivity {
        GridView gridview;
        ArrayList<String> arrayList;

        ConnectionClass connectionClass;
        PreparedStatement stmt;
        ResultSet rs;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.gridviews);

            connectionClass = new ConnectionClass();
            gridview = (GridView) findViewById(R.id.gridview);

            String query = "select * from libri";



            try {

                Connection con = connectionClass.CONN();
                Statement statement = con.createStatement();
                rs = statement.executeQuery(query);
                List<Map<String, String>> data = null;
                data = new ArrayList<Map<String, String>>();

                while (rs.next()) {
                    Map<String, String> datanum = new HashMap<String, String>();
                    datanum.put("A", rs.getString("id"));
                    datanum.put("B", rs.getString("titolo"));
                    datanum.put("C", rs.getString("autore"));
                    datanum.put("D", rs.getString("isbn"));
                    datanum.put("E",rs.getString("disponibilità"));

                    data.add(datanum);
                }
                String[] from = {"A", "B" ,"C","D","E"};
                int[] views = {R.id.txtlibri,R.id.txttitolo, R.id.txtautore ,R.id.txtisbn,R.id.txtdisp};
                final SimpleAdapter ADA = new SimpleAdapter(Catalogo.this,
                        data, R.layout.templateforgrid, from, views);
                gridview.setAdapter(ADA);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

