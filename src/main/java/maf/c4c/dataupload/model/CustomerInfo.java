    package maf.c4c.dataupload.model;

    import com.fasterxml.jackson.annotation.JsonProperty;
    import maf.c4c.dataupload.service.CountryService;
    import maf.c4c.dataupload.util.JSONUtil;
    import org.apache.commons.csv.CSVRecord;
    import org.apache.log4j.Logger;

    import java.math.BigDecimal;

    public class CustomerInfo {
        final static Logger logger = Logger.getLogger(CustomerInfo.class);
        @JsonProperty("RoleCode")
        private String roleCode;
        @JsonProperty("FirstName")
        private String firstName;
        @JsonProperty("LastName")
        private String lastName;
        @JsonProperty("GenderCode")
        private String genderCode;
        @JsonProperty("LanguageCode")
        private String languageCode;
        @JsonProperty("NationalityCountryCode")
        private String nationalityCountryCode;
        @JsonProperty("CountryCode")
        private String countryCode;
        @JsonProperty("Phone")
        private String phone;
        @JsonProperty("Mobile")
        private String mobile;
        @JsonProperty("Email")
        private String email;
        @JsonProperty("NonSAPExternalSystem_KUT")
        private String nonSAPExternalSystem;
        @JsonProperty("CUST_EXT_ID_KUT")
        private String customrExternalId;

        public String getRoleCode() {
            return roleCode;
        }

        public void setRoleCode(String roleCode) {
            this.roleCode = roleCode;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getGenderCode() {
            return genderCode;
        }

        public void setGenderCode(String genderCode) {
            this.genderCode = genderCode;
        }

        public String getLanguageCode() {
            return languageCode;
        }

        public void setLanguageCode(String languageCode) {
            this.languageCode = languageCode;
        }

        public String getNationalityCountryCode() {
            return nationalityCountryCode;
        }

        public void setNationalityCountryCode(String nationalityCountryCode) {
            this.nationalityCountryCode = nationalityCountryCode;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getNonSAPExternalSystem() {
            return nonSAPExternalSystem;
        }

        public void setNonSAPExternalSystem(String nonSAPExternalSystem) {
            this.nonSAPExternalSystem = nonSAPExternalSystem;
        }

        public String getCustomrExternalId() {
            return customrExternalId;
        }

        public void setCustomrExternalId(String customrExternalId) {
            this.customrExternalId = customrExternalId;
        }

        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public CustomerInfo() {

        }
        public CustomerInfo(CSVRecord record) {
            CountryService countryService = new CountryService();
            try {
                this.setRoleCode(record.get("Role"));
                this.setFirstName( record.get("First_Name"));
                this.setLastName(record.get("Last_Name") );
                this.setNationalityCountryCode(countryService.getCountryCodeByISO(record.get("Nationality")));
                this.setGenderCode(record.get("Gender").equalsIgnoreCase("M") ? "1" : "2");
                this.setLanguageCode(record.get("Language"));
                this.setCountryCode(countryService.getCountryCodeByISO(record.get("Country")));
                String phone = record.get("Phone");
                String mobile = record.get("Mobile");
                this.setPhone(phone.length() > 0 ? formatNumber(phone) : phone);
                this.setMobile(mobile.length() > 0 ? formatNumber(mobile) : mobile);
                this.setEmail("automated_test_final."+record.get("EMail"));
                this.setCustomrExternalId(record.get("External_Key"));
                this.setNonSAPExternalSystem("CDB");
            }
            catch (Exception ex) {
                logger.error("Error while reading the row from CSV "+record, ex);
            }
        }

        private String  formatNumber(String stringNumber) {
            try {
                BigDecimal phoneNo = new BigDecimal(stringNumber);
                return phoneNo.toPlainString();
            }catch(Exception e) {
               logger.error(e.getMessage() + " at formatNumber(String stringNumber)", e);
                return stringNumber;
            }
        }


        @Override
        public String toString() {
            return JSONUtil.toJsonString(this);
        }
    }
