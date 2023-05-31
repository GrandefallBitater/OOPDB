package org.example;

public class Operator implements Employee {
    private final static int FixedSalary = 20000;
    private int MounthSalary;
    private company Company;

    @Override
    public int getMounthSalary() {
        return MounthSalary;
    }

    @Override
    public void CreateSalary() {
        MounthSalary = FixedSalary;
    }

    @Override
    public void prepare(company Company){
        this.Company = Company;
        CreateSalary();
        sqlManager.generateInsertOperator(Company.getName(), getMounthSalary());
    }

    @Override
    public void prepareAll(company Company){
        this.Company = Company;
        CreateSalary();
    }

    public String toString() {
        return "\nтип работника: Operator\nзарплата: " + MounthSalary + "\nдоход компании: " + Company.getIncome();
    }
}
