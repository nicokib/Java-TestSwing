import javax.swing.*;
import java.awt.event.*;

public class PopupMenuExample {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("JPopupMenu Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);

            JTextArea textArea = new JTextArea("Right-click anywhere inside this text area...");

            // 1. Create the popup menu and menu items
            JPopupMenu popupMenu = new JPopupMenu();
            JMenuItem cutItem = new JMenuItem("Cut");
            JMenuItem copyItem = new JMenuItem("Copy");
            JMenuItem pasteItem = new JMenuItem("Paste");

            // 2. Add actions to the menu items
            cutItem.addActionListener(e -> textArea.cut());
            copyItem.addActionListener(e -> textArea.copy());
            pasteItem.addActionListener(e -> textArea.paste());

            // 3. Add items (and optional separators) to the popup menu
            popupMenu.add(cutItem);
            popupMenu.add(copyItem);
            popupMenu.addSeparator();
            popupMenu.add(pasteItem);

            // 4. Attach a MouseListener to trigger the popup cross-platform
            textArea.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    checkPopup(e);
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    checkPopup(e);
                }

                private void checkPopup(MouseEvent e) {
                    if (e.isPopupTrigger()) {
                        popupMenu.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            });

            frame.add(new JScrollPane(textArea));
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}