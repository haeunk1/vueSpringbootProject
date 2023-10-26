package com.gallery.backend.Controller.Api;

import com.gallery.backend.Service.JwtService;
import com.gallery.backend.entity.Cart;
import com.gallery.backend.entity.Item;
import com.gallery.backend.repository.CartRepository;
import com.gallery.backend.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class ApiCartController {
    @Autowired
    JwtService jwtService;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ItemRepository itemRepository;
    //카트 안의 아이템 목록 가져오기
    @GetMapping("/api/cart/items")
    public ResponseEntity getCartItems(@CookieValue(value="token",required = false) String token){
        if(!jwtService.isValue(token)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        int memberId = jwtService.getId(token);
        List<Cart> carts = cartRepository.findByMemberId(memberId);
        List<Integer> itemIds = carts.stream().map(Cart::getItemId).toList();//carts결과에서 itemId를 리스트 형태로 반환

        List<Item> items = itemRepository.findByIdIn(itemIds);//아이템리스트

        return new ResponseEntity<>(items,HttpStatus.OK);
    }
    @PostMapping("/api/cart/items/{itemId}")
    public ResponseEntity pushCartItem(
            @PathVariable("itemId") int itemId,
            @CookieValue(value="token",required = false) String token
    ){
        if(!jwtService.isValue(token)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        int memberId = jwtService.getId(token);
        Cart cart = cartRepository.findByMemberIdAndItemId(memberId,itemId);

        if(cart == null){
            Cart newCart = new Cart();
            newCart.setMemberId(memberId);
            newCart.setItemId(itemId);
            cartRepository.save(newCart);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
