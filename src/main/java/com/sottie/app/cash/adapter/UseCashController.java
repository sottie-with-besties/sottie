package com.sottie.app.cash.adapter;

import com.sottie.app.cash.application.UseCashService;
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
class UseCashController {

    private final UseCashService useCashService;

    @PostMapping("/sottie/cash/use")
    public ResponseEntity<CashLogDto> useCash(@RequestBody @Valid CashRequest cashRequest) {
        CashLogDto cashLogDto = useCashService.useCash(cashRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(cashLogDto);
    }

}
