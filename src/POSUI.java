import java.awt.EventQueue;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumn;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.awt.Color;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
Q2:ARRAY如何隨時增減個數?
Q4:目前還是一步一步的寫法, 物件導向要如何思考?
BUG已解, 少加一清除text field步驟. Delay己加.
*
**********再請老師指教*****************.
*/	

public class POSUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfBarCodein;
	
	
//ADD NEED VAR FOR ALL
	PDData[] pd=new PDData[]{
        	new PDData("11111111", "LCD", "1004", "20"),
        	new PDData("22222222", "NB", "2004", "2"),
        	new	PDData("33333333", "MOUSE", "3004", "10"),
        	new PDData("44444444", "POWER", "4004", "10"),
        	new PDData("55555555", "CPU", "5004", "5")
        };
	Member[] mb=new Member[]{
        	new Member("5555", "張三", "0.9", "ADDR1"),
        	new Member("6666", "李四", "0.8", "ADDR2"),
        	new	Member("7777", "趙大", "0.7", "ADDR3"),
        	new Member("8888", "王二", "0.6", "ADDR4"),
        	new Member("9999", "老五", "0.5", "ADDR5")
        };
	//如何隨時增加個數?
	POData[] po=new POData[50];
	String stBBSout="                *****GG COMPUTER CO.*****\nDP ID\tPD Name\tQty\tPrice\n"
			+ "=======================================\n";
			
	double posum=0.0; 
	
//ADD NEED VAR FOR ALL
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					POSUI frame = new POSUI();
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
	public POSUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 561, 329);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 59, 213, 194);
		contentPane.add(scrollPane);
//ADD FOR JTABLE
        // Define your column names and data (similar to what you did before)
        Object[] columnNames = {"PD ID", "PD Name", "PD Price", "Stock"};
        Object[][] rowData = new Object[pd.length][4];
        for (int i = 0; i < pd.length; i++) {
            rowData[i][0] = pd[i].getPDID();
            rowData[i][1] = pd[i].getPDName();
            rowData[i][2] = pd[i].getPDPrice();
            rowData[i][3] = pd[i].getPDStock();
        }
        JTable table = new JTable(rowData, columnNames);
        // Set column widths
        TableColumn column = null;
        for (int i = 0; i < table.getColumnCount(); i++) {
            column = table.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(100); // ID
            } else if (i == 1) {
                column.setPreferredWidth(100); // Name
            } else if (i == 2) {
                column.setPreferredWidth(90); // Price
            } else if (i == 3) {
                column.setPreferredWidth(70); // Stock
            }
        }
        scrollPane.setViewportView(table);		
//ADD FOR JTABLE
        
		JFormattedTextField formattedTextField = new JFormattedTextField();
		scrollPane.setColumnHeaderView(formattedTextField);
		
		JLabel lbTop = new JLabel("請輸入商品條碼");
		
		lbTop.setBounds(10, 10, 241, 15);
		contentPane.add(lbTop);
    
		JCheckBox cbMinus = new JCheckBox("減項");
		cbMinus.setBounds(154, 27, 97, 23);
		contentPane.add(cbMinus);
		
		JTextArea taOutPt = new JTextArea();
		taOutPt.setBounds(255, 27, 281, 259);
		contentPane.add(taOutPt);
		
		JLabel lblNewLabel = new JLabel("銷售商品清單");
		lblNewLabel.setBounds(255, 10, 162, 15);
		contentPane.add(lblNewLabel);

		JButton btnSUM = new JButton("結帳");
