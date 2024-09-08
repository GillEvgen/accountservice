
import com.example.accountservice.controller.AccountController;
import com.example.accountservice.model.Account;
import com.example.accountservice.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AccountControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
    }

    @Test
    public void testGetAccountByIdFound() throws Exception {
        Account account = new Account();
        account.setId(1L);
        account.setBalance(new BigDecimal("100.00"));
        account.setCurrency("USD");

        when(accountService.getAccountById(1L)).thenReturn(Optional.of(account));

        mockMvc.perform(get("/api/accounts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.balance").value(100.00))
                .andExpect(jsonPath("$.currency").value("USD"));

        verify(accountService, times(1)).getAccountById(1L);
    }

    @Test
    public void testGetAccountByIdNotFound() throws Exception {
        when(accountService.getAccountById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/accounts/1"))
                .andExpect(status().isNotFound());

        verify(accountService, times(1)).getAccountById(1L);
    }

    @Test
    public void testDepositSuccess() throws Exception {
        Account account = new Account();
        account.setId(1L);
        account.setBalance(new BigDecimal("150.00"));
        account.setCurrency("USD");

        when(accountService.deposit(anyLong(), any(BigDecimal.class))).thenReturn(account);

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/api/accounts/1/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .param("amount", "50.00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.balance").value(150.00))
                .andExpect(jsonPath("$.currency").value("USD"));

        verify(accountService, times(1)).deposit(1L, new BigDecimal("50.00"));
    }
}