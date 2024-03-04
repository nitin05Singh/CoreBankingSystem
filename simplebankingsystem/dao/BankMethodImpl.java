package simplebankingsystem.dao;

//importing important Classes
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import simplebankingsystem.models.BankAccount;

//Transfering from one account to other
public class BankMethodImpl implements BankMethods {

	@Override
	public boolean Transaction(long sender, long reciever, int amount) {
		BankAccount senderAccount = getCustomerByAccountNo(sender);
		BankAccount reciverAccount = getCustomerByAccountNo(reciever);

		boolean withdraw = withdrawAmount(senderAccount.getAccountNo(), amount);
		boolean deposit = Deposit(reciverAccount.getAccountNo(), amount);

		if (deposit && withdraw) {
			return true;
		}
		return false;
	}

	//Deposit In Account
	@Override
	public boolean Deposit(Long accountNo, int amount) {
		BankAccount account = getCustomerByAccountNo(accountNo);
		int balance = account.getAccountBalance() + amount;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/SimpleBankingSystem", "root",
					"Nitin@05");

			PreparedStatement ps = con.prepareStatement("update BankAccount set accountBalance=? where accountNo=?");

			ps.setInt(1, balance);
			ps.setLong(2, accountNo);

			int x = ps.executeUpdate();

			if (x != 0) {
				return true;
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	//Check Balance
	@Override
	public int checkBalance(Long accountNo) {
		BankAccount account = getCustomerByAccountNo(accountNo);
		return account.getAccountBalance();
	}

	//Withdraw From Account
	@Override
	public boolean withdrawAmount(Long accountNo, int amount) {
		BankAccount account = getCustomerByAccountNo(accountNo);
		int balance = account.getAccountBalance() - amount;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/SimpleBankingSystem", "root",
					"Nitin@05");

			PreparedStatement ps = con.prepareStatement("update BankAccount set accountBalance=? where accountNo=?");

			ps.setInt(1, balance);
			ps.setLong(2, accountNo);

			int x = ps.executeUpdate();

			if (x != 0) {
				return true;
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	//Add New Account
	@Override
	public boolean AddAccount(BankAccount account) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/SimpleBankingSystem", "root",
					"Nitin@05");

			PreparedStatement ps = con.prepareStatement("insert into BankAccount(bankName,"
					+ " name, age, address, contact, pin, accountBalance, accountType)" + " values(?,?,?,?,?,?,?,?)");

			ps.setString(1, account.getBankName());
			ps.setString(2, account.getName());
			ps.setInt(3, account.getAge());
			ps.setString(4, account.getAddress());
			ps.setLong(5, account.getContact());
			ps.setInt(6, account.getPin());
			ps.setInt(7, account.getAccountBalance());
			ps.setString(8, account.getAccountType());

			int isAdded = ps.executeUpdate();

			if (isAdded != 0) {
				return true;
			}
			con.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	//Login to Account
	@Override
	public boolean customerLogin(Long accountNo, int pin) {
		BankAccount account = getCustomerByAccountNo(accountNo);
		if (accountNo == account.getAccountNo() && pin == account.getPin()) {
			return true;
		}
		return false;
	}

	//Find Customer wiht Account-No
	@Override
	public BankAccount getCustomerByAccountNo(Long accountNo) {
		BankAccount account = new BankAccount();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/SimpleBankingSystem", "root",
					"Nitin@05");

			PreparedStatement ps = con.prepareStatement("Select * from BankAccount where accountNo=?");

			ps.setLong(1, accountNo);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				account.setAccountNo(rs.getLong(1));
				account.setBankName(rs.getString(2));
				account.setName(rs.getString(3));
				account.setAge(rs.getInt(4));
				account.setAddress(rs.getString(5));
				account.setContact(rs.getLong(6));
				account.setPin(rs.getInt(7));
				account.setAccountBalance(rs.getInt(8));
				account.setAccountType(rs.getString(9));
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return account;

	}

	//CHange Pin
	@Override
	public boolean changePin(Long accountNo, int newPin) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/SimpleBankingSystem", "root",
					"Nitin@05");

			PreparedStatement ps = con.prepareStatement("update BankAccount set pin=? where accountNo=?");

			ps.setInt(1, newPin);
			ps.setLong(2, accountNo);

			int x = ps.executeUpdate();

			if (x != 0) {
				return true;
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	//Delete Account
	@Override
	public boolean deleteAccount(Long accountNo) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/SimpleBankingSystem", "root",
					"Nitin@05");

			PreparedStatement ps = con.prepareStatement("delete from BankAccount where accountNo=?");
			ps.setLong(1, accountNo);

			int x = ps.executeUpdate();

			if (x != 0) {
				return true;
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	//Latest Added Account
	@Override
	public BankAccount getLatestData() {
		BankAccount account = new BankAccount();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/SimpleBankingSystem", "root",
					"Nitin@05");

			PreparedStatement ps = con.prepareStatement("Select * from BankAccount order by accountNo desc limit 1");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				account.setAccountNo(rs.getLong(1));
				account.setBankName(rs.getString(2));
				account.setName(rs.getString(3));
				account.setAge(rs.getInt(4));
				account.setAddress(rs.getString(5));
				account.setContact(rs.getLong(6));
				account.setPin(rs.getInt(7));
				account.setAccountBalance(rs.getInt(8));
				account.setAccountType(rs.getString(9));
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return account;

	}

}
