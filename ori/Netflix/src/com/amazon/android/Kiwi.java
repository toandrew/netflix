package com.amazon.android;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.amazon.android.f.MyActivtyResult_f;
import com.amazon.android.framework.context.ContextManager;
import com.amazon.android.framework.context.ContextManagerImpl_d;
import com.amazon.android.framework.prompt.PromptManager;
import com.amazon.android.framework.prompt.PromptManagerImpl;
import com.amazon.android.framework.resource.ResourceManager_a;
import com.amazon.android.framework.resource.b;
import com.amazon.android.framework.resource.ResourceManagerImpl_c;
import com.amazon.android.framework.task.Task;
import com.amazon.android.framework.task.TaskManager;
import com.amazon.android.framework.task.command.AbstractCommandTask;
import com.amazon.android.framework.task.command.CommandResultVerifier_e;
import com.amazon.android.framework.task.pipeline.TaskPipelineId;
import com.amazon.android.framework.util.KiwiLogger;
import com.amazon.android.o.EventManager_g;

// Referenced classes of package com.amazon.android:
//			b, h, d, f, 
//			g, e, c

public final class Kiwi implements b {

    protected static final String ACTIVITY_NAME = "ActivityName";
    protected static final String EVENT_NAME = "EventName";
    private static final KiwiLogger LOGGER = new KiwiLogger("Kiwi");
    protected static final String TIMESTAMP = "Timestamp";
    private static Kiwi instance;
    private Application application;
    private com.amazon.android.m.DataAuthenticationKeyLoader_c authKeyLoader;
    private ContextManager contextManager;
    private com.amazon.android.n.DataStore_a dataStore;
    private final boolean drmFull;
    private EventManager_g eventManager;
    private PromptManager promptManager;
    private com.amazon.android.f.ResultManager_b resultManager;
    private TaskManager taskManager;

    private Kiwi(Application application1, boolean drmfull) {
        long l = System.currentTimeMillis();
        drmFull = drmfull;
        if (KiwiLogger.TRACE_ON) {
            LOGGER.trace("-------------- CREATING KIWI ----------------------");
            LOGGER.trace((new StringBuilder()).append("Application: ")
                    .append(application1.getPackageName()).toString());
            LOGGER.trace((new StringBuilder()).append("DRM: ").append(drmfull)
                    .toString());
            LOGGER.trace("---------------------------------------------------");
        }
        instantiateTheWorld(application1);
        registerTestModeReceiver(application1);
        if (KiwiLogger.TRACE_ON) {
            long l1 = System.currentTimeMillis();
            LOGGER.trace((new StringBuilder())
                    .append("Kiwi.Constructor Time: ").append(l1 - l)
                    .toString());
        }
    }

    static KiwiLogger a() {
        return LOGGER;
    }

    static com.amazon.android.n.DataStore_a a(Kiwi kiwi) {
        return kiwi.dataStore;
    }

    public static void addCommandToCommandTaskPipeline(
            AbstractCommandTask abstractcommandtask) {
        if (isInitialized()) {
            instance.taskManager.enqueue(TaskPipelineId.COMMAND,
                    abstractcommandtask);
            return;
        } else {
            LOGGER.trace("Kiwi was not yet initialized - cannot do the IAP call");
            return;
        }
    }

    public static void addCommandToCommandTaskPipeline(
            AbstractCommandTask abstractcommandtask, Context context) {
        if (!isInitialized())
            instance = new Kiwi((Application) context.getApplicationContext(),
                    false);
        addCommandToCommandTaskPipeline(abstractcommandtask);
    }

    static Kiwi b() {
        instance = null;
        return null;
    }

    static boolean b(Kiwi kiwi) {
        return kiwi.drmFull;
    }

    static EventManager_g c(Kiwi kiwi) {
        return kiwi.eventManager;
    }

    static void d(Kiwi kiwi) {
        kiwi.enqueueAppLaunchWorkflowTask();
    }

    static Application e(Kiwi kiwi) {
        return kiwi.application;
    }

    private void enqueueAppLaunchWorkflowTask() {
        if (KiwiLogger.TRACE_ON)
            LOGGER.trace("---------- Enqueuing launch workflow --------------");
        Task task = getLaunchWorkflow();
        taskManager.enqueue(TaskPipelineId.COMMAND, task);
    }

