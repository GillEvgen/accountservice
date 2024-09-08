import com.example.accountservice.controller.UserController;
import com.example.accountservice.model.User;
import com.example.accountservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    public void testCreateUser() throws Exception {
        // Настройка MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        // Подготовка данных
        User user = new User();
        user.setId(1L);
        user.setName("John Doe");

        when(userService.createUser(any(User.class))).thenReturn(user);

        ObjectMapper objectMapper = new ObjectMapper();

        // Выполнение запроса
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("John Doe"));

        verify(userService, times(1)).createUser(any(User.class));
    }

    @Test
    public void testGetUserByIdFound() throws Exception {
        // Настройка MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        // Подготовка данных
        User user = new User();
        user.setId(1L);
        user.setName("John Doe");

        when(userService.getUserById(1L)).thenReturn(Optional.of(user));

        // Выполнение запроса
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("John Doe"));

        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    public void testGetUserByIdNotFound() throws Exception {
        // Настройка MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        when(userService.getUserById(anyLong())).thenReturn(Optional.empty());

        // Выполнение запроса
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isNotFound());

        verify(userService, times(1)).getUserById(1L);
    }
}