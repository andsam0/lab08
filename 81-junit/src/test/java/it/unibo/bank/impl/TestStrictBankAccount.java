package it.unibo.bank.impl;

import it.unibo.bank.api.AccountHolder;
import it.unibo.bank.api.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;


public class TestStrictBankAccount {

    private final static int INITIAL_AMOUNT = 100;

    // Create a new AccountHolder and a StrictBankAccount for it each time tests are executed.
    private AccountHolder mRossi;
    private BankAccount bankAccount;

    /**
     * Prepare the tests.
     */
    @BeforeEach
    public void setUp() {
        this.mRossi = new AccountHolder("Mario", "Rossi", INITIAL_AMOUNT);
        this.bankAccount = new StrictBankAccount(this.mRossi, 0.0);
    }

    /**
     * Test the initial state of the StrictBankAccount.
     */
    @Test
    public void testInitialization() {
        assertEquals(0.0, bankAccount.getBalance());
        assertEquals(0, bankAccount.getTransactionsCount());
        assertEquals(mRossi, bankAccount.getAccountHolder());
    }

    /**
     * Perform a deposit of 100€, compute the management fees, and check that the balance is correctly reduced.
     */
    @Test
    public void testManagementFees() {
        assertFalse(bankAccount.getTransactionsCount() == 0);
        bankAccount.deposit(mRossi.getUserID(), INITIAL_AMOUNT);
        double expectedValue = bankAccount.getBalance() - (StrictBankAccount.MANAGEMENT_FEE + bankAccount.getTransactionsCount() * StrictBankAccount.TRANSACTION_FEE);
        assertTrue(bankAccount.getTransactionsCount() == 0);
        bankAccount.chargeManagementFees(mRossi.getUserID());
        assertEquals(expectedValue, bankAccount.getBalance());
        assertFalse(bankAccount.getTransactionsCount() == 1);
    }

    /**
     * Test that withdrawing a negative amount causes a failure.
     */
    @Test
    public void testNegativeWithdraw() {
        try{
            bankAccount.withdraw(mRossi.getUserID(), -122);
            fail("Is not allowed to do a negative withdraw");
        }catch(IllegalArgumentException e){
            assertEquals("Cannot withdraw a negative amount", e.getMessage());
        }
    }

    /**
     * Test that withdrawing more money than it is in the account is not allowed.
     */
    @Test
    public void testWithdrawingTooMuch() {
        bankAccount.deposit(mRossi.getUserID(), INITIAL_AMOUNT * 10);
        try{
            bankAccount.withdraw(mRossi.getUserID(), 2500);
            fail("Is not allowed to withdraw more than the balance");
        }catch(IllegalArgumentException e){
            assertEquals("Insufficient balance", e.getMessage());
        }
    }
}
