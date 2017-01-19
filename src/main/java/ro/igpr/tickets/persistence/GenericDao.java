package ro.igpr.tickets.persistence;

import com.strategicgains.repoexpress.exception.ItemNotFoundException;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import ro.igpr.tickets.config.Constants;
import ro.igpr.tickets.util.PasswordHash;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.io.File;
import java.lang.reflect.Field;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Vlad on 17-Nov-14.
 */
public class GenericDao {

    private static GenericDao instance;

    @Resource(name = "sessionFactory")
    private static final SessionFactory sessionFactory = getSessionFactory();

    public static GenericDao getInstance() {
        if (instance == null) {
            instance = new GenericDao();
        }

        return instance;
    }

    public final static SessionFactory getSessionFactory() {

        if (sessionFactory == null) {
            try {
                final Configuration configuration = new Configuration();
                configuration.setInterceptor(new EntityInterceptor());
                final File configFile = new File("config/" + ro.igpr.tickets.config.Configuration.getEnvironmentName() + "/hibernate.cfg.xml");
                configuration.configure(configFile);


                return configuration.buildSessionFactory();
            } catch (Throwable ex) {
                throw new ExceptionInInitializerError(ex);
            }
        }
        return sessionFactory;
    }

    public final <T> T save(final T o) {

        final Session session = sessionFactory.getCurrentSession();
        final Transaction tx = session.beginTransaction();

        try {
            final T entity = (T) session.save(o);
            tx.commit();
            return entity;
        } catch (ConstraintViolationException ex) { // catch all javax validation exceptions here and just show the message
            tx.rollback();
            StringBuilder sb = new StringBuilder();

            for (ConstraintViolation c : ex.getConstraintViolations()) {
                sb.append(c.getPropertyPath()).append(" ").append(c.getMessage()).append(",");

            }

            throw new ValidationException(sb.toString().substring(0, sb.length() - 1));
        } catch (RuntimeException re) {
            tx.rollback();
            throw re;
        }
    }

    public final void delete(final Object object) {

        final Session session = sessionFactory.getCurrentSession();
        final Transaction tx = session.beginTransaction();
        try {
            session.delete(object);
            tx.commit();
        } catch (RuntimeException re) {
            tx.rollback();
            throw re;
        }
    }

    public final <T> T get(final Class<T> type, final String field, final Object value) {
        return this.get(type, field, value, null, null);
    }

    public final <T> T get(final Class<T> type, final String field, final Object value, final List<String> columns) {
        return this.get(type, field, value, columns, null);
    }

    /**
     * Find row by field name and value
     * Can return only specified fields
     *
     * @param type
     * @param value
     * @param field
     * @param <T>
     * @return
     */
    public final <T> T get(final Class<T> type, final String field, final Object value, final List<String> columns, Order order) {

        final Session session = sessionFactory.getCurrentSession();
        final Transaction tx = session.beginTransaction();
        try {
            final Criteria crit = session.createCriteria(type);
            crit.add(Restrictions.eq(field, value));
            crit.add(Restrictions.isNull("deleteDate"));

            // only select specified columns(or all if none is specified)
            if (columns != null) {
                final ProjectionList properties = Projections.projectionList();
                for (String column : columns) {
                    properties.add(Projections.property(column));
                }
                crit.setProjection(properties);
            }

            if (order != null)
                crit.addOrder(order);

            final List<T> entities = crit.list();
            tx.commit();
            if (entities != null && entities.size() > 0) return entities.get(0);
            return null;
        } catch (RuntimeException re) {
            tx.rollback();
            throw re;
        }
    }

    /**
     * Find a row by providing a set of criteria
     *
     * @param type
     * @param params
     * @param <T>
     * @return
     */
    public final <T> T get(final Class<T> type, final Map<String, Object> params) {

        final Session session = sessionFactory.getCurrentSession();
        final Transaction tx = session.beginTransaction();
        try {
            final Criteria crit = session.createCriteria(type);
            crit.add(Restrictions.isNull("deleteDate"));

            if (params != null) {
                for (Map.Entry<String, Object> entry : params.entrySet())
                    crit.add(Restrictions.eq(entry.getKey(), entry.getValue()));
            }

            final List<T> entities = crit.list();
            tx.commit();
            if (entities != null && entities.size() > 0) return entities.get(0);
            return null;
        } catch (RuntimeException re) {
            tx.rollback();
            throw re;
        }
    }

