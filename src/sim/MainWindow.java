package sim;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import static sim.MainWindow.*;

public class MainWindow extends JFrame {

    static int summaryOp1 = 0, summaryOp2 = 0, summaryOp3 = 0, summaryOp4 = 0, summaryOp5 = 0;
    static ArrayList<Integer> listSummaryOp1= new ArrayList<Integer>(), listSummaryOp2= new ArrayList<Integer>(), listSummaryOp3= new ArrayList<Integer>(), listSummaryOp4= new ArrayList<Integer>(), listSummaryOp5 = new ArrayList<Integer>();
    static ArrayList<Double> listLoadOp1= new ArrayList<>(),  listLoadOp2= new ArrayList<>(), listLoadOp3= new ArrayList<>(), listLoadOp4= new ArrayList<>(), listLoadOp5 = new ArrayList<>();
    static ArrayList<Long> listSummaryTimeOp1= new ArrayList<>(), listSummaryTimeOp2= new ArrayList<>(), listSummaryTimeOp3= new ArrayList<>(), listSummaryTimeOp4= new ArrayList<>(), listSummaryTimeOp5 = new ArrayList<>();
    static ArrayList<Integer> listOtkaz = new ArrayList<>();
    static ArrayList<Long> listSummaryTime = new ArrayList<>();
    static double loadOp1 = 0.0, loadOp2 = 0.0, loadOp3 = 0.0, loadOp4 = 0.0, loadOp5 = 0.0;
    static int servedClients = 0;
    static int otkaz = 0;
    static long startTime = 0;
    static long endTime = 0;
    static int currentAbonent = 0;
    static int currentReplication = 1;
    private static int createCallMax = 8;
    private static int createCallMin = 2;
    private static int proccessCallMax = 50;
    private static int proccessCallMin = 10;
    private static int countCall = 200;
    private static int queueMax = 2;
    private static int queue1 = 0;
    private static int queue2 = 0;
    private static int queue3 = 0;
    private static int queue4 = 0;
    private static int queue5 = 0;
    private static int scale = 1;
    private static long summaryTimeOp1 = 0, summaryTimeOp2 = 0, summaryTimeOp3 = 0, summaryTimeOp4 = 0, summaryTimeOp5 = 0;
    private static boolean flagEmerStop = false;
    private static boolean flagWork = false;
    operator1 op1;
    operator2 op2;
    operator3 op3;
    operator4 op4;
    operator5 op5;
    Generator gen;
    javax.swing.JTextField textFieldOtkaz;
    JButton startButton;
    javax.swing.JTextField textFieldTimeModel;
    javax.swing.JTextField textFieldItogOp1;
    private int numberReplication = 1;
    private boolean flagIsModelOver = false;
    private JPanel panelMain;
    private JTextField textFieldSummaryAb;
    private JTextField textFieldTimeOp1;
    private JTextField textFieldLoadOp1;
    private JTextField textFieldItogOp2;
    private JTextField textFieldTimeOp2;
    private JTextField textFieldLoadOp2;
    private JTextField textFieldItogOp3;
    private JTextField textFieldTimeOp3;
    private JTextField textFieldLoadOp3;
    private JTextField textFieldItogOp4;
    private JTextField textFieldTimeOp4;
    private JTextField textFieldLoadOp4;
    private JTextField textFieldItogOp5;
    private JTextField textFieldTimeOp5;
    private JTextField textFieldLoadOp5;
    private JTextArea textAreaReport;
    private JButton buttonSettings;
    private JButton openReportButton;
    private JButton stopButton;
    private JTextField textFieldTestInfo;
    private JTextField textFieldShowCreateCall;
    private JTextField textFieldQueue1;
    private JTextField textFieldShowOp1;
    private JTextField textFieldQueue2;
    private JTextField textFieldQueue3;
    private JTextField textFieldQueue4;
    private JTextField textFieldQueue5;
    private JTextField textFieldShowOtkaz;
    private JPanel leftDerewo;
    private JPanel dc1;
    private JPanel dc2;
    private JPanel dc3;
    private JPanel dc4;
    private JPanel dc5;
    private JPanel rightDerewo;
    private JTextField textFieldShowServed;
    private JTextField textFieldShowOp2;
    private JTextField textFieldShowOp3;
    private JTextField textFieldShowOp4;
    private JTextField textFieldShowOp5;

