import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.Random;

class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Random rand = new Random();
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        int num1, num2;
        char[] operators = { '+', '-', '*', '/' };
        char operator;
        int score = 0;
        int incorrectScore = 0;
        long totalResponseTime = 0; // Track total response time
        int numQuestionsAnswered = 0; // Track the number of questions answered

        System.out.println("Choose the type of questions you want to answer:");
        System.out.println("1. Single digit and double digit");
        System.out.println("2. Single digit and single digit");
        System.out.println("3. Double digit and double digit");
        System.out.print("Enter the number of your choice: ");
        int choice = input.nextInt();
        input.nextLine(); // Consume the newline character

        System.out.println("Enter the number of questions you want to answer: ");
        int numQuestions = input.nextInt();
        input.nextLine(); // Consume the newline character

        while (numQuestionsAnswered < numQuestions) {
            num1 = generateNumber(choice, rand);
            num2 = generateNumber(choice, rand);
            operator = operators[rand.nextInt(4)];

            long startTime = System.currentTimeMillis(); // Record start time at the beginning of each question

            System.out.println("Question " + (numQuestionsAnswered + 1) + ": What is " + num1 + " " + operator + " "
                    + num2 + "? Enter 'q' to quit.");
            String userInput = input.nextLine();

            long endTime = System.currentTimeMillis(); // Record end time after each question
            long responseTime = endTime - startTime; // Calculate the response time for the current question

            if (userInput.equalsIgnoreCase("q")) {
                System.out
                        .println("Exiting the program. Final score: " + score + ", Incorrect score: " + incorrectScore);
                break;
            }

            try {
                // Parse the user input with up to 2 decimal places
                double userResult = Double.parseDouble(decimalFormat.format(Double.parseDouble(userInput)));
                double result = performOperation(num1, num2, operator);
                double errorMargin = 0.0001; // Define the error margin

                if (Math.abs(userResult - result) < errorMargin) {
                    System.out.println("Correct!");
                    score++;
                } else {
                    System.out.println("Incorrect. The answer is " + result);
                    incorrectScore++;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number or 'q' to quit.");
            }

            // Add the response time to the total and keep track of the number of questions
            // answered
            totalResponseTime += responseTime;
            numQuestionsAnswered++;
        }

        double averageResponseTimeMillis = (double) totalResponseTime / numQuestionsAnswered;
        double averageResponseTimeMinutes = averageResponseTimeMillis / (60 * 1000);
        System.out.println("Average response time for answers: " + averageResponseTimeMinutes + " minutes");
    }

    public static int generateNumber(int choice, Random rand) {
        int num;
        switch (choice) {
            case 1:
                num = rand.nextInt(90) + 10; // Generate a random number between 10 and 99
                break;
            case 2:
                num = rand.nextInt(10); // Generate a random number between 0 and 9
                break;
            case 3:
                num = rand.nextInt(90) + 10; // Generate a random number between 10 and 99
                break;
            default:
                throw new IllegalArgumentException("Invalid choice");
        }
        return num;
    }

    public static double performOperation(int num1, int num2, char operator) {
        double result;
        switch (operator) {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num1 - num2;
                break;
            case '*':
                result = num1 * num2;
                break;
            case '/':
                result = (double) num1 / num2;
                break;
            default:
                throw new IllegalArgumentException("Invalid operator");
        }
        return result;
    }
}
