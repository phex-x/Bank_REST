package com.example.bankcards.controller;

import com.example.bankcards.dto.*;
import com.example.bankcards.entity.BlockRequestStatus;
import com.example.bankcards.entity.User;
import com.example.bankcards.service.BlockService;
import com.example.bankcards.service.CardService;
import com.example.bankcards.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final UserService userService;
    private final CardService cardService;
    private final BlockService blockService;

    public AdminController(UserService userService, CardService cardService, BlockService blockService) {
        this.userService = userService;
        this.cardService = cardService;
        this.blockService = blockService;
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id ) {
        UserResponse user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/user/get-all")
    public ResponseEntity<Page<UserResponse>> getAllUsers(
            @PageableDefault(
                    size = 10,
                    sort = "id",
                    direction = Sort.Direction.DESC
            )
            Pageable pageable) {
        Page<UserResponse> users = userService.getAllUsers(pageable);

        return ResponseEntity.ok(users);
    }

    @PatchMapping("/user/disable/{id}")
    public ResponseEntity<?> disableUser(@PathVariable Long id) {
        userService.disableUser(id);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/user/enable/{id}")
    public ResponseEntity<?> enableUser(@PathVariable Long id) {
        userService.enableUser(id);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/user/change-role")
    public ResponseEntity<UserResponse> changeUserRole(@RequestBody UserChangeRoleRequest userChangeRoleRequest) {
        UserResponse user = userService.changeRole(userChangeRoleRequest.getId(), userChangeRoleRequest.getRole());

        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/card/create")
    public ResponseEntity<CardResponse> createCard(@Valid @RequestBody CardCrateRequest cardCrateRequest) {

        return ResponseEntity.ok(cardService.createCard(cardCrateRequest));
    }

    @PatchMapping("/card/change-status")
    public ResponseEntity<CardResponse> activateCard(@Valid @RequestBody CardChangeStatusRequest cardChangeStatusRequest) {
        return ResponseEntity.ok(
                cardService.changeStatus(cardChangeStatusRequest.getId(),
                        cardChangeStatusRequest.getStatus()))
                ;
    }

    @GetMapping("/card/get-all")
    public ResponseEntity<Page<CardResponse>> getAllCards(Pageable pageable) {
        return ResponseEntity.ok(cardService.getAllCards(pageable));
    }

    @DeleteMapping("/card/delete/{id}")
    public ResponseEntity<?> deleteCard(@PathVariable Long id) {
        cardService.deleteCard(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/to-block/get-all")
    public ResponseEntity<Page<BlockResponseBeforeResult>> getAllRequests(Pageable pageable) {
        return ResponseEntity.ok(blockService.getAllRequests(pageable));
    }

    @GetMapping("/to-block/{id}")
    public ResponseEntity<BlockResponseBeforeResult> getRequest(@PathVariable Long id) {
        return ResponseEntity.ok(blockService.getRequestById(id));
    }

    @PostMapping("/to-block/result")
    public ResponseEntity<BlockResponseAfterResult> acceptBlock(@Valid @RequestBody AdminBlockRequest adminBlockRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assert auth != null;
        User admin = (User) auth.getPrincipal();
        assert admin != null;

        if (adminBlockRequest.getStatus().equals(BlockRequestStatus.APPROVED)) {
            return ResponseEntity.ok(blockService.acceptBlock(admin.getId(), adminBlockRequest.getRequestId(), adminBlockRequest.getComment()));
        } else {
            return ResponseEntity.ok(blockService.rejectBlock(admin.getId(), adminBlockRequest.getRequestId(), adminBlockRequest.getComment()));
        }
    }
}
