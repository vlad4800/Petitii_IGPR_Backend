package ro.igpr.tickets.persistence;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import ro.igpr.tickets.domain.BaseEntity;

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
