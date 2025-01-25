package com.dziem.WineCellarManager.controller;

import com.dziem.WineCellarManager.model.WineDTO;
import com.dziem.WineCellarManager.model.WinePostDTO;
import com.dziem.WineCellarManager.service.WineService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class WineRESTController {
    private final WineService wineService;
    @GetMapping("/viewWine")
    @ResponseBody
    public WineDTO getClickedWineById(@RequestParam("id") Long id) {
        return wineService.getClickedWineById(id);
    }
    @PutMapping(value = "/updateWine", consumes = "application/json")
    public ResponseEntity<Void> updateClickedWineById(@Validated @RequestBody WineDTO wine) {
        if(wineService.updateClickedWineById(wine)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/deleteWine")
    public ResponseEntity<Void> deleteClickedWineById(@RequestParam Long id) {
        if(wineService.deleteClickedWineById(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/addWine")
    public ResponseEntity<Void> createWine(@Validated @RequestBody WinePostDTO winePostDTO) {
        if(wineService.createWine(winePostDTO, winePostDTO.getCustomerId())) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
