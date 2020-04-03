package maf.c4c.dataupload.service;

import maf.c4c.dataupload.model.Country;
import java.io.IOException;
import java.util.HashMap;

public class CountryService {
    public static void main(String[] args)  {
       System.out.println(new CountryService().getCountryCodeByISO("NZL"));
    }

    public String getCountryCodeByISO(String countryISO) {
        if(countryISO.length() >0 ) {
            Country country = this.getCountryMap().get(countryISO);
            if(country !=null) {
                return country.getCode();
            }
            return countryISO;
        } else {
            return countryISO;
        }
    }

    public HashMap<String, Country> getCountryMap() {
        HashMap<String, Country> countryMap =new HashMap<>();
        countryMap.put("AFG",new Country("Afghanistan","AF","AFG",4));
        countryMap.put("ALB",new Country("Albania","AL","ALB",8));
        countryMap.put("DZA",new Country("Algeria","DZ","DZA",12));
        countryMap.put("ASM",new Country("American Samoa","AS","ASM",16));
        countryMap.put("AND",new Country("Andorra","AD","AND",20));
        countryMap.put("AGO",new Country("Angola","AO","AGO",24));
        countryMap.put("AIA",new Country("Anguilla","AI","AIA",660));
        countryMap.put("ATA",new Country("Antarctica","AQ","ATA",10));
        countryMap.put("ATG",new Country("Antigua and Barbuda","AG","ATG",28));
        countryMap.put("ARG",new Country("Argentina","AR","ARG",32));
        countryMap.put("ARM",new Country("Armenia","AM","ARM",51));
        countryMap.put("ABW",new Country("Aruba","AW","ABW",533));
        countryMap.put("AUS",new Country("Australia","AU","AUS",36));
        countryMap.put("AUT",new Country("Austria","AT","AUT",40));
        countryMap.put("AZE",new Country("Azerbaijan","AZ","AZE",31));
        countryMap.put("BHS",new Country("Bahamas (the)","BS","BHS",44));
        countryMap.put("BHR",new Country("Bahrain","BH","BHR",48));
        countryMap.put("BGD",new Country("Bangladesh","BD","BGD",50));
        countryMap.put("BRB",new Country("Barbados","BB","BRB",52));
        countryMap.put("BLR",new Country("Belarus","BY","BLR",112));
        countryMap.put("BEL",new Country("Belgium","BE","BEL",56));
        countryMap.put("BLZ",new Country("Belize","BZ","BLZ",84));
        countryMap.put("BEN",new Country("Benin","BJ","BEN",204));
        countryMap.put("BMU",new Country("Bermuda","BM","BMU",60));
        countryMap.put("BTN",new Country("Bhutan","BT","BTN",64));
        countryMap.put("BOL",new Country("Bolivia (Plurinational State of)","BO","BOL",68));
        countryMap.put("BES",new Country("Bonaire, Sint Eustatius and Saba","BQ","BES",535));
        countryMap.put("BIH",new Country("Bosnia and Herzegovina","BA","BIH",70));
        countryMap.put("BWA",new Country("Botswana","BW","BWA",72));
        countryMap.put("BVT",new Country("Bouvet Island","BV","BVT",74));
        countryMap.put("BRA",new Country("Brazil","BR","BRA",76));
        countryMap.put("IOT",new Country("British Indian Ocean Territory (the)","IO","IOT",86));
        countryMap.put("BRN",new Country("Brunei Darussalam","BN","BRN",96));
        countryMap.put("BGR",new Country("Bulgaria","BG","BGR",100));
        countryMap.put("BFA",new Country("Burkina Faso","BF","BFA",854));
        countryMap.put("BDI",new Country("Burundi","BI","BDI",108));
        countryMap.put("CPV",new Country("Cabo Verde","CV","CPV",132));
        countryMap.put("KHM",new Country("Cambodia","KH","KHM",116));
        countryMap.put("CMR",new Country("Cameroon","CM","CMR",120));
        countryMap.put("CAN",new Country("Canada","CA","CAN",124));
        countryMap.put("CYM",new Country("Cayman Islands (the)","KY","CYM",136));
        countryMap.put("CAF",new Country("Central African Republic (the)","CF","CAF",140));
        countryMap.put("TCD",new Country("Chad","TD","TCD",148));
        countryMap.put("CHL",new Country("Chile","CL","CHL",152));
        countryMap.put("CHN",new Country("China","CN","CHN",156));
        countryMap.put("CXR",new Country("Christmas Island","CX","CXR",162));
        countryMap.put("CCK",new Country("Cocos (Keeling) Islands (the)","CC","CCK",166));
        countryMap.put("COL",new Country("Colombia","CO","COL",170));
        countryMap.put("COM",new Country("Comoros (the)","KM","COM",174));
        countryMap.put("COD",new Country("Congo (the Democratic Republic of the)","CD","COD",180));
        countryMap.put("COG",new Country("Congo (the)","CG","COG",178));
        countryMap.put("COK",new Country("Cook Islands (the)","CK","COK",184));
        countryMap.put("CRI",new Country("Costa Rica","CR","CRI",188));
        countryMap.put("HRV",new Country("Croatia","HR","HRV",191));
        countryMap.put("CUB",new Country("Cuba","CU","CUB",192));
        countryMap.put("CUW",new Country("Curaçao","CW","CUW",531));
        countryMap.put("CYP",new Country("Cyprus","CY","CYP",196));
        countryMap.put("CZE",new Country("Czechia","CZ","CZE",203));
        countryMap.put("CIV",new Country("Côte d'Ivoire","CI","CIV",384));
        countryMap.put("DNK",new Country("Denmark","DK","DNK",208));
        countryMap.put("DJI",new Country("Djibouti","DJ","DJI",262));
        countryMap.put("DMA",new Country("Dominica","DM","DMA",212));
        countryMap.put("DOM",new Country("Dominican Republic (the)","DO","DOM",214));
        countryMap.put("ECU",new Country("Ecuador","EC","ECU",218));
        countryMap.put("EGY",new Country("Egypt","EG","EGY",818));
        countryMap.put("SLV",new Country("El Salvador","SV","SLV",222));
        countryMap.put("GNQ",new Country("Equatorial Guinea","GQ","GNQ",226));
        countryMap.put("ERI",new Country("Eritrea","ER","ERI",232));
        countryMap.put("EST",new Country("Estonia","EE","EST",233));
        countryMap.put("SWZ",new Country("Eswatini","SZ","SWZ",748));
        countryMap.put("ETH",new Country("Ethiopia","ET","ETH",231));
        countryMap.put("FLK",new Country("Falkland Islands (the) [Malvinas]","FK","FLK",238));
        countryMap.put("FRO",new Country("Faroe Islands (the)","FO","FRO",234));
        countryMap.put("FJI",new Country("Fiji","FJ","FJI",242));
        countryMap.put("FIN",new Country("Finland","FI","FIN",246));
        countryMap.put("FRA",new Country("France","FR","FRA",250));
        countryMap.put("GUF",new Country("French Guiana","GF","GUF",254));
        countryMap.put("PYF",new Country("French Polynesia","PF","PYF",258));
        countryMap.put("ATF",new Country("French Southern Territories (the)","TF","ATF",260));
        countryMap.put("GAB",new Country("Gabon","GA","GAB",266));
        countryMap.put("GMB",new Country("Gambia (the)","GM","GMB",270));
        countryMap.put("GEO",new Country("Georgia","GE","GEO",268));
        countryMap.put("DEU",new Country("Germany","DE","DEU",276));
        countryMap.put("GHA",new Country("Ghana","GH","GHA",288));
        countryMap.put("GIB",new Country("Gibraltar","GI","GIB",292));
        countryMap.put("GRC",new Country("Greece","GR","GRC",300));
        countryMap.put("GRL",new Country("Greenland","GL","GRL",304));
        countryMap.put("GRD",new Country("Grenada","GD","GRD",308));
        countryMap.put("GLP",new Country("Guadeloupe","GP","GLP",312));
        countryMap.put("GUM",new Country("Guam","GU","GUM",316));
        countryMap.put("GTM",new Country("Guatemala","GT","GTM",320));
        countryMap.put("GGY",new Country("Guernsey","GG","GGY",831));
        countryMap.put("GIN",new Country("Guinea","GN","GIN",324));
        countryMap.put("GNB",new Country("Guinea-Bissau","GW","GNB",624));
        countryMap.put("GUY",new Country("Guyana","GY","GUY",328));
        countryMap.put("HTI",new Country("Haiti","HT","HTI",332));
        countryMap.put("HMD",new Country("Heard Island and McDonald Islands","HM","HMD",334));
        countryMap.put("VAT",new Country("Holy See (the)","VA","VAT",336));
        countryMap.put("HND",new Country("Honduras","HN","HND",340));
        countryMap.put("HKG",new Country("Hong Kong","HK","HKG",344));
        countryMap.put("HUN",new Country("Hungary","HU","HUN",348));
        countryMap.put("ISL",new Country("Iceland","IS","ISL",352));
        countryMap.put("IND",new Country("India","IN","IND",356));
        countryMap.put("IDN",new Country("Indonesia","ID","IDN",360));
        countryMap.put("IRN",new Country("Iran (Islamic Republic of)","IR","IRN",364));
        countryMap.put("IRQ",new Country("Iraq","IQ","IRQ",368));
        countryMap.put("IRL",new Country("Ireland","IE","IRL",372));
        countryMap.put("IMN",new Country("Isle of Man","IM","IMN",833));
        countryMap.put("ISR",new Country("Israel","IL","ISR",376));
        countryMap.put("ITA",new Country("Italy","IT","ITA",380));
        countryMap.put("JAM",new Country("Jamaica","JM","JAM",388));
        countryMap.put("JPN",new Country("Japan","JP","JPN",392));
        countryMap.put("JEY",new Country("Jersey","JE","JEY",832));
        countryMap.put("JOR",new Country("Jordan","JO","JOR",400));
        countryMap.put("KAZ",new Country("Kazakhstan","KZ","KAZ",398));
        countryMap.put("KEN",new Country("Kenya","KE","KEN",404));
        countryMap.put("KIR",new Country("Kiribati","KI","KIR",296));
        countryMap.put("PRK",new Country("Korea (the Democratic People's Republic of)","KP","PRK",408));
        countryMap.put("KOR",new Country("Korea (the Republic of)","KR","KOR",410));
        countryMap.put("KWT",new Country("Kuwait","KW","KWT",414));
        countryMap.put("KGZ",new Country("Kyrgyzstan","KG","KGZ",417));
        countryMap.put("LAO",new Country("Lao People's Democratic Republic (the)","LA","LAO",418));
        countryMap.put("LVA",new Country("Latvia","LV","LVA",428));
        countryMap.put("LBN",new Country("Lebanon","LB","LBN",422));
        countryMap.put("LSO",new Country("Lesotho","LS","LSO",426));
        countryMap.put("LBR",new Country("Liberia","LR","LBR",430));
        countryMap.put("LBY",new Country("Libya","LY","LBY",434));
        countryMap.put("LIE",new Country("Liechtenstein","LI","LIE",438));
        countryMap.put("LTU",new Country("Lithuania","LT","LTU",440));
        countryMap.put("LUX",new Country("Luxembourg","LU","LUX",442));
        countryMap.put("MAC",new Country("Macao","MO","MAC",446));
        countryMap.put("MDG",new Country("Madagascar","MG","MDG",450));
        countryMap.put("MWI",new Country("Malawi","MW","MWI",454));
        countryMap.put("MYS",new Country("Malaysia","MY","MYS",458));
        countryMap.put("MDV",new Country("Maldives","MV","MDV",462));
        countryMap.put("MLI",new Country("Mali","ML","MLI",466));
        countryMap.put("MLT",new Country("Malta","MT","MLT",470));
        countryMap.put("MHL",new Country("Marshall Islands (the)","MH","MHL",584));
        countryMap.put("MTQ",new Country("Martinique","MQ","MTQ",474));
        countryMap.put("MRT",new Country("Mauritania","MR","MRT",478));
        countryMap.put("MUS",new Country("Mauritius","MU","MUS",480));
        countryMap.put("MYT",new Country("Mayotte","YT","MYT",175));
        countryMap.put("MEX",new Country("Mexico","MX","MEX",484));
        countryMap.put("FSM",new Country("Micronesia (Federated States of)","FM","FSM",583));
        countryMap.put("MDA",new Country("Moldova (the Republic of)","MD","MDA",498));
        countryMap.put("MCO",new Country("Monaco","MC","MCO",492));
        countryMap.put("MNG",new Country("Mongolia","MN","MNG",496));
        countryMap.put("MNE",new Country("Montenegro","ME","MNE",499));
        countryMap.put("MSR",new Country("Montserrat","MS","MSR",500));
        countryMap.put("MAR",new Country("Morocco","MA","MAR",504));
        countryMap.put("MOZ",new Country("Mozambique","MZ","MOZ",508));
        countryMap.put("MMR",new Country("Myanmar","MM","MMR",104));
        countryMap.put("NAM",new Country("Namibia","NA","NAM",516));
        countryMap.put("NRU",new Country("Nauru","NR","NRU",520));
        countryMap.put("NPL",new Country("Nepal","NP","NPL",524));
        countryMap.put("NLD",new Country("Netherlands (the)","NL","NLD",528));
        countryMap.put("NCL",new Country("New Caledonia","NC","NCL",540));
        countryMap.put("NZL",new Country("New Zealand","NZ","NZL",554));
        countryMap.put("NIC",new Country("Nicaragua","NI","NIC",558));
        countryMap.put("NER",new Country("Niger (the)","NE","NER",562));
        countryMap.put("NGA",new Country("Nigeria","NG","NGA",566));
        countryMap.put("NIU",new Country("Niue","NU","NIU",570));
        countryMap.put("NFK",new Country("Norfolk Island","NF","NFK",574));
        countryMap.put("MNP",new Country("Northern Mariana Islands (the)","MP","MNP",580));
        countryMap.put("NOR",new Country("Norway","NO","NOR",578));
        countryMap.put("OMN",new Country("Oman","OM","OMN",512));
        countryMap.put("PAK",new Country("Pakistan","PK","PAK",586));
        countryMap.put("PLW",new Country("Palau","PW","PLW",585));
        countryMap.put("PSE",new Country("Palestine, State of","PS","PSE",275));
        countryMap.put("PAN",new Country("Panama","PA","PAN",591));
        countryMap.put("PNG",new Country("Papua New Guinea","PG","PNG",598));
        countryMap.put("PRY",new Country("Paraguay","PY","PRY",600));
        countryMap.put("PER",new Country("Peru","PE","PER",604));
        countryMap.put("PHL",new Country("Philippines (the)","PH","PHL",608));
        countryMap.put("PCN",new Country("Pitcairn","PN","PCN",612));
        countryMap.put("POL",new Country("Poland","PL","POL",616));
        countryMap.put("PRT",new Country("Portugal","PT","PRT",620));
        countryMap.put("PRI",new Country("Puerto Rico","PR","PRI",630));
        countryMap.put("QAT",new Country("Qatar","QA","QAT",634));
        countryMap.put("MKD",new Country("Republic of North Macedonia","MK","MKD",807));
        countryMap.put("ROU",new Country("Romania","RO","ROU",642));
        countryMap.put("RUS",new Country("Russian Federation (the)","RU","RUS",643));
        countryMap.put("RWA",new Country("Rwanda","RW","RWA",646));
        countryMap.put("REU",new Country("Réunion","RE","REU",638));
        countryMap.put("BLM",new Country("Saint Barthélemy","BL","BLM",652));
        countryMap.put("SHN",new Country("Saint Helena, Ascension and Tristan da Cunha","SH","SHN",654));
        countryMap.put("KNA",new Country("Saint Kitts and Nevis","KN","KNA",659));
        countryMap.put("LCA",new Country("Saint Lucia","LC","LCA",662));
        countryMap.put("MAF",new Country("Saint Martin (French part)","MF","MAF",663));
        countryMap.put("SPM",new Country("Saint Pierre and Miquelon","PM","SPM",666));
        countryMap.put("VCT",new Country("Saint Vincent and the Grenadines","VC","VCT",670));
        countryMap.put("WSM",new Country("Samoa","WS","WSM",882));
        countryMap.put("SMR",new Country("San Marino","SM","SMR",674));
        countryMap.put("STP",new Country("Sao Tome and Principe","ST","STP",678));
        countryMap.put("SAU",new Country("Saudi Arabia","SA","SAU",682));
        countryMap.put("SEN",new Country("Senegal","SN","SEN",686));
        countryMap.put("SRB",new Country("Serbia","RS","SRB",688));
        countryMap.put("SYC",new Country("Seychelles","SC","SYC",690));
        countryMap.put("SLE",new Country("Sierra Leone","SL","SLE",694));
        countryMap.put("SGP",new Country("Singapore","SG","SGP",702));
        countryMap.put("SXM",new Country("Sint Maarten (Dutch part)","SX","SXM",534));
        countryMap.put("SVK",new Country("Slovakia","SK","SVK",703));
        countryMap.put("SVN",new Country("Slovenia","SI","SVN",705));
        countryMap.put("SLB",new Country("Solomon Islands","SB","SLB",90));
        countryMap.put("SOM",new Country("Somalia","SO","SOM",706));
        countryMap.put("ZAF",new Country("South Africa","ZA","ZAF",710));
        countryMap.put("SGS",new Country("South Georgia and the South Sandwich Islands","GS","SGS",239));
        countryMap.put("SSD",new Country("South Sudan","SS","SSD",728));
        countryMap.put("ESP",new Country("Spain","ES","ESP",724));
        countryMap.put("LKA",new Country("Sri Lanka","LK","LKA",144));
        countryMap.put("SDN",new Country("Sudan (the)","SD","SDN",729));
        countryMap.put("SUR",new Country("Suriname","SR","SUR",740));
        countryMap.put("SJM",new Country("Svalbard and Jan Mayen","SJ","SJM",744));
        countryMap.put("SWE",new Country("Sweden","SE","SWE",752));
        countryMap.put("CHE",new Country("Switzerland","CH","CHE",756));
        countryMap.put("SYR",new Country("Syrian Arab Republic","SY","SYR",760));
        countryMap.put("TWN",new Country("Taiwan (Province of China)","TW","TWN",158));
        countryMap.put("TJK",new Country("Tajikistan","TJ","TJK",762));
        countryMap.put("TZA",new Country("Tanzania, United Republic of","TZ","TZA",834));
        countryMap.put("THA",new Country("Thailand","TH","THA",764));
        countryMap.put("TLS",new Country("Timor-Leste","TL","TLS",626));
        countryMap.put("TGO",new Country("Togo","TG","TGO",768));
        countryMap.put("TKL",new Country("Tokelau","TK","TKL",772));
        countryMap.put("TON",new Country("Tonga","TO","TON",776));
        countryMap.put("TTO",new Country("Trinidad and Tobago","TT","TTO",780));
        countryMap.put("TUN",new Country("Tunisia","TN","TUN",788));
        countryMap.put("TUR",new Country("Turkey","TR","TUR",792));
        countryMap.put("TKM",new Country("Turkmenistan","TM","TKM",795));
        countryMap.put("TCA",new Country("Turks and Caicos Islands (the)","TC","TCA",796));
        countryMap.put("TUV",new Country("Tuvalu","TV","TUV",798));
        countryMap.put("UGA",new Country("Uganda","UG","UGA",800));
        countryMap.put("UKR",new Country("Ukraine","UA","UKR",804));
        countryMap.put("ARE",new Country("United Arab Emirates (the)","AE","ARE",784));
        countryMap.put("GBR",new Country("United Kingdom of Great Britain and Northern Ireland (the)","GB","GBR",826));
        countryMap.put("UMI",new Country("United States Minor Outlying Islands (the)","UM","UMI",581));
        countryMap.put("USA",new Country("United States of America (the)","US","USA",840));
        countryMap.put("URY",new Country("Uruguay","UY","URY",858));
        countryMap.put("UZB",new Country("Uzbekistan","UZ","UZB",860));
        countryMap.put("VUT",new Country("Vanuatu","VU","VUT",548));
        countryMap.put("VEN",new Country("Venezuela (Bolivarian Republic of)","VE","VEN",862));
        countryMap.put("VNM",new Country("Viet Nam","VN","VNM",704));
        countryMap.put("VGB",new Country("Virgin Islands (British)","VG","VGB",92));
        countryMap.put("VIR",new Country("Virgin Islands (U.S.)","VI","VIR",850));
        countryMap.put("WLF",new Country("Wallis and Futuna","WF","WLF",876));
        countryMap.put("ESH",new Country("Western Sahara","EH","ESH",732));
        countryMap.put("YEM",new Country("Yemen","YE","YEM",887));
        countryMap.put("ZMB",new Country("Zambia","ZM","ZMB",894));
        countryMap.put("ZWE",new Country("Zimbabwe","ZW","ZWE",716));
        countryMap.put("ALA",new Country("Åland Islands","AX","ALA",248));
        return countryMap;
    }
}
