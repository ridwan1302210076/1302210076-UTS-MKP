package lib;

public class TaxFunction {

    private static final int MONTHS_IN_YEAR = 12;
    private static final int MAX_CHILDREN_EXEMPTION = 3;
    private static final int BASIC_EXEMPTION = 54000000;
    private static final int MARRIED_EXEMPTION = 4500000;
    private static final int CHILD_EXEMPTION_PER_CHILD = 1500000;
    private static final double TAX_RATE = 0.05;

    public static int calculateTax(int monthlySalary, int otherMonthlyIncome, int numberOfMonthWorking, int deductible, boolean hasSpouse, int numberOfChildren) {
        validateMonthsWorked(numberOfMonthWorking);

        int taxableIncome = calculateTaxableIncome(monthlySalary, otherMonthlyIncome, numberOfMonthWorking, deductible, hasSpouse, numberOfChildren);
        int incomeTax = (int) Math.round(TAX_RATE * taxableIncome);

        return Math.max(0, incomeTax); // Ensure non-negative tax amount
    }

    private static void validateMonthsWorked(int numberOfMonthWorking) {
        if (numberOfMonthWorking > MONTHS_IN_YEAR) {
            throw new IllegalArgumentException("More than 12 months working per year");
        }
    }

    private static int calculateTaxableIncome(int monthlySalary, int otherMonthlyIncome, int numberOfMonthWorking, int deductible, boolean hasSpouse, int numberOfChildren) {
        int taxableIncome = ((monthlySalary + otherMonthlyIncome) * numberOfMonthWorking) - deductible;

        if (hasSpouse) {
            taxableIncome -= MARRIED_EXEMPTION;
        }

        taxableIncome -= BASIC_EXEMPTION;

        int childExemption = calculateChildExemption(numberOfChildren);
        taxableIncome -= childExemption;

        return taxableIncome;
    }

    private static int calculateChildExemption(int numberOfChildren) {
        int childExemption = 0;
        if (numberOfChildren > 0 && numberOfChildren <= MAX_CHILDREN_EXEMPTION) {
            childExemption = numberOfChildren * CHILD_EXEMPTION_PER_CHILD;
        } else if (numberOfChildren > MAX_CHILDREN_EXEMPTION) {
            childExemption = MAX_CHILDREN_EXEMPTION * CHILD_EXEMPTION_PER_CHILD;
        }
        return childExemption;
    }
}
