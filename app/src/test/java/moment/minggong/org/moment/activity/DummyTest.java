package moment.minggong.org.moment.activity;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;

/**
 * Created by minggong on 9/19/14.
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class DummyTest {

    @Test
    public void testWhoppingComplex() {
        assertTrue(Boolean.TRUE);
    }

    @Test
    public void testOnePlusOneEqualsTwo() {
        assertThat(2 + 2, is(4));
    }
}
