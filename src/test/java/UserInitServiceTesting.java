import com.revature.initiative.exception.DuplicateEntity;
import com.revature.initiative.model.UserInitiative;
import com.revature.initiative.repository.UserInitiativesRepository;
import com.revature.initiative.service.UserInitiativeService;
import com.revature.initiative.service.UserInitiativeServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import static org.mockito.Mockito.*;

public class UserInitServiceTesting {
    @Test
    public void testUserInitSignUp() throws DuplicateEntity {
        UserInitiativesRepository userInitiativesRepository = mock(UserInitiativesRepository.class);
        UserInitiativeService testSubject = new UserInitiativeServiceImpl(userInitiativesRepository);
        when(userInitiativesRepository.findByinitiativeIdAndUserId(21l,21l)).thenReturn(null);
        testSubject.signUp(21l,21l);
        verify(userInitiativesRepository,times(1)).save(any());

    }
    @Test
    public void testUserInitSignUpThrows(){
        UserInitiativesRepository userInitiativesRepository = mock(UserInitiativesRepository.class);
        UserInitiativeService testSubject = new UserInitiativeServiceImpl(userInitiativesRepository);
        when(userInitiativesRepository.findByinitiativeIdAndUserId(12l,21l)).thenReturn(new UserInitiative());
        Assertions.assertThrows(Exception.class,()->{
            testSubject.signUp(21l,12l);
        });
        verify(userInitiativesRepository,times(0)).save(any());
    }
    @Test
    public void testUserInitRemove()
    {
        UserInitiativesRepository userInitiativesRepository = mock(UserInitiativesRepository.class);
        UserInitiativeService testSubject = new  UserInitiativeServiceImpl(userInitiativesRepository);
        when(userInitiativesRepository.findByinitiativeIdAndUserId(12l,21l)).thenReturn(null);
        testSubject.remove(21l,12l);
        verify(userInitiativesRepository,times(1)).findByinitiativeIdAndUserId(12l,21l);


    }

    @Test
    public void testUserInitRemoveWithNull()
    {
        UserInitiativesRepository userInitiativesRepository = mock(UserInitiativesRepository.class);
        UserInitiativeService testSubject = new  UserInitiativeServiceImpl(userInitiativesRepository);
        when(userInitiativesRepository.findByinitiativeIdAndUserId(12l,21l)).thenReturn(new UserInitiative());
        testSubject.remove(21l,12l);
        verify(userInitiativesRepository,times(1)).findByinitiativeIdAndUserId(12l,21l);



    }


}
