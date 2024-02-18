import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Saldo extends JFrame {
    private JPanel panel1;
    private JPanel panelsaldo;
    private JTextField saldotexto;
    private JButton menuButton;
    private JLabel Saldo;
    private JLabel lblsaldo;
    public static int saldobanco;

    private Connection establecerConexion() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/cajero";
        String usuarioDB = "root";
        String contraseniaDB = "123456";
        return DriverManager.getConnection(url, usuarioDB, contraseniaDB);
    }
    public Saldo(String usuario) {
        super("Saldo");
        setContentPane(panelsaldo);
        setSize(500, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        obtenerSaldo(usuario); // Obtener el saldo del usuario actual
        lbltext(); // Actualizar la etiqueta de saldo
        lblsaldo.setText(String.valueOf(saldobanco)); // Actualizar la etiqueta de saldo inicialmente

        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Menu_Opciones menu = new Menu_Opciones();
                menu.setVisible(true);
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panelsaldo);
                frame.dispose();
            }
        });

    }
    private void lbltext(){
        String saldo = String.valueOf(saldobanco);
        lblsaldo.setText(saldo);
    }

    private void obtenerSaldo(String usuario) {
        try (Connection conexion = establecerConexion()) {
            String sql = "SELECT SaldoCli FROM usuarios WHERE UserCli=?";
            try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
                pstmt.setString(1, usuario);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        int saldo = rs.getInt("SaldoCli");
                        saldobanco = saldo; // actualizar el saldo en la clase Saldo
                        lbltext(); // actualizar la etiqueta de saldo en la interfaz
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al obtener el saldo", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al realizar la consulta: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}

