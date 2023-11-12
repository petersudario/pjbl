import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import com.opencsv.CSVWriter;

public class VisitanteForm extends JFrame implements Serializable {

    private JTextField nomeField;
    private JTextField idadeField;
    private JTextField saldoField;
    private JList<String> visitanteList;

    private JTextField nomeJaulaField;
    private JTextField espacoJaulaField;
    private JList<String> jaulaList;
    private ArrayList<Jaula> listaDeJaulas;

    

    public VisitanteForm() {
        nomeField = new JTextField(20);
        idadeField = new JTextField(20);
        saldoField = new JTextField(20);
        visitanteList = new JList<>(new DefaultListModel<>());

        nomeJaulaField = new JTextField(20);
        espacoJaulaField = new JTextField(20);
        jaulaList = new JList<>(new DefaultListModel<>());
        listaDeJaulas = new ArrayList<>();

        JButton addJaulaButton = new JButton("Add Jaula");
        addJaulaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Your logic to add a Jaula
                addJaula();
            }
        });

        JPanel jaulaPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcJaula = new GridBagConstraints();
        gbcJaula.insets = new Insets(5, 5, 5, 5);

        jaulaPanel.add(new JLabel("Nome da Jaula: "), gbcJaula);
        gbcJaula.gridx = 1;
        jaulaPanel.add(nomeJaulaField, gbcJaula);
        gbcJaula.gridx = 0;
        gbcJaula.gridy = 1;
        jaulaPanel.add(new JLabel("Espaco da Jaula: "), gbcJaula);
        gbcJaula.gridx = 1;
        jaulaPanel.add(espacoJaulaField, gbcJaula);

        // Add button to add Jaula
        gbcJaula.gridx = 0;
        gbcJaula.gridy = 2;
        gbcJaula.gridwidth = 2;
        jaulaPanel.add(addJaulaButton, gbcJaula);



        // Load data from the ".p" file automatically when the application starts
        loadVisitanteData();

        JButton saveButton = new JButton("Save Visitante");
        JButton loadButton = new JButton("Load Visitante");

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Pessoa visitante = new Visitante();
                visitante.setNome(nomeField.getText());
                visitante.setIdade(Integer.parseInt(idadeField.getText()));
                visitante.setSaldoInicial(Double.parseDouble(saldoField.getText()));

                DefaultListModel<String> model = (DefaultListModel<String>) visitanteList.getModel();
                model.addElement(visitante.toString());

                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("visitante.p"))) {
                    oos.writeObject(model);
                    JOptionPane.showMessageDialog(null, "Visitante saved to visitante.p");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Your existing load button logic
                loadVisitanteData(); // Load Visitante data when the button is clicked
            }
        });



        JPanel visitantesPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        visitantesPanel.add(new JLabel("Nome: "), gbc);
        gbc.gridx = 1;
        visitantesPanel.add(nomeField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        visitantesPanel.add(new JLabel("Idade: "), gbc);
        gbc.gridx = 1;
        visitantesPanel.add(idadeField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        visitantesPanel.add(new JLabel("Saldo Inicial: "), gbc);
        gbc.gridx = 1;
        visitantesPanel.add(saldoField, gbc);
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        visitantesPanel.add(saveButton, gbc);

        gbcJaula.gridx = 0;
        gbcJaula.gridy = 2;
        gbcJaula.gridwidth = 2;
        jaulaPanel.add(addJaulaButton, gbcJaula);

        JPanel logPanel = new JPanel(new BorderLayout());
        logPanel.add(loadButton, BorderLayout.NORTH);
        logPanel.add(new JScrollPane(visitanteList), BorderLayout.CENTER);

        this.add(visitantesPanel, BorderLayout.WEST);
        this.add(logPanel, BorderLayout.CENTER);
        this.add(jaulaPanel, BorderLayout.EAST);


        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Function to load visitante data from ".p" file
    private void loadVisitanteData() {
        DefaultListModel<String> model;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("visitante.p"))) {
            model = (DefaultListModel<String>) ois.readObject();
            JOptionPane.showMessageDialog(null, "Visitantes Carregados");
        } catch (IOException | ClassNotFoundException ex) {
            model = new DefaultListModel<>();
        }
        visitanteList.setModel(model);
    }

    private void addJaula() {
        Jaula jaula = new Jaula(nomeJaulaField.getText(), Double.parseDouble(espacoJaulaField.getText()));
        listaDeJaulas.add(jaula);

        // Append Jaula data to CSV
        try (CSVWriter writer = new CSVWriter(new FileWriter("jaula.csv", true))) {
            if (new File("jaula.csv").length() == 0) {
                // Write header only if the file is empty
                String[] header = {"Nome", "Espaco"};
                writer.writeNext(header);
            }

            // Write Jaula attributes
            String[] data = {jaula.getNome(), String.valueOf(jaula.getEspaco())};
            writer.writeNext(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}