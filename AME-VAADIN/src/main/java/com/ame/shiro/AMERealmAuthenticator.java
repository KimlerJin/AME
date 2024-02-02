package com.ame.shiro;

import org.apache.shiro.authc.AbstractAuthenticator;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.realm.Realm;

import java.util.Collection;
import java.util.List;

public class AMERealmAuthenticator extends AbstractAuthenticator {

    private List<Realm> realms;

    public AMERealmAuthenticator(List<Realm> realms) {
        this.realms = realms;
    }

    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken token) throws AuthenticationException {
        AuthenticationInfo info = null;
        Throwable t = null;
        for (Realm realm : realms) {
            if (realm.supports(token)) {
                try {
                    info = realm.getAuthenticationInfo(token);
                } catch (Throwable throwable) {
                    t = throwable;
                    if (t instanceof StopAuthenticatingException) {
                        break;
                    }
                }
                if (info != null) {
                    return info;
                }
            }
        }
        if (t != null) {
            if (t instanceof AuthenticationException) {
                throw (AuthenticationException) t;
            } else {
                throw new AuthenticationException(t);
            }
        }
        return null;
    }

    public Collection<Realm> getRealms() {
        return realms;
    }

    public void setRealms(List<Realm> realms) {
        this.realms = realms;
    }
}
