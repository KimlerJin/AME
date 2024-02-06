package com.ame.hibernate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.*;
import org.hibernate.query.Query;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.lang.Nullable;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.SessionFactoryUtils;
import org.springframework.transaction.support.ResourceHolderSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class HibernateTool {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Nullable
    private EntityManager sessionFactory;

    private boolean exposeNativeSession = false;

    @Nullable
    private String[] filterNames;

    private boolean cacheQueries = false;

    private boolean checkWriteOperations = true;
    @Nullable
    private String queryCacheRegion;
    private int fetchSize = 0;
    private int maxResults = 0;

    private static final ThreadLocal<Session> threadLocalSession = new ThreadLocal<>();


    public HibernateTool(EntityManager sessionFactory) {
        this.setSessionFactory(sessionFactory);
    }

    public void setSessionFactory(@Nullable EntityManager sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Nullable
    public EntityManager getSessionFactory() {
        return this.sessionFactory;
    }


    public <T> T get(Class<T> entityClass, Serializable id) throws DataAccessException {
        return get(entityClass, id, null);
    }

    public <T> T get(Class<T> entityClass, Serializable id, @Nullable LockMode lockMode) throws DataAccessException {
        return executeWithNativeSession(session -> {
            if (lockMode != null) {
                return session.get(entityClass, id, new LockOptions(lockMode));
            } else {
                return session.get(entityClass, id);
            }
        });
    }

    public void evict(Object entity) throws DataAccessException {
        executeWithNativeSession(session -> {
            session.evict(entity);
            return null;
        });
    }

    protected final EntityManager obtainSessionFactory() {
        EntityManager sessionFactory = this.getSessionFactory();
        Assert.state(sessionFactory != null, "No SessionFactory set");
        return sessionFactory;
    }


    @SuppressWarnings("unchecked")
    public <T> T merge(String entityName, T entity) throws DataAccessException {
        return nonNull(executeWithNativeSession(session -> {
            checkWriteOperationAllowed(session);
            return (T) session.merge(entityName, entity);
        }));
    }

    public <T> T merge(T entity) throws DataAccessException {
        return nonNull(executeWithNativeSession(session -> {
            checkWriteOperationAllowed(session);
            return (T) session.merge(entity);
        }));
    }

    public void delete(Object entity) throws DataAccessException {
        delete(entity, null);
    }

    public void delete(Object entity, @Nullable LockMode lockMode) throws DataAccessException {
        executeWithNativeSession(session -> {
            checkWriteOperationAllowed(session);
            if (lockMode != null) {
                session.buildLockRequest(new LockOptions(lockMode)).lock(entity);
            }
            boolean contains = session.contains(entity);
            session.delete(entity);
            return null;
        });
    }

    public void flush() throws DataAccessException {
        executeWithNativeSession(session -> {
            session.flush();
            return null;
        });
    }

    protected Session createSessionProxy(Session session) {
        return (Session) Proxy.newProxyInstance(session.getClass().getClassLoader(), new Class[]{Session.class}, new HibernateTool.CloseSuppressingInvocationHandler(session));
    }

    @Nullable
    public <T> T executeWithNativeSession(HibernateCallback<T> action) {
        return this.doExecute(action, true);
    }

    private static <T> T nonNull(@Nullable T result) {
        Assert.state(result != null, "No result");
        return result;
    }

    public Serializable save(Object entity) throws DataAccessException {
        return nonNull((Serializable) this.executeWithNativeSession((session) -> {
            this.checkWriteOperationAllowed(session);
            return session.save(entity);
        }));
    }

    public boolean isCheckWriteOperations() {
        return this.checkWriteOperations;
    }

    protected void checkWriteOperationAllowed(Session session) throws InvalidDataAccessApiUsageException {
        if (this.isCheckWriteOperations() && session.getHibernateFlushMode().lessThan(FlushMode.COMMIT)) {
            throw new InvalidDataAccessApiUsageException("Write operations are not allowed in read-only mode (FlushMode.MANUAL): Turn your Session into FlushMode.COMMIT/AUTO or remove 'readOnly' marker from transaction definition.");
        }
    }

    @Nullable
    public <T> T execute(HibernateCallback<T> action) throws DataAccessException {
        return doExecute(action, false);
    }


    protected <T> T doExecute(HibernateCallback<T> action, boolean enforceNativeSession) throws DataAccessException {
        Assert.notNull(action, "Callback object must not be null");
        Session session = threadLocalSession.get();
        if (session == null) {
            session = this.obtainSessionFactory().unwrap(Session.class);
            threadLocalSession.set(session);
        }
        boolean isNew = false;

        T var19;
        try {
            this.enableFilters(session);
            Session sessionToExpose = !enforceNativeSession && !this.isExposeNativeSession() ? this.createSessionProxy(session) : session;
            var19 = action.doInHibernate(sessionToExpose);
        } catch (HibernateException var15) {
            throw SessionFactoryUtils.convertHibernateAccessException(var15);
        } catch (PersistenceException var16) {
            Throwable var7 = var16.getCause();
            if (var7 instanceof HibernateException hibernateEx) {
                throw SessionFactoryUtils.convertHibernateAccessException(hibernateEx);
            }

            throw var16;
        } catch (RuntimeException var17) {
            throw var17;
        } finally {
            if (isNew) {
                SessionFactoryUtils.closeSession(session);
            } else {
                this.disableFilters(session);
            }

        }

        return var19;
    }

    @Nullable
    public String[] getFilterNames() {
        return this.filterNames;
    }

    protected void disableFilters(Session session) {
        String[] filterNames = getFilterNames();
        if (filterNames != null) {
            for (String filterName : filterNames) {
                session.disableFilter(filterName);
            }
        }
    }

    protected void enableFilters(Session session) {
        String[] filterNames = this.getFilterNames();
        if (filterNames != null) {
            String[] var3 = filterNames;
            int var4 = filterNames.length;

            for (int var5 = 0; var5 < var4; ++var5) {
                String filterName = var3[var5];
                session.enableFilter(filterName);
            }
        }
    }

    public boolean isExposeNativeSession() {
        return this.exposeNativeSession;
    }

    public boolean isCacheQueries() {
        return this.cacheQueries;
    }

    public String getQueryCacheRegion() {
        return this.queryCacheRegion;
    }

    public void setFetchSize(int fetchSize) {
        this.fetchSize = fetchSize;
    }

    public int getFetchSize() {
        return this.fetchSize;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    public int getMaxResults() {
        return this.maxResults;
    }

    protected void prepareQuery(Query<?> queryObject) {
        if (this.isCacheQueries()) {
            queryObject.setCacheable(true);
            if (this.getQueryCacheRegion() != null) {
                queryObject.setCacheRegion(this.getQueryCacheRegion());
            }
        }

        if (this.getFetchSize() > 0) {
            queryObject.setFetchSize(this.getFetchSize());
        }

        if (this.getMaxResults() > 0) {
            queryObject.setMaxResults(this.getMaxResults());
        }

        ResourceHolderSupport sessionHolder = (ResourceHolderSupport) TransactionSynchronizationManager.getResource(this.obtainSessionFactory());
        if (sessionHolder != null && sessionHolder.hasTimeout()) {
            queryObject.setTimeout(sessionHolder.getTimeToLiveInSeconds());
        }

    }

    private class CloseSuppressingInvocationHandler implements InvocationHandler {

        private final Session target;

        public CloseSuppressingInvocationHandler(Session target) {
            this.target = target;
        }

        @Override
        @Nullable
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            // Invocation on Session interface coming in...

            return switch (method.getName()) {
                // Only consider equal when proxies are identical.
                case "equals" -> (proxy == args[0]);
                // Use hashCode of Session proxy.
                case "hashCode" -> System.identityHashCode(proxy);
                // Handle close method: suppress, not valid.
                case "close" -> null;
                default -> {
                    try {
                        // Invoke method on target Session.
                        Object retVal = method.invoke(this.target, args);

                        // If return value is a Query or Criteria, apply transaction timeout.
                        // Applies to createQuery, getNamedQuery, createCriteria.

                        if (retVal instanceof Query<?> query) {
                            prepareQuery(query);
                        }

                        yield retVal;
                    } catch (InvocationTargetException ex) {
                        throw ex.getTargetException();
                    }
                }
            };
        }
    }
}
