package com.egcodes.advertisement.persistence.repository;

import com.egcodes.advertisement.dto.Statistics;
import com.egcodes.advertisement.enums.Category;
import com.egcodes.advertisement.persistence.entity.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {

    Long countByTitleAndDetailAndCategory(String title, String detail, Category category);

    @Query(value = "SELECT new com.egcodes.advertisement.dto.Statistics(a.status, count(a))" +
            " FROM Advertisement a GROUP BY a.status")
    List<Statistics> getAdvertisementCountByStatus();
}
