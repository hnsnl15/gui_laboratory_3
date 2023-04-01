public class Grade {
    private String name;
    private double grade;
    private int maxScore;


    public Grade(String name, double grade, int maxScore) {
        this.name = name;
        this.grade = grade;
        this.maxScore = maxScore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public double calculatePercentage() {
        return (grade / (double) maxScore) * 100;
    }

    @Override
    public String toString() {
        return String.format("'%s' with score %s and a max score of %d",
                name, grade, maxScore);
    }
}
