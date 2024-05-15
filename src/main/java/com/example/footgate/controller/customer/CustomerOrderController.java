package com.example.footgate.controller.customer;

import com.example.footgate.entities.Order;
import com.example.footgate.entities.User;
import com.example.footgate.request.OrderRequest;
import com.example.footgate.response.MessageResponse;
import com.example.footgate.response.PaymentResponse;
import com.example.footgate.service.OrderService;
import com.example.footgate.service.PaymentService;
import com.example.footgate.service.PaymentServiceImp;
import com.example.footgate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")

public class CustomerOrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private UserService userService;
    @PostMapping("/order")
    public ResponseEntity<PaymentResponse> createOrder(@RequestBody OrderRequest req,
                                                       @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        Order order = orderService.createOrder(req,user);
        PaymentResponse paymentResponse = paymentService.createPaymentLink(order);
        return new ResponseEntity<>(paymentResponse,HttpStatus.CREATED);
    }
    @GetMapping("/order/user")
    public ResponseEntity<List<Order>> getOrderHistory(@RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        List<Order> orders = orderService.getUsersOrder(user.getId());
        return new ResponseEntity<>(orders,HttpStatus.OK);
    }
    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<MessageResponse> cancelOrder(@PathVariable Long orderId,
                                                       @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        orderService.cancelOrder(orderId);
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Order Cancelled Successfully");
        return new ResponseEntity<>(messageResponse,HttpStatus.OK);
    }
}
