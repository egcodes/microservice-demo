package com.egcodes.advertisement.service.advertisement.impl;

import com.egcodes.advertisement.enums.Category;
import com.egcodes.advertisement.enums.Status;
import com.egcodes.advertisement.enums.ValidationRule;
import com.egcodes.advertisement.exception.AdvertisementException;
import com.egcodes.advertisement.persistence.entity.Advertisement;
import com.egcodes.advertisement.persistence.repository.AdvertisementRepository;
import com.egcodes.advertisement.service.ResourceService;
import com.egcodes.advertisement.service.status.StatusHistoryCommandService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdvertisementCommandServiceImplTest {

    @Mock
    private AdvertisementRepository advertisementRepository;

    @Mock
    private ResourceService resourceService;

    @Mock
    private StatusHistoryCommandService statusHistoryCommandService;

    @InjectMocks
    private AdvertisementCommandServiceImpl advertisementCommandService;

    private Advertisement advertisement;

    @BeforeEach
    void setUp() {
        advertisement = Advertisement.builder()
            .title("title1234")
            .detail("detail01234567890123456789")
            .category(Category.PROPERTY)
            .createDate(OffsetDateTime.now())
            .status(Status.WAITING_FOR_APPROVAL)
            .build();
    }

    @Test
    void givenContainsBadWordAdvertisement_whenAdd_thenThrowTitleNotAllowedException() {
        //given
        advertisement.setTitle("paexueutcu");

        //when
        when(resourceService.getBadWordList()).thenReturn(Arrays.asList("paexueutcu"));

        //then
        var thrown = catchThrowable(() -> advertisementCommandService.add(advertisement));
        assertThat(thrown).isInstanceOf(AdvertisementException.class);
        assertThat(thrown.getMessage()).isEqualTo(ValidationRule.TITLE_NOT_ALLOWED.getError());
    }

    @Test
    void givenDuplicateStatusAdvertisement_whenAdd_shouldAddStatusByDuplicate() {
        //when
        when(resourceService.getBadWordList()).thenReturn(Collections.emptyList());
        when(advertisementRepository.countByTitleAndDetailAndCategory(anyString(), anyString(), any())).thenReturn(1L);
        when(advertisementRepository.save(any())).thenReturn(Advertisement.builder().status(Status.DUPLICATE).build());

        //then
        var savedAdvertisement = advertisementCommandService.add(advertisement);

        assertThat(savedAdvertisement.getStatus()).isEqualTo(Status.DUPLICATE);
        verify(advertisementRepository, atLeastOnce()).save(advertisement);
    }

    @Test
    void givenDuplicateAdvertisement_whenChangeStatus_thenThrowUpdateDuplicateAdNotAllowed() {
        //given
        advertisement.setStatus(Status.DUPLICATE);
        var advertisementMaybe = Optional.of(advertisement);

        //when
        when(advertisementRepository.findById(anyLong())).thenReturn(advertisementMaybe);

        //then
        var thrown = catchThrowable(() -> advertisementCommandService.changeStatus(1L, Status.ACTIVE));
        assertThat(thrown).isInstanceOf(AdvertisementException.class);
        assertThat(thrown.getMessage()).isEqualTo(ValidationRule.UPDATE_DUPLICATE_ADS_NOT_ALLOWED.getError());
    }


    @Test
    void givenAlreadyActiveAdvertisement_whenApprove_thenThrowAdvertisementStatusNotSuitable() {
        //given
        advertisement.setStatus(Status.ACTIVE);
        var advertisementMaybe = Optional.of(advertisement);

        //when
        when(advertisementRepository.findById(anyLong())).thenReturn(advertisementMaybe);

        //then
        var thrown = catchThrowable(() -> advertisementCommandService.changeStatus(1L, Status.ACTIVE));
        assertThat(thrown).isInstanceOf(AdvertisementException.class);
        assertThat(thrown.getMessage()).isEqualTo(ValidationRule.ADVERTISEMENT_STATUS_NOT_SUITABLE.getError());
    }

    @Test
    void givenWaitingForApprovalAdvertisement_whenApprove_thenChangeStatusToActive() {
        //given
        advertisement.setStatus(Status.WAITING_FOR_APPROVAL);
        var advertisementMaybe = Optional.of(advertisement);

        //when
        when(advertisementRepository.findById(anyLong())).thenReturn(advertisementMaybe);
        when(advertisementRepository.save(any())).thenReturn(advertisement);
        when(statusHistoryCommandService.add(any())).thenReturn(null);

        //then
        var savedAdvertisement = advertisementCommandService.changeStatus(1L, Status.ACTIVE);
        assertThat(savedAdvertisement.getStatus()).isEqualTo(Status.ACTIVE);
    }

}