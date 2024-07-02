package model;

public class PITitleItem {
    private Integer id;
    private String PINO;
    private String PIPDID;
    private double PIqty;

    public PITitleItem() {
        super();
    }

    public PITitleItem(String PINO, String PIPDID, double PIqty) {
        super();
        this.PINO = PINO;
        this.PIPDID = PIPDID;
        this.PIqty = PIqty;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPINO() {
        return PINO;
    }

    public void setPINO(String PINO) {
        this.PINO = PINO;
    }

    public String getPIPDID() {
        return PIPDID;
    }

    public void setPIPDID(String PIPDID) {
        this.PIPDID = PIPDID;
    }

    public double getPIqty() {
        return PIqty;
    }

    public void setPIqty(double PIqty) {
        this.PIqty = PIqty;
    }
}

