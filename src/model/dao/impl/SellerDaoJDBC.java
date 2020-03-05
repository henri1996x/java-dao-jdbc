package model.dao.impl;

import connection.ConnectionClass;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {

    @Override
    public void insert(Seller obj) {

        String sql = "INSERT INTO seller"
                + "(Name, Email, BirthDate, BaseSalary, DepartmentId)"
                + " VALUES (?, ?, ?, ?, ?)";

        try(Connection conn = ConnectionClass.getConnection()){
            try(PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                st.setString(1, obj.getName());
                st.setString(2, obj.getEmail());
                st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
                st.setDouble(4, obj.getBaseSalary());
                st.setInt(5, obj.getDepartment().getId());

                int rowsAffected = st.executeUpdate();
                if(rowsAffected > 0){
                    try(ResultSet  rs = st.getGeneratedKeys()){
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
    public void update(Seller obj) {

        String sql = "UPDATE seller "
                + "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
                + "WHERE Id = ?";

        try(Connection conn = ConnectionClass.getConnection()){
            try(PreparedStatement st = conn.prepareStatement(sql)){
                st.setString(1, obj.getName());
                st.setString(2, obj.getEmail());
                st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
                st.setDouble(4, obj.getBaseSalary());
                st.setInt(5, obj.getDepartment().getId());
                st.setInt(6, obj.getId());
                st.executeUpdate();
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteById(Integer id) {

        String sql = "DELETE FROM seller WHERE Id = ?";

        try(Connection conn = ConnectionClass.getConnection()){
            try(PreparedStatement st = conn.prepareStatement(sql)){
                st.setInt(1, id);
                int rowsAffected = st.executeUpdate();
                if(rowsAffected == 0){
                    throw new SQLException("No Seller found!!");
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public Seller findById(Integer id) {

        String sql = "SELECT seller.*,department.Name as DepName "
                    + "FROM seller INNER JOIN department "
                    + "ON seller.DepartmentId = department.Id "
                    + "WHERE seller.Id = ?";
        try(Connection conn = ConnectionClass.getConnection()){
            try(PreparedStatement st = conn.prepareStatement(sql)) {
                st.setInt(1, id);
                try (ResultSet rs = st.executeQuery()) {
                    if (rs.next()) { //se o rs.next for falso, ele irá pular o if e cairá no else. logo, não haverá um seller com esse id.
                        Department dep = new Department();
                        dep.setId(rs.getInt("DepartmentId"));
                        dep.setName(rs.getString("DepName"));
                        Seller obj = new Seller();
                        obj.setId(rs.getInt("Id"));
                        obj.setName(rs.getString("Name"));
                        obj.setEmail(rs.getString("Email"));
                        obj.setBaseSalary(rs.getDouble("BaseSalary"));
                        obj.setBirthDate(rs.getDate("BirthDate"));
                        obj.setDepartment(dep);
                        return obj;
                    } else {
                        return null;
                    }
                }
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Seller> findAll() {

        String sql =  "SELECT seller.*,department.Name as DepName "
                    + "FROM seller INNER JOIN department "
                    + "ON seller.DepartmentId = department.Id "
                    + "ORDER BY Name";

        List<Seller> list = new ArrayList<>();
        Map<Integer, Department> map = new HashMap<>(); //A chave dele é integer e o valor de cada objeto é do tipo department
        try(Connection conn = ConnectionClass.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement(sql)) {
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        Department dep = map.get(rs.getInt("DepartmentId"));
                        //Se o departamento já existir, o map.get ira pegar ele
                        //o if retornará falso e o departamento será reaproveitado
                        //agora se o departamento não existir, o map.get retornará nulo
                        //o if dará verdadeiro e irá instanciar o department

                        if (dep == null) {
                            dep = new Department();
                            map.put(rs.getInt("DepartmentId"), dep);
                        }

                        dep.setId(rs.getInt("DepartmentId"));
                        dep.setName(rs.getString("DepName"));
                        Seller obj = new Seller();
                        obj.setId(rs.getInt("Id"));
                        obj.setName(rs.getString("Name"));
                        obj.setEmail(rs.getString("Email"));
                        obj.setBaseSalary(rs.getDouble("BaseSalary"));
                        obj.setBirthDate(rs.getDate("BirthDate"));
                        obj.setDepartment(dep);
                        list.add(obj);
                    }
                    return list;
                }
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }


    @Override
    public List<Seller> findByDepartment(Department department) {

        String sql =  "SELECT seller.*,department.Name as DepName "
                    + "FROM seller INNER JOIN department "
                    + "ON seller.DepartmentId = department.Id "
                    + "WHERE DepartmentId = ? "
                    + "ORDER BY Name";

        List<Seller> list = new ArrayList<>();
        Map<Integer, Department> map = new HashMap<>(); //A chave dele é integer e o valor de cada objeto é do tipo department
        try(Connection conn = ConnectionClass.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement(sql)) {
                st.setInt(1, department.getId());
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        Department dep = map.get(rs.getInt("DepartmentId"));
                        //Se o departamento já existir, o map.get ira pegar ele
                        //o if retornará falso e o departamento será reaproveitado
                        //agora se o departamento não existir, o map.get retornará nulo
                        //o if dará verdadeiro e irá instanciar o department

                        if (dep == null) {
                            dep = new Department();
                            map.put(rs.getInt("DepartmentId"), dep);
                        }

                        dep.setId(rs.getInt("DepartmentId"));
                        dep.setName(rs.getString("DepName"));
                        Seller obj = new Seller();
                        obj.setId(rs.getInt("Id"));
                        obj.setName(rs.getString("Name"));
                        obj.setEmail(rs.getString("Email"));
                        obj.setBaseSalary(rs.getDouble("BaseSalary"));
                        obj.setBirthDate(rs.getDate("BirthDate"));
                        obj.setDepartment(dep);
                        list.add(obj);
                    }
                    return list;
                }
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
