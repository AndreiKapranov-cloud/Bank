package _BankingApp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class Account {
	int balance;
	int previousTransaction;
	String customerName;
	String customerId;
	String AccountId;
	
	Account(String cName,String cId){
		customerName = cName;
		customerId = cId;
	}
	
	
	
	void deposit(int amount,Connection conn,String login,String bankName)throws SQLException {
	

		if(amount > 0) {
	balance += amount;
	previousTransaction = amount;
	
	
	
	
	
	 private static String getAccountByUserLoginAndBankName(Connection conn,String login,String bankName) throws SQLException {
	        PreparedStatement stmt = conn.prepareStatement("select account_id from account LEFT JOIN bank ON account.account_id = bank.acnt_id LEFT JOIN users ON account.account_id = users.ac_id where users.login = ? And bank.name = ?  LIMIT 1");
	        stmt.setString(1, login);
	        stmt.setString(2, bankName);
     return stmt.executeQuery().getString("account_id");
	        
	        
	        
	        ResultSet rs = stmt.executeQuery();
//	        while (rs.next()) {
//	            System.out.printf("id:%d bird:%s description:%s%n", rs.getLong("id"),
//	                    rs.getString("bird"), rs.getString("description"));
//	
//	UPDATE totals 
//	   SET total = total + 1
//	WHERE name = 'bill';

	int accountId = 
	
			select * from birds where bird=?
	 PreparedStatement updateStmt =
             conn.prepareStatement("UPDATE account SET balance = balance + ? WHERE account_id = ?");
     updateStmt.setInt(1,amount);
     updateStmt.setInt(2, accountId);
     int updatedRows = updateStmt.executeUpdate();
     System.out.printf("updated %s bird(s)%n", updatedRows);
	
		}
	
	}
	
	private static void updateBird(Connection conn) throws SQLException {
        // Similar to the previous example, we can use query params to fill in the condition
        // as well as the value to update
        PreparedStatement updateStmt =
                conn.prepareStatement("UPDATE birds SET description = ? WHERE bird = ?");
        updateStmt.setString(1, "has a red crown");
        updateStmt.setString(2, "rooster");
        int updatedRows = updateStmt.executeUpdate();
        System.out.printf("updated %s bird(s)%n", updatedRows);
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	void deposit(int amount) {
//		if(amount > 0) {
//	balance += amount;
//	previousTransaction = amount;
//		}
//	}
//	
	
	
	
	
	
	
	
	
	
	
	void withdraw(int amount) {
		if(amount > 0) {
			balance -= amount;
			previousTransaction = -amount;
		  }
    }
	
	
	
	
	
	
	
	
	
	void getPreviousTransaction() {
		if(previousTransaction > 0) {
			System.out.println("Deposited: " + previousTransaction);
		}
		else if(previousTransaction < 0) {
			System.out.println("Withdrawn: " + Math.abs(previousTransaction));
		}
		else {
			System.out.println("No transaction occured");
		}
	}
	void showmenu() {
		
		char option = '\0';
		Scanner scanner = new Scanner(System.in);
		System.out.println("Welcome " + customerName);
		System.out.println("Your ID is " + customerId);
		System.out.println("\n");
		System.out.println("A. Check Balance");
		System.out.println("B. Deposit");
		System.out.println("C. Withdraw");
		System.out.println("D. Previous transaction");
		System.out.println("E. Exit");
		
		do {
			System.out.println("======================================================");
			System.out.println("Enter an option");
			System.out.println("======================================================");
			option = scanner.next().charAt(0);
			System.out.println("\n");
			
			switch(option) {
			
			case 'A':
				System.out.println("--------------------------------------");
				System.out.println("Balance: "+ balance);
				System.out.println("--------------------------------------");
				System.out.println("\n");
				break;
				
			case 'B':
				System.out.println("--------------------------------------");
				System.out.println("Enter an amount to deposit:");
				System.out.println("--------------------------------------");
				int amount = scanner.nextInt();
				deposit(amount);
				System.out.println("\n");
				break;
				
			case 'C':
				System.out.println("--------------------------------------");
				System.out.println("Enter an amount to withdraw:");
				System.out.println("--------------------------------------");
				int amount2 = scanner.nextInt();
				withdraw(amount2);
				System.out.println("\n");
				break;
				
			case 'D':
				System.out.println("--------------------------------------");
				getPreviousTransaction();
				System.out.println("--------------------------------------");
				System.out.println("\n");
				break;
				
			case 'E':
				System.out.println("******************************************");
				break;
				
				default:
					System.out.println("Invalid option!!!Please enter again.");
					break;
					
			}
		}while(option != 'E');
		
		System.out.println("Thank you for using our services");
		scanner.close();
		
	}
			
}


