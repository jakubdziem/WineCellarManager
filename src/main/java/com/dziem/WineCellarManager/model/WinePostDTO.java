package com.dziem.WineCellarManager.model;

import com.dziem.WineCellarManager.validation.EnumValidator;
import com.dziem.WineCellarManager.validation.VintageValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.Year;
import java.util.UUID;

@Data
public class WinePostDTO {

    private UUID customerId;

    @NotBlank(message="Name is mandatory")
    @Size(min = 3, max = 100, message = "Name must be in range from 3 to 100")
    private String name;

    @VintageValidator(message = "Vintage must be a number between 1 and current year")
    @NotNull(message = "Vintage is mandatory")
    private Year vintage;

    @Pattern(regexp = "^(https?://[^\\s$.?#].[^\\s]*)$", message = "Image URL must be a valid URL")
    @NotBlank(message="Image URL is mandatory.")
    private String imageUrl;

    @Pattern(regexp = "^[A-Z].*", message = "Country name must start with a capital letter.")
    @NotBlank(message="Country name is mandatory")
    @Size(min = 3, max = 100, message = "Country name must be in range from 3 to 100")
    private String country;

    @NotBlank(message="Region name is mandatory")
    @Size(min = 3, max = 100, message = "Region name must be in range from 3 to 100")
    private String region;

    @NotBlank(message="Winery name is mandatory")
    @Size(min = 3, max = 100, message = "Winery name must be in range from 3 to 100")
    private String winery;

    @Pattern(regexp = "^\\$\\d+(\\.\\d{1,2})?$", message = "Price must be in the format $8, $8.00, or $8.35.")
    private String price;

    @EnumValidator(enumClazz = WineType.class, message = "Wine type must be one of RED, WHITE, SPARKLING, ROSE,DESSERT or PORT")
    private String wineType;
}
