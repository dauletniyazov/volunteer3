package task4;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class BankLookup {

    public static void main(String[] args) {
        String filename = "/bank_data.txt"; // File located directly inside the resources directory

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.print("Enter the first three digits of your bank account: ");
            String inputDigits = reader.readLine().trim();

            if (inputDigits.length() != 3 || !inputDigits.matches("\\d{3}")) {
                System.out.println("Invalid input. Please enter exactly three digits.");
                return;
            }

            String bankName = findBankName(filename, inputDigits);
            if (bankName != null) {
                System.out.println("The bank name for the entered digits is: " + bankName);
            } else {
                System.out.println("No bank found with these digits.");
            }
        } catch (IOException e) {
            System.out.println("Error reading input or processing file: " + e.getMessage());
        }
    }

    private static String findBankName(String filename, String accountDigits) {
        Pattern pattern = Pattern.compile("^" + accountDigits + "\\s+(\\D.*?)(\\s{2,}|$)");

        // Access the file from the resources directory
        try (InputStream is = BankLookup.class.getResourceAsStream(filename);
             BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line.trim());
                if (matcher.find()) {
                    return matcher.group(1).trim(); // Extract the bank name
                }
            }
        } catch (IOException | NullPointerException e) {
            System.out.println("Error reading the bank file: " + e.getMessage());
            return null;
        }
        return null;
    }
}
