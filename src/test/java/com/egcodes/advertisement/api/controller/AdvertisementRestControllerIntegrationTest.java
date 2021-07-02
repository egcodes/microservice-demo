package com.egcodes.advertisement.api.controller;

import com.egcodes.advertisement.dto.AdvertisementDTO;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@Import(AdvertisementRestControllerIntegrationTestSupport.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdvertisementRestControllerIntegrationTest {

    private static final String BASE_URI = "http://localhost";
    private static final String ADVERTISEMENT_LIST_ENDPOINT_URI = "/dashboard/classifieds/list";

    @LocalServerPort
    private int port;

    @Autowired
    private AdvertisementRestControllerIntegrationTestSupport.CommonTestSupport commonTestSupport;

    @Test
    void getAllAdvertisements_noRecordsPersistedBefore_shouldReturnEmptyListAndOk() {
        // @formatter:off
        List<AdvertisementDTO> advertisementDTOList =
            given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .port(port)
                .log().uri()
                .log().method().
            when().
                get(ADVERTISEMENT_LIST_ENDPOINT_URI).
            then()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response()
                .jsonPath()
                .getList("$", AdvertisementDTO.class);
        // @formatter:on

        // assertions
        assertThat(advertisementDTOList).isNotNull();
        assertThat(advertisementDTOList).isEmpty();
    }

    @Test
    void getAllAds_oneAdPersisted_shouldReturnOneItemListAndOk() {
        commonTestSupport.setUpAdvertisement();

        // @formatter:off
        List<AdvertisementDTO> advertisementDTOList =
            given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .port(port)
                .log().uri()
                .log().method().
            when().
                get(ADVERTISEMENT_LIST_ENDPOINT_URI).
            then()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response()
                .jsonPath()
                .getList("$", AdvertisementDTO.class);
        // @formatter:on

        // assertions
        assertThat(advertisementDTOList).isNotNull();
        assertThat(advertisementDTOList).isNotEmpty();
        assertThat(advertisementDTOList).hasSize(1);

        commonTestSupport.tearDown();
    }
}