    public final <T> T get(final Class<T> type, final Long id) {
        return get(type, id, true);
    }

    /***/
    public final <T> T get(final Class<T> type, final Long id, boolean excludeDeleted) {

        final Session session = sessionFactory.getCurrentSession();
        final Transaction tx = session.beginTransaction();
        try {
            final Criteria crit = session.createCriteria(type);
            if (excludeDeleted)
                crit.add(Restrictions.isNull("deleteDate"));

            crit.add(Restrictions.eq("id", id));

            final List<T> entities = crit.list();
            tx.commit();
            return (entities != null && entities.size() > 0) ? entities.get(0) : null;
        } catch (RuntimeException re) {
            tx.rollback();
            throw re;
        }

    }

    /***/
    public final <T> T merge(final T o) {

        final Session session = sessionFactory.getCurrentSession();
        final Transaction tx = session.beginTransaction();
        try {
            final T entity = (T) session.merge(o);
            tx.commit();
            return entity;
        } catch (RuntimeException re) {
            tx.rollback();
            throw re;
        }
    }

    /***/
    public final <T> T mergeFromEntities(final T newEntity, Long entityId) {
        return mergeFromEntities(newEntity, entityId, Constants.Messages.OBJECT_NOT_FOUND);
    }

    /***/
    public final <T> T mergeFromEntities(final T newEntity, Long entityId, String message) {
        return mergeFromEntities(newEntity, null, entityId, message);
    }

    public final <T> T mergeFromEntities(final T newEntity, final T passedEntity, final Long entityId, final String message) {

        final Session session = sessionFactory.getCurrentSession();
        final Transaction tx = session.beginTransaction();
        try {

            T currentEntity = passedEntity;
            if (currentEntity == null) {

                currentEntity = (T) session.get(newEntity.getClass(), entityId);

                if (currentEntity == null) {
                    throw new ItemNotFoundException(message);
                }
            }
            try {
                for (Class<?> c = newEntity.getClass(); c != null; c = c.getSuperclass()) {
                    for (Field field : c.getDeclaredFields()) {
                        field.setAccessible(true);

                        Object value = field.get(newEntity);
                        if (value != null) {
                            field.set(currentEntity, value);
                        }
                    }
                }
            } catch (IllegalArgumentException e) {
                tx.rollback();
                return null;
            } catch (IllegalAccessException e) {
                tx.rollback();
                return null;
            }
            final T entity = (T) session.merge(currentEntity);
            tx.commit();

            return entity;
        } catch (RuntimeException re) {
            tx.rollback();
            throw re;
        }
    }

    /***/
    public final <T> void saveOrUpdate(final T o) {

        final Session session = sessionFactory.getCurrentSession();
        final Transaction tx = session.beginTransaction();
        try {
            session.saveOrUpdate(o);
            tx.commit();
        } catch (RuntimeException re) {
            tx.rollback();
            throw re;
        }

    }

    /**
     * Find all rows
     *
     * @param type
     * @param <T>
     * @return
     */
    public final <T> List<T> getAll(final Class<T> type) {
        return getAll(type, null, false, null);
    }

    /**
     * Find all rows by providing a set of criteria
     *
     * @param type
     * @param field
     * @param value
     * @param <T>
     * @return
     */
    public final <T> List<T> getAll(final Class<T> type, final String field, final Object value) {

        final Session session = sessionFactory.getCurrentSession();
        final Transaction tx = session.beginTransaction();
        try {
            final Criteria crit = session.createCriteria(type);
            crit.add(Restrictions.isNull("deleteDate"));
            crit.add(Restrictions.like(field, value));

//            crit.setReadOnly(true);
            crit.setCacheable(true);
            crit.setCacheRegion("DimensionQueryCache");

            final List<T> entities = crit.list();
            tx.commit();
            if (entities != null && entities.size() > 0) return entities;
            return null;
        } catch (RuntimeException re) {
            tx.rollback();
            throw re;
        }
    }

    public final <T> List<T> getAll(final Class<T> type, Order order) {
        return getAll(type, null, false, order);
    }

