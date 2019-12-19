package com.example.demo.controller;


import com.example.demo.entity.Drugs;
import com.example.demo.service.DrugsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/drugs")
public class DrugsController {
    Set<String> titleSet = null;
    @Autowired
    DrugsService drugsService;

    @RequestMapping("/getDurgs")
    public Drugs getDurgs() {
        return drugsService.getDrugs(1L);
    }

    public static void main(String[] args) {
       /* String bb = String.format("a%s%d", "bb",123);
        System.out.println(bb);*/
        System.out.println("cc");
    }
}

