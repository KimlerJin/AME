package com.ame;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

public class SessionHelper {

    private SessionHelper(){
    }

    public static void setAttribute(String name, Object value) {
        SecurityUtils.getSubject().getSession().setAttribute(name, value);
    }

    public static Object getAttribute(String name) {
        Session session = SecurityUtils.getSubject().getSession(false);
        return session != null ? session.getAttribute(name) : null;
    }
}
