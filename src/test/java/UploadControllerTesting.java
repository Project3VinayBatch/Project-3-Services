import com.revature.initiative.controller.UploadController;
import com.revature.initiative.service.UploadService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UploadControllerTesting {

    @Test
    public void TestUploadController(){

        //Creating a mock upload services and naming it amazonS3BucketService
        UploadService amazonS3BucketService = mock(UploadService.class);

        //Creating an Upload Control and calling it Test
        UploadController testSubject = new UploadController(amazonS3BucketService);
        when(amazonS3BucketService.uploadFile(null, null,null )).thenReturn("this fails");

        ResponseEntity<String> labRat = testSubject.uploadFile(null, null, null);
        Assertions.assertNotNull(labRat);
    }

}
