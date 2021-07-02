package com.egcodes.advertisement.api.controller;

import com.egcodes.advertisement.enums.Category;
import com.egcodes.advertisement.enums.Status;
import com.egcodes.advertisement.persistence.entity.Advertisement;
import com.egcodes.advertisement.persistence.repository.AdvertisementRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.time.OffsetDateTime;

@TestConfiguration
public class AdvertisementRestControllerIntegrationTestSupport {

    @Bean
    public CommonTestSupport commonTestSupport(AdvertisementRepository advertisementRepository) {
        return new CommonTestSupport(advertisementRepository);
    }

    @RequiredArgsConstructor
    @Getter
    public static class CommonTestSupport {

        private final AdvertisementRepository advertisementRepository;

        public void setUpAdvertisement() {
            var advertisement = Advertisement.builder()
                    .title("Qashqai 2012 Tekna Pack")
                    .detail("Second owner, perfect vehicle")
                    .category(Category.VEHICLE)
                    .status(Status.WAITING_FOR_APPROVAL)
                    .createDate(OffsetDateTime.now())
                    .build();

            this.advertisementRepository.save(advertisement);
        }

        public void tearDown() {
            this.advertisementRepository.deleteAll();
        }
    }
}
