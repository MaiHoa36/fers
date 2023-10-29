package fpt.edu.eresourcessystem.enums;

public class DocumentEnum {

    public enum DocumentStatusEnum {
        DISPLAY("1", "display"),
        HIDE("0", "hide");

        private final String displayValue;

        private final String toString;

        DocumentStatusEnum(String displayValue, String toString) {
            this.displayValue = displayValue;
            this.toString = toString;
        }
    }

    public enum DefaultCourseResourceTypes {
        MATERIALS("Materials");

        private final String displayValue;

        DefaultCourseResourceTypes(String displayValue) {
            this.displayValue = displayValue;
        }

        public String getDisplayValue() {
            return displayValue;
        }
    }

    public enum DefaultTopicResourceTypes {
        LECTURE_NOTE("Lecture note"),
        READING("Reading"),
        ASSIGNMENT("Assignment"),
        EXERCISE("Exercise"),
        COMMON_MATERIAL("Common material"); // default, auto save in Materials

        private final String displayValue;

        DefaultTopicResourceTypes(String displayValue) {
            this.displayValue = displayValue;
        }

        public String getDisplayValue() {
            return displayValue;
        }
    }

    public enum DocumentFormat {
        PDF,
        DOC,
        DOCX,
        PPT,
        PPTX,
        XLS,
        XLSX,
        MD,
        HTML,
        TXT;

        public static DocumentFormat getDocType(String suffixName) {
            return switch (suffixName) {
                case ".pdf" -> PDF;
                case ".doc" -> DOC;
                case ".docx" -> DOCX;
                case ".ppt" -> PPT;
                case ".pptx" -> PPTX;
                case ".xls" -> XLS;
                case ".xlsx" -> XLSX;
                case ".md" -> MD;
                case ".html" -> HTML;
                case ".txt" -> TXT;
                default -> throw new IllegalStateException("Unexpected document suffix name: " + suffixName);
            };
        }
    }

    // ADD IMAGES + COMMON FILE ZIP, EXE...

    public enum AudioFileFormat {
        M4A,
        FLAC,
        MP3,
        MP4,
        WAV,
        WMA,
        AAC;

        public static AudioFileFormat getAudioType(String suffixName) {
            return switch (suffixName) {
                case ".m4a" -> M4A;
                case ".flac" -> FLAC;
                case ".mp3" -> MP3;
                case ".mp4" -> MP4;
                case ".wav" -> WAV;
                case ".wma" -> WMA;
                case ".aac" -> AAC;
                default -> throw new IllegalStateException("Unexpected audio suffix name: " + suffixName);
            };
        }
    }

    public enum VideoFileFormat {
        MP4,
        MOV,
        AVI,
        FLV,
        MKV,
        WEBM;

        public static VideoFileFormat getVideoType(String suffixName) {
            return switch (suffixName) {
                case ".mp4" -> MP4;
                case ".mov" -> MOV;
                case ".avi" -> AVI;
                case ".flv" -> FLV;
                case ".mkv" -> MKV;
                case ".webm" -> WEBM;
                default -> throw new IllegalStateException("Unexpected video suffix name: " + suffixName);
            };
        }
    }
}
