import java.util.Arrays;

/**
 * This class is used to test the function of AuditableBanking.java
 * 
 * @return true if there is no problem, otherwise it will return false
 */
public class AuditableBankingTests {

	/**
	 * Test the function of processCommand method in AuditableBanking.java It
	 * compares the return value of processCommand and correct answer. If there is
	 * no problem, a message showing everything is fine will display, if not, a
	 * error message will display.
	 * 
	 * 
	 * @return true if there is no problem, otherwise it will return false
	 */
	public static boolean testProcessCommand() {

		String command = "0 1 1 0 0 0 1 1";
		boolean foundProblem = false;
		int[][] transactions = new int[][] { { 1, 10, -20, +30, -20, -20 }, // +2 overdrafts (ending balance: -20)
				{ 0, 1, 1, 1, 0, 0, 1, 1, 1, 1 }, // +2 overdrafts (ending balance: -15)
				{ 1, 115 }, // +0 overdrafts (ending balance: +100)
				{ 2, 3, 1, 0, 1 }, // +1 overdrafts (ending balance: -100)
		};
		int transactionCount = 1;
		transactionCount = AuditableBanking.processCommand(command, transactions, transactionCount);
		if (transactionCount == 2) {
			System.out.println("PASSED TEST 1/1 of TestProcessCommand!!!");
		} else {
			foundProblem = true;
			System.out.println("FAILURE: processCommand did not return 2 when transactionCount = 1");
		}
		return !foundProblem;
	}

	/**
	 * Test the function of calculateCurrentBalance method in AuditableBanking.java
	 * It compares the return value of calculateCurrentBalance and correct answer.
	 * If there is no problem, a message showing everything is fine will display.
	 * 
	 * 
	 * @return true if there is no problem, otherwise it will return false
	 */
	public static boolean testCalculateCurrentBalance() {

		boolean foundProblem = false;
		int[][] transactions = new int[][] { { 1, 10, -20, +30, -20, -20 }, // +2 overdrafts (ending balance: -20)
				{ 0, 1, 1, 1, 0, 0, 1, 1, 1, 1 }, // +2 overdrafts (ending balance: -15)
				{ 1, 115 }, // +0 overdrafts (ending balance: +100)
				{ 2, 3, 1, 0, 1 }, // +1 overdrafts (ending balance: -100)
		};
		int balance = 0;
		int transactionCount = 1;
		balance = AuditableBanking.calculateCurrentBalance(transactions, transactionCount);
		if (balance == -20) {
			System.out.println("PASSED TEST 1/4 of TestCalculateCurrentBalance!!!");
		} else {
			foundProblem = true;
			System.out.println("FAILURE: calculateCurrentBalance did not return -20 when " + "transactionCount = 1");
		}
		transactionCount = 2;
		balance = AuditableBanking.calculateCurrentBalance(transactions, transactionCount);
		if (balance == -15) {
			System.out.println("PASSED TESTS 2/4 of TestCalculateCurrentBalance!!!");
		} else {
			foundProblem = true;
			System.out.println("FAILURE: calculateCurrentBalance did not return -15 when " + "transactionCount = 2");
		}
		transactionCount = 3;
		balance = AuditableBanking.calculateCurrentBalance(transactions, transactionCount);
		if (balance == 100) {
			System.out.println("PASSED TESTS 3/4 of TestCalculateCurrentBalance!!!");
		} else {
			foundProblem = true;
			System.out.println("FAILURE: calculateCurrentBalance did not return 100 when " + "transactionCount = 3");
		}
		transactionCount = 4;
		balance = AuditableBanking.calculateCurrentBalance(transactions, transactionCount);
		if (balance == -100) {
			System.out.println("PASSED TESTS 4/4 of TestCalculateCurrentBalance!!!");
		} else {
			foundProblem = true;
			System.out.println("FAILURE: calculateCurrentBalance did not return -100 when " + "transactionCount = 4");
		}
		return !foundProblem;
	}

	/**
	 * Test the function of calculateNumberOfOverdrafts method in
	 * AuditableBanking.java It compares the return value of
	 * calculateNumberOfOverdrafts and correct answer. If there is no problem, a
	 * message showing everything is fine will display.
	 * 
	 * 
	 * @return true if there is no problem, otherwise it will return false
	 */
	public static boolean testCalculateNumberOfOverdrafts() {
		boolean foundProblem = false;
		int[][] transactions = new int[][] { { 1, 10, -20, +30, -20, -20 }, // +2 overdrafts (ending balance: -20)
				{ 0, 1, 1, 1, 0, 0, 1, 1, 1, 1 }, // +2 overdrafts (ending balance: -15)
				{ 1, 115 }, // +0 overdrafts (ending balance: +100)
				{ 2, 3, 1, 0, 1 }, // +1 overdrafts (ending balance: -100)
		};

		// test with a single transaction of the Integer Amount encoding
		int transactionCount = 1;
		int overdrafts = AuditableBanking.calculateNumberOfOverdrafts(transactions, transactionCount);
		if (overdrafts != 2) {
			System.out.println("FAILURE: calculateNumberOfOverdrafts did not return 2 when "
					+ "transactionCount = 1, and transactions contained: " + Arrays.deepToString(transactions));
			foundProblem = true;
		} else
			System.out.println("PASSED TEST 1/2 of TestCalculateNumberOfOverdrafts!!!");

		// test with four transactions: including one of each encoding
		transactionCount = 4;
		overdrafts = AuditableBanking.calculateNumberOfOverdrafts(transactions, transactionCount);
		if (overdrafts != 5) {
			System.out.println("FAILURE: calculateNumberOfOverdrafts did not return 5 when "
					+ "transactionCount = 4, and transactions contained: " + Arrays.deepToString(transactions));
			foundProblem = true;
		} else
			System.out.println("PASSED TESTS 2/2 of TestCalculateNumberOfOverdrafts!!!");

		return !foundProblem;
	}

	/**
	 * The test class starts here
	 */
	public static void main(String arg[]) {
		testProcessCommand();
		testCalculateCurrentBalance();
		testCalculateNumberOfOverdrafts();
	}
}