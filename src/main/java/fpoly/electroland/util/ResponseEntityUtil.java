package fpoly.electroland.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseEntityUtil {

    /**
     * Trả về ResponseEntity với dữ liệu và mã trạng thái OK (200).
     *
     * @param <T>  Kiểu dữ liệu của body.
     * @param body Dữ liệu trả về.
     * @return ResponseEntity với body và trạng thái OK.
     */
    public static <T> ResponseEntity<T> ok(T body) {
        return ResponseEntity.ok(body);
    }

    /**
     * Trả về ResponseEntity với mã trạng thái No Content (204).
     *
     * @return ResponseEntity không có body và trạng thái No Content.
     */
    public static ResponseEntity<Void> noContent() {
        return ResponseEntity.noContent().build();
    }

    /**
     * Trả về ResponseEntity với dữ liệu và mã trạng thái Created (201).
     *
     * @param <T>  Kiểu dữ liệu của body.
     * @param body Dữ liệu trả về.
     * @return ResponseEntity với body và trạng thái Created.
     */
    public static <T> ResponseEntity<T> created(T body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    /**
     * Trả về ResponseEntity với mã trạng thái Bad Request (400).
     *
     * @param message Thông điệp lỗi.
     * @return ResponseEntity với thông điệp lỗi và trạng thái Bad Request.
     */
    public static ResponseEntity<String> badRequest(String message) {
        return ResponseEntity.badRequest().body(message);
    }

    /**
     * Trả về ResponseEntity với mã trạng thái Internal Server Error (500).
     *
     * @param message Thông điệp lỗi.
     * @return ResponseEntity với thông điệp lỗi và trạng thái Internal Server
     *         Error.
     */
    public static ResponseEntity<String> internalServerError(String message) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
    }

    /**
     * Trả về ResponseEntity với dữ liệu và mã trạng thái tùy chỉnh.
     *
     * @param <T>    Kiểu dữ liệu của body.
     * @param body   Dữ liệu trả về.
     * @param status Mã trạng thái HTTP.
     * @return ResponseEntity với body và trạng thái tùy chỉnh.
     */
    public static <T> ResponseEntity<T> withStatus(T body, HttpStatus status) {
        return ResponseEntity.status(status).body(body);
    }

    /**
     * Trả về ResponseEntity với mã trạng thái UNAUTHORIZED (401).
     *
     * @param message Thông điệp lỗi.
     * @return ResponseEntity với thông điệp lỗi và trạng thái UNAUTHORIZED
     *         Error.
     */
    public static ResponseEntity<String> unauthorizedError(String message) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
    }
}
