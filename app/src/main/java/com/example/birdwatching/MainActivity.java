package com.example.birdwatching;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextBird, editTextZipCode, editTextName;
    Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Report");

        editTextBird = findViewById(R.id.editTextBird);
        editTextZipCode = findViewById(R.id.editTextZipCode);
        editTextName = findViewById(R.id.editTextName);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        buttonSubmit.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("birdsightings");

        if (v == buttonSubmit){

            String createBirdName = editTextBird.getText().toString();
            Integer createZipCode = Integer.parseInt(editTextZipCode.getText().toString());
            String createName = editTextName.getText().toString();

            BirdSighting createBirdSighting =new BirdSighting(createBirdName, createZipCode, createName);

            myRef.push().setValue(createBirdSighting);



        }

    }



    //Make menu show up - alt-insert to add onCreateOptionsMenu override
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater(); //declare object
        inflater.inflate(R.menu.mainmenu, menu); //inflate menu in activity
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.itemReport) {
            Toast.makeText(this, "You are already on the Report page", Toast.LENGTH_SHORT).show();

        } else if (item.getItemId() == R.id.itemSearch){

            Intent itemSearchIntent = new Intent(this, Search.class);
            startActivity(itemSearchIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}
