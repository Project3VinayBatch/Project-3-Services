import com.revature.initiative.model.File;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InitiativeModelFileTest {

    @Test
    public void testModelFileHashCode() {

        File testFile = new File();
        testFile.setId(123123123l);

        File testFileDeux = new File();
        testFileDeux.setId(123123123l);

        Assertions.assertTrue(testFile.equals(testFileDeux) && testFileDeux.equals(testFile));
        Assertions.assertTrue(testFile.hashCode() == testFileDeux.hashCode());
        Assertions.assertTrue(true, String.valueOf(testFile == (null) || testFile != testFileDeux));
        Assertions.assertTrue(testFileDeux.getClass() != testFile.getClass());
    }

}
