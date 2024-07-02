package util;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.Component;
import java.util.List;

public class TableUtils {

    /**
     * Puts data into the specified JTable and adjusts column widths.
     *
     * @param data        The data to be displayed in the table.
     * @param table       The JTable to display the data.
     * @param columnNames The names of the columns.
     */
    public static void putDataIntoTable(List<Object[]> data, JTable table, String[] columnNames) {
        // Create table model and set column names
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);

        // Add data rows to the model
        for (Object[] rowData : data) {
            model.addRow(rowData);
        }

        // Set the model to the table
        table.setModel(model);

        // Adjust column widths
        for (int i = 0; i < table.getColumnCount(); i++) {
            adjustColumnWidth(table, i);
        }
    }

    /**
     * Adjusts the width of the specified column in the specified JTable.
     *
     * @param table  The JTable containing the column.
     * @param column The index of the column to adjust.
     */
    private static void adjustColumnWidth(JTable table, int column) {
        TableColumn tableColumn = table.getColumnModel().getColumn(column);
        int preferredWidth = tableColumn.getMinWidth();
        int maxWidth = tableColumn.getMaxWidth();

        // Calculate the width based on column header
        TableCellRenderer headerRenderer = table.getTableHeader().getDefaultRenderer();
        Component headerComp = headerRenderer.getTableCellRendererComponent(table, tableColumn.getHeaderValue(), false, false, 0, 0);
        preferredWidth = Math.max(preferredWidth, headerComp.getPreferredSize().width);

        // Calculate the width based on cell content
        for (int row = 0; row < table.getRowCount(); row++) {
            TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
            Component c = cellRenderer.getTableCellRendererComponent(table, table.getValueAt(row, column), false, false, row, column);
            preferredWidth = Math.max(preferredWidth, c.getPreferredSize().width);
            if (preferredWidth >= maxWidth) {
                break;
            }
        }

        // Add some margin to the width
        preferredWidth += 10;

        // Set the preferred width for the column
        tableColumn.setPreferredWidth(preferredWidth);
    }
}
