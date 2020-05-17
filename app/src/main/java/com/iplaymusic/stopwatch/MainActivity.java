package com.iplaymusic.stopwatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private   Button butStart,butStop,butRest;
    private TextView tvCounter;
    private int seconds;
    private int minutes;
    private int hours;
    private boolean isRunning=false;
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null)
        {
            seconds=savedInstanceState.getInt("seconds");
            minutes=savedInstanceState.getInt("min");
            hours=savedInstanceState.getInt("hr");
            isRunning=savedInstanceState.getBoolean("boolean");
            wasRunning=savedInstanceState.getBoolean("wasRunning");


        }

        setContentView(R.layout.activity_main);
        intWidgets();
        onClick();
        runTimer();


    }

    private void runTimer()
    {
        final Handler handler=new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(seconds==60)
                {
                    seconds=0;
                    minutes++;
                    if(minutes==60)
                    {
                        minutes=0;
                        hours++;


                    }
                }

                String time = String.format("%d:%02d:%02d",
                        hours, minutes, seconds);
                tvCounter.setText(time);
                if (isRunning) {
                    seconds++;
                }
                handler.postDelayed(this,1000);
            }
        });




    }

    private void onClick()
    {
        butStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isRunning=true;

            }
        });

        butStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isRunning==true)
                {
                    isRunning=false;
                    butStop.setText(getString(R.string.butResume));
                }
                else
                {
                    butStop.setText(getString(R.string.butStop));
                    isRunning=true;
                }
            }
        });

        butRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               isRunning=false;
               seconds=0;
               minutes=0;
               hours=0;
            }
        });


    }

    private void intWidgets()
    {
        butStart=(Button)findViewById(R.id.butStart);
        butStop=(Button)findViewById(R.id.butStop);
        butRest=(Button)findViewById(R.id.butRest);
        tvCounter=(TextView)findViewById(R.id.tvTimer);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putInt("seconds",seconds);
        outState.putInt("min",minutes);
        outState.putInt("hr",hours);
        outState.putBoolean("boolean",isRunning);
        outState.putBoolean("wasRunning",wasRunning);

    }
/*

    @Override
    protected void onStop() {
        super.onStop();
        wasRunning=isRunning;
        isRunning=false;
    }
*/

  /*  @Override
    protected void onStart() {
        super.onStart();

        if(wasRunning)
        {
            isRunning=true;
        }
    }*/

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning=isRunning;
        isRunning=false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(wasRunning)
        { isRunning=true;}

    }
}
