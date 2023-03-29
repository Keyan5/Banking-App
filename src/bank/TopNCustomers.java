package bank;

import java.util.PriorityQueue;

public class TopNCustomers {
    private static PriorityQueue<Customer> customers;
    
    public static void setCustomers(PriorityQueue<Customer> customers) {
        TopNCustomers.customers = customers;
    }

    public static PriorityQueue<Customer> getRefreshedCustomers()
    {
        PriorityQueue<Customer> refreshedCustomers = new PriorityQueue<>();
        for (Customer customer : customers) 
            refreshedCustomers.add(customer);
        return refreshedCustomers;
    }

    public static boolean isCustomerInTopN(int accountNo,int N)
    {
        int count = 0;
        PriorityQueue<Customer> customers = TopNCustomers.getRefreshedCustomers();
        for (Customer customer : customers) 
        {
            if(count==N)
                break;
            if(customer.getAccountNo()==accountNo)
                return true;
            count++;
        }
        return false;
    }

    public static boolean printTopNCustomers(int N)
    {
        int count = 0;
        for (Customer customer : customers) 
        {
            if(count==N)
                break;
            System.out.println(customer.getName()+" "+customer.getBalance());
            count++;
        }
        return false;
    }
}
