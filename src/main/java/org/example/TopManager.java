package org.example;

public class TopManager implements Employee {
    private final static int FixedSalary = 40000;
    private company Company;
    private int MounthSalary;

    @Override
    public int getMounthSalary() {
        return MounthSalary;
    }

    @Override
    public void CreateSalary() {
        MounthSalary = FixedSalary;
        if (Company.getIncome() > 10000000) {
            MounthSalary += 1.5 * MounthSalary;
        }
    }

    @Override
    public void prepare(company Company){
        this.Company = Company;
        CreateSalary();
        sqlManager.generateInsertTopManager(Company.getName(), getMounthSalary());
    }

    @Override
    public void prepareAll(company Company){
        this.Company = Company;
        CreateSalary();
    }

    public String toString() {
        return "\nтип работника: TopManager\nзарплата: " + MounthSalary + "\nдоход компании: " + Company.getIncome();
    }
}
