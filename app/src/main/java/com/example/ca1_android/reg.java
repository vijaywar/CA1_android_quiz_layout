package com.example.ca1_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class reg extends AppCompatActivity {

    EditText name;
    ArrayList<String> names=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        openalert();

        name=findViewById(R.id.name);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("regusers");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for(DataSnapshot i:dataSnapshot.getChildren()){
                    System.out.println(i.child("name").getValue(String.class)+"....................................");
                    names.add(i.child("name").getValue(String.class));
                }


            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                System.out.println(error+".........................................");
                // Log.w(TAG, "Failed to read value.", error.toException());
                Toast.makeText(reg.this,"Error check ur Network",Toast.LENGTH_LONG).show();

            }
        });





    }

    private  void openalert(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(reg.this);
        alertDialogBuilder.setMessage("Have u Registered Already?");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent iv =new Intent(reg.this,MainActivity.class);
                        iv.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                        startActivity(iv);
                    }
                });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(reg.this,"Create user to continue!",Toast.LENGTH_LONG).show();
                //k  finish();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    public void submit(View view) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("regusers");

        if(!names.contains(String.valueOf(name.getText()))) {
            myRef.push().child("name").setValue(String.valueOf(name.getText()));
            Intent iv =new Intent(reg.this,MainActivity.class);
            iv.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            startActivity(iv);
        }
        else
            Toast.makeText(this,"Try different Username!",Toast.LENGTH_SHORT).show();
    }

    public void skip(View view) {
        Intent iv =new Intent(reg.this,MainActivity.class);
        iv.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        startActivity(iv);
    }
}
