import com.revature.initiative.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InitiativeModelUserTest {

    @Test
    public void testModelUserHashCode() {

        User testUser = new User();
        testUser.setId(123123123l);

        User testUserDeux = new User();
        testUserDeux.setId(123123123l);

        Assertions.assertTrue(testUser.equals(testUserDeux) && testUserDeux.equals(testUser));
        Assertions.assertTrue(testUser.hashCode() == testUserDeux.hashCode());

    }

}
