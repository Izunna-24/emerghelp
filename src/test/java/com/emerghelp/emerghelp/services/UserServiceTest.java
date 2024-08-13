package com.emerghelp.emerghelp.services;

import com.emerghelp.emerghelp.data.constants.Role;
import com.emerghelp.emerghelp.data.models.User;
import com.emerghelp.emerghelp.data.repositories.MedicRequestRepository;
import com.emerghelp.emerghelp.data.repositories.UserRepository;
import com.emerghelp.emerghelp.dtos.requests.RegisterUserRequest;
import com.emerghelp.emerghelp.dtos.responses.RegisterUserResponse;
import com.emerghelp.emerghelp.dtos.responses.ViewProfileResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TextNode;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jackson.jsonpointer.JsonPointerException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.fge.jsonpatch.ReplaceOperation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Set;

import static com.emerghelp.emerghelp.data.constants.Gender.FEMALE;
import static com.emerghelp.emerghelp.data.constants.Gender.UNDEFINED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(scripts = "/db/data.sql")
public class UserServiceTest {


    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MedicRequestRepository medicRequestRepository;

    @Test
    @DisplayName("test that user can be registered on the system")
    public void registerTest() {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setFirstName("Jumoke");
        request.setLastName("Joseph");
        request.setEmail("ridrijul22@gfum.com");
        request.setPassword("password");
        request.setGender(UNDEFINED);
        request.setPhoneNumber("09078480034");
        RegisterUserResponse response = userService.register(request);
        assertNotNull(response);
        assertTrue(response.getMessage().contains("success"));
    }
    @Test
    @DisplayName("test that user can view profile")
    public void viewProfileTest() {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setFirstName("Jumoke");
        request.setLastName("Joseph");
        request.setEmail("josephfeyisetan123@gmail.com");
        request.setPassword("password");
        request.setGender(FEMALE);
        request.setPhoneNumber("09078480034");
        RegisterUserResponse response = userService.register(request);
        assertNotNull(response);
        assertTrue(response.getMessage().contains("success"));

        ViewProfileResponse viewProfileResponse = userService.viewProfile(response.getId());
        assertEquals("Jumoke", viewProfileResponse.getFirstName());
        assertEquals("Joseph", viewProfileResponse.getLastName());
        assertEquals("09078480034", viewProfileResponse.getPhoneNumber());
        assertEquals("josephfeyisetan123@gmail.com", viewProfileResponse.getEmail());
        assertEquals(FEMALE, viewProfileResponse.getGender());
    }


    @Test
    @DisplayName("test that user can update profile using JSON Patch")
    public void updateProfileUsingJsonPatchTest() throws JsonPatchException, JsonPointerException {
        User user = userService.getById(202L);

        ObjectMapper objectMapper = new ObjectMapper();
        List<JsonPatchOperation> operations = List.of(
                new ReplaceOperation(new JsonPointer("/firstName"), new TextNode("Olajumoke")),
                new ReplaceOperation(new JsonPointer("/lastName"), new TextNode("Feyishetan")),
                new ReplaceOperation(new JsonPointer("/phoneNumber"), new TextNode("08087163671")),
                new ReplaceOperation(new JsonPointer("/email"), new TextNode("jummy@gmail.com")),
                new ReplaceOperation(new JsonPointer("/gender"), new TextNode(UNDEFINED.name())),
                new ReplaceOperation(new JsonPointer("/roles"), objectMapper.valueToTree(Set.of(Role.USER)))
        );
        JsonPatch jsonPatch = new JsonPatch(operations);
        userService.updateProfile(202L, jsonPatch);
        User updatedProfile = userService.getById(202L);
        assertThat(updatedProfile.getFirstName()).isEqualTo("Olajumoke");
        assertThat(updatedProfile.getLastName()).isEqualTo("Feyishetan");
        assertThat(updatedProfile.getPhoneNumber()).isEqualTo("08087163671");
        assertThat(updatedProfile.getEmail()).isEqualTo("jummy@gmail.com");
        assertThat(updatedProfile.getGender()).isEqualTo(UNDEFINED);
        assertThat(updatedProfile.getRoles()).containsExactly(Role.USER);
    }




}

