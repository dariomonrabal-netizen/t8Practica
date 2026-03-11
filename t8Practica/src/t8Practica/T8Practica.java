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
//connection
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

	// Para mostrar la tabla alumno
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

	// Para mostrar la tabla matricula
	private void mostrarTablaMatricula() {
		try {
			DefaultTableModel model = new DefaultTableModel();
			Connection con = ConnectionSingleton.getConnection();

			model.addColumn("ID Alumno");
			model.addColumn("ID Asignatura");

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Matricula");
			while (rs.next()) {
				Object[] row = new Object[2];
				row[0] = rs.getInt("idAlumno");
				row[1] = rs.getInt("idAsignatura");
				model.addRow(row);
			}
			tableMatricula.setModel(model);
			rs.close();
			stmt.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
	}

	// Para mostrar la tabla asignatura
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

	// Para crear la tabla asignatura
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

	// Para crear la tabla alumno
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
		frame.setBounds(100, 100, 1136, 494);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		// NADA
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int indexAlumno = tableAlumno.getSelectedRow();
				TableModel modelAlumno = tableAlumno.getModel();

				textFieldIdAl.setText(modelAlumno.getValueAt(indexAlumno, 0).toString());
				textFieldNomAl.setText(modelAlumno.getValueAt(indexAlumno, 1).toString());
				textFieldEdadAl.setText(modelAlumno.getValueAt(indexAlumno, 2).toString());
			}
		});
		scrollPane.setBounds(33, 51, 215, 157);
		frame.getContentPane().add(scrollPane);

//		NADA
		JScrollPane scrollPane1 = new JScrollPane();
		scrollPane1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int indexAsignatura = tableAsignatura.getSelectedRow();
				TableModel modelAsignatura = tableAsignatura.getModel();

				textFieldIdAs.setText(modelAsignatura.getValueAt(indexAsignatura, 0).toString());
				textFieldNomAs.setText(modelAsignatura.getValueAt(indexAsignatura, 1).toString());
				textFieldHorasAs.setText(modelAsignatura.getValueAt(indexAsignatura, 2).toString());
			}
		});
		scrollPane1.setBounds(598, 51, 228, 157);
		frame.getContentPane().add(scrollPane1);

		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane2.setBounds(301, 51, 235, 157);
		frame.getContentPane().add(scrollPane2);

		JLabel lblAlumno = new JLabel("Alumno");
		lblAlumno.setBounds(112, 22, 60, 17);
		frame.getContentPane().add(lblAlumno);

		JLabel lblMatricula = new JLabel("Matrícula");
		lblMatricula.setBounds(371, 22, 60, 17);
		frame.getContentPane().add(lblMatricula);

		tableMatricula = new JTable();
		scrollPane2.setViewportView(tableMatricula);

		//PARA CUANDO LE DES CLICK A UNO TE SALGA EN EL LABEL
		tableAlumno = new JTable();
		tableAlumno.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int indexAlumno = tableAlumno.getSelectedRow();
				TableModel modelAlumno = tableAlumno.getModel();

				textFieldIdAl.setText(modelAlumno.getValueAt(indexAlumno, 0).toString());
				textFieldNomAl.setText(modelAlumno.getValueAt(indexAlumno, 1).toString());
				textFieldEdadAl.setText(modelAlumno.getValueAt(indexAlumno, 2).toString());
			}
		});
		scrollPane.setViewportView(tableAlumno);
		//PARA CUANDO LE DES CLICK A UNO TE SALGA EN EL LABEL
		tableAsignatura = new JTable();
		tableAsignatura.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int indexAsignatura = tableAsignatura.getSelectedRow();
				TableModel modelAsignatura = tableAsignatura.getModel();

				textFieldIdAs.setText(modelAsignatura.getValueAt(indexAsignatura, 0).toString());
				textFieldNomAs.setText(modelAsignatura.getValueAt(indexAsignatura, 1).toString());
				textFieldHorasAs.setText(modelAsignatura.getValueAt(indexAsignatura, 2).toString());
			}
		});
		scrollPane1.setViewportView(tableAsignatura);

		JLabel lblAsignatura = new JLabel("Asignatura");
		lblAsignatura.setBounds(645, 22, 87, 17);
		frame.getContentPane().add(lblAsignatura);

		JLabel lblId = new JLabel("ID:");
		lblId.setBounds(71, 221, 60, 17);
		frame.getContentPane().add(lblId);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(71, 258, 60, 17);
		frame.getContentPane().add(lblNombre);

		JLabel lblEdad = new JLabel("Edad:");
		lblEdad.setBounds(71, 295, 60, 17);
		frame.getContentPane().add(lblEdad);

		textFieldIdAl = new JTextField();
		textFieldIdAl.setEditable(false);
		textFieldIdAl.setBounds(114, 219, 100, 21);
		frame.getContentPane().add(textFieldIdAl);
		textFieldIdAl.setColumns(10);

		textFieldNomAl = new JTextField();
		textFieldNomAl.setBounds(134, 256, 114, 21);
		frame.getContentPane().add(textFieldNomAl);
		textFieldNomAl.setColumns(10);

		textFieldEdadAl = new JTextField();
		textFieldEdadAl.setBounds(108, 293, 114, 21);
		frame.getContentPane().add(textFieldEdadAl);
		textFieldEdadAl.setColumns(10);

		//BOTON PARA MATRICULAR UNIENDO DE DOS TABLAS
		JButton btnMatricular = new JButton("Matricular");
		btnMatricular.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int indexAlumno = tableAlumno.getSelectedRow();
				int indexAsignatura = tableAsignatura.getSelectedRow();

				if (indexAlumno == -1 || indexAsignatura == -1) {
					JOptionPane.showMessageDialog(null, "Selecciona un alumno y una asignatura primero");
					return;
				}

				TableModel modelAlumno = tableAlumno.getModel();
				TableModel modelAsignatura = tableAsignatura.getModel();
				int idAlumno = Integer.parseInt(modelAlumno.getValueAt(indexAlumno, 0).toString());
				int idAsignatura = Integer.parseInt(modelAsignatura.getValueAt(indexAsignatura, 0).toString());
