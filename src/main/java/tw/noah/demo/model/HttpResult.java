package tw.noah.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpResult {

    public enum Status {
        success, failure;
    }

    /**
     * response message
     */
    private Status status;

    /**
     * response main data
     */
    private Object data;

}

