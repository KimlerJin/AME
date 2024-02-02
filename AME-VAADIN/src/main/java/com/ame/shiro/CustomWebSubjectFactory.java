package com.ame.shiro;

import com.ame.utils.WebUtils;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.shiro.mgt.DefaultSubjectFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.subject.WebSubject;
import org.apache.shiro.web.subject.WebSubjectContext;
import org.apache.shiro.web.subject.support.WebDelegatingSubject;


public class CustomWebSubjectFactory extends DefaultSubjectFactory {

    @Override
    public Subject createSubject(SubjectContext context) {
        // SHIRO-646
        // Check if the existing subject is NOT a WebSubject. If it isn't, then call super.createSubject instead.
        // Creating a WebSubject from a non-web Subject will cause the ServletRequest and ServletResponse to be null,
        // which wil fail when creating a session.
        boolean isNotBasedOnWebSubject = context.getSubject() != null && !(context.getSubject() instanceof WebSubject);
        if (isNotBasedOnWebSubject) {
            return super.createSubject(context);
        }

        WebSubjectContext wsc = (WebSubjectContext) context;
        SecurityManager securityManager = wsc.resolveSecurityManager();
        Session session = wsc.resolveSession();
        HttpServletRequest request = (HttpServletRequest) wsc.resolveServletRequest();
        ServletResponse response = wsc.resolveServletResponse();
        PrincipalCollection principals = wsc.resolvePrincipals();
        boolean authenticated = wsc.resolveAuthenticated();
        boolean sessionEnabled = WebUtils.isBrowser(request);
        String host = wsc.resolveHost();

        return new WebDelegatingSubject(principals, authenticated, host, session, sessionEnabled, request, response,
                securityManager);
    }
}
