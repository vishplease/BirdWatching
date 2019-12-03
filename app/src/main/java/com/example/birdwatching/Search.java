package com.example.birdwatching;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Search extends AppCompatActivity implements View.OnClickListener{

    EditText editTextZipSearch;
    Button buttonZipSearch;
    TextView textViewBirdNameResult, textViewPersonNameResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        this.setTitle("Bird Sighting Search");

        editTextZipSearch = findViewById(R.id.editTextZipSearch);
        buttonZipSearch = findViewById(R.id.buttonZipSearch);
        textViewBirdNameResult = findViewById(R.id.textViewBirdNameResult);
        textViewPersonNameResult = findViewById(R.id.textViewPersonNameResult);

        buttonZipSearch.setOnClickListener(this);

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
            Toast.makeText(this, "You are already on the Report page", Toast.LENGTH_SHORT).show();

        } else if (item.getItemId() == R.id.itemSearch){

            Intent itemSearchIntent = new Intent(this, Search.class);
            startActivity(itemSearchIntent);
        } else if (item.getItemId() == R.id.itemLogOut) {

            FirebaseAuth.getInstance().signOut();

            Intent intent = new Intent(Search.this, LoginActivity.class);
            startActivity(intent);


        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("birdsightings");

        if (v == buttonZipSearch) {


            Integer newZipSearch = Integer.parseInt(editTextZipSearch.getText().toString());

            myRef.orderByChild("zipCode").equalTo(newZipSearch).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    BirdSighting foundSighting = dataSnapshot.getValue(BirdSighting.class);

                    String findName = foundSighting.personName;
                    String findBird = foundSighting.birdName;

                    textViewPersonNameResult.setText(findName);
                    textViewBirdNameResult.setText(findBird);

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


    }


}
