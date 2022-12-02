package br.edu.univas.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import br.edu.univas.dao.StudentDAO;
import br.edu.univas.vo.Student;


public class StudentFrame extends JFrame {

	private StudentDAO dao;
	private JTextField nameTextField;
	private JTextField registryTextField;
	private JTextField cpfTextField;
	private JTextField emailTextField;
	private JTable studentTable;
	
	private JPanel contentPane;
	
	public StudentFrame() {
		setTitle("Cadastro de Alunos");
		setSize(800, 640);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new FlowLayout());
		
		try {
			dao = new StudentDAO();
			initialize();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void initialize() throws SQLException {
		
		contentPane = new JPanel();
		contentPane.setLayout(new GridBagLayout());
		setContentPane(contentPane);
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		JLabel nameLabel = new JLabel();
		nameLabel.setText("Nome");
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(15, 15, 15, 15);
		gbc.fill = GridBagConstraints.BOTH;
		getContentPane().add(nameLabel, gbc);
		
		nameTextField = new JTextField();
		gbc.gridx = 1;
		gbc.weightx = 1.0;
		gbc.gridwidth = 3;
		getContentPane().add(nameTextField, gbc);
		
		JLabel registryLabel = new JLabel();
		registryLabel.setText("Matricula");
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.weightx = 0.0;
		getContentPane().add(registryLabel, gbc);
		
		registryTextField = new JTextField();
		gbc.gridx = 1;
		gbc.weightx = 1.0;
		getContentPane().add(registryTextField, gbc);
		
		JLabel cpfLabel = new JLabel();
		cpfLabel.setText("CPF");
		gbc.gridx = 2;
		gbc.weightx = 0.0;
		contentPane.add(cpfLabel, gbc);
		
		cpfTextField = new JTextField();
		gbc.gridx = 3;
		gbc.weightx = 1.0;
		getContentPane().add(cpfTextField, gbc);

		JLabel emailLabel = new JLabel();
		emailLabel.setText("E-mail");
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 0.0;
		getContentPane().add(emailLabel, gbc);
		
		emailTextField = new JTextField();
		gbc.gridx = 1;
		gbc.weightx = 1.0;
		gbc.gridwidth = 3;
		getContentPane().add(emailTextField, gbc);
		
		JButton saveButton = new JButton();
		saveButton.setText("Salvar");
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.weightx = 0.0;
		gbc.gridwidth = 4;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.NONE;
		
		saveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				saveStudent();
			
			}
		});
		
		getContentPane().add(saveButton, gbc);
		
		Vector<String> columns = new Vector<String>();
		columns.add("Nome");
		columns.add("Matricula");
		columns.add("CPF");
		columns.add("E-mail");
		Vector<? extends Vector> vector = new Vector();
		
		studentTable = new JTable(vector, columns);
		
		JScrollPane studentTableScroll = new JScrollPane(studentTable);
		studentTableScroll.setMinimumSize(new Dimension(100, 350));
		studentTableScroll.setHorizontalScrollBarPolicy(
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		studentTableScroll.setVerticalScrollBarPolicy(
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.weightx = 1.0;
		gbc.gridwidth = 4;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.BOTH;
		getContentPane().add(studentTableScroll, gbc);
		updateTable();
	}

	private void saveStudent() {
		Student student = new Student();
		student.setName(nameTextField.getText());
		student.setMatricula(registryTextField.getText());
		student.setCpf(cpfTextField.getText());
		student.setEmail(emailTextField.getText());
	
		
		clearFields();
		JOptionPane.showMessageDialog(this, "Aluno cadastrado com sucesso!",
				"Sucesso", JOptionPane.INFORMATION_MESSAGE);
		
		try {
			dao.save(student);
			updateTable();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void updateTable() throws SQLException {
		DefaultTableModel tableModel = (DefaultTableModel) studentTable.getModel();
		tableModel.setRowCount(0);
		
		for (Student student : dao.getAll()) {
			Object[] data = {
					student.getName(),
					student.getMatricula(),
					student.getCpf(),
					student.getEmail()
					
			};
			
			tableModel.addRow(data);
		}
	}
	private void clearFields() {
		nameTextField.requestFocus();
		nameTextField.setText(null);
		cpfTextField.setText(null);
		emailTextField.setText(null);
		registryTextField.setText(null);
	}
}
