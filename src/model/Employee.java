package model;

public class Employee {
    private Integer id;
    private String EMname;
    private String EMID;
    private String EMPW;
    private String EMlevel;
    private String EMboss;
    private String EMgetout;

    public Employee() {
        super();
    }

    public Employee(String EMname, String EMID, String EMPW, String EMlevel, String EMboss, String EMgetout) {
        super();
        this.EMname = EMname;
        this.EMID = EMID;
        this.EMPW = EMPW;
        this.EMlevel = EMlevel;
        this.EMboss = EMboss;
        this.EMgetout = EMgetout;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEMname() {
        return EMname;
    }

    public void setEMname(String EMname) {
        this.EMname = EMname;
    }

    public String getEMID() {
        return EMID;
    }

    public void setEMID(String EMID) {
        this.EMID = EMID;
    }

    public String getEMPW() {
        return EMPW;
    }

    public void setEMPW(String EMPW) {
        this.EMPW = EMPW;
    }

    public String getEMlevel() {
        return EMlevel;
    }

    public void setEMlevel(String EMlevel) {
        this.EMlevel = EMlevel;
    }

    public String getEMboss() {
        return EMboss;
    }

    public void setEMboss(String EMboss) {
        this.EMboss = EMboss;
    }

    public String getEMgetout() {
        return EMgetout;
    }

    public void setEMgetout(String EMgetout) {
        this.EMgetout = EMgetout;
    }
}
