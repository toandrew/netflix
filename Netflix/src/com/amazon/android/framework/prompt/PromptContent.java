package com.amazon.android.framework.prompt;

public class PromptContent {

    private final String buttonLabel;
    private final String message;
    private final String title;
    private final boolean visible;

    public PromptContent(String s, String s1, String s2, boolean flag) {
        title = s;
        message = s1;
        buttonLabel = s2;
        visible = flag;
    }

    public String getButtonLabel() {
        return buttonLabel;
    }

    public String getMessage() {
        return message;
    }

    public String getTitle() {
        return title;
    }

    public boolean isVisible() {
        return visible;
    }

    public String toString() {
        return (new StringBuilder()).append("PromptContent: [ title:")
                .append(title).append(", message: ").append(message)
                .append(", label: ").append(buttonLabel).append(", visible: ")
                .append(visible).append("]").toString();
    }
}
