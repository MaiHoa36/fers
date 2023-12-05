package fpt.edu.eresourcessystem.enums;

public class CourseEnum {
    public enum Status {

        NEW("New"),
        PUBLISH("Publish"),
        DRAFT("Draft"),
        HIDE("Hide");
        private final String displayValue;

        Status(String displayValue) {
            this.displayValue = displayValue;
        }

        public String getDisplayValue() {
            return displayValue;
        }
    }

    public enum ChangeableStatus {
        PUBLISH("Publish"),
        DRAFT("Draft"),
        HIDE("Hide");
        private final String displayValue;

        ChangeableStatus(String displayValue) {
            this.displayValue = displayValue;
        }

        public String getDisplayValue() {
            return displayValue;
        }
    }




}
