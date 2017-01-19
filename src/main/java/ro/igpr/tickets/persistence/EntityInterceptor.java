package ro.igpr.tickets.persistence;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import ro.igpr.tickets.config.Constants;
import ro.igpr.tickets.domain.BaseEntity;
import ro.igpr.tickets.domain.UsersEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;

public final class EntityInterceptor extends EmptyInterceptor {

    private static final long serialVersionUID = 1L;

    public final void onDelete(Object entity,
                               Serializable id,
                               Object[] state,
                               String[] propertyNames,
                               Type[] types) {
    }

    // called when an Entity gets updated.
    public final boolean onFlushDirty(Object entity,
                                      Serializable id,
                                      Object[] currentState,
                                      Object[] previousState,
                                      String[] propertyNames,
                                      Type[] types) {

        boolean somethingHasChanged = false;
        if (entity instanceof BaseEntity) {
            for (int i = 0; i < propertyNames.length; i++) {
                if ("updateDate".equals(propertyNames[i])) {
                    currentState[i] = new Date();
                    somethingHasChanged = true;
                    break;
                }
            }
        }

        if (entity instanceof UsersEntity) {

            for (int i = 0; i < propertyNames.length; i++) {

                if ("password".equals(propertyNames[i]) && currentState[i] != null) {
                    // do not re-hash the password if we have the flag to ignore it
                    if (!currentState[i].toString().equals(Constants.Values.IGNORE_PASSWORD)) {
                        final String oldPass = currentState[i].toString();
                        final String newPass = GenericDao.generatePasswordHash(oldPass);
                        currentState[i] = newPass;
                        somethingHasChanged = true;
                    } else {
                        currentState[i] = previousState[i];
                    }
                    break;
                }
            }
        }

        return somethingHasChanged;
    }

    // called on load events
    public final boolean onLoad(Object entity,
                                Serializable id,
                                Object[] state,
                                String[] propertyNames,
                                Type[] types) {
        return true;
    }

    public final boolean onSave(Object entity,
                                Serializable id,
                                Object[] state,
                                String[] propertyNames,
                                Type[] types) {

        boolean somethingHasChanged = false;
        if (entity instanceof BaseEntity) {
            for (int i = 0; i < propertyNames.length; i++) {
                if ("createDate".equals(propertyNames[i])) {
                    state[i] = new Date();
                    somethingHasChanged = true;
                    break;
                }
            }
        }
        if (entity instanceof UsersEntity) {
            for (int i = 0; i < propertyNames.length; i++) {
                if ("password".equals(propertyNames[i]) && state[i] != null) {
                    final String oldPass = state[i].toString();
                    final String newPass = GenericDao.generatePasswordHash(oldPass);
                    state[i] = newPass;
                    somethingHasChanged = true;
                    break;
                }
            }
        }
        return somethingHasChanged;
    }

    //called before commit into database
    @Override
    public final void preFlush(Iterator iterator) {
        super.preFlush(iterator);
    }

    //called after committed into database
    @Override
    public final void postFlush(Iterator iterator) {
        super.postFlush(iterator);
    }
}
