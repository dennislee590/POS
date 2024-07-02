package util;

public class SharedState {
    private static String EMID;
    private static String EMname;
    private static String EMlevel;
    private static String EMboss;
    private static String ELflagRan;
    private static String MBID;
    private static String MBName;
    private static String MBLevel;
    
	public static String getEMID() {
		return EMID;
	}
	public static void setEMID(String eMID) {
		EMID = eMID;
	}
	public static String getEMname() {
		return EMname;
	}
	public static void setEMname(String eMname) {
		EMname = eMname;
	}
	public static String getEMlevel() {
		return EMlevel;
	}
	public static void setEMlevel(String eMlevel) {
		EMlevel = eMlevel;
	}
	public static String getEMboss() {
		return EMboss;
	}
	public static void setEMboss(String eMboss) {
		EMboss = eMboss;
	}
	public static String getELflagRan() {
		return ELflagRan;
	}
	public static void setELflagRan(String eLflagRan) {
		ELflagRan = eLflagRan;
	}
	public static String getMBID() {
		return MBID;
	}
	public static void setMBID(String mBID) {
		MBID = mBID;
	}
	public static String getMBName() {
		return MBName;
	}
	public static void setMBName(String mBName) {
		MBName = mBName;
	}
	public static String getMBLevel() {
		return MBLevel;
	}
	public static void setMBLevel(String mBLevel) {
		MBLevel = mBLevel;
	}

}
