import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.SecureRandom;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class cambio_contraseña extends JFrame {
    private JPanel panel1;
    private JTextField textField1;
    private JTextField textField2;
    private JButton cambiarButton;
    private JButton menuButton;

    private Connection establecerConexion() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/cajero";
        String usuarioDB = "root";
        String contraseniaDB = "123456";
        return DriverManager.getConnection(url, usuarioDB, contraseniaDB);
    }

    public cambio_contraseña() {
        super("Cambio de contraseña");
        setContentPane(panel1);
        setSize(500, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Menu_Opciones menu = new Menu_Opciones();
                menu.setVisible(true);
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel1);
                frame.dispose();
            }
        });

       /*cambiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String contra_actual = textField1.getText();
                String contra_nueva = textField2.getText();

                try (Connection conexion = establecerConexion()) {
                    String sql = "UPDATE usuarios SET ContraCli = ? WHERE UserCli = ? AND ContraCli = ?";
                    try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
                        pstmt.setString(1, contra_nueva);
                        pstmt.setString(2, Bienvenida.NomUsuario); // Suponiendo que NomUsuario es el nombre de usuario actual
                        pstmt.setString(3, contra_actual);

                        int filasAfectadas = pstmt.executeUpdate();

                        if (filasAfectadas > 0) {
                            JOptionPane.showMessageDialog(null, "Contraseña actualizada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Contraseña actual incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al actualizar la contraseña: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });*/
        cambiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String contra_actual = textField1.getText();
                String contra_nueva = textField2.getText();

                // Verificación de cambio de contraseña
                int respuesta = JOptionPane.showConfirmDialog(null,
                        "¿Desea cambiar la contraseña?\nContraseña actual: " + contra_actual + "\nNueva contraseña: " + contra_nueva,
                        "Confirmar cambio de contraseña", JOptionPane.YES_NO_OPTION);

                if (respuesta == JOptionPane.YES_OPTION) {
                    // Guardar transacción en la tabla 'transacciones'
                    try (Connection conexion = establecerConexion()) {
                        guardarTransaccion(conexion, "Cambio de Contraseña", contra_actual, contra_nueva);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    // Realizar el cambio de contraseña
                    try (Connection conexion = establecerConexion()) {
                        String sql = "UPDATE usuarios SET ContraCli = ? WHERE UserCli = ? AND ContraCli = ?";
                        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
                            pstmt.setString(1, contra_nueva);
                            pstmt.setString(2, Bienvenida.NomUsuario); // Suponiendo que NomUsuario es el nombre de usuario actual
                            pstmt.setString(3, contra_actual);

                            int filasAfectadas = pstmt.executeUpdate();

                            if (filasAfectadas > 0) {
                                JOptionPane.showMessageDialog(null, "Contraseña actualizada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(null, "Contraseña actual incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error al actualizar la contraseña: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }
    private void guardarTransaccion(Connection conexion, String tipoTransaccion, String contraActual, String contraNueva) throws SQLException {
        String idTransaccion = generarID();
        String fechaTransaccion = obtenerFechaActual();
        String horaTransaccion = obtenerHoraActual();

        String sql = "INSERT INTO transacciones(ID_Transac, Tipo_Transac, Fecha_Transac, Hora_Transac, ID_CLI_fk) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, idTransaccion);
            pstmt.setString(2, tipoTransaccion);
            pstmt.setString(3, fechaTransaccion);
            pstmt.setString(4, horaTransaccion);
            pstmt.setString(5, Bienvenida.NomUsuario); // Suponiendo que NomUsuario es el nombre de usuario actual

            pstmt.executeUpdate();
        }
    }
        private static final String CARACTERES_PERMITIDOS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        private static final int LONGITUD_ID = 5;

        public static String generarID() {
            SecureRandom random = new SecureRandom();
            StringBuilder sb = new StringBuilder(LONGITUD_ID);

            for (int i = 0; i < LONGITUD_ID; i++) {
                int index = random.nextInt(CARACTERES_PERMITIDOS.length());
                char caracter = CARACTERES_PERMITIDOS.charAt(index);
                sb.append(caracter);
            }

            return sb.toString();
        }
    private String obtenerFechaActual() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private String obtenerHoraActual() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        return timeFormat.format(date);
    }
}






