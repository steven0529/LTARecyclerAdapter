package com.chuck.ltactionrecycleradapter;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    public static final int PERMISSION_ACCESS_PHOTO = 100;

    public static final int REQUEST_CODE_CAMERA = 1;
    public static final int REQUEST_CODE_GALLERY = 2;

    private RecyclerView recyclerView;
    private String imageCameraPath;
    private List<String> pathsList;
    private ImagesRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        pathsList = new ArrayList<>();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        adapter = new ImagesRecyclerAdapter(this, pathsList, 3);
        adapter.setItemOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeImageOnClick(v, R.menu.menu_has_image_options);
            }
        });
        adapter.setActionOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeImageOnClick(v, R.menu.menu_add_image_options);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    public void throwGalleryIntent() {
        Intent galleryIntent =
                new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, REQUEST_CODE_GALLERY);
    }

    public void throwCameraIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File fileAppDir = new File (Environment.getExternalStorageDirectory(),
                    getString(R.string.app_name));
            if(!fileAppDir.exists())
                fileAppDir.mkdirs();

            File photoFile = null;
            try {
                String imageFileName = "temp";
                photoFile = File.createTempFile(
                        imageFileName,  /* prefix */
                        ".jpg",         /* suffix */
                        fileAppDir      /* directory */
                );
                imageCameraPath = photoFile.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // Continue only if the File was successfully created
            // for Nougat support
            // https://inthecheesefactory.com/blog/how-to-share-access-to-file-with-fileprovider-on-android-nougat/en
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        BuildConfig.APPLICATION_ID + ".provider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        photoURI);
                startActivityForResult(takePictureIntent, REQUEST_CODE_CAMERA);
            }
        }
    }

    public void askPhotoPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PERMISSION_ACCESS_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_GALLERY
                && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String stringSelcted = selectedImage.toString();
            pathsList.add(stringSelcted);
            adapter.notifyDataSetChanged();
        } else if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK) {
            pathsList.add(imageCameraPath);
            adapter.notifyDataSetChanged();
        }
    }

    private void storeImageOnClick(View v, int resId) {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            PopupMenu popup = new PopupMenu(MainActivity.this, v);
            /** Adding menu items to the popumenu */
            popup.getMenuInflater().inflate(resId, popup.getMenu());
            /** Defining menu item click listener for the popup menu */
            popup.setOnMenuItemClickListener(MainActivity.this);
            /** Showing the popup menu */
            popup.show();
        } else
            askPhotoPermission();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.gallery) {
            throwGalleryIntent();
        } else if (id == R.id.camera) {
            throwCameraIntent();
        } else if (id == R.id.remove) {
            Toast.makeText(this, "Remove image", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
