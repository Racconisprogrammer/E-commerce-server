package com.example.ecommerce.controller;


import com.example.ecommerce.controller.http.response.ApiResponse;
import com.example.ecommerce.controller.http.response.PaymentLinkResponse;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.exception.OrderException;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.service.OrderService;
import com.example.ecommerce.service.UserService;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PaymentController {

    @Value("${RAZORPAY_PUBLISHABLE_ID}")
    private String apiKey;

    @Value("${RAZORPAY_SECRET_KEY}")
    private String secretKey;

    private final OrderService orderService;
    private final UserService userService;
    private final OrderRepository orderRepository;

    @PostMapping("/payments/{orderId}")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(@PathVariable Long orderId,
        @RequestHeader("Authorization") String jwt) throws OrderException, RazorpayException {
        Order order = orderService.findOrderById(orderId);

        try {
            RazorpayClient razorpayClient = new RazorpayClient(apiKey, secretKey);
            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount", order.getTotalPrice()*100);
            paymentLinkRequest.put("currency", "INR");
            JSONObject customer = new JSONObject();
            customer.put("name", order.getUser().getFirstName());
            customer.put("email", order.getUser().getEmail());
            paymentLinkRequest.put("customer", customer);
            JSONObject notifyServer = new JSONObject();
            notifyServer.put("sms", true);
            notifyServer.put("email", true);
            paymentLinkRequest.put("notify", notifyServer);
            paymentLinkRequest.put("callback_url", "http://localhost:3000/payment/" + orderId);
            paymentLinkRequest.put("callback_method", "get");
            PaymentLink paymentLink = razorpayClient.paymentLink.create(paymentLinkRequest);

            String paymentLinkId = paymentLink.get("id");
            String paymentLinkUrl = paymentLink.get("short_url");

            PaymentLinkResponse response = new PaymentLinkResponse();
            response.setPayment_link_id(paymentLinkId);
            response.setPayment_link_url(paymentLinkUrl);

            return new ResponseEntity<PaymentLinkResponse>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            throw new  RazorpayException(e.getMessage());
        }
    }

    @GetMapping("/payments")
    public ResponseEntity<ApiResponse> redirect(
            @RequestParam(name = "payment_id") String paymentId,
            @RequestParam(name = "order_id") Long orderId
    ) throws OrderException, RazorpayException {
        Order order = orderService.findOrderById(orderId);
        RazorpayClient razorpayClient = new RazorpayClient(apiKey, secretKey);


        try {
            Payment payment = razorpayClient.payments.fetch(paymentId);

            if (payment.get("status").equals("captured")) {
                order.getPaymentDetails().setPaymentId(paymentId);
                order.getPaymentDetails().setStatus("COMPLETED");
                order.setOrderStatus("PLACED");
                orderRepository.save(order);
            }

            ApiResponse response = new ApiResponse();
            response.setMessage("your ordered get placed");
            response.setStatus(true);

            return new ResponseEntity<ApiResponse>(response, HttpStatus.ACCEPTED);

        } catch (Exception e) {
            throw new RazorpayException(e.getMessage());
        }
    }

}
