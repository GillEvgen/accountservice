import com.example.accountservice.controller.AccountController;
import com.example.accountservice.dto.AccountDto;
import com.example.accountservice.service.AccountService;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {
    private MockMvc mockMvc;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    @Test
    public void testGetAccountById() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();

        AccountDto accountDto = new AccountDto(1L, 1L, "USD", new BigDecimal("100.00"));

        when(accountService.getAccountById(anyLong())).thenReturn(accountDto);

        mockMvc.perform(get("/api/accounts/1"))
                .andExpect(status().isOk())  // Ожидаем статус 200 OK
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.balance").value(100.00))
                .andExpect(jsonPath("$.currency").value("USD"));
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