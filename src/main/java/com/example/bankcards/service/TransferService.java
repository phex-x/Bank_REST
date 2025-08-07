package com.example.bankcards.service;

import com.example.bankcards.dto.TransferRequestDTO;
import com.example.bankcards.dto.TransferResponseDTO;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.Transfer;
import com.example.bankcards.exception.CardNotFoundException;
import com.example.bankcards.exception.InsufficientFundsException;
import com.example.bankcards.exception.NotOwnedCardTransferException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.TransferRepository;
import com.example.bankcards.util.mapper.TransferMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransferService {
    private final TransferRepository transferRepository;
    private final CardRepository cardRepository;
    private final TransferMapper transferMapper;

    public TransferResponseDTO createTransfer(TransferRequestDTO transferRequestDTO) {
        Card sourceCard = cardRepository.findById(transferRequestDTO.getSourceCardId())
                .orElseThrow(() -> new CardNotFoundException("card with id " + transferRequestDTO.getSourceCardId() + " not found"));
        Card destinationCard = cardRepository.findById(transferRequestDTO.getDestinationCardId())
                .orElseThrow(() -> new CardNotFoundException("card with id " + transferRequestDTO.getDestinationCardId() + " not found"));

        if (sourceCard.getBalance().compareTo(transferRequestDTO.getAmount()) < 0) {
            throw new InsufficientFundsException("insufficient funds on source card");
        }

        if (!sourceCard.getOwner().equals(destinationCard.getOwner())) {
            throw new NotOwnedCardTransferException("attempt to transfer not between your cards");
        }
        Transfer transfer = transferMapper.toEntity(transferRequestDTO);
        transferRepository.save(transfer);
        log.info("Transfer created");

        return transferMapper.toDTO(transfer);
    }

    public Page<TransferResponseDTO> getMyTransfers(Pageable pageable) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return transferRepository.findAllBySourceCardOwnerUsername(username, pageable).map(transferMapper::toDTO);
    }

    public Page<TransferResponseDTO> getAllTransfers(Pageable pageable) {
        return transferRepository.findAll(pageable).map(transferMapper::toDTO);
    }
}
