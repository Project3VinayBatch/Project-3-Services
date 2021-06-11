import com.revature.initiative.controller.FileController;
import com.revature.initiative.controller.InitiativeController;
import com.revature.initiative.dto.FileDTO;
import com.revature.initiative.dto.InitiativeDTO;
import com.revature.initiative.enums.InitiativeState;
import com.revature.initiative.exception.EmptyEntity;
import com.revature.initiative.exception.InvalidTitleException;
import com.revature.initiative.model.Initiative;
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

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FileControllerTesting {


    //<Controllers
    @Test
    public void TestFilerController1(){// For File Controler's getAllInitiative method
        FileService fileService = mock(FileService.class);
        FileController testSubject = new FileController(fileService);
        when(fileService.getFiles()).thenReturn(new ArrayList<>());
        List <FileDTO> labRats = testSubject.getAllInitiatives().getBody();
        Assertions.assertEquals(0,labRats.size());
    }

    @Test
    public void TestFileController2(){// For File Controler's getAllInitiative method
        FileService fileService = mock(FileService.class);
        String name = "Test";
        FileController testSubject = new FileController(fileService);
        when(fileService.getFiles()).thenReturn(new ArrayList<>());
        List <FileDTO> labRats = testSubject.getAllInitiatives(name).getBody();
        Assertions.assertEquals(0,labRats.size());
    }

    @Test
    public void TestFileController3(){// For File Controler's getAllInitiative method
        FileService fileService = mock(FileService.class);
        long test = 15;

        FileController testSubject = new FileController(fileService);
        when(fileService.getFiles()).thenReturn(new ArrayList<>());
        List <FileDTO> labRats = testSubject.getAllInitiatives(test).getBody();
        Assertions.assertEquals(0,labRats.size());
    }










}
