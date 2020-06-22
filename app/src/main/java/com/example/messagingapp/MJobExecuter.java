package com.example.messagingapp;

import android.os.AsyncTask;

public class MJobExecuter extends AsyncTask <Void, Void, String>{
    @Override
    protected String doInBackground(Void... voids)  {
        return "backgroud long running task finishes...";
    }
}
