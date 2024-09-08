package seviceTest;

import com.example.accountservice.model.Account;
import com.example.accountservice.model.Transaction;
import com.example.accountservice.repository.TransactionRepository;
import com.example.accountservice.service.AccountService;
import com.example.accountservice.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateTransaction() {
        Account account = new Account();
        account.setId(1L);
        account.setBalance(new BigDecimal("100.00"));

        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setAccount(account);
        transaction.setAmount(new BigDecimal("50.00"));
        transaction.setCurrency("USD");
        transaction.setTransactionDate(LocalDateTime.now());

        when(accountService.getAccountById(anyLong())).thenReturn(Optional.of(account));
        when(accountService.deposit(anyLong(), any(BigDecimal.class))).thenReturn(account);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        Transaction createdTransaction = transactionService.createTransaction(1L, new BigDecimal("50.00"), "USD");

        assertNotNull(createdTransaction);
        assertEquals(new BigDecimal("50.00"), createdTransaction.getAmount());
        assertEquals("USD", createdTransaction.getCurrency());
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    public void testCreateTransactionAccountNotFound() {
        when(accountService.getAccountById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            transactionService.createTransaction(1L, new BigDecimal("50.00"), "USD");
        });

        assertEquals("Account not found", exception.getMessage());
        verify(accountService, times(1)).getAccountById(anyLong());
    }

    @Test
    public void testGetTransactionsByAccountId() {
        Account account = new Account();
        account.setId(1L);

        Transaction transaction1 = new Transaction();
        transaction1.setId(1L);
        transaction1.setAccount(account);
        transaction1.setAmount(new BigDecimal("50.00"));
        transaction1.setCurrency("USD");

        Transaction transaction2 = new Transaction();
        transaction2.setId(2L);
        transaction2.setAccount(account);
        transaction2.setAmount(new BigDecimal("100.00"));
        transaction2.setCurrency("USD");

        when(transactionRepository.findByAccountId(anyLong())).thenReturn(List.of(transaction1, transaction2));

        List<Transaction> transactions = transactionService.getTransactionsByAccountId(1L);

        assertEquals(2, transactions.size());
        assertEquals(new BigDecimal("50.00"), transactions.get(0).getAmount());
        verify(transactionRepository, times(1)).findByAccountId(anyLong());
    }
}