package bank;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Scanner;

public class FileHandler {
    private static final String transactionFolder = "./database/transactions/";
    public static PriorityQueue<Customer> read() 
    {
        PriorityQueue<Customer> customers = new PriorityQueue<>();
        String fileName = "./database/bank_db.txt";
        try(Scanner scanner = new Scanner(new File(fileName));)
        {            
            scanner.nextLine();
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] field = line.split("\\s+");
                int custId = Integer.parseInt(field[0]);
                int accountNo = Integer.parseInt(field[1]);
                String name = field[2];
                double balance = Double.parseDouble(field[3]);
                String password = field[4];
                Customer customer = new Customer(custId,accountNo,name,balance,password);
                if(!fileExists(transactionFolder+accountNo+".txt"))
                    write(customer);
                customers.add(customer);
            }
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return customers;
    }

    public static void write(Customer customer)
    {
        int accountNo = customer.getAccountNo();
        String Name = String.format("Name - %s",customer.getName());
        String AccountNo = String.format("Account No - %s",""+accountNo);
        String CustID = String.format("Customer Id - %s",""+customer.getCustId());
        String fileName = transactionFolder+accountNo+".txt";
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName)))
        {
            writer.write(String.format("%45s%n%n%45s%n%45s%n%44s%n%n","Account Statement",Name,AccountNo,CustID));
            writer.write(String.format("%-20s%-30s%-20s%-20s%n","TransId","Transaction","Amount","Balance"));
        }
        catch(Exception e)
        {
            System.out.println("Error in accessing the file "+e);
        }
    }

    public static boolean fileExists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    public static void write(PriorityQueue<Customer> customers)
    {
        String fileName = "./database/bank_db.txt";

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) 
        {   
            writer.write(String.format("%-11s%-14s%-16s%-15s%s%n","CustId","AccountNo","Name","Balance","EncryptedPwd" ));
            for (Customer customer : customers) {
                writer.write(String.format("%-11s%-14s%-16s%-15s%s%n", 
                    customer.getCustId(), customer.getAccountNo(), 
                    customer.getName(), customer.getBalance(), 
                    customer.getPassword()));
            }
        } catch (IOException e) {
            System.err.println("Failed to write customers data to " + fileName + " file: " + e.getMessage());
        }
    }

    public static int getLinesCount(String fileName)
    {
        try(Scanner scanner = new Scanner(new File(fileName)))
        {
            int linesCount = 0;
            scanner.nextLine();
            while(scanner.hasNextLine()){
                scanner.nextLine();
                linesCount++;
            }
            return linesCount;
        }
        catch(Exception e)
        {
            System.err.println("Error occurred while accessing the file");
            return 0;
        }
    }

    public static void transactionHistory(Customer customer,int amount,String transactionType)
    {
        int accountNo = customer.getAccountNo();
        double balance = customer.getBalance();
        String fileName = transactionFolder+accountNo+".txt";
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
            int transId = getLinesCount(fileName)-5;
            writer.write(String.format("%-20s%-30s%-20s%-20s%n",transId,transactionType,amount,balance));
            writer.close();
            if(!transactionType.contains("transfer from") && transId%5==0)
            {
                System.out.println("You need to change Your password for security reasons ...");
                PasswordChanger.changePassword(customer);
                System.out.println("Remember this password for logging in future");
            }
            if(transId%10==0)
                Transactions.withdraw(customer,100);
            return;
        } 
        catch (IOException e) 
        {
            System.out.print("Error in accessing the file "+e);
        }
    }

    public static void main(String[] args) {
        Customer customer = new Customer(1, 1001, "John", 1000, "1234");
        transactionHistory(customer, 10000, "opening");
    }

}