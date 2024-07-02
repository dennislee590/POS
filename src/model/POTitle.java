package model;

public class POTitle {
	
    private Integer id;
    private String PONO;
    private String POdate;
    private double POamount;
    private double POdiscount;
    private String POmember;
    private String POemployee;

    public POTitle() {
        super();
    }

    public POTitle(String PONO, String POdate, double POamount, double POdiscount, String POmember, String POemployee) {
        super();
        this.PONO = PONO;
        this.POdate = POdate;
        this.POamount = POamount;
        this.POdiscount = POdiscount;
        this.POmember = POmember;
        this.POemployee = POemployee;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPONO() {
        return PONO;
    }

    public void setPONO(String PONO) {
        this.PONO = PONO;
    }

    public String getPOdate() {
        return POdate;
    }

    public void setPOdate(String POdate) {
        this.POdate = POdate;
    }

    public double getPOamount() {
        return POamount;
    }

    public void setPOamount(double POamount) {
        this.POamount = POamount;
    }

    public double getPOdiscount() {
        return POdiscount;
    }

    public void setPOdiscount(double POdiscount) {
        this.POdiscount = POdiscount;
    }

    public String getPOmember() {
        return POmember;
    }

    public void setPOmember(String POmember) {
        this.POmember = POmember;
    }

    public String getPOemployee() {
        return POemployee;
    }

    public void setPOemployee(String POemployee) {
        this.POemployee = POemployee;
    }
}
