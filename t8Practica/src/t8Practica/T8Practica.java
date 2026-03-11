package t8Practica;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import t8Practica.ConnectionSingleton;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class ConnectionSingleton {

	private static Connection con;

	public static Connection getConnection() throws SQLException {
		String url = "jdbc:mysql://127.0.0.1:3307/t8Practica";
		String user = "alumno";
		String password = "alumno";
		if (con == null || con.isClosed()) {
			con = DriverManager.getConnection(url, user, password);
		}
		return con;
		// Connection con= ConnectionSingleton.getConnection(); PARA LLAMARLO
	}
}

public class T8Practica {

	private JFrame frame;
	private JTable tableAlumno;
	private JTable tableMatricula;
	private JTable tableAsignatura;
	private JTextField textFieldIdAl;
	private JTextField textFieldNomAl;
	private JTextField textFieldEdadAl;
	private JTextField textFieldIdAs;
	private JTextField textFieldNomAs;
	private JTextField textFieldHorasAs;

	private void mostrarTablaAlumno() {
		try {
			DefaultTableModel model = new DefaultTableModel();
			Connection con = ConnectionSingleton.getConnection();

			model.addColumn("ID");
			model.addColumn("Nombre");
			model.addColumn("Edad");

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Alumno");
			while (rs.next()) {
				Object[] row = new Object[3];
				row[0] = rs.getInt("idAlumno");
				row[1] = rs.getString("nombre");
				row[2] = rs.getInt("edad");
				model.addRow(row);
			}
			tableAlumno.setModel(model);
			rs.close();
			stmt.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
	}

	private void mostrarTablaAsignatura() {
		try {
			DefaultTableModel model = new DefaultTableModel();
			Connection con = ConnectionSingleton.getConnection();

			model.addColumn("ID");
			model.addColumn("Nombre");
			model.addColumn("Horas");

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Asignatura");
			while (rs.next()) {
				Object[] row = new Object[3];
				row[0] = rs.getInt("idAsignatura");
				row[1] = rs.getString("nombre");
				row[2] = rs.getInt("horas");
				model.addRow(row);
			}
			tableAsignatura.setModel(model);
			rs.close();
			stmt.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
	}

	private void crearAsignatura() {
		try {
			Connection con = ConnectionSingleton.getConnection();
			PreparedStatement ins_pstmt = con
					.prepareStatement("INSERT INTO Asignatura (nombre, horas) " + "VALUES (?, ?)");

			String nombre = textFieldNomAs.getText();
			ins_pstmt.setString(1, nombre);
			String horas = textFieldHorasAs.getText();
			int horasI = Integer.parseInt(horas);
			ins_pstmt.setInt(2, horasI);

			int rowsInserted = ins_pstmt.executeUpdate();
			ins_pstmt.close();
			JOptionPane.showMessageDialog(null, "Asignatura creada");
		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
	}

	private void crearAlumno() {
		try {
			Connection con = ConnectionSingleton.getConnection();
			PreparedStatement ins_pstmt = con.prepareStatement("INSERT INTO Alumno (nombre, edad) " + "VALUES (?, ?)");

			String nombre = textFieldNomAl.getText();
			ins_pstmt.setString(1, nombre);
			String edad = textFieldEdadAl.getText();
			int edadI = Integer.parseInt(edad);

			ins_pstmt.setInt(2, edadI);
			int rowsInserted = ins_pstmt.executeUpdate();
			ins_pstmt.close();
			JOptionPane.showMessageDialog(null, "Alumno creado");
		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					T8Practica window = new T8Practica();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public T8Practica() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.setBounds(100, 100, 591, 356);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(33, 51, 151, 93);
		frame.getContentPane().add(scrollPane);

		JScrollPane scrollPane1 = new JScrollPane();
		scrollPane1.setBounds(403, 51, 151, 93);
		frame.getContentPane().add(scrollPane1);

		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane2.setBounds(218, 51, 151, 93);
		frame.getContentPane().add(scrollPane2);

		JLabel lblAlumno = new JLabel("Alumno");
		lblAlumno.setBounds(76, 23, 60, 17);
		frame.getContentPane().add(lblAlumno);

		JLabel lblMatricula = new JLabel("Matrícula");
		lblMatricula.setBounds(266, 23, 60, 17);
		frame.getContentPane().add(lblMatricula);

		tableMatricula = new JTable();
		scrollPane2.setViewportView(tableMatricula);

		tableAlumno = new JTable();
		scrollPane.setViewportView(tableAlumno);

		tableAsignatura = new JTable();
		scrollPane1.setViewportView(tableAsignatura);

		JLabel lblAsignatura = new JLabel("Asignatura");
		lblAsignatura.setBounds(440, 23, 87, 17);
		frame.getContentPane().add(lblAsignatura);

		JLabel lblId = new JLabel("ID:");
		lblId.setBounds(33, 173, 60, 17);
		frame.getContentPane().add(lblId);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(33, 210, 60, 17);
		frame.getContentPane().add(lblNombre);

		JLabel lblEdad = new JLabel("Edad:");
		lblEdad.setBounds(33, 247, 60, 17);
		frame.getContentPane().add(lblEdad);

		textFieldIdAl = new JTextField();
		textFieldIdAl.setEditable(false);
		textFieldIdAl.setBounds(76, 171, 100, 21);
		frame.getContentPane().add(textFieldIdAl);
		textFieldIdAl.setColumns(10);

		textFieldNomAl = new JTextField();
		textFieldNomAl.setBounds(96, 208, 114, 21);
		frame.getContentPane().add(textFieldNomAl);
		textFieldNomAl.setColumns(10);

		textFieldEdadAl = new JTextField();
		textFieldEdadAl.setBounds(70, 245, 114, 21);
		frame.getContentPane().add(textFieldEdadAl);
		textFieldEdadAl.setColumns(10);

		JButton btnMatricular = new JButton("Matricular");
		btnMatricular.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		btnMatricular.setBounds(250, 168, 105, 27);
		frame.getContentPane().add(btnMatricular);

		JButton btnDesmatricular = new JButton("Desmatricular");
		btnDesmatricular.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		btnDesmatricular.setBounds(239, 205, 130, 27);
		frame.getContentPane().add(btnDesmatricular);

		JLabel lblIdAs = new JLabel("ID:");
		lblIdAs.setBounds(394, 173, 60, 17);
		frame.getContentPane().add(lblIdAs);

		JLabel lblNombreAs = new JLabel("Nombre:");
		lblNombreAs.setBounds(394, 210, 60, 17);
		frame.getContentPane().add(lblNombreAs);

		JLabel lblHorasAs = new JLabel("Horas");
		lblHorasAs.setBounds(394, 247, 60, 17);
		frame.getContentPane().add(lblHorasAs);

		textFieldIdAs = new JTextField();
		textFieldIdAs.setEditable(false);
		textFieldIdAs.setColumns(10);
		textFieldIdAs.setBounds(436, 171, 100, 21);
		frame.getContentPane().add(textFieldIdAs);

		textFieldNomAs = new JTextField();
		textFieldNomAs.setColumns(10);
		textFieldNomAs.setBounds(456, 208, 114, 21);
		frame.getContentPane().add(textFieldNomAs);

		textFieldHorasAs = new JTextField();
		textFieldHorasAs.setColumns(10);
		textFieldHorasAs.setBounds(440, 245, 114, 21);
		frame.getContentPane().add(textFieldHorasAs);

		JButton btnGuardarAl = new JButton("Guardar");
		btnGuardarAl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				crearAlumno();
				mostrarTablaAlumno();
			}
		});
		btnGuardarAl.setBounds(12, 274, 81, 27);
		frame.getContentPane().add(btnGuardarAl);

		JButton btnNewButtonAl = new JButton("Act");
		btnNewButtonAl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int index = tableAlumno.getSelectedRow();
				TableModel model = tableAlumno.getModel();
				// ID NAME AGE CITY
				textFieldIdAl.setText(model.getValueAt(index, 0).toString());
				textFieldNomAl.setText(model.getValueAt(index, 1).toString());
				textFieldEdadAl.setText(model.getValueAt(index, 2).toString());

				try {
					int id = Integer.parseInt(textFieldIdAl.getText());
					String nombre = textFieldNomAl.getText();
					int edad = Integer.parseInt(textFieldEdadAl.getText());
					Connection con = ConnectionSingleton.getConnection();
					PreparedStatement upd_pstmt = con
							.prepareStatement("UPDATE Alumno SET nombre = ?, edad = ? WHERE idAlumno = ?");
					
					upd_pstmt.setString(1, nombre);
					upd_pstmt.setInt(2, edad);
					upd_pstmt.setInt(3, id);
					upd_pstmt.executeUpdate();
					mostrarTablaAlumno();
					upd_pstmt.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});
		btnNewButtonAl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButtonAl.setBounds(96, 274, 53, 27);
		frame.getContentPane().add(btnNewButtonAl);

		JButton btnBorrarAl = new JButton("Borrar");
		btnBorrarAl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = tableAlumno.getSelectedRow();
				TableModel model = tableAlumno.getModel();
				// ID NAME AGE CITY
				textFieldIdAl.setText(model.getValueAt(index, 0).toString());
				textFieldNomAl.setText(model.getValueAt(index, 1).toString());
				textFieldEdadAl.setText(model.getValueAt(index, 2).toString());

				try {
					String id = model.getValueAt(index, 0).toString();
					Connection con = ConnectionSingleton.getConnection();
					PreparedStatement dele_pstmt = con.prepareStatement("DELETE FROM Alumno WHERE idAlumno = ?");
					dele_pstmt.setString(1, id);
					dele_pstmt.executeUpdate();
					JOptionPane.showMessageDialog(null, "Alumno eliminado");
					mostrarTablaAlumno();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnBorrarAl.setBounds(151, 274, 71, 27);
		frame.getContentPane().add(btnBorrarAl);

		JButton btnGuardarAs = new JButton("Guardar");
		btnGuardarAs.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				crearAsignatura();
				mostrarTablaAsignatura();
			}
		});
		btnGuardarAs.setBounds(369, 274, 81, 27);
		frame.getContentPane().add(btnGuardarAs);

		JButton btnNewButtonAs = new JButton("Act");
		btnNewButtonAs.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		btnNewButtonAs.setBounds(453, 274, 53, 27);
		frame.getContentPane().add(btnNewButtonAs);

		JButton btnBorrarAs = new JButton("Borrar");
		btnBorrarAs.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = tableAsignatura.getSelectedRow();
				TableModel model = tableAsignatura.getModel();
				// ID NAME AGE CITY
				textFieldIdAs.setText(model.getValueAt(index, 0).toString());
				textFieldNomAs.setText(model.getValueAt(index, 1).toString());
				textFieldHorasAs.setText(model.getValueAt(index, 2).toString());

				try {
					String id = model.getValueAt(index, 0).toString();
					Connection con = ConnectionSingleton.getConnection();
					PreparedStatement dele_pstmt = con
							.prepareStatement("DELETE FROM Asignatura WHERE idAsignatura = ?");
					dele_pstmt.setString(1, id);
					dele_pstmt.executeUpdate();
					JOptionPane.showMessageDialog(null, "Asignatura eliminado");
					mostrarTablaAsignatura();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		btnBorrarAs.setBounds(508, 274, 71, 27);
		frame.getContentPane().add(btnBorrarAs);
		mostrarTablaAlumno();
		mostrarTablaAsignatura();
	}
}
