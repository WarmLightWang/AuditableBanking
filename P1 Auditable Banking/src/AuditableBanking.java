import java.util.Scanner;

/**
 * The class AuditableBanking simulates a real bank account and provide 4
 * methods for users, each or the methods in turn has following functions:
 * 
 * submitTransactions: add a new transaction into the allTransactions Array.
 * 
 * processCommand: process the user's operation (a single line of String type
 * command) so as to classify the commands and call different type of methods to
 * process these commands.
 * 
 * calculateCurrentBalance: calculate the balance based on the given amount of
 * transactions.
 * 
 * calculateNumberOfOverdrafts: calculate the number of overdrafts based on the
 * given amount of transactions step by step.
 * 
 * The class start with main method, in which a loop is used to process the
 * commands line by line. Each line of command will be passed to processCommand.
 * Based on the type of the command, program will then call different methods:
 * submitTransactions (if the first operation is 0, 1, or 2),
 * calculateCurrentBalance (if the first operation is "B" or "b"),
 * calculateNumberOfOverdrafts (if the first operation is "O" or "o"), or return
 * to the main method with a special mark(allTransactionCount set to -1) so that
 * the loop in main method will end.
 */
public class AuditableBanking {

	/**
	 * Adds a transaction group to an array of transaction groups. If the
	 * allTransactions array is already full then this method will do nothing other
	 * than return allTransactionCount.
	 * 
	 * @param newTransactions      is the new transaction group being added (perfect
	 *                             size).
	 * @param allTransactions      is the collection that newTransactions is being
	 *                             added to (over size).
	 * @param allTransactionsCount is the number of transaction groups within
	 *                             allTransactions (before newTransactions is added.
	 * @return the number of transaction groups within allTransactions after
	 *         newTransactions is added.
	 */
	public static int submitTransactions(int[] newTransactions, int[][] allTransactions, int allTransactionsCount) {
		allTransactions[allTransactionsCount] = new int[newTransactions.length];
		for (int i = 0; i < newTransactions.length; i++) {
			allTransactions[allTransactionsCount][i] = newTransactions[i];
		}
		allTransactionsCount++;
		return allTransactionsCount;
	}

	/**
	 * Process the user's operation (a single line of String type command) so as to
	 * classify the commands and call different type of methods to process these
	 * commands.
	 * 
	 * Based on the type of the command, program will then call different methods:
	 * submitTransactions (if the first operation is 0, 1, or 2),
	 * calculateCurrentBalance (if the first operation is "B" or "b"),
	 * calculateNumberOfOverdrafts (if the first operation is "O" or "o").
	 * 
	 * Note that the allTransactionCount will be set to -1 if the first operation of
	 * the command is "Q" or "q", so that the return value will be -1 and a
	 * conditional statement in main method will receive the return value and
	 * terminate the program. Why use -1? Because usually the allTransactionsCount
	 * will not turn negative, it only adds up.
	 * 
	 * @param command              is a String (a single line of user's input) that
	 *                             is going to be processed.
	 * @param allTransactions      is the Array to record transactions, which has
	 *                             the same reference to the one in main method and
	 *                             can be modified.
	 * @param allTransactionsCount is the number of transaction groups within
	 *                             allTransactions.
	 * @return the number of transaction groups within allTransactions after command
	 *         is processed.
	 */
	public static int processCommand(String command, int[][] allTransactions, int allTransactionsCount) {

		String[] cmd = command.split(" ");

		if (cmd[0].equals("0") || cmd[0].equals("1") || cmd[0].equals("2")) {
			int[] cmdint = new int[cmd.length];
			for (int i = 0; i < cmd.length; i++) {
				cmdint[i] = Integer.parseInt(cmd[i]);
			}

			allTransactionsCount = submitTransactions(cmdint, allTransactions, allTransactionsCount);

		}
		if (cmd[0].equals("b") || cmd[0].equals("B")) {
			int res = calculateCurrentBalance(allTransactions, allTransactionsCount);
			System.out.println("Current Balance: " + res);
		}
		if (cmd[0].equals("o") || cmd[0].equals("O")) {
			int res = calculateNumberOfOverdrafts(allTransactions, allTransactionsCount);
			System.out.println("Number of Overdrafts: " + res);
		}
		if (cmd[0].equals("q") || cmd[0].equals("Q")) {
			System.out.println("============ Thank you for using this App!!!! ============");
			allTransactionsCount = -1;
		}
		return allTransactionsCount;
	}

