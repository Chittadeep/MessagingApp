package com.example.messagingapp;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.media.MediaPlayer;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MJobScheduler extends JobService {

    private MJobExecuter mJobExecuter;
    @Override
    public boolean onStartJob(final JobParameters params) {

        mJobExecuter = new MJobExecuter()
        {
            protected void onPostExecute(String s) {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                MediaPlayer player = MediaPlayer.create(getApplicationContext(), Settings.System.DEFAULT_RINGTONE_URI);
                //player.setLooping(true);
                player.start();
                jobFinished(params, false);
            }
        };

        mJobExecuter.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        mJobExecuter.cancel(true);
        return false;
    }
}