//para cambiar lo que quieras que se muestre
				textFieldIdAl.setText(String.valueOf(idAlumno));
				textFieldIdAs.setText(String.valueOf(idAsignatura));

				try {
					Connection con = ConnectionSingleton.getConnection();
					PreparedStatement pstmt = con
							.prepareStatement("INSERT INTO Matricula (idAlumno, idAsignatura) VALUES (?, ?)");
					pstmt.setInt(1, idAlumno);
					pstmt.setInt(2, idAsignatura);
					pstmt.executeUpdate();
					pstmt.close();
					 con.close();
					JOptionPane.showMessageDialog(null, "Matrícula creada correctamente");
					mostrarTablaMatricula();
					mostrarTablaAsignatura();
					mostrarTablaAlumno();
				} catch (SQLException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});
		btnMatricular.setBounds(361, 248, 105, 27);
		frame.getContentPane().add(btnMatricular);

		//BOTON PARA DESMATRICULAR UNIENDO DE DOS TABLAS
		JButton btnDesmatricular = new JButton("Desmatricular");
		btnDesmatricular.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = tableMatricula.getSelectedRow();

				if (index == -1) {
					JOptionPane.showMessageDialog(null, "Selecciona una fila de la tabla matrícula");
					return;
				}

				TableModel model = tableMatricula.getModel();
				int idAlumno = Integer.parseInt(model.getValueAt(index, 0).toString());
				int idAsignatura = Integer.parseInt(model.getValueAt(index, 1).toString());

				try {
					Connection con = ConnectionSingleton.getConnection();
					PreparedStatement pstmt = con
							.prepareStatement("DELETE FROM Matricula WHERE idAlumno = ? AND idAsignatura = ?");

					pstmt.setInt(1, idAlumno);
					pstmt.setInt(2, idAsignatura);
					pstmt.executeUpdate();
					pstmt.close();
					 con.close();
					JOptionPane.showMessageDialog(null, "Matrícula eliminada correctamente");

					mostrarTablaMatricula();

				} catch (SQLException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});
		btnDesmatricular.setBounds(350, 285, 130, 27);
		frame.getContentPane().add(btnDesmatricular);

		JLabel lblIdAs = new JLabel("ID:");
		lblIdAs.setBounds(627, 221, 60, 17);
		frame.getContentPane().add(lblIdAs);

		JLabel lblNombreAs = new JLabel("Nombre:");
		lblNombreAs.setBounds(627, 258, 60, 17);
		frame.getContentPane().add(lblNombreAs);

		JLabel lblHorasAs = new JLabel("Horas");
		lblHorasAs.setBounds(627, 295, 60, 17);
		frame.getContentPane().add(lblHorasAs);

		textFieldIdAs = new JTextField();
		textFieldIdAs.setEditable(false);
		textFieldIdAs.setColumns(10);
		textFieldIdAs.setBounds(669, 219, 100, 21);
		frame.getContentPane().add(textFieldIdAs);

		textFieldNomAs = new JTextField();
		textFieldNomAs.setColumns(10);
		textFieldNomAs.setBounds(689, 256, 114, 21);
		frame.getContentPane().add(textFieldNomAs);

		textFieldHorasAs = new JTextField();
		textFieldHorasAs.setColumns(10);
		textFieldHorasAs.setBounds(673, 293, 114, 21);
		frame.getContentPane().add(textFieldHorasAs);

		//BOTON PARA GUARDAR 
		JButton btnGuardarAl = new JButton("Guardar");
		btnGuardarAl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				crearAlumno();
				mostrarTablaAlumno();
			}
		});
		btnGuardarAl.setBounds(50, 322, 81, 27);
		frame.getContentPane().add(btnGuardarAl);

		//BOTON PARA ACTUALIZAR 
		JButton btnNewButtonAl = new JButton("Act");
		btnNewButtonAl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int index = tableAlumno.getSelectedRow();
				if (index == -1) {
					JOptionPane.showMessageDialog(null, "Selecciona un alumno de la tabla");
					return;
				}

				TableModel model = tableAlumno.getModel();
				int idAlumno = Integer.parseInt(model.getValueAt(index, 0).toString());
				String nombre = textFieldNomAl.getText();
				int edad = Integer.parseInt(textFieldEdadAl.getText());

				try {
					Connection con = ConnectionSingleton.getConnection();
					PreparedStatement pstmt = con
							.prepareStatement("UPDATE Alumno SET nombre = ?, edad = ? WHERE idAlumno = ?");

					pstmt.setString(1, nombre);
					pstmt.setInt(2, edad);
					pstmt.setInt(3, idAlumno);
					pstmt.executeUpdate();
					pstmt.close();
					 con.close();
					JOptionPane.showMessageDialog(null, "Alumno actualizado correctamente");
					mostrarTablaAlumno();

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
		btnNewButtonAl.setBounds(134, 322, 53, 27);
		frame.getContentPane().add(btnNewButtonAl);

		//BOTON PARA BORRAR 
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
					 con.close();
					JOptionPane.showMessageDialog(null, "Alumno eliminado");
					mostrarTablaAlumno();
					mostrarTablaMatricula();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnBorrarAl.setBounds(189, 322, 71, 27);
		frame.getContentPane().add(btnBorrarAl);

		//BOTON PARA GUARDAR 2
		JButton btnGuardarAs = new JButton("Guardar");
		btnGuardarAs.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				crearAsignatura();
				mostrarTablaAsignatura();
			}
		});
		btnGuardarAs.setBounds(602, 322, 81, 27);
		frame.getContentPane().add(btnGuardarAs);

		//BOTON PARA ACTUALIZAR 2 
		JButton btnNewButtonAs = new JButton("Act");
		btnNewButtonAs.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int index = tableAsignatura.getSelectedRow();
				if (index == -1) {
					JOptionPane.showMessageDialog(null, "Selecciona una asignatura de la tabla");
					return;
				}

				TableModel model = tableAsignatura.getModel();
				int idAsignatura = Integer.parseInt(model.getValueAt(index, 0).toString());
				String nombre = textFieldNomAs.getText();
				int horas = Integer.parseInt(textFieldHorasAs.getText());

				try {
					Connection con = ConnectionSingleton.getConnection();
					PreparedStatement pstmt = con
							.prepareStatement("UPDATE Asignatura SET nombre = ?, horas = ? WHERE idAsignatura = ?");

					pstmt.setString(1, nombre);
					pstmt.setInt(2, horas);
					pstmt.setInt(3, idAsignatura);
					pstmt.executeUpdate();
					pstmt.close();
					con.close();

					JOptionPane.showMessageDialog(null, "Asignatura actualizada correctamente");
					mostrarTablaAsignatura();

				} catch (SQLException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});
		btnNewButtonAs.setBounds(686, 322, 53, 27);
		frame.getContentPane().add(btnNewButtonAs);

		//BOTON PARA BORRAR 2 
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
					 con.close();
					JOptionPane.showMessageDialog(null, "Asignatura eliminado");
					mostrarTablaAsignatura();
					mostrarTablaMatricula();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		btnBorrarAs.setBounds(741, 322, 71, 27);
		frame.getContentPane().add(btnBorrarAs);
		
		JButton btnInformeAs = new JButton("Informe asignaturas");
		btnInformeAs.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = tableAsignatura.getSelectedRow();
				TableModel model = tableAsignatura.getModel();
				// haces variables lo que quieres mostrar y lo que quieres coger
				int idAsignatura = Integer.parseInt(model.getValueAt(index, 0).toString());
				String nombreAsignatura= model.getValueAt(index, 1).toString(); 
				//acuerdate cambiar el numero de columna

				textFieldIdAs.setText(model.getValueAt(index, 0).toString());
				textFieldNomAs.setText(model.getValueAt(index, 1).toString());
				textFieldHorasAs.setText(model.getValueAt(index, 2).toString());
				//esto es para que salga en los jlabel
				
				if (index == -1) {
				    JOptionPane.showMessageDialog(null, "Selecciona una asignatura primero");
				    return;
				}//por si da error

				try {
				    Connection con = ConnectionSingleton.getConnection();
				    PreparedStatement pstmt = con.prepareStatement(
				        "SELECT COUNT(*) FROM Matricula WHERE idAsignatura = ?");
				    pstmt.setInt(1, idAsignatura); //para rellenar ?
				    ResultSet rs = pstmt.executeQuery();
				    
				     int cantidadAlumnos = rs.getInt(1);
				     JOptionPane.showMessageDialog(null, "En la asignatura "+nombreAsignatura + " hay "
				     + cantidadAlumnos + " alumnos matriculados.");
				    
				    rs.close();
				    pstmt.close();
				    con.close();
				} catch (SQLException ex) {
				    ex.printStackTrace();
				    JOptionPane.showMessageDialog(null, ex.getMessage());
				}
				
			}
		});
		btnInformeAs.setBounds(598, 372, 215, 42);
		frame.getContentPane().add(btnInformeAs);
		
		JButton btnInformeAl = new JButton("Informe alumnos");
		btnInformeAl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = tableAlumno.getSelectedRow();
				TableModel model = tableAlumno.getModel();
				int idAlumno = Integer.parseInt(model.getValueAt(index, 0).toString());
				String nombreAlumno = model.getValueAt(index, 1).toString();
				// ID NAME AGE CITY
				textFieldIdAl.setText(model.getValueAt(index, 0).toString());
				textFieldNomAl.setText(model.getValueAt(index, 1).toString());
				textFieldEdadAl.setText(model.getValueAt(index, 2).toString());
				
				if (index == -1) {
				    JOptionPane.showMessageDialog(null, "Selecciona una alumno primero");
				    return;
				}

				try {
				    Connection con = ConnectionSingleton.getConnection();
				    PreparedStatement pstmt = con.prepareStatement(
				        "SELECT COUNT(*) FROM Matricula WHERE idAlumno = ?");
				    pstmt.setInt(1, idAlumno);
				    ResultSet rs = pstmt.executeQuery();
				    
				   
				     int cantidadAsignaturas = rs.getInt(1);
				     JOptionPane.showMessageDialog(null, "El alumno "+nombreAlumno+ " está en " + cantidadAsignaturas + " asignaturas matriculadas.");
			
				    
				    rs.close();
				    pstmt.close();
				    con.close();
				} catch (SQLException ex) {
				    ex.printStackTrace();
				    JOptionPane.showMessageDialog(null, ex.getMessage());
				}
				
			}
		});

		btnInformeAl.setBounds(45, 380, 215, 42);
		frame.getContentPane().add(btnInformeAl);
		mostrarTablaAlumno();
		mostrarTablaAsignatura();
		mostrarTablaMatricula();
	}
}
