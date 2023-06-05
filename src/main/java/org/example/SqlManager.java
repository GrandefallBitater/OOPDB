package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlManager {
    public static void clearDB(){
        List<String> commands = new ArrayList<>();
        commands.add("delete from manager;");
        commands.add("delete from operator;");
        commands.add("delete from topmanager;");
        commands.add("delete from company;");
        executeAll(commands);
    }

    public  static void deleteEmployee(List<String> nameOfCTable, Company company){
        List<String> commands = new ArrayList<>();
        for (String str:
             nameOfCTable) {
            String sub  = "delete from "+ str +" where id = (select id from "+ str +" where company_name = '"+ company.getName() +"' limit 1)";
            commands.add(sub);
        }
        executeAll(commands);
    }

    public static void executeAll(List<String> List){
        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/oopdb", "postgres", "ROOT");
             Statement statement = conn.createStatement()) {
            conn.setAutoCommit(false);
            for (String str:
                 List) {
                statement.executeUpdate(str);
            }
            conn.commit();
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertHireAll(List<Employee> List, Company company){
        List<String> commands = new ArrayList<>();
        for (Employee e:
                List) {
            if(e instanceof Operator){
                commands.add("INSERT INTO operator (monthsalary, company_name) " +
                        "VALUES ('" + e.getMounthSalary() + "','"+ company.getName() + "')");
            }else if(e instanceof Manager){
                commands.add("INSERT INTO manager (company_name, personal_income, mounthsalary) " +
                        "VALUES ('" + company.getName() + "','" + ((Manager) e).getPersonIncome() + "'," + e.getMounthSalary() +")");
            } else if (e instanceof TopManager) {
                commands.add("INSERT INTO topmanager (company_name, mounthsalary) " +
                        "VALUES ('" + company.getName() + "','"+ e.getMounthSalary() + "')");
            }
        }
        executeAll(commands);
    }

    public static int selectCompanyIncome(String name) {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/oopdb", "postgres", "ROOT");
             Statement statement = conn.createStatement()) {
            ResultSet select = statement.executeQuery("select * from company where name = '" + name +"';");
            if (select.next()){
                return select.getInt("income");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }


    public static List<Integer> selectLowest(String name) {
        String managerCommand = "select * from manager where company_name = '" + name +"' " +
                "order by mounthsalary ASC;";
        String operatorCommand = "select * from operator where company_name = '" + name +"' " +
                "order by monthsalary ASC;";
        String topManagerCommand = "select * from topmanager where company_name = '" + name +"' " +
                "order by mounthsalary ASC;";
        return SelectAll(managerCommand, operatorCommand, topManagerCommand);
    }
    public static List<Integer> selectTop(String name) {
        String managerCommand = "select * from manager where company_name = '" + name +"' " +
                "order by mounthsalary DESC;";
        String operatorCommand = "select * from operator where company_name = '" + name +"' " +
                "order by monthsalary DESC;";
        String topManagerCommand = "select * from topmanager where company_name = '" + name +"' " +
                "order by mounthsalary DESC;";
        return SelectAll(managerCommand, operatorCommand, topManagerCommand);
    }

    private static List<Integer> SelectAll(String managerCommand, String operatorCommand, String topManagerCommand){
        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/oopdb", "postgres", "ROOT");
             Statement statement = conn.createStatement()) {
            ResultSet selectManager = statement.executeQuery(managerCommand);
            List<Integer> salares = new ArrayList<>();
            while (selectManager.next()){
                int income = selectManager.getInt("mounthsalary");
                salares.add(income);
            }
            ResultSet selectOperator = statement.executeQuery(operatorCommand);
            while (selectOperator.next()){
                int income = selectOperator.getInt("monthsalary");
                salares.add(income);
            }
            ResultSet selectTopManagers = statement.executeQuery(topManagerCommand);
            while (selectTopManagers.next()){
                int income = selectTopManagers.getInt("mounthsalary");
                salares.add(income);
            }
            return salares;
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void updateCompanyIncome(String name, int income) {
        List<String> commands = new ArrayList<>();
        commands.add("Update company SET income = " + income + " WHERE name = '" + name + "'");
        executeAll(commands);
    }

    public static void generateInsertCompany(String name, int income) {
        List<String> commands = new ArrayList<>();
        commands.add("INSERT INTO company (name, income) " +
                    "VALUES ('" + name + "',"+ income + ")");
        executeAll(commands);
    }

    public static void generateInsertOperator(String name, int income) {
        List<String> commands = new ArrayList<>();
        commands.add("INSERT INTO operator (monthsalary, company_name) " +
                "VALUES ('" + income + "','"+ name + "')");
        executeAll(commands);
    }

    public static void generateInsertManager(String name, int income, int p_income) {
        List<String> commands = new ArrayList<>();
        commands.add("INSERT INTO manager (company_name, personal_income, mounthsalary) " +
                "VALUES ('" + name + "','"+ p_income + "'," + income +")");
        executeAll(commands);
    }

    public static void generateInsertTopManager(String name, int income) {
        List<String> commands = new ArrayList<>();
        commands.add("INSERT INTO topmanager (company_name, mounthsalary) " +
                "VALUES ('" + name + "','"+ income + "')");
        executeAll(commands);
    }
}
