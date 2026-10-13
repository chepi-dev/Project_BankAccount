import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BankAccountTest {

    private static final double DELTA = 0.001;
    private BankAccount account;

    @BeforeEach
    void setUp() {
        account = new BankAccount(100000);
    }

    @Test
    @DisplayName("Saldo awal default adalah 0")
    void saldoAwalDefault() {
        assertEquals(0, new BankAccount().getBalance(), DELTA);
    }

    @Test
    @DisplayName("Konstruktor dengan saldo awal negatif melempar exception")
    void saldoAwalNegatif() {
        assertThrows(IllegalArgumentException.class,
                () -> new BankAccount(-1));
    }

    @Test
    @DisplayName("Deposit menambah saldo")
    void depositMenambahSaldo() {
        account.deposit(50000);
        assertEquals(150000, account.getBalance(), DELTA);
    }

    @Test
    @DisplayName("Deposit beberapa kali diakumulasi")
    void depositBeberapaKali() {
        account.deposit(10000);
        account.deposit(20000.5);
        assertEquals(130000.5, account.getBalance(), DELTA);
    }

    @Test
    @DisplayName("Deposit negatif melempar exception")
    void depositNegatif() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class, () -> account.deposit(-1));
        assertEquals("Jumlah deposit tidak boleh negatif", ex.getMessage());
        assertEquals(100000, account.getBalance(), DELTA);
    }

    @Test
    @DisplayName("Withdraw mengurangi saldo")
    void withdrawMengurangiSaldo() {
        account.withdraw(40000);
        assertEquals(60000, account.getBalance(), DELTA);
    }

    @Test
    @DisplayName("Withdraw sebesar saldo menghasilkan saldo 0 (batas)")
    void withdrawSeluruhSaldo() {
        account.withdraw(100000);
        assertEquals(0, account.getBalance(), DELTA);
    }

    @Test
    @DisplayName("Withdraw melebihi saldo melempar exception")
    void withdrawMelebihiSaldo() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> account.withdraw(100000.01));
        assertEquals("Saldo tidak mencukupi", ex.getMessage());
        assertEquals(100000, account.getBalance(), DELTA);
    }

    @Test
    @DisplayName("Withdraw negatif melempar exception")
    void withdrawNegatif() {
        assertThrows(IllegalArgumentException.class,
                () -> account.withdraw(-500));
    }

    @Test
    @DisplayName("Deposit lalu withdraw menghasilkan saldo yang benar")
    void depositLaluWithdraw() {
        account.deposit(25000);
        account.withdraw(75000);
        assertEquals(50000, account.getBalance(), DELTA);
    }
}
