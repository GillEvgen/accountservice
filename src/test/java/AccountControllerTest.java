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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {

    private static final String EXPECTED_ACCOUNT_JSON = "{\n" +
            "  \"id\": 1,\n" +
            "  \"balance\": 100.00,\n" +
            "  \"currency\": \"USD\"\n" +
            "}";
    
    private MockMvc mockMvc;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    @AfterEach
    public void tearDown() {
        // Проверяем, что не было дополнительных взаимодействий с accountService
        verifyNoMoreInteractions(accountService);
    }

    @Test
    public void testGetAccountById() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();

        AccountDto accountDto = new AccountDto(1L, 1L, "USD", new BigDecimal("100.00"));

        when(accountService.getAccountById(anyLong())).thenReturn(accountDto);

        // Выполняем GET запрос и проверяем, что ответ совпадает с эталонным JSON
        mockMvc.perform(get("/api/accounts/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(EXPECTED_ACCOUNT_JSON));  // Проверяем весь JSON целиком

        // Проверка вызова метода
        verify(accountService).getAccountById(1L);
    }

    @Test
    public void testCreateAccount() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();

        AccountDto accountDto = new AccountDto(1L, 1L, "USD", new BigDecimal("0.00"));

        when(accountService.createAccount(Mockito.any(AccountDto.class))).thenReturn(accountDto);

        mockMvc.perform(post("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\": 1, \"currency\": \"USD\"}"))
                .andExpect(status().isCreated())  // Ожидаем статус 201 Created
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.currency").value("USD"));

        // Проверка вызова метода
        verify(accountService).createAccount(any(AccountDto.class));
    }

    @Test
    public void testDeposit() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();

        AccountDto accountDto = new AccountDto(1L, 1L, "USD", new BigDecimal("150.00"));

        when(accountService.deposit(anyLong(), Mockito.any(BigDecimal.class))).thenReturn(accountDto);

        mockMvc.perform(put("/api/accounts/1/deposit")
                .param("amount", "50.00"))
                .andExpect(status().isOk())  // Ожидаем статус 200 OK
                .andExpect(jsonPath("$.balance").value(150.00));
    }

    @Test
    public void testDeleteAccount() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();

        mockMvc.perform(delete("/api/accounts/1"))
                .andExpect(status().isNoContent());  // Ожидаем статус 204 No Content
    }
}