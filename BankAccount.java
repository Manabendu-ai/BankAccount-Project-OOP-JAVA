import java.util.LinkedList;

class InsufficientBalanceException extends Exception{
    public InsufficientBalanceException(String message){
        super(message);
    }
}

class InvalidUser extends Exception{
    public InvalidUser(String message){
        super(message);
    }
}

public class BankAccount{
    private final int accountNumber;
    private final int pin;

    private double balance = 50000;

    static LinkedList<BankAccount> accounts = new LinkedList<BankAccount>();
    LinkedList<String> transactions = new LinkedList<String>();
    int number_of_transaction = 0;


    private BankAccount(int accNumber, int pin, double balance){
        this.accountNumber = accNumber;
        this.pin = pin;
        this.balance = balance;
    }

    public static BankAccount registerAccount(int accNumber, int pin, double balance){
        BankAccount acc = new BankAccount(accNumber, pin, balance);
        accounts.add(acc);
        System.out.println("Account registered: " + accNumber+"\n");
        return acc;
    }

    public static BankAccount LoginAccount(int accNo, int pin) throws InvalidUser{
        for(BankAccount acc : accounts){
            if((acc.accountNumber == accNo) && (acc.pin == pin)){
                System.out.println("LoggedIn successfuly!\n");
                return acc;
            }

        }
       throw new InvalidUser("Invalid Credentials!");

    }


    public void deposit(double amount){
        try{
            balance += amount;
            number_of_transaction += 1;
            System.out.println("\nAmount deposited successfull!"+toString());
            transactions.add(number_of_transaction+". amount credited: $"+amount);
        } catch(Exception e){
            System.out.println(e);
        }
        
    }

    public void withdraw(double amount){
        try{
            isValid(amount);
            balance -= amount;
            System.out.println("\nAmount Withdrawn successfull!"+toString());
            number_of_transaction += 1;
            transactions.add(number_of_transaction+". amount debited: $"+amount);
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public void transfer(BankAccount acc2, double amount){
        try{
            this.withdraw(amount);
            acc2.deposit(amount);
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public void isLegal(boolean flag) throws InvalidUser{
        if( flag == false){
            throw new InvalidUser("Invalid User!\nFunction not Allowed!");
        }
        
    }
    
    public void isValid(double amount) throws InsufficientBalanceException{
        if(amount > balance){
                throw new InsufficientBalanceException("Insufficient Balance: $"+balance);
        }
    }

    @Override
    public String toString() {
        return (
                "\nAccount Details\n"+
                "account number: "+accountNumber+"\n"+
                "balance : $"+balance
        );
    }

    public void showTransaction(){
        System.out.println("\nAccount no: "+accountNumber+" Transactions");
        for(String transaction : transactions){
            System.out.println(transaction);
        }
    }


}


class Main{
    public static void main(String[] args) {
        try{
            //Register Accounts:
            BankAccount acc1 = BankAccount.registerAccount(1111, 1234, 5000);
            BankAccount acc2 = BankAccount.registerAccount(1112, 1009, 5000);

            // Login to existing accounts;
            acc1 = BankAccount.LoginAccount(1111, 1234);
            acc2 = BankAccount.LoginAccount(1112, 1009);

            // Doing various operations
            System.out.println(acc1);
            System.out.println(acc2);

            acc1.deposit(1200);
            acc2.withdraw(3400);
            acc1.transfer(acc2, 800);

            acc1.showTransaction();
            acc2.showTransaction();

        } catch(Exception e){
            System.out.println(e);
        }
    }
}