    private Task getLaunchWorkflow() {
        if (drmFull)
            return new com.amazon.android.DrmFullApplicationLaunchTaskWorkflow_b();
        else
            return new DrmFreeApplicationLaunchTaskWorkflow_h();
    }

    public static PromptManager getPromptManager() {
        return instance.promptManager;
    }

    private static void ignoreEvent(String s, Context context) {
        if (KiwiLogger.TRACE_ON)
            LOGGER.trace((new StringBuilder()).append(s)
                    .append(" called on context: ").append(context)
                    .append(" when ").append("Kiwi is dead, ignoring...")
                    .toString());
    }

    private void instantiateTheWorld(Application application1) {
        ResourceManagerImpl_c c1 = new ResourceManagerImpl_c();
        c1.a(application1);
        c1.a(new com.amazon.android.framework.  task.TaskManagerImpl_a());
        c1.a(new com.amazon.android.n.DataStore_a());
        c1.a(new com.amazon.android.f.ActivityResultManagerImpl_c());
        c1.a(new ContextManagerImpl_d());
        c1.a(new PromptManagerImpl());
        c1.a(new com.amazon.android.o.EventManagerImpl_b());
        c1.a(new com.amazon.android.c.CrashManagerImpl_a());
        c1.a(new com.amazon.android.q.MetricsManagerImpl_c());
        c1.a(new com.amazon.android.framework.task.command.CommandServiceClient_b());
        c1.a(new com.amazon.android.m.DataAuthenticationKeyLoader_c());
        c1.a(new CommandResultVerifier_e());
        c1.a(new com.amazon.android.g.ObfuscationManagerImpl_a());
        c1.a();
        c1.b(this);
    }

    private static boolean isInitialized() {
        return instance != null;
    }

    public static boolean isSignedByKiwi(String s, String s1) {
        if (!isInitialized()) {
            LOGGER.trace("Kiwi was not yet initialized - cannot do the IAP call");
            return false;
        }

        boolean flag = false;
        try {
            flag = com.amazon.android.m.SignatureVerifier_a.a(s, s1,
                    instance.authKeyLoader.a());
        } catch (com.amazon.android.h.CertFailedToLoadKiwiException_a e) {
            LOGGER.trace((new StringBuilder())
                    .append("Unable to validate signature: ")
                    .append(e.getMessage()).toString());
        }

        return flag;
    }

    public static boolean onActivityResult(Activity activity, int i, int j,
            Intent intent) {
        if (preProcess("onActivityResult", activity)) {
            MyActivtyResult_f f1 = new MyActivtyResult_f(activity, i, j, intent);
            return instance.resultManager.a(f1);
        } else {
            return false;
        }
    }

    public static void onCreate(Activity activity, boolean flag) {
        long l = System.currentTimeMillis();
        if (!isInitialized())
            instance = new Kiwi(activity.getApplication(), flag);
        if (preProcess("onCreate", activity))
            instance.contextManager.onCreate(activity);
        if (KiwiLogger.TRACE_ON) {
            long l1 = System.currentTimeMillis();
            LOGGER.trace((new StringBuilder())
                    .append("Kiwi.ActivityOnCreate Time: ").append(l1 - l)
                    .toString());
        }
    }

    public static void onCreate(Service service, boolean flag) {
        long l = System.currentTimeMillis();
        if (preProcess("onCreate", service))
            instance.contextManager.onCreate(service);
        if (KiwiLogger.TRACE_ON) {
            long l1 = System.currentTimeMillis();
            LOGGER.trace((new StringBuilder())
                    .append("Kiwi.ServiceOnCreate Time: ").append(l1 - l)
                    .toString());
        }
    }

    public static Dialog onCreateDialog(Activity activity, int i) {
        long l = System.currentTimeMillis();
        if (preProcess("onCreateDialog", activity))
            return instance.promptManager.onCreateDialog(activity, i);
        if (KiwiLogger.TRACE_ON) {
            long l1 = System.currentTimeMillis();
            LOGGER.trace((new StringBuilder())
                    .append("Kiwi.ActivityOnCreateDialog Time: ")
                    .append(l1 - l).toString());
        }
        return null;
    }

