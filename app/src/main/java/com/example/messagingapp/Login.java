package com.example.messagingapp;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    private static final int Job_ID = 101;
    private JobScheduler jobScheduler;
    private JobInfo jobInfo;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Button loginButton = findViewById(R.id.loginButton);
        Button sign_upButton = findViewById(R.id.sign_upButton);

        sign_upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registration_intent = new Intent(getApplicationContext(), registration.class);
                startActivity(registration_intent);

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                /*ComponentName componentName = new ComponentName(getApplicationContext(), MJobScheduler.class);
                JobInfo.Builder builder = new JobInfo.Builder(Job_ID, componentName);

                builder.setMinimumLatency(5000);
                //builder.setPeriodic(5*1000);
                builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
                builder.setPersisted(true);

                jobInfo = builder.build();
                jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);

                jobScheduler.schedule(jobInfo);
                //Toast.makeText(getApplicationContext(), "service started", Toast.LENGTH_LONG).show();*/


                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("users");
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {

                    TextView errorHandler =findViewById(R.id.errorHandler);
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       try{

                           long num = Member.checkNum(((EditText) findViewById(R.id.number)).getText().toString().trim());
                           String pass = (((EditText) findViewById(R.id.pass)).getText().toString());

                            Member member = dataSnapshot.child(Long.toString(num)).getValue(Member.class);

                            if (member.getPassword().equals(pass)) {

                                //startService(new Intent(getApplicationContext(), notificationService.class).putExtra("User", member));
                                startActivity(new Intent(getApplicationContext(), Dashboard.class).putExtra("User", member));

                            }
                            else
                                errorHandler.setText("password not matching with username");
                       }
                       catch (NullPointerException e)
                       {
                           errorHandler.setText("no such registered user");
                       } catch (Exception e) {
                           errorHandler.setText(e.getLocalizedMessage());
                       }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "cancelled data fetch", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        setContentView(R.layout.about);
        return true;
    }
}
