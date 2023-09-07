package _BankingApp;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Scanner;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class Account {
	int balance;
	static synchronized void transfer(int amount,Connection conn,String fromLogin,String toLogin,String fromBankName,String toBankName) throws SQLException {
		int transactionId = withdraw(amount,conn,fromLogin,fromBankName);
	 	
		
		int accountId = depositWithoutTransaction(amount,conn,toLogin,toBankName);
		PreparedStatement insertStmt1 =
	             conn.prepareStatement("INSERT INTO transactions_account (account_account_id,transactions_transaction_id) VALUES (?,?)");
	             		
	     insertStmt1.setInt(1,accountId);
	     insertStmt1.setInt(2,transactionId);
	     insertStmt1.executeUpdate();
	}
	
	static int depositWithoutTransaction(int amount,Connection conn,String login,String bankName) throws SQLException {
	
	int accountId = getAccountByUserLoginAndBankName(conn,login,bankName);
	
	
	if(amount > 0) {
	PreparedStatement updateStmt =
             conn.prepareStatement("UPDATE account SET balance = balance + ? WHERE account_id = ? ");
     updateStmt.setInt(1,amount);
     updateStmt.setInt(2, accountId);
     updateStmt.executeUpdate();

	    }
	return accountId;
	}
	
	
	
	
	static synchronized void deposit(int amount,Connection conn,String login,String bankName)throws SQLException {
		int transactionId = 0;
		
		String currency = "USD";

		 int accountId = depositWithoutTransaction(amount,conn,login,bankName);
	     
	     PreparedStatement insertStmt =
	             conn.prepareStatement("INSERT INTO transactions (amount,timestemp,currency) VALUES (?, ?,?) RETURNING transaction_id");
	     Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	    
	     
	     
	     insertStmt.setInt(1,amount);
	     insertStmt.setTimestamp(2, timestamp);
	    
	    
		 insertStmt.setString(3,currency);
	  
	     ResultSet rs = insertStmt.executeQuery();
	     
		while (rs.next()) {

				 transactionId =  rs.getInt("transaction_id");
				 }
	    
	     PreparedStatement insertStmt1 =
	             conn.prepareStatement("INSERT INTO transactions_account (account_account_id,transactions_transaction_id) VALUES (?,?)");
	             		
	     insertStmt1.setInt(1,accountId);
	     insertStmt1.setInt(2,transactionId);
	     insertStmt1.executeUpdate();
	     
	   
	    	}
		
		
	
	
		static synchronized int getBalance(Connection conn,int accountId) throws SQLException {
			
			
		int balance = 0;
			
		
			PreparedStatement stmt = conn.prepareStatement("select balance from account where account_id=?");
			stmt.setInt(1, accountId);
			ResultSet rs = stmt.executeQuery();
			 while (rs.next()) {

				 balance =  rs.getInt("balance");
				 }
			 return balance;

		}
		
	
		
	
		
		static synchronized int withdraw(int amount,Connection conn,String login,String bankName)throws SQLException {

		    int transactionId = 0;	
			
			int accountId = getAccountByUserLoginAndBankName(conn,login,bankName);
			int balance = getBalance(conn,accountId);
			String currency = "USD";
			if(amount > balance) {
			System.out.println("Insufficient founds");
			}else if(amount <= balance) {
			PreparedStatement updateStmt =
		             conn.prepareStatement("UPDATE account SET balance = balance - ? WHERE account_id = ?");
		     updateStmt.setInt(1,amount);
		     updateStmt.setInt(2, accountId);
		     updateStmt.executeUpdate();
			
		     
		     PreparedStatement insertStmt =
		             conn.prepareStatement("INSERT INTO transactions (amount,timestemp,currency) VALUES (?, ?,?) RETURNING transaction_id");
		     Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		    
		     
		     insertStmt.setInt(1,amount);
		     insertStmt.setTimestamp(2, timestamp);
		    
		     insertStmt.setString(3,currency);
		   
		     ResultSet rs = insertStmt.executeQuery();
		     while (rs.next()) {

					transactionId =  rs.getInt("transaction_id");
		     
		     
			}
			
		     PreparedStatement insertStmt1 =
		             conn.prepareStatement("INSERT INTO transactions_account (account_account_id,transactions_transaction_id) VALUES (?,?)");
		             		
	         insertStmt1.setInt(1,accountId);
		     insertStmt1.setInt(2,transactionId);
		     insertStmt1.executeUpdate();
		
			   }
			return transactionId;
			}		
			
	
	
	
	 static int getAccountByUserLoginAndBankName(Connection conn,String login,String bankName) throws SQLException {

 	 PreparedStatement stmt = conn.prepareStatement("select account_id from account WHERE user_id = (SELECT user_id FROM users WHERE login = ?) AND bank_id = (select bank_id from bank WHERE name = ?) LIMIT 1");
     stmt.setString(1,login);
     stmt.setString(2, bankName);

    
    
     int accountId = 0;
    
     ResultSet rs = stmt.executeQuery();

     while (rs.next()) {

	 accountId = rs.getInt("account_id");

 }
     
     
     return accountId;
     
	 }      
	        

	

 static boolean checkIfUserExists(Connection conn,String login,String password) throws SQLException {
      PreparedStatement stmt = conn.prepareStatement("select * from users where login = ? AND password = ?");
      stmt.setString(1, login);
      stmt.setString(2, password);
      ResultSet rs = stmt.executeQuery();
      Boolean userExists = false;
      while (rs.next()) {

    	  userExists = true;
      }
      return userExists;
  }
	

	
	static void showmenu(Connection conn) throws SQLException {
		char option = '\0';
		String login = "\0";
		String password = "\0";
		String bankName = "\0";
		int accountId = 0;
		int balance = 0;
		
		System.out.println("Welcome " );
		Scanner scanner = new Scanner(System.in);
		do {
		System.out.println("Please enter login:");
		
				
				
				login = scanner.next();
		
		
				System.out.println("Please enter password:");
		
				password = scanner.next();
				
				
				
		}while(checkIfUserExists(conn,login,password) != true);
		
		
		System.out.println("Welcome " + login);
		System.out.println("Please enter your bank name:" );
		bankName = scanner.next();
		accountId = getAccountByUserLoginAndBankName(conn,login,bankName);
		
		
		System.out.println("\n");
		System.out.println("A. Check Balance");
		System.out.println("B. Deposit");
		System.out.println("C. Withdraw");
		System.out.println("D. Transfer");
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
				System.out.println("Balance: "+ getBalance(conn,accountId));
				System.out.println("--------------------------------------");
				System.out.println("\n");
				break;
				
			case 'B':
				System.out.println("--------------------------------------");
				System.out.println("Enter an amount to deposit:");
				System.out.println("--------------------------------------");
				int amount = scanner.nextInt();
				deposit(amount,conn,login,bankName);
				System.out.println("\n");
				break;
				
			case 'C':
				System.out.println("--------------------------------------");
				System.out.println("Enter an amount to withdraw:");
				System.out.println("--------------------------------------");
				int amount2 = scanner.nextInt();
				withdraw(amount2,conn,login,bankName);
				System.out.println("\n");
				break;
				
			case 'D':
			
				System.out.println("--------------------------------------");
				System.out.println("Enter BankName of the recipient:");
				System.out.println("--------------------------------------");
				String recipientBankName = scanner.next();
				System.out.println("--------------------------------------");
				System.out.println("Enter login of the recipient:");
				System.out.println("--------------------------------------");
				String recipientLogin = scanner.next();
				System.out.println("--------------------------------------");
				System.out.println("Enter an amount to transfer:");
				System.out.println("--------------------------------------");
      			int amount3 = scanner.nextInt();
      			transfer(amount3,conn,login,recipientLogin,bankName,recipientBankName);
				
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


