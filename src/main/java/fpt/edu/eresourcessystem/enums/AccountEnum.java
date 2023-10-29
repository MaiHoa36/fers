package fpt.edu.eresourcessystem.enums;

import java.util.ArrayList;
import java.util.List;

public class AccountEnum {

    public enum AccountType {
        SYSTEM_ACC,
        FPT_MAIL_ACC,
        FEID_ACC;
    }

    public enum Status {
        ACTIVE("Active"),
        INACTIVE("Inactive");

        private final String displayValue;

        Status(String displayValue) {
            this.displayValue = displayValue;
        }

        public String getDisplayValue() {
            return displayValue;
        }
    }
    public enum Role {
        STUDENT("STUDENT"),
        LECTURER("LECTURER"),
        LIBRARIAN("LIBRARIAN"),
        ADMIN("ADMIN");
        private final String displayValue;
        Role(String displayValue) {
            this.displayValue = displayValue;
        }
        public String getDisplayValue() {
            return displayValue;
        }
    }

    public enum Campus {
        HN("FU-HOA LAC"),
        HCM("FU-HO CHI MINH"),
        DN("FU-DA NANG"),
        CT("FU-CAN THO"),
        QN("FU-QUY NHON");

        private final String displayValue;

        Campus(String displayValue) {
            this.displayValue = displayValue;
        }

        public String getDisplayValue() {
            return displayValue;
        }

    }
    public enum Gender {
        M("Male"),
        F("Female");
//        O("Other");
        private final String displayValue;

        Gender(String displayValue) {
            this.displayValue = displayValue;
        }

        public String getDisplayValue() {
            return displayValue;
        }
    }

    public static List<String> getListCampus(){
        List<String> displayValues = new ArrayList<>();
        for (Campus campus : Campus.values()) {
            displayValues.add(campus.getDisplayValue());
        }
        return displayValues;
    }
}
