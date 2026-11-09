import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ExempleGridBagLayout extends JFrame {

    // Étape 1 : Mettre les composants en variables d'instance (champs de la classe)
    // pour qu'ils soient accessibles depuis toutes les méthodes de la classe.
    private JTextField txtNom;
    private JComboBox<Person> comboPerson;
    private JComboBox<BreadType> comboBreadType;
    private JRadioButton radOui;
    private JList<String> listSandwiches;

    public ExempleGridBagLayout() {
        int numGridY = 0;
        setTitle("Exemple GridBagLayout Réorganisé");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 450);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // 1. Title
        JLabel lblTitre = new JLabel("Choix du sandwich", JLabel.CENTER);
        lblTitre.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = numGridY; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        panel.add(lblTitre, gbc);

        // 2. COMBOBOX (Person)
        JLabel lblNom = new JLabel("Nom :");
        gbc.gridx = 0; gbc.gridy = ++numGridY; gbc.gridwidth = 1; gbc.weightx = 0.0;
        panel.add(lblNom, gbc);

        List<Person> lstPerson = new ArrayList<>();
        lstPerson.add(new Person(1L,"Dupont", "Jean", Gender.MALE));
        lstPerson.add(new Person(2L, "Durand", "Marie", Gender.FEMALE));

        comboPerson = new JComboBox<Person>(lstPerson.toArray(new Person[0])); // Initialisation de la variable d'instance
        gbc.gridx = 1; gbc.gridy = numGridY; gbc.weightx = 1.0;
        panel.add(comboPerson, gbc);

        // 3. COMBOBOX (Civilité)
        JLabel lblBreadType = new JLabel("Type de pain :");
        gbc.gridx = 0; gbc.gridy = ++numGridY; gbc.weightx = 0.0;
        panel.add(lblBreadType, gbc);

        comboBreadType = new JComboBox<>(BreadType.values()); // Initialisation
        gbc.gridx = 1; gbc.gridy = numGridY; gbc.weightx = 1.0;
        panel.add(comboBreadType, gbc);

        // 4. RADIO BUTTONS (Abonnement)
        JLabel lblCrudity = new JLabel("Crudités :");
        gbc.gridx = 0; gbc.gridy = ++numGridY; gbc.weightx = 0.0;
        panel.add(lblCrudity, gbc);

        JPanel radioPanelCrudity = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        radOui = new JRadioButton("Oui", true); // Initialisation
        JRadioButton radNon = new JRadioButton("Non");
        ButtonGroup group = new ButtonGroup();
        group.add(radOui); group.add(radNon);
        radioPanelCrudity.add(radOui); radioPanelCrudity.add(radNon);

        gbc.gridx = 1; gbc.gridy = numGridY; gbc.weightx = 1.0;
        panel.add(radioPanelCrudity, gbc);

        // 5. JLIST (Intérêts)
        JLabel lblInterets = new JLabel("Intérêts :");
        gbc.gridx = 0; gbc.gridy = ++numGridY; gbc.anchor = GridBagConstraints.NORTHWEST; gbc.weightx = 0.0;
        panel.add(lblInterets, gbc);

        String[] itemsInterets = {"Poulet", "Americain", "Hawai", "Thon", "Végétarien", "Kebab", "Steak", "Merguez"};
        listSandwiches = new JList<>(itemsInterets); // Initialisation
        listSandwiches.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(listSandwiches);
        scrollPane.setPreferredSize(new Dimension(150, 80));

        gbc.gridx = 1; gbc.gridy = numGridY; gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0; gbc.weighty = 1.0;
        panel.add(scrollPane, gbc);

        // 6. BOUTON
        JButton btnValider = new JButton("Valider l'inscription");
        gbc.gridx = 0; gbc.gridy = ++numGridY; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.CENTER; gbc.weighty = 0.0;
        panel.add(btnValider, gbc);

        // L'écouteur d'événement délègue maintenant le travail à notre nouvelle méthode
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                traiterFormulaire();
            }
        });

        add(panel);
    }

    /**
     * Étape 2 : Méthode isolée pour gérer le traitement des données du formulaire
     */
    private void traiterFormulaire() {
        String nom = txtNom.getText().trim();

        if (nom.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez saisir un nom !", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        BreadType breadType = (BreadType) comboBreadType.getSelectedItem();
        String abonnement = radOui.isSelected() ? "Oui" : "Non";
        List<String> interetsSelectionnes = listSandwiches.getSelectedValuesList();
        String sawndwich = interetsSelectionnes.isEmpty() ? "Aucun" : String.join(", ", interetsSelectionnes);

        String message = String.format(
                "Résumé de l'inscription :\n\n" +
                        " Civilité : %s\n" +
                        " Nom : %s\n" +
                        " Statut : %s\n" +
                        " Sawndwich : %s",
                breadType.getDisplayText(), nom, abonnement, sawndwich
        );

        JOptionPane.showMessageDialog(this, message, "Réservation OK", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ExempleGridBagLayout().setVisible(true));
    }
}