package model;

public class Employeelog {
    private Integer id;
    private String ELEMID;
    private String ELloginTime;
    private String ELlogoutTime;
    private String ELflagRan;

    public Employeelog() {
        super();
    }

    public Employeelog(String ELEMID, String ELloginTime, String ELlogoutTime, String ELflagRan) {
        super();
        this.ELEMID = ELEMID;
        this.ELloginTime = ELloginTime;
        this.ELlogoutTime = ELlogoutTime;
        this.ELflagRan = ELflagRan;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getELEMID() {
        return ELEMID;
    }

    public void setELEMID(String ELEMID) {
        this.ELEMID = ELEMID;
    }

    public String getELloginTime() {
        return ELloginTime;
    }

    public void setELloginTime(String ELloginTime) {
        this.ELloginTime = ELloginTime;
    }

    public String getELlogoutTime() {
        return ELlogoutTime;
    }

    public void setELlogoutTime(String ELlogoutTime) {
        this.ELlogoutTime = ELlogoutTime;
    }

    public String getELflagRan() {
        return ELflagRan;
    }

    public void setELflagRan(String ELflagRan) {
        this.ELflagRan = ELflagRan;
    }
}
