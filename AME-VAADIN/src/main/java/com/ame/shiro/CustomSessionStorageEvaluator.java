package com.ame.shiro;

import com.ame.utils.WebUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.shiro.mgt.SessionStorageEvaluator;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.subject.WebSubject;

public class CustomSessionStorageEvaluator implements SessionStorageEvaluator {
    @Override
    public boolean isSessionStorageEnabled(Subject subject) {
        if (subject instanceof WebSubject) {
            HttpServletRequest servletRequest = (HttpServletRequest) ((WebSubject) subject).getServletRequest();
            return WebUtils.isBrowser(servletRequest);
        }
        return false;
    }
}
