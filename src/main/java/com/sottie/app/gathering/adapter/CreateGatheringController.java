package com.sottie.app.gathering.adapter;

import com.sottie.app.gathering.application.CreateGatheringService;
import com.sottie.app.gathering.model.dto.GatheringDto;
import com.sottie.app.gathering.model.record.CreateGatheringRequest;
import com.sottie.security.Permission;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Permission(roles = {"USER"})
class CreateGatheringController {

    private final CreateGatheringService createGatheringService;

    @PostMapping("/sottie/gathering")
    public ResponseEntity<GatheringDto> createGathering(@RequestBody @Valid CreateGatheringRequest createGatheringRequest) {
        GatheringDto result = createGatheringService.createGathering(createGatheringRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

}
