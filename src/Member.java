import javax.swing.JTable;
import javax.swing.table.TableColumn;

public class Member {
	//fields
	private String MBID;
	private String MBNAME;
	private String MBDISC;
	private String MBADDR;
	
	//constructors	
	Member(String MBID,String MBNAME,String MBDISC,String MBADDR){
		this.MBID=MBID;
		this.MBNAME=MBNAME;
		this.MBDISC=MBDISC;
		this.MBADDR=MBADDR;
	}
	
	//methods
	public String getMBID() {
		return MBID;
	}

	public void setMBID(String MBID) {
		this.MBID = MBID;
	}

	public String getMBNAME() {
		return MBNAME;
	}

	public void setMBName(String MBNAME) {
		this.MBNAME = MBNAME;
	}

	public String getMBDISC() {
		return MBDISC;
	}

	public void setMBDISC(String MBDISC) {
		this.MBDISC = MBDISC;
	}

	public String getMBADDR() {
		return MBADDR;
	}

	public void setMBADDR(String MBADDR) {
		this.MBADDR = MBADDR;
	}
	public void renewJTable() {
 	/*   	//ADD FOR JTABLE
	         // Define your column names and data (similar to what you did before)
	         Object[] columnNames = {"MB ID", "Name", "MB DISC", "MB ADDR"};
	         Object[][] rowData = new Object[mb.length][4];
	         for (int i = 0; i < mb.length; i++) {
	             rowData[i][0] = mb[i].MBID;
	             rowData[i][1] = mb[i].MBNAME;
	             rowData[i][2] = mb[i].MBDISC;
	             rowData[i][3] = mb[i].MBADDR;
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
		*/
	}
	
}

