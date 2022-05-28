package com.monstertechno.moderndashbord;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

public class Alarm_Activity extends AppCompatActivity {
    ImageView alarmgif;
     Timer timergif;
     TextView textView1,textView2,textView3;

     DatabaseReference reff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        textView1=findViewById(R.id.textView6);
        textView2=findViewById(R.id.textView7);
        textView3=findViewById(R.id.textView8);



        reff= FirebaseDatabase.getInstance().getReference().child("Vehicle").child("2");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                String code =snapshot.child("code").getValue().toString();
                String matriculea =snapshot.child("matricule").getValue().toString();
                String name =snapshot.child("name").getValue().toString();

                textView1.setText(name);
                textView2.setText(code);
                textView3.setText(matriculea);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        alarmgif=findViewById(R.id.alarmGIF);
//        timergif = new Timer();
//        timergif.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                alarmgif.setVisibility(View.INVISIBLE);
//            }
//        }, 3000);

    }
}