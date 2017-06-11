package kr.blogspot.httpcarelesssandbox.a170601np;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    static String SERVER_IP="172.17.64.91";
    static int SERVER_PORT=200;
    String msg="";
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et=(EditText)findViewById(R.id.etmsg);

    }

    public void onClick(View v){
        myThread.start();
//        Toast.makeText(getApplicationContext(),"클릭",Toast.LENGTH_SHORT).show();
    }

    Handler myHandler=new Handler();
    Thread myThread=new Thread(){
        @Override
        public void run() {
            try {
                Socket socket = new Socket(SERVER_IP, SERVER_PORT);

                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

                String msg="Client>> "+et.getText().toString();
                outputStream.writeObject(msg);
                outputStream.flush();

                ObjectInputStream inputstream = new ObjectInputStream(socket.getInputStream());
                final String data = (String)inputstream.readObject();

                myHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"Server>> "+ data,Toast.LENGTH_SHORT).show();
                    }
                });
                socket.close();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };
}
