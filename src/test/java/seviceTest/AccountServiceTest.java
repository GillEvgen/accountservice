package seviceTest;

import com.example.accountservice.model.Account;
import com.example.accountservice.model.User;
import com.example.accountservice.repository.AccountRepository;
import com.example.accountservice.repository.UserRepository;
import com.example.accountservice.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;  // Мокируем репозиторий счетов

    @Mock
    private UserRepository userRepository;  // Мокируем репозиторий пользователей

    @InjectMocks
    private AccountService accountService;  // Тестируемый сервис

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Инициализируем моки
    }

    @Test
    public void testCreateAccount() {
        // Подготовка данных
        User user = new User();
        user.setId(1L);
        user.setName("John Doe");

        Account account = new Account();
        account.setId(1L);
        account.setUser(user);
        account.setBalance(BigDecimal.ZERO);
        account.setCurrency("USD");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        // Выполнение метода
        Account createdAccount = accountService.createAccount(1L, "USD");

        // Проверка
        assertNotNull(createdAccount);
        assertEquals("USD", createdAccount.getCurrency());
        assertEquals(BigDecimal.ZERO, createdAccount.getBalance());
        verify(userRepository, times(1)).findById(1L);
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    public void testDeposit() {
        // Подготовка данных
        Account account = new Account();
        account.setId(1L);
        account.setBalance(new BigDecimal("100.00"));
        account.setCurrency("USD");

        when(accountRepository.findByIdForUpdate(1L)).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        // Выполнение метода
        Account updatedAccount = accountService.deposit(1L, new BigDecimal("50.00"));

        // Проверка
        assertNotNull(updatedAccount);
        assertEquals(new BigDecimal("150.00"), updatedAccount.getBalance());
        verify(accountRepository, times(1)).findByIdForUpdate(1L);
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    public void testDepositAccountNotFound() {
        // Подготовка данных
        when(accountRepository.findByIdForUpdate(anyLong())).thenReturn(Optional.empty());

        // Проверка выброса исключения
        assertThrows(RuntimeException.class, () -> accountService.deposit(1L, new BigDecimal("50.00")));

        verify(accountRepository, times(1)).findByIdForUpdate(1L);
    }
}