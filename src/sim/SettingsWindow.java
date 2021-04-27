package sim;

import javax.swing.*;
import java.awt.event.*;

public class SettingsWindow extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textFieldCountRep;
    private JSlider slider1;
    private JTextPane TextPane;
    private JCheckBox CheckBoxOnRealTime;
    private JTextField textFieldMaxCall;
    private JTextField textFieldIntervalCreateMean;
    private JTextField textFieldIntervalCreateStDeav;
    private JTextField textFieldProccessMean;
    private JTextField textFieldProccessStDeav;
    private JTextField textFieldMaxQueue;
    private final MainWindow mainWindow;
    private  int speed = 1;

    public SettingsWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        setContentPane(contentPane);
        textFieldCountRep.setText(String.valueOf(mainWindow.getNumberReplication()));
        textFieldMaxCall.setText(String.valueOf(MainWindow.getCountCall()));
        textFieldIntervalCreateMean.setText(String.valueOf((MainWindow.getCreateCallMax()+MainWindow.getCreateCallMin())/2));
        textFieldIntervalCreateStDeav.setText(String.valueOf(MainWindow.getCreateCallMax()-(MainWindow.getCreateCallMax()+MainWindow.getCreateCallMin())/2));
        textFieldProccessMean.setText(String.valueOf((MainWindow.getProccessCallMax()+MainWindow.getProccessCallMin())/2));
        textFieldProccessStDeav.setText(String.valueOf(MainWindow.getProccessCallMax()-(MainWindow.getProccessCallMax()+MainWindow.getProccessCallMin())/2));
        textFieldMaxQueue.setText(String.valueOf(MainWindow.getQueueMax()));
        if(MainWindow.getScale()==1000){
            slider1.setEnabled(false);
            CheckBoxOnRealTime.setSelected(true);
        }
        else
            slider1.setValue(MainWindow.getScale());
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
        CheckBoxOnRealTime.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(CheckBoxOnRealTime.isSelected()){
                    slider1.setEnabled(false);

                }else{
                    slider1.setEnabled(true);
                }
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public static void main(String[] args, MainWindow mainWindow) {
        SettingsWindow dialog = new SettingsWindow(mainWindow);
        dialog.pack();
        dialog.setVisible(true);
    }

    private void onOK() {
        try{
        if(Integer.parseInt(textFieldIntervalCreateMean.getText())>Integer.parseInt(textFieldIntervalCreateStDeav.getText())
                &&Integer.parseInt(textFieldProccessMean.getText())>Integer.parseInt(textFieldProccessStDeav.getText())
                &&Integer.parseInt(textFieldIntervalCreateMean.getText())>0&&Integer.parseInt(textFieldIntervalCreateStDeav.getText())>=0
                &&Integer.parseInt(textFieldProccessMean.getText())>0&&Integer.parseInt(textFieldProccessStDeav.getText())>=0
                &&Integer.parseInt(textFieldMaxQueue.getText())>0&&Integer.parseInt(textFieldMaxCall.getText())>0
                &&Integer.parseInt(textFieldCountRep.getText())>0)
        {
        MainWindow.setCreateCallMax(Integer.parseInt(textFieldIntervalCreateMean.getText())+Integer.parseInt(textFieldIntervalCreateStDeav.getText()));
        MainWindow.setCreateCallMin(Integer.parseInt(textFieldIntervalCreateMean.getText())-Integer.parseInt(textFieldIntervalCreateStDeav.getText()));

        MainWindow.setProccessCallMax(Integer.parseInt(textFieldProccessMean.getText())+Integer.parseInt(textFieldProccessStDeav.getText()));
        MainWindow.setProccessCallMin(Integer.parseInt(textFieldProccessMean.getText())-Integer.parseInt(textFieldProccessStDeav.getText()));

        MainWindow.setCountCall(Integer.parseInt(textFieldMaxCall.getText()));
        MainWindow.setQueueMax(Integer.parseInt(textFieldMaxQueue.getText()));

        mainWindow.setNumberReplication(Integer.parseInt(textFieldCountRep.getText()));
        if(CheckBoxOnRealTime.isSelected())
            speed = 1000;
        else
            speed = slider1.getValue();
        MainWindow.setScale(speed);
        Dispose();}else {
            JOptionPane.showMessageDialog(this, "Введены значения неверного формата","Ошибка", JOptionPane.ERROR_MESSAGE);
        }
        }catch (Exception ex){
            JOptionPane.showMessageDialog(this, "Введены значения неверного формата","Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCancel() {
        Dispose();
    }

    public void Dispose() {
        dispose();
    }
}
