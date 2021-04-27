package sim;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

public class Report extends JFrame {
    private JPanel panel1;
    private JTextArea textAreaReport;
    private JButton saveButton;

    public Report(String reportText) {
        setContentPane(panel1);
        setSize(600, 800);
        textAreaReport.append(reportText);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileNameExtensionFilter filter = new FileNameExtensionFilter("*.TXT", "*.*");
                JFileChooser fc = new JFileChooser();
                fc.setFileFilter(filter);
                if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    try (FileWriter fw = new FileWriter(fc.getSelectedFile())) {
                        fw.write(reportText);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        setVisible(true);

    }
}
