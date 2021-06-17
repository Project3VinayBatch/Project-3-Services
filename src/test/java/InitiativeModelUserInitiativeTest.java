import com.revature.initiative.model.UserInitiative;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InitiativeModelUserInitiativeTest {

    @Test
    public void testModelUserHashCode() {

        UserInitiative testUserInitiative = new UserInitiative();
        testUserInitiative.setId(123123123l);

        UserInitiative testUserInitiativeDeux = new UserInitiative();
        testUserInitiativeDeux.setId(123123123l);

        Assertions.assertTrue(testUserInitiative.equals(testUserInitiativeDeux) && testUserInitiativeDeux.equals(testUserInitiative));
        Assertions.assertTrue(testUserInitiative.hashCode() == testUserInitiativeDeux.hashCode());

    }

}
