package com.example.sherman.sbook.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;


import com.example.sherman.sbook.R;
import com.example.sherman.sbook.models.Book;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by VõVân on 10/29/2016.
 */

public class AddBookActivity extends Activity {

    private final int PICK_IMAGE = 1;
    private static final int CAMERA_REQUEST = 1888;

    private EditText txtAuthor;
    private EditText txtPublisher;
    private EditText txtTitle;
    private EditText txtInter;
    private Spinner spiner;
    private com.getbase.floatingactionbutton.FloatingActionButton btnGalley;
    private com.getbase.floatingactionbutton.FloatingActionButton btnCamera;
    private ImageView img;

    private ArrayAdapter<String> adapterSpiner;
    private DatabaseReference database;
    private String txtTags = "";
    private String title = "";
    private String inter = "";
    private String publisher = "";
    private String author = "";
    private String url = "";
    private String owner = "";

    private Button btnSaveBook;
    private Bitmap bitmap = null;
    private StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_book_activity);

        database = FirebaseDatabase.getInstance().getReference();
        storageRef = FirebaseStorage.getInstance().getReference().child("pictủre/" + System.currentTimeMillis());

        btnSaveBook = (Button)findViewById(R.id.btnSaveBook);
        spiner = (Spinner)findViewById(R.id.spiner);
        txtAuthor = (EditText)findViewById(R.id.txtAuthor);
        txtPublisher = (EditText) findViewById(R.id.txtPublisher);
        txtTitle = (EditText)findViewById(R.id.txtTitle);
        txtInter = (EditText)findViewById(R.id.txtInterest);

        btnCamera = (com.getbase.floatingactionbutton.FloatingActionButton)findViewById(R.id.btnCamera);
        btnGalley = (com.getbase.floatingactionbutton.FloatingActionButton)findViewById(R.id.btnGalley);

        img = (ImageView)findViewById(R.id.imgBook);

        btnGalley.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallIntent = new Intent(Intent.ACTION_GET_CONTENT);
                gallIntent.setType("image/*");
                startActivityForResult(Intent.createChooser(gallIntent, "Select Picture"), PICK_IMAGE);
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        //class members
        String businessType[] = { "Truyện ngôn tình", "Trinh thám", "Computers", "Education",
                "Tiểu thuyết", "Travel" };

        adapterSpiner = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_singlechoice, businessType);

        spiner.setAdapter(adapterSpiner);

       spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });

        btnSaveBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncTask<Void, Void, Void> loadTask = new AsyncTask<Void, Void, Void>() {
                    ProgressDialog pdLoading = new ProgressDialog(AddBookActivity.this);

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();

                        //this method will be running on UI thread
                        pdLoading.setMessage("\tLoading...");
                        pdLoading.show();

                        // Data init
                        txtTags = (String)spiner.getSelectedItem();
                        author = txtAuthor.getText().toString();
                        publisher = txtPublisher.getText().toString();
                        title = txtTitle.getText().toString();
                        inter = txtInter.getText().toString();
                        owner = getOwner();
                    }
                    @Override
                    protected Void doInBackground(Void... params) {

                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        super.onPostExecute(result);
                        //this method will be running on UI thread
                        Book newBook = new Book(title, url, author, txtTags, publisher, inter, owner);
                        database.child("books").push().setValue(newBook);
                        //pdLoading.dismiss();
                        previewFirebaseImage(pdLoading);
                    }

                };

                loadTask.execute();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                img.setImageBitmap(bitmap);
                storeImageToFirebase(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if(requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK){
            try{
                bitmap = (Bitmap) data.getExtras().get("data");
                img.setImageBitmap(bitmap);
                storeImageToFirebase(bitmap);
            } catch (Exception e){
                e.printStackTrace();
            }

        }
    }

     private synchronized void storeImageToFirebase(Bitmap bitmap) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8; // shrink it down otherwise we will use stupid amounts of memory
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();
       /* String base64Image = Base64.encodeToString(bytes, Base64.DEFAULT);
        // we finally have our base64 string version of the image, save it.
        database.child("pic").setValue(base64Image);
        previewStoredFirebaseImage();*/

         UploadTask uploadTask = storageRef.putBytes(bytes);
         uploadTask.addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception exception) {
                 // Handle unsuccessful uploads
             }
         }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
             @Override
             public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                 // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                 Uri downloadUrl = taskSnapshot.getDownloadUrl();
                 url = downloadUrl.toString();
             }
         });
    }

    private void previewStoredFirebaseImage() {
        database.child("pic").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                url = (String) snapshot.getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    private void previewFirebaseImage(final ProgressDialog pro) {
        database.child("books").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                pro.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    private String getOwner(){
        Random randomGenerator = new Random();
        int randomNum = randomGenerator.nextInt(2) + 1;
        final ArrayList<String> list = new ArrayList<String>();
        list.add("01vKuHSGJTV4Lx9sswhuWc30BC12");
        list.add("NDJah5ROVaRkrunxSQDaEX7h7Ph2");

        return list.get(randomNum);
    }


}
