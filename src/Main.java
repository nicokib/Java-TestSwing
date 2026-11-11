import ui.PasswordFormDialog;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        // Applique le style visuel du système d'exploitation
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(() -> {
            // Test 1 : Mode AJOUTER (ingredient est null)
            PasswordFormDialog dialog = new PasswordFormDialog(null);
            dialog.setVisible(true); // Bloque l'exécution jusqu'à la fermeture du dialogue
            // Termine proprement le programme de test
            System.exit(0);
        });
    }
}