    public MainWindow() {
        gen = new Generator();
        setContentPane(panelMain);
        leftDerewo.setLayout(new BorderLayout());
        leftDerewo.add(setImage("C://dl.png"));
        rightDerewo.setLayout(new BorderLayout());
        rightDerewo.add(setImage("C://dr.png"));
        dc1.setLayout(new BorderLayout()); dc1.add(setImage("C://dc.png"));
        dc2.setLayout(new BorderLayout()); dc2.add(setImage("C://dc.png"));
        dc3.setLayout(new BorderLayout()); dc3.add(setImage("C://dc.png"));
        dc4.setLayout(new BorderLayout()); dc4.add(setImage("C://dc.png"));
        dc5.setLayout(new BorderLayout()); dc5.add(setImage("C://dc.png"));
        setSize(600, 800);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (flagIsModelOver) {
                    currentReplication = 1;
                    textAreaReport.setText(null);
                    flagIsModelOver = false;
                } else {
                    setFlagEmerStop(false);
                    stopButton.setEnabled(true);
                    startModeling();
                }

            }
        });
        openReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Report reportWindow = new Report(textAreaReport.getText());
            }
        });
        buttonSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSettings();
            }
        });
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startButton.setEnabled(true);
                setFlagEmerStop(true);
                stopButton.setEnabled(false);
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(0);
            }
        });
        setVisible(true);
    }

    protected JLabel setImage(String path) {
        File f;
        f = new File(path);
        BufferedImage img = null;
        try {
            img = ImageIO.read(f); // Считываем картинку
        } catch(IOException ioe) {
            JOptionPane.showConfirmDialog(null, "Что-то неправильно!\n" + ioe.toString(),
                    "Error!", JOptionPane.PLAIN_MESSAGE);
        }
        JLabel jl = new JLabel(new ImageIcon(img));
        return jl;

    }
    public synchronized static boolean isFlagEmerStop() {
        return flagEmerStop;
    }

    public synchronized static void setFlagEmerStop(boolean flagEmerStop) {
        MainWindow.flagEmerStop = flagEmerStop;
    }

    public synchronized static boolean isFlagWork() {
        return flagWork;
    }

    public synchronized static void setFlagWork(boolean flagWork) {
        MainWindow.flagWork = flagWork;
    }

    public synchronized static int getQueue1() {
        return queue1;
    }

    public synchronized static void setQueue1(int queue1) {
        MainWindow.queue1 = queue1;
    }

    public synchronized static int getQueue2() {
        return queue2;
    }

    public synchronized static void setQueue2(int queue2) {
        MainWindow.queue2 = queue2;
    }

    public synchronized static int getQueue3() {
        return queue3;
    }

    public synchronized static void setQueue3(int queue3) {
        MainWindow.queue3 = queue3;
    }

    public synchronized static int getQueue4() {
        return queue4;
    }

    public synchronized static void setQueue4(int queue4) {
        MainWindow.queue4 = queue4;
    }

    public synchronized static int getQueue5() {
        return queue5;
    }

    public synchronized static void setQueue5(int queue5) {
        MainWindow.queue5 = queue5;
    }

    public synchronized static long getSummaryTimeOp1() {
        return summaryTimeOp1;
    }

    public synchronized static void setSummaryTimeOp1(long summaryTimeOp1) {
        MainWindow.summaryTimeOp1 = summaryTimeOp1;
    }

    public synchronized static long getSummaryTimeOp2() {
        return summaryTimeOp2;
    }

    public synchronized static void setSummaryTimeOp2(long summaryTimeOp2) {
        MainWindow.summaryTimeOp2 = summaryTimeOp2;
    }

    public synchronized static long getSummaryTimeOp3() {
        return summaryTimeOp3;
    }

    public synchronized static void setSummaryTimeOp3(long summaryTimeOp3) {
        MainWindow.summaryTimeOp3 = summaryTimeOp3;
    }

    public synchronized static long getSummaryTimeOp4() {
        return summaryTimeOp4;
    }

    public synchronized static void setSummaryTimeOp4(long summaryTimeOp4) {
        MainWindow.summaryTimeOp4 = summaryTimeOp4;
    }

    public synchronized static long getSummaryTimeOp5() {
        return summaryTimeOp5;
    }

    public synchronized static void setSummaryTimeOp5(long summaryTimeOp5) {
        MainWindow.summaryTimeOp5 = summaryTimeOp5;
    }

    public synchronized static int getScale() {
        return scale;
    }

    public synchronized static void setScale(int scale) {
        MainWindow.scale = scale;
    }

    public int getNumberReplication() {
        return numberReplication;
    }

    public void setNumberReplication(int numberReplication) {
        this.numberReplication = numberReplication;
    }

    public synchronized static ArrayList<Integer> getListSummaryOp1() {
        return listSummaryOp1;
    }

    public synchronized static void setListSummaryOp1(Integer elem) {
        MainWindow.listSummaryOp1.add(elem);
    }

    public synchronized static ArrayList<Integer> getListSummaryOp2() {
        return listSummaryOp2;
    }

    public synchronized static void setListSummaryOp2(Integer elem) {
        MainWindow.listSummaryOp2.add(elem);
    }

    public synchronized static ArrayList<Integer> getListSummaryOp3() {
        return listSummaryOp3;
    }

    public synchronized static void setListSummaryOp3(Integer elem) {
        MainWindow.listSummaryOp3.add(elem);
    }

    public synchronized static ArrayList<Integer> getListSummaryOp4() {
        return listSummaryOp4;
    }

    public synchronized static void setListSummaryOp4(Integer elem) {
        MainWindow.listSummaryOp4.add(elem);
    }

    public synchronized static ArrayList<Integer> getListSummaryOp5() {
        return listSummaryOp5;
    }

    public synchronized static void setListSummaryOp5(Integer elem) {
        MainWindow.listSummaryOp5.add(elem);
    }

    public synchronized static ArrayList<Double> getListLoadOp1() {
        return listLoadOp1;
    }

    public synchronized static void setListLoadOp1(Double elem) {
        MainWindow.listLoadOp1.add(elem);
    }

    public synchronized static ArrayList<Double> getListLoadOp2() {
        return listLoadOp2;
    }

    public synchronized static void setListLoadOp2(Double elem) {
        MainWindow.listLoadOp2.add(elem);
    }

    public synchronized static ArrayList<Double> getListLoadOp3() {
        return listLoadOp3;
    }

    public synchronized static void setListLoadOp3(Double elem) {
        MainWindow.listLoadOp3.add(elem);
    }

    public synchronized static ArrayList<Double> getListLoadOp4() {
        return listLoadOp4;
    }

    public synchronized static void setListLoadOp4(Double elem) {
        MainWindow.listLoadOp4.add(elem);
    }

    public synchronized static ArrayList<Double> getListLoadOp5() {
        return listLoadOp5;
    }

    public synchronized static void setListLoadOp5(Double elem) {
        MainWindow.listLoadOp5.add(elem);
    }

    public synchronized static ArrayList<Long> getListSummaryTimeOp1() {
        return listSummaryTimeOp1;
    }

    public synchronized static void setListSummaryTimeOp1(Long time) {
        MainWindow.listSummaryTimeOp1.add(time);
    }

    public synchronized static ArrayList<Long> getListSummaryTimeOp2() {
        return listSummaryTimeOp2;
    }

    public synchronized static void setListSummaryTimeOp2(Long time) {
        MainWindow.listSummaryTimeOp2.add(time);
    }

    public synchronized static ArrayList<Long> getListSummaryTimeOp3() {
        return listSummaryTimeOp3;
    }

    public synchronized static void setListSummaryTimeOp3(Long time) {
        MainWindow.listSummaryTimeOp3.add(time);
    }

    public synchronized static ArrayList<Long> getListSummaryTimeOp4() {
        return listSummaryTimeOp4;
    }

    public synchronized static void setListSummaryTimeOp4(Long time) {
        MainWindow.listSummaryTimeOp4.add(time);;
    }

    public synchronized static ArrayList<Long> getListSummaryTimeOp5() {
        return listSummaryTimeOp5;
    }

    public synchronized static void setListSummaryTimeOp5(Long time) {
        MainWindow.listSummaryTimeOp5.add(time);
    }

    public synchronized static ArrayList<Integer> getListOtkaz() {
        return listOtkaz;
    }

    public synchronized static void setListOtkaz(Integer elem) {
        MainWindow.listOtkaz.add(elem);
    }

    public synchronized static ArrayList<Long> getListSummaryTime() {
        return listSummaryTime;
    }

    public synchronized static void setListSummaryTime(Long time) {
        MainWindow.listSummaryTime.add(time);
    }

    public synchronized static int getCreateCallMax() {
        return createCallMax;
    }

    public synchronized static void setCreateCallMax(int createCallMax) {
        MainWindow.createCallMax = createCallMax;
    }

    public synchronized static int getCreateCallMin() {
        return createCallMin;
    }

    public synchronized static void setCreateCallMin(int createCallMin) {
        MainWindow.createCallMin = createCallMin;
    }

    public synchronized static int getProccessCallMax() {
        return proccessCallMax;
    }

    public synchronized static void setProccessCallMax(int proccessCallMax) {
        MainWindow.proccessCallMax = proccessCallMax;
    }

    public synchronized static int getProccessCallMin() {
        return proccessCallMin;
    }

    public synchronized static void setProccessCallMin(int proccessCallMin) {
        MainWindow.proccessCallMin = proccessCallMin;
    }

    public synchronized static int getCountCall() {
        return countCall;
    }

    public synchronized static void setCountCall(int countCall) {
        MainWindow.countCall = countCall;
    }

    public synchronized static int getQueueMax() {
        return queueMax;
    }

    public synchronized static void setQueueMax(int queueMax) {
        MainWindow.queueMax = queueMax;
    }

    public synchronized static int getServedClients() {
        return servedClients;
    }

    public synchronized static void setServedClients(int servedClients) {
        MainWindow.servedClients = servedClients;
    }

    private void openSettings() {
        SettingsWindow.main(new String[]{}, this);
    }

    public void startModeling() {
        if (currentReplication < numberReplication + 1) {
            buttonSettings.setEnabled(false);
            setServedClients(0);
            textFieldShowOtkaz.setText("Пропущ: 0");
            textFieldShowServed.setText("Обслуж: 0");
            textFieldQueue1.setText("O1: 0"); textFieldQueue1.setBackground(Color.green);
            textFieldQueue2.setText("O2: 0"); textFieldQueue2.setBackground(Color.green);
            textFieldQueue3.setText("O3: 0"); textFieldQueue3.setBackground(Color.green);
            textFieldQueue4.setText("O4: 0"); textFieldQueue4.setBackground(Color.green);
            textFieldQueue5.setText("O5: 0"); textFieldQueue5.setBackground(Color.green);
            textFieldShowOp1.setText("Свободен"); textFieldShowOp1.setBackground(Color.green);
            textFieldShowOp2.setText("Свободен"); textFieldShowOp2.setBackground(Color.green);
            textFieldShowOp3.setText("Свободен"); textFieldShowOp3.setBackground(Color.green);
            textFieldShowOp4.setText("Свободен"); textFieldShowOp4.setBackground(Color.green);
            textFieldShowOp5.setText("Свободен"); textFieldShowOp5.setBackground(Color.green);

            textFieldItogOp1.setText("Ожидание результатов");
            textFieldItogOp2.setText("Ожидание результатов");
            textFieldItogOp3.setText("Ожидание результатов");
            textFieldItogOp4.setText("Ожидание результатов");
            textFieldItogOp5.setText("Ожидание результатов");
            textFieldTimeOp1.setText("Ожидание результатов");
            textFieldTimeOp2.setText("Ожидание результатов");
            textFieldTimeOp3.setText("Ожидание результатов");
            textFieldTimeOp4.setText("Ожидание результатов");
            textFieldTimeOp5.setText("Ожидание результатов");
            textFieldLoadOp1.setText("Ожидание результатов");
            textFieldLoadOp2.setText("Ожидание результатов");
            textFieldLoadOp3.setText("Ожидание результатов");
            textFieldLoadOp4.setText("Ожидание результатов");
            textFieldLoadOp5.setText("Ожидание результатов");
            textFieldTimeModel.setText("Ожидание результатов");
            textFieldSummaryAb.setText("Ожидание результатов");
            setFlagWork(false);
            op1 = new operator1();
            op2 = new operator2();
            op3 = new operator3();
            op4 = new operator4();
            op5 = new operator5();
            setQueue1(0);
            setQueue2(0);
            setQueue3(0);
            setQueue4(0);
            setQueue5(0);
            setSummaryTimeOp1(0);
            setSummaryTimeOp2(0);
            setSummaryTimeOp3(0);
            setSummaryTimeOp4(0);
            setSummaryTimeOp5(0);
            summaryOp1 = summaryOp2 = summaryOp3 = summaryOp4 = summaryOp5 = 0;
            otkaz = 0;
            currentAbonent = 0;
            startTime = System.currentTimeMillis();
            op1.process(textFieldItogOp1, textFieldTimeOp1, textFieldQueue1, textFieldShowOp1, textFieldShowServed);
            op2.process(textFieldItogOp2, textFieldTimeOp2, textFieldQueue2, textFieldShowOp2, textFieldShowServed);
            op3.process(textFieldItogOp3, textFieldTimeOp3, textFieldQueue3, textFieldShowOp3, textFieldShowServed);
            op4.process(textFieldItogOp4, textFieldTimeOp4, textFieldQueue4, textFieldShowOp4, textFieldShowServed);
            op5.process(textFieldItogOp5, textFieldTimeOp5, textFieldQueue5, textFieldShowOp5, textFieldShowServed);
            gen.generator(startButton, stopButton, textFieldOtkaz, textFieldTimeModel, textFieldSummaryAb, textFieldLoadOp1,
                    textFieldLoadOp2, textFieldLoadOp3, textFieldLoadOp4, textFieldLoadOp5, textAreaReport, getNumberReplication(), textFieldTestInfo,
                    textFieldQueue1, textFieldQueue2, textFieldQueue3, textFieldQueue4, textFieldQueue5, textFieldShowOtkaz, textFieldShowCreateCall);
        } else {
            buttonSettings.setEnabled(true);
            fillReportAvg();
            listLoadOp1.clear(); listLoadOp2.clear(); listLoadOp3.clear(); listLoadOp4.clear(); listLoadOp5.clear();
            listSummaryOp1.clear();listSummaryOp2.clear();listSummaryOp3.clear();listSummaryOp4.clear();listSummaryOp5.clear();
            listSummaryTimeOp1.clear();listSummaryTimeOp2.clear();listSummaryTimeOp3.clear();listSummaryTimeOp4.clear();listSummaryTimeOp5.clear();
            listSummaryTime.clear(); listOtkaz.clear();
            stopButton.setEnabled(false);
            flagIsModelOver = true;
        }
    }


    private Long avgValueLong(ArrayList<Long> arrayList){
        long value = 0L;
        for (Long aLong : arrayList) value += aLong;
        value = value/arrayList.size();
        return value;
    }
    private int avgValueInt(ArrayList<Integer> arrayList){
        int value = 0;
        for (Integer aInt : arrayList) value += aInt;
        value = value/arrayList.size();
        return value;
    }

    private double avgValueDouble(ArrayList<Double> arrayList){
        double value = 0.0;
        for (Double aDouble : arrayList) value += aDouble;
        value = value/arrayList.size();
        return value;
    }


    public void fillReportAvg(){
        long avgDiffTime = avgValueLong(getListSummaryTime());
        int avgOtkaz = avgValueInt(getListOtkaz());

        int avgSummaryOp1 = avgValueInt(getListSummaryOp1());
        int avgSummaryOp2 = avgValueInt(getListSummaryOp2());
        int avgSummaryOp3 = avgValueInt(getListSummaryOp3());
        int avgSummaryOp4 = avgValueInt(getListSummaryOp4());
        int avgSummaryOp5 = avgValueInt(getListSummaryOp5());
        double avgLoadOp1 = avgValueDouble(getListLoadOp1());
        double avgLoadOp2 = avgValueDouble(getListLoadOp2());
        double avgLoadOp3 = avgValueDouble(getListLoadOp3());
        double avgLoadOp4 = avgValueDouble(getListLoadOp4());
        double avgLoadOp5 = avgValueDouble(getListLoadOp5());
        long avgTimeOp1= avgValueLong(getListSummaryTimeOp1());
        long avgTimeOp2= avgValueLong(getListSummaryTimeOp2());
        long avgTimeOp3= avgValueLong(getListSummaryTimeOp3());
        long avgTimeOp4= avgValueLong(getListSummaryTimeOp4());
        long avgTimeOp5= avgValueLong(getListSummaryTimeOp5());



        textAreaReport.append("\n\n Средние значения по всем прогонам: ");
        textAreaReport.append("\n-------------------------------------------------\n");
        textAreaReport.append("\nСр. Суммарное время (секунды) :" + avgDiffTime);
        textAreaReport.append("\nСр. Количество отказов в обслуживании : " + avgOtkaz);

        textAreaReport.append("\n\nОператор 1");
        textAreaReport.append("\n   Ср. Обслужено : " + avgSummaryOp1);
        textAreaReport.append("\n   Ср. Нагрузка (%) : " + avgLoadOp1);
        textAreaReport.append("\n   Ср. Суммарное время работы (сек) : " + avgTimeOp1);

        textAreaReport.append("\n\nОператор 2");
        textAreaReport.append("\n   Ср. Обслужено : " + avgSummaryOp2);
        textAreaReport.append("\n   Ср. Нагрузка (%) : " + avgLoadOp2);
        textAreaReport.append("\n   Ср. Суммарное время работы (сек) : " + avgTimeOp2);

        textAreaReport.append("\n\nОператор 3");
        textAreaReport.append("\n   Ср. Обслужено : " + avgSummaryOp3);
        textAreaReport.append("\n   Ср. Нагрузка (%) : " + avgLoadOp3);
        textAreaReport.append("\n   Ср. Суммарное время работы (сек) : " + avgTimeOp3);

        textAreaReport.append("\n\nОператор 4");
        textAreaReport.append("\n   Ср. Обслужено : " + avgSummaryOp4);
        textAreaReport.append("\n   Ср. Нагрузка (%) : " + avgLoadOp4);
        textAreaReport.append("\n   Ср. Суммарное время работы (сек) : " + avgTimeOp4);

        textAreaReport.append("\n\nОператор 5");
        textAreaReport.append("\n   Ср. Обслужено : " + avgSummaryOp5);
        textAreaReport.append("\n   Ср. Нагрузка (%) : " + avgLoadOp5);
        textAreaReport.append("\n   Ср. Суммарное время работы (сек) : " + avgTimeOp5);
    }
}


