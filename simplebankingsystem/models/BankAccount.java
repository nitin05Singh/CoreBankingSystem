package simplebankingsystem.models;

public class BankAccount {
	private long accountNo;
	private String bankName;
	private String name;
	private int age;
	private String Address;
	private long contact;
	private int pin;
	private int accountBalance;
	private String accountType;

	
	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	public long getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(long accountNo) {
		this.accountNo = accountNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(int accountBalance) {
		this.accountBalance = accountBalance;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public long getContact() {
		return contact;
	}

	public void setContact(long contact) {
		this.contact = contact;
	}

	@Override
	public String toString() {
		return "BankAccount [accountNo=" + accountNo + ", Bank Name=" + bankName + ", name=" + name + ", age=" + age
				+ ", Address=" + Address + ", contact=" + contact + ", pin=" + pin + ", accountBalance="
				+ accountBalance + ", accountType=" + accountType + "]";
	}

}
