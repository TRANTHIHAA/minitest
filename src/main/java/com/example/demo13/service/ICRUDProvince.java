package com.example.demo13.service;

import com.example.demo13.common.ICRUDService;
import com.example.demo13.model.Province;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ICRUDProvince extends ICRUDService<Province> {
    Page<Province> findBySearch(String name,Pageable pageable);
    Page<Province> findAll(Pageable pageable);
    //Page<Province> findAllByCountry(Optional<Long> id, Pageable pageable);
    Page<Province> findAllByCountry(Optional<Long> id, Pageable pageable);

    Page<Province> findTop4Gdp(Pageable pageable);
    Page<Province> findTop4Area(Pageable pageable);

    Page<Province> demo(Double minG, Double maxG, Long minP, Long maxP, Long countryId,Pageable pageable);

}
