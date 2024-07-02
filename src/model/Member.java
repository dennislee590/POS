package model;

public class Member {
    private Integer id;
    private String MBname;
    private String MBID;
    private String MBPW;
    private String MBemail;
    private String MBphone;
    private String MBaddress;
    private String MBhint;
    private String MBlevel;
    private String MBgetout;

    public Member() {
        super();
    }

    public Member(String MBname, String MBID, String MBPW, String MBemail, String MBphone, String MBaddress, String MBhint, String MBlevel, String MBgetout) {
        super();
        this.MBname = MBname;
        this.MBID = MBID;
        this.MBPW = MBPW;
        this.MBemail = MBemail;
        this.MBphone = MBphone;
        this.MBaddress = MBaddress;
        this.MBhint = MBhint;
        this.MBlevel = MBlevel;
        this.MBgetout = MBgetout;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMBname() {
        return MBname;
    }

    public void setMBname(String MBname) {
        this.MBname = MBname;
    }

    public String getMBID() {
        return MBID;
    }

    public void setMBID(String MBID) {
        this.MBID = MBID;
    }

    public String getMBPW() {
        return MBPW;
    }

    public void setMBPW(String MBPW) {
        this.MBPW = MBPW;
    }

    public String getMBemail() {
        return MBemail;
    }

    public void setMBemail(String MBemail) {
        this.MBemail = MBemail;
    }

    public String getMBphone() {
        return MBphone;
    }

    public void setMBphone(String MBphone) {
        this.MBphone = MBphone;
    }

    public String getMBaddress() {
        return MBaddress;
    }

    public void setMBaddress(String MBaddress) {
        this.MBaddress = MBaddress;
    }

    public String getMBhint() {
        return MBhint;
    }

    public void setMBhint(String MBhint) {
        this.MBhint = MBhint;
    }

    public String getMBlevel() {
        return MBlevel;
    }

    public void setMBlevel(String MBlevel) {
        this.MBlevel = MBlevel;
    }

    public String getMBgetout() {
        return MBgetout;
    }

    public void setMBgetout(String MBgetout) {
        this.MBgetout = MBgetout;
    }
}
