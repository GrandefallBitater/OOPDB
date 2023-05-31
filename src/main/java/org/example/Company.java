package org.example;

import java.util.Comparator;
import java.util.List;

public class Company {

    private final static int maxIncome = 20000000;
    private final static int minIncome = 8000000;
    private int Income;
    private final String name;

    public Company(String name) {
        this.Income = createIncome();
        this.name = name;
        SqlManager.generateInsertCompany(this.name, Income);
    }

    public String getName() {
        return name;
    }

    public int getIncome() {
        return SqlManager.selectCompanyIncome(this.getName());
    }

    private int createIncome() {
        return (int) ((Math.random() * (maxIncome - minIncome) + 1) + minIncome);
    }

    protected void setIncome(int personIncome){
        Income += personIncome;
    }

    public void hire(Employee employee) {
        employee.prepare(this);
    }

    public void hireAll(List<Employee> listOfEmployees) {
        for (Employee e :
                listOfEmployees) {
            e.prepareAll(this);
        }
        SqlManager.insertHireAll(listOfEmployees, this);
    }

    public void fire(String type) {
        SqlManager.deleteEmploee(type, this);
    }

    public List<Integer> getTopORLowestSalaryStaff(int count, boolean flag) {
        if (count <= 0) {
            return null;
        }

        List<Integer> sub;
        if(flag){
            sub = SqlManager.selectTop(this.getName());
            sub.sort(Comparator.reverseOrder());
        }else {
            sub = SqlManager.selectLowest(this.getName());
            sub.sort(Comparator.naturalOrder());
        }
        return sub.subList(0, count);
    }

    public String toString() {
        return "\nприбыль компании: " + Income;
    }
}
