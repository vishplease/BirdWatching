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
    Button buttonZipSearch, buttonAddImportance;
    TextView textViewBirdNameResult, textViewPersonNameResult;
    Integer importanceResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        this.setTitle("Bird Sighting Search");

        editTextZipSearch = findViewById(R.id.editTextZipSearch);
        buttonZipSearch = findViewById(R.id.buttonZipSearch);
        buttonAddImportance = findViewById(R.id.buttonAddImportance);
        textViewBirdNameResult = findViewById(R.id.textViewBirdNameResult);
        textViewPersonNameResult = findViewById(R.id.textViewPersonNameResult);

        buttonZipSearch.setOnClickListener(this);
        buttonAddImportance.setOnClickListener(this);

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

            Intent itemSearchIntent = new Intent(this, MainActivity.class);
            startActivity(itemSearchIntent);

        } else if (item.getItemId() == R.id.itemSearch){

            Toast.makeText(this, "You are already on the Search page", Toast.LENGTH_SHORT).show();

        } else if (item.getItemId() == R.id.itemLogOut) {

            FirebaseAuth.getInstance().signOut();
            Toast.makeText(this, "Sign out successful", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(Search.this, LoginActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.itemImportance) {
            Intent intent = new Intent(this, ImportanceActivity.class);
            startActivity(intent);
        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("birdsightings");

        if (v == buttonZipSearch) {
            if (editTextZipSearch.getText().toString().trim().equalsIgnoreCase("")) {
                editTextZipSearch.setError("Zip code is empty. This field cannot be blank.");
            } else {
                try {
                    Integer zipTest = Integer.parseInt(editTextZipSearch.getText().toString());

                } catch (Exception e) {
                    editTextZipSearch.setError("Enter Numerical Value");
                    return;

                }


                Integer newZipSearch = Integer.parseInt(editTextZipSearch.getText().toString());

                myRef.orderByChild("zipCode").equalTo(newZipSearch).limitToLast(1).addChildEventListener(new ChildEventListener() {
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


        } else if (v == buttonAddImportance) {

            if (editTextZipSearch.getText().toString().trim().equalsIgnoreCase("")) {
                editTextZipSearch.setError("This field cannot be blank");
            } else {

                try {
                    Integer zipTest = Integer.parseInt(editTextZipSearch.getText().toString());

                } catch (Exception e) {
                    editTextZipSearch.setError("Enter Numerical Value");
                    return;
                }

                Integer newZipSearch = Integer.parseInt(editTextZipSearch.getText().toString());

                myRef.orderByChild("zipCode").equalTo(newZipSearch).limitToLast(1).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        String editKey = dataSnapshot.getKey();

                        BirdSighting foundSighting = dataSnapshot.getValue(BirdSighting.class);

                        Integer editImportance = foundSighting.sightingImportance + 1;

                        myRef.child(editKey).child("sightingImportance").setValue(editImportance);

                        Toast.makeText(Search.this, "Importance level set to: "+editImportance, Toast.LENGTH_SHORT).show();

                        //Toast.makeText(Search.this, "EditKey is "+editKey, Toast.LENGTH_SHORT).show();


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


}
