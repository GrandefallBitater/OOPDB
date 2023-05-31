package org.example;

public class Manager implements Employee {
    private final static int FixedSalary = 40000;
    private int PersonIncome;
    private company Company;
    private int MounthSalary;

    public int getPersonIncome() {
        return PersonIncome;
    }

    public void CreatePersonIncome() {
        int maxIncome = 140000;
        int minIncome = 115000;
        PersonIncome = (int) ((Math.random() * (maxIncome - minIncome) + 1) + minIncome);
        Company.setIncome(PersonIncome);
    }

    @Override
    public void CreateSalary() {
        MounthSalary = FixedSalary + ((PersonIncome * 5) / 100);
    }

    @Override
    public int getMounthSalary() {
        return MounthSalary;
    }

    @Override
    public void prepare(company Company){
        this.Company = Company;
        CreatePersonIncome();
        CreateSalary();
        sqlManager.updateCompanyIncome(Company.getName(), PersonIncome + Company.getIncome());
        sqlManager.generateInsertManager(Company.getName(), getMounthSalary(), PersonIncome);
    }

    @Override
    public void prepareAll(company Company){
        this.Company = Company;
        CreatePersonIncome();
        CreateSalary();
        sqlManager.updateCompanyIncome(Company.getName(), PersonIncome + Company.getIncome());
    }

    public String toString() {
        return "\nтип работника: Manager\nзарплата: " + MounthSalary + "\nдоход компании: " + Company.getIncome();
    }
}
