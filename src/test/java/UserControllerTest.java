import com.example.accountservice.controller.UserController;
import com.example.accountservice.dto.UserDto;
import com.example.accountservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)  // Указываем контроллер для теста
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;  // MockMvc для выполнения HTTP-запросов

    @MockBean
    private UserService userService;  // Мокаем сервис, который используется в контроллере

    // Подготовка данных перед каждым тестом
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userService)).build();
    }

    @Test
    public void testGetUserById() throws Exception {
        // Создаем DTO с помощью сеттеров
        UserDto user = new UserDto();
        user.setId(1L);
        user.setName("John Doe");
        user.setDocumentNumber("12345");

        // Мокаем сервис, чтобы вернуть подготовленные данные
        when(userService.getUserById(1L)).thenReturn(Optional.of(user));

        // Выполняем GET-запрос и проверяем результат
        mockMvc.perform(get("/api/users/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.documentNumber").value("12345"));

        // Проверяем, что сервис был вызван один раз с правильным ID
        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    public void testCreateUser() throws Exception {
        // Создаем новый DTO с помощью сеттеров
        UserDto newUser = new UserDto();
        newUser.setName("Jane Doe");
        newUser.setDocumentNumber("67890");

        UserDto createdUser = new UserDto();
        createdUser.setId(2L);
        createdUser.setName("Jane Doe");
        createdUser.setDocumentNumber("67890");

        // Мокаем сервис, чтобы при создании возвращался созданный объект
        when(userService.create(any(UserDto.class))).thenReturn(createdUser);

        // Выполняем POST-запрос с тестовыми данными
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Jane Doe\",\"documentNumber\":\"67890\",\"email\":\"jane@example.com\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.name").value("Jane Doe"))
                .andExpect(jsonPath("$.documentNumber").value("67890"));

        // Проверяем, что сервис был вызван один раз для создания пользователя
        verify(userService, times(1)).create(any(UserDto.class));
    }

    @Test
    public void testDeleteUser() throws Exception {
        // Выполняем DELETE-запрос для удаления пользователя с ID 1
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());

        // Проверяем, что сервис был вызван один раз для удаления пользователя
        verify(userService, times(1)).delete(1L);
    }
}
