package com.example.birdwatching;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ImportanceActivity extends AppCompatActivity {

    TextView textViewImportanceHeadline;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_importance);

        textViewImportanceHeadline = findViewById(R.id.textViewImportanceHeadline);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("birdsightings");

        myRef.orderByChild("sightingImportance").limitToLast(1).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                BirdSighting foundSighting = dataSnapshot.getValue(BirdSighting.class);

                String findName = foundSighting.personName;
                String findBird = foundSighting.birdName;
                Integer findZip = foundSighting.zipCode;
                Integer findImportance = foundSighting.sightingImportance;

                textViewImportanceHeadline.setText("The most important bird found is " + findBird + " found by " + findName + " in zip code " + findZip + ". The importance level is " + findImportance + ".");

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater(); //declare object
        inflater.inflate(R.menu.mainmenu, menu); //inflate menu in activity
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.itemReport) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        } else if (item.getItemId() == R.id.itemSearch){

            Intent itemSearchIntent = new Intent(this, Search.class);
            startActivity(itemSearchIntent);
        } else if (item.getItemId() == R.id.itemLogOut) {

            FirebaseAuth.getInstance().signOut();
            Toast.makeText(this, "Log out successful", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(ImportanceActivity.this, LoginActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.itemImportance) {

            Toast.makeText(this, "You are already on the Item Importance page", Toast.LENGTH_SHORT).show();
        }


        return super.onOptionsItemSelected(item);
    }
}
