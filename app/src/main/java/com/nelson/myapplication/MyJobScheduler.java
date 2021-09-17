package com.nelson.myapplication;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import java.util.function.LongFunction;

/**
 * @author nelson
 */
public class MyJobScheduler extends JobService {

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d("zhangyuhong","123123");
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d("zhangyuhong", "onStopJob: 123123");
        return false;
    }
}
