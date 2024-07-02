package control;

import model.Employee;
import model.Employeelog;
import model.Member;
import model.PDStock;
import model.PITitle;
import model.PITitleItem;
import model.POTitle;
import model.POTitleItem;
import dao.impl.GeneralDaoImpl;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class ManagerUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private GeneralDaoImpl dao;

    /**
     * 启动应用程序。
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // 设置字体
                    UIManager.put("Table.font", new Font("SansSerif", Font.PLAIN, 12));
                    UIManager.put("TableHeader.font", new Font("SansSerif", Font.BOLD, 12));

                    ManagerUI frame = new ManagerUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 创建框架。
     */
    public ManagerUI() {
        dao = new GeneralDaoImpl();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 812, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JPanel panel = new JPanel();
        panel.setBounds(10, 10, 788, 540);
        contentPane.add(panel);
        panel.setLayout(null);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 76, 760, 454);
        panel.add(scrollPane);
        
        table = new JTable();
        scrollPane.setViewportView(table);
        
        JButton btnEmployeeManagement = new JButton("員工管理");
        btnEmployeeManagement.setBounds(10, 10, 100, 30);
        panel.add(btnEmployeeManagement);
        
        JButton btnEmployeeAttendance = new JButton("員工出勤");
        btnEmployeeAttendance.setBounds(120, 10, 100, 30);
        panel.add(btnEmployeeAttendance);
        
        JButton btnImportRecords = new JButton("進貨記錄");
        btnImportRecords.setBounds(230, 10, 100, 30);
        panel.add(btnImportRecords);
        
        JButton btnStockInfo = new JButton("庫存信息");
        btnStockInfo.setBounds(340, 10, 100, 30);
        panel.add(btnStockInfo);
        
        JButton btnCustomerManagement = new JButton("客户管理");
        btnCustomerManagement.setBounds(450, 10, 100, 30);
        panel.add(btnCustomerManagement);
        
        JButton btnSalesRecords = new JButton("銷售記錄");
        btnSalesRecords.setBounds(560, 10, 100, 30);
        panel.add(btnSalesRecords);
        
        JButton btnSave = new JButton("保存");
        btnSave.setBounds(670, 10, 100, 30);
        panel.add(btnSave);
        
        JButton btnBack = new JButton("回上一層");
        btnBack.setBounds(670, 43, 100, 30);
        panel.add(btnBack);

        btnEmployeeManagement.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadEmployeeData();
            }
        });

        btnEmployeeAttendance.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadEmployeeLogData();
            }
        });

        btnImportRecords.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadImportData();
            }
        });

        btnStockInfo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadPDStockData();
            }
        });

        btnCustomerManagement.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadMemberData();
            }
        });

        btnSalesRecords.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadSalesData();
            }
        });

        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveData();
            }
        });

        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new FuncSelectUI().setVisible(true);
            }
        });
    }

    private void loadEmployeeData() {
        List<Employee> employees = dao.queryByParameters("employee", new HashMap<>(), Employee.class);

        String[] columnNames = {"id", "emname", "userID", "password", "level", "boss"};
        List<Object[]> data = employees.stream()
                .map(emp -> new Object[]{emp.getId(), emp.getEMname(), emp.getEMID(), emp.getEMPW(), emp.getEMlevel(), emp.getEMboss()})
                .collect(Collectors.toList());

        DefaultTableModel model = new DefaultTableModel(data.toArray(new Object[0][]), columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        };
        table.setModel(model);

        setColumnWidths();
    }

    private void loadMemberData() {
        List<Member> members = dao.queryByParameters("member", new HashMap<>(), Member.class);

        String[] columnNames = {"id", "mbname", "mbID", "mbPW", "email", "phone", "address", "hint", "mblevel"};
        List<Object[]> data = members.stream()
                .map(mb -> new Object[]{mb.getId(), mb.getMBname(), mb.getMBID(), mb.getMBPW(), mb.getMBemail(), mb.getMBphone(), mb.getMBaddress(), mb.getMBhint(), mb.getMBlevel()})
                .collect(Collectors.toList());

        DefaultTableModel model = new DefaultTableModel(data.toArray(new Object[0][]), columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        };
        table.setModel(model);

        setColumnWidths();
    }

    private void loadEmployeeLogData() {
        List<Employeelog> logs = dao.queryByParameters("employeelog", new HashMap<>(), Employeelog.class);

        String[] columnNames = {"id", "ELEMID", "ELloginTime", "ELlogoutTime", "ELflagRan"};
        List<Object[]> data = logs.stream()
                .map(log -> new Object[]{log.getId(), log.getELEMID(), log.getELloginTime(), log.getELlogoutTime(), log.getELflagRan()})
                .collect(Collectors.toList());

        DefaultTableModel model = new DefaultTableModel(data.toArray(new Object[0][]), columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        };
        table.setModel(model);

        setColumnWidths();
    }

    private void loadPDStockData() {
        List<PDStock> stocks = dao.queryByParameters("pdstock", new HashMap<>(), PDStock.class);

        String[] columnNames = {"id", "PDID", "PDname", "PDdesc", "PDbarcode", "PDprice", "PDqty"};
        List<Object[]> data = stocks.stream()
                .map(stock -> new Object[]{stock.getId(), stock.getPDID(), stock.getPDname(), stock.getPDdesc(), stock.getPDbarcode(), stock.getPDprice(), stock.getPDqty()})
                .collect(Collectors.toList());

        DefaultTableModel model = new DefaultTableModel(data.toArray(new Object[0][]), columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        };
        table.setModel(model);

        setColumnWidths();
    }

    private void loadImportData() {
        // 加载进货记录
        List<PITitle> titles = dao.queryByParameters("pititle", new HashMap<>(), PITitle.class);
        List<PITitleItem> items = dao.queryByParameters("pititleitem", new HashMap<>(), PITitleItem.class);

        String[] columnNames = {"PINO", "Date", "Employee", "Approval", "PIPDID", "PIqty"};
        List<Object[]> data = titles.stream()
                .flatMap(title -> items.stream()
                        .filter(item -> item.getPINO().equals(title.getPINO()))
                        .map(item -> new Object[]{title.getPINO(), title.getPIdate(), title.getPIemployee(), title.getPIapproval(), item.getPIPDID(), item.getPIqty()}))
                .collect(Collectors.toList());

        DefaultTableModel model = new DefaultTableModel(data.toArray(new Object[0][]), columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        };
        table.setModel(model);

        setColumnWidths();
    }

    private void loadSalesData() {
        // 加载销售记录
        List<POTitle> titles = dao.queryByParameters("potitle", new HashMap<>(), POTitle.class);
        List<POTitleItem> items = dao.queryByParameters("potitleitem", new HashMap<>(), POTitleItem.class);

        String[] columnNames = {"PONO", "Date", "Employee", "Member", "POPDID", "POqty"};
        List<Object[]> data = titles.stream()
                .flatMap(title -> items.stream()
                        .filter(item -> item.getPONO().equals(title.getPONO()))
                        .map(item -> new Object[]{title.getPONO(), title.getPOdate(), title.getPOemployee(), title.getPOmember(), item.getPOID(), item.getPOqty()}))
                .collect(Collectors.toList());

        DefaultTableModel model = new DefaultTableModel(data.toArray(new Object[0][]), columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        };
        table.setModel(model);

        setColumnWidths();
    }

    private void saveData() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        String firstColumn = model.getColumnName(1); // 第一个可编辑列名

        if ("emname".equals(firstColumn)) {
            // 保存员工数据
            for (int i = 0; i < model.getRowCount(); i++) {
                Integer id = (Integer) model.getValueAt(i, 0);
                String name = (String) model.getValueAt(i, 1);
                String userID = (String) model.getValueAt(i, 2);
                String password = (String) model.getValueAt(i, 3);
                String level = (String) model.getValueAt(i, 4);
                String boss = (String) model.getValueAt(i, 5);

                Employee employee = new Employee();
                employee.setId(id);
                employee.setEMname(name);
                employee.setEMID(userID);
                employee.setEMPW(password);
                employee.setEMlevel(level);
                employee.setEMboss(boss);

                dao.update(employee);
            }
        } else if ("mbname".equals(firstColumn)) {
            // 保存成员数据
            for (int i = 0; i < model.getRowCount(); i++) {
                Integer id = (Integer) model.getValueAt(i, 0);
                String mbname = (String) model.getValueAt(i, 1);
                String mbuserID = (String) model.getValueAt(i, 2);
                String mbpassword = (String) model.getValueAt(i, 3);
                String email = (String) model.getValueAt(i, 4);
                String phone = (String) model.getValueAt(i, 5);
                String address = (String) model.getValueAt(i, 6);
                String hint = (String) model.getValueAt(i, 7);
                String mblevel = (String) model.getValueAt(i, 8);

                Member member = new Member();
                member.setId(id);
                member.setMBname(mbname);
                member.setMBID(mbuserID);
                member.setMBPW(mbpassword);
                member.setMBemail(email);
                member.setMBphone(phone);
                member.setMBaddress(address);
                member.setMBhint(hint);
                member.setMBlevel(mblevel);

                dao.update(member);
            }
        } else if ("ELEMID".equals(firstColumn)) {
            // 保存员工出勤数据
            for (int i = 0; i < model.getRowCount(); i++) {
                Integer id = (Integer) model.getValueAt(i, 0);
                String ELEMID = (String) model.getValueAt(i, 1);
                String ELloginTime = (String) model.getValueAt(i, 2);
                String ELlogoutTime = (String) model.getValueAt(i, 3);
                String ELflagRan = (String) model.getValueAt(i, 4);

                Employeelog employeelog = new Employeelog();
                employeelog.setId(id);
                employeelog.setELEMID(ELEMID);
                employeelog.setELloginTime(ELloginTime);
                employeelog.setELlogoutTime(ELlogoutTime);
                employeelog.setELflagRan(ELflagRan);

                dao.update(employeelog);
            }
        } else if ("PDID".equals(firstColumn)) {
            // 保存库存信息
            for (int i = 0; i < model.getRowCount(); i++) {
                Integer id = (Integer) model.getValueAt(i, 0);
                String PDID = (String) model.getValueAt(i, 1);
                String PDname = (String) model.getValueAt(i, 2);
                String PDdesc = (String) model.getValueAt(i, 3);
                String PDbarcode = (String) model.getValueAt(i, 4);
                Double PDprice = Double.valueOf(model.getValueAt(i, 5).toString());
                Double PDqty = Double.valueOf(model.getValueAt(i, 6).toString());

                PDStock pdStock = new PDStock();
                pdStock.setId(id);
                pdStock.setPDID(PDID);
                pdStock.setPDname(PDname);
                pdStock.setPDdesc(PDdesc);
                pdStock.setPDbarcode(PDbarcode);
                pdStock.setPDprice(PDprice);
                pdStock.setPDqty(PDqty);

                dao.update(pdStock);            
            }
        } else if ("PINO".equals(firstColumn)) {
            // 保存进货记录
            for (int i = 0; i < model.getRowCount(); i++) {
                String pino = (String) model.getValueAt(i, 0);
                String date = (String) model.getValueAt(i, 1);
                String employee = (String) model.getValueAt(i, 2);
                String approval = (String) model.getValueAt(i, 3);
                String pipdid = (String) model.getValueAt(i, 4);
                Double piqty = Double.valueOf(model.getValueAt(i, 5).toString());

                PITitle piTitle = new PITitle();
                piTitle.setPINO(pino);
                piTitle.setPIdate(date);
                piTitle.setPIemployee(employee);
                piTitle.setPIapproval(approval);
                dao.update(piTitle);

                PITitleItem piTitleItem = new PITitleItem();
                piTitleItem.setPINO(pino);
                piTitleItem.setPIPDID(pipdid);
                piTitleItem.setPIqty(piqty);
                dao.update(piTitleItem);
            }
        } else if ("PONO".equals(firstColumn)) {
            // 保存销售记录
            for (int i = 0; i < model.getRowCount(); i++) {
                String pono = (String) model.getValueAt(i, 0);
                String date = (String) model.getValueAt(i, 1);
                String employee = (String) model.getValueAt(i, 2);
                String member = (String) model.getValueAt(i, 3);
                String popdid = (String) model.getValueAt(i, 4);
                Double poqty = Double.valueOf(model.getValueAt(i, 5).toString());

                POTitle poTitle = new POTitle();
                poTitle.setPONO(pono);
                poTitle.setPOdate(date);
                poTitle.setPOemployee(employee);
                poTitle.setPOmember(member);
                dao.update(poTitle);

                POTitleItem poTitleItem = new POTitleItem();
                poTitleItem.setPONO(pono);
                poTitleItem.setPOID(popdid);
                poTitleItem.setPOqty(poqty);
                dao.update(poTitleItem);
            }
        }

        // 重新加载数据以确保更改被反映
        reloadTableData(firstColumn);
    }

    private void reloadTableData(String firstColumn) {
        if ("emname".equals(firstColumn)) {
            loadEmployeeData();
        } else if ("mbname".equals(firstColumn)) {
            loadMemberData();
        } else if ("ELEMID".equals(firstColumn)) {
            loadEmployeeLogData();
        } else if ("PDID".equals(firstColumn)) {
            loadPDStockData();
        } else if ("PINO".equals(firstColumn)) {
            loadImportData();
        } else if ("PONO".equals(firstColumn)) {
            loadSalesData();
        }
    }

    private void setColumnWidths() {
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(100);
        }
    }

    private void goBack() {
        dispose();
        new FuncSelectUI().setVisible(true);
    }
}
