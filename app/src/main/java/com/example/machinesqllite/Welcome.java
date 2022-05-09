package com.example.machinesqllite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Welcome extends AppCompatActivity {
  ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        img=findViewById(R.id.welcome);
        Animation an= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.alpha);
        img.startAnimation(an);
        Thread t = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(4000);
                    Intent intent  = new Intent(Welcome.this, Login.class);
                    startActivity(intent);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        t.start();
    }
    @Override
    protected void onPause() {
        super.onPause();
        this.finish();
    }
}