	/**
	 * Calculate the balance based on the given amount of transactions. It needs the
	 * parameter allTransactionsCount to decide the range of transactions that will
	 * be included to calculate the balance.
	 * 
	 * @param allTransactions      is the collection of all transactions.
	 * @param allTransactionsCount is the number to decide the range of transactions
	 *                             that will be included to calculate the balance.
	 * @return the balance based on the given amount of transactions.
	 */
	public static int calculateCurrentBalance(int[][] allTransactions, int allTransactionsCount) {
		int balance = 0;
		for (int i = 0; i < allTransactionsCount; i++) {
			for (int j = 1; j < allTransactions[i].length; j++) {

				if (allTransactions[i][0] == 0) {
					if (allTransactions[i][j] == 1) {
						balance++;
					} else {
						balance--;
					}
				}
				if (allTransactions[i][0] == 1) {
					balance += allTransactions[i][j];
				}
				if (allTransactions[i][0] == 2) {
					balance -= allTransactions[i][1] * 20;
					balance -= allTransactions[i][2] * 40;
					balance -= allTransactions[i][3] * 80;
					balance -= allTransactions[i][4] * 100;
					break;
					/**
					 * Break here because transaction type 2 (quick withdraw) do not need to loop 4
					 * statements above have already finished the process of all quick withdraws `
					 */
				}

			}
		}
		return balance;
	}

	/**
	 * Calculate the number of overdrafts based on the given amount of transactions
	 * step by step. It needs the parameter allTransactionsCount to decide the range
	 * of transactions that will be included to calculate the number of overdrafts.
	 * 
	 * Note that when dealing with quick withdraws, the calculation of overdrafts
	 * should be step by step.
	 * 
	 * @param allTransactions      is the collection of all transactions.
	 * @param allTransactionsCount is the number to decide the range of transactions
	 *                             that will be included to calculate the number of
	 *                             overdrafts.
	 * @return the number of overdrafts based on the given amount of transactions.
	 */
	public static int calculateNumberOfOverdrafts(int[][] allTransactions, int allTransactionsCount) {
		int overdrafts = 0;
		int balance = 0;
		for (int i = 0; i < allTransactionsCount; i++) {
			for (int j = 1; j < allTransactions[i].length; j++) {

				if (allTransactions[i][0] == 0) {
					if (allTransactions[i][j] == 1) {
						balance++;
					} else {
						balance--;
						if (balance < 0) {
							overdrafts++;
						}
					}
				}

				if (allTransactions[i][0] == 1) {
					balance += allTransactions[i][j];
					if (balance < 0 && allTransactions[i][j] < 0) {
						overdrafts++;
					}
				}

				if (allTransactions[i][0] == 2) {

					for (int k = 0; k < allTransactions[i][1]; k++) {
						balance -= 20;
						if (balance < 0) {
							overdrafts++;
						}
					}

					for (int k = 0; k < allTransactions[i][2]; k++) {
						balance -= 40;
						if (balance < 0) {
							overdrafts++;
						}
					}

					for (int k = 0; k < allTransactions[i][3]; k++) {
						balance -= 80;
						if (balance < 0) {
							overdrafts++;
						}
					}

					for (int k = 0; k < allTransactions[i][4]; k++) {
						balance -= 100;
						if (balance < 0) {
							overdrafts++;
						}
					}
					break;
					/**
					 * Break here because transaction type 2 (quick withdraw) do not need to loop 4
					 * loops above have already finished the process of all quick withdraws step by
					 * step and calculate the number of overdrafts
					 */
				}
			}
		}
		return overdrafts;
	}

	/**
	 * The class start with main method, in which a loop is used to process the
	 * commands line by line, and display the interface. Each line of command will
	 * be passed to processCommand.
	 * 
	 * @see Line 95. A conditional statement is used to check if the return value of
	 *      processCommand is -1. If so, that means the program should end by
	 *      breaking the while loop.
	 */
	public static void main(String arg[]) {
		System.out.println("========== Welcome to the Auditable Banking App ==========");
		int allTransactions[][] = new int[1000][];
		int newTransactionsCount = 0;
		Scanner scnr = new Scanner(System.in);
		while (true) {
			System.out.println("COMMAND MENU:");
			System.out.println("  Submit a Transaction (enter sequence of integers separated " + "by spaces)");
			System.out.println("  Show Current [B]alance");
			System.out.println("  Show Number of [O]verdrafts");
			System.out.println("  [Q]uit Program");
			System.out.print("ENTER COMMAND: ");
			String command = scnr.nextLine();
			newTransactionsCount = processCommand(command, allTransactions, newTransactionsCount);
			if (newTransactionsCount == -1) {
				break;
			}
			System.out.println("");
		}
		scnr.close();
	}
}