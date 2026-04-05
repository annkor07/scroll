package com.example.scroll;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
public class MainActivity extends AppCompatActivity {

    boolean isRunning = false;
    Handler handler = new Handler();
    int speed = 5;
    ScrollView scrollView;
    EditText etTempo;
    Button btnStartStop;
    Button btnFiles;
    Button btnColorMode;
    Button btnGoUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        scrollView = findViewById(R.id.scrollView);
        btnFiles = findViewById(R.id.btnFiles);
        btnStartStop = findViewById(R.id.btnStartStop);
        btnColorMode = findViewById(R.id.btnColorMode);
        btnGoUp = findViewById(R.id.goUp);
        etTempo = findViewById(R.id.etTempo);

        btnGoUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isRunning = false;
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(View.FOCUS_UP);
                    }
                });
            }
        });

        btnStartStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isRunning){
                    isRunning = true;
                    btnStartStop.setText("Stop");
                    String tempo = etTempo.getText().toString();
                    if (!tempo.isEmpty()) {
                        speed = Integer.parseInt(tempo);
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (isRunning) {
                                scrollView.scrollBy(0, speed);
                                handler.postDelayed(this, 16);
                                if (!scrollView.canScrollVertically(1)) {
                                    isRunning = false;
                                    btnStartStop.setText("Start");
                                }
                            }
                        }
                    });
                }else{
                    isRunning = false;
                }

            }
        });
    }


}