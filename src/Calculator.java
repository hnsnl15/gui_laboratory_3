import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.LinkedList;

public class Calculator extends JFrame {
    private final LinkedList<Grade> gradeList = new LinkedList<>();
    JTextField gradeNameField = new JTextField();
    JTextField scoreField = new JTextField();
    JTextField totalField = new JTextField();
    boolean isName, isScore, isTotal;

    public Calculator() {
        setTitle("Grade Calculator");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 4));

        // Label and field that will accept input
        JLabel name = new JLabel("Subject");
        add(name);
        add(gradeNameField);

        JLabel score = new JLabel("Add Grade");
        add(score);
        add(scoreField);

        JLabel total = new JLabel("Max score");
        add(total);
        add(totalField);

        // Button for adding new grade
        JButton addGradeButton = new JButton("Add Grade");
        add(addGradeButton);
        addGradeButton.setEnabled(isFieldsBlank());
        addGradeButton.addActionListener(e -> addGradeToList());

        // Button for calculating the result
        JButton calculateButton = new JButton("Calculate Grade");
        add(calculateButton);
        // If list is empty, then the button is disabled
        calculateButton.setEnabled(!gradeList.isEmpty());
        calculateButton.addActionListener(e -> calculateGrade());

        add(new JTextField(""));

        // Button for resetting list of grade
        JButton resetButton = new JButton("Reset");
        add(resetButton);
        resetButton.addActionListener(e -> resetListOfGrade());

        // Validate buttons
        totalField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkIfBlank();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkIfBlank();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkIfBlank();
            }

            public void checkIfBlank() {
                isName = gradeNameField.getText().isBlank();
                isScore = scoreField.getText().isBlank();
                isTotal = totalField.getText().isBlank();
                addGradeButton.setEnabled(!isFieldsBlank());
                calculateButton.setEnabled(!gradeList.isEmpty());
            }
        });
    }

    // Run the calculator
    public static void main(String[] args) {
        new Calculator().setVisible(true);
    }

    // Create a new Grade object
    private void addGradeToList() {
        String name = gradeNameField.getText().strip();
        double grade = Double.parseDouble(scoreField.getText().strip());
        int max = Integer.parseInt(totalField.getText().strip());
        Grade newGrade = new Grade(name, grade, max);

        // Add new grade to the list
        gradeList.add(newGrade);
        // Display confirmation
        JOptionPane.showMessageDialog(this, newGrade + " added!");
        // Clear the field after adding the values
        gradeNameField.setText("");
        scoreField.setText("");
        totalField.setText("");
    }

    // Calculate Grade
    private void calculateGrade() throws IllegalArgumentException {
        try {
            double accumulatedPercentage = 0;
            // Loop through grade list
            for (Grade grade : gradeList) {
                if (grade.getGrade() < 0 || grade.getGrade() > grade.getMaxScore()) {
                    throw new IllegalArgumentException("Invalid grade entered!");
                }
                // Accumulated percentage by looping through the list
                accumulatedPercentage += grade.calculatePercentage();
            }
            // Calculate percentage of all grades listed by dividing the accumulated percentage
            // to the size of the list
            double finalGrade = accumulatedPercentage / gradeList.size();
            // Display the final grade
            JOptionPane.showMessageDialog(this,
                    String.format("Your final grade is: %.2f percent!", finalGrade));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input entered!");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private boolean isFieldsBlank() {
        return isName && isScore && isTotal;
    }

    private void resetListOfGrade() {
        gradeList.clear();
        gradeNameField.setText("");
        scoreField.setText("");
        totalField.setText("");
    }
}