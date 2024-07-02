package model;


public class POTitleItem {
    private Integer id;
    private String PONO;
    private String POID;
    private double POqty;

    public POTitleItem() {
        super();
    }

    public POTitleItem(String PONO, String POID, double POqty) {
        super();
        this.PONO = PONO;
        this.POID = POID;
        this.POqty = POqty;
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

    public String getPOID() {
        return POID;
    }

    public void setPOID(String POID) {
        this.POID = POID;
    }

    public double getPOqty() {
        return POqty;
    }

    public void setPOqty(double POqty) {
        this.POqty = POqty;
    }
}