    public static void onDestroy(Activity activity) {
        long l = System.currentTimeMillis();
        if (preProcess("onDestroy", activity))
            instance.contextManager.onDestroy(activity);
        if (KiwiLogger.TRACE_ON) {
            long l1 = System.currentTimeMillis();
            LOGGER.trace((new StringBuilder())
                    .append("Kiwi.ActivityOnDestroy Time: ").append(l1 - l)
                    .toString());
        }
    }

    public static void onDestroy(Service service) {
        long l = System.currentTimeMillis();
        if (preProcess("onDestroy", service))
            instance.contextManager.onDestroy(service);
        if (KiwiLogger.TRACE_ON) {
            long l1 = System.currentTimeMillis();
            LOGGER.trace((new StringBuilder())
                    .append("Kiwi.ServiceOnDestroy Time: ").append(l1 - l)
                    .toString());
        }
    }

    public static void onPause(Activity activity) {
        long l = System.currentTimeMillis();
        if (preProcess("onPause", activity))
            instance.contextManager.onPause(activity);
        if (KiwiLogger.TRACE_ON) {
            long l1 = System.currentTimeMillis();
            LOGGER.trace((new StringBuilder())
                    .append("Kiwi.ActivityOnPause Time: ").append(l1 - l)
                    .toString());
        }
    }

    public static void onResume(Activity activity) {
        long l = System.currentTimeMillis();
        if (preProcess("onResume", activity))
            instance.contextManager.onResume(activity);
        if (KiwiLogger.TRACE_ON) {
            long l1 = System.currentTimeMillis();
            LOGGER.trace((new StringBuilder())
                    .append("Kiwi.ActivityOnResume Time: ").append(l1 - l)
                    .toString());
        }
    }

    public static void onStart(Activity activity) {
        long l = System.currentTimeMillis();
        if (preProcess("onStart", activity))
            instance.contextManager.onStart(activity);
        if (KiwiLogger.TRACE_ON) {
            long l1 = System.currentTimeMillis();
            LOGGER.trace((new StringBuilder())
                    .append("Kiwi.ActivityOnStart Time: ").append(l1 - l)
                    .toString());
        }
    }

    public static void onStop(Activity activity) {
        long l = System.currentTimeMillis();
        if (preProcess("onStop", activity))
            instance.contextManager.onStop(activity);
        if (KiwiLogger.TRACE_ON) {
            long l1 = System.currentTimeMillis();
            LOGGER.trace((new StringBuilder())
                    .append("Kiwi.ActivityOnStop Time: ").append(l1 - l)
                    .toString());
        }
    }

    public static void onWindowFocusChanged(Activity activity, boolean flag) {
        if (preProcess("onWindowFocusChanged", activity))
            instance.promptManager.onWindowFocusChanged(activity, flag);
    }

    private static boolean preProcess(String s, Context context) {
        com.amazon.android.d.a.a();
        if (KiwiLogger.TRACE_ON)
            LOGGER.trace((new StringBuilder()).append(s).append(": ")
                    .append(context).toString());
        if (isInitialized()) {
            return true;
        } else {
            ignoreEvent(s, context);
            return false;
        }
    }

    private void registerActivityLifeCyclePauseListener() {
        com.amazon.android.c_d d1 = new com.amazon.android.c_d(this);
        eventManager.a(d1);
    }

    private void registerActivityLifeCycleResumeListener() {
        com.amazon.android.c_f f1 = new com.amazon.android.c_f(this);
        eventManager.a(f1);
    }

    private void registerApplicationCreationListener() {
        com.amazon.android.c_g g1 = new com.amazon.android.c_g(this);
        eventManager.a(g1);
    }

    private void registerApplicationDestructionListener() {
        com.amazon.android.c_e e1 = new com.amazon.android.c_e(this);
        eventManager.a(e1);
    }

    private void registerTestModeReceiver(Application application1) {
        application1.registerReceiver(
                new com.amazon.android.KiwiReceiver_c(this),
                new IntentFilter((new StringBuilder())
                        .append(application1.getPackageName())
                        .append(".enable.test.mode").toString()));
    }

    public final void onResourcesPopulated() {
        registerApplicationCreationListener();
        registerApplicationDestructionListener();
        registerActivityLifeCyclePauseListener();
        registerActivityLifeCycleResumeListener();
    }

}
