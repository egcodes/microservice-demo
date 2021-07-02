package com.egcodes.advertisement.persistence.repository;

import com.egcodes.advertisement.persistence.entity.StatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatusHistoryRepository extends JpaRepository<StatusHistory, Long> {

    List<StatusHistory> findByAdvertisementId(Long advertisementId);

}
