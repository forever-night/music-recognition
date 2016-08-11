package musicrecognition.util;

public class Global {
    public enum Message {
        SUCCESS ("Success!"),
        FIELD_EMPTY ("One of the fields is empty"),
        UNSUPPORTED_TYPE ("Unsupported file type"),
        INTERNAL_ERROR ("Internal server error"),
        UNEXPECTED ("Unexpected error"),
        ERROR ("Error");
        
        
        private final String message;
        
        Message(String message) {
            this.message = message;
        }
        
        public String getMessage() {
            return this.message;
        }
    }
    
    public static final int DEFAULT_SAMPLE_RATE = 41000;
    public static final int MAX_FILE_SIZE = 15728640;       // 15 MB
    public static final int MAX_FINGERPRINT_PAIRS = 10;
    public static final int MAX_FINGERPRINT_MATCHES = 5;
}
