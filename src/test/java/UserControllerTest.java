import com.example.accountservice.controller.UserController;
import com.example.accountservice.dto.UserDto;
import com.example.accountservice.service.UserService;
import com.example.accountservice.type.DocumentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        // Добавляем PageableHandlerMethodArgumentResolver для поддержки пагинации
        PageableHandlerMethodArgumentResolver pageableArgumentResolver = new PageableHandlerMethodArgumentResolver();

        // Инициализируем MockMvc с контроллером и резолвером для пагинации
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setCustomArgumentResolvers(pageableArgumentResolver) // Добавляем резолвер пагинации
                .build();
    }

    @Test
    public void testGetAllUsers() throws Exception {
        // Данные для теста
        UserDto user1 = new UserDto();
        UserDto user2 = new UserDto();

        Pageable pageRequest = PageRequest.of(0, 10);
        Page<UserDto> userPage = new PageImpl<>(List.of(user1, user2), pageRequest, 2);

        // Мокаем сервис
        when(userService.getAllUsers(pageRequest)).thenReturn(userPage);

        // Выполняем GET запрос с параметрами пагинации
        mockMvc.perform(get("/api/users")
                .param("page", "0")  // Параметр "page"
                .param("size", "10"))  // Параметр "size"
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[0].name").value("User 1"))
                .andExpect(jsonPath("$.content[1].id").value(2L))
                .andExpect(jsonPath("$.content[1].name").value("User 2"));
    }

    @Test
    public void testGetUserById_Success() throws Exception {
        // Данные для теста
        UserDto user = new UserDto();

        when(userService.getUserById(anyLong())).thenReturn(Optional.of(user));

        // Выполняем GET запрос
        mockMvc.perform(get("/api/users/{userId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("User 1"));
    }

    @Test
    public void testGetUserByDocument_Success() throws Exception {
        // Данные для теста
        UserDto user = new UserDto();

        when(userService.getUserByDocument(any(String.class))).thenReturn(Optional.of(user));

        // Выполняем GET запрос
        mockMvc.perform(get("/api/users/document/{documentNumber}", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("User 1"));
    }

    @Test
    public void testCreateUser() throws Exception {
        UserDto createdUserDto = new UserDto();

        when(userService.create(any(UserDto.class))).thenReturn(createdUserDto);

        // Выполняем POST запрос
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"User 1\",\"documentNumber\":\"1\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("User 1"));
    }
}
