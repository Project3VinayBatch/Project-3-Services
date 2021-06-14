import com.revature.initiative.dto.FileDTO;
import com.revature.initiative.repository.FileRepository;
import com.revature.initiative.repository.UserRepository;
import com.revature.initiative.service.FileService;
import com.revature.initiative.service.FileServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class FileServiceTesting {

    @Test
    public void TestFileServiceGetFiles() {

        FileRepository fileRepository = mock(FileRepository.class);
        UserRepository userRepository = mock(UserRepository.class);

        FileService testSubject = new FileServiceImpl(fileRepository, userRepository);

        //create the object and set the values of what you will be retrieving
        FileDTO guineaPig = new FileDTO();

        //when a function is called it will return an Array List
        when(fileRepository.findAll()).thenReturn(new ArrayList<>());

        //Creating a list called LabRats that is equal to all Retrieved Files
        List<FileDTO> labRats = testSubject.getFiles();

        Assertions.assertEquals(0, labRats.size());

        List<FileDTO> filesList = new ArrayList<>();
        filesList.add(guineaPig);
        FileService fileService = mock(FileService.class);
        when(fileService.getFiles()).thenReturn(filesList);
    }

    public void TestGetFilesByInitiativeId() {

        FileRepository fileRepository = mock(FileRepository.class);
        UserRepository userRepository = mock(UserRepository.class);

        FileService testSubject = new FileServiceImpl(fileRepository, userRepository);

        //create the object and set the values of what you will be retrieving
        FileDTO guineaPig = new FileDTO();

        guineaPig.setId(12312312l);
        guineaPig.setFileName("testFileName");
        guineaPig.setFileUrl("www.testurl.com");
        guineaPig.setUploadedBy(12312312l);
        guineaPig.setInitiativeId(12312312l);

        List<FileDTO> filesList  = new ArrayList<>();
        filesList.add(guineaPig);
        FileService fileService = mock(FileService.class);

        when(fileRepository.findAllByFileInitiativeId(guineaPig.getInitiativeId())).thenReturn(new ArrayList<>());

        List<FileDTO> labRats = testSubject.getFilesByInitiativeId(guineaPig.getInitiativeId());

    }
}

/*
//set the values for guineaPig so we can return
        guineaPig.setId(12312312l);
                guineaPig.setFileName("testFileName");
                guineaPig.setFileUrl("www.testurl.com");
                guineaPig.setUploadedBy(12312312l);
                guineaPig.setInitiativeId(12312312l);
*/