package tech.build.agregadorinvestimento.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.build.agregadorinvestimento.controller.dto.CreateUserDto;
import tech.build.agregadorinvestimento.controller.dto.UpdateUserDto;
import tech.build.agregadorinvestimento.entity.User;
import tech.build.agregadorinvestimento.repository.UserRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;
    @Captor
    private ArgumentCaptor<UUID> userUUIDCaptor;
    @InjectMocks
    private UserService userService;

    @Nested
    class createUser {
        @Test
        @DisplayName("Should create a user with success")
        void shouldGetUserByIdWithSuccessWhenOptionalIsPresent() {
            //ARRANGE
            var userId = java.util.UUID.randomUUID();
            doReturn(Optional.empty()).when(userRepository).findById(userUUIDCaptor.capture());
            //ACT
            var output = userService.getUserByID(userId.toString());
            //ASSERTION
            assertTrue(output.isEmpty());
            assertEquals(userId, userUUIDCaptor.getValue());
        }

        void shouldCreateUser() {
            //ARRANGE
            var user = new User(
                    java.util.UUID.randomUUID(),
                    "username",
                    "email@gmail.com",
                    "123",
                    null,
                    Instant.now()
            );
            doThrow(new RuntimeException()).when(userRepository).save(userArgumentCaptor.capture());
            var input = new CreateUserDto("username", "email@gmail.com", "123");
            //ACT & ASSERTION
            assertThrows(RuntimeException.class, () -> userService.createUser(input));
            var userCaptured = userArgumentCaptor.getValue();
            assertEquals(input.username(), userCaptured.getUsername());
            assertEquals(input.email(), userCaptured.getEmail());
            assertEquals(input.password(), userCaptured.getPassword());


        }
    }

    @Nested
    class getUserById {
        @Test
        void shouldGetUserByIdwithSuccess() {
            //ARRANGE
            var user = new User(
                    java.util.UUID.randomUUID(),
                    "username",
                    "email@gmail.com",
                    "123",
                    null,
                    Instant.now()
            );
            doReturn(Optional.of(user))
                    .when(userRepository).findById(userUUIDCaptor.capture());
            //ACT
            var output = userService.getUserByID(user.getUserId().toString());
            //ASSERTION
            assertTrue(output.isPresent());
            assertEquals(user.getUserId(), userUUIDCaptor.getValue());


        }
    }

    @Nested
    class listUsers {
        @Test
        void ShouldReturnAllUsers() {
            //ARRANGE
            var user = new User(
                    java.util.UUID.randomUUID(),
                    "username",
                    "email@gmail.com",
                    "123",
                    null,
                    Instant.now()
            );
            var userList = List.of(user);
            doReturn(List.of(user))
                    .when(userRepository).findAll();
            //ACT
            var output = userService.listUsuarios();
            //ASSERT
            assertNotNull(output);
            assertEquals(1,output.size());
        }
    }

    @Nested
    class deletedById {
        @Test
        @DisplayName("Should delete userWith success")
        void shouldDeleteIfExistsUserWithSuccess(){
                //ARRANGE


                doReturn(true)
                        .when(userRepository)
                        .existsById(userUUIDCaptor.capture());
                doNothing()
                        .when(userRepository)
                        .deleteById(userUUIDCaptor.capture());
                var userId = java.util.UUID.randomUUID();
                //ACT
               userService.deleteById(userId.toString());
                //ASSERTION
                var idList = userUUIDCaptor.getAllValues();
                assertEquals(userId, idList.get(0));
                assertEquals(userId, idList.get(1));

                verify(userRepository,times(1)).existsById(idList.get(0));
                verify(userRepository,times(1)).deleteById(idList.get(1));

        }
        @Test
        void shouldNotDeleteIfNotExistsUserWithSuccess(){
            //ARRANGE


            doReturn(false)
                    .when(userRepository)
                    .existsById(userUUIDCaptor.capture());
            var userId = java.util.UUID.randomUUID();
            //ACT
            userService.deleteById(userId.toString());
            //ASSERTION
            assertEquals(userId,userUUIDCaptor.getValue());


            verify(userRepository,times(1))
                    .existsById(userUUIDCaptor.getValue());
            verify(userRepository,times(0))
                    .deleteById(any());


        }

    }
    
    @Nested
    class updateById{
        @Test
        @DisplayName("Should update user when user and username and password is filled")
        void ShouldUpdateUserByIdWhenUserExistAndUsernameAndPasswordIsFilled() {
            //ARRANGE
            var updateUserDto = new UpdateUserDto(
              "newUsername",
              "new123"
            );
            var user = new User(
                    java.util.UUID.randomUUID(),
                    "username",
                    "email@gmail.com",
                    "123",
                    null,
                    Instant.now()
            );
            doReturn(Optional.of(user))
                    .when(userRepository).findById(userUUIDCaptor.capture());
            doReturn(user)
                    .when(userRepository).save(userArgumentCaptor.capture());
            //ACT
            userService. updateById(user.getUserId().toString(),updateUserDto);
            //ASSERTION
            assertEquals(user.getUserId(), userUUIDCaptor.getValue());

            var userCaptured = userArgumentCaptor.getValue();
            assertEquals(updateUserDto.username(),userCaptured.getUsername());
            assertEquals(updateUserDto.password(),userCaptured.getPassword());

            verify(userRepository,times(1)).findById(userUUIDCaptor.getValue());
            verify(userRepository,times(1)).save(user);
        }
        @Test
        @DisplayName("Should Not update user when user Not exists and username and password is Not filled")
        void ShouldNotUpdateUserWhenUserNotExistsAndUsernameAndPasswordIsNotFilled(){
            //ARRANGE
            var updateUserDto = new UpdateUserDto(
                    "newUsername",
                    "new123"
            );
            var userId = java.util.UUID.randomUUID();
            doReturn(Optional.empty())
                    .when(userRepository).findById(userUUIDCaptor.capture());

            //ACT
            userService. updateById(userId.toString(),updateUserDto);
            //ASSERTION
            assertEquals(userId, userUUIDCaptor.getValue());

            verify(userRepository,times(1)).findById(userUUIDCaptor.getValue());
        }
    }
}

