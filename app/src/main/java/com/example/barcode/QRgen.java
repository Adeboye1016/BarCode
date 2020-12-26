package com.example.barcode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class QRgen extends AppCompatActivity {
    Button generate, save;
    ImageView img;
    EditText text;
OutputStream outputStream;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_rgen);
        ActivityCompat.requestPermissions(QRgen.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        ActivityCompat.requestPermissions(QRgen.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);

        generate = (Button) findViewById(R.id.generate);
        save = (Button) findViewById(R.id.save);
        text = (EditText) findViewById(R.id.textgen);
img= (ImageView) findViewById(R.id.img);
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = text.getText().toString();
                QRGEncoder qrgEncoder = new QRGEncoder(data, null, QRGContents.Type.TEXT, 500);
                try {
                    Bitmap qrBits = qrgEncoder.encodeAsBitmap();
                    img.setImageBitmap(qrBits);

                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
BitmapDrawable drawable=(BitmapDrawable) img.getDrawable();
Bitmap bitmap= drawable.getBitmap();

File filepath= Environment.getExternalStorageDirectory();
File dir= new File(filepath.getAbsolutePath()+"/Barcode/");
dir.mkdir();
File file= new File(dir,System.currentTimeMillis()+".jpg");
try {
    outputStream= new FileOutputStream(file);

} catch (FileNotFoundException e){
    e.printStackTrace();
}
bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
Toast.makeText(getApplicationContext(),"Saved to Gallery", Toast.LENGTH_SHORT).show();
                try {
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

}
