package com.gallery.backend.Controller.Api;

import com.gallery.backend.Service.JwtService;
import com.gallery.backend.dto.OrderDto;
import com.gallery.backend.entity.Cart;
import com.gallery.backend.entity.Item;
import com.gallery.backend.entity.Order;
import com.gallery.backend.repository.CartRepository;
import com.gallery.backend.repository.ItemRepository;
import com.gallery.backend.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class ApiOrderController {
    @Autowired
    JwtService jwtService;

    @Autowired
    OrderRepository orderRepository;
    
    @PostMapping("/api/orders")
    public ResponseEntity pushOrder(
            @RequestBody OrderDto dto,
            //@PathVariable("itemId") int itemId,
            @CookieValue(value="token",required = false) String token
    ){
        if(!jwtService.isValue(token)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        int memberId = jwtService.getId(token);
        
        Order newOrder = new Order();
        newOrder.setMemberId(memberId);
        newOrder.setName(dto.getName());//유효성 검사 추가해야됨
        newOrder.setAddress(dto.getAddress());
        newOrder.setPayment(dto.getPayment());
        newOrder.setCardNumber(dto.getCardNumber());
        newOrder.setItems(dto.getItems());

        orderRepository.save(newOrder);
                


        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/api/orders")
    public ResponseEntity getOrder(
            @CookieValue(value="token",required = false) String token
    ){
        if(!jwtService.isValue(token)){ // AOP 고려
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        List<Order> orders = orderRepository.findAll();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }


}
