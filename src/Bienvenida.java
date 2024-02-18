import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Bienvenida extends JFrame {
    private JPanel panel1;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton ingresarButton;
    private JButton salirButton;
    private JButton conexion;
    private JButton resgistro;
    private JButton cambiarcontraeña;
    private JLabel bienvenidoAlBancoElLabel;
    public static String NomUsuario;

    private Connection establecerConexion() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/cajero";
        String usuarioDB = "root";
        String contraseniaDB = "123456";
        return DriverManager.getConnection(url, usuarioDB, contraseniaDB);
    }

    public Bienvenida() {
        super("Bienvenida");
        setContentPane(panel1);
        setSize(500, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Agregado para cerrar la aplicación al cerrar la ventana
        setVisible(true);


        conexion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conexion_base();
            }
        });

        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int respuesta = JOptionPane.showConfirmDialog(null, "¿Quiere salir de la app?",
                        "Salir", JOptionPane.YES_NO_OPTION);
                try {
                    if (respuesta == JOptionPane.YES_OPTION) {
                        dispose();
                        System.exit(0); // Agregado para cerrar la aplicación
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        ingresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        resgistro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection conexion = establecerConexion();
                    Registro registro = new Registro(conexion);
                    registro.setVisible(true);
                    dispose();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    }

    public void conexion_base() {
        try {
            Connection conexion = establecerConexion();
            JOptionPane.showMessageDialog(null, "Conexión establecida con la base de datos.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            conexion.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void login() {
        try (Connection conexion = establecerConexion()) {
            String nombreUsu = textField1.getText().trim();
            String contraseña = new String(passwordField1.getPassword()).trim();

            String sql = "SELECT * FROM usuarios WHERE UserCli=? AND ContraCli=?";
            try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
                pstmt.setString(1, nombreUsu);
                pstmt.setString(2, contraseña);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        NomUsuario = nombreUsu;
                        Menu_Opciones menu = new Menu_Opciones();
                        menu.setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al realizar la consulta: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Bienvenida());
    }
}