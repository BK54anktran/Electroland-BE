package fpoly.electroland.restController;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.MediaType;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/payment")
public class MoMoController {
    @PostMapping("/create")
    public ResponseEntity<Object> createPayment(@RequestBody Map<String, Object> requestData) {

        String momoUrl = "https://test-payment.momo.vn/v2/gateway/api/create";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestData, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                momoUrl,
                HttpMethod.POST,
                requestEntity,
                Map.class);

        // Ép kiểu về Map<String, String>
        Map<String, String> resultMap = (Map<String, String>) response.getBody();
        System.out.println(resultMap);
        return ResponseEntity.ok().body(resultMap);
    }

    @PostMapping("/callback")
    public ResponseEntity<String> momoCallback(@RequestBody Map<String, Object> callbackData) {
        System.out.println("Nhận callback từ MoMo: " + callbackData);

        // Kiểm tra trạng thái giao dịch
        String resultCode = callbackData.get("resultCode").toString();
        if ("0".equals(resultCode)) {
            System.out.println("Thanh toán thành công!");
            // Cập nhật trạng thái đơn hàng trong DB
        } else {
            System.out.println("Thanh toán thất bại! Mã lỗi: " + resultCode);
        }

        return ResponseEntity.ok("Received callback");
    }

    @GetMapping("/status")
    public ResponseEntity<String> checkPaymentStatus(@RequestParam String orderId) throws Exception {
        String momoUrl = "https://test-payment.momo.vn/v2/gateway/api/query";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Thay thế bằng thông tin thực tế của bạn
        String partnerCode = "MOMO";
        String accessKey = "F8BBA842ECF85";
        String secretKey = "K951B6PE1waDMi640xX08PD3vg6EkVlz";
        String requestId = partnerCode + System.currentTimeMillis();

        // Tạo rawData để ký
        String rawData = "accessKey=" + accessKey
                + "&orderId=" + orderId
                + "&partnerCode=" + partnerCode
                + "&requestId=" + requestId;

        // Tạo chữ ký SHA256
        String signature = hmacSHA256(rawData, secretKey);

        // Tạo request body
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("partnerCode", partnerCode);
        requestData.put("accessKey", accessKey);
        requestData.put("requestId", requestId);
        requestData.put("orderId", orderId);
        requestData.put("lang", "vi");
        requestData.put("signature", signature);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestData, headers);

        ResponseEntity<String> response = restTemplate.exchange(momoUrl, HttpMethod.POST, requestEntity, String.class);

        return ResponseEntity.ok().body(response.getBody());
    }

    public static String hmacSHA256(String data, String key) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "HmacSHA256");
        mac.init(secretKeySpec);
        byte[] hmacData = mac.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(hmacData);
    }
}
