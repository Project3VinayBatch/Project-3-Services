import com.revature.initiative.dto.InitiativeDTO;
import com.revature.initiative.enums.InitiativeState;
import com.revature.initiative.exception.EmptyEntity;
import com.revature.initiative.exception.InvalidTitleException;
import com.revature.initiative.model.Initiative;
import com.revature.initiative.repository.InitiativeRepository;
import com.revature.initiative.service.InitiativeService;
import com.revature.initiative.service.InitiativeServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InitServiceTesting {

    //-------------------------------Services
    @Test
    public void TestInitService1()
    {
        Initiative guineaPig = new Initiative();
        guineaPig.setCreatedById(1l);
        guineaPig.setPointOfContactId(1l);
        guineaPig.setTitle("Big boss");
        guineaPig.setDescription("This is a sea urchin");
        guineaPig.setState(InitiativeState.COMPLETE);
        guineaPig.setMembers(new HashSet<>());


        InitiativeRepository initiativeRepository = mock(InitiativeRepository.class);
        InitiativeService testSubject = new InitiativeServiceImpl(initiativeRepository,null);

        when(initiativeRepository.save(any())).thenReturn(guineaPig);
        InitiativeDTO labRat = testSubject.addInitiative(mock(InitiativeDTO.class));

        Assertions.assertEquals(guineaPig.getTitle(),labRat.getTitle());
    }
    @Test
    public void TestInitServiceThrows1()
    {
        InitiativeService testSubject = new InitiativeServiceImpl(null,null);
        Assertions.assertThrows(EmptyEntity.class,()->{
            testSubject.addInitiative(null);
        });

    }
    @Test
    public void TestInitServiceCatch1()
    {
        InitiativeRepository initiativeRepository = mock(InitiativeRepository.class);
        when(initiativeRepository.save(any(Initiative.class))).thenThrow(mock(org.springframework.dao.DataIntegrityViolationException.class));
        InitiativeService testSubject = new InitiativeServiceImpl(initiativeRepository,null);

        Assertions.assertThrows(InvalidTitleException.class,()->{
            testSubject.addInitiative(mock(InitiativeDTO.class));
        });
    }
}
