package bank;
import java.util.Random;
import java.io.Console;

public class Register {
    private static int sequence = 0;
    public static int generate(int digits)
    {
        int max = (int) Math.pow(10, digits) - 1;
        Random random = new Random();
        long timestamp = System.currentTimeMillis()- random.nextInt(100000);
        int shifter = random.nextInt(12);
        int uncertainValue = random.nextInt(10000);
        int snowflakeId = (int)(timestamp << shifter) | (uncertainValue << shifter) | sequence;
        sequence = (sequence + 1) & 0xFFF;
        return (snowflakeId & max);
    }
    public static String passwordChecker()
    {
        System.out.print("Enter your password: ");
        Console console = System.console();
        char [] pass1 = console.readPassword();
        String password1 = new String(pass1);
        System.out.print("Re-enter your password: ");
        char [] pass2 = console.readPassword();
        String password2 = new String(pass2);
        if(password1.equals(password2))
        {
            if(password1.length()<6)
            {
                System.out.println("Password must be at least 6 characters long");
                return passwordChecker();
            }
            int lowerCount = 0;
            int upperCount = 0;
            int digitCount = 0;
            for(char c: pass1)
            {
                if(Character.isLowerCase(c))
                    lowerCount++;
                else if(Character.isUpperCase(c))
                    upperCount++;
                else if(Character.isDigit(c))
                    digitCount++;
                else 
                {
                    System.out.println("Password must not contain special characters");
                    break;
                }
            }
            if(lowerCount<2 || upperCount<2 || digitCount<2)
            {
                System.out.println("Password must contain at least 2 lowercase, 2 uppercase and 2 digits");
                return passwordChecker();
            }
            return password1;
        }
        else
        {
            System.out.println("Passwords do not match! Retry");
            return passwordChecker();
        }
    }

    public static String nameChecker()
    {
        System.out.print("Enter your name: ");
        String name = System.console().readLine();
        for(char c: name.toCharArray())
        {
            if(!Character.isLetter(c))
            {
                System.out.println("Name must not contain special characters");
                return nameChecker();
            }
        }
        return name;
    }

    public static Customer register(int id) {
        System.out.println("Register");
        String name = nameChecker();
        System.out.println("To ensure the security of your account, we require a password that is both robust and unique.\nYour password should be a \n  1.Minimum of six characters in length \n  2.Must include at least two lowercase and two uppercase letters \n  3.As well as a minimum of two digits\n  4.Password must not contain special characters");
        String password = passwordChecker();
        String encryptPassword = Customer.encrypt(password.toCharArray());
        int custId = generate(3)+id;
        int accountNo = generate(6)+id;
        Customer customer = new Customer(custId,accountNo,name,10000,encryptPassword);
        System.out.println("\nPlease! Note Down Your Customer ID, You may need this for logging in");
        System.out.println("Your Customer ID is: " + customer.getCustId());
        System.out.println("Your account number is: " + customer.getAccountNo());
        FileHandler.write(customer);
        FileHandler.transactionHistory(customer, 10000, "Opening");
        return customer;
    }
    
    public static void main(String[] args) {
        register(1);
    }

}