package dao.impl;

import dao.DbConnection;
import dao.GeneralDao;
import model.Employee;
import model.Employeelog;
import model.Member;
import model.PDStock;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GeneralDaoImpl2 implements GeneralDao {

    public static void main(String[] args) {
        GeneralDaoImpl2 dao = new GeneralDaoImpl2();
/*        // 添加员工示例
        PDStock newPDStock = new PDStock("PD1", "NB001", "GOOD NB1", "11111111", "10000","100");
        dao.add(newPDStock);
        System.out.println("Added employee: " + newPDStock);

        // 添加员工示例
        Employee newEmployee = new Employee("Jane Smith", "HR", "Manager", "janesmith@example.com", "0987654321");
        dao.add(newEmployee);
        System.out.println("Added employee: " + newEmployee);
*/
        // 查询员工示例
        Map<String, String> employeeParams = new HashMap<>();
        employeeParams.put("idEM", "2");
        List<Employee> employees = dao.queryByParameters("employee", employeeParams, Employee.class);
/*
        employee.setBoss("1122334455");
        dao.update(employee);
        System.out.println("Updated employee: " + employee);
*/
        
        
        /*            
            if (!employees.isEmpty()) {
                List<Integer> ids = new ArrayList<>();
                List<String> userIDs = new ArrayList<>();
                for (Employee employee : employees) {
                    Integer id = employee.getId();
                    String userID = employee.getUserID();
                    ids.add(id);
                    userIDs.add(userID);
                    
                    System.out.println("Queried employee: " + employee+ " "+ids.toString());
                }
                userIDs.get(0);
            }
            Employeelog newEmployeelog = new Employeelog("John Doe", "AA", "BB");
                    
            //dao.add(newEmployeelog);
            //userIDs.get(0); \\if之外就沒有了
            
            // 更新员工示例
            employee.setBoss("1122334455");
            dao.update(employee);
            System.out.println("Updated employee: " + employee);

            // 删除员工示例
            //dao.delete(employee.getId(), Employee.class);
            //System.out.println("Deleted employee with id: " + employee.getId());
                

        // 添加成员示例
        Member newMember = new Member("John Doe", "johndoe", "password", "johndoe@example.com", "1234567890",
                "123 Main St", "hint", "1");
        dao.add(newMember);
        //System.out.println("Added member: " + newMember);

        // 查询成员示例
        Map<String, String> parameters = new HashMap<>();
        parameters.put("name", "a2");
        parameters.put("userID", "");
        List<Member> members = dao.queryByParameters("member", parameters, Member.class);
        System.out.println(members.getClass());
        for (Member member : members) {
            System.out.println("Queried member: " + member);
        }
        		//System.out.println(members.toArray());
            // 更新成员示例
            members.setPassword("ppp");
            dao.update(members);
            System.out.println("Updated member: " + member);

            // 删除成员示例
            dao.delete(members.getId(), Member.class);
            System.out.println("Deleted member with id: " + member.getId());
   */   
    }

    @Override
    public void add(Object obj) {
        Class<?> clazz = obj.getClass();
        String tableName = clazz.getSimpleName().toLowerCase(); // 假设表名和类名相同且为小写
        Field[] fields = clazz.getDeclaredFields();

        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();
        List<Object> parameterValues = new ArrayList<>();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(obj);
                if (value != null && !field.getName().equalsIgnoreCase("id")) { // 忽略ID字段
                    if (columns.length() > 0) {
                        columns.append(", ");
                        values.append(", ");
                    }
                    columns.append(field.getName());
                    values.append("?");
                    parameterValues.add(value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        String SQL = "INSERT INTO " + tableName + " (" + columns.toString() + ") VALUES (" + values.toString() + ")";
        System.out.println(SQL);

        try (Connection conn = DbConnection.getDbC(); PreparedStatement ps = conn.prepareStatement(SQL)) {
            for (int i = 0; i < parameterValues.size(); i++) {
                ps.setObject(i + 1, parameterValues.get(i));
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T> List<T> queryByParameters(String tableName, Map<String, String> parameters, Class<T> clazz) {
        StringBuilder subSQL = new StringBuilder();
        List<String> parameterValues = new ArrayList<>();

        Set<Map.Entry<String, String>> entrySet = parameters.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            if (!entry.getValue().isEmpty()) {
                if (subSQL.length() > 0)
                    subSQL.append(" and ");
                subSQL.append(entry.getKey()).append("=?");
                parameterValues.add(entry.getValue());
            }
        }

        String SQL = subSQL.length() == 0
                ? "SELECT * FROM " + tableName
                : "SELECT * FROM " + tableName + " WHERE " + subSQL.toString();

        System.out.println(SQL);
        List<T> result = new ArrayList<>();
        try (Connection conn = DbConnection.getDbC(); PreparedStatement ps = conn.prepareStatement(SQL)) {
            for (int i = 0; i < parameterValues.size(); i++) {
                ps.setString(i + 1, parameterValues.get(i));
            }
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (rs.next()) {
                T instance = clazz.getDeclaredConstructor().newInstance();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object columnValue = rs.getObject(i);
                    try {
                        Field field = clazz.getDeclaredField(columnName);
                        field.setAccessible(true);
                        field.set(instance, columnValue);
                    } catch (NoSuchFieldException e) {
                        // 字段在类中不存在，继续处理下一个字段
                    }
                }
                result.add(instance);
            }
        } catch (SQLException | ReflectiveOperationException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void update(Object obj) {
        Class<?> clazz = obj.getClass();
        String tableName = clazz.getSimpleName().toLowerCase(); // 假设表名和类名相同且为小写
        Field[] fields = clazz.getDeclaredFields();

        StringBuilder setClause = new StringBuilder();
        List<Object> parameterValues = new ArrayList<>();
        Integer id = null;

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(obj);
                if (value != null) {
                    if (field.getName().equalsIgnoreCase("id")) {
                        id = (Integer) value;
                    } else {
                        if (setClause.length() > 0) {
                            setClause.append(", ");
                        }
                        setClause.append(field.getName()).append("=?");
                        parameterValues.add(value);
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        if (id == null) {
            throw new IllegalArgumentException("Object must have an 'id' field.");
        }

        String SQL = "UPDATE " + tableName + " SET " + setClause.toString() + " WHERE id=?";
        parameterValues.add(id);

        System.out.println(SQL);

        try (Connection conn = DbConnection.getDbC(); PreparedStatement ps = conn.prepareStatement(SQL)) {
            for (int i = 0; i < parameterValues.size(); i++) {
                ps.setObject(i + 1, parameterValues.get(i));
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void delete(Object id, Class<?> clazz) {
        String tableName = clazz.getSimpleName().toLowerCase(); // 假设表名和类名相同且为小写
        String SQL = "DELETE FROM " + tableName + " WHERE id=?";

        System.out.println(SQL);

        try (Connection conn = DbConnection.getDbC(); PreparedStatement ps = conn.prepareStatement(SQL)) {
            ps.setObject(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
