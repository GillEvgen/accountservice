
import com.example.accountservice.controller.TransactionController;
import com.example.accountservice.model.Transaction;
import com.example.accountservice.service.TransactionService;
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
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TransactionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
    }

    @Test
    public void testCreateTransaction() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setAmount(new BigDecimal("50.00"));
        transaction.setCurrency("USD");
        transaction.setTransactionDate(LocalDateTime.now());

        when(transactionService.createTransaction(anyLong(), any(BigDecimal.class), anyString()))
                .thenReturn(transaction);

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/api/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .param("accountId", "1")
                .param("amount", "50.00")
                .param("currency", "USD"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.amount").value(50.00))
                .andExpect(jsonPath("$.currency").value("USD"));

        verify(transactionService, times(1))
                .createTransaction(anyLong(), any(BigDecimal.class), anyString());
    }

    @Test
    public void testGetTransactionsByAccountId() throws Exception {
        Transaction transaction1 = new Transaction();
        transaction1.setId(1L);
        transaction1.setAmount(new BigDecimal("50.00"));
        transaction1.setCurrency("USD");

        Transaction transaction2 = new Transaction();
        transaction2.setId(2L);
        transaction2.setAmount(new BigDecimal("100.00"));
        transaction2.setCurrency("USD");

        when(transactionService.getTransactionsByAccountId(anyLong()))
                .thenReturn(List.of(transaction1, transaction2));

        mockMvc.perform(get("/api/transactions/account/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].amount").value(50.00))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].amount").value(100.00));

        verify(transactionService, times(1)).getTransactionsByAccountId(1L);
    }
}