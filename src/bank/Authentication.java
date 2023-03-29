package bank;
import java.io.Console;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Authentication {

    public static Customer authenticate(PriorityQueue<Customer> customers)
    {
            Scanner scanner = new Scanner(System.in);
            Console console = System.console();
            System.out.print("Enter your customer id: ");
            int custId = scanner.nextInt();
            System.out.print("Enter your password: ");
            char [] pass1 = console.readPassword();
            String password = new String(pass1);
            for(Customer customer : customers)
            {
                if(customer.getCustId() == custId)
                {
                    String encrpytedPassword = Customer.encrypt(password.toCharArray());
                    if(customer.getPassword().split(",")[0].equals(encrpytedPassword))
                        return customer;
                }
            }
            return null;
    }    
}
