package com.example.bankcards.util;

import com.example.bankcards.dto.BlockResponseAfterResult;
import com.example.bankcards.dto.BlockResponseBeforeResult;
import com.example.bankcards.entity.ToBlock;
import org.springframework.stereotype.Component;

@Component
public class ToBlockMapper {
    private final UserMapper userMapper;
    private final CardMapper cardMapper;

    public ToBlockMapper(UserMapper userMapper, CardMapper cardMapper) {
        this.userMapper = userMapper;
        this.cardMapper = cardMapper;
    }

    public BlockResponseAfterResult toBlockResponseAfterResult(ToBlock toBlock) {
        BlockResponseAfterResult blockResponseAfterResult = new BlockResponseAfterResult();
        blockResponseAfterResult.setId(toBlock.getId());
        blockResponseAfterResult.setUser(userMapper.toUserResponse(toBlock.getUser()));
        blockResponseAfterResult.setRequestDateTime(toBlock.getRequestDateTime());
        blockResponseAfterResult.setAdmin(userMapper.toUserResponse(toBlock.getAdmin()));
        blockResponseAfterResult.setCard(cardMapper.toCardResponse(toBlock.getCard()));
        blockResponseAfterResult.setProcessedDateTime(toBlock.getProcessedDateTime());
        blockResponseAfterResult.setAdminComment(toBlock.getAdminComment());
        blockResponseAfterResult.setStatus(toBlock.getStatus());

        return blockResponseAfterResult;
    }

    public BlockResponseBeforeResult toBlockResponseBeforeResult(ToBlock toBlock) {
        BlockResponseBeforeResult blockResponseBeforeResult = new BlockResponseBeforeResult();
        blockResponseBeforeResult.setId(toBlock.getId());
        blockResponseBeforeResult.setUser(userMapper.toUserResponse(toBlock.getUser()));
        blockResponseBeforeResult.setRequestDateTime(toBlock.getRequestDateTime());
        blockResponseBeforeResult.setCard(cardMapper.toCardResponse(toBlock.getCard()));
        blockResponseBeforeResult.setStatus(toBlock.getStatus());

        return blockResponseBeforeResult;
    }
}
