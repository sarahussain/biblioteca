package com.example.sara.biblio;

/**
 * Created by sara on 22/10/2016.
 */

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionClass {
    String classs = "net.sourceforge.jtds.jdbc.Driver";

    @SuppressLint("NewApi")
    public Connection CONN() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL = null;
        try {
            Class.forName(classs);
             ConnURL = "jdbc:jtds:sqlserver://192.168.1.64:1433/libreria;instance=SQLEXPRESS;user=app2;password=ciao";
             // ConnURL = "jdbc:jtds:sqlserver://155.185.79.93:1433/libreria;instance=SQLEXPRESS;user=app2;password=ciao";
            //ConnURL = "jdbc:jtds:sqlserver://155.185.73.54:1433/libreria;instance=SQLEXPRESS;user=app2;password=ciao";
            //ConnURL = "jdbc:jtds:sqlserver:// 155.185.79.100:1433/libreria;instance=SQLEXPRESS;user=app2;password=ciao";

            conn = DriverManager.getConnection(ConnURL);
        } catch (SQLException se) {
            Log.e("ERRO", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("ERRO", e.getMessage());
        } catch (Exception e) {
            Log.e("ERRO", e.getMessage());
        }
        return conn;
    }

}

