package ui;

import model.Sandwich;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class GestionSandwiches extends JFrame {

    private JList<Sandwich> listSandwiches;
    private DefaultListModel<Sandwich> modelSandwich;

    public GestionSandwiches() {
        super("Liste des Sandwichs");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 350);

        // Load initial data from SandwichRepository
        List<Sandwich> listeInitiale = new ArrayList<>();
        listeInitiale.add(new Sandwich("Poulet", "Kip", 4.50, null));
        listeInitiale.add(new Sandwich("Américain", "Americain", 5.00, null));
        listeInitiale.add(new Sandwich("Hawaï", "Hawai", 5.50, null));
        listeInitiale.add(new Sandwich("Thon", "Thon", 4.20, null));
        listeInitiale.add(new Sandwich("Végétarien", "Veggie", 4.00, null));

        // Fill the DefaultListModel<model.Sandwich>
        modelSandwich = new DefaultListModel<>();
        for (Sandwich s : listeInitiale) {
            modelSandwich.addElement(s);
        }


        listSandwiches = new JList<>(modelSandwich);
        // We can select only one sandwich
        listSandwiches.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // define Propup
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem addItem = new JMenuItem("Ajouter");
        JMenuItem editItem = new JMenuItem("Modifier");
        JMenuItem deleteItem = new JMenuItem("Supprimer");
        JMenuItem viewItem = new JMenuItem("Visualiser");

        popupMenu.add(addItem);
        popupMenu.addSeparator();
        popupMenu.add(editItem);
        popupMenu.add(deleteItem);
        popupMenu.add(viewItem);

        // Listener for each popup action
        addItem.addActionListener(e -> addSandwich());

        editItem.addActionListener(e -> editSelectedSandwich());

        deleteItem.addActionListener(e -> deleteSelectedSandwich());

        viewItem.addActionListener(e -> consultSelectedSandwich());

        // Listener for the Button
        listSandwiches.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                gererPopup(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                gererPopup(e);
            }

            private void gererPopup(MouseEvent e) {
                // If we click on the popup (popup is triggered)
                if (e.isPopupTrigger()) {

                    // Get the index of the list where we click, if any selcetion is done, we clear the selection...
                    int index = listSandwiches.locationToIndex(e.getPoint());
                    if (index != -1 && listSandwiches.getCellBounds(index, index).contains(e.getPoint())) {
                        listSandwiches.setSelectedIndex(index);
                    } else {
                        listSandwiches.clearSelection();
                    }

                    // is an element is selected, the Edit/Delete/Consult action is enabled...
                    boolean estSelectionne = !listSandwiches.isSelectionEmpty();
                    editItem.setEnabled(estSelectionne);
                    deleteItem.setEnabled(estSelectionne);
                    viewItem.setEnabled(estSelectionne);

                    // We display the Popup with the actions
                    popupMenu.show(listSandwiches, e.getX(), e.getY());
                }
            }
        });

        // We place the element on the page (GridBagLayout)
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel titleLabel = new JLabel("Liste des Sandwichs");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 5, 10);
        panel.add(titleLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 10, 5, 10);
        panel.add(new JScrollPane(listSandwiches), gbc);

        JLabel hintLabel = new JLabel("Faites un clic droit pour voir les actions");
        hintLabel.setFont(new Font("SansSerif", Font.ITALIC, 12));
        hintLabel.setForeground(Color.GRAY);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 10, 10, 10);
        panel.add(hintLabel, gbc);

        setContentPane(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Link to the "Add" action
    public void addSandwich() {
        SandwichFormDialog dialog = new SandwichFormDialog(this, SandwichFormDialog.ActionMode.AJOUTER, null);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

        Sandwich nouveau = dialog.getSandwich();
        if (nouveau != null) {
            modelSandwich.addElement(nouveau);
        }
    }

    // Link to the "Edit" action
    public void editSelectedSandwich() {
        int index = listSandwiches.getSelectedIndex();
        if (index != -1) {
            Sandwich selection = modelSandwich.get(index);
            SandwichFormDialog dialog = new SandwichFormDialog(this, SandwichFormDialog.ActionMode.MODIFIER, selection);
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
            listSandwiches.repaint();
        }
    }

    // Link to the "Delete" action
    public void deleteSelectedSandwich() {
        int index = listSandwiches.getSelectedIndex();
        if (index != -1) {
            Sandwich selection = modelSandwich.get(index);
            int confirmation = JOptionPane.showConfirmDialog(
                    this,
                    "Supprimer \"" + selection.getNomFr() + "\" ?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirmation == JOptionPane.YES_OPTION) {
                modelSandwich.remove(index);
            }
        }
    }

    // Link to the "Consult" action
    public void consultSelectedSandwich() {
        Sandwich selection = listSandwiches.getSelectedValue();
        if (selection != null) {
            SandwichFormDialog dialog = new SandwichFormDialog(this, SandwichFormDialog.ActionMode.VISUALISER, selection);
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GestionSandwiches::new);
    }
}