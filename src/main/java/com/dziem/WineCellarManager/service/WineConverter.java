package com.dziem.WineCellarManager.service;

import com.dziem.WineCellarManager.model.Wine;
import com.dziem.WineCellarManager.model.WineDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.Year;
import java.util.Locale;
import java.util.Random;

@Service
public class WineConverter {
    private static final int BASE_PRICE = 100;
    private static final String BASE_LOCATION = "Spain;Empordà";
    private static final String BASE_WINERY = "Maselva";
    public Wine convertWineDTOToWine(WineDTO wineDTO) {
        Wine wine = new Wine();

        wine.setWineType(wineDTO.getWineType());
        wine.setImageUrl(wineDTO.getImage());

        String winery = wineDTO.getWinery().isEmpty() ? BASE_WINERY : wineDTO.getWinery();
        wine.setWinery(winery);

        String location = wineDTO.getLocation().isEmpty() ? BASE_LOCATION : wineDTO.getLocation();
        String[] countryAndRegion = location.replace("\n", "").replace("·", ";").split(";");
        wine.setCountry(countryAndRegion[0]);
        wine.setRegion(countryAndRegion[1]);

        String[] wineNameAndWinery = wineDTO.getWine().split(" ");
        String name = "";
        for(int i = 0; i < wineNameAndWinery.length-1; i++) {
            name = name.concat(wineNameAndWinery[i]).concat(i==wineNameAndWinery.length-2 ? "" : " ");
        }
        wine.setName(name);

        String yearOrNV = wineNameAndWinery[wineNameAndWinery.length - 1];
        if(yearOrNV.equals("N.V.")) { //no vintage
            wine.setVintage(Year.now());
        } else {
            wine.setVintage(Year.parse(yearOrNV));
        }

        BigDecimal price = calculatePrice(wine.getVintage().getValue());
        wine.setPrice(formatPriceAsDollar(price));
        return wine;
    }
    private BigDecimal calculatePrice(int vintageYear) {
        int currentYear = Year.now().getValue();
        int age = currentYear - vintageYear;
        double basePrice = BASE_PRICE + (age * 10);
        Random rand = new Random();
        double randomFactor = 0.8 + (0.4 * rand.nextDouble());
        return BigDecimal.valueOf(basePrice * randomFactor).setScale(2, RoundingMode.HALF_UP);
    }

    private String formatPriceAsDollar(BigDecimal price) {
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
        return format.format(price);
    }
}
