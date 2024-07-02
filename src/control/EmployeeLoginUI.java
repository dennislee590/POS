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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import dao.impl.GeneralDaoImpl;
import model.Employee;
import model.Employeelog;
import util.SharedState;

public class EmployeeLoginUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtId;
    private JTextField txtPw;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    EmployeeLoginUI frame = new EmployeeLoginUI();
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
    public EmployeeLoginUI() {
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
        JLabel lblTop = new JLabel("GG電腦 POS登錄系統");
        lblTop.setFont(new Font("新細明體", Font.PLAIN, 22));
        lblTop.setBounds(10, 10, 225, 31);
        panel.add(lblTop);

        JLabel lblInfoTop = new JLabel("");
        lblInfoTop.setBounds(20, 47, 215, 15);
        panel.add(lblInfoTop);

        JLabel lblInfoTop_1 = new JLabel("TOP_1");
        lblInfoTop_1.setBounds(20, 64, 215, 15);
        panel.add(lblInfoTop_1);
		lblInfoTop.setText("請輸入帳號和密碼");
		lblInfoTop_1.setText("");

        JLabel lblUserID = new JLabel("User ID");
        lblUserID.setBounds(20, 93, 67, 15);
        panel.add(lblUserID);

        txtId = new JTextField();
        txtId.setBounds(86, 90, 115, 21);
        panel.add(txtId);
        txtId.setColumns(10);

        JLabel lblPassWord = new JLabel("PassWord");
        lblPassWord.setBounds(20, 121, 67, 15);
        panel.add(lblPassWord);

        txtPw = new JTextField();
        txtPw.setColumns(10);
        txtPw.setBounds(86, 118, 115, 21);
        panel.add(txtPw);

        JButton btnLogin = new JButton("Login");
        btnLogin.setBounds(26, 161, 85, 23);
        panel.add(btnLogin);

        JButton btnLogout = new JButton("Logout");
        btnLogout.setBounds(128, 161, 85, 23);
        panel.add(btnLogout);

        JTextArea textArea = new JTextArea();
        textArea.setBounds(245, 10, 171, 243);
        panel.add(textArea);

        Timer timer = new Timer(1000, t1 -> setTitle(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));
		timer.start();

		txtId.setText("EM3");
		txtPw.setText("33");
        
        btnLogin.addActionListener(e -> {
            GeneralDaoImpl dao = new GeneralDaoImpl();

            if (!(txtId.getText().isEmpty() && txtPw.getText().isEmpty())) {
            	//new Params=null;
                Map<String, String> Params = new HashMap<>();
                System.out.println("AAA"+Params.getClass());
                Params.put("EMID", txtId.getText());
                Params.put("EMPW", txtPw.getText());
                List<Employee> results = dao.queryByParameters("employee", Params, Employee.class);

                if (!results.isEmpty()) {
                    Employee employee = results.get(0); // 應只有一個
                    SharedState.setEMID(employee.getEMID());
                    SharedState.setEMname(employee.getEMname());
                    SharedState.setEMlevel(employee.getEMlevel());
                    SharedState.setEMboss(employee.getEMboss());
                    SharedState.setELflagRan(String.valueOf((int)(Math.random() * 100000)));

                    Employeelog employeelog = new Employeelog(employee.getEMID(),
                            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),"", SharedState.getELflagRan());
                    dao.add(employeelog);
                    System.out.println("LOGIN OK, FUNCOPEN");

                    FuncSelectUI ui1 = new FuncSelectUI();
                    ui1.setVisible(true);
                    dispose();
                } else {
                    lblInfoTop_1.setText("帳號密碼錯誤, 請重新輸入");
                    System.out.println("Incorrect username or password");
                }
            }
        });
    }
}
