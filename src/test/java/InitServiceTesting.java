import com.revature.initiative.dto.InitiativeDTO;
import com.revature.initiative.enums.InitiativeState;
import com.revature.initiative.exception.EmptyEntity;
import com.revature.initiative.exception.InvalidTitleException;
import com.revature.initiative.model.Initiative;
import com.revature.initiative.repository.InitiativeRepository;
import com.revature.initiative.repository.UserInitiativesRepository;
import com.revature.initiative.repository.UserRepository;
import com.revature.initiative.service.InitiativeService;
import com.revature.initiative.service.InitiativeServiceImpl;
import io.swagger.models.auth.In;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InitServiceTesting {

    //-------------------------------Services
    @Test
    public void TestAddInitService1()
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
    public void TestAddInitServiceThrows1()
    {
        InitiativeService testSubject = new InitiativeServiceImpl(null,null);
        Assertions.assertThrows(EmptyEntity.class,()->{
            testSubject.addInitiative(null);
        });

    }
    @Test
    public void TestAddInitServiceCatch1()
    {
        InitiativeRepository initiativeRepository = mock(InitiativeRepository.class);
        when(initiativeRepository.save(any(Initiative.class))).thenThrow(mock(org.springframework.dao.DataIntegrityViolationException.class));
        InitiativeService testSubject = new InitiativeServiceImpl(initiativeRepository,null);

        Assertions.assertThrows(InvalidTitleException.class,()->{
            testSubject.addInitiative(mock(InitiativeDTO.class));
        });
    }

    @Test
    public void TestGetInitService()
    {
        Initiative guineaPig = new Initiative();
        guineaPig.setCreatedById(1l);
        guineaPig.setPointOfContactId(1l);
        guineaPig.setTitle("Big boss");
        guineaPig.setDescription("This is a sea urchin");
        guineaPig.setState(InitiativeState.COMPLETE);
        guineaPig.setMembers(new HashSet<>());

        long guineaPig2 = 1l;
        InitiativeRepository initiativeRepository = mock(InitiativeRepository.class);
        InitiativeService testSubject = new InitiativeServiceImpl(initiativeRepository,null);
        when(initiativeRepository.findById(guineaPig2)).thenReturn(java.util.Optional.of(guineaPig));
        InitiativeDTO labRat = testSubject.getInitiative(guineaPig2);

        Assertions.assertEquals(guineaPig.getTitle(),labRat.getTitle());
    }
    @Test
    public void TestGetInitService2()
    {
        Initiative guineaPig = new Initiative();
        guineaPig.setCreatedById(1l);
        guineaPig.setPointOfContactId(1l);
        guineaPig.setTitle("Big boss");
        guineaPig.setDescription("This is a sea urchin");
        guineaPig.setState(InitiativeState.COMPLETE);
        guineaPig.setMembers(new HashSet<>());

        String guineaPig2 = "Big boss";
        InitiativeRepository initiativeRepository = mock(InitiativeRepository.class);
        InitiativeService testSubject = new InitiativeServiceImpl(initiativeRepository,null);
        when(initiativeRepository.findByTitle(guineaPig2)).thenReturn(guineaPig);
        InitiativeDTO labRat = testSubject.getInitiative(guineaPig2);

        Assertions.assertEquals(guineaPig.getTitle(),labRat.getTitle());
    }

    @Test
    public void TestGetInitsService(){

        InitiativeRepository initiativeRepository = mock(InitiativeRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        InitiativeService testSubject = new InitiativeServiceImpl(initiativeRepository,userRepository);

        when(initiativeRepository.findAll()).thenReturn(new ArrayList<>());
        List<InitiativeDTO> labRats = testSubject.getInitiatives();
        Assertions.assertEquals(0,labRats.size());

    }

}
