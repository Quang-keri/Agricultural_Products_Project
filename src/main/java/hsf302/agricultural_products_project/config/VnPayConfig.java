package hsf302.agricultural_products_project.config;

import hsf302.agricultural_products_project.dto.PaymentRequest;
import hsf302.agricultural_products_project.dto.PaymentResponse;
import hsf302.agricultural_products_project.dto.PaymentVerification;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class VnPayConfig {
    private static final Logger log = LoggerFactory.getLogger(VnPayConfig.class);

    public static final String vnp_Version = "2.1.0";
    public static final String vnp_Command = "pay";
    public static final String vnp_PayUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    public static final String vnp_ReturnUrl = "http://localhost:8333/payment/vnpayReturn";

    public static final String vnp_TmnCode = "4YUP19I4";
    public static final String vnp_HashSecret = "MDUIFDCRAKLNBPOFIAFNEKFRNMFBYEPX";

    public PaymentResponse createPaymentUrl(PaymentRequest request) {
        try {
            Map<String, String> params = buildPaymentParams(request);
            String queryUrl = buildQueryUrl(params);
            return new PaymentResponse(true, vnp_PayUrl + "?" + queryUrl, null);
        } catch (Exception e) {
            log.error("Error creating payment URL: ", e);
            return new PaymentResponse(false, null, "Error creating payment: " + e.getMessage());
        }
    }

    public PaymentVerification verifyPaymentReturn(Map<String, String> responseParams) {
        String txnRef = responseParams.get("vnp_TxnRef");
        try {
            String secureHash = responseParams.get("vnp_SecureHash");

            if (secureHash == null) {
                return new PaymentVerification(false, "Missing secure hash");
            }

            Map<String, String> verifyParams = new HashMap<>(responseParams);
            verifyParams.remove("vnp_SecureHash");

            String calculatedHash = calculateSecureHash(verifyParams);
            boolean isValid = calculatedHash.equals(secureHash);

            if (!isValid) {
                return new PaymentVerification(false, "Invalid signature");
            }

            String responseCode = responseParams.get("vnp_ResponseCode");
            boolean isSuccess = "00".equals(responseCode);

            return new PaymentVerification(
                    isSuccess,
                    isSuccess ? "Payment successful" : "Payment failed",
                    responseParams.get("vnp_TxnRef"),
                    responseCode
            );
        } catch (Exception e) {
            log.error("Error verifying payment: ", e);
            return new PaymentVerification(false, "Verification error: " + e.getMessage());
        }
    }

    private Map<String, String> buildPaymentParams(PaymentRequest request) {
        Map<String, String> params = new HashMap<>();

        params.put("vnp_Version", vnp_Version);
        params.put("vnp_Command", vnp_Command);
        params.put("vnp_TmnCode", vnp_TmnCode);
        params.put("vnp_Amount", String.valueOf((long) request.getAmount() * 100));
        params.put("vnp_CurrCode", "VND");

        String txnRef = request.getOrderId() + "_" + System.currentTimeMillis();
        params.put("vnp_TxnRef", txnRef);
        params.put("vnp_OrderInfo", "Thanh toan don hang:" + txnRef);
        params.put("vnp_OrderType", "other");
        params.put("vnp_Locale", "vn");

        if (request.getBankCode() != null && !request.getBankCode().isEmpty()) {
            params.put("vnp_BankCode", request.getBankCode());
        }

        params.put("vnp_ReturnUrl", vnp_ReturnUrl);
        params.put("vnp_IpAddr", getIpAddress(request.getRequest()));

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String createDate = formatter.format(cld.getTime());
        cld.add(Calendar.MINUTE, 15);
        String expireDate = formatter.format(cld.getTime());

        params.put("vnp_CreateDate", createDate);
        params.put("vnp_ExpireDate", expireDate);

        return params;
    }

    private String buildQueryUrl(Map<String, String> params) throws UnsupportedEncodingException {
        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);

        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        for (String key : keys) {
            String value = params.get(key);
            if (value != null && !value.isEmpty()) {
                hashData.append(key).append("=").append(URLEncoder.encode(value, StandardCharsets.US_ASCII.toString())).append("&");
                query.append(URLEncoder.encode(key, StandardCharsets.US_ASCII.toString()))
                        .append("=")
                        .append(URLEncoder.encode(value, StandardCharsets.US_ASCII.toString()))
                        .append("&");
            }
        }

        String secureHash = hmacSHA512(vnp_HashSecret, hashData.substring(0, hashData.length() - 1));
        return query.substring(0, query.length() - 1) + "&vnp_SecureHash=" + secureHash;
    }

    private String calculateSecureHash(Map<String, String> fields) {
        List<String> fieldNames = new ArrayList<>(fields.keySet());
        Collections.sort(fieldNames);
        StringBuilder sb = new StringBuilder();
        for (String name : fieldNames) {
            String value = fields.get(name);
            if (value != null && !value.isEmpty()) {
                sb.append(name).append("=").append(value).append("&");
            }
        }
        if (sb.length() > 0) sb.setLength(sb.length() - 1);
        return hmacSHA512(vnp_HashSecret, sb.toString());
    }

    private String hmacSHA512(String key, String data) {
        try {
            Mac hmac512 = Mac.getInstance("HmacSHA512");
            byte[] hmacKeyBytes = key.getBytes();
            SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
            hmac512.init(secretKey);
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            byte[] result = hmac512.doFinal(dataBytes);
            StringBuilder sb = new StringBuilder();
            for (byte b : result) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            log.error("HMAC error: ", e);
            return "";
        }
    }

    private String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-FORWARDED-FOR");
        return (ip != null && !ip.isEmpty()) ? ip : request.getRemoteAddr();
    }




}
