package tw.noah.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {

    public enum Status {
        success, failure;
    }

    private Status status;

    private Object data;

}

