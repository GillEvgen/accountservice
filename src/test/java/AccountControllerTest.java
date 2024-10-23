import com.example.accountservice.controller.AccountController;
import com.example.accountservice.dto.AccountDto;
import com.example.accountservice.service.AccountService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {AccountController.class, AccountService.class})
@WebMvcTest(AccountController.class)  // Поднимаем контекст с веб-слоем
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;  // Инструмент для тестирования контроллера

    @Mock
    private AccountService accountService;  // Мокаем сервис

    @InjectMocks
    private AccountController accountController;  // Внедряем моки в контроллер

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();  // Инициализация MockMvc
    }

//    @Test
//    public void testGetAllAccounts() throws Exception {
//        // Данные для теста
//        AccountDto account1 = new AccountDto(1L, BigDecimal.valueOf(100.00), "USD");
//        AccountDto account2 = new AccountDto(2L, BigDecimal.valueOf(200.00), "EUR");
//
//        Pageable pageable = PageRequest.of(0, 10);
//        Page<AccountDto> accountPage = new PageImpl<>(List.of(account1, account2), pageable, 2);
//
//        // Мокаем вызов сервиса
//        when(accountService.getAllAccounts(pageable)).thenReturn(accountPage);
//
//        // Выполняем GET-запрос и проверяем результат
//        mockMvc.perform(get("/api/accounts")
//                .param("page", "0")
//                .param("size", "10"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content[0].id").value(1L))
//                .andExpect(jsonPath("$.content[0].balance").value(100.00))
//                .andExpect(jsonPath("$.content[0].currency").value("USD"))
//                .andExpect(jsonPath("$.content[1].id").value(2L))
//                .andExpect(jsonPath("$.content[1].balance").value(200.00))
//                .andExpect(jsonPath("$.content[1].currency").value("EUR"));
//
//        verify(accountService, times(1)).getAllAccounts(pageable);  // Проверяем, что сервис вызван 1 раз
//    }
//
//    @Test
//    public void testGetAccountById() throws Exception {
//        // Данные для теста
//        AccountDto account = new AccountDto(1L, BigDecimal.valueOf(100.00), "USD");
//
//        // Мокаем вызов сервиса
//        when(accountService.getAccountById(1L)).thenReturn(account);
//
//        // Выполняем GET-запрос и проверяем результат
//        mockMvc.perform(get("/api/accounts/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1L))
//                .andExpect(jsonPath("$.balance").value(100.00))
//                .andExpect(jsonPath("$.currency").value("USD"));
//
//        verify(accountService, times(1)).getAccountById(1L);  // Проверяем, что сервис вызван 1 раз
//    }

    @Test
    public void testCreateAccount() throws Exception {
        // Создаем объект AccountDto с помощью сеттеров
        AccountDto newAccount = new AccountDto();
        newAccount.setBalance(BigDecimal.valueOf(300.00));
        newAccount.setCurrency("GBP");

        // Создаем объект для ответа (createdAccount)
        AccountDto createdAccount = new AccountDto();
        createdAccount.setId(3L);  // Устанавливаем ID для созданного аккаунта
        createdAccount.setBalance(BigDecimal.valueOf(300.00));
        createdAccount.setCurrency("GBP");

        // Мокаем вызов сервиса
        when(accountService.create(any(AccountDto.class))).thenReturn(createdAccount);

        // Выполняем POST-запрос и проверяем результат
        mockMvc.perform(post("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"balance\": 300.00, \"currency\": \"GBP\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3L))
                .andExpect(jsonPath("$.balance").value(300.00))
                .andExpect(jsonPath("$.currency").value("GBP"));

        // Проверяем, что сервис вызван 1 раз
        verify(accountService, times(1)).create(any(AccountDto.class));
    }
}