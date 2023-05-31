package org.example;

import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        SqlManager.clearDB();
        //-------
        Company firstCompany = new Company("ruslan");
        List<Employee> StaffList;
        StaffList = CreateStaff(160, 80, 10);
        firstCompany.hireAll(StaffList);

        System.out.println("\nСписок из пятнадцати зарплат по убыванию:");
        TopSalary(15, firstCompany);

        System.out.println("\nСписок из тридцати зарплат по возрастанию:");
        LowestSalary(30, firstCompany);

        //увольняем 50% сотрудников
        for (int i = 0; i < 80; i++){
            firstCompany.fire("operator");
        }
        for (int i1 = 0; i1 < 40; i1++){
            firstCompany.fire("manager");
        }
        for (int i2 = 0; i2 < 5; i2++){
            firstCompany.fire("topmanager");
        }
        System.out.println("\nудаление прошло успешно");

        System.out.println("\nСписок из пятнадцати зарплат по убыванию:");
        TopSalary(15, firstCompany);

        System.out.println("\nСписок из тридцати зарплат по возрастанию:");
        LowestSalary(30, firstCompany);
    }

    private static List<Employee> CreateStaff(int OperatorCount, int ManagerCount, int TopManagerCount) {
        List<Employee> ListOfEmployee = new ArrayList<>();
        for (int i = 0; i < OperatorCount; i++) {
            Operator sub = new Operator();
            ListOfEmployee.add(sub);
        }
        for (int i = 0; i < ManagerCount; i++) {
            Manager sub = new Manager();
            ListOfEmployee.add(sub);
        }
        for (int i = 0; i < TopManagerCount; i++) {
            TopManager sub = new TopManager();
            ListOfEmployee.add(sub);
        }
        return ListOfEmployee;
    }

    private static void TopSalary(int count, Company firstCompany) {

        List<Integer> topSalaryStaff = firstCompany.getTopORLowestSalaryStaff(count, true);
        if (topSalaryStaff == null) {
            System.out.println("некорретное число работников");
        } else {
            for (Integer e :
                    topSalaryStaff) {
                System.out.println(e + " руб");
            }
        }
    }

    private static void LowestSalary(int count, Company firstCompany) {
        List<Integer> lowestSalaryStaff = firstCompany.getTopORLowestSalaryStaff(count, false);
        if (lowestSalaryStaff == null) {
            System.out.println("некорретное число работников");
        } else {
            for (int e :
                    lowestSalaryStaff) {
                System.out.println(e + " руб");
            }
        }
    }
}