//ADD FOR CLICK BT
		btnSUM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		     	if(btnSUM.getText().equals("結帳")) {    		
		     		stBBSout=stBBSout+"=======================================\n";
		     		stBBSout=stBBSout+"\t\t合計 : \t"+ String.format("%.1f",posum) +"\n";
		     		taOutPt.setText(stBBSout);
	    			btnSUM.setText("會員");
		    		lbTop.setText("請輸入會員條碼 若無會員直接按\"會員\"按鍵");
   		     		tfBarCodein.setText("");
   		     		//renew JT FOR MB DATA
   		     		//mb[1].renewJTable();
   		//ADD FOR JTABLE
   		         // Define your column names and data (similar to what you did before)
   		         Object[] columnNames = {"MB ID", "Name", "MB DISC", "MB ADDR"};
   		         Object[][] rowData = new Object[mb.length][4];
   		         for (int i = 0; i < mb.length; i++) {
   		             rowData[i][0] = mb[i].getMBID();
   		             rowData[i][1] = mb[i].getMBNAME();
   		             rowData[i][2] = mb[i].getMBDISC();
   		             rowData[i][3] = mb[i].getMBADDR();
   		         }
   		         JTable table = new JTable(rowData, columnNames);
   		         // Set column widths
   		         TableColumn column = null;
   		         for (int i = 0; i < table.getColumnCount(); i++) {
   		             column = table.getColumnModel().getColumn(i);
   		             if (i == 0) {
   		                 column.setPreferredWidth(70); // ID
   		             } else if (i == 1) {
   		                 column.setPreferredWidth(80); // Name
   		             } else if (i == 2) {
   		                 column.setPreferredWidth(100); // Price
   		             } else if (i == 3) {
   		                 column.setPreferredWidth(100); // Stock
   		             }
   		         }
   		//ADD FOR JTABLE
		     		scrollPane.setViewportView(table);		
   		         
		     	}else if(btnSUM.getText().equals("會員")) {   		
		     		stBBSout=stBBSout+"=======================================\n";
		     		stBBSout=stBBSout+"\t\t總計 : \t"+ String.format("%.1f",posum) +"\n";
		     		taOutPt.setText(stBBSout);
		     		btnSUM.setText("下一位");
		     		lbTop.setText("請輸入商品條碼");
   		     		tfBarCodein.setText("");

		     	}else if(btnSUM.getText().equals("下一位")) {   		
					posum=0.0;
	     			System.out.println("TEST1"+mb[1].getMBNAME());
					po=new POData[50];
					stBBSout="                *****GG COMPUTER CO.*****\nDP ID\tPD Name\tQty\tPrice\n"
							+ "=======================================\n";
					taOutPt.setText(" ");
					btnSUM.setText("結帳");
					lbTop.setText("請輸入商品條碼");
   		     		tfBarCodein.setText("");
   		     		
   	   	//ADD FOR JTABLE
   		         // Define your column names and data (similar to what you did before)
   		         Object[] columnNames = {"PD ID", "PD Name", "PD Price", "PD Stock"};
   		         Object[][] rowData = new Object[pd.length][4];
   		         for (int i = 0; i < pd.length; i++) {
   		             rowData[i][0] = pd[i].getPDID();
   		             rowData[i][1] = pd[i].getPDName();
   		             rowData[i][2] = pd[i].getPDPrice();
   		             rowData[i][3] = pd[i].getPDStock();
   		         }
   		         JTable table = new JTable(rowData, columnNames);
   		         // Set column widths
   		         TableColumn column = null;
   		         for (int i = 0; i < table.getColumnCount(); i++) {
   		             column = table.getColumnModel().getColumn(i);
   		             if (i == 0) {
   		                 column.setPreferredWidth(100); // ID
   		             } else if (i == 1) {
   		                 column.setPreferredWidth(100); // Name
   		             } else if (i == 2) {
   		                 column.setPreferredWidth(90); // Price
   		             } else if (i == 3) {
   		                 column.setPreferredWidth(70); // Stock
   		             }
   		         }
   		         scrollPane.setViewportView(table);		
   	  //ADD FOR JTABLE
				}
			}
		});
//ADD FOR CLICK BT		
		btnSUM.setBounds(10, 263, 85, 23);
		contentPane.add(btnSUM);

		tfBarCodein = new JTextField();
		tfBarCodein.setBackground(new Color(255, 255, 255));
