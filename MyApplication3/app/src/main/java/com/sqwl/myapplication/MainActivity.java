package com.sqwl.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

public class MainActivity extends AppCompatActivity {
    private TextView tvload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        tvload = findViewById(R.id.tv_load);
        requestPermission(this);
        initData();
    }

    private void initData() {
        tvload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download();
            }

        });
    }
    private void download() {
        String url = "http://47.111.13.148:9001/wushuang/data/data.zip";
        final String savePath = Environment.getExternalStorageDirectory().getAbsolutePath() +"/com.game.rpg/data.zip";
        FileDownloader.getImpl().create(url)
                .setPath(savePath)
                .setForceReDownload(true)
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        tvload.setText(soFarBytes+"/"+totalBytes);
                    }

                    @Override
                    protected void blockComplete(BaseDownloadTask task) {
                    }

                    @Override
                    protected void retry(final BaseDownloadTask task, final Throwable ex, final int retryingTimes, final int soFarBytes) {
                    }

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    protected void completed(BaseDownloadTask task) {
                        tvload.setText("下载完成");
                        unPackage(savePath,Environment.getExternalStorageDirectory().getAbsolutePath() + "/com.game.rpg");
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                    }
                }).start();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void unPackage(String zipPath, String outputDir){
        try {
            ZipUtils.UnZipFolder(zipPath,outputDir);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //设备API大于6.0时，主动申请权限
    private void requestPermission(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, 0);

            }
        }
    }
}