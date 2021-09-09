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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.Arrays;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdvertisementCommandServiceImpl implements AdvertisementCommandService {

    private final AdvertisementRepository advertisementRepository;
    private final StatusHistoryCommandService statusHistoryCommandService;
    private final ResourceService resourceService;

    @Override
    public Advertisement add(Advertisement ad) {
        ad.setCreateDate(OffsetDateTime.now());
        ad.setStatus(Status.WAITING_FOR_APPROVAL);

        if (areThereAnyBadWords(ad.getTitle())) {
            throw new AdvertisementException(ValidationRule.TITLE_NOT_ALLOWED);
        }

        if (areThereAnySimilarAdvertisementByTitleDetailCategory(ad)) {
            ad.setStatus(Status.DUPLICATE);
        }

        switch (ad.getCategory()) {
            case PROPERTY:
                ad.setEndDate(ad.getCreateDate().plusWeeks(AdExpiryTimes.PROPERTY));
                break;
            case VEHICLE:
                ad.setEndDate(ad.getCreateDate().plusWeeks(AdExpiryTimes.VEHICLE));
                break;
            case OTHER:
                ad.setEndDate(ad.getCreateDate().plusWeeks(AdExpiryTimes.OTHER));
                break;
            case SHOPPING:
                ad.setStatus(Status.ACTIVE);
                ad.setEndDate(ad.getCreateDate().plusWeeks(AdExpiryTimes.SHOPPING));
                break;
            default:
                throw new AdvertisementException(ValidationRule.CATEGORY_NOT_ALLOWED);
        }

        ad.setStatusHistory(Arrays.asList(addStatusHistory(ad, ad.getStatus())));

        return advertisementRepository.save(ad);
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

    private boolean areThereAnySimilarAdvertisementByTitleDetailCategory(Advertisement ad) {
        return advertisementRepository.countByTitleAndDetailAndCategory(ad.getTitle(), ad.getDetail(), ad.getCategory()) > 0;
    }

    private boolean isSuitableToActivate(Advertisement ad, Status status) {
        return status.equals(Status.ACTIVE) && ad.getStatus().equals(Status.WAITING_FOR_APPROVAL);
    }

    private boolean isSuitableToDeactivate(Advertisement ad, Status status) {
        return status.equals(Status.DEACTIVATE)
                && (ad.getStatus().equals(Status.ACTIVE) || ad.getStatus().equals(Status.WAITING_FOR_APPROVAL));
    }

    private StatusHistory addStatusHistory(Advertisement ad, Status status) {
        var statusHistory = StatusHistory.builder()
                .status(status)
                .advertisement(ad)
                .updatedDate(OffsetDateTime.now())
                .build();
        return statusHistoryCommandService.add(statusHistory);
    }

    private boolean areThereAnyBadWords(String word) {
        return resourceService.getBadWordList().stream().anyMatch(word::contains);
    }

}
