import java.util.PriorityQueue;
import bank.*;

public class Main {

    public static void main(String[] args) throws Exception 
    {
        PriorityQueue<Customer> customers = FileHandler.read();
        TopNCustomers.setCustomers(customers);
        Customer authorizedCustomer = null;
        while(true)
        {
            System.out.println("Welcome to the Bank!");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Quit");
            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(System.console().readLine());
            switch(choice)
            {
                case 1:
                    authorizedCustomer = Authentication.authenticate(customers);
                    if(authorizedCustomer==null)
                    {
                        System.out.println("Invalid credentials");
                        continue;
                    }
                    Home.dashboard(authorizedCustomer, customers);
                    break;
                case 2:
                    Customer newCustomer = Register.register(customers.size());
                    customers.add(newCustomer);
                    System.out.println("Registration successful");
                    TopNCustomers.setCustomers(customers);
                    continue;
                case 3:
                    System.out.println("Quitting... ");
                    FileHandler.write(customers);
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
        
    }
}
