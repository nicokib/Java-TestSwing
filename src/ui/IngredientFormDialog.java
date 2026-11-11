package ui;

import model.Ingredient;

import javax.swing.*;
import java.awt.*;

public class IngredientFormDialog extends JDialog {

    public enum ActionMode {
        AJOUTER("Ajouter un ingrédient"),
        MODIFIER("Modifier un ingrédient"),
        SUPPRIMER("Supprimer un ingrédient");

        final String titre;
        ActionMode(String titre) { this.titre = titre; }
    }

    private JTextField txtId = new JTextField(10);
    private JTextField txtNomFr = new JTextField(20);
    private JTextField txtNomNl = new JTextField(20);

    private Ingredient ingredient;
    private ActionMode mode;
    private boolean okClicked = false;

    public IngredientFormDialog(Frame parent, ActionMode mode, Ingredient ingredient) {
        super(parent, mode.titre, true);
        this.mode = mode;
        this.ingredient = ingredient;

        buildUI();
        loadData();
        applyModeRestrictions();

        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    private void buildUI() {
        int idxRow = 0;
        // Desgin the screen with labels and text fields for ingredient properties
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 12, 6, 12);

        // Title (Row 0)
        JLabel lblTitre = new JLabel(mode.titre);
        lblTitre.setFont(new Font("SansSerif", Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = idxRow; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(12, 12, 14, 12);
        panel.add(lblTitre, gbc);

        gbc.gridwidth = 1;
        gbc.insets = new Insets(6, 12, 6, 12);

        // Nom en français
        gbc.gridx = 0; gbc.gridy = ++idxRow; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("Nom en français :"), gbc);

        gbc.gridx = 1; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(txtNomFr, gbc);

        // Nom en néerlandais
        gbc.gridx = 0; gbc.gridy = ++idxRow; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("Nom en néerlandais :"), gbc);

        gbc.gridx = 1; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(txtNomNl, gbc);

        // Buttons OK / Cancel
        // create a specific panel for the button (to align the button at the right)
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        JButton btnOk = new JButton("OK");
        JButton btnCancel = new JButton("Cancel");

        btnOk.addActionListener(e -> onOk());
        btnCancel.addActionListener(e -> dispose());

        btnPanel.add(btnOk);
        btnPanel.add(btnCancel);

        gbc.gridx = 0; gbc.gridy = ++idxRow; gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(16, 12, 12, 12);
        panel.add(btnPanel, gbc);

        setContentPane(panel);
    }

    private void loadData() {
        if (ingredient != null) {
            txtNomFr.setText(ingredient.getNomFr());
            txtNomNl.setText(ingredient.getNomNl());
        }
    }

    private void applyModeRestrictions() {
        // Lock text fields in Delete mode
        boolean isEditable = (mode != ActionMode.SUPPRIMER);
        txtNomFr.setEditable(isEditable);
        txtNomNl.setEditable(isEditable);
    }

    private void onOk() {
        if (mode == ActionMode.SUPPRIMER) {
            okClicked = true;
            dispose();
            return;
        }

        String nameFr = txtNomFr.getText().trim();
        String nameNl = txtNomNl.getText().trim();

        if (nameFr.isEmpty() || nameNl.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tous les champs sont obligatoires.");
            return;
        }


        if (mode == ActionMode.AJOUTER) {
            ingredient = new Ingredient(null, nameFr, nameNl);
        } else if (mode == ActionMode.MODIFIER) {
            ingredient.setNomFr(nameFr);
            ingredient.setNomNl(nameNl);
        }

        okClicked = true;
        dispose();
    }

    public Ingredient getIngredient() { return ingredient; }

    public boolean isOkClicked() { return okClicked; }



    public static void main(String[] args) {
        // Applique le style visuel du système d'exploitation
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            // Test 1 : Mode AJOUTER (ingredient est null)
            IngredientFormDialog dialog = new IngredientFormDialog(null, ActionMode.AJOUTER, null);
            dialog.setVisible(true); // Bloque l'exécution jusqu'à la fermeture du dialogue

            // Vérification après fermeture
            if (dialog.okClicked) {
                Ingredient resultat = dialog.getIngredient();
                System.out.println("Action validée !");
                System.out.println("FR : " + resultat.getNomFr() + " | NL : " + resultat.getNomNl());
            } else {
                System.out.println("Action annulée.");
            }

            // Termine proprement le programme de test
            System.exit(0);
        });
    }
}
