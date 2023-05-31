package org.example;

public class Operator implements Employee {
    private final static int FixedSalary = 20000;
    private int MounthSalary;
    private org.example.Company Company;

    @Override
    public int getMounthSalary() {
        return MounthSalary;
    }

    @Override
    public void createSalary() {
        MounthSalary = FixedSalary;
    }

    @Override
    public void prepare(org.example.Company Company){
        this.Company = Company;
        createSalary();
        SqlManager.generateInsertOperator(Company.getName(), getMounthSalary());
    }

    @Override
    public void prepareAll(org.example.Company Company){
        this.Company = Company;
        createSalary();
    }

    public String toString() {
        return "\nтип работника: Operator\nзарплата: " + MounthSalary + "\nдоход компании: " + Company.getIncome();
    }
}
