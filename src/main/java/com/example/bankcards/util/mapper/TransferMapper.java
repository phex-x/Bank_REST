package com.example.bankcards.util.mapper;

import com.example.bankcards.dto.TransferRequestDTO;
import com.example.bankcards.dto.TransferResponseDTO;
import com.example.bankcards.entity.Transfer;
import com.example.bankcards.exception.CardNotFoundException;
import com.example.bankcards.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransferMapper {
    private final CardRepository cardRepository;

    public Transfer toEntity(TransferRequestDTO transferRequestDTO) {
        Transfer transfer = new Transfer();
        transfer.setSourceCard(cardRepository.findById(transferRequestDTO.getSourceCardId())
                .orElseThrow(() -> new CardNotFoundException("card with id " + transferRequestDTO.getSourceCardId() + " not found")));
        transfer.setDestinationCard(cardRepository.findById(transferRequestDTO.getDestinationCardId())
                .orElseThrow(() -> new CardNotFoundException("card with id " + transferRequestDTO.getDestinationCardId() + " not found")));
        transfer.setAmount(transferRequestDTO.getAmount());

        return transfer;
    }

    public TransferResponseDTO toDTO(Transfer transfer) {
        TransferResponseDTO transferResponseDTO = new TransferResponseDTO();
        transferResponseDTO.setId(transfer.getId());
        transferResponseDTO.setMaskedSourceCard(cardRepository.findByEncryptedCardNumber(transfer.getSourceCard()
                .getEncryptedCardNumber()).getMaskedCardNumber());
        transferResponseDTO.setMaskedDestinationCard(cardRepository.findByEncryptedCardNumber(transfer.getDestinationCard()
                .getEncryptedCardNumber()).getMaskedCardNumber());
        transferResponseDTO.setAmount(transfer.getAmount());
        transferResponseDTO.setTimestamp(transfer.getTimestamp());

        return transferResponseDTO;
    }
}
