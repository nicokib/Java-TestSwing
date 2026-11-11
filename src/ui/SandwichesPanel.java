package ui;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

public class SandwichesPanel extends JPanel {

    public SandwichesPanel(String title) {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        int idxRow = 0;

        // Title
        JLabel lblTitre = new JLabel(title);
        lblTitre.setFont(new Font("SansSerif", Font.BOLD, 12));
        gbc.gridx = 0; gbc.gridy = idxRow; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(12, 12, 12, 12);
        add(lblTitre, gbc);

        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 12, 5, 12);
        
        
        // Données d'exemple pour les listes déroulantes
        String[] sandwichesOptions = {"Jambon-Fromage", "Thon-Mayo", "Poulet Curry", "Américain", "Martino"};
        String[] painOptions = {"Baguette blanche", "Pain gris", "Ciabatta", "Pain complet"};

        // 5. Label "Sandwiches :"
        gbc.gridy = ++idxRow;
        gbc.insets = new Insets(0, 0, 2, 0);
        add(new JLabel("Liste des sandwichs :"), gbc);

        //  TreeView in a JScrollPane
        gbc.gridy = ++idxRow;
        gbc.insets = new Insets(2, 0, 10, 0);
        gbc.weighty = 1.0; // Permet au JTree d'occuper tout l'espace vertical disponible
        gbc.fill = GridBagConstraints.BOTH;

        JTree treeView = createSampleTree();
        JScrollPane treeScrollPane = new JScrollPane(treeView);
        treeScrollPane.setPreferredSize(new Dimension(250, 160));
        add(treeScrollPane, gbc);

        // Réinitialisation des contraintes pour les éléments du bas
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 8. Avec crudités (CheckBox)
        gbc.gridy = ++idxRow;
        gbc.insets = new Insets(2, 0, 8, 0);
        JCheckBox cruditesCheck2 = new JCheckBox("Avec crudités :");
        cruditesCheck2.setHorizontalTextPosition(SwingConstants.LEFT);
        add(cruditesCheck2, gbc);

        // 9. Type de pain (ComboBox)
        gbc.gridy = ++idxRow;
        gbc.insets = new Insets(2, 0, 0, 0);
        add(createInlineComboPanel("Type de pain : ", painOptions), gbc);
    }

    // Méthode pour aligner le label "Type de pain :" à gauche de la ComboBox
    private JPanel createInlineComboPanel(String labelText, String[] items) {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.add(new JLabel(labelText), BorderLayout.WEST);
        panel.add(new JComboBox<>(items), BorderLayout.CENTER);
        return panel;
    }

    // Méthode pour générer une arborescence d'exemple dans le JTree
    private JTree createSampleTree() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Catégories");

        DefaultMutableTreeNode classiques = new DefaultMutableTreeNode("Classiques");
        classiques.add(new DefaultMutableTreeNode("Jambon-Fromage"));
        classiques.add(new DefaultMutableTreeNode("Thon-Mayo"));

        DefaultMutableTreeNode speciaux = new DefaultMutableTreeNode("Spéciaux");
        speciaux.add(new DefaultMutableTreeNode("Américain"));
        speciaux.add(new DefaultMutableTreeNode("Martino"));

        root.add(classiques);
        root.add(speciaux);

        JTree tree = new JTree(root);
        // Déplie tous les nœuds par défaut
        for (int i = 0; i < tree.getRowCount(); i++) {
            tree.expandRow(i);
        }
        return tree;
    }

    // Méthode main pour tester le JPanel directement
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Test ui.SandwichesPanel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new SandwichesPanel("model.Sandwich"));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}