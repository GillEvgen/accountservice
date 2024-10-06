import com.example.accountservice.controller.TransactionController;
import com.example.accountservice.dto.TransactionDto;
import com.example.accountservice.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)  // Используем MockitoExtension для работы с моками
public class TransactionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TransactionService transactionService;  // Мокаем сервис

    @InjectMocks
    private TransactionController transactionController;  // Внедряем моки в контроллер

    @BeforeEach
    public void setUp() {
        // Инициализируем MockMvc с контроллером
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
    }

    @Test
    public void testGetTransactionsByAccountId() throws Exception {
        // Создаем DTO транзакций с корректными id
        TransactionDto transaction1 = new TransactionDto(1L, new BigDecimal("100.00"), "USD");
        TransactionDto transaction2 = new TransactionDto(2L, new BigDecimal("200.00"), "USD");

        // Создаем объект Pageable
        Pageable pageable = PageRequest.of(0, 10);

        // Создаем PageImpl, чтобы мокаутить возврат страницы
        Page<TransactionDto> page = new PageImpl<>(List.of(transaction1, transaction2), pageable, 2);

        // Мокаем вызов сервиса, чтобы вернуть список транзакций с пагинацией
        when(transactionService.getTransactionsByAccountId(anyLong(), Mockito.any(Pageable.class)))
                .thenReturn(page);

        // Выполняем GET запрос и проверяем, что поле id и amount заполнены корректно
        mockMvc.perform(get("/api/accounts/1/transactions")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L))  // Проверяем, что id = 1
                .andExpect(jsonPath("$.content[0].amount").value(100.00))  // Проверяем сумму транзакции
                .andExpect(jsonPath("$.content[0].currency").value("USD")) // Проверяем валюту
                .andExpect(jsonPath("$.content[1].id").value(2L))  // Проверяем, что id = 2
                .andExpect(jsonPath("$.content[1].amount").value(200.00))  // Проверяем сумму транзакции
                .andExpect(jsonPath("$.content[1].currency").value("USD")); // Проверяем валюту
    }
}
