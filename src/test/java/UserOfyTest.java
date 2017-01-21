import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.Closeable;

import entity.User;
import junit.framework.TestCase;
import persist.UserOfy;

import java.util.logging.Logger;

/**
 * Created by vku131 on 1/21/17.
 */
public class UserOfyTest extends TestCase {
    Logger logger = Logger.getLogger(UserOfyTest.class.getName());
    User user;
    Key<User> userKey;
    LocalServiceTestHelper helper =
            new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig()
                    .setDefaultHighRepJobPolicyUnappliedJobPercentage(100));
    private Closeable closeable;

    public void setUp() {
        logger.info("Setup start");
        helper.setUp();
        closeable = ObjectifyService.begin();
        user = new User("12345");
        userKey = UserOfy.save(user);
        logger.info("Setup end");
    }

    public void testSave() {
        assertNotNull(userKey);
    }

    public void testLoadByKey() {
        user = UserOfy.loadByKey(userKey);
        assertEquals("12345", user.getFbId());
    }

    public void tearDown() {
        logger.info("Tear down start.");
        user = null;
        userKey = null;
        helper.tearDown();
        closeable.close();
        logger.info("Tear down end.");
    }
}
