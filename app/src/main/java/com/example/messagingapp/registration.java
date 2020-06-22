package com.example.messagingapp;

import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registration extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        Button create = findViewById(R.id.create_button);

        create.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                TextView errorHandler = (TextView)findViewById(R.id.errorHandler);
                try {
                    long num = Member.checkNum(((EditText) findViewById(R.id.phone)).getText().toString().trim());

                    FirebaseApp.initializeApp(getApplicationContext());

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("users/" + Long.toString(num).trim());

                    String password = ((EditText) findViewById(R.id.password)).getText().toString();
                    String confirmPassword = ((EditText) findViewById(R.id.confirmPassword)).getText().toString();


                    if (!confirmPassword.equals(password))
                        throw new Exception("passwords do not match");

                    Member member = new Member(((EditText) findViewById(R.id.name)).getText().toString(),
                            num,
                            ((EditText) findViewById(R.id.mail)).getText().toString(),
                            ((EditText) findViewById(R.id.address)).getText().toString(),
                            ((EditText) findViewById(R.id.designation)).getText().toString(),
                            password);

                    myRef.setValue(member);

                    Toast.makeText(getApplicationContext(), "account created", Toast.LENGTH_LONG).show();
                }
                catch (Exception e) {
                    errorHandler.setText(e.getLocalizedMessage());

                }

            }
           });

        }
    }



