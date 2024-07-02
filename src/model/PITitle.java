package model;

public class PITitle {
    private Integer id;
    private String PINO;
    private String PIdate;
    private String PIemployee;
    private String PIapproval;

    public PITitle() {
        super();
    }

    public PITitle(String PINO, String PIdate, String PIemployee, String PIapproval) {
        super();
        this.PINO = PINO;
        this.PIdate = PIdate;
        this.PIemployee = PIemployee;
        this.PIapproval = PIapproval;
    }

    public Integer getId() {
        return id;
    }

//    public void setId(Integer id) {
//        this.id = id;
//    }

    public String getPINO() {
        return PINO;
    }

    public void setPINO(String PINO) {
        this.PINO = PINO;
    }

    public String getPIdate() {
        return PIdate;
    }

    public void setPIdate(String PIdate) {
        this.PIdate = PIdate;
    }

    public String getPIemployee() {
        return PIemployee;
    }

    public void setPIemployee(String PIemployee) {
        this.PIemployee = PIemployee;
    }

    public String getPIapproval() {
        return PIapproval;
    }

    public void setPIapproval(String PIapproval) {
        this.PIapproval = PIapproval;
    }
}
