import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Menu_Opciones extends JFrame {
    private JPanel menu;
    private JPanel panel1;
    private JRadioButton verSaldoRadioButton;
    private JRadioButton depositoRadioButton;
    private JRadioButton retiroRadioButton;
    private JRadioButton salirRadioButton;
    private JRadioButton transaccioes;
    private JRadioButton cambiarContra;
    private JRadioButton cambiarContraseñaRadioButton;

    private Connection establecerConexion() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/cajero";
        String usuarioDB = "root";
        String contraseniaDB = "123456";
        return DriverManager.getConnection(url, usuarioDB, contraseniaDB);
    }
    public Menu_Opciones() {
        super("menu");
        setContentPane(menu);
        setSize(500, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        verSaldoRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreUsuarioActual = Bienvenida.NomUsuario;
                Saldo saldoWindow = new Saldo(nombreUsuarioActual);
                saldoWindow.setVisible(true);

                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(menu);
                frame.dispose();


            }
        });
        retiroRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Retiro retirousuario = new Retiro();
                retirousuario.setVisible(true);

                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(menu);
                frame.dispose();


            }
        });
        depositoRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Deposito depositousuario = new Deposito();
                depositousuario.setVisible(true);

                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(menu);
                frame.dispose();

            }
        });
        salirRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int respuesta = JOptionPane.showConfirmDialog(null, "Quiere salir de la app",
                        "Salir", JOptionPane.YES_NO_OPTION);
                try {
                    if (respuesta == JOptionPane.YES_OPTION) {
                        Bienvenida menu = new Bienvenida();
                        menu.setVisible(true);
                        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel1);
                        frame.dispose();

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });


        transaccioes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection conexion = establecerConexion();
                    transferencia transferencia = new transferencia();
                    transferencia.setVisible(true);
                    dispose();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        cambiarContra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection conexion = establecerConexion();
                    cambio_contraseña cambio_contraseña = new cambio_contraseña();
                    cambio_contraseña.setVisible(true);
                    dispose();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
