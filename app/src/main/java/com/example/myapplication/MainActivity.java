package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class MainActivity extends AppCompatActivity {
    int type = 1; //qr code
    boolean color = false; //흑백
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.textview);
        ImageView imageView = findViewById(R.id.qrcode);
        EditText editText = findViewById(R.id.edittext);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = editText.getText().toString();
                if(text.equals("")){
                    Toast.makeText(MainActivity.this, "입력해주세요", Toast.LENGTH_SHORT).show();
                }else{
                    Bitmap bitmap;
                    if(type == 1) {
                        QRCodeWriter qrCodeWriter = new QRCodeWriter();
                        try {
                            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 1000, 500);
                            if (color) {
                                bitmap = toBitmap(bitMatrix);
                            } else {
                                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                                bitmap = barcodeEncoder.createBitmap(bitMatrix);
                            }
                            imageView.setImageBitmap(bitmap);
                            textView.setText(text);
                        } catch (WriterException e) {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                        BitMatrix bitMatrix = null;
                        try {
                            bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.CODE_128, 800, 400);
                        } catch (WriterException e) {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        if (color) {
                            int codeColor = 0XFF0000FF;
                            int backgroundColor = 0XFFFFFFFF;
                            bitmap = Bitmap.createBitmap(800,400,Bitmap.Config.ARGB_8888);
                            for (int i = 0; i < 800; i++)
                                for(int j = 0; j < 400; j++)
                                    bitmap.setPixel(i, j, bitMatrix.get(i,j) ? codeColor : backgroundColor);
                        } else {
                            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                            bitmap = barcodeEncoder.createBitmap(bitMatrix);
                        }
                        imageView.setImageBitmap(bitmap);
                        textView.setText(text);
                    }
                }
            }
        });
    }

    private Bitmap toBitmap(BitMatrix bitMatrix) {
        int height = bitMatrix.getHeight();
        int width = bitMatrix.getWidth();
        int qrcodeColor = 0XFF0000FF;
        int backgroundColor = 0XFFFFFFFF;
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        for(int x = 0; x < width; x++)
            for(int y = 0; y < height; y++)
                bmp.setPixel(x, y, bitMatrix.get(x, y) ? qrcodeColor : backgroundColor);
        return bmp;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                type = 1;
                if(color){
                    button.setText("Generate QRCode (Color)");
                } else {
                    button.setText("Generate QRCode");
                }
                item.setChecked(true);
                break;
            case R.id.item2:
                type = 2;
                if(color){
                    button.setText("Generate BarCode (Color)");
                } else {
                    button.setText("Generate BarCode");
                }
                item.setChecked(true);
                break;
            case R.id.item_color:
                if(!color) {
                    item.setChecked(true);
                    button.setText(button.getText() + "(Color)");
                } else {
                    item.setChecked(false);
                    button.setText(button.getText().subSequence(0,17));
                }
                color = !color;
                break;
        }
        return true;
    }
}