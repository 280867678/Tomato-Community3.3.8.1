package com.google.android.exoplayer2.scheduler;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.PersistableBundle;
import com.google.android.exoplayer2.util.Util;

@TargetApi(21)
/* loaded from: classes3.dex */
public final class PlatformScheduler implements Scheduler {
    /* JADX INFO: Access modifiers changed from: private */
    public static void logd(String str) {
    }

    /* loaded from: classes.dex */
    public static final class PlatformSchedulerService extends JobService {
        @Override // android.app.job.JobService
        public boolean onStopJob(JobParameters jobParameters) {
            return false;
        }

        @Override // android.app.job.JobService
        public boolean onStartJob(JobParameters jobParameters) {
            PlatformScheduler.logd("PlatformSchedulerService started");
            PersistableBundle extras = jobParameters.getExtras();
            if (new Requirements(extras.getInt("requirements")).checkRequirements(this)) {
                PlatformScheduler.logd("Requirements are met");
                String string = extras.getString("service_action");
                String string2 = extras.getString("service_package");
                Intent intent = new Intent(string).setPackage(string2);
                PlatformScheduler.logd("Starting service action: " + string + " package: " + string2);
                Util.startForegroundService(this, intent);
                return false;
            }
            PlatformScheduler.logd("Requirements are not met");
            jobFinished(jobParameters, true);
            return false;
        }
    }
}
