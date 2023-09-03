package _BankingApp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class User{
	
	String userId;
	String login;
	String password;
	String[] accountIds;
}