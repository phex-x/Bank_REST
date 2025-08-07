package com.example.bankcards.controller;

import com.example.bankcards.dto.TransferRequestDTO;
import com.example.bankcards.dto.TransferResponseDTO;
import com.example.bankcards.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequiredArgsConstructor
public class TransferController {
    private final TransferService transferService;

    @PostMapping("/transfer")
    public TransferResponseDTO transfer(TransferRequestDTO transferRequestDTO) {
        return transferService.createTransfer(transferRequestDTO);
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('USER')")
    public Page<TransferResponseDTO> getMyTransfers(Pageable pageable) {
        return transferService.getMyTransfers(pageable);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<TransferResponseDTO> getAllTransfers(Pageable pageable) {
        return transferService.getAllTransfers(pageable);
    }
}
