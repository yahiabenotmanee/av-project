package com.monstertechno.moderndashbord;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.HashMap;
import java.util.Map;

public class Vehicle_Activity extends AppCompatActivity {

    EditText vehNumber,vehcode,vehname;
    Button Qrbtn,addvehBtn;
    TextView textGenQRcode;
    
    DatabaseReference reff;
    Vehicle vehicle;

    long maxid=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);

        /*
         reff= FirebaseDatabase.getInstance().getReference().child("Vehicle").child("2");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                String code =snapshot.child("code").getValue().toString();
                String matriculea =snapshot.child("matricule").getValue().toString();
                String name =snapshot.child("name").getValue().toString();

                 vehcode.setHint(code);
                vehname.setHint(name);
                vehNumber.setHint(matricule);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
       */

        //initializa variables
        //edit texts
        vehcode=findViewById(R.id.edtVEHnumber);
        vehname=findViewById(R.id.edtVEHName);
        vehNumber=findViewById(R.id.edtVEHMatricul);
        reff= FirebaseDatabase.getInstance().getReference().child("Vehicle").child("2");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                String code =snapshot.child("code").getValue().toString();
                String matriculea =snapshot.child("matricule").getValue().toString();
                String name =snapshot.child("name").getValue().toString();

                vehcode.setHint(code);
                vehname.setHint(name);
                vehNumber.setHint(matriculea);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //buttons
        Qrbtn=findViewById(R.id.buttonqr);
        addvehBtn=findViewById(R.id.buttonADDveh);
        //textviews
        textGenQRcode=findViewById(R.id.textGENvehQr);
        //
        String txtcode = vehcode.getText().toString().trim();
        String txtNumber = vehNumber.getText().toString().trim();
        String txtname = vehname.getText().toString().trim();
        //
        vehicle = new Vehicle();
        //
        reff=FirebaseDatabase.getInstance().getReference().child("Vehicle");

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()){
                maxid=(snapshot.getChildrenCount());
            }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //click to save
        addvehBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                vehicle.setName(vehname.getText().toString().trim());
                vehicle.setCode(vehcode.getText().toString().trim());
                vehicle.setMatricule(vehNumber.getText().toString().trim());

               // reff.push().setValue(vehicle);
                reff.child(String.valueOf(maxid+1)).setValue(vehicle);
                Toast.makeText(Vehicle_Activity.this,"Data inser successfuly ",Toast.LENGTH_LONG).show();

                vehcode.setHint(vehcode.getText());
                vehname.setHint(vehname.getText());
                vehNumber.setHint(vehNumber.getText());


                vehcode.setText(null);
                vehname.setText(null);
                vehNumber.setText(null);


            }
        });
        //click to generat QR code
        textGenQRcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get text from number code edit text
                String scode = vehcode.getHint().toString().trim();


                Intent intent = new Intent(Vehicle_Activity.this,QRCode_Activity.class);
               intent.putExtra("code",scode);
                startActivity(intent);
            }
        });

        // Enter to scan with qr code
        Qrbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // initializ intent integrator
                IntentIntegrator intentIntegrator =new IntentIntegrator(Vehicle_Activity.this);
                //set prompt text
                intentIntegrator.setPrompt("for flash use volume up key");
                //set beep
                intentIntegrator.setBeepEnabled(true);
                //locked oriontaion
                intentIntegrator.setOrientationLocked(true);
                //set capture activity
                intentIntegrator.setCaptureActivity(Capture.class);
                //inital scan
                intentIntegrator.initiateScan();

            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //initalize intent result
        IntentResult intentResult = IntentIntegrator.parseActivityResult(
                requestCode,resultCode,data
        );
        //check condition
        if (intentResult.getContents() != null){
            //when result content is not null
            //initialze alert dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    Vehicle_Activity.this
            );
            builder.setTitle("Result");
            //set message
            builder.setMessage(intentResult.getContents());
           vehcode.setText(intentResult.getContents());

            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //dismiss dialog
                    dialogInterface.dismiss();
                }
            });
            // show alert dialog
            builder.show();
        }else{
            //when result content is null
            //display toast
            Toast.makeText(getApplicationContext(), "OOPS ...You did not scan anything ", Toast.LENGTH_SHORT).show();

        }
    }
}