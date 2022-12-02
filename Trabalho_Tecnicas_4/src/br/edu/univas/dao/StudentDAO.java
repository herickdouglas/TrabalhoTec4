package br.edu.univas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.edu.univas.vo.Student;

public class StudentDAO {

	private Connection connection;
	
	public StudentDAO() throws SQLException {
		connection = ConnectionUtil.getConnection();
	}
	
	public void save(Student student) {
		String sql = "insert into \"student\" (name, matricula, cpf, email) "
				+ "values (?, ?, ?, ?)";
		
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			int index = 1;
			statement.setString(index++, student.getName());
			statement.setString(index++, student.getMatricula());
			statement.setString(index++, student.getCpf());
			statement.setString(index++, student.getEmail());
			statement.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Student> getAll() {
		List<Student> students = new ArrayList<Student>();
		
		String sql = "select * from student";
		
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			
			while (result.next()) {
				Student student = new Student();
				student.setName(result.getString("name"));
				student.setMatricula(result.getString("matricula"));
				student.setEmail(result.getString("email"));
				student.setCpf(result.getString("cpf"));
				students.add(student);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return students;
	}
}
