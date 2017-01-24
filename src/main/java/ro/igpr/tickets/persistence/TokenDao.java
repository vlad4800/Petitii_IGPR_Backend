package ro.igpr.tickets.persistence;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ro.igpr.tickets.config.Configuration;
import ro.igpr.tickets.config.Constants;
import ro.igpr.tickets.domain.TokenEntity;
import ro.igpr.tickets.persistence.types.TokenType;
import ro.igpr.tickets.util.AuthUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TokenDao extends GenericDao {

    private static TokenDao instance;

    public static TokenDao getInstance() {
        if (instance == null) {
            instance = new TokenDao();
        }

        return instance;
    }

    public final TokenEntity getToken(String entityId) {
        return getToken(entityId, TokenType.bearer);
    }

    public final TokenEntity getToken(String entityId, TokenType type) {

        // check if we have an active token for these credentials and serve it instead of generate a new one
        TokenEntity te = this.findNonExpiredToken(entityId, type);

        if (te == null) {
            te = this.generateNewToken(entityId, type);
        }

        return te;
    }

    public final TokenEntity findNonExpiredToken(String entityId, TokenType type) {

        final Session session = sessionFactory.getCurrentSession();
        final Transaction tx = session.beginTransaction();
        try {


            final Query query = session.createQuery("from TokenEntity " +
                    "where entityId = :entityId and type = :type " +
                    "and deleteDate is null order by expiryDate desc")
                    .setMaxResults(1);

            query.setParameter(Constants.Fields.TYPE, type);
            query.setParameter(Constants.Fields.ENTITY_ID, entityId);

            final List<TokenEntity> list = query.list();
            tx.commit();
            if (list != null && list.size() > 0) {

                final TokenEntity t = list.get(0);

                final Date currentTime = new Date();
                if (t.getExpiryDate().before(currentTime)) return null;

                int expiry = Configuration.getTokenBearerExpiry();

                final TokenEntity te = new TokenEntity();
                te.setValue(t.getValue());
                te.setExpiryDate(t.getExpiryDate());
                te.setType(t.getType());
//                te.setEntityId(t.getEntityId());

                return te;
            }
        } catch (RuntimeException re) {
            tx.rollback();
            throw re;
        }
        return null;
    }

    public final TokenEntity generateNewToken(String entityId) {
        return generateNewToken(entityId, TokenType.bearer);
    }

    public final TokenEntity generateNewToken(String entityId, TokenType type) {

        // All ok, generate new token
        final String token = AuthUtil.generateAPIToken(entityId);
        if (token != null) {
            final TokenEntity te = new TokenEntity();
            final Calendar calendar = Calendar.getInstance();
            int expiry = Configuration.getTokenBearerExpiry();
            if (type.equals(TokenType.device)) {
                expiry = Configuration.getTokenDeviceExpiry();
            } else if (type.equals(TokenType.resetPassword)) {
                expiry = Configuration.getTokenResetExpiry();
            }

            calendar.add(Calendar.SECOND, expiry);
            final Date expiryDate = new Date(calendar.getTimeInMillis());

            te.setType(type);
            te.setValue(token);
            te.setExpiryDate(expiryDate);
            te.setEntityId(entityId);

            // save token in db
            final TokenEntity insertedToken = super.merge(te);

            if (insertedToken == null || insertedToken.getId() == null) {
                throw new HibernateException(Constants.Messages.TOKEN_VALIDATION_FAILED);
            }
            return te;
        }
        return null;
    }
}
