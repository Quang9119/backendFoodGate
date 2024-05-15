package com.example.footgate.controller.admin;

import com.example.footgate.entities.Order;
import com.example.footgate.entities.User;
import com.example.footgate.request.OrderRequest;
import com.example.footgate.service.OrderService;
import com.example.footgate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminOrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @GetMapping("/order/restaurant/{restaurantId}")
    public ResponseEntity<List<Order>> getOrderHistory(@PathVariable Long restaurantId,
                                                        @RequestParam(required = false) String orderStatus,
                                                        @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        List<Order> orders = orderService.getRestaurantOrders(restaurantId,orderStatus);
        return new ResponseEntity<>(orders,HttpStatus.OK);
    }
    @PutMapping("/order/{orderId}/{orderStatus}")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long orderId,
                                                       @PathVariable(required = false) String orderStatus,
                                                       @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        Order orders = orderService.updateOrder(orderId,orderStatus);
        return new ResponseEntity<>(orders,HttpStatus.OK);
    }
}
