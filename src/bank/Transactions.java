package bank;

import java.util.PriorityQueue;

public class Transactions {
    public static boolean deposit(Customer customer,int amount){
        try{
            if(amount!=0)
                customer.setBalance(customer.getBalance()+amount);
            else{
                System.out.println("Enter the amount to be deposited: ");
                int depositAmount = Integer.parseInt(System.console().readLine());
                if(depositAmount<=0)
                {
                    System.out.println("Invalid amount");
                    System.out.println("Deposit Failed");
                    return false;
                }
                customer.setBalance(customer.getBalance()+depositAmount);
                FileHandler.transactionHistory(customer, depositAmount, "cash deposit");
                System.out.println("Amount deposited successfully");
            }
            return true;
        }
        catch(Exception e){
            System.out.println("Invalid amount");
            System.out.println("Deposit Failed");
            return false;
        }
    } 

    public static boolean withdraw(Customer customer,int amount){
        try{
            if(amount!=0)
            {
                if(amount > customer.getBalance()-1000)
                    throw new Exception();
                if(amount==10)
                {
                    customer.setBalance(customer.getBalance()-amount);
                    FileHandler.transactionHistory(customer, amount, "operational fee");
                }
                else if(amount==100 && !TopNCustomers.isCustomerInTopN(customer.getAccountNo(),3))
                {
                    customer.setBalance(customer.getBalance()-amount);
                    FileHandler.transactionHistory(customer, amount, "maintenance fee");
                }
                else
                    customer.setBalance(customer.getBalance()-amount);
            }
            else
            {
                System.out.println("Enter the amount to be withdrawn: ");
                int withdrawalAmount = Integer.parseInt(System.console().readLine());
                if(withdrawalAmount<=0)
                {
                    System.out.println("Invalid amount");
                    System.out.println("Withdrawal Failed");
                    return false;
                }
                if(withdrawalAmount>customer.getBalance()-1000)
                    throw new Exception();
                else
                {
                    customer.setBalance(customer.getBalance()-withdrawalAmount);
                    FileHandler.transactionHistory(customer, withdrawalAmount, "cash withdrawal");
                }
                System.out.println("Amount withdrawn successfully");
            }
            
            return true;
        }
        catch(Exception e){
            System.out.println("Insufficient amount");
            System.out.println("Withdrawal Failed");
            return false;
        }
    }  

    public static boolean isCustomerInTop3(int accountNo,PriorityQueue<Customer> customers)
    {
        for(int i=0;i<3;i++)
            if(customers.peek().getAccountNo()==accountNo)
                return true;
        return false;
    }

    public static PriorityQueue<Customer> transfer(Customer customer, PriorityQueue<Customer> customers){
        try{
            System.out.print("Enter the Beneficiary Account No ");
            int beneficiaryAccountNo = Integer.parseInt(System.console().readLine());

            System.out.println("Enter the amount to be transfered: ");
            int transactionAmount = Integer.parseInt(System.console().readLine());
            
            Customer beneficiaryCustomer = null;
            for (Customer customer2 : customers) 
            {
                if (customer2.getAccountNo() == beneficiaryAccountNo) 
                {
                    beneficiaryCustomer = customer2;
                    break;
                }
            }
            if(beneficiaryCustomer==null)
                throw new Exception();
            boolean withdrawn = withdraw(customer,transactionAmount);
            if(withdrawn)
            {

                boolean deposited = deposit(beneficiaryCustomer, transactionAmount);
                if(!deposited)
                {
                    deposit(customer,transactionAmount);
                    FileHandler.transactionHistory(customer, transactionAmount, "transaction failed");
                    throw new Exception();
                }
                FileHandler.transactionHistory(customer,transactionAmount , "transferred to "+beneficiaryAccountNo);
                FileHandler.transactionHistory(beneficiaryCustomer, transactionAmount, "transferred from "+customer.getAccountNo());
                if(transactionAmount>5000)
                    withdraw(customer,10);
                System.out.println("Rs "+transactionAmount+" transfered successfully from "+customer.getAccountNo()+" to "+beneficiaryAccountNo);
            }
            return customers;
        }
        catch(Exception e){
            System.out.println("Invalid account number or Invalid amount");
            System.out.println("Transaction Failed");
            transfer(customer,customers);
        }
        return customers;
    }
}