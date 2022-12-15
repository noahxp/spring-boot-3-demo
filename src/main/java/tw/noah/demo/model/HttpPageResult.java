package tw.noah.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpPageResult extends HttpResult {

    private Page page;

    public HttpPageResult(Status status, Object data, Page page) {
        super(status, data);
        this.page = page;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Page {

        /**
         * 總頁數
         */
        private int total;
        /**
         * 目前頁碼
         */
        private int index;
        /**
         * 每頁筆數
         */
        private int size;

        /**
         * 目前分頁筆數
         */
        private int rows;

        /**
         * 總筆數
         */
        private int totalRows;


    }
}

