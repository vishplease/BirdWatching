package com.example.birdwatching;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class Search extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        this.setTitle("Search");
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
            Intent itemReportIntent = new Intent(this, MainActivity.class);
            startActivity(itemReportIntent);

        } else if (item.getItemId() == R.id.itemSearch){
            Toast.makeText(this, "You are already on the Search page", Toast.LENGTH_SHORT).show();
        }


        return super.onOptionsItemSelected(item);
    }
}