//ADD FOR BARCODE
		tfBarCodein.addKeyListener(new KeyAdapter() {
			int POcont=0;
			@Override
        	public void keyTyped(KeyEvent e) {
				if(btnSUM.getText().equals("結帳")) {    		
					int flagPASS=0;
					if(tfBarCodein.getText().length()>7) {
						for(int i=0;i<pd.length;i++) {
       			     		if(tfBarCodein.getText().equals(pd[i].getPDID())){
       			     		//CK +/- PO
       			     			if(cbMinus.isSelected()) {
       			     				po[POcont] = new POData(pd[i].getPDID(),pd[i].getPDName(),"-1",pd[i].getPDPrice());
       			     			}
       			     			else {
       			     				po[POcont] = new POData(pd[i].getPDID(),pd[i].getPDName(),"1",pd[i].getPDPrice());
       			     			}
       			     		//CK +/- PO
       			     			stBBSout=stBBSout+po[POcont].getPOID()+"\t"+po[POcont].getPOName()+"\t"+po[POcont].getPOQty()+"\t"+po[POcont].getPOPrice()+"\n";		
       			     			taOutPt.setText(stBBSout);
       			     			posum=posum+Double.valueOf(po[POcont].getPOQty())*Double.valueOf(po[POcont].getPOPrice());
       			     			tfBarCodein.setText("");
       			     			POcont++;
       			     			flagPASS=1;
       			     			break;
       			     	}//endif barcadein eq pdid
        		}//endfor for scan all PD list
		    	if(flagPASS==0) {//if DATA cannot match
		    		tfBarCodein.setBackground(new Color(255, 0, 0));
					//lbTop.repaint();
					lbTop.setText("請輸入會員條碼:");
//delay				// 創建一個計時器，在一秒後更改標籤的文字，再在一秒後改回原始文字
				    Timer timer = new Timer();
				    // 創建一個 TimerTask，並實現 run 方法
				    TimerTask task = new TimerTask() {
				    	@Override
				    	public void run() {
				    		tfBarCodein.setBackground(new Color(255, 255, 255));
				        }
				    };
				    timer.schedule(task, 500);					        
//delay				// 設定計時器在 0.5 秒後執行一次任務
		    	}
		    	tfBarCodein.setText("");
		    	tfBarCodein.requestFocus();
		   }
		}	
		    if(btnSUM.getText().equals("會員")) {    		
				POcont=0;
		    	if(tfBarCodein.getText().length()>3) {
		    		for(int i=0;i<mb.length;i++) {
		    			if(tfBarCodein.getText().equals(mb[i].getMBID())){
		       	     		posum=posum*Double.valueOf(mb[i].getMBDISC());
	       		     		stBBSout=stBBSout+"會員 "+mb[i].getMBNAME()+" 您好, 您的特價折扣為: "+ mb[i].getMBDISC()+"\n";		
	    		     		stBBSout=stBBSout+"=======================================\n";
	       		     		stBBSout=stBBSout+"\t     折扣後的總價為: \t"+ String.format("%.1f",posum)+ " 元";		
	       		     		taOutPt.setText(stBBSout);
	       		     		btnSUM.setText("下一位");
	       		     		tfBarCodein.setText("");
	       		     		POcont++;
	       		     		tfBarCodein.setText("");
		       			}
		       			else {
				    		tfBarCodein.setBackground(new Color(0, 255, 0));
		//delay				// 創建一個計時器，在一秒後更改標籤的文字，再在一秒後改回原始文字
						    Timer timer = new Timer();
						    // 創建一個 TimerTask，並實現 run 方法
						    TimerTask task = new TimerTask() {
						    	@Override
						    	public void run() {
						    		tfBarCodein.setBackground(new Color(255, 255, 255));
						        }
						    };
						    timer.schedule(task, 500);					        
		//delay				// 設定計時器在 0.5 秒後執行一次任務
							lbTop.setText("請輸入商品條碼:");
					    	tfBarCodein.requestFocus();
		       			}
		       				
	        		}
	    				tfBarCodein.setText("");
		         }
		      }
		      
		      if(btnSUM.getText().equals("下一位")) {    		
		    	  tfBarCodein.setText("");
	   			  System.out.println("TEST1"+mb[1].getMBNAME());
		    		tfBarCodein.setBackground(new Color(50, 10, 0));
					//delay				// 創建一個計時器，在一秒後更改標籤的文字，再在一秒後改回原始文字
				    Timer timer = new Timer();
				    // 創建一個 TimerTask，並實現 run 方法
				    TimerTask task = new TimerTask() {
				    	@Override
				    	public void run() {
				    		tfBarCodein.setBackground(new Color(255, 255, 255));
				        }
				    };
				    timer.schedule(task, 500);					        
//delay				// 設定計時器在 0.5 秒後執行一次任務
					lbTop.setText("請輸入商品條碼:");
			    	tfBarCodein.requestFocus();

		      
		      
		      }
        	}
        });	
//ADD FOR CLICK BT
		tfBarCodein.setBounds(10, 28, 136, 21);
		contentPane.add(tfBarCodein);
		tfBarCodein.setColumns(100);

	}
}
