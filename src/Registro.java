import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Registro extends JFrame {

    private JPanel panelregistro;
    private JPanel panel1;
    private JTextField textCI;
    private JTextField textNombre;
    private JTextField textUsuario;
    private JTextField textContraseña;
    private JButton crearUsuarioButton;
    private JButton regresar;

    public Registro(Connection conexion) {
        super("Registro");
        setContentPane(panelregistro);
        setSize(500, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        crearUsuarioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertarUsuario(conexion);
                textCI.setText("");
                textNombre.setText("");
                textUsuario.setText("");
                textContraseña.setText("");
            }
        });

        regresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Bienvenida bienvenida = new Bienvenida();
                bienvenida.setVisible(true);
                dispose();
            }
        });
    }

    private void insertarUsuario(Connection conexion) {
        String id = textCI.getText();
        String nombre = textNombre.getText();
        String usuario = textUsuario.getText();
        String contraseña = textContraseña.getText();

        String query = "INSERT INTO usuarios (ID_CLI, NomCli, UserCli, ContraCli, SaldoCli) VALUES (?, ?, ?, ?, 0.00)";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(query)) {
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, nombre);
            preparedStatement.setString(3, usuario);
            preparedStatement.setString(4, contraseña);

            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Usuario registrado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al registrar usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
