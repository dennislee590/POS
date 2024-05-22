
public class POData {
	//fields
	private String POID;
	private String POName;
	private String POQty;
	private String POPrice;

	//constructor
	public POData(String POID, String POName,
			String POQty, String POPrice) {
		//super();
		this.POID = POID;
		this.POName = POName;
		this.POQty = POQty;
		this.POPrice = POPrice;
	}
	
	//methods
	public String wJTTop() {
		return "                *****GG COMPUTER CO.*****\nDP ID\tPD Name\tQty\tPrice\n"
				+ "=======================================\n";
	}
	public String show() {
		
		return POID+"/t"+POName+"/t"+POQty+"/t"+POPrice;
	}

	public String getPOID() {
		return POID;
	}

	public void setPOID(String pOID) {
		POID = pOID;
	}

	public String getPOName() {
		return POName;
	}

	public void setPOName(String pOName) {
		POName = pOName;
	}

	public String getPOQty() {
		return POQty;
	}

	public void setPOQty(String pOQty) {
		POQty = pOQty;
	}
	public String getPOPrice() {
		return POPrice;
	}

	public void setPOPrice(String pOPrice) {
		POQty = pOPrice;
	}


}	
/*	
	//fields
	private String POID;
	private String PODISC;
	private String PODCONT;
	private String POSUM;

	//constructor
	public SaleInfor(String POID, String PODISC,
			String PODCONT, String POSUM) {
		super();
		this.POID = POID;
		this.PODISC = PODISC;
		this.PODCONT = PODCONT;
		this.POSUM = POSUM;
	}
	//methods

	public String addOrder() {
		return POID;
	}
	public String toPOline() {
		return POID;
	}
	public String getPOID() {
		return POID;
	}

	public void setPOID(String POID) {
		this.POID = POID;
	}

	public String getPODISC() {
		return PODISC;
	}

	public void setPODISC(String PODISC) {
		this.PODISC = PODISC;
	}

	public String getPODCONT() {
		return PODCONT;
	}

	public void setPODCONT(String PODCONT) {
		this.PODCONT = PODCONT;
	}

	public String getPOSUM() {
		return POSUM;
	}

	public void setPOSUM(String POSUM) {
		this.POSUM =POSUM;
	}

} */
	
	


