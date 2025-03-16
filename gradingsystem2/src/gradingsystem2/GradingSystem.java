/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package gradingsystem2;

import java.util.Scanner;

public class GradingSystem {

    private static final String[] SUBJECTS = {
        "Mathematical Activities",
        "Language",
        "Kiswahili",
        "Environmental Activities",
        "Creative Activities"
    };

    private static final String[] ASSESSMENTS = {"Opener", "Midterm", "Endterm"};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int numStudents = getValidIntInput(scanner, "Enter number of students: ", 1, Integer.MAX_VALUE);

        for (int i = 0; i < numStudents; i++) {
            processStudent(scanner, i + 1);
        }

        scanner.close();
    }

    private static void processStudent(Scanner scanner, int studentNumber) {
        System.out.println("\n=== Processing Student " + studentNumber + " ===");
        String name = getValidName(scanner);

        int[][] scores = new int[5][3];
        for (int subject = 0; subject < 5; subject++) {
            System.out.println("\nEntering scores for " + SUBJECTS[subject]);
            for (int assessment = 0; assessment < 3; assessment++) {
                String prompt = String.format("  %s score: ", ASSESSMENTS[assessment]);
                scores[subject][assessment] = getValidIntInput(scanner, prompt, 0, 100);
            }
        }

        double[] subjectAverages = new double[5];
        String[] subjectGrades = new String[5];
        for (int subject = 0; subject < 5; subject++) {
            int sum = 0;
            for (int score : scores[subject]) {
                sum += score;
            }
            subjectAverages[subject] = sum / 3.0;
            subjectGrades[subject] = getGradeCategory(subjectAverages[subject]);
        }

        double meanGrade = 0;
        for (double avg : subjectAverages) {
            meanGrade += avg;
        }
        meanGrade /= 5.0;
        String meanGradeCategory = getGradeCategory(meanGrade);

        generateReport(name, scores, subjectAverages, subjectGrades, meanGrade, meanGradeCategory);
    }

    private static void generateReport(String name, int[][] scores, double[] subjectAverages, String[] subjectGrades, double meanGrade, String meanGradeCategory) {
        System.out.println("\n\n=== Report for " + name + " ===");
        System.out.println("+-----------------------------+--------+---------+---------+---------+----------------------+");
        System.out.println("| Subject                     | Opener | Midterm | Endterm | Average | Grade                |");
        System.out.println("+-----------------------------+--------+---------+---------+---------+----------------------+");
        for (int i = 0; i < 5; i++) {
            System.out.printf("| %-27s | %6d | %7d | %7d | %7.1f | %-20s |\n",
                    SUBJECTS[i],
                    scores[i][0],
                    scores[i][1],
                    scores[i][2],
                    subjectAverages[i],
                    subjectGrades[i]);
        }
        System.out.println("+-----------------------------+--------+---------+---------+---------+----------------------+");
        System.out.printf("| Mean Grade: %.1f (%s)                                                         |\n", meanGrade, meanGradeCategory);
        System.out.println("+---------------------------------------------------------------------------------------------+");
    }

    private static String getGradeCategory(double average) {
        if (average >= 70) {
            return "Exceeds Expectation";
        } else if (average >= 50) {
            return "Meets Expectation";
        } else if (average >= 40) {
            return "Approaches Expectation";
        } else {
            return "Below Expectation";
        }
    }

    private static String getValidName(Scanner scanner) {
        System.out.print("Enter student's name: ");
        String name;
        while (true) {
            name = scanner.nextLine().trim();
            if (!name.isEmpty()) {
                break;
            }
            System.out.println("Name cannot be empty. Please enter again:");
        }
        return name;
    }

    private static int getValidIntInput(Scanner scanner, String prompt, int min, int max) {
        int value;
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                value = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (value >= min && value <= max) {
                    break;
                } else {
                    System.out.printf("Please enter a value between %d and %d.\n", min, max);
                }
            } else {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.next(); // Consume invalid token
            }
        }
        return value;
    }
}