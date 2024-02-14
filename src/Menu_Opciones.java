import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu_Opciones extends JFrame {
    private JPanel menu;
    private JPanel panel1;
    private JRadioButton verSaldoRadioButton;
    private JRadioButton depositoRadioButton;
    private JRadioButton retiroRadioButton;
    private JRadioButton salirRadioButton;
    private JRadioButton cambiarContraseñaRadioButton;

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
                Saldo saldousuario = new Saldo();
                saldousuario.setVisible(true);
                dispose();

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
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(menu);
                int respuesta = JOptionPane.showConfirmDialog(null, "Quiere salir de la app",
                        "Salir", JOptionPane.YES_NO_OPTION);
                try {
                    if (respuesta == JOptionPane.YES_OPTION) {
                        dispose();

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });

        cambiarContraseñaRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambio_contraseña cambio_contraseña = new cambio_contraseña();
                cambio_contraseña.setVisible(true);
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel1);
                frame.dispose();
            }
        });
    }
}
