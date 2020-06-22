package com.example.messagingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Dashboard extends AppCompatActivity {
    Member user;
    TextView errorHandler;
    String myTag = "LogMessage";

  /**  protected void onResume()
    {
        Inbox adapter = new Inbox(this,
                R.layout.inbox,user.getInbox());

        ListView inbox = findViewById(R.id.inbox);
        inbox.setAdapter(adapter);

        adapter.notifyDataSetChanged();
        super.onResume();
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        user = (Member) getIntent().getSerializableExtra("User");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

         errorHandler = (TextView) findViewById(R.id.errorHandler);

        Button send = (Button) findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();

                final DatabaseReference myRef = database.getReference("users/");

                myRef.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        try {

                            Long num = Member.checkNum(((EditText) findViewById(R.id.phone)).getText().toString().trim());

                            if(num.equals(user.getPhoneNum()))
                                throw new Exception("you cannot send message to yourself in same client");

                            String message = ((EditText) findViewById(R.id.message)).getText().toString().trim();

                            Member receiver = dataSnapshot.child(Long.toString(num)).getValue(Member.class);

                            receiver.push(user.getPhoneNum(), new Message(message, false));
                            user.push(num, new Message(message, true));

                            myRef.child(Long.toString(user.getPhoneNum())).setValue(user);
                            myRef.child(num.toString()).setValue(receiver);

                            errorHandler.setText("message sent");
                        } catch (NullPointerException e)
                        {
                            errorHandler.setText("no such number present: ");
                        }

                        catch (Exception e) {
                            errorHandler.setText(e.getLocalizedMessage());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        errorHandler.setText("database connection failed");
                    }
                });


            }
        });



    }

}