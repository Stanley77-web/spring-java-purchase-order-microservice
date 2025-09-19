package com.dans.javaonboard.ordering.constollers;

import com.dans.javaonboard.ordering.entities.TransactionHistoriesEntity;
import com.dans.javaonboard.ordering.services.OrderingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("ordering/v1/orders")
public class OrderingController {

    @Autowired
    private OrderingService orderingService;

    @GetMapping("/{username}/histories")
    public ResponseEntity<List<TransactionHistoriesEntity>> getOrderHistoriesByUser(@PathVariable("username") String username) {
        return ResponseEntity.ok(orderingService.getTransactionHistories(username));
    }
}
