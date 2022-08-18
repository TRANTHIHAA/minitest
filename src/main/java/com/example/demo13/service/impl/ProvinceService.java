package com.example.demo13.service.impl;

import com.example.demo13.model.Province;
import com.example.demo13.repository.IProvinceRepository;
import com.example.demo13.service.ICRUDProvince;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProvinceService implements ICRUDProvince {
    @Autowired
    private IProvinceRepository iProvinceRepository;

    @Override
    public Province save(Province province) {
        return iProvinceRepository.save(province);
    }

    @Override
    public void delete(Long id) {
        iProvinceRepository.deleteById(id);
    }

    @Override
    public Optional<Province> findById(Long id) {
        return iProvinceRepository.findById(id);
    }

    @Override
    public List<Province> findAll() {
        return null;
    }

    @Override
    public Page<Province> findBySearch(String name, Pageable pageable) {
        return iProvinceRepository.findByName("%" + name + "%", pageable);
    }

    @Override
    public Page<Province> findAll(Pageable pageable) {
        return iProvinceRepository.findAll(pageable);
    }

    @Override
    public Page<Province> findAllByCountry(Optional<Long> id, Pageable pageable) {
        return iProvinceRepository.findByCountry(id, pageable);
    }

    @Override
    public Page<Province> findTop4Gdp(Pageable pageable) {
        return iProvinceRepository.findTop4Gdp(pageable);
    }

    @Override
    public Page<Province> findTop4Area(Pageable pageable) {
        return iProvinceRepository.findTop4ByOrderByAreaAsc(pageable);
    }

    @Override
    public Page<Province> demo(Double minG, Double maxG, Long minP, Long maxP, Long countryId, Pageable pageable) {
//        return iProvinceRepository.demo(minG, maxG, minP, maxP, countryId, pageable);
        return null;
    }
}
