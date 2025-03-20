package fpoly.electroland.restController;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
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

    private final String secretKey = "K951B6PE1waDMi640xX08PD3vg6EkVlz"; // 🔹 Thay bằng secretKey của bạn

    @PostMapping("/check")
    public ResponseEntity<Map<String, Object>> checkMomoPayment(@RequestBody Map<String, String> payload) {
        try {
            // Lấy thông tin từ payload
            String partnerCode = "MOMO";
            String orderId = payload.get("orderId");
            String requestId = payload.get("requestId");
            String signature = payload.get("signature");
          

            // Tạo chữ ký từ rawSignature

            // Tạo request JSON gửi lên MoMo
            JSONObject requestData = new JSONObject();
            requestData.put("partnerCode", partnerCode);
            requestData.put("orderId", orderId);
            requestData.put("partnerCode",partnerCode);
            requestData.put("requestId", requestId);
            requestData.put("signature", signature);

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(requestData.toString(), headers);
            ResponseEntity<String> response = restTemplate.exchange(
                    "https://test-payment.momo.vn/v2/gateway/api/query", HttpMethod.POST, entity, String.class);


            // Chuyển response body thành JSON
            JSONObject responseBody = new JSONObject(response.getBody());
            int resultCode = responseBody.getInt("resultCode");
            // Chuẩn bị phản hồi cho Frontend
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("resultCode", resultCode);
            responseMap.put("message", responseBody.getString("message"));
            responseMap.put("orderId", responseBody.getString("orderId"));
            responseMap.put("requestId", responseBody.getString("requestId"));
            responseMap.put("amount", responseBody.optInt("amount", 0));

            return ResponseEntity.ok(responseMap);

        } catch (Exception e) {
            // // Xử lý lỗi nếu có
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Lỗi khi kiểm tra MoMo: " + e.getMessage());
            // return
            // ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
            return null;
        }

    }

    // Tạo signature HMAC-SHA256 với đầu ra HEX
    public static String hmacSHA256(String data, String key) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        mac.init(secretKeySpec);
        byte[] hmacData = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));

        // Chuyển đổi sang HEX (giống FE)
        StringBuilder hexString = new StringBuilder();
        for (byte b : hmacData) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
}
