package org.chromium.content_shell_apk;

import java.util.ArrayList;

import org.chromium.base.ChromiumActivity;
import org.chromium.base.MemoryPressureListener;
import org.chromium.content.app.LibraryLoader;
import org.chromium.content.browser.ActivityContentVideoViewClient;
import org.chromium.content.browser.AndroidBrowserProcess;
import org.chromium.content.browser.BrowserStartupConfig;
import org.chromium.content.browser.ContentVideoViewClient;
import org.chromium.content.browser.ContentView;
import org.chromium.content.browser.DeviceUtils;
import org.chromium.content.browser.TracingIntentHandler;
import org.chromium.content.common.CommandLine;
import org.chromium.content.common.ProcessInitException;
import org.chromium.content_shell.ContentMediaSurface;
import org.chromium.content_shell.ContentMediaSurfaceClient;
import org.chromium.content_shell.MediaSurfaceDelegate;
import org.chromium.content_shell.NeroContentViewClient;
import org.chromium.content_shell.Shell;
import org.chromium.content_shell.ShellManager;
import org.chromium.ui.WindowAndroid;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import com.amazon.android.Kiwi;

public class ContentShellActivity extends ChromiumActivity implements
        MediaSurfaceDelegate, ContentMediaSurfaceClient {
    class audioFocusChangeListener implements
            android.media.AudioManager.OnAudioFocusChangeListener {
        public void onAudioFocusChange(int i) {
            // TODO
        }

        audioFocusChangeListener() {
            // TODO
        }
    }

    private static final String ACTION_CONNECTIVITY_LOST = "com.amazon.tv.networkmonitor.CONNECTIVITY_LOST";
    private static final String ACTION_LOW_MEMORY = "org.chromium.content_shell.action.ACTION_LOW_MEMORY";
    private static final String ACTION_NETFLIX_CONNECTIVITY_LOST = "org.chromium.content_shell_apk.action.ACTION_NETFLIX_CONNECTIVITY_LOST";
    private static final String ACTION_START_TRACE = "org.chromium.content_shell.action.PROFILE_START";
    private static final String ACTION_STOP_TRACE = "org.chromium.content_shell.action.PROFILE_STOP";
    private static final String ACTION_TRIM_MEMORY_MODERATE = "org.chromium.content_shell.action.ACTION_TRIM_MEMORY_MODERATE";
    private static final String ACTIVE_SHELL_URL_KEY = "activeUrl";
    public static final String COMMAND_LINE_ARGS_KEY = "commandLineArgs";
    public static final String COMMAND_LINE_FILE = "/data/local/tmp/content-shell-command-line";
    private static final String EXTRA_ON_TRY_AGAIN = "org.chromium.content_shell_apk.extra.EXTRA_ON_TRY_AGAIN";
    private static final String ON_TRY_AGAIN_RELOAD_STARTUP_URL = "on_try_again_reload_startup_url";
    private static final String TAG = "ContentShellActivity";
    private android.media.AudioManager.OnAudioFocusChangeListener afChangeListener;
    private boolean hasAudioFocus;
    private boolean mActivityBackground;
    private FrameLayout mContentViewHolder;
    private float mDensity;
    private ContentMediaSurface mMediaSurface;
    private BroadcastReceiver mReceiver;
    private ShellManager mShellManager;
    private WindowAndroid mWindowAndroid;

    public ContentShellActivity() {
        hasAudioFocus = false;
        afChangeListener = null;
        mMediaSurface = null;
        mActivityBackground = false;
    }

    private void acquireAudioFocus() {
        // TODO
    }

    private void attachMediaSurfaceIfNeeded(int i) {
        if (mMediaSurface == null) {
            mMediaSurface = new ContentMediaSurface(this, this, i);
            getWindow().addContentView(mMediaSurface,
                    new android.widget.FrameLayout.LayoutParams(-1, -1, 17));
            getWindow().addFlags(128);
        }
    }

    private void createBackKeyEvent() {
        Shell shell = getActiveShell();
        KeyEvent keyevent = new KeyEvent(0, 4);
        if (shell != null)
            shell.dispatchKeyEvent(keyevent);
    }

    private void detachMediaSurface() {
        if (mMediaSurface != null) {
            mMediaSurface.destroy();
            ((FrameLayout) getWindow().getDecorView())
                    .removeView(mMediaSurface);
            mMediaSurface = null;
            getWindow().clearFlags(128);
        }
    }

    private void finishInitialization() {
        getActiveContentView().setContentViewClient(
                new NeroContentViewClient(this) {

                    final ContentShellActivity this$0;

                    public ContentVideoViewClient getContentVideoViewClient() {
                        return new ActivityContentVideoViewClient(
                                ContentShellActivity.this);
                    }

                    public void onPageFinished(String s) {
                        if (mShellManager.canConnectToNetflix()) {
                            mContentViewHolder = (FrameLayout) findViewById(0x7f08001f);
                            mContentViewHolder.setY(0.0F);
                            mShellManager.destroySplashScreen();
                            return;
                        } else {
                            mShellManager
                                    .broadcastNetflixConnectivityLost("on_try_again_reload_startup_url");
                            return;
                        }
                    }

                    public void onRendererCrash(boolean flag) {
                        Log.e("ContentShellActivity",
                                "Received onRendererCrash");
                        finish();
                    }

                    public void onWebMediaPlayerCreated(int i) {
                        Log.i("ContentShellActivity",
                                "Received onWebMediaPlayerCreated");
                        if (mActivityBackground)
                            createBackKeyEvent();
                    }

                    {
                        this$0 = ContentShellActivity.this;
                    }
                });
    }

    private static String[] getCommandLineParamsFromIntent(Intent intent) {
        if (intent != null)
            return intent.getStringArrayExtra("commandLineArgs");
        else
            return null;
    }

    private String[] getNeroCommandLineArgs() {
        ArrayList arraylist = new ArrayList();
        String s = Build.TYPE.toUpperCase();
        if (!s.contains("DEBUG") && !s.contains("ENG"))
            arraylist.add("--disable-remote-debugging");
        return (String[]) arraylist.toArray(new String[arraylist.size()]);
    }

    private static String getUrlFromIntent(Intent intent) {
        if (intent != null)
            return intent.getDataString();
        else
            return null;
    }

    private void releaseAudioFocus() {
        // TODO
    }

    private void showAlert(final String onTryAgain) {
        (new android.app.AlertDialog.Builder(this))
                .setMessage(
                        "Unable to connect to Netflix. Please try again or visit: www.netflix.com/tv/help")
                .setPositiveButton("Try Again",
                        new android.content.DialogInterface.OnClickListener() {

                            public void onClick(
                                    DialogInterface dialoginterface, int i) {
                                if ("on_try_again_reload_startup_url"
                                        .equals(onTryAgain))
                                    mShellManager.loadStartupUrl();
                            }
                        })
                .setNegativeButton("Exit",
                        new android.content.DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialoginterface, int i) {
                                finish();
                            }
                        }).show();
    }

    private void waitForDebuggerIfNeeded() {
        if (CommandLine.getInstance().hasSwitch("wait-for-java-debugger")) {
            Log.e("ContentShellActivity",
                    "Waiting for Java debugger to connect...");
            Debug.waitForDebugger();
            Log.e("ContentShellActivity",
                    "Java debugger connected. Resuming execution.");
        }
    }

    public ContentView getActiveContentView() {
        Shell shell = getActiveShell();
        if (shell != null)
            return shell.getContentView();
        else
            return null;
    }

    public Shell getActiveShell() {
        if (mShellManager != null)
            return mShellManager.getActiveShell();
        else
            return null;
    }

    public ShellManager getShellManager() {
        return mShellManager;
    }

    public void onActivityResult(int i, int j, Intent intent) {
        if (Kiwi.onActivityResult(this, i, j, intent)) {
            return;
        } else {
            onActivityResultContentShellActivity(i, j, intent);
            return;
        }
    }

    public void onActivityResultContentShellActivity(int i, int j, Intent intent) {
        super.onActivityResult(i, j, intent);
        mWindowAndroid.onActivityResult(i, j, intent);
    }

    public void onCreate(Bundle bundle) {
        onCreateContentShellActivity(bundle);
        Kiwi.onCreate(this, true);
    }

    protected void onCreateContentShellActivity(Bundle bundle) {
        super.onCreate(bundle);
        Log.i("ContentShellActivity", "Received onCreate event.");
        if (!CommandLine.isInitialized()) {
            CommandLine
                    .initFromFile("/data/local/tmp/content-shell-command-line");
            String as[] = getCommandLineParamsFromIntent(getIntent());
            if (as != null)
                CommandLine.getInstance().appendSwitchesAndArguments(as);
            String as1[] = getNeroCommandLineArgs();
            if (as1 != null)
                CommandLine.getInstance().appendSwitchesAndArguments(as1);
            mDensity = 1.5F;
        }
        afChangeListener = new audioFocusChangeListener();
        waitForDebuggerIfNeeded();
        DeviceUtils.addDeviceSpecificUserAgentSwitch(this);
        String s1;
        try {
            LibraryLoader.ensureInitialized();
            setContentView(0x7f030004);
            mShellManager = (ShellManager) findViewById(0x7f080010);
            mShellManager.getRootView().setBackgroundColor(0x106000c);
            mWindowAndroid = new WindowAndroid(this);
            mWindowAndroid.restoreInstanceState(bundle);
            mShellManager.setWindow(mWindowAndroid);
            String s = getUrlFromIntent(getIntent());
            if (!TextUtils.isEmpty(s))
                mShellManager.setStartupUrl(Shell.sanitizeUrl(s));
            if (!CommandLine.getInstance().hasSwitch("dump-render-tree"))
                BrowserStartupConfig
                        .setAsync(new org.chromium.content.browser.BrowserStartupConfig.StartupCallback() {
                            public void run(int i) {
                                if (i > 0) {
                                    Log.e("ContentShellActivity",
                                            "ContentView initialization failed.");
                                    finish();
                                    return;
                                } else {
                                    finishInitialization();
                                    return;
                                }
                            }
                        });
            if (AndroidBrowserProcess.init(this, 9)) {
                // TODO
            }
        } catch (ProcessInitException processinitexception) {
            Log.e("ContentShellActivity", "ContentView initialization failed.",
                    processinitexception);
            finish();
            return;
        }
        s1 = "https://uiboot.netflix.com/apps/cadmiumtvui/upgrade_policy?v=2.0&first_boot=true&platform_id=nero";
        if (bundle == null) {
            // TODO
        }
        if (bundle.containsKey("activeUrl"))
            s1 = bundle.getString("activeUrl");
        mShellManager.launchShell(s1);
        finishInitialization();
    }

    public Dialog onCreateDialog(int i) {
        Dialog dialog = Kiwi.onCreateDialog(this, i);
        if (dialog != null)
            return dialog;
        else
            return super.onCreateDialog(i);
    }

    public Dialog onCreateDialog(int i, Bundle bundle) {
        Dialog dialog = Kiwi.onCreateDialog(this, i);
        if (dialog != null)
            return dialog;
        else
            return super.onCreateDialog(i, bundle);
    }

    public void onDestroy() {
        onDestroyContentShellActivity();
        Kiwi.onDestroy(this);
    }

    protected void onDestroyContentShellActivity() {
        super.onDestroy();
        Log.i("ContentShellActivity", "Received onDestroy event.");
        if (getActiveShell() != null)
            mShellManager.closeAllWindows();
        System.exit(0);
    }

    public void onExternalVideoSurfaceRequested(int i) {
        if (i == -1)
            return;
        if (mMediaSurface != null && i != mMediaSurface.playerId())
            detachMediaSurface();
        attachMediaSurfaceIfNeeded(i);
    }

    public void onGeometryChanged(int i, RectF rectf) {
        label0: {
            if (mMediaSurface != null && mMediaSurface.playerId() == i) {
                if (!rectf.isEmpty())
                    break label0;
                detachMediaSurface();
            }
            return;
        }
        Rect rect = new Rect((int) (rectf.left * mDensity),
                (int) (rectf.top * mDensity), (int) (rectf.right * mDensity),
                (int) (rectf.bottom * mDensity));
        mMediaSurface.resizeSurface(rect);
    }

    public boolean onKeyUp(int i, KeyEvent keyevent) {
        if (i != 4)
            return super.onKeyUp(i, keyevent);
        Shell shell = getActiveShell();
        if (shell != null && shell.getContentView().canGoBack()) {
            shell.getContentView().goBack();
            return true;
        } else {
            return super.onKeyUp(i, keyevent);
        }
    }

    public void onMediaSurfaceCreated() {
        Log.i("ContentShellActivity", "Received onMediaSurfaceCreated event.");
        if (getActiveContentView() != null
                && mMediaSurface.getSurface() != null)
            getActiveContentView().attachExternalVideoSurface(
                    mMediaSurface.playerId(), mMediaSurface.getSurface());
    }

    public void onMediaSurfaceLost() {
        if (getActiveContentView() != null && mMediaSurface != null)
            getActiveContentView().detachExternalVideoSurface(
                    mMediaSurface.playerId());
    }

    protected void onNewIntent(Intent intent) {
        if (getCommandLineParamsFromIntent(intent) != null)
            Log.i("ContentShellActivity",
                    "Ignoring command line params: can only be set when creating the activity.");
        if ("org.chromium.content_shell.action.ACTION_LOW_MEMORY".equals(intent
                .getAction())) {
            MemoryPressureListener.simulateMemoryPressureSignal(80);
        } else {
            if ("org.chromium.content_shell.action.ACTION_TRIM_MEMORY_MODERATE"
                    .equals(intent.getAction())) {
                MemoryPressureListener.simulateMemoryPressureSignal(60);
                return;
            }
            String s = getUrlFromIntent(intent);
            if (!TextUtils.isEmpty(s)) {
                Shell shell = getActiveShell();
                if (shell != null) {
                    shell.loadUrl(s);
                    return;
                }
            }
        }
    }

    public void onPaintExternalVideoSurface(int i, int j) {
        while (i == -1 || mMediaSurface == null
                || mMediaSurface.playerId() != i
                || !mMediaSurface.getSurfaceHolder().getSurface().isValid())
            return;
        mMediaSurface.getSurfaceView().setBackgroundColor(j);
        Log.i("ContentShellActivity",
                (new StringBuilder()).append("Setting Background color to : ")
                        .append(j).toString());
    }

    public void onPause() {
        onPauseContentShellActivity();
        Kiwi.onPause(this);
    }

    protected void onPauseContentShellActivity() {
        super.onPause();
        Log.i("ContentShellActivity", "Received onPause event.");
        mActivityBackground = true;
        ContentView contentview = getActiveContentView();
        if (contentview != null
                && (mMediaSurface != null || contentview.getPlayerCount() > 0))
            createBackKeyEvent();
        unregisterReceiver(mReceiver);
        releaseAudioFocus();
    }

    public void onResume() {
        onResumeContentShellActivity();
        Kiwi.onResume(this);
    }

    protected void onResumeContentShellActivity() {
        super.onResume();
        Log.i("ContentShellActivity", "Received onResume event.");
        mActivityBackground = false;
        IntentFilter intentfilter = new IntentFilter(
                "org.chromium.content_shell.action.PROFILE_START");
        intentfilter
                .addAction("org.chromium.content_shell.action.PROFILE_STOP");
        intentfilter
                .addAction("com.amazon.tv.networkmonitor.CONNECTIVITY_LOST");
        intentfilter
                .addAction("org.chromium.content_shell_apk.action.ACTION_NETFLIX_CONNECTIVITY_LOST");
        mReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String s = intent.getAction();
                if ("org.chromium.content_shell.action.PROFILE_START".equals(s)) {
                    String s2 = intent.getStringExtra("file");
                    if (!s2.isEmpty()) {
                        TracingIntentHandler.beginTracing(s2);
                        Log.i("ContentShellActivity", "start tracing");
                        return;
                    } else {
                        Log.e("ContentShellActivity",
                                "Can not start tracing without specifing saving location");
                        return;
                    }
                }

                if ("org.chromium.content_shell.action.PROFILE_STOP".equals(s)) {
                    Log.i("ContentShellActivity", "stop tracing");
                    TracingIntentHandler.endTracing();
                    return;
                }

                if (!"com.amazon.tv.networkmonitor.CONNECTIVITY_LOST".equals(s)) {
                    Log.e("ContentShellActivity",
                            "Cannot load page when internet connection is lost");
                    finish();
                    return;
                }

                if ("org.chromium.content_shell_apk.action.ACTION_NETFLIX_CONNECTIVITY_LOST"
                        .equals(s)) {
                    Log.e("ContentShellActivity",
                            "Netflix server is currently unreachable");
                    String s1 = intent
                            .getStringExtra("org.chromium.content_shell_apk.extra.EXTRA_ON_TRY_AGAIN");
                    showAlert(s1);
                    return;
                }
            }
        };
        registerReceiver(mReceiver, intentfilter);
        acquireAudioFocus();
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        Shell shell = getActiveShell();
        if (shell != null)
            bundle.putString("activeUrl", shell.getContentView().getUrl());
        mWindowAndroid.saveInstanceState(bundle);
    }

    public void onStart() {
        onStartContentShellActivity();
        Kiwi.onStart(this);
    }

    protected void onStartContentShellActivity() {
        super.onStart();
        Log.i("ContentShellActivity", "Received onStart event.");
        ContentView contentview = getActiveContentView();
        if (contentview != null)
            contentview.onActivityResume();
        if (mActivityBackground)
            (new Handler()).postDelayed(new Runnable() {
                public void run() {
                    if (mContentViewHolder != null)
                        mContentViewHolder.setY(0.0F);
                }
            }, 1000L);
        mActivityBackground = false;
    }

    public void onStop() {
        onStopContentShellActivity();
        Kiwi.onStop(this);
    }

    protected void onStopContentShellActivity() {
        super.onStop();
        mActivityBackground = true;
        Log.i("ContentShellActivity", "Received onStop event.");
        if (!isFinishing()) {
            ContentView contentview = getActiveContentView();
            if (contentview != null)
                contentview.onActivityPause();
            Shell shell = getActiveShell();
            if (shell != null)
                shell.clearFocus();
        }
        if (mContentViewHolder != null)
            mContentViewHolder.setY(mShellManager.getHeight());
    }
}
