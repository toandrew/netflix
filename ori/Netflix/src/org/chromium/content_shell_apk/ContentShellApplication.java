package org.chromium.content_shell_apk;

import org.chromium.base.PathUtils;
import org.chromium.content.browser.ResourceExtractor;

import android.app.Application;

public class ContentShellApplication extends Application {

    private static final String MANDATORY_PAK_FILES[] = { "content_shell.pak" };
    private static final String PRIVATE_DATA_DIRECTORY_SUFFIX = "content_shell";

    public ContentShellApplication() {
    }

    public static void initializeApplicationParameters() {
        ResourceExtractor.setMandatoryPaksToExtract(MANDATORY_PAK_FILES);
        PathUtils.setPrivateDataDirectorySuffix("content_shell");
    }

    public void onCreate() {
        super.onCreate();
        initializeApplicationParameters();
    }

}
