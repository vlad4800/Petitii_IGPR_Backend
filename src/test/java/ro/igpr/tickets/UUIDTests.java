package ro.igpr.tickets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

/**
 * Created by vlad4800@gmail.com on 20-Jan-17.
 */
public class UUIDTests {

    private static final Logger LOG = LogManager.getLogger("");

    @Test
    public void validateFromString() {

        String uuid = "A9C594E-98B7-4D52-A1E8-DC62BDB352BEF";

        UUID u = UUID.fromString(uuid);
        Assert.assertTrue(u.getClass().equals(UUID.class));
    }
    @Test
    public void stringPerf() {

        String formatString = "Hi %s; Hi to you %s";

        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            String s = String.format(formatString, i, +i * 2);
        }

        long end = System.currentTimeMillis();
        LOG.info("Format = " + ((end - start)) + " millisecond");

        start = System.currentTimeMillis();

        for (int i = 0; i < 1000000; i++) {
            String s = "Hi " + i + "; Hi to you " + i * 2;
        }

        end = System.currentTimeMillis();

        LOG.info("Concatenation = " + ((end - start)) + " millisecond");

        start = System.currentTimeMillis();

        for (int i = 0; i < 1000000; i++) {
            StringBuilder bldString = new StringBuilder("Hi ");
            bldString.append(i).append("; Hi to you ").append(i * 2).toString();
        }

        end = System.currentTimeMillis();

        LOG.info("String Builder = " + ((end - start)) + " millisecond");
    }
}
