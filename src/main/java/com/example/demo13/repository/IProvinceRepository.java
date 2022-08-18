package com.example.demo13.repository;

import com.example.demo13.model.Province;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IProvinceRepository extends JpaRepository<Province, Long> {
    // List<Province> findAllByNameContaining(String name);

    @Query(value = "select * from province where name like :name", nativeQuery = true)
    Page<Province> findByName(@Param("name") String name, Pageable pageable);

    @Query(value = "select * from province where country_id = :id", nativeQuery = true)
    Page<Province> findByCountry(@Param("id") Optional<Long> id, Pageable pageable);

    @Query(value = "select*from province where province.gdp in (select*from top4gdp)", nativeQuery = true)
    Page<Province> findTop4Gdp(Pageable pageable);

//    @Query(value = "select*from province as p where p.gdp between :minGdp and :maxGdp and " +
//            "p.popular between :minPopular and :maxPopular and p.country_id = :countryId", nativeQuery = true)
//    Page<Province> demo(@Param("minGDP") Double minGdp, @Param("maxGdp") Double maxGdp,
//                        @Param("minPopular") Long minPopular, @Param("maxPopular") Long maxPopular,
//                        @Param("countryId") Long countryId,
//                        Pageable pageable);

    Page<Province> findTop4ByOrderByAreaAsc(Pageable pageable);
    // Page<Province> findProvincesByCountry(Country country,Pageable pageable);


}
