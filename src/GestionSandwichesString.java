import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GestionSandwichesString {

    private JList<String> listSandwiches;
    private DefaultListModel<String> model;

    public GestionSandwichesString() {
        JFrame frame = new JFrame("Liste des Sandwichs");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 350);

        // 1. Initial data as String array
        String[] itemsInterets = {"Poulet", "Americain", "Hawai", "Thon", "Végétarien", "Kebab", "Steak", "Merguez"};

        // 2. Create and populate the DefaultListModel
        model = new DefaultListModel<>();
        for (String s : itemsInterets) {
            model.addElement(s);
        }

        // 3. Create the JList linked to the model
        listSandwiches = new JList<>(model);
        listSandwiches.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // 4. Create the JPopupMenu and its items
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

        // 5. Action listeners for each menu item
        addItem.addActionListener(e -> {
            String nouveau = JOptionPane.showInputDialog(frame, "Nom du nouveau sandwich :", "Ajouter", JOptionPane.PLAIN_MESSAGE);
            if (nouveau != null && !nouveau.trim().isEmpty()) {
                model.addElement(nouveau.trim());
            }




        });

        editItem.addActionListener(e -> {
            int index = listSandwiches.getSelectedIndex();
            if (index != -1) {
                String actuel = model.get(index);
                String modifie = JOptionPane.showInputDialog(frame, "Modifier le sandwich :", actuel);
                if (modifie != null && !modifie.trim().isEmpty()) {
                    model.set(index, modifie.trim());
                }
            }
        });

        deleteItem.addActionListener(e -> {
            int index = listSandwiches.getSelectedIndex();
            if (index != -1) {
                int confirmation = JOptionPane.showConfirmDialog(
                        frame,
                        "Supprimer \"" + model.get(index) + "\" ?",
                        "Confirmation",
                        JOptionPane.YES_NO_OPTION
                );
                if (confirmation == JOptionPane.YES_OPTION) {
                    model.remove(index);
                }
            }
        });

        viewItem.addActionListener(e -> {
            String selection = listSandwiches.getSelectedValue();
            if (selection != null) {
                JOptionPane.showMessageDialog(frame, "model.Sandwich sélectionné : " + selection, "Visualiser", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // 6. Handle right-click trigger and enable/disable menu items
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
                if (e.isPopupTrigger()) {
                    int index = listSandwiches.locationToIndex(e.getPoint());

                    // Select item under cursor, or clear selection if clicked on empty space
                    if (index != -1 && listSandwiches.getCellBounds(index, index).contains(e.getPoint())) {
                        listSandwiches.setSelectedIndex(index);
                    } else {
                        listSandwiches.clearSelection();
                    }

                    // Disable Edit, Delete, and View if nothing is selected
                    boolean estSelectionne = !listSandwiches.isSelectionEmpty();
                    editItem.setEnabled(estSelectionne);
                    deleteItem.setEnabled(estSelectionne);
                    viewItem.setEnabled(estSelectionne);

                    popupMenu.show(listSandwiches, e.getX(), e.getY());
                }
            }
        });

        // 7. Layout setup using GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Title "model.Sandwich List" above the list (row 0)
        JLabel titleLabel = new JLabel("model.Sandwich List");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 5, 10);
        panel.add(titleLabel, gbc);

        // Sandwiches list in the middle (row 1)
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 10, 5, 10);
        panel.add(new JScrollPane(listSandwiches), gbc);

        // Helper text below the list (row 2)
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

        frame.setContentPane(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GestionSandwichesString::new);
    }
}