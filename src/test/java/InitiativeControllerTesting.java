import com.revature.initiative.controller.InitiativeController;
import com.revature.initiative.dto.InitiativeDTO;
import com.revature.initiative.enums.InitiativeState;
import com.revature.initiative.exception.DuplicateEntity;
import com.revature.initiative.exception.InvalidTitleException;
import com.revature.initiative.exception.UserNotFoundException;
import com.revature.initiative.service.InitiativeService;
import com.revature.initiative.service.UserInitiativeService;
import io.swagger.models.auth.In;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class InitiativeControllerTesting {
    @Test
    public void TestInitController1(){//Initiative Controller's create initiative
        InitiativeService initiativeService = mock(InitiativeService.class);
        UserInitiativeService userInitiativeService = mock(UserInitiativeService.class);
        InitiativeDTO guineaPig = new InitiativeDTO();
        InitiativeController testSubject = new InitiativeController(initiativeService,userInitiativeService);

        when(initiativeService.addInitiative(guineaPig)).thenReturn(new InitiativeDTO());
        ResponseEntity<Object> labRat = testSubject.createInitiative(guineaPig);
        Assertions.assertNotNull(labRat);
    }

    @Test
    public void TestInitController1ForCatch(){//Catch for above test
        InitiativeService initiativeService = mock(InitiativeService.class);
        UserInitiativeService userInitiativeService = mock(UserInitiativeService.class);
        InitiativeDTO guineaPig = new InitiativeDTO();
        InitiativeController testSubject = new InitiativeController(initiativeService,userInitiativeService);

        when(initiativeService.addInitiative(guineaPig)).thenThrow(new InvalidTitleException("Oh no"));
        ResponseEntity<Object> labRat= testSubject.createInitiative(guineaPig);
        Assertions.assertEquals("Oh no",labRat.getBody());
        Assertions.assertEquals(400,labRat.getStatusCodeValue());
    }

    @Test
    public void TestInitController2(){//Initiative Controller's getInitiative
        InitiativeService initiativeService = mock(InitiativeService.class);
        UserInitiativeService userInitiativeService = mock(UserInitiativeService.class);
        InitiativeController testSubject = new InitiativeController(initiativeService,userInitiativeService);

        when(initiativeService.getInitiatives()).thenReturn(new ArrayList<>());
        List<InitiativeDTO> labRats = testSubject.getAllInitiatives().getBody();
        Assertions.assertEquals(0,labRats.size());
    }

    @Test
    public void TestInitController3(){//Initiative Controller's getInitiative with InitiativeState
        InitiativeService initiativeService = mock(InitiativeService.class);
        UserInitiativeService userInitiativeService = mock(UserInitiativeService.class);
        InitiativeController testSubject = new InitiativeController(initiativeService,userInitiativeService);

        when(initiativeService.getInitiatives(InitiativeState.COMPLETE)).thenReturn(new ArrayList<>());
        List<InitiativeDTO> labRats = testSubject.getInitiatives(InitiativeState.COMPLETE).getBody();
        Assertions.assertEquals(0,labRats.size());
    }

//    @Test
//    public void TestInitController4(){//Initiative Controller's getInitiative with InitiativeState and Id as long
//        InitiativeService initiativeService = mock(InitiativeService.class);
//        UserInitiativeService userInitiativeService = mock(UserInitiativeService.class);
//        InitiativeController testSubject = new InitiativeController(initiativeService,userInitiativeService);
//        long testTube= 21;
//        when(initiativeService.setInitiativeState(testTube,InitiativeState.COMPLETE)).thenReturn(new InitiativeDTO());
//        InitiativeDTO labRat = testSubject.getAllInitiatives(testTube,InitiativeState.COMPLETE).getBody();
//        Assertions.assertNotNull(labRat);
//    }
    @Test
    public void TestInitController5() throws DuplicateEntity {//Initiative Controller's getAllInitiative
        InitiativeService initiativeService = mock(InitiativeService.class);
        UserInitiativeService userInitiativeService = mock(UserInitiativeService.class);
        InitiativeController testSubject = new InitiativeController(initiativeService,userInitiativeService);


        ResponseEntity<Object> someEntity = testSubject.signUp(1l,2l);
        Assertions.assertEquals(200,someEntity.getStatusCodeValue());
        verify(userInitiativeService).signUp(1l,2l);


    }
    @Test
    public void TestInitController5Catch() throws DuplicateEntity {//Initiative Controller's getAllInitiative
        InitiativeService initiativeService = mock(InitiativeService.class);
        UserInitiativeService userInitiativeService = mock(UserInitiativeService.class);
        InitiativeController testSubject = new InitiativeController(initiativeService,userInitiativeService);

        doThrow(new DuplicateEntity()).when(userInitiativeService).signUp(1l,2l);
        ResponseEntity<Object> labRat = testSubject.signUp(1l,2l);
        Assertions.assertNotNull(labRat);

    }
    @Test
    public void TestInitController6(){// For Init Controller's signOff
        InitiativeService initiativeService = mock(InitiativeService.class);
        UserInitiativeService userInitiativeService = mock(UserInitiativeService.class);

        InitiativeController testSubject = new InitiativeController(initiativeService,userInitiativeService);
        ResponseEntity<Object> someEntity = testSubject.signOff(1l,2l);
        Assertions.assertEquals(200, someEntity.getStatusCodeValue());
        verify(userInitiativeService).remove(1l,2l);
    }

    @Test
    public void TestInitController7(){//For Init Controller updateInitiativePOC
        InitiativeService initiativeService = mock(InitiativeService.class);
        UserInitiativeService userInitiativeService = mock(UserInitiativeService.class);
        InitiativeController testSubject = new InitiativeController(initiativeService,userInitiativeService);
        InitiativeDTO initiativeDTO = mock(InitiativeDTO.class);

        when(initiativeService.setInitiativePOC("Hello I exist",1321)).thenReturn(new InitiativeDTO());
        ResponseEntity<Object> labRat = testSubject.updateInitiativePOC(initiativeDTO);

    }
    @Test
    public void TestInitController7ForCatch(){ //catch for method above
        InitiativeService initiativeService = mock(InitiativeService.class);
        UserInitiativeService userInitiativeService = mock(UserInitiativeService.class);
        InitiativeDTO guineaPig = new InitiativeDTO();
        guineaPig.setTitle("King");
        guineaPig.setPointOfContact(1232l);
        InitiativeController testSubject = new InitiativeController(initiativeService,userInitiativeService);

        when(initiativeService.setInitiativePOC(anyString(),anyLong())).thenThrow(new InvalidTitleException("Oh no"));
        ResponseEntity<Object> labRat = testSubject.updateInitiativePOC(guineaPig);
        Assertions.assertEquals("Oh no", labRat.getBody());
        Assertions.assertEquals(400, labRat.getStatusCodeValue());


    }
    @Test
    public void TestInitController7ForCatch2(){ //continuation for method above
        InitiativeService initiativeService = mock(InitiativeService.class);
        UserInitiativeService userInitiativeService = mock(UserInitiativeService.class);
        InitiativeDTO guineaPig = new InitiativeDTO();
        guineaPig.setTitle("King");
        guineaPig.setPointOfContact(1232l);
        InitiativeController testSubject = new InitiativeController(initiativeService,userInitiativeService);


        when(initiativeService.setInitiativePOC(anyString(),anyLong())).thenThrow(new UserNotFoundException("Oh no again"));
        ResponseEntity<Object> labRat2 = testSubject.updateInitiativePOC(guineaPig);
        Assertions.assertEquals("Oh no again", labRat2.getBody());
        Assertions.assertEquals(400, labRat2.getStatusCodeValue());

    }
//    @Test
//    public void TestInitController1ForCatch(){//Catch for above test
//        InitiativeService initiativeService = mock(InitiativeService.class);
//        UserInitiativeService userInitiativeService = mock(UserInitiativeService.class);
//        InitiativeDTO guineaPig = new InitiativeDTO();
//        InitiativeController testSubject = new InitiativeController(initiativeService,userInitiativeService);
//
//        when(initiativeService.addInitiative(guineaPig)).thenThrow(new InvalidTitleException("Oh no"));
//        ResponseEntity<Object> labRat= testSubject.createInitiative(guineaPig);
//        Assertions.assertEquals("Oh no",labRat.getBody());
//        Assertions.assertEquals(400,labRat.getStatusCodeValue());
//    }
}