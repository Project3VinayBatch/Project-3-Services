import com.revature.initiative.controller.UserController;
import com.revature.initiative.dto.UserDTO;
import com.revature.initiative.enums.Role;
import com.revature.initiative.model.User;
import com.revature.initiative.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;


import java.util.ArrayList;
import java.util.HashSet;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTesting {

    @Test
    public void testGetLoggedInUser(){
        UserService userService = mock(UserService.class);
        UserController testSubject = new UserController(userService);
        User user = new User();
        user.setId(123l); user.setFiles(new ArrayList<>()); user.setInitiatives(new HashSet<>()); user.setRole(Role.USER);
        when(userService.findUserByUsername("Bob")).thenReturn(new UserDTO());

        ResponseEntity<UserDTO> labRat = testSubject.getLoggedInUser(mock(Authentication.class));
        Assertions.assertNotNull(labRat);
    }
}
