package com.sottie.app.cash.adapter;

import com.sottie.app.cash.application.ChargeCashService;
import com.sottie.app.cash.model.dto.CashLogDto;
import com.sottie.app.cash.model.record.CashRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
class ChargeCashController {

    private final ChargeCashService chargeCashService;

    @PostMapping("/sottie/cash/charge")
    public ResponseEntity<CashLogDto> chargeCash(@RequestBody @Valid CashRequest cashRequest) {
        CashLogDto cashLogDto = chargeCashService.chargeCash(cashRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(cashLogDto);
    }

}
