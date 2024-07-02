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

public class GeneralDaoImpl3 implements GeneralDao {

    public static void main(String[] args) {
        GeneralDaoImpl3 dao = new GeneralDaoImpl3();

        // 添加成员示例
        Member newMember = new Member();
        newMember.setId(1); // 确保这个ID在你的数据库中是唯一的
        newMember.setMBname("John Doe");
        newMember.setMBID("johndoe");
        newMember.setMBPW("password");
        newMember.setMBemail("johndoe@example.com");
        newMember.setMBphone("1234567890");
        newMember.setMBaddress("123 Main St");
        newMember.setMBhint("hint");
        newMember.setMBlevel("1");

        dao.add(newMember);
        System.out.println("Added member: " + newMember);

        // 查询成员示例
        Map<String, String> memberParams = new HashMap<>();
        memberParams.put("id", "1");
        List<Member> members = dao.queryByParameters("member", memberParams, Member.class);

        for (Member member : members) {
            printMemberDetails(member);
        }

        // 更新成员示例
        if (!members.isEmpty()) {
            Member memberToUpdate = members.get(0);
            memberToUpdate.setMBPW("newpassword");
            dao.update(memberToUpdate);
            System.out.println("Updated member: " + memberToUpdate);
        }

        // 再次查询以验证更新
        members = dao.queryByParameters("member", memberParams, Member.class);
        for (Member member : members) {
            printMemberDetails(member);
        }

        // 删除成员示例
        if (!members.isEmpty()) {
            Member memberToDelete = members.get(0);
            dao.delete(memberToDelete.getId(), Member.class);
            System.out.println("Deleted member with id: " + memberToDelete.getId());
        }

        // 最后查询以验证删除
        members = dao.queryByParameters("member", memberParams, Member.class);
        if (members.isEmpty()) {
            System.out.println("Member successfully deleted.");
        } else {
            System.out.println("Member deletion failed.");
        }
    }

    private static void printMemberDetails(Member member) {
        System.out.println("Member Details: ");
        System.out.println("ID: " + member.getId());
        System.out.println("Name: " + member.getMBname());
        System.out.println("UserID: " + member.getMBID());
        System.out.println("Password: " + member.getMBPW());
        System.out.println("Email: " + member.getMBemail());
        System.out.println("Phone: " + member.getMBphone());
        System.out.println("Address: " + member.getMBaddress());
        System.out.println("Hint: " + member.getMBhint());
        System.out.println("Level: " + member.getMBlevel());
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
        System.out.println("Parameters: " + parameterValues);

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
