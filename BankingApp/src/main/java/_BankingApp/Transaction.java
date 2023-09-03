package _BankingApp;

import java.security.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class Transaction{
	
	String transactionId;
	Integer amount;
	Timestamp timestamp;
	String currency;
	String[] accountIds;
	
	
}