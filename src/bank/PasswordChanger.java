package bank;

public class PasswordChanger{
    public static void changePassword(Customer customer)
    {
        String newPassword = Register.passwordChecker();
        String encryptedNewPassword = Customer.encrypt(newPassword.toCharArray());
        String [] passwords = customer.getPassword().split(",");
        for(String password: passwords)
        {
            if(password.equals(encryptedNewPassword))
            {
                System.out.println("You have already used this password");
                changePassword(customer);
                return ;
            }
        }
        int size = passwords.length == 3? 2: passwords.length;
        for(int i=0;i<size;i++)
            encryptedNewPassword = encryptedNewPassword+","+passwords[i];
        customer.setPassword(encryptedNewPassword);
        System.out.println("Password changed successfully");
    }
}