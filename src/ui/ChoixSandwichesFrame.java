package ui;

import utils.CurrentUser;

import javax.swing.*;
import java.awt.*;

public class ChoixSandwichesFrame extends JFrame {

    public ChoixSandwichesFrame() {

        /*if(CurrentUser.getUser() == null)
        {
            JOptionPane.showMessageDialog(this, "User is not identified", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            System.exit(1);
        }*/

        setTitle("Commande de sandwiches");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panneau principal avec GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        int row = 0;

        // 1. Titre principal (sur 2 colonnes)
        JLabel titleLabel = new JLabel("Choisi ton ou tes sandwiches", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2; // S'étale sur les 2 colonnes (Panel1 et Panel2)
        gbc.insets = new Insets(0, 0, 15, 0);
        mainPanel.add(titleLabel, gbc);

        // 2. Champ Name (sur 2 colonnes)
        gbc.gridy = row++;
        gbc.insets = new Insets(5, 0, 2, 0);
        mainPanel.add(new JLabel("Name :"), gbc);

        gbc.gridy = row++;
        gbc.insets = new Insets(0, 0, 10, 0);
        JLabel nameField = new JLabel("");
        mainPanel.add(nameField, gbc);

        // 3. Champ Cours (sur 2 colonnes)
        gbc.gridy = row++;
        gbc.insets = new Insets(5, 0, 2, 0);
        mainPanel.add(new JLabel("Cours :"), gbc);

        gbc.gridy = row++;
        gbc.insets = new Insets(0, 0, 15, 0);
        JLabel coursField = new JLabel("");
        mainPanel.add(coursField, gbc);

        // 4. Panel1 et Panel2 côte à côte
        gbc.gridwidth = 1; // Retour à 1 colonne par composant
        gbc.gridy = row++;
        gbc.weighty = 1.0; // Permet aux panneaux centraux de s'étirer en hauteur
        gbc.fill = GridBagConstraints.BOTH;

        // Panel 1 (Gauche)
        gbc.gridx = 0;
        gbc.insets = new Insets(0, 0, 15, 10); // Marge à droite pour séparer les deux panneaux
        SandwichesPanel panel1 = new SandwichesPanel("model.Sandwich 1");
        mainPanel.add(panel1, gbc);

        // Panel 2 (Droite)
        gbc.gridx = 1;
        gbc.insets = new Insets(0, 10, 15, 0); // Marge à gauche
        SandwichesPanel panel2 = new SandwichesPanel("model.Sandwich 2 (Optionnel)");
        mainPanel.add(panel2, gbc);

        // Réinitialisation pour les éléments du bas (sur 2 colonnes)
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.weighty = 0.3; // Légère élasticité pour les remarques
        gbc.fill = GridBagConstraints.BOTH;

        // 5. Zone Remarks
        gbc.gridy = row++;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 2, 0);
        mainPanel.add(new JLabel("Remarks :"), gbc);

        gbc.gridy = row++;
        gbc.weighty = 0.3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 15, 0);
        JTextArea remarksArea = new JTextArea(4, 20);
        remarksArea.setLineWrap(true);
        remarksArea.setWrapStyleWord(true);
        mainPanel.add(new JScrollPane(remarksArea), gbc);

        // 6. Boutons Valider et Annuler
        gbc.gridy = row++;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 0, 0);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        JButton validerBtn = new JButton("Valider");
        JButton annulerBtn = new JButton("Annuler");

        validerBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Commande enregistrée !"));
        annulerBtn.addActionListener(e -> dispose());

        buttonPanel.add(validerBtn);
        buttonPanel.add(annulerBtn);
        mainPanel.add(buttonPanel, gbc);

        add(new JScrollPane(mainPanel));
        pack();
        setMinimumSize(new Dimension(650, 750));
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new ChoixSandwichesFrame().setVisible(true));
    }
}