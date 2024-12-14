package com.dziem.WineCellarManager.controller;

import com.dziem.WineCellarManager.model.Wine;
import com.dziem.WineCellarManager.model.WineType;
import com.dziem.WineCellarManager.repository.WineRepository;
import com.dziem.WineCellarManager.service.WineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class CollarController {
    private final WineRepository wineRepository;
    private final WineService wineService;
    @GetMapping("/collar")
    public String getCollarPage() {
        return "collar";
    }
    @ModelAttribute("wines")
    public Map<WineType, List<Wine>> groupWinesByType() {
        return wineRepository.findAll().stream().filter(w -> w.getId() >= 741 && w.getId() <= 750).collect(Collectors.groupingBy(Wine::getWineType));
    }
    @GetMapping("/viewWine")
    @ResponseBody
    public Wine getClickedWineById(@RequestParam("id") Long id) {
        return wineService.getClickedWineById(id);
    }
    @PutMapping(value = "/updateWine", consumes = "application/json")
    public ResponseEntity<Void> editClickedWineById(@RequestBody Wine wine) {
        if(wineService.editClickedWineById(wine)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
