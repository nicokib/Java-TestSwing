package ui;

import model.Ingredient;
import model.Sandwich;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class SandwichFormDialog extends JDialog {

    public enum ActionMode {
        AJOUTER("Ajouter un sandwich"),
        MODIFIER("Modifier un sandwich"),
        SUPPRIMER("Supprimer un sandwich"),
        VISUALISER("Consulter un sandwich");

        final String titre;
        ActionMode(String titre) { this.titre = titre; }
    }

    private JTextField txtNomFr = new JTextField(20);
    private JTextField txtNomNl = new JTextField(20);
    private JTextField txtPrice = new JTextField(10);
    private DefaultListModel<Ingredient> modelIngredients = new DefaultListModel<>();
    private JList<Ingredient> listIngredients = new JList<>(modelIngredients);

    private Sandwich sandwich;
    private ActionMode mode;
    private boolean okClicked = false;

    public SandwichFormDialog(Frame parent, ActionMode mode, Sandwich sandwich) {
        super(parent, mode.titre, true);
        this.mode = mode;
        this.sandwich = sandwich;

        buildUI();
        loadData();
        applyModeRestrictions();

        setSize(420, 460);
        setLocationRelativeTo(parent);
    }

    private void buildUI() {
        int idxRow = 0;
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 12, 6, 12);

        // Title
        JLabel lblTitre = new JLabel(mode.titre);
        lblTitre.setFont(new Font("SansSerif", Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = idxRow; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(12, 12, 12, 12);
        panel.add(lblTitre, gbc);

        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 12, 5, 12);

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

        // Price
        gbc.gridx = 0; gbc.gridy = ++idxRow; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("Price :"), gbc);

        gbc.gridx = 1; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.WEST;
        panel.add(txtPrice, gbc);

        // Label Liste des ingrédients
        gbc.gridx = 0; gbc.gridy = ++idxRow; gbc.gridwidth = 2;
        gbc.insets = new Insets(12, 12, 4, 12);
        panel.add(new JLabel("Liste des ingrédients :"), gbc);

        // JList des ingrédients
        listIngredients.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setupIngredientsPopup();

        gbc.gridx = 0; gbc.gridy = ++idxRow; gbc.gridwidth = 2;
        gbc.weightx = 1.0; gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 12, 12, 12);
        panel.add(new JScrollPane(listIngredients), gbc);

        // Boutons OK / Cancel
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        JButton btnOk = new JButton("OK");
        JButton btnCancel = new JButton("Cancel");

        btnOk.addActionListener(e -> onOk());
        btnCancel.addActionListener(e -> dispose());

        btnPanel.add(btnOk);
        btnPanel.add(btnCancel);

        gbc.gridx = 0; gbc.gridy = ++idxRow; gbc.gridwidth = 2;
        gbc.weightx = 0; gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(4, 12, 12, 12);
        panel.add(btnPanel, gbc);

        setContentPane(panel);
    }

    private void setupIngredientsPopup() {
        JPopupMenu popup = new JPopupMenu();
        JMenuItem itemAdd = new JMenuItem("Ajouter");
        JMenuItem itemEdit = new JMenuItem("Modifier");
        JMenuItem itemDelete = new JMenuItem("Supprimer");

        popup.add(itemAdd);
        popup.add(itemEdit);
        popup.add(itemDelete);

        itemAdd.addActionListener(e -> addIngredient());
        itemEdit.addActionListener(e -> editSelectedIngredient());
        itemDelete.addActionListener(e -> deleteSelectedIngredient());


        listIngredients.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) { showPopup(e); }
            @Override
            public void mouseReleased(MouseEvent e) { showPopup(e); }

            private void showPopup(MouseEvent e) {
                if (mode == ActionMode.SUPPRIMER) return; // Disabled in Delete mode

                if (e.isPopupTrigger()) {
                    int idx = listIngredients.locationToIndex(e.getPoint());
                    if (idx != -1 && listIngredients.getCellBounds(idx, idx).contains(e.getPoint())) {
                        listIngredients.setSelectedIndex(idx);
                    } else {
                        listIngredients.clearSelection();
                    }
                    boolean selected = !listIngredients.isSelectionEmpty();
                    itemEdit.setEnabled(selected);
                    itemDelete.setEnabled(selected);
                    popup.show(listIngredients, e.getX(), e.getY());
                }
            }
        });
    }

    private void loadData() {
        if (sandwich != null) {
            txtNomFr.setText(sandwich.getNomFr());
            txtNomNl.setText(sandwich.getNomNl());
            txtPrice.setText(String.valueOf(sandwich.getPrice()));
            if(sandwich.getIngredients() != null)
            {
                for (Ingredient ing : sandwich.getIngredients()) {
                    modelIngredients.addElement(ing);
                }
            }
        }
    }

    private void applyModeRestrictions() {
        boolean isEditable = (mode != ActionMode.SUPPRIMER);
        txtNomFr.setEditable(isEditable);
        txtNomNl.setEditable(isEditable);
        txtPrice.setEditable(isEditable);
    }

    private void addIngredient() {
        // En mode AJOUTER, il n'y a pas encore de model.Ingredient existant : on passe null
        IngredientFormDialog dialog = new IngredientFormDialog(null, IngredientFormDialog.ActionMode.AJOUTER, null);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

        // Récupérer le sandwich créé après fermeture (si la méthode existe dans votre dialog)
        Ingredient nouveau = dialog.getIngredient();
        if (nouveau != null) {
            modelIngredients.addElement(nouveau);
        }

    }

    // Link to the "Edit" action
    public void editSelectedIngredient() {

        int index = listIngredients.getSelectedIndex();
        if (index != -1) {
            Ingredient selection = modelIngredients.get(index);
            IngredientFormDialog dialog = new IngredientFormDialog(null, IngredientFormDialog.ActionMode.MODIFIER, selection);
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
            listIngredients.repaint();
        }
    }

    public void deleteSelectedIngredient() {
        int idx = listIngredients.getSelectedIndex();
        if (idx != -1) {
            modelIngredients.remove(idx);
        }
    }

    private void onOk() {
        if (mode == ActionMode.SUPPRIMER) {
            okClicked = true;
            dispose();
            return;
        }

        String fr = txtNomFr.getText().trim();
        String nl = txtNomNl.getText().trim();
        String priceText = txtPrice.getText().trim().replace(",", ".");

        if (fr.isEmpty() || nl.isEmpty() || priceText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tous les champs sont obligatoires.");
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Prix invalide.");
            return;
        }

        List<Ingredient> ings = new ArrayList<>();
        for (int i = 0; i < modelIngredients.size(); i++) {
            ings.add(modelIngredients.get(i));
        }

        if (mode == ActionMode.AJOUTER) {
            sandwich = new Sandwich(fr, nl, price, ings);
        } else if (mode == ActionMode.MODIFIER) {
            sandwich.setNomFr(fr);
            sandwich.setNomNl(nl);
            sandwich.setPrice(price);
            sandwich.setIngredients(ings);
        }

        okClicked = true;
        dispose();
    }

    public boolean isOkClicked() { return okClicked; }
    public Sandwich getSandwich() { return sandwich; }



    public static void main(String[] args) {
        // Applique le style visuel du système d'exploitation
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            // Test 1 : Mode AJOUTER (ingredient est null)
            SandwichFormDialog dialog = new SandwichFormDialog(null, SandwichFormDialog.ActionMode.AJOUTER, null);
            dialog.setVisible(true); // Bloque l'exécution jusqu'à la fermeture du dialogue

            // Vérification après fermeture
            if (dialog.isOkClicked()) {
                System.out.println("Action validée !");
            } else {
                System.out.println("Action annulée.");
            }

            // Termine proprement le programme de test
            System.exit(0);
        });
    }

}