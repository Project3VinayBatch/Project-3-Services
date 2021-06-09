import com.revature.initiative.controller.FileController;
import com.revature.initiative.dto.FileDTO;
import com.revature.initiative.service.FileService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class Testing {

    @Test
    public void Test1(){


        FileService fileService = mock(FileService.class);

        FileController testSubject = new FileController(fileService);

        when(fileService.getFiles()).thenReturn(new ArrayList<>());

        List <FileDTO> labRats = testSubject.getAllInitiatives().getBody();

        Assertions.assertEquals(0,labRats.size());

    }

}
