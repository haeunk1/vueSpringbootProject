package com.gallery.backend.Controller.Api;

import com.gallery.backend.entity.Item;
import com.gallery.backend.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ApiItemController {
    @Autowired
    ItemRepository itemRepository;

    @GetMapping("/api/items")
    public List<Item> getItems(){
        List<Item> items = itemRepository.findAll();
        return items;
    }
}
