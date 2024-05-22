import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;

public class PDData {
	//fields
	private String PDID;
	private String PDName;
	private String PDPrice;
	private String PDStock;

	//constructor
	public PDData(String PDID, String PDName,
			String PDPrice, String PDStock) {
		this.PDID = PDID;
		this.PDName = PDName;
		this.PDPrice = PDPrice;
		this.PDStock = PDStock;
	}
	//methods
	//是能設就用STATIC嗎?理由?
	public String toJTableColum() {
		return "ID\tName\tPrice\t";
	}
	public String toJTableLine() {
		return "=============================";
	}
	
	//未完
	public String scan2buy(String PDID) {
	/*	for(i=0;i<3;i++) {
			if(PDInfor)
		}
	*/	return PDID;
	}
	public String getPDID() {
		return PDID;
	}

	public void setPDID(String PDID) {
		this.PDID = PDID;
	}

	public String getPDName() {
		return PDName;
	}

	public void setPDName(String PDName) {
		this.PDName = PDName;
	}

	public String getPDPrice() {
		return PDPrice;
	}

	public void setPDPrice(String PDPrice) {
		this.PDPrice = PDPrice;
	}

	public String getPDStock() {
		return PDStock;
	}

	public void setPDStock(String PDStock) {
		this.PDStock = PDStock;
	}
	void delay() {
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
	
}
