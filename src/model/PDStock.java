package model;

public class PDStock {
	private Integer id;
	private String PDID;
	private String PDname;
	private String PDdesc;
	private String PDbarcode;
	private double PDprice;
	private double PDqty;

	public PDStock() {
		super();
	}

	public PDStock(String PDID, String PDname, String PDdesc, String PDbarcode, double PDprice, double PDqty) {
		super();
		this.PDID = PDID;
		this.PDname = PDname;
		this.PDdesc = PDdesc;
		this.PDbarcode = PDbarcode;
		this.PDprice = PDprice;
		this.PDqty = PDqty;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPDID() {
		return PDID;
	}

	public void setPDID(String PDID) {
		this.PDID = PDID;
	}

	public String getPDname() {
		return PDname;
	}

	public void setPDname(String PDname) {
		this.PDname = PDname;
	}

	public String getPDdesc() {
		return PDdesc;
	}

	public void setPDdesc(String PDdesc) {
		this.PDdesc = PDdesc;
	}

	public String getPDbarcode() {
		return PDbarcode;
	}

	public void setPDbarcode(String PDbarcode) {
		this.PDbarcode = PDbarcode;
	}

	public double getPDprice() {
		return PDprice;
	}

	public void setPDprice(double PDprice) {
		this.PDprice = PDprice;
	}

	public double getPDqty() {
		return PDqty;
	}

	public void setPDqty(double PDqty) {
		this.PDqty = PDqty;
	}
}
