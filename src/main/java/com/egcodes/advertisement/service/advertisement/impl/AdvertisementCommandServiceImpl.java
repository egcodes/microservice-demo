package com.egcodes.advertisement.service.advertisement.impl;

import com.egcodes.advertisement.exception.AdvertisementException;
import com.egcodes.advertisement.constants.AdExpiryTimes;
import com.egcodes.advertisement.enums.Status;
import com.egcodes.advertisement.enums.ValidationRule;
import com.egcodes.advertisement.persistence.entity.Advertisement;
import com.egcodes.advertisement.persistence.entity.StatusHistory;
import com.egcodes.advertisement.persistence.repository.AdvertisementRepository;
import com.egcodes.advertisement.service.ResourceService;
import com.egcodes.advertisement.service.advertisement.AdvertisementCommandService;
import com.egcodes.advertisement.service.status.StatusHistoryCommandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.Arrays;

@Service
@Transactional
@Slf4j
public class AdvertisementCommandServiceImpl implements AdvertisementCommandService {

    private final AdvertisementRepository advertisementRepository;
    private final StatusHistoryCommandService statusHistoryCommandService;
    private final ResourceService resourceService;

    @Autowired
    public AdvertisementCommandServiceImpl(AdvertisementRepository advertisementRepository,
                                           StatusHistoryCommandService statusHistoryCommandService,
                                           ResourceService resourceService) {
        this.advertisementRepository = advertisementRepository;
        this.statusHistoryCommandService = statusHistoryCommandService;
        this.resourceService = resourceService;
    }

    @Override
    public Advertisement add(Advertisement advertisement) {
        advertisement.setCreateDate(OffsetDateTime.now());
        advertisement.setStatus(Status.WAITING_FOR_APPROVAL);

        if (areThereAnyBadWords(advertisement.getTitle())) {
            throw new AdvertisementException(ValidationRule.TITLE_NOT_ALLOWED);
        }

        if (areThereAnySimilarAdvertisementByTitleDetailCategory(advertisement)) {
            advertisement.setStatus(Status.DUPLICATE);
        }

        switch (advertisement.getCategory()) {
            case PROPERTY:
                advertisement.setEndDate(advertisement.getCreateDate().plusWeeks(AdExpiryTimes.PROPERTY));
                break;
            case VEHICLE:
                advertisement.setEndDate(advertisement.getCreateDate().plusWeeks(AdExpiryTimes.VEHICLE));
                break;
            case OTHER:
                advertisement.setEndDate(advertisement.getCreateDate().plusWeeks(AdExpiryTimes.OTHER));
                break;
            case SHOPPING:
                advertisement.setStatus(Status.ACTIVE);
                advertisement.setEndDate(advertisement.getCreateDate().plusWeeks(AdExpiryTimes.SHOPPING));
                break;
            default:
                throw new AdvertisementException(ValidationRule.CATEGORY_NOT_ALLOWED);
        }

        advertisement.setStatusHistory(Arrays.asList(addStatusHistory(advertisement, advertisement.getStatus())));

        return advertisementRepository.save(advertisement);
    }

    @Override
    public Advertisement changeStatus(Long id, Status status) {
        var advertisementMaybe = advertisementRepository.findById(id);

        if (advertisementMaybe.isPresent()) {
            var advertisement = advertisementMaybe.get();

            if (advertisement.getStatus().equals(Status.DUPLICATE)) {
                throw new AdvertisementException(ValidationRule.UPDATE_DUPLICATE_ADS_NOT_ALLOWED);

            } else if (isSuitableToActivate(advertisement, status) || isSuitableToDeactivate(advertisement, status)) {
                advertisement.setStatus(status);

                var savedAdvertisement = advertisementRepository.save(advertisement);
                addStatusHistory(savedAdvertisement, status);
                return savedAdvertisement;

            } else {
                throw new AdvertisementException(ValidationRule.ADVERTISEMENT_STATUS_NOT_SUITABLE);
            }

        } else {
            throw new AdvertisementException(ValidationRule.ADVERTISEMENT_NOT_FOUND);
        }
    }

    private boolean areThereAnySimilarAdvertisementByTitleDetailCategory(Advertisement advertisement) {
        return advertisementRepository.countByTitleAndDetailAndCategory(advertisement.getTitle(), advertisement.getDetail(), advertisement.getCategory()) > 0;
    }

    private boolean isSuitableToActivate(Advertisement advertisement, Status status) {
        return status.equals(Status.ACTIVE) && advertisement.getStatus().equals(Status.WAITING_FOR_APPROVAL);
    }

    private boolean isSuitableToDeactivate(Advertisement advertisement, Status status) {
        return status.equals(Status.DEACTIVATE)
                && (advertisement.getStatus().equals(Status.ACTIVE) || advertisement.getStatus().equals(Status.WAITING_FOR_APPROVAL));
    }

    private StatusHistory addStatusHistory(Advertisement advertisement, Status status) {
        var statusHistory = StatusHistory.builder()
                .status(status)
                .advertisement(advertisement)
                .updatedDate(OffsetDateTime.now())
                .build();
        return statusHistoryCommandService.add(statusHistory);
    }

    private boolean areThereAnyBadWords(String word) {
        return resourceService.getBadWordList().stream().anyMatch(s -> word.contains(s));
    }

}
