import com.revature.initiative.model.Initiative;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InitiativeModelTest {

    @Test
    public void testHashCode() {


        Initiative testInit = new Initiative();
        testInit.setId(123123123l);

        Initiative testInitDeux = new Initiative();
        testInitDeux.setId(123123123l);

        Assertions.assertTrue(testInit.equals(testInitDeux) && testInitDeux.equals(testInit));
        Assertions.assertTrue(testInit.hashCode() == testInitDeux.hashCode());

    }

}
