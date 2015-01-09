package com.amazon.android.framework.context;

import android.app.Activity;
import android.app.Service;

public interface ContextManager {

    public abstract void finishActivities();

    public abstract Activity getRoot();

    public abstract Activity getVisible();

    public abstract boolean hasAppShutdownBeenRequested();

    public abstract boolean isVisible();

    public abstract void onCreate(Activity activity);

    public abstract void onCreate(Service service);

    public abstract void onDestroy(Activity activity);

    public abstract void onDestroy(Service service);

    public abstract void onPause(Activity activity);

    public abstract void onResume(Activity activity);

    public abstract void onStart(Activity activity);

    public abstract void onStop(Activity activity);

    public abstract void stopServices();
}
