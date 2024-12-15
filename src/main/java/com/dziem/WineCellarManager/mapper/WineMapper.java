package com.dziem.WineCellarManager.mapper;

import com.dziem.WineCellarManager.model.Wine;
import com.dziem.WineCellarManager.model.WineDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WineMapper {
    WineDTO wineToWineDTO(Wine wine);
    Wine wineDTOToWine(WineDTO wineDTO);
}