import com.revature.initiative.dto.UserDTO;
import com.revature.initiative.enums.Role;
import com.revature.initiative.model.User;
import com.revature.initiative.repository.UserRepository;
import com.revature.initiative.service.UserService;
import com.revature.initiative.service.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import java.util.ArrayList;
import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTesting {
    UserRepository userRepository = mock(UserRepository.class);

    UserService testSubject = new UserServiceImpl(userRepository);

    @Test
    public void UserServiceFindUserByIdTest(){


        when(userRepository.findUserById(12l)).thenReturn(new User());

        UserDTO labRat = testSubject.findUserById(12l);
        Assertions.assertNotNull(labRat);

    }
    @Test
    public void UserServiceFindUserByUsernameTest(){

        when(userRepository.findByUsername("Pooh")).thenReturn(new User());

        UserDTO labRat = testSubject.findUserByUsername("Pooh");
        Assertions.assertNotNull(labRat);
    }

    @Test
    public void UserServiceAddUserTest(){
        User guineaPig = new User();
        guineaPig.setRole(Role.USER);
        guineaPig.setFiles(new ArrayList<>());
        guineaPig.setInitiatives(new HashSet<>());
        guineaPig.setId(12l);

        when(userRepository.save(guineaPig)).thenReturn(new User());

        UserDTO labRat = testSubject.addUser(guineaPig);
        Assertions.assertNotNull(labRat);
    }

    @Test
    public void UserServiceMapTokenUserDTO(){
        User guineaPig = new User();
        guineaPig.setRole(Role.USER);
        guineaPig.setFiles(new ArrayList<>());
        guineaPig.setInitiatives(new HashSet<>());
        guineaPig.setId(12l);

        UserDTO labRat = testSubject.mapTokenUserDTO(guineaPig);
        Assertions.assertEquals(labRat.getId(),guineaPig.getId());
        Assertions.assertEquals(labRat.getRole(),guineaPig.getRole());
        Assertions.assertNotNull(labRat);

        Assertions.assertNull(testSubject.mapTokenUserDTO(null));
        guineaPig.setRole(null);
        labRat = testSubject.mapTokenUserDTO(guineaPig);
        Assertions.assertEquals(labRat.getId(),guineaPig.getId());
        Assertions.assertEquals(labRat.getRole(),Role.USER);
        Assertions.assertNotNull(labRat);
    }
    @Test
    public void testUserServiceMapToUser(){
        UserDTO guineaPig = new UserDTO();
        guineaPig.setFiles(new ArrayList<>());
        guineaPig.setId(21l);
        guineaPig.setInitiatives(new HashSet<>());
        guineaPig.setRole(Role.USER);

        User labRat = testSubject.mapToUser(guineaPig);
        Assertions.assertEquals(labRat.getId(),guineaPig.getId());
        Assertions.assertEquals(labRat.getRole(),Role.USER);
        Assertions.assertNotNull(labRat);

        Assertions.assertNull(testSubject.mapToUser(null));
        guineaPig.setRole(null);
        labRat = testSubject.mapToUser(guineaPig);
        Assertions.assertEquals(labRat.getId(),guineaPig.getId());
        Assertions.assertEquals(labRat.getRole(),Role.USER);
        Assertions.assertNotNull(labRat);
    }
    @Test
    public void testUserServiceGetUser(){
        when(userRepository.findUserById(21l)).thenReturn(new User());
        UserDTO labRat = testSubject.getUser(21l);
        Assertions.assertNotNull(labRat);

    }






}
