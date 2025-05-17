package com.myapp.service;

import org.springframework.stereotype.Service;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import javax.xml.bind.DatatypeConverter;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class VNPayService {
    private final String vnp_TmnCode = "PL3MJRUO";
    private final String vnp_HashSecret ="PIYRCT1T0VQB5YP28Y0NSEZ32962F7FP";
    private final String vnp_Url = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    private final String vnp_ReturnUrl = "http://localhost:8080/user/checkout/payment/vnpay-return";
    public String createPaymentUrl(Integer orderId, BigDecimal amount, HttpServletRequest request) {
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", "pay");
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", amount.multiply(BigDecimal.valueOf(100)).toBigInteger().toString());
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", String.valueOf(orderId));
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang #" + orderId);
        vnp_Params.put("vnp_OrderType", "other");
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", request.getRemoteAddr());
        vnp_Params.put("vnp_CreateDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        for (String name : fieldNames) {
            String value = vnp_Params.get(name);

            // Encode tên và giá trị
            String encodedName = URLEncoder.encode(name, StandardCharsets.UTF_8);
            String encodedValue = URLEncoder.encode(value, StandardCharsets.UTF_8);

            // Tạo chuỗi hashData giống format query
            if (hashData.length() > 0) hashData.append('&');
            hashData.append(encodedName).append('=').append(encodedValue);

            // Tạo query string
            query.append(encodedName).append('=').append(encodedValue).append('&');
        }

        String secureHash = hmacSHA512(vnp_HashSecret, hashData.toString());
        query.append("vnp_SecureHash=").append(secureHash);
        return vnp_Url + "?" + query.toString();
    }

    public boolean validateSignature(Map<String, String> params, String receivedHash) {
        Map<String, String> sorted = new TreeMap<>(params);
        sorted.remove("vnp_SecureHash");
        sorted.remove("vnp_SecureHashType"); // nên loại bỏ cái này nếu tồn tại

        StringBuilder hashData = new StringBuilder();
        for (Map.Entry<String, String> entry : sorted.entrySet()) {
            if (hashData.length() > 0) hashData.append('&');

            // Encode key và value
            String encodedKey = URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8);
            String encodedValue = URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8);

            hashData.append(encodedKey).append('=').append(encodedValue);
        }

        String calculatedHash = hmacSHA512(vnp_HashSecret, hashData.toString());
        System.out.println("hashData = " + hashData.toString());
        System.out.println("receivedHash = " + receivedHash);
        System.out.println("calculatedHash = " + calculatedHash);
        return calculatedHash.equalsIgnoreCase(receivedHash);


    }

    private String hmacSHA512(String key, String data) {
        try {
            Mac hmac = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
            hmac.init(secretKey);
            byte[] bytes = hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return DatatypeConverter.printHexBinary(bytes).toLowerCase();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi HMAC SHA512", e);
        }
    }
} 