package control;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import dao.impl.GeneralDaoImpl;
import model.PDStock;
import model.PITitle;
import model.PITitleItem;

public class ImpoUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable tableTitle;
    private JTable tableItems;
    private JTable tableNewPDStock;
    private JTextField textField;
    private GeneralDaoImpl dao;
    private List<String> validPDIDs;

    private String[] titleColumns = { "PINO", "Date", "Employee", "Approval" };
    private String[] itemColumns = { "PINO", "PIPDID", "PIqty" };
    private String[] newPDStockColumns = { "PDID", "PDname", "PDdesc", "PDbarcode", "PDprice", "PDqty" };

    private boolean isFormLoading = false;
    private boolean isFirstAddCompleted = false;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ImpoUI frame = new ImpoUI();
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
    public ImpoUI() {
        dao = new GeneralDaoImpl();
        validPDIDs = loadPDStockData();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(10, 10, 770, 550);
        contentPane.add(panel);
        panel.setLayout(null);

        initializeComponents(panel);

        // Add window listener to handle window closing event
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new FuncSelectUI().setVisible(true);
            }
        });
    }

    private void initializeComponents(JPanel panel) {
        JScrollPane scrollPaneTitle = new JScrollPane();
        scrollPaneTitle.setBounds(10, 76, 750, 80);
        panel.add(scrollPaneTitle);

        tableTitle = new JTable();
        scrollPaneTitle.setViewportView(tableTitle);

        JScrollPane scrollPaneItems = new JScrollPane();
        scrollPaneItems.setBounds(10, 166, 750, 160);
        panel.add(scrollPaneItems);

        tableItems = new JTable() {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    String pipdid = (String) getValueAt(row, 1);
                    c.setBackground(validPDIDs.contains(pipdid) ? Color.WHITE : new Color(255, 204, 204));
                }
                return c;
            }
        };
        scrollPaneItems.setViewportView(tableItems);

        textField = new JTextField();
        textField.setBounds(10, 43, 400, 21);
        panel.add(textField);
        textField.setColumns(10);
        textField.setText("C:\\javaocp\\eclipse\\POS\\src\\doc\\PITitleData.xlsx");

        initializeButtons(panel);

        JScrollPane scrollPaneNewPDStock = new JScrollPane();
        scrollPaneNewPDStock.setBounds(10, 336, 750, 160);
        panel.add(scrollPaneNewPDStock);

        tableNewPDStock = new JTable();
        scrollPaneNewPDStock.setViewportView(tableNewPDStock);
    }

    private void initializeButtons(JPanel panel) {
        JButton btnLoadForm = new JButton("表單進貨");
        btnLoadForm.setBounds(10, 10, 85, 23);
        panel.add(btnLoadForm);
        btnLoadForm.addActionListener(e -> loadForm());

        JButton btnShowRecords = new JButton("進貨記錄");
        btnShowRecords.setBounds(200, 10, 85, 23);
        panel.add(btnShowRecords);
        btnShowRecords.addActionListener(e -> showRecords());

        JButton btnShowStock = new JButton("庫存資訊");
        btnShowStock.setBounds(295, 10, 85, 23);
        panel.add(btnShowStock);
        btnShowStock.addActionListener(e -> showStock());

        JButton btnFileSelect = new JButton("檔案選擇");
        btnFileSelect.setBounds(420, 42, 85, 23);
        panel.add(btnFileSelect);
        btnFileSelect.addActionListener(e -> selectFile());

        JButton btnAddData = new JButton("新增");
        btnAddData.setBounds(579, 10, 85, 23);
        panel.add(btnAddData);
        btnAddData.addActionListener(e -> addData());

        JButton btnBack = new JButton("回上一層");
        btnBack.setBounds(579, 43, 85, 23);
        panel.add(btnBack);
        btnBack.addActionListener(e -> goBack());
    }

    private void selectFile() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            textField.setText(selectedFile.getAbsolutePath());
        }
    }

    private void loadForm() {
        isFormLoading = true;
        isFirstAddCompleted = false;
        loadExcelData();
        tableItems.setVisible(true);
        tableNewPDStock.setVisible(true);
    }

    private void showRecords() {
        isFormLoading = false;
        displayRecords();
        tableItems.setVisible(false);
        tableNewPDStock.setVisible(false);
        adjustTableTitleBounds();
    }

    private void showStock() {
        isFormLoading = false;
        displayStock();
        tableItems.setVisible(false);
        tableNewPDStock.setVisible(false);
        adjustTableTitleBounds();
    }

    private void addData() {
        if (isFormLoading) {
            if (!isFirstAddCompleted) {
                addNewPDStockData();
            } else {
                addPITitleAndItems();
            }
        } else {
            JOptionPane.showMessageDialog(this, "新增僅在表單進貨模式下有效。", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void goBack() {
        dispose();
        new FuncSelectUI().setVisible(true);
    }

    private void loadExcelData() {
        String excelFilePath = textField.getText();
        try (FileInputStream fis = new FileInputStream(excelFilePath)) {
            Workbook workbook = WorkbookFactory.create(fis);
            Sheet sheet = workbook.getSheetAt(0);

            boolean hasNewData = loadTitleData(sheet);
            if (hasNewData) {
                loadItemData(sheet);
            } else {
                isFormLoading = false;
                tableItems.setVisible(false);
                tableNewPDStock.setVisible(false);
            }

            workbook.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading Excel file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean loadTitleData(Sheet sheet) {
        List<Object[]> titleData = new ArrayList<>();
        Row titleRow = sheet.getRow(1);
        Object[] titleValues = new Object[titleColumns.length];
        for (int i = 0; i < titleColumns.length; i++) {
            titleValues[i] = getCellValue(titleRow.getCell(i));
        }
        titleData.add(titleValues);

        DefaultTableModel titleModel = new DefaultTableModel(titleData.toArray(new Object[0][]), titleColumns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        String pino = (String) titleModel.getValueAt(0, 0);
        Map<String, String> params = new HashMap<>();
        params.put("PINO", pino);
        List<PITitle> existingTitles = dao.queryByParameters("pititle", params, PITitle.class);

        if (!existingTitles.isEmpty()) {
            JOptionPane.showMessageDialog(this, "單號重覆: " + pino, "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        tableTitle.setModel(titleModel);
        setTableColumnWidths(tableTitle);
        return true;
    }

    private void loadItemData(Sheet sheet) {
        List<Object[]> itemData = new ArrayList<>();
        List<Object[]> newPDStockData = new ArrayList<>();
        for (int i = 4; i <= sheet.getLastRowNum(); i++) {
            Row itemRow = sheet.getRow(i);
            Object[] itemValues = new Object[itemColumns.length];
            for (int j = 0; j < itemColumns.length; j++) {
                itemValues[j] = getCellValue(itemRow.getCell(j));
            }
            itemData.add(itemValues);

            String pipdid = (String) itemValues[1];
            if (!validPDIDs.contains(pipdid)) {
                newPDStockData.add(new Object[] { pipdid, "", "", "", 0.0, 0.0 });
            }
        }

        DefaultTableModel itemModel = new DefaultTableModel(itemData.toArray(new Object[0][]), itemColumns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableItems.setModel(itemModel);
        setTableColumnWidths(tableItems);

        DefaultTableModel newPDStockModel = new DefaultTableModel(newPDStockData.toArray(new Object[0][]), newPDStockColumns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        };
        tableNewPDStock.setModel(newPDStockModel);
        setTableColumnWidths(tableNewPDStock);
    }

    private void displayRecords() {
        List<PITitle> titles = dao.queryByParameters("pititle", new HashMap<>(), PITitle.class);
        List<PITitleItem> items = dao.queryByParameters("pititleitem", new HashMap<>(), PITitleItem.class);

        List<Object[]> records = new ArrayList<>();
        for (PITitle title : titles) {
            for (PITitleItem item : items) {
                if (item.getPINO().equals(title.getPINO())) {
                    records.add(new Object[] { title.getPINO(), title.getPIdate(), title.getPIemployee(), title.getPIapproval(), item.getPIPDID(), item.getPIqty() });
                }
            }
        }

        DefaultTableModel recordsModel = new DefaultTableModel(records.toArray(new Object[0][]), new String[] { "PINO", "Date", "Employee", "Approval", "PIPDID", "PIqty" }) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableTitle.setModel(recordsModel);
        setTableColumnWidths(tableTitle);
    }

    private void displayStock() {
        List<PDStock> stocks = dao.queryByParameters("pdstock", new HashMap<>(), PDStock.class);

        List<Object[]> stockData = new ArrayList<>();
        for (PDStock stock : stocks) {
            stockData.add(new Object[] { stock.getPDID(), stock.getPDname(), stock.getPDdesc(), stock.getPDbarcode(), stock.getPDprice(), stock.getPDqty() });
        }

        DefaultTableModel stockModel = new DefaultTableModel(stockData.toArray(new Object[0][]), newPDStockColumns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableTitle.setModel(stockModel);
        setTableColumnWidths(tableTitle);
    }

    private void addNewPDStockData() {
        DefaultTableModel newPDStockModel = (DefaultTableModel) tableNewPDStock.getModel();

        List<PDStock> newPDStocks = new ArrayList<>();
        for (int i = 0; i < newPDStockModel.getRowCount(); i++) {
            String pdid = (String) newPDStockModel.getValueAt(i, 0);
            if (!validPDIDs.contains(pdid)) {
                PDStock newPDStock = new PDStock();
                newPDStock.setPDID(pdid);
                newPDStock.setPDname((String) newPDStockModel.getValueAt(i, 1));
                newPDStock.setPDdesc((String) newPDStockModel.getValueAt(i, 2));
                newPDStock.setPDbarcode((String) newPDStockModel.getValueAt(i, 3));
                newPDStock.setPDprice((Double) newPDStockModel.getValueAt(i, 4));
                newPDStock.setPDqty((Double) newPDStockModel.getValueAt(i, 5));
                newPDStocks.add(newPDStock);
            }
        }

        if (!newPDStocks.isEmpty()) {
            for (PDStock newPDStock : newPDStocks) {
                dao.add(newPDStock);
            }

            validPDIDs = loadPDStockData();

            DefaultTableModel itemModel = (DefaultTableModel) tableItems.getModel();
            boolean allPDIDsExist = true;
            for (int i = 0; i < itemModel.getRowCount(); i++) {
                String pdid = (String) itemModel.getValueAt(i, 1);
                if (!validPDIDs.contains(pdid)) {
                    allPDIDsExist = false;
                    break;
                }
            }

            if (!allPDIDsExist) {
                JOptionPane.showMessageDialog(this, "請先完成新產品資料的新增。", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                JOptionPane.showMessageDialog(this, "新產品資料新增完成。", "Info", JOptionPane.INFORMATION_MESSAGE);
                tableNewPDStock.setModel(new DefaultTableModel(new Object[0][newPDStockColumns.length], newPDStockColumns));
                isFirstAddCompleted = true;
            }
        }
    }

    private void addPITitleAndItems() {
        DefaultTableModel titleModel = (DefaultTableModel) tableTitle.getModel();
        DefaultTableModel itemModel = (DefaultTableModel) tableItems.getModel();

        PITitle piTitle = new PITitle();
        piTitle.setPINO((String) titleModel.getValueAt(0, 0));
        piTitle.setPIdate(String.valueOf(titleModel.getValueAt(0, 1)));
        piTitle.setPIemployee((String) titleModel.getValueAt(0, 2));
        piTitle.setPIapproval((String) titleModel.getValueAt(0, 3));
        dao.add(piTitle);

        for (int i = 0; i < itemModel.getRowCount(); i++) {
            PITitleItem piTitleItem = new PITitleItem();
            piTitleItem.setPINO((String) itemModel.getValueAt(i, 0));
            piTitleItem.setPIPDID((String) itemModel.getValueAt(i, 1));
            piTitleItem.setPIqty((Double) itemModel.getValueAt(i, 2));
            dao.add(piTitleItem);

            PDStock pdStock = dao.queryByParameters("pdstock", Map.of("PDID", piTitleItem.getPIPDID()), PDStock.class).get(0);
            pdStock.setPDqty(pdStock.getPDqty() + piTitleItem.getPIqty());
            dao.update(pdStock);
        }

        JOptionPane.showMessageDialog(this, "進貨記錄已成功新增。", "Info", JOptionPane.INFORMATION_MESSAGE);
        clearTableData();
        isFirstAddCompleted = false;
    }

    private void clearTableData() {
        ((DefaultTableModel) tableTitle.getModel()).setRowCount(0);
        ((DefaultTableModel) tableItems.getModel()).setRowCount(0);
        ((DefaultTableModel) tableNewPDStock.getModel()).setRowCount(0);
    }

    private List<String> loadPDStockData() {
        List<String> pdIDs = new ArrayList<>();
        List<PDStock> pdStocks = dao.queryByParameters("pdstock", new HashMap<>(), PDStock.class);
        for (PDStock pdStock : pdStocks) {
            pdIDs.add(pdStock.getPDID());
        }
        return pdIDs;
    }

    private Object getCellValue(org.apache.poi.ss.usermodel.Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return cell.getNumericCellValue();
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    private void setTableColumnWidths(JTable table) {
        TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < columnModel.getColumnCount(); column++) {
            int width = 75; // Min width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width + 1, width);
            }
            if (width > 300) {
                width = 300;
            }
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }

    private void adjustTableTitleBounds() {
        JScrollPane scrollPaneTitle = (JScrollPane) tableTitle.getParent().getParent();
        scrollPaneTitle.setBounds(10, 76, 750, 450);
    }
}
