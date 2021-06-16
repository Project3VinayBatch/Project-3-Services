import com.revature.initiative.controller.FileController;
import com.revature.initiative.dto.FileDTO;
import com.revature.initiative.model.File;
import com.revature.initiative.service.FileService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FileControllerTesting {


    @Test
    public void testGetAllInitiatives(){
        FileService fileService = mock(FileService.class);
        FileController testSubject = new FileController(fileService);
        when(fileService.getFiles()).thenReturn(new ArrayList<>());
        ResponseEntity<List<FileDTO>> labRats = testSubject.getAllInitiatives();
        Assertions.assertNotNull(labRats);
    }

    @Test
    public void testGetAllInitiativesString(){
        FileService fileService = mock(FileService.class);
        FileController testSubject = new FileController(fileService);
        when(fileService.getFilesByUsername("Bob")).thenReturn(new ArrayList<>());
        ResponseEntity<List<FileDTO>> labRats = testSubject.getAllInitiatives("Bob");
        Assertions.assertNotNull(labRats);
    }
    @Test
    public void testGetAllInitiativesLong(){
        FileService fileService = mock(FileService.class);
        FileController testSubject = new FileController(fileService);
        when(fileService.getFilesByInitiativeId(21l)).thenReturn(new ArrayList<>());
        ResponseEntity<List<FileDTO>> labRats = testSubject.getAllInitiatives(21l);
        Assertions.assertNotNull(labRats);
    }
}
