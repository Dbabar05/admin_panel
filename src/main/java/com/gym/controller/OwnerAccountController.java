package com.gym.controller;

import com.gym.communication.dto.OwnerCreationRequest;
import com.gym.dto.ResponseDto;
import com.gym.security.OwnerAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/owner")
@RequiredArgsConstructor
public class OwnerAccountController {

    private final OwnerAccountService ownerAccountService;

    @PostMapping("/create-owner")
    public ResponseEntity<ResponseDto> createOwner(@RequestBody OwnerCreationRequest request){
        ResponseDto responseDto = ownerAccountService.createOwner(request);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);

    }


}
