package sssemil.com.samplelogcatapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tv = (TextView)findViewById(R.id.textView);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Runtime rt = Runtime.getRuntime();
                    //here you can add your params
                    String[] commands = {"logcat"};
                    Process proc = rt.exec(commands);

                    BufferedReader stdInput = new BufferedReader(new
                            InputStreamReader(proc.getInputStream()));

                    BufferedReader stdError = new BufferedReader(new
                            InputStreamReader(proc.getErrorStream()));

                    String s;
                    while ((s = stdInput.readLine()) != null) {
                        //here is your logcat
                        //Log.i("logcat", s);
                        final String finalS = s;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv.append(finalS + "\n");
                            }
                        });
                    }

                    while ((s = stdError.readLine()) != null) {
                        //Log.e("logcat", s);
                        final String finalS = s;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv.append(finalS + "\n");
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
