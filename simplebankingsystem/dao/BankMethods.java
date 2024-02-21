package simplebankingsystem.dao;

import simplebankingsystem.models.BankAccount;

public interface BankMethods {
	boolean Transaction(long sender,long reciver, int amount);

	boolean AddAccount(BankAccount account);
	
	boolean Deposit(Long accountNo,int amount);

	int checkBalance(Long accountNo);
	
	BankAccount getCustomerByAccountNo(Long accountNo);
	
	boolean customerLogin(Long accountNo, int pin);

	boolean withdrawAmount(Long accountNo, int amount);
	
	boolean changePin(Long accountNo, int newPin);
	
	boolean deleteAccount(Long accountNo);
	
	BankAccount getLatestData();

}
