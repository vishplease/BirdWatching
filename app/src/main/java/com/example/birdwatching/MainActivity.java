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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;

    TextView textViewBirdName, textViewUserEmail, textViewZipCode;
    EditText editTextBird, editTextZipCode, editTextName;
    Button buttonSubmit;
    public FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Report Sighting");

        textViewBirdName = findViewById(R.id.textViewBirdName1);
        textViewUserEmail = findViewById(R.id.textViewUserEmail);
        textViewZipCode = findViewById(R.id.textViewZipCode);
        editTextBird = findViewById(R.id.editTextBird);
        editTextZipCode = findViewById(R.id.editTextZipCode);
        editTextName = findViewById(R.id.editTextName);
        editTextName.setText(currentUser.getEmail());
        buttonSubmit = findViewById(R.id.buttonSubmit);

        buttonSubmit.setOnClickListener(this);


        mAuth = FirebaseAuth.getInstance();

    }



    @Override
    public void onClick(View v) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("birdsightings");

        if (v == buttonSubmit){

            if (editTextBird.getText().toString().trim().equalsIgnoreCase("")) {
                editTextBird.setError("This field can not be blank");
            }  else if(editTextZipCode.getText().toString().trim().equalsIgnoreCase("")) {
                editTextName.setError("This field can not be blank");
            }   else {
                String createBirdName = editTextBird.getText().toString();
                String createPersonName = currentUser.getEmail();
                Integer createZipCode;
                Integer createSightingImportance = 0;

                try {
                    createZipCode = Integer.parseInt(editTextZipCode.getText().toString());
                } catch(Exception e) {

                    editTextZipCode.setError("Enter Numeric Values Only");
                    return;
                }

                BirdSighting createBirdSighting = new BirdSighting(createBirdName, createPersonName, createZipCode, createSightingImportance);

                myRef.push().setValue(createBirdSighting);

                Toast.makeText(this, "Bird sighting successfully added to database", Toast.LENGTH_SHORT).show();

            }

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
        } else if (item.getItemId() == R.id.itemLogOut) {

            FirebaseAuth.getInstance().signOut();
            Toast.makeText(this, "Log out successful", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);


        }


        return super.onOptionsItemSelected(item);
    }
}
