package control;

import dao.DbConnection;
import dao.impl.GeneralDaoImpl;
import model.PDStock;
import model.POTitle;
import model.POTitleItem;
import util.SharedState;
import util.TableUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.Timer;

public class POSUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField tfBarCodein;
    private JTable table;
    private JTextArea taOutPt;
    private String stBBSout;
    private DefaultTableModel tableModel;
    private List<PDStock> pdstocks; // 成員變量
    private Map<String, Integer> productQuantities = new HashMap<>(); // 记录每个商品的数量
    private JButton btnSUM; // 结账按钮
    private boolean isCheckoutComplete = false; // 记录是否结账完成
    String stBBSout1 = "                *****GG COMPUTER CO.*****\nDP ID\tPD Name\tQty\tPrice\n"
            + "顧客: " + SharedState.getMBName() + "\t 服務員: " + SharedState.getEMname() + "\n"
            + "=======================================\n";

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
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 改为DISPOSE_ON_CLOSE，不是EXIT_ON_CLOSE
        setBounds(100, 100, 805, 422);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        addWindowListener(new WindowAdapter() { // 添加窗口监听器
            @Override
            public void windowClosing(WindowEvent e) {
            	FuncSelectUI funcSelectUI = new FuncSelectUI();
                funcSelectUI.setVisible(true);
            }
        });

        JLabel lblTop = new JLabel("GG電腦 POS登錄系統");
        lblTop.setFont(new Font("新細明體", Font.PLAIN, 22));
        lblTop.setBounds(10, 10, 225, 31);
        contentPane.add(lblTop);

        JLabel lblInfoTop = new JLabel("INFO TOP");
        lblInfoTop.setBounds(20, 47, 215, 15);
        contentPane.add(lblInfoTop);

        // Mocking the shared state data
        lblInfoTop.setText(
                "UserID: " + SharedState.getEMID() + ", Name: " + SharedState.getEMname() + ", MBName: " + SharedState.getMBName() + ", MBLevel: " + "2");

        JLabel lblInfoTop_1 = new JLabel("TOP_1");
        lblInfoTop_1.setBounds(20, 64, 215, 15);
        contentPane.add(lblInfoTop_1);

        JLabel lblBarcode = new JLabel("請輸入商品條碼");
        lblBarcode.setBounds(128, 107, 97, 15);
        contentPane.add(lblBarcode);

        taOutPt = new JTextArea();
        taOutPt.setBounds(379, 141, 397, 201);
        contentPane.add(taOutPt);

        JLabel lblNewLabel = new JLabel("銷售商品清單");
        lblNewLabel.setBounds(408, 107, 162, 15);
        contentPane.add(lblNewLabel);

        btnSUM = new JButton("結帳");
        btnSUM.setBounds(10, 352, 85, 23);
        contentPane.add(btnSUM);

        JButton btnMB = new JButton("會員");
        btnMB.setBounds(20, 100, 85, 23);
        contentPane.add(btnMB);

        tfBarCodein = new JTextField();
        tfBarCodein.setBounds(233, 104, 136, 21);
        tfBarCodein.setBackground(new Color(255, 255, 255));
        contentPane.add(tfBarCodein);
        tfBarCodein.setColumns(100);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 141, 359, 201);
        contentPane.add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);

        // 查询所有的 PDStock 数据并放入 JTable 中
        GeneralDaoImpl dao = new GeneralDaoImpl();

        Map<String, String> pdstockParams = new HashMap<>();
        List<PDStock> pdstocks = dao.queryByParameters("pdstock", pdstockParams, PDStock.class);

        // 将数据转换为 Object 数组列表
        List<Object[]> data = new ArrayList<>();
        for (PDStock stock : pdstocks) {
            Object[] rowData = new Object[]{stock.getPDID(), stock.getPDname(), stock.getPDdesc(),
                    stock.getPDbarcode(), stock.getPDprice(), stock.getPDqty()};
            data.add(rowData);
        }

        // 定义列名
        String[] columnNames = {"編號", "名稱", "說明", "條碼", "價格", "庫存"};

        // 使用 TableUtils 将数据放入 JTable 并自动调整列宽
        TableUtils.putDataIntoTable(data, table, columnNames);

        // 按钮事件处理
        btnMB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MemberLoginUI ui1 = new MemberLoginUI();
                ui1.setVisible(true);
                dispose();
            }
        });

        btnSUM.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isCheckoutComplete) {
                    resetForNextCustomer();
                } else {
                    if (!SharedState.getMBID().isEmpty()) {
                        handleCheckout();
                    } else {
                        JOptionPane.showMessageDialog(contentPane, "请先登录会员!");
                    }
                }
            }
        });

        tfBarCodein.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                handleBarcodeIfComplete();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                handleBarcodeIfComplete();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // 不需要在这里处理
            }
        });
    }

    private void handleBarcodeIfComplete() {
        if (SharedState.getMBID().isEmpty()) {
            JOptionPane.showMessageDialog(contentPane, "請先登錄會員!");
            return;
        }
        
        String barcode = tfBarCodein.getText();
        if (barcode.length() >= 8) {
            handleBarcode(barcode);
            SwingUtilities.invokeLater(() -> tfBarCodein.setText("")); // 清空文本框
        }
    }

    private void handleBarcode(String barcode) {
        System.out.println("處理條碼: " + barcode);

        int rowCount = table.getRowCount();
        boolean found = false;
        for (int i = 0; i < rowCount; i++) {
            String pdBarcode = (String) table.getValueAt(i, 3); // 條碼欄位索引為3
            if (barcode.equals(pdBarcode)) {
                String pdID = (String) table.getValueAt(i, 0);
                String pdName = (String) table.getValueAt(i, 1);
                String pdDesc = (String) table.getValueAt(i, 2);
                String pdPrice = table.getValueAt(i, 4).toString();

                productQuantities.put(pdID, productQuantities.getOrDefault(pdID, 0) + 1);
                String pdQty = productQuantities.get(pdID).toString();

                updateOutputTextArea();

                found = true;
                break;
            }
        }
        if (!found) {
            tfBarCodein.setBackground(Color.RED);
            // 延时恢复背景颜色
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    tfBarCodein.setBackground(Color.WHITE);
                }
            }, 500);
        }
    }

    private void updateOutputTextArea() {
        StringBuilder outputText = new StringBuilder(stBBSout1);
        for (Map.Entry<String, Integer> entry : productQuantities.entrySet()) {
            for (int i = 0; i < table.getRowCount(); i++) {
                if (table.getValueAt(i, 0).equals(entry.getKey())) {
                    String pdID = (String) table.getValueAt(i, 0);
                    String pdName = (String) table.getValueAt(i, 1);
                    String pdPrice = table.getValueAt(i, 4).toString();
                    String pdQty = entry.getValue().toString();
                    outputText.append(pdID).append("\t").append(pdName).append("\t").append(pdQty).append("\t").append(pdPrice).append("\n");
                    break;
                }
            }
        }
        taOutPt.setText(outputText.toString());
    }

    private void handleCheckout() {
        GeneralDaoImpl dao = new GeneralDaoImpl();

        // 计算总金额
        double totalAmount = calculateTotalAmount();

        // 将日期转换为字符串
        SimpleDateFormat sdf = new SimpleDateFormat("MMdd");
        String dateStr = sdf.format(new Date());

        // 创建并插入到POTitle表中
        POTitle potitle = new POTitle();
        potitle.setPOdate(dateStr);
        potitle.setPOamount(totalAmount);
        potitle.setPOdiscount(1.0); // 你可以根据需要修改折扣
        potitle.setPOmember(SharedState.getMBID());
        potitle.setPOemployee(SharedState.getEMID());
        
        int potitleID = insertPOTitle(potitle);

        // 获取插入的 POTitle ID 并生成唯一 PONO
        String pono = generateUniquePONO(potitleID);
        potitle.setPONO(pono);
        updatePOTitlePONO(potitleID, pono);

        // 插入到POTitleItem表中并更新PDStock表
        for (Map.Entry<String, Integer> entry : productQuantities.entrySet()) {
            String pdID = entry.getKey();
            int quantity = entry.getValue();

            // 创建并插入到POTitleItem表中
            POTitleItem potitleItem = new POTitleItem();
            potitleItem.setPONO(pono); // 使用相同的 PONO
            potitleItem.setPOID(pdID);
            potitleItem.setPOqty(quantity);
            dao.add(potitleItem);

            // 更新PDStock表
            Map<String, String> pdstockParams = new HashMap<>();
            pdstockParams.put("PDID", pdID);
            List<PDStock> pdstocks = dao.queryByParameters("pdstock", pdstockParams, PDStock.class);
            if (!pdstocks.isEmpty()) {
                PDStock pdstockToUpdate = pdstocks.get(0);
                int currentStock = (int) pdstockToUpdate.getPDqty();
                int newStock = currentStock - quantity;
                if (newStock >= 0) {
                    pdstockToUpdate.setPDqty(newStock);
                    dao.update(pdstockToUpdate);
                } else {
                    System.out.println("Error: Insufficient stock for PDID: " + pdID);
                }
            }
        }

        // 在 JTextArea 显示总金额
        taOutPt.append("\n總金額: " + totalAmount);

        // 生成Excel账单
        generateExcelBill(pono);

        // 更新按钮文本为"下一位"
        btnSUM.setText("下一位");
        isCheckoutComplete = true;
    }

    private double calculateTotalAmount() {
        double totalAmount = 0.0;
        for (Map.Entry<String, Integer> entry : productQuantities.entrySet()) {
            for (int i = 0; i < table.getRowCount(); i++) {
                if (table.getValueAt(i, 0).equals(entry.getKey())) {
                    double price = Double.parseDouble(table.getValueAt(i, 4).toString());
                    int quantity = entry.getValue();
                    totalAmount += price * quantity;
                    break;
                }
            }
        }
        return totalAmount;
    }

    private String generateUniquePONO(int id) {
        return String.format("PO%04d", id);
    }

    private void generateExcelBill(String pono) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("帳單");

        // 创建表头信息
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("*****GG COMPUTER CO.*****");

        Row customerRow = sheet.createRow(1);
        customerRow.createCell(0).setCellValue("顧客: " + SharedState.getMBName());
        customerRow.createCell(3).setCellValue("服務員: " + SharedState.getEMname());

        Row separatorRow = sheet.createRow(2);
        separatorRow.createCell(0).setCellValue("=======================================");

        // 创建标题行
        Row headerRow = sheet.createRow(3);
        String[] columns = {"商品編號", "商品名稱", "數量", "單位", "總金額"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }

        // 填充数据行
        int rowNum = 4;
        for (Map.Entry<String, Integer> entry : productQuantities.entrySet()) {
            for (int i = 0; i < table.getRowCount(); i++) {
                if (table.getValueAt(i, 0).equals(entry.getKey())) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue((String) table.getValueAt(i, 0));
                    row.createCell(1).setCellValue((String) table.getValueAt(i, 1));
                    row.createCell(2).setCellValue(entry.getValue());
                    double price = Double.parseDouble(table.getValueAt(i, 4).toString());
                    row.createCell(3).setCellValue(price);
                    row.createCell(4).setCellValue(price * entry.getValue());
                    break;
                }
            }
        }

        // 添加总金额行
        Row totalRow = sheet.createRow(rowNum);
        totalRow.createCell(3).setCellValue("總金額");
        totalRow.createCell(4).setCellValue(calculateTotalAmount());

        // 创建文件名
        String fileName = "帳單_" + pono + ".xlsx";

        // 写入Excel文件
        try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
            workbook.write(fileOut);
            workbook.close();
            JOptionPane.showMessageDialog(contentPane, "帳單已生成: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(contentPane, "生成帳單時有錯!");
        }
    }

    private void resetForNextCustomer() {
        productQuantities.clear(); // 清空购物车
        SharedState.setMBID(""); // 清空会员ID
        SharedState.setMBName(""); // 清空会员名称
        stBBSout1 = "                *****GG COMPUTER CO.*****\nDP ID\tPD Name\tQty\tPrice\n"
                + "顧客: " + SharedState.getMBName() + "\t 服務員: " + SharedState.getEMname() + "\n"
                + "=======================================\n";
        taOutPt.setText(stBBSout1); // 重置输出文本区
        btnSUM.setText("結帳"); // 重置按钮文本为"結帳"
        isCheckoutComplete = false;
        JOptionPane.showMessageDialog(contentPane, "請登錄新會員進行結幄帳。");
    }

    private int insertPOTitle(POTitle potitle) {
        int id = -1;
        String sql = "INSERT INTO potitle (POdate, POamount, POdiscount, POmember, POemployee) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DbConnection.getDbC();
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, potitle.getPOdate());
            pstmt.setDouble(2, potitle.getPOamount());
            pstmt.setDouble(3, potitle.getPOdiscount());
            pstmt.setString(4, potitle.getPOmember());
            pstmt.setString(5, potitle.getPOemployee());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        id = generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    private void updatePOTitlePONO(int id, String pono) {
        String sql = "UPDATE potitle SET PONO = ? WHERE id = ?";
        try (Connection conn =DbConnection.getDbC();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, pono);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
