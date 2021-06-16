import com.revature.initiative.controller.UploadController;
import com.revature.initiative.service.UploadService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UploadControllerTesting {

//     @Test
//     public void TestUploadController(){

//         //Creating a mock upload services and naming it amazonS3BucketService
//         UploadService amazonS3BucketService = mock(UploadService.class);

//         //Commenting to use later to check out uploading to the DB
//         //UploadService amazonS3BucketService = new UploadServiceImpl(new User)

//         //Creating an Upload Control and calling it Test
//         UploadController testSubject = new UploadController(amazonS3BucketService);


// //        MultipartFile multipartFile = new MockMultipartFile("testfile.tmp", "Weddingidea".getBytes());
// //        File file = new File("D:\\Brian_Folder\\Project3_BackEnd_Bkelly\\Project-3-Services\\src\\test\\testfiles\\Wedding idea.PNG");
// //        try (OutputStream os = new FileOutputStream(file)){
// //            os.write(multipartFile.getBytes());
// //        } catch (Exception e) {
// //            e.printStackTrace();
// //        }

//         String url = "https://www.returningURL.com";
//         when(amazonS3BucketService.uploadFile( null, null,null )).thenReturn(url);
//         ResponseEntity<String> labRat = testSubject.uploadFile(null , null, null);
//         Assertions.assertEquals(labRat, ResponseEntity.ok(url));
//     }

}
