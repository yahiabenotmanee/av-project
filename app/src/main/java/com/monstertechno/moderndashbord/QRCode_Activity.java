package com.monstertechno.moderndashbord;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QRCode_Activity extends AppCompatActivity {
    //Initialize variable
    Button btGenerat;
    EditText etInput;
    ImageView ivOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        //Assign variable
        //Button
        btGenerat=findViewById(R.id.bt_generat);
        etInput=findViewById(R.id.et_input);
        ivOutput=findViewById(R.id.iv_output);

        Intent intent =getIntent();
        String CODEQR = intent.getStringExtra("code");
        etInput.setText(CODEQR);

        // button on click
        btGenerat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get input value from edittext
                String stext = etInput.getText().toString().toString();
                //initoialize multi format writer
                MultiFormatWriter writer =new MultiFormatWriter();

                try {
                    //initialize bit matrix
                    BitMatrix matrix = writer.encode(stext, BarcodeFormat.QR_CODE
                    ,350,350);
                    //initializ barcode incoder
                    BarcodeEncoder encoder = new BarcodeEncoder();
                    //initializ bitmap
                    Bitmap bitmap = encoder.createBitmap(matrix);
                    //set bitmao on iamge view
                    ivOutput.setImageBitmap(bitmap);
                    //initalize input manager
                    InputMethodManager manager = (InputMethodManager) getSystemService(
                            Context.INPUT_METHOD_SERVICE
                    );
                    //hide soft keyboard
                    manager.hideSoftInputFromWindow(etInput.getApplicationWindowToken(),
                            0);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}