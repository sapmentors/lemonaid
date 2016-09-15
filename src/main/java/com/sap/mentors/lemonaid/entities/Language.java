package com.sap.mentors.lemonaid.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="languages")
public class Language {
	
	public static final String AA = "aa";
	public static final String AB = "ab";
	public static final String AE = "ae";
	public static final String AF = "af";
	public static final String AK = "ak";
	public static final String AM = "am";
	public static final String AN = "an";
	public static final String AR = "ar";
	public static final String AS = "as"; 
	public static final String AV = "av"; 
	public static final String AY = "ay"; 
	public static final String AZ = "az"; 
	public static final String BA = "ba";
	public static final String BE = "be";
	public static final String BG = "bg";
	public static final String BH = "bh";
	public static final String BI = "bi";
	public static final String BM = "bm";
	public static final String BN = "bn";
	public static final String BO = "bo";
	public static final String BR = "br";
	public static final String BS = "bs";
	public static final String CA = "ca";
	public static final String CE = "ce";
	public static final String CH = "ch";
	public static final String CO = "co";
	public static final String CR = "cr";
	public static final String CS = "cs";
	public static final String CU = "cu";
	public static final String CV = "cv";
	public static final String CY = "cy";
	public static final String DA = "da";
	public static final String DE = "de";
	public static final String DV = "dv";
	public static final String DZ = "dz";
	public static final String EE = "ee";
	public static final String EL = "el";
	public static final String EN = "en";
	public static final String EO = "eo";
	public static final String ES = "es"; 
	public static final String ET = "et";
	public static final String EU = "eu"; 
	public static final String FA = "fa";
	public static final String FF = "ff";
	public static final String FI = "fi";
	public static final String FJ = "fj";
	public static final String FO = "fo";
	public static final String FR = "fr";
	public static final String FY = "fy";
	public static final String GA = "ga"; 
	public static final String GD = "gd";
	public static final String GL = "gl";
	public static final String GN = "gn";
	public static final String GU = "gu";
	public static final String GV = "gv";
	public static final String HA = "ha";
	public static final String HE = "he";
	public static final String HI = "hi";
	public static final String HO = "ho";
	public static final String HR = "hr";
	public static final String HT = "ht";
	public static final String HU = "hu";
	public static final String HY = "hy";
	public static final String HZ = "hz";
	public static final String IA = "ia";
	public static final String ID = "id";
	public static final String IE = "ie";
	public static final String IG = "ig";
	public static final String II = "ii";
	public static final String IK = "ik";
	public static final String IO = "io";
	public static final String IS = "is";
	public static final String IT = "it";
	public static final String IU = "iu";
	public static final String JA = "ja";
	public static final String JV = "jv";
	public static final String KA = "ka";
	public static final String KG = "kg"; 
	public static final String KI = "ki"; 
	public static final String KJ = "kj"; 
	public static final String KK = "kk"; 
	public static final String KL = "kl"; 
	public static final String KM = "km"; 
	public static final String KN = "kn"; 
	public static final String KO = "ko"; 
	public static final String KR = "kr"; 
	public static final String KS = "ks"; 
	public static final String KU = "ku"; 
	public static final String KV = "kv"; 
	public static final String KW = "kw";
	public static final String KY = "ky"; 
	public static final String LA = "la";
	public static final String LB = "lb";
	public static final String LG = "lg";
	public static final String LI = "li";
	public static final String LN = "ln";
	public static final String LO = "lo";
	public static final String LT = "lt";
	public static final String LU = "lu";
	public static final String LV = "lv";
	public static final String MG = "mg";
	public static final String MH = "mh";
	public static final String MI = "mi";
	public static final String MK = "mk";
	public static final String ML = "ml";
	public static final String MN = "mn";
	public static final String MR = "mr";
	public static final String MS = "ms";
	public static final String MT = "mt";
	public static final String MY = "my";
	public static final String NA = "na";
	public static final String NB = "nb";
	public static final String ND = "nd";
	public static final String NE = "ne";
	public static final String NG = "ng";
	public static final String NL = "nl";
	public static final String NN = "nn";
	public static final String NO = "no";
	public static final String NR = "nr";
	public static final String NV = "nv";
	public static final String NY = "ny";
	public static final String OC = "oc";
	public static final String OJ = "oj";
	public static final String OM = "om";
	public static final String OR = "or";
	public static final String OS = "os";
	public static final String PA = "pa";
	public static final String PI = "pi";
	public static final String PL = "pl";
	public static final String PS = "ps";
	public static final String PT = "pt";
	public static final String QU = "qu";
	public static final String RM = "rm";
	public static final String RN = "rn";
	public static final String RO = "ro";
	public static final String RU = "ru";
	public static final String RW = "rw";
	public static final String SA = "sa";
	public static final String SC = "sc";
	public static final String SD = "sd";
	public static final String SE = "se";
	public static final String SG = "sg";
	public static final String SI = "si";
	public static final String SK = "sk";
	public static final String SL = "sl";
	public static final String SM = "sm";
	public static final String SN = "sn";
	public static final String SO = "so";
	public static final String SQ = "sq";
	public static final String SR = "sr";
	public static final String SS = "ss";
	public static final String ST = "st";
	public static final String SU = "su";
	public static final String SV = "sv";
	public static final String SW = "sw";
	public static final String TA = "ta";
	public static final String TE = "te";
	public static final String TG = "tg";
	public static final String TH = "th";
	public static final String TI = "ti";
	public static final String TK = "tk";
	public static final String TL = "tl";
	public static final String TN = "tn";
	public static final String TO = "to";
	public static final String TR = "tr";
	public static final String TS = "ts";
	public static final String TT = "tt";
	public static final String TW = "tw";
	public static final String TY = "ty";
	public static final String UG = "ug";
	public static final String UK = "uk";
	public static final String UR = "ur";
	public static final String UZ = "uz";
	public static final String VE = "ve";
	public static final String VI = "vi";
	public static final String VO = "vo";
	public static final String WA = "wa";
	public static final String WO = "wo";
	public static final String XH = "xh";
	public static final String YI = "yi";
	public static final String YO = "yo";
	public static final String ZA = "za";
	public static final String ZH = "zh";
	public static final String ZU = "zu";
	
	@Id
	private String id;
    private String name;
    private String nativeName;
    
	public Language() {}

    public Language(String id) {
    	this.id = id;
    }

    public Language(String id, String name, String nativeName) {
    	this.id = id;
        this.name = name;
        this.nativeName = nativeName;
    }

    @Override
    public String toString() {
        return String.format(
                "Language[id=%d, name='%s', nativeName='%s']",
                id, name, nativeName);
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

	public String getNativeName() {
		return nativeName;
	}

	public void setNativeName(String nativeName) {
		this.nativeName = nativeName;
	}

}
