package simplebankingsystem.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import simplebankingsystem.dao.BankMethodImpl;
import simplebankingsystem.models.BankAccount;

public class BankClient {

	public static void main(String[] args) throws IOException {

		Scanner sc = new Scanner(System.in);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BankAccount account = new BankAccount();
		BankMethodImpl impl = new BankMethodImpl();
		boolean flag = false;

		while (true) {
			//Main Index
			System.out.println("Enter Your Choice : ");
			System.out.println(" 1.Open An Account \n 2.Already Have An Account \n 3.Do's and Dont's \n 4.Exit");
			int choice = sc.nextInt();

			switch (choice) {
			case 1:
				//Logic to Open an Account
				System.out.println("Please Enter Which Type Of Account You Want : ");
				System.out.println(" 1.Savings (Zero Banalce) \n 2.Current (Min. 2000 Rs. Required).");
				int type = sc.nextInt();
				if (type > 2) {
					System.out.println("Enter Valid Number!!");
					break;
				}

				String bankName = "Eclipse Bank";
				account.setBankName(bankName);

				System.out.println("Enter The Customer Name : ");
				String cName = br.readLine();
				account.setName(cName);

				System.out.println("Enter The Cutomer Age : ");
				int cAge = sc.nextInt();
				if (cAge < 18) {
					System.out.println("Age Should Be Greater Than 18!");
					break;
				} else {
					account.setAge(cAge);
				}

				System.out.println("Enter The Customer Address : ");
				String cAddress = br.readLine();
				account.setAddress(cAddress);

				System.out.println("Enter The Contact Number : ");
				long cContact = sc.nextLong();
				account.setContact(cContact);

				System.out.println("Set your Pin : ");
				int cPin = sc.nextInt();
				account.setPin(cPin);

				if (type == 1) {
					String cType = "Savings";
					account.setAccountType(cType);

					System.out.println("Enter The Amount You want To Deposit at The Time Opening Account : ");
					int cBalance = sc.nextInt();
					account.setAccountBalance(cBalance);
				} else {
					String cType = "Current";
					account.setAccountType(cType);

					System.out.println("Enter The Amount You want To Deposit at The Time Opening Account : ");
					int cBalance = sc.nextInt();
					if (cBalance < 2000) {
						System.out.println("For Current Account Min. Balance Should be greater than 2000");
						break;
					} else {
						account.setAccountBalance(cBalance);
					}
				}

				boolean isAdded = impl.AddAccount(account);

				if (isAdded) {
					System.out.println("Account Created!");
					BankAccount latestAccount = impl.getLatestData();
					System.out.println(latestAccount);
				} else {
					System.out.println("Try Again!!");
				}

				break;

			case 2:
				//Customer Login
				System.out.println("Enter The Account Number : ");
				long accountNo = sc.nextLong();
				BankAccount transferAccount = impl.getCustomerByAccountNo(accountNo);
				System.out.println("Enter The Pin : ");
				int pin = sc.nextInt();
				boolean isLogin = impl.customerLogin(accountNo, pin);
				if (isLogin) {
					while (true) {
						//After Login Operations
						System.out.println("Enter The Operation : ");
						System.out.println(" 1.Check Balance \n 2.Bank Transfer \n "
								+ "3.Deposit Amount \n 4.Withdraw Amount \n 5.Display Details \n 6.Change Pin"
								+ " \n 7.Close Account \n 8.Previous Menu ");
						int operation = sc.nextInt();
						switch (operation) {
						case 1:
							//Check Balance	
							int Balance = impl.checkBalance(accountNo);
							System.out.println("Account Balance is : " + Balance);
							break;

						case 2:
							//Bank Transfer	
							System.out.println("Enter The Reciever Account No : ");
							long reciever = sc.nextLong();
							System.out.println("Enter The pin : ");
							int transferPin = sc.nextInt();
							System.out.println("Enter The Amount To transfer : ");
							int transferAmount = sc.nextInt();
							if (transferPin == pin) {
								if (transferAccount.getAccountType().equalsIgnoreCase("Savings")
										&& transferAccount.getAccountBalance() > transferAmount) {
									boolean istransfered = impl.Transaction(accountNo, reciever, transferAmount);
									if (istransfered) {
										System.out.println("Transfer Successfull");
									} else {
										System.out.println("failed!!");
									}
								} else if (transferAccount.getAccountType().equalsIgnoreCase("Current")
										&& transferAccount.getAccountBalance() > transferAmount + 2000) {
									boolean istransfered = impl.Transaction(accountNo, reciever, transferAmount);
									if (istransfered) {
										System.out.println("Transfer Successfull");
									} else {
										System.out.println(
												"Failed!!  After Transfer Your Account will less than 2000 which is not valid for Current Account");
									}
								} else {
									System.out.println("Insufficient Balance!!");
									System.out.println("Your Current Balance : " + impl.checkBalance(accountNo));
								}
							} else {
								System.out.println("Wrong Pin!!");
							}
							break;

						case 3:
							//Deposit	
							System.out.println("Enter The Amount you want to Deposit : ");
							int deposit = sc.nextInt();
							boolean isDeposited = impl.Deposit(accountNo, deposit);
							if (isDeposited) {
								System.out.println("Amount Deposited Successfully!!");
								System.out.println("Your Current Balance : " + impl.checkBalance(accountNo));
							} else {
								System.out.println("failed!!!");
							}
							break;

						case 4:
							//Withdrwal	
							System.out.println("Enter The Amount you want to Withdraw : ");
							int withdraw = sc.nextInt();
							BankAccount bankWithdraw = impl.getCustomerByAccountNo(accountNo);
							if (withdraw > bankWithdraw.getAccountBalance()) {
								int need = withdraw - bankWithdraw.getAccountBalance();
								System.out.println("Insufficient Balance To Withdraw!!");
								System.out.println("You Need " + need + " More to Withdraw");
							} else {
								boolean isWithdrawl = impl.withdrawAmount(accountNo, withdraw);
								if (isWithdrawl) {
									System.out.println("Amount withdraw Successfully!!");
									System.out.println("Your Current Balance : " + impl.checkBalance(accountNo));
								} else {
									System.out.println("failed");
								}
							}
							break;

						case 5:
							//User Profile	
							BankAccount bankDisplay = impl.getCustomerByAccountNo(accountNo);
							System.out.println(bankDisplay);
							break;

						case 6:
							//Change Pin	
							System.out.println("Enter The Updated Pin : ");
							int newPin = sc.nextInt();
							boolean pinChanged = impl.changePin(accountNo, newPin);
							if (pinChanged) {
								System.out.println("Pin changed");
							} else {
								System.out.println("Failed");
							}
							break;

						case 7:
							//Delete Account
							boolean isDeleted = impl.deleteAccount(accountNo);
							if (isDeleted) {
								System.out.println("Account Deleted Succefully!!");
							} else {
								System.out.println("Failled!!!");
							}
							break;

						case 8:
							//Back To Previous Menu	
							flag = true;
							break;
						}

						if (flag) {
							flag = false;
							break;
						}
					}
				} else {
					System.out.println("Invalid Details!!");
				}
				break;
			case 3:
				//Reading External File whith FileReader	
				File file = new File("info.txt");

				if (!file.exists()) {
					file.createNewFile();
				}

				FileInputStream fis = new FileInputStream(file);

				InputStreamReader isr = new InputStreamReader(fis);

				BufferedReader brf = new BufferedReader(isr);

				String s = null;

				while (((s = brf.readLine()) != null)) {
					System.out.println(s);
				}

				brf.close();
				isr.close();
				fis.close();
				break;

			case 4:
				//Exit	
				System.out.println("Thank For Banking With Us!!");
				System.exit(0);
				sc.close();
				br.close();
			}
		}
	}

}
