package com.example.bankcards.service;

import com.example.bankcards.dto.BlockResponseAfterResult;
import com.example.bankcards.dto.BlockResponseBeforeResult;
import com.example.bankcards.entity.BlockRequestStatus;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.Status;
import com.example.bankcards.entity.ToBlock;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.ToBlockRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.util.ToBlockMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BlockService {
    private final ToBlockRepository toBlockRepository;
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final ToBlockMapper toBlockMapper;
    private final CardService cardService;

    public BlockService(ToBlockRepository toBlockRepository, CardRepository cardRepository, UserRepository userRepository, ToBlockMapper toBlockMapper, CardService cardService) {
        this.toBlockRepository = toBlockRepository;
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
        this.toBlockMapper = toBlockMapper;
        this.cardService = cardService;
    }

    public BlockResponseBeforeResult requestBlock(Long userId, Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new BadCredentialsException("Card not found"));

        if (!card.getUser().getId().equals(userId)) {
            throw new BadCredentialsException("you don't have right to this card");
        }

        if (!card.getStatus().equals(Status.ACTIVE)) {
            throw new BadCredentialsException("you don't have active card");
        }

        ToBlock toBlock = new ToBlock();
        toBlock.setCard(card);
        toBlock.setUser(userRepository.findById(userId).orElseThrow(() -> new BadCredentialsException("User not found")));
        toBlockRepository.save(toBlock);

        return toBlockMapper.toBlockResponseBeforeResult(toBlock);
    }

    public BlockResponseAfterResult acceptBlock(Long adminId,Long requestId, String comment) {
        ToBlock toBlock = toBlockRepository.findById(requestId)
                .orElseThrow(() -> new BadCredentialsException("request not found"));
        toBlock.setAdmin(userRepository.findById(adminId).orElseThrow(() -> new BadCredentialsException("admin not found")));
        toBlock.setProcessedDateTime(LocalDateTime.now());
        toBlock.setStatus(BlockRequestStatus.APPROVED);
        toBlock.setAdminComment(comment);
        toBlockRepository.save(toBlock);

        cardService.changeStatus(toBlock.getCard().getId(), Status.BLOCKED);

        return toBlockMapper.toBlockResponseAfterResult(toBlock);
    }

    public BlockResponseAfterResult rejectBlock(Long adminId,Long requestId, String comment) {
        ToBlock toBlock = toBlockRepository.findById(requestId)
                .orElseThrow(() -> new BadCredentialsException("request not found"));
        toBlock.setAdmin(userRepository.findById(adminId).orElseThrow(() -> new BadCredentialsException("admin not found")));
        toBlock.setProcessedDateTime(LocalDateTime.now());
        toBlock.setStatus(BlockRequestStatus.REJECTED);
        toBlock.setAdminComment(comment);
        toBlockRepository.save(toBlock);

        return toBlockMapper.toBlockResponseAfterResult(toBlock);
    }

    public Page<BlockResponseBeforeResult> getAllRequests(Pageable pageable) {
        Page<ToBlock> toBlocks = toBlockRepository.findAll(pageable);

        return toBlocks.map(toBlockMapper::toBlockResponseBeforeResult);
    }

    public BlockResponseBeforeResult getRequestById(Long requestId) {
        ToBlock toBlock = toBlockRepository.findById(requestId)
                .orElseThrow(() -> new BadCredentialsException("request not found"));

        return toBlockMapper.toBlockResponseBeforeResult(toBlock);
    }
}
