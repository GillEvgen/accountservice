package seviceTest;

import com.example.accountservice.model.User;
import com.example.accountservice.repository.UserRepository;
import com.example.accountservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;  // Мокируем репозиторий

    @InjectMocks
    private UserService userService;  // Внедряем UserService с замокированным репозиторием

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Инициализируем моки перед каждым тестом
    }

    @Test
    public void testCreateUser() {
        // Подготовка данных
        User user = new User();
        user.setName("John Doe");

        when(userRepository.save(any(User.class))).thenReturn(user);

        // Выполнение метода
        User createdUser = userService.createUser(user);

        // Проверка результатов
        assertNotNull(createdUser);
        assertEquals("John Doe", createdUser.getName());
        verify(userRepository, times(1)).save(user);  // Проверяем, что метод save был вызван 1 раз
    }

    @Test
    public void testGetUserById() {
        // Подготовка данных
        User user = new User();
        user.setId(1L);
        user.setName("John Doe");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Выполнение метода
        Optional<User> foundUser = userService.getUserById(1L);

        // Проверка результатов
        assertTrue(foundUser.isPresent());
        assertEquals("John Doe", foundUser.get().getName());
        verify(userRepository, times(1)).findById(1L);  // Проверяем, что findById был вызван 1 раз
    }

    @Test
    public void testGetUserByIdNotFound() {
        // Подготовка данных
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Выполнение метода
        Optional<User> foundUser = userService.getUserById(1L);

        // Проверка результатов
        assertFalse(foundUser.isPresent());
        verify(userRepository, times(1)).findById(1L);
    }
}