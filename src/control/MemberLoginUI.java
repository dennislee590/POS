package control;

import java.awt.Color;
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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import dao.impl.GeneralDaoImpl;
import model.Member;
import model.PDStock;
import util.SharedState;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MemberLoginUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private JTextField txtId;
	private JTextField txtPw;
	private JLabel lblInfoTop_1;
	private JTextField textName;
	private JTextField textEmail;
	private JTextField textPhone;
	private JTextField textAddress;
	private JTextField textHint;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MemberLoginUI frame = new MemberLoginUI();
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
	public MemberLoginUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
		JLabel lblTop = new JLabel("GG電腦 POS系統 會員登錄");
		lblTop.setFont(new Font("新細明體", Font.PLAIN, 22));
		lblTop.setBounds(10, 10, 225, 31);
		panel.add(lblTop);

		JLabel lblInfoTop = new JLabel("INFO TOP");
		lblInfoTop.setBounds(20, 47, 215, 15);
		panel.add(lblInfoTop);

		lblInfoTop_1 = new JLabel("TOP_1");
		lblInfoTop_1.setBounds(20, 64, 215, 15);
		panel.add(lblInfoTop_1);

		lblInfoTop.setText("請輸入帳號和密碼");
		lblInfoTop_1.setText("  新會員請按\"註冊會員\"");

		JLabel lblUserID = new JLabel("User ID");
		lblUserID.setBounds(20, 93, 67, 15);
		panel.add(lblUserID);

		// lblUserID
		txtId = new JTextField();
		txtId.setText("mb1");
		txtId.setBounds(86, 90, 115, 21);
		panel.add(txtId);
		txtId.setColumns(10);

		JLabel lblPassWord = new JLabel("PassWord");
		lblPassWord.setBounds(20, 121, 67, 15);
		panel.add(lblPassWord);

		// lblPassWord
		txtPw = new JTextField();
		txtPw.setText("");
		txtPw.setColumns(10);
		txtPw.setBounds(86, 118, 115, 21);
		panel.add(txtPw);

		JLabel lblMemberNew = new JLabel("註冊會員");
		lblMemberNew.setForeground(new Color(0, 128, 255));
		lblMemberNew.setBounds(38, 166, 67, 15);
		panel.add(lblMemberNew);

		JLabel lblIDCheck = new JLabel("帳號重複查核");
		lblIDCheck.setVisible(false);
		lblIDCheck.setForeground(new Color(0, 128, 255));
		lblIDCheck.setBounds(86, 114, 149, 15);
		panel.add(lblIDCheck);

		textName = new JTextField();
		textName.setEnabled(false);
		textName.setText("");
		textName.setColumns(10);
		textName.setBounds(301, 87, 115, 21);
		panel.add(textName);

		JLabel lblName = new JLabel("姓名:");
		lblName.setEnabled(false);
		lblName.setBounds(235, 90, 67, 15);
		panel.add(lblName);

		textEmail = new JTextField();
		textEmail.setEnabled(false);
		textEmail.setText("");
		textEmail.setColumns(10);
		textEmail.setBounds(301, 118, 115, 21);
		panel.add(textEmail);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setEnabled(false);
		lblEmail.setBounds(235, 121, 67, 15);
		panel.add(lblEmail);

		textPhone = new JTextField();
		textPhone.setEnabled(false);
		textPhone.setText("");
		textPhone.setColumns(10);
		textPhone.setBounds(301, 149, 115, 21);
		panel.add(textPhone);

		JLabel lblPhone = new JLabel("電話:");
		lblPhone.setEnabled(false);
		lblPhone.setBounds(235, 152, 67, 15);
		panel.add(lblPhone);

		textAddress = new JTextField();
		textAddress.setText("");
		textAddress.setEnabled(false);
		textAddress.setColumns(10);
		textAddress.setBounds(301, 177, 115, 21);
		panel.add(textAddress);

		JLabel lblAddress = new JLabel("地址:");
		lblAddress.setEnabled(false);
		lblAddress.setBounds(235, 180, 67, 15);
		panel.add(lblAddress);

		textHint = new JTextField();
		textHint.setEnabled(false);
		textHint.setText("");
		textHint.setColumns(10);
		textHint.setBounds(301, 205, 115, 21);
		panel.add(textHint);

		JLabel lblHint = new JLabel("提示:");
		lblHint.setEnabled(false);
		lblHint.setBounds(235, 208, 67, 15);
		panel.add(lblHint);

		JLabel lblIfNeeded = new JLabel("選填:");
		lblIfNeeded.setEnabled(false);
		lblIfNeeded.setBounds(277, 64, 67, 15);
		panel.add(lblIfNeeded);

		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(20, 191, 85, 23);
		panel.add(btnLogin);

		JButton btnNew = new JButton("註冊");
		btnNew.setBounds(126, 191, 85, 23);
		panel.add(btnNew);
		btnNew.setEnabled(false);

		JButton btnGetUp = new JButton("放棄");
		btnGetUp.setVisible(false);
		btnGetUp.setBounds(126, 162, 85, 23);
		panel.add(btnGetUp);

		Timer timer = new Timer(1000, t1 -> setTitle(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));
		timer.start();

		/*****************/
		GeneralDaoImpl dao = new GeneralDaoImpl();

		btnLogin.addActionListener(e -> {
			if (!txtId.getText().trim().isEmpty() && !txtPw.getText().trim().isEmpty()) {
					//1.H 2.H
				Map<String, String> memberParams;
				memberParams = null;
				memberParams = new HashMap<>();
				memberParams.put("MBID", txtId.getText().trim());// .trim()去除尾部的空白
				memberParams.put("MBPW", txtPw.getText().trim());
				List<Member> members;
				members = null;
				members = dao.queryByParameters("member", memberParams, Member.class);
				if (!members.isEmpty()) {
					Member member = members.get(0); // Should only have one
					// Update shared state
					SharedState.setMBID(member.getMBID());
					SharedState.setMBName(member.getMBname());
					SharedState.setMBLevel(member.getMBlevel());

					System.out.println("MBLOGIN OK, BACK TO POSUI");
					System.out.println(SharedState.getMBName() + SharedState.getMBLevel());

					POSUI ui1 = new POSUI();
					ui1.setVisible(true);
					dispose();
				}
			}
			if (!txtId.getText().trim().isEmpty()) {
				Map<String, String>memberParams;
				memberParams = null;
				memberParams = new HashMap<>();
				memberParams.put("MBID", txtId.getText().trim());// .trim()去除尾部的空白
				List<Member> members;
				members = null;
				members = dao.queryByParameters("member", memberParams, Member.class);

				if (!members.isEmpty()) {
					lblInfoTop.setText("密碼錯誤, 請看提示重新輸入");
					lblInfoTop_1.setText("密碼提示帳為: " + members.get(0).getMBhint());
				}else {
					lblInfoTop.setText("帳號密碼錯誤, 請重新輸入");
					lblInfoTop_1.setText("或請註冊");
				}
			}
			
		});

		lblMemberNew.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblUserID.setEnabled(true);
				txtId.setEnabled(true);
				lblPassWord.setVisible(false);
				txtPw.setVisible(false);
				// btnLogin.setEnabled(false);
				lblIDCheck.setVisible(true);
				lblMemberNew.setEnabled(false);
				btnLogin.setEnabled(false);

				lblInfoTop_1.setText("檢查userID是否重複");
				
				if (lblIDCheck.isVisible()) {
					Map<String, String> memberParams;
					memberParams = null;
					memberParams = new HashMap<>();
					memberParams.put("MBID", txtId.getText().trim());// .trim()去除尾部的空白
					List<Member> members;
					members = null;
					System.out.print("OO"+txtId.getText().trim());
					members = dao.queryByParameters("member", memberParams, Member.class);
					System.out.print("A "+ (members.isEmpty()));
					System.out.print("AA"+ (members.isEmpty()||txtId.getText().trim().isEmpty()));
					System.out.print("$$   "+ txtId.getText().trim());
					if (members.isEmpty()&&txtId.getText().trim().isEmpty()) {
						System.out.print("CC" + members.toString());
						lblIDCheck.setText("帳號可用, 請確認");
					} else {
						System.out.print("DD" + members.toString());
						lblIDCheck.setText("帳號不可用,按我離開");
					}
				}
			}
		});
		
		txtId.getDocument().addDocumentListener(new DocumentListener() {
		    @Override
		    public void insertUpdate(DocumentEvent e) {
				if (lblIDCheck.isVisible()) {
					Map<String, String> memberParams;
					memberParams = null;
					memberParams = new HashMap<>();
					memberParams.put("MBID", txtId.getText().trim());// .trim()去除尾部的空白
					List<Member> members;
					members = null;
					members = dao.queryByParameters("member", memberParams, Member.class);
					if (!members.isEmpty()||txtId.getText().trim().isEmpty()) {
						lblIDCheck.setText("帳號不可用,按我離開");
						//btnLogin.setEnabled(false);
					}else {
						lblIDCheck.setText("帳號可用,確認請按");
						//btnLogin.setEnabled(false);
					}
				}
		    }

		    @Override
		    public void removeUpdate(DocumentEvent e) {
				if (lblIDCheck.isVisible()) {
					Map<String, String> memberParams;
					memberParams = null;
					memberParams = new HashMap<>();
					memberParams.put("MBID", txtId.getText().trim());// .trim()去除尾部的空白
					List<Member> members;
					members = null;
					members = dao.queryByParameters("member", memberParams, Member.class);
					if (!members.isEmpty()||txtId.getText().trim().isEmpty()) {
						lblIDCheck.setText("帳號不可用,按我離開");
						//btnLogin.setEnabled(false);
					}else {
						lblIDCheck.setText("帳號可用,確認請按");
						//btnLogin.setEnabled(false);
					}
				}
		    }

			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		lblIDCheck.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (lblIDCheck.getText()=="帳號可用,確認請按") {
					System.out.print("q ");
					lblName.setEnabled(true);
					textName.setEnabled(true);
					lblEmail.setEnabled(true);
					textEmail.setEnabled(true);
					lblPhone.setEnabled(true);
					textPhone.setEnabled(true);
					lblAddress.setEnabled(true);
					textAddress.setEnabled(true);
					lblHint.setEnabled(true);
					textHint.setEnabled(true);
					lblIfNeeded.setEnabled(true);
					lblMemberNew.setEnabled(false);
					lblIDCheck.setVisible(false);
					txtPw.setVisible(true);
					lblPassWord.setVisible(true);
					txtId.setEnabled(false);
					btnLogin.setEnabled(false);
					btnNew.setEnabled(true);
					btnGetUp.setVisible(true);
				}else {
					System.out.print("q ");
					lblName.setEnabled(false);
					textName.setEnabled(false);
					lblEmail.setEnabled(false);
					textEmail.setEnabled(false);
					lblPhone.setEnabled(false);
					textPhone.setEnabled(false);
					lblAddress.setEnabled(false);
					textAddress.setEnabled(false);
					lblHint.setEnabled(false);
					textHint.setEnabled(false);
					lblIfNeeded.setEnabled(false);
					lblMemberNew.setEnabled(true);
					lblIDCheck.setVisible(false);
					txtPw.setVisible(true);
					lblPassWord.setVisible(true);
					txtId.setEnabled(true);
					btnLogin.setEnabled(true);
					btnNew.setEnabled(false);
					btnGetUp.setVisible(false);
				}
			
			}
		});
		btnNew.addActionListener(e -> {

			Member newMember = new Member(textName.getText(), txtId.getText(), txtPw.getText(), textEmail.getText(),
					textPhone.getText(), textAddress.getText(), textHint.getText(), "1", "");
			dao.add(newMember);

			lblInfoTop_1.setText("註冊成功, 請重新登錄");

			lblName.setEnabled(false);
			textName.setEnabled(false);
			lblEmail.setEnabled(false);
			textEmail.setEnabled(false);
			lblPhone.setEnabled(false);
			textPhone.setEnabled(false);
			lblAddress.setEnabled(false);
			textAddress.setEnabled(false);
			lblHint.setEnabled(false);
			textHint.setEnabled(false);
			lblIfNeeded.setEnabled(false);

			txtId.setEnabled(true);
			btnLogin.setEnabled(true);
			btnNew.setEnabled(false);

			btnGetUp.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					POSUI ui1 = new POSUI();
					ui1.setVisible(true);
					dispose();
				}
			});
		
		});
		btnGetUp.addActionListener(e -> {
			lblName.setEnabled(false);
			textName.setEnabled(false);
			lblEmail.setEnabled(false);
			textEmail.setEnabled(false);
			lblPhone.setEnabled(false);
			textPhone.setEnabled(false);
			lblAddress.setEnabled(false);
			textAddress.setEnabled(false);
			lblHint.setEnabled(false);
			textHint.setEnabled(false);
			lblIfNeeded.setEnabled(false);
			lblMemberNew.setEnabled(true);
			lblIDCheck.setVisible(false);
			txtPw.setVisible(true);
			lblPassWord.setVisible(true);
			txtId.setEnabled(true);
			btnLogin.setEnabled(true);
			btnNew.setEnabled(false);
			btnGetUp.setVisible(false);

		});

		// 添加窗口监听器
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				POSUI ui1 = new POSUI();
				ui1.setVisible(true);
			}
		});
	}
}
