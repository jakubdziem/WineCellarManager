package com.dziem.WineCellarManager.controller;

import com.dziem.WineCellarManager.model.Customer;
import com.dziem.WineCellarManager.model.CustomerProfile;
import com.dziem.WineCellarManager.model.Wine;
import com.dziem.WineCellarManager.model.WineType;
import com.dziem.WineCellarManager.repository.CustomerRepository;
import com.dziem.WineCellarManager.repository.WineRepository;
import com.dziem.WineCellarManager.service.WineService;
import com.dziem.WineCellarManager.utilities.PriceSumming;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class CollarController {
    private final WineRepository wineRepository;
    private final CustomerRepository customerRepository;
    private final WineService wineService;
    @GetMapping("/collar")
    public String getCollarPage() {
        return "collar";
    }
    @ModelAttribute("customer")
    public CustomerProfile getCustomer() {
        Customer customer = customerRepository.findById(1L).get();
        List<Wine> wines = customer.getWines();
        Map<String, List<Wine>> winesByRegion = wines.stream().collect(Collectors.groupingBy(Wine::getRegion));
        String favoriteRegion = "";
        int max = 0;
        String favoriteCountry = "";
        Map<String, List<Wine>> winesByCountry = wines.stream().collect(Collectors.groupingBy(Wine::getCountry));
        String favoriteWinery= "";
        Map<String, List<Wine>> winesByWinery = wines.stream().collect(Collectors.groupingBy(Wine::getWinery));
        WineType favoriteWineType = WineType.SPARKLING;
        Map<WineType, List<Wine>> winesByWineType = wines.stream().collect(Collectors.groupingBy(Wine::getWineType));
        for(String region : winesByRegion.keySet()) {
            int size = winesByRegion.get(region).size();
            if(size > max) {
                max = size;
                favoriteRegion = region;
            }
        }
        max = 0;
        for(String country : winesByCountry.keySet()) {
            int size = winesByCountry.get(country).size();
            if(size > max) {
                max = size;
                favoriteCountry = country;
            }
        }
        max = 0;
        for(String winery: winesByWinery.keySet()) {
            int size = winesByWinery.get(winery).size();
            if(size > max) {
                max = size;
                favoriteWinery = winery;
            }
        }
        max = 0;
        for(WineType wineType : winesByWineType.keySet()) {
            int size = winesByWineType.get(wineType).size();
            if(size > max) {
                max = size;
                favoriteWineType = wineType;
            }
        }
        return CustomerProfile.builder()
                .nickname(customer.getNickname())
                .numberOfWines(wines.size())
                .numberOfRatings(customer.getRatings().size())
                .wineWithOldestVintage(wines.stream().max((a,b) -> {
                    if(a.getVintage().equals(b.getVintage())) {
                        return 0;
                    } else if(a.getVintage().isBefore(b.getVintage())) {
                        return 1;
                    } else {
                        return -1;
                    }
                }).get())
                .favoriteRegion(favoriteRegion)
                .favoriteCountry(favoriteCountry)
                .favoriteWinery(favoriteWinery)
                .favoriteWineType(favoriteWineType)
                .priceOfCollar(wines.stream().map(Wine::getPrice).reduce(String.valueOf(0), PriceSumming::sumPrice))
                .build();
    }
    @ModelAttribute("wines")
    public Map<WineType, List<Wine>> groupWinesByType() {
//        return wineRepository.findAll().stream().filter(w -> w.getId() >= 741 && w.getId() <= 750).sorted(Comparator.comparing(Wine::getId)).collect(Collectors.groupingBy(Wine::getWineType));
        return customerRepository.findById(1L).get().getWines().stream().sorted(Comparator.comparing(Wine::getId)).collect(Collectors.groupingBy(Wine::getWineType));
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
    @DeleteMapping("/deleteWine")
    public ResponseEntity<Void> deleteClickedWineById(@RequestParam Long id) {
        if(wineService.deleteClickedWineById(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