class Generator extends Thread {
    double d_diffTime = 0.0;
    double timeOp1 = 0.0;
    double timeOp2 = 0.0;
    double timeOp3 = 0.0;
    double timeOp4 = 0.0;
    double timeOp5 = 0.0;
    double d_loadOp1 = 0.0;
    double d_loadOp2 = 0.0;
    double d_loadOp3 = 0.0;
    double d_loadOp4 = 0.0;
    double d_loadOp5 = 0.0;
    long diffTime = 0L;

    public void generator(JButton startButton,
                          JButton stopButton,
                          JTextField textFieldOtkazs,
                          JTextField textFieldTimeModel,
                          JTextField textSummaryAb,
                          JTextField loadOp1,
                          JTextField loadOp2,
                          JTextField loadOp3,
                          JTextField loadOp4,
                          JTextField loadOp5,
                          JTextArea reportArea, int numberReplicationS, JTextField testInfo,
                          JTextField q1, JTextField q2, JTextField q3, JTextField q4, JTextField q5, JTextField otk, JTextField newCall) {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        synchronized (this) {
                            int min = MainWindow.getCreateCallMin();
                            int max = MainWindow.getCreateCallMax();
                            Random random = new Random(System.currentTimeMillis());
                            NormalDistribution normalDistribution = new NormalDistribution();
                            while (true) {
                                if (isFlagEmerStop()) {
                                    break;
                                }
                                while (currentAbonent < MainWindow.getCountCall()) {
                                    if (isFlagEmerStop()) {
                                        break;
                                    }
                                    startButton.setEnabled(false);
                                    try {
                                        //int delay = (random.nextInt(max - min + 1) + min) * getScale();
                                        int delay  = (int)Math.abs(normalDistribution.generateGaussian((max+min)/2,max-(max+min)/2)*getScale());
                                        long sInfo = System.currentTimeMillis();
                                        sleep(delay);
                                        newCall.setBackground(Color.GREEN);
                                        testInfo.setText(String.valueOf((delay)/getScale()));
                                        if (getQueue1() < MainWindow.getQueueMax()) {
                                            setQueue1((getQueue1() + 1));
                                            q1.setBackground(Color.orange);
                                            q1.setText("О1: "+getQueue1());
                                        } else if (getQueue2() < MainWindow.getQueueMax()) {
                                            setQueue2(getQueue2() + 1);
                                            q2.setBackground(Color.orange);
                                            q2.setText("О2: "+getQueue2());
                                        } else if (getQueue3() < MainWindow.getQueueMax()) {
                                            setQueue3(getQueue3() + 1);
                                            q3.setBackground(Color.orange);
                                            q3.setText("О3: "+getQueue3());
                                        } else if (getQueue4() < MainWindow.getQueueMax()) {
                                            setQueue4(getQueue4() + 1);
                                            q4.setBackground(Color.orange);
                                            q4.setText("О4: "+getQueue4());
                                        } else if (getQueue5() < MainWindow.getQueueMax()) {
                                            setQueue5(getQueue5() + 1);
                                            q5.setBackground(Color.orange);
                                            q5.setText("О5: "+getQueue5());
                                        } else {
                                            otkaz++;
                                            otk.setBackground(Color.ORANGE);
                                            otk.setText("Пропущ: "+otkaz);
                                        }
                                        textFieldOtkazs.setText(String.valueOf(otkaz));
                                        currentAbonent++;
                                        newCall.setText("Звонков: "+currentAbonent);
                                        newCall.setBackground(Color.WHITE);
                                        textSummaryAb.setText(String.valueOf(currentAbonent));

                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                }
                                if (currentAbonent == MainWindow.getCountCall() && getQueue1() == 0 && getQueue2() == 0 && getQueue3() == 0 && getQueue4() == 0 && getQueue5() == 0) {
                                    setFlagWork(true);
                                    endTime = System.currentTimeMillis();
                                    diffTime = endTime - startTime;
                                    d_diffTime = diffTime;
                                    d_diffTime = d_diffTime / getScale();
                                    textFieldTimeModel.setText(String.valueOf(d_diffTime));
                                    timeOp1 = getSummaryTimeOp1();
                                    timeOp2 = getSummaryTimeOp2();
                                    timeOp3 = getSummaryTimeOp3();
                                    timeOp4 = getSummaryTimeOp4();
                                    timeOp5 = getSummaryTimeOp5();
                                    d_loadOp1 = timeOp1 / d_diffTime * 100;
                                    d_loadOp2 = timeOp2 / d_diffTime * 100;
                                    d_loadOp3 = timeOp3 / d_diffTime * 100;
                                    d_loadOp4 = timeOp4 / d_diffTime * 100;
                                    d_loadOp5 = timeOp5 / d_diffTime * 100;
                                    loadOp1.setText(String.valueOf(d_loadOp1));
                                    loadOp2.setText(String.valueOf(d_loadOp2));
                                    loadOp3.setText(String.valueOf(d_loadOp3));
                                    loadOp4.setText(String.valueOf(d_loadOp4));
                                    loadOp5.setText(String.valueOf(d_loadOp5));
                                    fillReport(reportArea);
                                    int numberReplication = numberReplicationS;
                                    startButton.setEnabled(true);

                                    if (numberReplication > 0) {
                                        currentReplication++;
                                        startButton.doClick();
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
        ).start();
    }

    void fillReport(JTextArea report) {
        report.append("\n\nПрогон : " + currentReplication + "  " + (new Date(System.currentTimeMillis())).toString());
        report.append("\n-------------------------------------------------\n");
        report.append("\nСуммарное время (секунды) :" + d_diffTime);
        report.append("\nКоличество отказов в обслуживании : " + otkaz);
        
        MainWindow.setListSummaryTime((long) d_diffTime);
        MainWindow.setListOtkaz(otkaz);
        
        report.append("\n\nОператор 1");
        report.append("\n   Обслужено : " + summaryOp1);
        report.append("\n   Нагрузка (%) : " + d_loadOp1);
        report.append("\n   Суммарное время работы (сек) : " + timeOp1);
        
        MainWindow.setListLoadOp1(d_loadOp1);
        MainWindow.setListSummaryOp1(summaryOp1);
        MainWindow.setListSummaryTimeOp1((long) timeOp1);

        report.append("\n\nОператор 2");
        report.append("\n   Обслужено : " + summaryOp2);
        report.append("\n   Нагрузка (%) : " + d_loadOp2);
        report.append("\n   Суммарное время работы (сек) : " + timeOp2);

        MainWindow.setListLoadOp2(d_loadOp2);
        MainWindow.setListSummaryOp2(summaryOp2);
        MainWindow.setListSummaryTimeOp2((long) timeOp2);

        report.append("\n\nОператор 3");
        report.append("\n   Обслужено : " + summaryOp3);
        report.append("\n   Нагрузка (%) : " + d_loadOp3);
        report.append("\n   Суммарное время работы (сек) : " + timeOp3);

        MainWindow.setListLoadOp3(d_loadOp3);
        MainWindow.setListSummaryOp3(summaryOp3);
        MainWindow.setListSummaryTimeOp3((long) timeOp3);

        report.append("\n\nОператор 4");
        report.append("\n   Обслужено : " + summaryOp4);
        report.append("\n   Нагрузка (%) : " + d_loadOp4);
        report.append("\n   Суммарное время работы (сек) : " + timeOp4);

        MainWindow.setListLoadOp4(d_loadOp4);
        MainWindow.setListSummaryOp4(summaryOp4);
        MainWindow.setListSummaryTimeOp4((long) timeOp4);

        report.append("\n\nОператор 5");
        report.append("\n   Обслужено : " + summaryOp5);
        report.append("\n   Нагрузка (%) : " + d_loadOp5);
        report.append("\n   Суммарное время работы (сек) : " + timeOp5);

        MainWindow.setListLoadOp5(d_loadOp5);
        MainWindow.setListSummaryOp5(summaryOp5);
        MainWindow.setListSummaryTimeOp5((long) timeOp5);
    }
}

class operator1 extends Thread {

    public void process(JTextField textFieldItogOp1, JTextField timeOp1, JTextField queueAnim, JTextField opAnim, JTextField serv) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    int max = MainWindow.getProccessCallMax();
                    int min = MainWindow.getProccessCallMin();
                    Random random = new Random(System.currentTimeMillis());
                    NormalDistribution normalDistribution = new NormalDistribution();
                    while (true) {
                        if (isFlagEmerStop())
                            break;
                        try {
                            if (!isFlagWork()) {
                                if (getQueue1() > 0) {
                                    // int delay = ((int) (Math.random() * (max - min + 1) + min));
                                    //Random random = new Random(System.currentTimeMillis());
                                    //int delay = (random.nextInt(max - min + 1) + min);
                                    int delay  = (int)Math.abs(normalDistribution.generateGaussian((max+min)/2,max-(max+min)/2));
                                    //int delay = Math.abs((int)random.nextGaussian()*20+30)*getScale();
                                    setSummaryTimeOp1(getSummaryTimeOp1() + delay);
                                    opAnim.setBackground(Color.RED);
                                    opAnim.setText("Занят");
                                    sleep(delay * getScale());
                                    opAnim.setBackground(Color.GREEN);
                                    opAnim.setText("Свободен");
                                    summaryOp1++;
                                    setServedClients(getServedClients()+1);
                                    serv.setText("Обслуж: "+String.valueOf(getServedClients()));
                                    timeOp1.setText(String.valueOf(getSummaryTimeOp1()));
                                    textFieldItogOp1.setText(String.valueOf(summaryOp1));
                                    setQueue1(getQueue1() - 1);
                                    if(getQueue1()==0){
                                        queueAnim.setBackground(Color.green);
                                        queueAnim.setText("O1: "+getQueue1());
                                    }
                                }
                            } else {
                                break;
                            }
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }
}

class operator2 extends Thread {

    public void process(JTextField textFieldItogOp2, JTextField timeOp2, JTextField queueAnim, JTextField opAnim, JTextField serv) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    int max = MainWindow.getProccessCallMax();
                    int min = MainWindow.getProccessCallMin();
                    Random random = new Random(System.currentTimeMillis());
                    NormalDistribution normalDistribution = new NormalDistribution();
                    while (true) {
                        if (isFlagEmerStop())
                            break;
                        try {
                            if (!isFlagWork()) {
                                if (getQueue2() > 0) {
                                    // int delay = ((int) (Math.random() * (max - min + 1) + min));
                                    //Random random = new Random(System.currentTimeMillis());
                                    //int delay = (random.nextInt(max - min + 1) + min) ;
                                    int delay  = (int)Math.abs(normalDistribution.generateGaussian((max+min)/2,max-(max+min)/2));
                                    //int delay = Math.abs((int)random.nextGaussian()*20+30)*getScale();
                                    setSummaryTimeOp2(getSummaryTimeOp2() + delay);
                                    opAnim.setBackground(Color.RED);
                                    opAnim.setText("Занят");
                                    sleep(delay * getScale());
                                    opAnim.setBackground(Color.GREEN);
                                    opAnim.setText("Свободен");
                                    summaryOp2++;
                                    setServedClients(getServedClients()+1);
                                    serv.setText("Обслуж: "+String.valueOf(getServedClients()));
                                    textFieldItogOp2.setText(String.valueOf(summaryOp2));
                                    timeOp2.setText(String.valueOf(getSummaryTimeOp2()));
                                    setQueue2(getQueue2() - 1);
                                    if(getQueue2()==0){
                                        queueAnim.setBackground(Color.green);
                                        queueAnim.setText("O2: "+getQueue2());
                                    }
                                }
                            } else {
                                break;
                            }
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }
}

class operator3 extends Thread {

    public void process(JTextField textFieldItogOp3, JTextField timeOp3, JTextField queueAnim, JTextField opAnim, JTextField serv) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    int max = MainWindow.getProccessCallMax();
                    int min = MainWindow.getProccessCallMin();
                    Random random = new Random(System.currentTimeMillis());
                    NormalDistribution normalDistribution = new NormalDistribution();
                    while (true) {
                        if (isFlagEmerStop())
                            break;
                        try {
                            if (!isFlagWork()) {
                                if (getQueue3() > 0) {
                                    // int delay = ((int) (Math.random() * (max - min + 1) + min));
                                    //Random random = new Random(System.currentTimeMillis());
                                    //int delay = (random.nextInt(max - min + 1) + min) ;
                                    int delay  = (int)Math.abs(normalDistribution.generateGaussian((max+min)/2,max-(max+min)/2));
                                    //int delay = Math.abs((int)random.nextGaussian()*20+30)*getScale();
                                    setSummaryTimeOp3(getSummaryTimeOp3() + delay);
                                    opAnim.setBackground(Color.RED);
                                    opAnim.setText("Занят");
                                    sleep(delay * getScale());
                                    opAnim.setBackground(Color.GREEN);
                                    opAnim.setText("Свободен");
                                    summaryOp3++;
                                    setServedClients(getServedClients()+1);
                                    serv.setText("Обслуж: "+String.valueOf(getServedClients()));
                                    textFieldItogOp3.setText(String.valueOf(summaryOp3));
                                    timeOp3.setText(String.valueOf(getSummaryTimeOp3()));
                                    setQueue3(getQueue3() - 1);
                                    if(getQueue3()==0){
                                        queueAnim.setBackground(Color.green);
                                        queueAnim.setText("O3: "+getQueue3());
                                    }
                                }
                            } else {
                                break;
                            }
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }
}

class operator4 extends Thread {

    public void process(JTextField textFieldItogOp4, JTextField timeOp4, JTextField queueAnim, JTextField opAnim, JTextField serv) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    int max = MainWindow.getProccessCallMax();
                    int min = MainWindow.getProccessCallMin();
                    Random random = new Random(System.currentTimeMillis());
                    NormalDistribution normalDistribution = new NormalDistribution();
                    while (true) {
                        if (isFlagEmerStop())
                            break;
                        try {
                            if (!isFlagWork()) {
                                if (getQueue4() > 0) {
                                    // int delay = ((int) (Math.random() * (max - min + 1) + min));
                                    //Random random = new Random(System.currentTimeMillis());
                                    //int delay = (random.nextInt(max - min + 1) + min) ;
                                    int delay  = (int)Math.abs(normalDistribution.generateGaussian((max+min)/2,max-(max+min)/2));
                                    //int delay = Math.abs((int)random.nextGaussian()*20+30)*getScale();
                                    setSummaryTimeOp4(getSummaryTimeOp4() + delay);
                                    opAnim.setBackground(Color.RED);
                                    opAnim.setText("Занят");
                                    sleep(delay * getScale());
                                    opAnim.setBackground(Color.GREEN);
                                    opAnim.setText("Свободен");
                                    summaryOp4++;
                                    setServedClients(getServedClients()+1);
                                    serv.setText("Обслуж: "+String.valueOf(getServedClients()));
                                    textFieldItogOp4.setText(String.valueOf(summaryOp4));
                                    timeOp4.setText(String.valueOf(getSummaryTimeOp4()));
                                    setQueue4(getQueue4() - 1);
                                    if(getQueue4()==0){
                                        queueAnim.setBackground(Color.green);
                                        queueAnim.setText("O4: "+getQueue4());
                                    }
                                }
                            } else {
                                break;
                            }
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }
}

class operator5 extends Thread {

    public void process(JTextField textFieldItogOp5, JTextField timeOp5, JTextField queueAnim, JTextField opAnim, JTextField serv) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    int max = MainWindow.getProccessCallMax();
                    int min = MainWindow.getProccessCallMin();
                    Random random = new Random(System.currentTimeMillis());
                    NormalDistribution normalDistribution = new NormalDistribution();
                    while (true) {
                        if (isFlagEmerStop())
                            break;
                        try {
                            if (!isFlagWork()) {
                                if (getQueue5() > 0) {
                                    // int delay = ((int) (Math.random() * (max - min + 1) + min));
                                    //int delay = (random.nextInt(max - min + 1) + min) ;
                                    int delay  = (int)Math.abs(normalDistribution.generateGaussian((max+min)/2,max-(max+min)/2));
                                    //int delay = Math.abs((int)random.nextGaussian()*20+30)*getScale();
                                    setSummaryTimeOp5((getSummaryTimeOp5() + delay));
                                    //textFieldItogOp5.setBackground(Color.CYAN);
                                    opAnim.setBackground(Color.RED);
                                    opAnim.setText("Занят");
                                    sleep(delay * getScale());
                                    opAnim.setBackground(Color.GREEN);
                                    opAnim.setText("Свободен");

                                    //textFieldItogOp5.setBackground(Color.LIGHT_GRAY);
                                    summaryOp5++;
                                    setServedClients(getServedClients()+1);
                                    serv.setText("Обслуж: "+String.valueOf(getServedClients()));
                                    textFieldItogOp5.setText(String.valueOf(summaryOp5));
                                    timeOp5.setText(String.valueOf(getSummaryTimeOp5()));
                                    setQueue5(getQueue5() - 1);
                                    if(getQueue5()==0){
                                        queueAnim.setBackground(Color.green);
                                        queueAnim.setText("O5: "+getQueue5());
                                    }
                                }
                            } else {
                                break;
                            }
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }
}

/**
 * Класс для генерации случайных чисел по нормальному распределению.
 * За основу взят метод nextGaussian из класса Random. Были исправлены случаи, когда распределение выходило
 * за рамки [-1;1].
 */
class NormalDistribution{
    private double spare;
    private boolean hasSpare = false;

    /**
     * <p>Возвращает случайное число, полученное по нормальному распределению с математическим ожиданием 0 и дисперсией 1.</p>
     * <p>Пример:
     * <p>Возратить число полученное по нормальному распределению с мат. ожиданием 10 и дисперсией 5.
     * </p><p>
     * <b>nextGaussian(10,5)</b> - возратит случайное число [-5;25]. <p>При этом
     * Около 68 % значений из нормального распределения
     * находятся на расстоянии не более одного стандартного отклонения σ от среднего;
     * около 95 % значений лежат расстоянии не более двух стандартных отклонений; и 99,7 % не более трёх.</p>
     * </p></p>
     * @return случайное число
     */
    public synchronized double generateGaussian(double mean, double stdDev) {
        if (hasSpare) {
            hasSpare = false;
            return spare * stdDev + mean;
        } else {
            double u, v, s;
            do {
                u = Math.random() * 2 - 1;
                v = Math.random() * 2 - 1;
                s = u * u + v * v;
            } while (s >= 1 || s == 0);
            s = Math.sqrt(-2.0 * Math.log(s) / s);
            spare = v * s;
            hasSpare = true;
            return mean + stdDev * u * s;
        }
    }


}

class mainStart {
    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();
    }
}
