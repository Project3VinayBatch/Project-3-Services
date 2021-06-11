import com.revature.initiative.controller.FileController;
import com.revature.initiative.controller.InitiativeController;
import com.revature.initiative.dto.FileDTO;
import com.revature.initiative.dto.InitiativeDTO;
import com.revature.initiative.enums.InitiativeState;
import com.revature.initiative.exception.InvalidTitleException;
import com.revature.initiative.repository.InitiativeRepository;
import com.revature.initiative.repository.UserInitiativesRepository;
import com.revature.initiative.service.*;
import io.swagger.models.auth.In;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class Testing {

    @Test
    public void Test1(){// For File Controler's getAllInitiative method
        FileService fileService = mock(FileService.class);
        FileController testSubject = new FileController(fileService);
        when(fileService.getFiles()).thenReturn(new ArrayList<>());
        List <FileDTO> labRats = testSubject.getAllInitiatives().getBody();
        Assertions.assertEquals(0,labRats.size());
    }

    @Test
    public void Test2(){// For File Controler's getAllInitiative method
        FileService fileService = mock(FileService.class);
        String name = "Test";
        FileController testSubject = new FileController(fileService);
        when(fileService.getFiles()).thenReturn(new ArrayList<>());
        List <FileDTO> labRats = testSubject.getAllInitiatives(name).getBody();
        Assertions.assertEquals(0,labRats.size());
    }

    @Test
    public void Test3(){// For File Controler's getAllInitiative method
        FileService fileService = mock(FileService.class);
        long test = 15;

        FileController testSubject = new FileController(fileService);
        when(fileService.getFiles()).thenReturn(new ArrayList<>());
        List <FileDTO> labRats = testSubject.getAllInitiatives(test).getBody();
        Assertions.assertEquals(0,labRats.size());
    }

    @Test
    public void Test4(){//Initiative Controller's create initiative
        InitiativeService initiativeService = mock(InitiativeService.class);
        UserInitiativeService userInitiativeService = mock(UserInitiativeService.class);
        InitiativeDTO guineaPig = new InitiativeDTO();
        InitiativeController testSubject = new InitiativeController(initiativeService,userInitiativeService);

        when(initiativeService.addInitiative(guineaPig)).thenReturn(new InitiativeDTO());
        ResponseEntity<Object> labRat = testSubject.createInitiative(guineaPig);
        Assertions.assertNotNull(labRat);
    }

//    @Test
//    public void Test5(){//Throws for above
//        InitiativeService initiativeService = mock(InitiativeService.class);
//        UserInitiativeService userInitiativeService = mock(UserInitiativeService.class);
//        InitiativeDTO guineaPig = new InitiativeDTO();
//        InitiativeController testSubject = new InitiativeController(initiativeService,userInitiativeService);
//
//        when(initiativeService.addInitiative(guineaPig)).thenReturn(new InitiativeDTO());
//        ResponseEntity<Object> labRat = testSubject.createInitiative(guineaPig);
//        Assertions.assertThrows();
//    }

    @Test
    public void Test6(){//Initiative Controller's getInitiative
        InitiativeService initiativeService = mock(InitiativeService.class);
        UserInitiativeService userInitiativeService = mock(UserInitiativeService.class);
        InitiativeController testSubject = new InitiativeController(initiativeService,userInitiativeService);

        when(initiativeService.getInitiatives()).thenReturn(new ArrayList<>());
        List<InitiativeDTO> labRats = testSubject.getAllInitiatives().getBody();
        Assertions.assertEquals(0,labRats.size());
    }

    @Test
    public void Test7(){//Initiative Controller's getInitiative with InitiativeState
        InitiativeService initiativeService = mock(InitiativeService.class);
        UserInitiativeService userInitiativeService = mock(UserInitiativeService.class);
        InitiativeController testSubject = new InitiativeController(initiativeService,userInitiativeService);

        when(initiativeService.getInitiatives(InitiativeState.COMPLETE)).thenReturn(new ArrayList<>());
        List<InitiativeDTO> labRats = testSubject.getInitiatives(InitiativeState.COMPLETE).getBody();
        Assertions.assertEquals(0,labRats.size());
    }

    @Test
    public void Test8(){//Initiative Controller's getInitiative with InitiativeState and Id as long
        InitiativeService initiativeService = mock(InitiativeService.class);
        UserInitiativeService userInitiativeService = mock(UserInitiativeService.class);
        InitiativeController testSubject = new InitiativeController(initiativeService,userInitiativeService);
        long testTube= 21;
        when(initiativeService.setInitiativeState(testTube,InitiativeState.COMPLETE)).thenReturn(new InitiativeDTO());
        InitiativeDTO labRat = testSubject.setInitiativeState(testTube,InitiativeState.COMPLETE).getBody();
        Assertions.assertNotNull(labRat);
    }




}
