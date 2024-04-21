package lib;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Employee {

    private String employeeId;
    private String firstName;
    private String lastName;
    private String idNumber;
    private String address;

    private LocalDate joiningDate;
    private boolean isForeigner;
    public enum Gender {
        PRIA,
        WANITA
    }
    public Gender gender;
    private int monthlySalary;
    private int otherMonthlyIncome;
    private int annualDeductible;

    private String spouseName;
    private String spouseIdNumber;

    private List<String> childNames;
    private List<String> childIdNumbers;

	// Enum Grade
	public enum Grade {
		GRADE_1,
		GRADE_2,
		GRADE_3
	}

    public Employee(String employeeId, String firstName, String lastName, String idNumber, String address,
                    int yearJoined, int monthJoined, int dayJoined, boolean isForeigner, Gender gender) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.idNumber = idNumber;
        this.address = address;
        this.joiningDate = LocalDate.of(yearJoined, monthJoined, dayJoined);
        this.isForeigner = isForeigner;
        this.gender = gender;

        childNames = new LinkedList<>();
        childIdNumbers = new LinkedList<>();
    }

    public void setMonthlySalary(Grade grade) { // Perbaikan: Ubah parameter menjadi Grade
        switch (grade) {
            case GRADE_1:
                monthlySalary = 3000000;
                break;
            case GRADE_2:
                monthlySalary = 5000000;
                break;
            case GRADE_3:
                monthlySalary = 7000000;
                break;
        }
        if (isForeigner) {
            monthlySalary = (int) (monthlySalary * 1.5);
        }
    }

    public void setAnnualDeductible(int deductible) {
        this.annualDeductible = deductible;
    }

    public void setAdditionalIncome(int income) {
        this.otherMonthlyIncome = income;
    }

    public void setSpouse(String spouseName, String spouseIdNumber) {
        this.spouseName = spouseName;
        this.spouseIdNumber = spouseIdNumber;
    }

    public void addChild(String childName, String childIdNumber) {
        childNames.add(childName);
        childIdNumbers.add(childIdNumber);
    }

    public int getAnnualIncomeTax() {
        int monthsWorked = calculateMonthsWorked();
        return TaxFunction.calculateTax(monthlySalary, otherMonthlyIncome, monthsWorked, annualDeductible,
                spouseIdNumber.equals(""), childIdNumbers.size());
    }

    private int calculateMonthsWorked() {
        LocalDate currentDate = LocalDate.now();
        int monthsWorked = 12; // Default to a full year
        if (currentDate.getYear() == joiningDate.getYear()) {
            monthsWorked = currentDate.getMonthValue() - joiningDate.getMonthValue();
        }
        return Math.max(0, monthsWorked); // Ensure non-negative value
    }
}
