package org.example;

public class Manager implements Employee {
    private final static int FixedSalary = 40000;
    private int PersonIncome;
    private org.example.Company Company;
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
    public void createSalary() {
        MounthSalary = FixedSalary + ((PersonIncome * 5) / 100);
    }

    @Override
    public int getMounthSalary() {
        return MounthSalary;
    }

    @Override
    public void prepare(org.example.Company Company){
        this.Company = Company;
        CreatePersonIncome();
        createSalary();
        SqlManager.updateCompanyIncome(Company.getName(), PersonIncome + Company.getIncome());
        SqlManager.generateInsertManager(Company.getName(), getMounthSalary(), PersonIncome);
    }

    @Override
    public void prepareAll(org.example.Company Company){
        this.Company = Company;
        CreatePersonIncome();
        createSalary();
        SqlManager.updateCompanyIncome(Company.getName(), PersonIncome + Company.getIncome());
    }

    public String toString() {
        return "\nтип работника: Manager\nзарплата: " + MounthSalary + "\nдоход компании: " + Company.getIncome();
    }
}
