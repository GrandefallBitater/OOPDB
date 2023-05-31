package org.example;

import java.util.Comparator;
import java.util.List;

public class company {

    private final static int maxIncome = 20000000;
    private final static int minIncome = 8000000;
    private int Income;
    private final String name;

    public String getName() {
        return name;
    }

    public company(String name) {
        this.Income = createIncome();
        this.name = name;
        sqlManager.generateInsertCompany(this.name, Income);
    }

    public int getIncome() {
        return sqlManager.selectCompanyIncome(this.getName());
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
        sqlManager.insertHireAll(listOfEmployees, this);
    }

    public void fire(String type) {
        sqlManager.deleteEmploee(type, this);
    }

    public List<Integer> getTopSalaryStaff(int count) {
        if (count <= 0) {
            return null;
        }
        List<Integer> sub;
        sub = sqlManager.selectTop(this.getName());
        sub.sort(Comparator.reverseOrder());
        return sub.subList(0, count);
    }

    public List<Integer> getLowestSalaryStaff(int count) {
        if (count <= 0) {
            return null;
        }
        List<Integer> sub;
        sub = sqlManager.selectLowest(this.getName());
        sub.sort(Comparator.naturalOrder());
        return sub.subList(0 , count);
    }

    public String toString() {
        return "\nприбыль компании: " + Income;
    }
}
