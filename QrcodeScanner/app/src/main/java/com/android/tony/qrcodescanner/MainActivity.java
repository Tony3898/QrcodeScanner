package com.android.tony.qrcodescanner;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.blikoon.qrcodescanner.QrCodeActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    Button scanButton;
    TextToSpeech textToSpeech;
    String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scanButton = findViewById(R.id.scanbutton);
        textToSpeech = new TextToSpeech(this,this);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, QrCodeActivity.class),101);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101 && resultCode == RESULT_OK && data!=null)
        {
             result = data.getStringExtra("com.blikoon.qrcodescanner.got_qr_scan_relult");
            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
            textToSpeech.speak(result,TextToSpeech.QUEUE_FLUSH,null,"speech");
        }
    }

    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS)
        {
            int res = textToSpeech.setLanguage(Locale.US);
            float i =50;
            if(res == TextToSpeech.LANG_MISSING_DATA || res == TextToSpeech.LANG_NOT_SUPPORTED)
                Toast.makeText(getApplicationContext(),"Language not supported",Toast.LENGTH_SHORT).show();
            else
            {
                textToSpeech.speak(result,TextToSpeech.QUEUE_FLUSH,null,"speech");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(textToSpeech!=null)
        {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }
}
