package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connection.ConnectionFactory;
import models.Student;

public class StudentDAO extends ConnectionFactory {
    
    public void insert(Student student){
        String sql = "INSERT INTO alunos(cpf, nome, data_nasc, peso, altura) VALUES(?, ?, ?, ?, ?)";
        try{
            PreparedStatement stmt = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, student.getCpf());
            stmt.setString(2, student.getNome());
            stmt.setString(3, student.getData_nascimento());
            stmt.setDouble(4, student.getPeso());
            stmt.setDouble(5, student.getAltura());
            stmt.execute();
            ResultSet rs = stmt.getGeneratedKeys();
            while(rs.next()){
                student.setId(rs.getInt(1));
            }
            stmt.close();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void delete(Student student){
        String sql = "DELETE FROM alunos WHERE id = ?";
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, student.getId());
            stmt.executeUpdate();
            stmt.close();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void update(Student student){
        String sql = "UPDATE alunos SET cpf = ?, nome = ?, data_nasc = ?, peso = ?, altura = ? WHERE id = ?";
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, student.getCpf());
            stmt.setString(2, student.getNome());
            stmt.setString(3, student.getData_nascimento());
            stmt.setDouble(4, student.getPeso());
            stmt.setDouble(5, student.getAltura());
            stmt.setInt(6, student.getId());
            stmt.execute();
            stmt.close();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Student> getAll(){
        String sql ="SELECT * FROM alunos";
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            ArrayList<Student> list = new ArrayList<>();
            while(rs.next()){
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setCpf(rs.getString("cpf"));
                student.setNome(rs.getString("nome"));
                student.setData_nascimento(rs.getString("data_nasc"));
                student.setPeso(rs.getDouble("altura"));
                student.setAltura(rs.getDouble("altura"));
                list.add(student);
            }
            stmt.close();
            return list;
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
}
