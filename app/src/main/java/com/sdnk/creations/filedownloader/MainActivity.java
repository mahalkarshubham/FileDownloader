package com.sdnk.creations.filedownloader;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 100;
    public static final String imageURL = "https://notes-apps.000webhostapp.com/android/demoImage.png";
    String imageName = "demoImage.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // storage runtime permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
            }
        }

        Button btnDownloadImage = findViewById(R.id.btnDownloadImage);
        btnDownloadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadImage(imageURL, imageName);
            }
        });
    }

    public void downloadImage(String url, String outputFileName) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle(imageName);
        request.setDescription("Downloading " + imageName);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.allowScanningByMediaScanner();
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, outputFileName);
        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }
}
