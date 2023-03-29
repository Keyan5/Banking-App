package bank;
import java.lang.Comparable;
public class Customer implements Comparable<Customer>{
    private int custId;
    private int accountNo;
    private String name;
    private double balance;
    private String password;

    public Customer(int custId, int accountNo, String name, double balance, String password) {
        this.custId = custId;
        this.accountNo = accountNo;
        this.name = name;
        this.balance = balance;
        this.password = password;
    }

    public int getCustId() {
        return custId;
    }

    public int getAccountNo() {
        return accountNo;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static String encrypt(char[] password){
        for(int ind = 0; ind < password.length; ind++)
        {
            if(password[ind]=='z')
                password[ind] = 'a';
            else if(password[ind]=='Z')
                password[ind] = 'A';
            else if(password[ind]=='9')
                password[ind] = '0';
            else
                password[ind]++;
        }
        return new String(password);
    }

    public boolean validate(String password)
    {
        return this.getPassword().equals(encrypt(password.toCharArray()));
    }
    
    @Override
    public int compareTo(Customer other) {
        return Double.compare(other.getBalance(),this.getBalance());
    }


}
