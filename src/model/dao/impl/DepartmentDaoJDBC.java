package model.dao.impl;

import connection.ConnectionClass;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDao {
    @Override
    public void insert(Department obj) {

        String sql = "INSERT INTO department " +
                "(Name) " +
                "VALUES " +
                "(?)";

        try(Connection connection = ConnectionClass.getConnection()){
            try(PreparedStatement st = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                st.setString(1, obj.getName());
                int rowsAffected = st.executeUpdate();
                if(rowsAffected > 0){
                    try(ResultSet rs = st.getGeneratedKeys()){
                        if(rs.next()){
                            int id = rs.getInt(1);
                            obj.setId(id);
                        }
                    }
                }
                else{
                    throw new SQLException("Error! No rows affected.");
                }
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Department obj) {

        String sql = "UPDATE department "
                +    "SET Name = ? "
                +    "WHERE Id = ?";

        try(Connection conn = ConnectionClass.getConnection()){
            try(PreparedStatement st = conn.prepareStatement(sql)){
                st.setString(1, obj.getName());
                st.setInt(2,obj.getId());
                st.executeUpdate();
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteById(Integer id) {

        String sql = "DELETE FROM department WHERE Id = ?";

        try(Connection conn = ConnectionClass.getConnection()){
            try(PreparedStatement st = conn.prepareStatement(sql)){
                st.setInt(1, id);
                int rowsAffected = st.executeUpdate();
                if(rowsAffected == 0){
                    throw new SQLException("No Department found!!");
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    @Override
    public Department findById(Integer id) {

        String sql = "SELECT * FROM department WHERE Id = ?";

        try(Connection conn = ConnectionClass.getConnection()){
            try(PreparedStatement st = conn.prepareStatement(sql)){
                st.setInt(1, id);
                try(ResultSet rs = st.executeQuery()){
                    if(rs.next()){
                        Department department = new Department();
                        department.setId(rs.getInt("Id"));
                        department.setName(rs.getString("Name"));
                        return department;
                    }
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Department> findAll() {

        String sql = "SELECT * FROM department ORDER BY Name";

        try(Connection conn = ConnectionClass.getConnection()){
            try(PreparedStatement st = conn.prepareStatement(sql)){
                try(ResultSet rs = st.executeQuery()){
                    List <Department> list = new ArrayList<>();
                    while (rs.next()){
                        Department department = new Department();
                        department.setId(rs.getInt("Id"));
                        department.setName(rs.getString("Name"));
                        list.add(department);
                    }
                    return list;
                }
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
