package archivos_registro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalTime;
import java.util.List;

public class Main {
    private FileManager fileManager = new FileManager();
    
    // Declarar las áreas de texto como variables de instancia
    private JTextArea textArea1;
    private JTextArea textArea2;

    public static void main(String[] args) {
        new Main().createAndShowUI();
    }

    public void createAndShowUI() {
        JFrame frame = new JFrame("Gestor de Archivos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Panel principal con diseño BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // PANEL SUPERIOR
        JPanel topPanel = new JPanel(new BorderLayout());
        JTextField textBar = new JTextField("Selecciona una carpeta...");
        textBar.setEditable(false); // Deshabilitar edición en el campo de texto de la ruta
        JButton browseButton = new JButton("...");
        topPanel.add(textBar, BorderLayout.CENTER);
        topPanel.add(browseButton, BorderLayout.EAST);
        
        // Panel con espacio para botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JButton button1 = new JButton("Eliminar _1");
        JButton button2 = new JButton("Eliminar espacios");
        JButton button3 = new JButton("Cambiar mayusculas");
        JButton button4 = new JButton("Buscar y reemplazar");
        buttonPanel.add(button1);
        buttonPanel.add(button2);
        buttonPanel.add(button3);
        buttonPanel.add(button4);

        // Crear áreas de texto
        textArea1 = new JTextArea();
        textArea2 = new JTextArea();
        
        // Deshabilitar la escritura en las áreas de texto
        textArea1.setEditable(false);
        textArea2.setEditable(false);
        
        textArea2.setLineWrap(false);

        JScrollPane scrollPane1 = new JScrollPane(textArea1);
        JScrollPane scrollPane2 = new JScrollPane(textArea2);

        // Crear el divisor horizontal entre las áreas de texto
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane1, scrollPane2);
        splitPane.setDividerLocation(0.6); // Dividir el espacio horizontalmente 50/50
        splitPane.setResizeWeight(0.6);

        // Crear el contenedor para el área de texto con el ajuste de tamaño
        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        textPanel.add(splitPane, BorderLayout.CENTER);

        // Combinar todo en el panel principal con un ajuste para el tamaño
        mainPanel.add(topPanel, BorderLayout.NORTH); // Barra superior con la ruta
        mainPanel.add(textPanel, BorderLayout.CENTER); // Áreas de texto
        mainPanel.add(buttonPanel, BorderLayout.SOUTH); // Fila con los botones

        // Configurar la funcionalidad del botón de búsqueda
        browseButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int option = chooser.showOpenDialog(frame);
            if (option == JFileChooser.APPROVE_OPTION) {
                File selectedDirectory = chooser.getSelectedFile();
                textBar.setText(selectedDirectory.getAbsolutePath());
                fileManager.setDirectory(selectedDirectory);

                List<String> fileNames = fileManager.getFileNames();
                
                if (fileNames.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No se encontraron archivos en la carpeta seleccionada.",
                            "Información", JOptionPane.INFORMATION_MESSAGE);
                    textArea1.setText("");
                    textArea2.setText("");
                } else {
                    textArea1.setText(String.join("\n", fileNames));
                    textArea2.setText("");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "No se seleccionó ninguna carpeta.",
                        "Información", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        button1.addActionListener(e -> {
        	int count = fileManager.eliminar_1();
        	
        	System.out.println(count);
        	
        	LocalTime ahora = LocalTime.now();
        	
        	String horaActual = ahora.getHour() + ":" + String.format("%02d", ahora.getMinute());

        	List<String> fileNames = fileManager.getFileNames();
        	
        	if (fileNames.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No se encontraron archivos en la carpeta seleccionada.",
                        "Información", JOptionPane.INFORMATION_MESSAGE);
                textArea1.setText("");
                textArea2.setText("");
            } else {
                textArea1.setText(String.join("\n", fileNames));
                textArea2.setText("[" + horaActual + "] Se han modificado " + count + " archivos");
            }
        });

        frame.setContentPane(mainPanel);
        frame.setVisible(true);
    }
}

