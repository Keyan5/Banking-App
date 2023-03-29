package bank;
import java.util.PriorityQueue;

public class Home{

    public static void dashboard(Customer authorizedCustomer,PriorityQueue<Customer> customers)
    {
        System.out.println();
        System.out.println("Dashboard");
        System.out.println();
        while(authorizedCustomer!=null)
        {
            System.out.println("1. Deposit");
            System.out.println("2. Withdrawal");
            System.out.println("3. Transfer");
            System.out.println("4. Check Balance");
            System.out.println("5. Change password");
            System.out.println("6. Logout");
            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(System.console().readLine());
            switch(choice)
            {
                case 1:
                    Transactions.deposit(authorizedCustomer,0);
                    break;
                case 2:
                    Transactions.withdraw(authorizedCustomer,0);
                    break;
                case 3:
                    customers = Transactions.transfer(authorizedCustomer,customers);;
                    break;
                case 4:
                    System.out.println(authorizedCustomer.getBalance());;
                    break;
                case 5:
                    PasswordChanger.changePassword(authorizedCustomer);
                    break;
                case 6:
                    System.out.println("Logging out ... ");
                    authorizedCustomer = null;
                    break;
                default:
                    System.out.println("Invalid choice");
            }
            System.out.println();
        }
    }
}