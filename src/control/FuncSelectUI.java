package control;

import java.awt.EventQueue;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import dao.DbConnection;
import dao.impl.GeneralDaoImpl;
import model.Employeelog;
import util.SharedState;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FuncSelectUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    FuncSelectUI frame = new FuncSelectUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public FuncSelectUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(10, 10, 426, 253);
        contentPane.add(panel);
        panel.setLayout(null);

        // LABEL
        JLabel lblTop = new JLabel("GG電腦 POS系統");
        lblTop.setFont(new Font("新細明體", Font.PLAIN, 22));
        lblTop.setBounds(10, 10, 225, 31);
        panel.add(lblTop);

        JLabel lblInfoTop = new JLabel("INFO TOP");
        lblInfoTop.setBounds(20, 47, 215, 15);
        panel.add(lblInfoTop);
        

        JLabel lblInfoTop_1 = new JLabel("TOP_1FUNC");
        lblInfoTop_1.setBounds(20, 64, 215, 15);
        panel.add(lblInfoTop_1);

        lblInfoTop.setText("你好"+SharedState.getEMname()+", 你的ID為"+SharedState.getEMID());
        lblInfoTop_1.setText("你的職等是"+SharedState.getEMlevel());

        
        JButton btnPOS = new JButton("POS");
        btnPOS.setBounds(20, 95, 85, 23);
        panel.add(btnPOS);
        
        JButton btnImport = new JButton("Import");
        btnImport.setBounds(20, 130, 85, 23);
        panel.add(btnImport);

        JButton btnManage = new JButton("Manage");
        btnManage.setBounds(20, 165, 85, 23);
        panel.add(btnManage);

        JButton btnLogout = new JButton("Logout");
        btnLogout.setBounds(20, 200, 85, 23);
        panel.add(btnLogout);
        
        Timer timer = new Timer(1000, t1 -> setTitle( new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));
        timer.start();

        switch (SharedState.getEMlevel()) {
            case "1":
                btnImport.setVisible(false);
                btnManage.setVisible(false);
                break;
            case "2":
                btnManage.setVisible(false);
                break;
            case "3":
                // No buttons hidden for level 3
                break;
        }
        
        GeneralDaoImpl dao = new GeneralDaoImpl();
        
        btnPOS.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                POSUI ui1 = new POSUI();
                ui1.setVisible(true);
                dispose();
            }
        });
        
        btnLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Update logout time and close the application
                Map<String, String> employeelogParams = new HashMap<>();
                employeelogParams.put("ELEMID", SharedState.getEMID());
                employeelogParams.put("ELflagRan", SharedState.getELflagRan()); // 使用ELflagRan来确定记录

                List<Employeelog> employeelogs = dao.queryByParameters("employeelog", employeelogParams, Employeelog.class);
                
                if (employeelogs.size() == 1) {
                    Employeelog employeelog = employeelogs.get(0);
                    employeelog.setELlogoutTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                    employeelog.setELflagRan("");
                    dao.update(employeelog);
                    
                    dispose();
                    DbConnection.closeConnection();
                    System.exit(0);
                }
            }
        });
        
        btnManage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Manage button action
                ManagerUI ui1 = new ManagerUI();
                ui1.setVisible(true);
                dispose();
            }
        });
        
        btnImport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Import button action
                ImpoUI ui1 = new ImpoUI();
                ui1.setVisible(true);
                dispose();
            }
        });
    }
}
