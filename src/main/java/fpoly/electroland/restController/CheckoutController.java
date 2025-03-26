package fpoly.electroland.restController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import fpoly.electroland.dto.request.ReceiptRequest;
import fpoly.electroland.service.PaymentService;
import fpoly.electroland.service.PaymentTypeService;
import fpoly.electroland.service.ReceiptService;

@RestController
public class CheckoutController {

    @Autowired
    ReceiptService receiptService;

    @Autowired
    PaymentService paymentService;

    @Autowired
    PaymentTypeService paymentTypeService;

    @PostMapping("/checkout")
    public Object Checkout(@RequestBody ReceiptRequest receiptRequest) {
        // System.out.println(receiptRequest.toString());
        return receiptService.createCart(receiptRequest);
    }

    @SuppressWarnings("deprecation")
    @GetMapping("/checkTransaction")
    public ResponseEntity<String> checkTransaction(@RequestParam int amount, @RequestParam String content,
            @RequestParam String timeStart) {
        String account_number = "0865854002";
        String transaction_date_min = timeStart;
        int amount_in = amount;
        String urlString = "https://my.sepay.vn/userapi/transactions/list?account_number=" + account_number
                + "&amount_in=" + amount_in + "&transaction_date_min=" + transaction_date_min;
        // System.out.println(urlString);
        StringBuilder response = new StringBuilder();

        try {
            URL url = new URL(urlString);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

            // Thiết lập phương thức và các header
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization",
                    "Bearer G7ET1NH8COKRYZSNQ8VBGJP5VLALJ4PSBDICQ9R3UMBTITUG5FEOJN9A74HFRCQ6");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            conn.setRequestProperty("Accept-Encoding", "gzip, deflate, br");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                }
            } else {
                return ResponseEntity.status(responseCode).body("GET request failed: " + responseCode);
            }

            // Xử lý phản hồi JSON
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(response.toString());

            // Kiểm tra `transaction_content`
            JsonNode contentNode = node.findValue("transaction_content");
            if (contentNode != null && contentNode.asText().contains(content)) {
                return ResponseEntity.ok("true");
            } else {
                return ResponseEntity.ok("false");
            }

        } catch (Exception e) {
            // Ghi lỗi vào log và trả về phản hồi lỗi hợp lý
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }
}
