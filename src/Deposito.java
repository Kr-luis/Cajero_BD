import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Deposito extends JFrame {

    private JPanel paneldeposito;
    private JTextField textField1;
    private JButton a1Button;
    private JButton a2Button;
    private JButton a3Button;
    private JButton a4Button;
    private JButton a5Button;
    private JButton a6Button;
    private JButton ENTERButton;
    private JButton a8Button;
    private JButton a9Button;
    private JButton a7Button;
    private JButton a0Button;
    private JButton menuButton;
    private JButton borrarButton;
    private Saldo saldo;

    public Deposito() {
        super("Deposito");
        this.saldo = saldo;

        setContentPane(paneldeposito);
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);

        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Menu_Opciones menu = new Menu_Opciones();
                menu.setVisible(true);
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(paneldeposito);
                frame.dispose();
            }
        });
        a1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String consulta = textField1.getText() + "1";
                textField1.setText(consulta);
            }
        });

        a2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String consulta = textField1.getText() + "2";
                textField1.setText(consulta);
            }
        });
        a3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String consulta = textField1.getText() + "3";
                textField1.setText(consulta);
            }
        });
        a4Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String consulta = textField1.getText() + "4";
                textField1.setText(consulta);
            }
        });
        a5Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String consulta = textField1.getText() + "5";
                textField1.setText(consulta);
            }
        });
        a6Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String consulta = textField1.getText() + "6";
                textField1.setText(consulta);
            }
        });
        a7Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String consulta = textField1.getText() + "7";
                textField1.setText(consulta);
            }
        });
        a8Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String consulta = textField1.getText() + "8";
                textField1.setText(consulta);
            }
        });
        a9Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String consulta = textField1.getText() + "9";
                textField1.setText(consulta);
            }
        });
        a0Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String consulta = textField1.getText() + "0";
                textField1.setText(consulta);
            }
        });
        ENTERButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int saldoactual = Saldo.saldobanco;
                    String deposito1 = textField1.getText();
                    int deposito = Integer.parseInt(deposito1);

                    int nuevoSaldo = saldoactual + deposito;

                    actualizarSaldoEnBD(nuevoSaldo);

                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Quiere imprimir su factura?",
                            "Imprimir", JOptionPane.YES_NO_OPTION);
                    if (respuesta == JOptionPane.YES_OPTION) {
                        JOptionPane.showMessageDialog(null, "Su saldo anterior era: " + saldoactual + "\nSu saldo actual es " + nuevoSaldo + "\nHa hecho un depósito de : " + deposito + "\nGracias por usar nuestro servicio", "Factura", JOptionPane.WARNING_MESSAGE);
                    }

                    // Limpiar el textField
                    textField1.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor, introduzca un número válido para el depósito", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al actualizar el saldo en la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField1.setText("");
            }
        });


    }

    private void actualizarSaldoEnBD(int nuevoSaldo) throws SQLException {
        try (Connection conexion = establecerConexion()) {
            String nombreUsu = Bienvenida.NomUsuario;
            String tipoTransaccion = "Depósito";
            guardarTransaccion(conexion, tipoTransaccion);
            String sql = "UPDATE usuarios SET SaldoCli = ? WHERE UserCli = ?";
            try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
                pstmt.setInt(1, nuevoSaldo);
                pstmt.setString(2, nombreUsu);
                int filasActualizadas = pstmt.executeUpdate();
                if (filasActualizadas != 1) {
                    throw new SQLException("No se pudo actualizar el saldo en la base de datos.");
                }
            }
        }
    }

    private Connection establecerConexion() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/cajero";
        String usuarioDB = "root";
        String contraseniaDB = "123456";
        return DriverManager.getConnection(url, usuarioDB, contraseniaDB);
    }
    private void guardarTransaccion(Connection conexion, String tipoTransaccion) throws SQLException {
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
