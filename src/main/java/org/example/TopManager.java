package org.example;

public class TopManager implements Employee {
    private final static int FixedSalary = 40000;
    private org.example.Company Company;
    private int MounthSalary;

    @Override
    public int getMounthSalary() {
        return MounthSalary;
    }

    @Override
    public void createSalary() {
        MounthSalary = FixedSalary;
        if (Company.getIncome() > 10000000) {
            MounthSalary += 1.5 * MounthSalary;
        }
    }

    @Override
    public void prepare(org.example.Company Company){
        this.Company = Company;
        createSalary();
        SqlManager.generateInsertTopManager(Company.getName(), getMounthSalary());
    }

    @Override
    public void prepareAll(org.example.Company Company){
        this.Company = Company;
        createSalary();
    }

    public String toString() {
        return "\nтип работника: TopManager\nзарплата: " + MounthSalary + "\nдоход компании: " + Company.getIncome();
    }
}
