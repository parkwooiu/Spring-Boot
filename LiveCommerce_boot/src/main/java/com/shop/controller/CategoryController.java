package com.shop.controller;

import com.shop.constant.Category;
import com.shop.entity.Item;
import com.shop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CategoryController {

    private final ItemService itemService;

    @GetMapping("/category")
    public String getItemsByCategory(@PathVariable("category") Category category, Model model) {
        List<Item> items = itemService.getItemsByCategory(category);
        model.addAttribute("category", category);
        model.addAttribute("items", items);
        return "category";
    }
}
