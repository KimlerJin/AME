package com.ame.base;

public interface Notifiable {

    void notificationInfo(String bundleKey, String defaultText);

    void notificationInfo(String bundleKey, String defaultText, Object... params);

    void notificationWarning(String bundleKey, String defaultText);

    void notificationWarning(String bundleKey, String defaultText, Object... params);

    void notificationError(String bundleKey, String defaultText);

    void notificationError(String bundleKey, String defaultText, Object... params);

}
