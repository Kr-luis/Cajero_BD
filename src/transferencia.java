/*import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class transferencia extends JFrame {

    private JPanel panel1;
    private JPanel paneltransferencia;
    private JButton buttonMostrar;
    private JButton menu;
    private JTextPane historial;
    private JButton descargarButton;

    private Connection establecerConexion() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/cajero";
        String usuarioDB = "root";
        String contraseniaDB = "123456";
        return DriverManager.getConnection(url, usuarioDB, contraseniaDB);
    }
    public transferencia(){
        super("Transferencia");
        setContentPane(paneltransferencia);
        setSize(500, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        descargarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        buttonMostrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    System.out.println("Botón 'Mostrar' presionado");
                    Connection conexion = establecerConexion();
                    String nombreUsuarioActual = Bienvenida.NomUsuario;
                    String query = "SELECT * FROM transacciones WHERE ID_CLI_fk = ?";

                    try (PreparedStatement preparedStatement = conexion.prepareStatement(query)) {
                        preparedStatement.setString(1, nombreUsuarioActual);

                        try (ResultSet resultSet = preparedStatement.executeQuery()) {
                            StringBuilder resultText = new StringBuilder();

                            while (resultSet.next()) {
                                // Obtener los valores de la fila
                                String idTransac = resultSet.getString("ID_Transac");
                                String tipoTransac = resultSet.getString("Tipo_Transac");
                                String fechaTransac = resultSet.getString("Fecha_Transac");
                                String horaTransac = resultSet.getString("Hora_Transac");

                                // Construir el texto a mostrar en el textArea
                                resultText.append("ID Transacción: ").append(idTransac).append("\n");
                                resultText.append("Tipo Transacción: ").append(tipoTransac).append("\n");
                                resultText.append("Fecha Transacción: ").append(fechaTransac).append("\n");
                                resultText.append("Hora Transacción: ").append(horaTransac).append("\n\n");
                            }

                            // Mostrar el resultado en el textArea
                            historial.setText(resultText.toString());
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al mostrar la información: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Menu_Opciones menu = new Menu_Opciones();
                menu.setVisible(true);
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(paneltransferencia);
                frame.dispose();
            }
        });
    }

}*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class transferencia extends JFrame {

    private JPanel paneltransferencia;
    private JPanel panel1;
    private JButton buttonMostrar;
    private JButton menu;
    private JTextPane historial;
    private JScrollBar scrollBar1;
    private JButton descargarButton;
    private Throwable ex;

    public transferencia() {
        super("Transferencia");
        setContentPane(paneltransferencia);
        setSize(500, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        descargarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Obtener el contenido del JTextPane
                    String contenido = historial.getText();

                    // Obtener el directorio "Descargas" del usuario
                    String directorioDescargas = System.getProperty("user.home") + "/Downloads/";

                    // Crear un objeto FileWriter para escribir en un archivo en la carpeta de descargas
                    FileWriter fileWriter = new FileWriter(directorioDescargas + "historial.txt");

                    // Crear un BufferedWriter para escribir de manera eficiente
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                    // Escribir el contenido en el archivo
                    bufferedWriter.write(contenido);

                    // Cerrar los flujos
                    bufferedWriter.close();
                    fileWriter.close();

                    // Puedes mostrar un mensaje de éxito si lo deseas
                    JOptionPane.showMessageDialog(null, "Historial descargado exitosamente en la carpeta Descargas");

                } catch (IOException ex) {
                    ex.printStackTrace();  // Imprimir detalles de la excepción en la consola
                    JOptionPane.showMessageDialog(null, "Error al descargar el historial: " + ex.getMessage());
                } catch (Exception ex) {
                    ex.printStackTrace();  // Imprimir detalles de la excepción en la consola
                    JOptionPane.showMessageDialog(null, "Error inesperado al descargar el historial: " + ex.getMessage());
                }
            }
        });




        buttonMostrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarInformacion();
            }
        });

        menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirMenuOpciones();
            }
        });

    }

    private void mostrarInformacion() {
        try {
            System.out.println("Botón 'Mostrar' presionado");
            Connection conexion = establecerConexion();
            String nombreUsuarioActual = Bienvenida.NomUsuario;
            String query = "SELECT * FROM transacciones WHERE ID_CLI_fk = ?";

            try (PreparedStatement preparedStatement = conexion.prepareStatement(query)) {
                preparedStatement.setString(1, nombreUsuarioActual);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    StringBuilder resultText = new StringBuilder();

                    while (resultSet.next()) {
                        String idTransac = resultSet.getString("ID_Transac");
                        String tipoTransac = resultSet.getString("Tipo_Transac");
                        String fechaTransac = resultSet.getString("Fecha_Transac");
                        String horaTransac = resultSet.getString("Hora_Transac");

                        resultText.append("ID Transacción: ").append(idTransac).append("\n");
                        resultText.append("Tipo Transacción: ").append(tipoTransac).append("\n");
                        resultText.append("Fecha Transacción: ").append(fechaTransac).append("\n");
                        resultText.append("Hora Transacción: ").append(horaTransac).append("\n\n");
                    }

                    historial.setText(resultText.toString());
                }
            }
        } catch (SQLException ex) {
            mostrarError("Error al conectar con la base de datos: " + ex.getMessage());
        } catch (Exception ex) {
            mostrarError("Error al mostrar la información: " + ex.getMessage());
        }
    }

    private Connection establecerConexion() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/cajero";
        String usuarioDB = "root";
        String contraseniaDB = "123456";
        return DriverManager.getConnection(url, usuarioDB, contraseniaDB);
    }

    private void abrirMenuOpciones() {
        Menu_Opciones menu = new Menu_Opciones();
        menu.setVisible(true);
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(paneltransferencia);
        frame.dispose();
    }

    private void mostrarError(String mensaje) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

}


