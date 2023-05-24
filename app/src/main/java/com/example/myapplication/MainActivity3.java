package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.glxn.qrgen.android.QRCode;

import java.util.Arrays;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        TextView textView = findViewById(R.id.textview);
        EditText editText = findViewById(R.id.edittext);
        ImageView imageView = findViewById(R.id.qrcode);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = editText.getText().toString();
                if(text.equals("")){
                    Toast.makeText(MainActivity3.this, "입력하세요", Toast.LENGTH_SHORT).show();
                } else {
                    byte[] bytes = text.getBytes();
                    String qr_string = Arrays.toString(bytes);
                    Bitmap bitmap = QRCode.from(qr_string).withColor(0XFF000000, 0X00000000).bitmap();
                    imageView.setImageBitmap(bitmap);
                    textView.setText(text);
                }
            }
        });
    }
}