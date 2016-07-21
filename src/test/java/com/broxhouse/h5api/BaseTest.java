package com.broxhouse.h5api;
import com.broxhouse.h5api.utils.Constants;
import com.broxhouse.h5api.utils.KeyReader;
import org.junit.Before;
import org.junit.After;
/**
 * Created by Brock Berrett on 7/20/2016.
 */

public class BaseTest
{
    private long startTime;
    private long timeout = Constants.TIMEOUT;

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    @Before
    public void setUp() {
        startTime = System.currentTimeMillis();
    }

    @After
    public void tearDown() throws InterruptedException {
        long endTime = System.currentTimeMillis();
        long delta = endTime - startTime;
        System.out.println(String.format("Test took %d ms", delta));
        if (delta < timeout) {
            Thread.sleep(timeout - delta);
        }
    }
}
