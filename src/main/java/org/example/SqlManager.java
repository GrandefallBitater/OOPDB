package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlManager {
    public static void clearDB(){
        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/oopdb", "postgres", "ROOT");
             Statement statement = conn.createStatement()) {
            conn.setAutoCommit(false);
            statement.executeUpdate("delete from manager;");
            statement.executeUpdate("delete from operator;");
            statement.executeUpdate("delete from topmanager;");
            statement.executeUpdate("delete from company;");
            conn.commit();
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int selectEmployeeId(String command) {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/oopdb", "postgres", "ROOT");
             Statement statement = conn.createStatement()) {
            ResultSet select = statement.executeQuery(command);
            if (select.next()){
                return select.getInt("id");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void fireEmploee(String command){
        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/oopdb", "postgres", "ROOT");
             Statement statement = conn.createStatement()) {
            conn.setAutoCommit(false);
            statement.executeUpdate(command);
            conn.commit();
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public  static void deleteEmploee(String nameOfCTable, Company Company){
        String command;
        int id;

        command = "select id from " + nameOfCTable + " where company_name = '" + Company.getName() +"' ";
        id = SqlManager.selectEmployeeId(command);
        command = "delete from "+nameOfCTable+" where id = "+id+";";
        fireEmploee(command);
    }

    public static void insertHireAll(List<Employee> List, Company Company){
        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/oopdb", "postgres", "ROOT");
             Statement statement = conn.createStatement()) {
            conn.setAutoCommit(false);
            for (Employee e:
                 List) {
                if(e instanceof Operator){
                    statement.executeUpdate("INSERT INTO operator (monthsalary, company_name) " +
                            "VALUES ('" + e.getMounthSalary() + "','"+ Company.getName() + "')");
                }else if(e instanceof Manager){
                    statement.executeUpdate("INSERT INTO manager (company_name, personal_income, mounthsalary) " +
                            "VALUES ('" + Company.getName() + "','" + ((Manager) e).getPersonIncome() + "'," + e.getMounthSalary() +")");
                } else if (e instanceof TopManager) {
                    statement.executeUpdate("INSERT INTO topmanager (company_name, mounthsalary) " +
                            "VALUES ('" + Company.getName() + "','"+ e.getMounthSalary() + "')");
                }
            }
            conn.commit();
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private static void insert(String command) {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/oopdb", "postgres", "ROOT");
             Statement statement = conn.createStatement()) {
            statement.executeUpdate(command);
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateCompanyIncome(String name, int income) {
        String command = "Update company SET income = " + income + " WHERE name = '" + name + "'";
        insert(command);
    }

    public static void generateInsertCompany(String name, int income) {
            String command = "INSERT INTO company (name, income) " +
                    "VALUES ('" + name + "',"+ income + ")";
            insert(command);
    }

    public static void generateInsertOperator(String name, int income) {
            String command = "INSERT INTO operator (monthsalary, company_name) " +
                    "VALUES ('" + income + "','"+ name + "')";
            insert(command);
    }

    public static void generateInsertManager(String name, int income, int p_income) {
        String command = "INSERT INTO manager (company_name, personal_income, mounthsalary) " +
                "VALUES ('" + name + "','"+ p_income + "'," + income +")";
        insert(command);
    }

    public static void generateInsertTopManager(String name, int income) {
        String command = "INSERT INTO topmanager (company_name, mounthsalary) " +
                "VALUES ('" + name + "','"+ income + "')";
        insert(command);
    }
}
