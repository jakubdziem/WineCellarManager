package com.dziem.WineCellarManager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CollarController {
    @GetMapping("/collar")
    public String getCollarPage() {
        return "collar";
    }
}