    public final <T> List<T> getAll(final Class<T> type, final Map<String, Object> fields, Order order) {
        return getAll(type, fields, false, order);
    }

    public final <T> List<T> getAll(final Class<T> type, final Map<String, Object> fields) {
        return getAll(type, fields, false, null);
    }

    /**
     * Find all rows by providing a set of criteria
     *
     * @param type
     * @param fields
     * @param <T>
     * @return
     */
    public final <T> List<T> getAll(final Class<T> type, final Map<String, Object> fields, boolean getDeleted, Order order) {

        final Session session = sessionFactory.getCurrentSession();
        final Transaction tx = session.beginTransaction();
        try {
            final Criteria crit = session.createCriteria(type);

            if (!getDeleted)
                crit.add(Restrictions.isNull("deleteDate"));

            if (fields != null) {
                for (Map.Entry<String, Object> entry : fields.entrySet()) {

                    if (entry.getValue().toString().contains("%")) {
                        crit.add(Restrictions.like(entry.getKey(), entry.getValue()));
                    } else if (entry.getKey().toString().endsWith("_in")) {
                        String key = entry.getKey().replace("_in", "");
                        String[] values = (String[]) entry.getValue();
                        crit.add(Restrictions.in(key, values));
                    } else {
                        crit.add(Restrictions.eq(entry.getKey(), entry.getValue()));
                    }
                }
            }

            if (order != null)
                crit.addOrder(order);

            final List<T> entities = crit.list();
            tx.commit();
            if (entities != null && entities.size() > 0) return entities;
            return null;
        } catch (RuntimeException re) {
            tx.rollback();
            throw re;
        }
    }

    public final <T> List<T> getEntitiesByIds(final Class<T> type, List<String> ids) {
        final Session session = sessionFactory.getCurrentSession();
        final Transaction tx = session.beginTransaction();
        try {

            final Criteria crit = session.createCriteria(type);
            crit.add(Restrictions.isNull("deleteDate"));

            if (ids != null)
                crit.add(Restrictions.in("id", ids));

            final List<T> items = crit.list();
            tx.commit();
            return items;
        } catch (RuntimeException re) {
            tx.rollback();
            throw re;
        }
    }

    public <T> String generateShortId(final Class<T> entityClass) {

        // generate a shortId
        for (int i = 0; i <= 100; i++) {
            final String shortId = RandomStringUtils.randomAlphanumeric(8).toLowerCase();
            if (this.get(entityClass, "id", shortId, Arrays.asList("id"), null) == null) {
                return shortId;
            }
        }
        return null;
    }

    public final <T> long count(final Class<T> type) {
        return this.count(type, null, true);
    }

    public final <T> long count(final Class<T> type, final Map<String, Object> fields) {
        return this.count(type, fields, true);
    }

    public final <T> long count(final Class<T> type, final Map<String, Object> fields, boolean excludeDeleted) {
        final Session session = sessionFactory.getCurrentSession();
        final Transaction tx = session.beginTransaction();
        try {
            final Criteria crit = session.createCriteria(type);

            if (excludeDeleted)
                crit.add(Restrictions.isNull("deleteDate"));

            if (fields != null) {
                for (Map.Entry<String, Object> entry : fields.entrySet()) {

                    if (entry.getValue().toString().contains("%")) {
                        crit.add(Restrictions.like(entry.getKey(), entry.getValue()));
                    } else if (entry.getKey().toString().endsWith("_in")) {
                        String key = entry.getKey().replace("_in", "");
                        String[] values = (String[]) entry.getValue();
                        crit.add(Restrictions.in(key, values));
                    } else {
                        crit.add(Restrictions.eq(entry.getKey(), entry.getValue()));
                    }
                }
            }

            crit.setProjection(Projections.rowCount());

            Object count = crit.uniqueResult();

            tx.commit();
            if (count != null) return (Long) count;
            return 0;
        } catch (RuntimeException re) {
            tx.rollback();
            throw re;
        }
    }

    public final static String generatePasswordHash(String realPassword) {

        if (realPassword == null || realPassword.length() == 0) return null;

        try {
            return PasswordHash.createHash(realPassword);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e1) {
            e1.printStackTrace();
        }
        return null;
    }
}
