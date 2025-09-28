import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * StudentGradeTracker
 * A console-based Java program to manage and track student grades using arrays.
 * It allows users to add students with multiple subject grades and displays a
 * summary report including the class average and individual letter grades.
 */
public class StudentGradeTracker {

    // --- Data Storage (Parallel Arrays) ---
    private static final int MAX_STUDENTS = 50;
    // Array to store student names
    private static String[] studentNames = new String[MAX_STUDENTS];
    // Array to store the calculated average grade for each student
    private static double[] studentAverages = new double[MAX_STUDENTS];
    // Counter to track the number of students currently stored
    private static int studentCount = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        System.out.println("--- Welcome to the Student Grade Tracker ---");

        // Main menu loop
        while (choice != 3) {
            System.out.println("\n-------------------------------------------");
            System.out.println("1. Add New Student and Grades");
            System.out.println("2. View Summary Report");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        addStudent(scanner);
                        break;
                    case 2:
                        displayReport();
                        break;
                    case 3:
                        System.out.println("\nThank you for using the Grade Tracker. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nError: Invalid input. Please enter a number for your choice.");
                scanner.nextLine(); // Consume the invalid input
                choice = -1; // Reset choice to keep the loop running
            }
        }

        scanner.close();
    }

    /**
     * Adds a new student record to the arrays.
     * Prompts the user for the student's name and their grades for multiple subjects.
     * Calculates the average grade and stores it.
     * @param scanner The Scanner object for console input.
     */
    private static void addStudent(Scanner scanner) {
        if (studentCount >= MAX_STUDENTS) {
            System.out.println("Cannot add more students. Tracker is at maximum capacity of " + MAX_STUDENTS + ".");
            return;
        }

        System.out.println("\n--- Add Student ---");
        System.out.print("Enter student name: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Student name cannot be empty. Returning to menu.");
            return;
        }

        int numGrades = 0;
        boolean validNumGrades = false;
        while (!validNumGrades) {
            try {
                System.out.print("Enter number of subjects/grades (e.g., 3): ");
                numGrades = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (numGrades <= 0) {
                    System.out.println("Number of grades must be positive.");
                } else {
                    validNumGrades = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid whole number.");
                scanner.nextLine(); // Consume invalid input
            }
        }

        double totalScore = 0;
        int gradesEntered = 0;

        // Loop to collect individual subject grades
        System.out.println("Please enter grades (0-100) for " + name + ":");
        while (gradesEntered < numGrades) {
            try {
                System.out.print("Grade " + (gradesEntered + 1) + ": ");
                double grade = scanner.nextDouble();
                if (grade < 0 || grade > 100) {
                    System.out.println("Grade must be between 0 and 100. Please try again.");
                    continue;
                }
                totalScore += grade;
                gradesEntered++;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number for the grade.");
                scanner.nextLine(); // Consume invalid input
            }
        }
        
        // Calculate and store the average
        double average = totalScore / numGrades;
        studentNames[studentCount] = name;
        studentAverages[studentCount] = average;
        studentCount++;

        System.out.printf("\nSuccessfully added %s. Average Score: %.2f (Grade: %s)\n", 
                          name, average, getLetterGrade(average));
    }

    /**
     * Displays a summary report of all students and calculates the overall class average.
     */
    private static void displayReport() {
        if (studentCount == 0) {
            System.out.println("\n--- Report ---");
            System.out.println("No student data available. Please add students first.");
            return;
        }

        System.out.println("\n=======================================================");
        System.out.println("                STUDENT GRADE SUMMARY REPORT             ");
        System.out.println("=======================================================");
        System.out.printf("%-5s | %-30s | %-8s | %-5s\n", "No.", "Name", "Average", "Grade");
        System.out.println("-------------------------------------------------------");

        double totalClassScore = 0;

        // Iterate through the arrays to display data for each student
        for (int i = 0; i < studentCount; i++) {
            String name = studentNames[i];
            double average = studentAverages[i];
            String letterGrade = getLetterGrade(average);

            System.out.printf("%-5d | %-30s | %-8.2f | %-5s\n", 
                              (i + 1), name, average, letterGrade);
            
            totalClassScore += average;
        }

        // Calculate and display the overall class average
        double classAverage = totalClassScore / studentCount;
        System.out.println("=======================================================");
        System.out.printf("OVERALL CLASS AVERAGE: %.2f\n", classAverage);
        System.out.println("Total Students Processed: " + studentCount);
        System.out.println("=======================================================");
    }

    /**
     * Helper method to determine the letter grade based on the average score.
     * @param average The student's average score (0-100).
     * @return The corresponding letter grade (A, B, C, D, or F).
     */
    private static String getLetterGrade(double average) {
        if (average >= 90) {
            return "A";
        } else if (average >= 80) {
            return "B";
        } else if (average >= 70) {
            return "C";
        } else if (average >= 60) {
            return "D";
        } else {
            return "F";
        }
    }
}