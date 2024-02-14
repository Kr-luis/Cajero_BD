import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Retiro extends JFrame{
    private JPanel panel1;
    private JPanel panelretiro;
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



    public Retiro(){
        super("Retiro");

        setContentPane(panelretiro);
        setSize(500,500);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Menu_Opciones menu = new Menu_Opciones();
                menu.setVisible(true);
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panelretiro);
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
        ENTERButton.addActionListener (new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int saldoactual = Saldo.saldobanco;
                String retiro1 = textField1.getText();
                int retiro = Integer.parseInt(retiro1);
                if(retiro > saldoactual){
                    JOptionPane.showMessageDialog(null, "No tiene saldo suficiente ", "Factura", JOptionPane.WARNING_MESSAGE);
                    textField1.setText("");
                }
                else {
                    Saldo.saldobanco = saldoactual - retiro;
                    textField1.setText("");
                    int respuesta = JOptionPane.showConfirmDialog(null, "Quiere imprimir su factura",
                            "Imprimir", JOptionPane.YES_NO_OPTION);
                    try {
                        if (respuesta == JOptionPane.YES_OPTION) {
                            JOptionPane.showMessageDialog(null, "Su saldo anterior era: " + saldoactual + "\nSu saldo actual es " + Saldo.saldobanco + "\nA echo un retiro de : " + retiro + "\nGracias por usar nuestro servicio", "Factura", JOptionPane.WARNING_MESSAGE);


                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
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

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Retiro();
            }

        });
    }
}
