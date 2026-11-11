package ui;


import javax.swing.*;
import java.awt.*;

public class PasswordFormDialog extends JDialog {

    private JTextField txtUsername = new JTextField(40);
    private JTextField txtPassword = new JPasswordField(40);

    private boolean okClicked = false;

    public PasswordFormDialog(Frame parent) {
        super(parent, "Password", true);

        buildUI();

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
        JLabel lblTitre = new JLabel("Enter your identifiant and password");
        lblTitre.setFont(new Font("SansSerif", Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = idxRow; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(12, 12, 14, 12);
        panel.add(lblTitre, gbc);

        gbc.gridwidth = 1;
        gbc.insets = new Insets(6, 12, 6, 12);

        // Nom en français
        gbc.gridx = 0; gbc.gridy = ++idxRow; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("Username :"), gbc);

        gbc.gridx = 1; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(txtUsername, gbc);

        // Nom en néerlandais
        gbc.gridx = 0; gbc.gridy = ++idxRow; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("Password :"), gbc);

        gbc.gridx = 1; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(txtPassword, gbc);

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


    private void onOk() {

        // Found email in PersonRepository + valid password
        String user =  txtUsername.getText();
        String password = txtPassword.getText();

        //Person personFound = new Person();
        //PasswordUtils.checkPassword(password, personFound.getHashedPassword());

        okClicked = true;
        dispose();


        SwingUtilities.invokeLater(() -> {
            new GestionSandwiches();
        });


        /*// Instancier et afficher la JFrame
        ChoixSandwichesFrame nouvelleFenetre = new ChoixSandwichesFrame();
        nouvelleFenetre.setSize(500, 400);
        nouvelleFenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        nouvelleFenetre.setLocationRelativeTo(null);
        nouvelleFenetre.setVisible(true);*/
    }

    public boolean isOkClicked() { return okClicked; }


    public static void main(String[] args) {
        // Applique le style visuel du système d'exploitation
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            // Test 1 : Mode AJOUTER (ingredient est null)
            PasswordFormDialog dialog = new PasswordFormDialog(null);
            dialog.setVisible(true); // Bloque l'exécution jusqu'à la fermeture du dialogue

            // Vérification après fermeture
            if (dialog.isOkClicked()) {
                System.out.println("Action validée !");
            } else {
                System.out.println("Action annulée.");
            }

        });
    }
}
