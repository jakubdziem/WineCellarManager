package com.dziem.WineCellarManager.service;

import com.dziem.WineCellarManager.model.Customer;
import com.dziem.WineCellarManager.model.CustomerProfile;
import com.dziem.WineCellarManager.model.Wine;
import com.dziem.WineCellarManager.model.WineType;
import com.dziem.WineCellarManager.repository.CustomerRepository;
import com.dziem.WineCellarManager.utilities.PriceSumming;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    @Override
    public CustomerProfile getCustomer(String nickname) {
        Customer customer = customerRepository.findByNickname(nickname).get();
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
                .id(customer.getId())
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
                }).orElse(new Wine()))
                .favoriteRegion(favoriteRegion)
                .favoriteCountry(favoriteCountry)
                .favoriteWinery(favoriteWinery)
                .favoriteWineType(favoriteWineType)
                .priceOfCollar(wines.stream().map(Wine::getPrice).reduce(String.valueOf(0), PriceSumming::sumPrice))
                .build();
    }

    @Override
    public boolean customerExist(String nickname) {
        Optional<Customer> foundCustomer = customerRepository.findByNickname(nickname);
        return foundCustomer.isPresent();
    }


}
