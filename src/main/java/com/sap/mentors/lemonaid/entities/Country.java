package com.sap.mentors.lemonaid.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="countries")
public class Country {
	
	public static final String AD = "AD";
	public static final String AE = "AE";
	public static final String AF = "AF";
	public static final String AG = "AG";
	public static final String AI = "AI";
	public static final String AL = "AL";
	public static final String AM = "AM";
	public static final String AO = "AO";
	public static final String AQ = "AQ";
	public static final String AR = "AR";
	public static final String AS = "AS";
	public static final String AT = "AT";
	public static final String AU = "AU";
	public static final String AW = "AW";
	public static final String AX = "AX";
	public static final String AZ = "AZ";
	public static final String BA = "BA";
	public static final String BB = "BB";
	public static final String BD = "BD";
	public static final String BE = "BE";
	public static final String BF = "BF";
	public static final String BG = "BG";
	public static final String BH = "BH";
	public static final String BI = "BI";
	public static final String BJ = "BJ";
	public static final String BL = "BL";
	public static final String BM = "BM";
	public static final String BN = "BN";
	public static final String BO = "BO";
	public static final String BQ = "BQ";
	public static final String BR = "BR";
	public static final String BS = "BS";
	public static final String BT = "BT";
	public static final String BV = "BV";
	public static final String BW = "BW";
	public static final String BY = "BY";
	public static final String BZ = "BZ";
	public static final String CA = "CA";
	public static final String CC = "CC";
	public static final String CD = "CD";
	public static final String CF = "CF";
	public static final String CG = "CG";
	public static final String CH = "CH";
	public static final String CI = "CI";
	public static final String CK = "CK";
	public static final String CL = "CL";
	public static final String CM = "CM";
	public static final String CN = "CN";
	public static final String CO = "CO";
	public static final String CR = "CR";
	public static final String CU = "CU";
	public static final String CV = "CV";
	public static final String CW = "CW";
	public static final String CX = "CX";
	public static final String CY = "CY";
	public static final String CZ = "CZ";
	public static final String DE = "DE";
	public static final String DJ = "DJ";
	public static final String DK = "DK";
	public static final String DM = "DM";
	public static final String DO = "DO";
	public static final String DZ = "DZ";
	public static final String EC = "EC";
	public static final String EE = "EE";
	public static final String EG = "EG";
	public static final String EH = "EH";
	public static final String ER = "ER";
	public static final String ES = "ES";
	public static final String ET = "ET";
	public static final String FI = "FI";
	public static final String FJ = "FJ";
	public static final String FK = "FK";
	public static final String FM = "FM";
	public static final String FO = "FO";
	public static final String FR = "FR";
	public static final String GA = "GA";
	public static final String GB = "GB";
	public static final String GD = "GD";
	public static final String GE = "GE";
	public static final String GF = "GF";
	public static final String GG = "GG";
	public static final String GH = "GH";
	public static final String GI = "GI";
	public static final String GL = "GL";
	public static final String GM = "GM";
	public static final String GN = "GN";
	public static final String GP = "GP";
	public static final String GQ = "GQ";
	public static final String GR = "GR";
	public static final String GS = "GS";
	public static final String GT = "GT";
	public static final String GU = "GU";
	public static final String GW = "GW";
	public static final String GY = "GY";
	public static final String HK = "HK";
	public static final String HM = "HM";
	public static final String HN = "HN";
	public static final String HR = "HR";
	public static final String HT = "HT";
	public static final String HU = "HU";
	public static final String ID = "ID";
	public static final String IE = "IE";
	public static final String IL = "IL";
	public static final String IM = "IM";
	public static final String IN = "IN";
	public static final String IO = "IO";
	public static final String IQ = "IQ";
	public static final String IR = "IR";
	public static final String IS = "IS";
	public static final String IT = "IT";
	public static final String JE = "JE";
	public static final String JM = "JM";
	public static final String JO = "JO";
	public static final String JP = "JP";
	public static final String KE = "KE";
	public static final String KG = "KG";
	public static final String KH = "KH";
	public static final String KI = "KI";
	public static final String KM = "KM";
	public static final String KN = "KN";
	public static final String KP = "KP";
	public static final String KR = "KR";
	public static final String KW = "KW";
	public static final String KY = "KY";
	public static final String KZ = "KZ";
	public static final String LA = "LA";
	public static final String LB = "LB";
	public static final String LC = "LC";
	public static final String LI = "LI";
	public static final String LK = "LK";
	public static final String LR = "LR";
	public static final String LS = "LS";
	public static final String LT = "LT";
	public static final String LU = "LU";
	public static final String LV = "LV";
	public static final String LY = "LY";
	public static final String MA = "MA";
	public static final String MC = "MC";
	public static final String MD = "MD";
	public static final String ME = "ME";
	public static final String MF = "MF";
	public static final String MG = "MG";
	public static final String MH = "MH";
	public static final String MK = "MK";
	public static final String ML = "ML";
	public static final String MM = "MM";
	public static final String MN = "MN";
	public static final String MO = "MO";
	public static final String MP = "MP";
	public static final String MQ = "MQ";
	public static final String MR = "MR";
	public static final String MS = "MS";
	public static final String MT = "MT";
	public static final String MU = "MU";
	public static final String MV = "MV";
	public static final String MW = "MW";
	public static final String MX = "MX";
	public static final String MY = "MY";
	public static final String MZ = "MZ";
	public static final String NA = "NA";
	public static final String NC = "NC";
	public static final String NE = "NE";
	public static final String NF = "NF";
	public static final String NG = "NG";
	public static final String NI = "NI";
	public static final String NL = "NL";
	public static final String NO = "NO";
	public static final String NP = "NP";
	public static final String NR = "NR";
	public static final String NU = "NU";
	public static final String NZ = "NZ";
	public static final String OM = "OM";
	public static final String PA = "PA";
	public static final String PE = "PE";
	public static final String PF = "PF";
	public static final String PG = "PG";
	public static final String PH = "PH";
	public static final String PK = "PK";
	public static final String PL = "PL";
	public static final String PM = "PM";
	public static final String PN = "PN";
	public static final String PR = "PR";
	public static final String PS = "PS";
	public static final String PT = "PT";
	public static final String PW = "PW";
	public static final String PY = "PY";
	public static final String QA = "QA";
	public static final String RE = "RE";
	public static final String RO = "RO";
	public static final String RS = "RS";
	public static final String RU = "RU";
	public static final String RW = "RW";
	public static final String SA = "SA";
	public static final String SB = "SB";
	public static final String SC = "SC";
	public static final String SD = "SD";
	public static final String SE = "SE";
	public static final String SG = "SG";
	public static final String SH = "SH";
	public static final String SI = "SI";
	public static final String SJ = "SJ";
	public static final String SK = "SK";
	public static final String SL = "SL";
	public static final String SM = "SM";
	public static final String SN = "SN";
	public static final String SO = "SO";
	public static final String SR = "SR";
	public static final String SS = "SS";
	public static final String ST = "ST";
	public static final String SV = "SV";
	public static final String SX = "SX";
	public static final String SY = "SY";
	public static final String SZ = "SZ";
	public static final String TC = "TC";
	public static final String TD = "TD";
	public static final String TF = "TF";
	public static final String TG = "TG";
	public static final String TH = "TH";
	public static final String TJ = "TJ";
	public static final String TK = "TK";
	public static final String TL = "TL";
	public static final String TM = "TM";
	public static final String TN = "TN";
	public static final String TO = "TO";
	public static final String TR = "TR";
	public static final String TT = "TT";
	public static final String TV = "TV";
	public static final String TW = "TW";
	public static final String TZ = "TZ";
	public static final String UA = "UA";
	public static final String UG = "UG";
	public static final String UM = "UM";
	public static final String US = "US";
	public static final String UY = "UY";
	public static final String UZ = "UZ";
	public static final String VA = "VA";
	public static final String VC = "VC";
	public static final String VE = "VE";
	public static final String VG = "VG";
	public static final String VI = "VI";
	public static final String VN = "VN";
	public static final String VU = "VU";
	public static final String WF = "WF";
	public static final String WS = "WS";
	public static final String YE = "YE";
	public static final String YT = "YT";
	public static final String ZA = "ZA";
	public static final String ZM = "ZM";
	public static final String ZW = "ZW";
	
	@Id
	private String id;
    private String name;
    
	public Country() {}

    public Country(String id) {
    	this.id = id;
    }

    public Country(String id, String name) {
    	this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format(
                "Country[id=%d, name='%s']",
                id, name);
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
