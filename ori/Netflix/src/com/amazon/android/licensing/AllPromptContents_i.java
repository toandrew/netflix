package com.amazon.android.licensing;

import com.amazon.android.framework.prompt.PromptContent;

public final class AllPromptContents_i {

    public static final PromptContent a = new PromptContent(
            "Amazon Appstore required",
            "It looks like the Amazon Appstore was uninstalled from your device. Please install the Amazon Appstore and sign in with your username and password to use this app",
            "OK", true);
    public static final PromptContent b = new PromptContent(
            "Amazon Appstore: store connection failure",
            "An error occurred connecting to the Amazon Appstore for Android. Please try again",
            "OK", true);
    public static final PromptContent c = new PromptContent(
            "Amazon Appstore required",
            "Your version of the Amazon Appstore appears to be out of date.  Please visit Amazon.com to install the latest version of the Appstore.",
            "OK", true);
    public static final PromptContent d = new PromptContent(
            "Amazon Appstore: internet connection required",
            "Unable to connect. Please establish network connection to continue",
            "OK", true);
    public static final PromptContent e = new PromptContent(
            "Amazon Appstore: unknown error",
            "An error occurred. Please download this app again from the Amazon Appstore",
            "OK", true);
    public static final PromptContent f = new PromptContent(
            "Amazon Appstore: internal failure",
            "An internal error occured, please try re-launching the app again",
            "OK", true);

    private AllPromptContents_i() {
    }

}
