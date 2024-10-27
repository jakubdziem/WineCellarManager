package com.dziem.WineCellarManager.controller;

import com.dziem.WineCellarManager.model.Wine;
import com.dziem.WineCellarManager.model.WineType;
import com.dziem.WineCellarManager.repository.WineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class CollarController {
    private final WineRepository wineRepository;
    @GetMapping("/collar")
    public String getCollarPage() {
        return "collar";
    }
//    @ModelAttribute("wines")
//    public List<Wine> getExampleWines() {
//        System.out.println(wineRepository.findAll().stream().limit(10).toList());
//        return wineRepository.findAll().stream().limit(10).toList();
//    }
    @ModelAttribute("wines")
    public Map<WineType, List<Wine>> groupWinesByType() {
        return wineRepository.findAll().stream().skip(717).limit(10).collect(Collectors.groupingBy(Wine::getWineType));
    }
}
