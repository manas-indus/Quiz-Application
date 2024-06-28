package com.indusnet.ums.controller;

import com.google.gson.Gson;
import com.indusnet.ums.dto.UserModelDto;
import com.indusnet.ums.model.ChangePassword;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


import com.indusnet.ums.common.MessageTypeConst;
import com.indusnet.ums.common.LoggingResponseModel;
import com.indusnet.ums.common.ResponseModel;
import com.indusnet.ums.model.UserModel;

import com.indusnet.ums.service.IUserService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/ums/")
@Slf4j
public class UserController {
    
	@Autowired
    Gson gson;
    
	@Autowired
    IUserService service;
    
	@PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseModel>register(@Valid @RequestBody UserModel model) {
        
		LoggingResponseModel msgStart = LoggingResponseModel.builder()
                .message("Start add register")
                .messageTypeId(MessageTypeConst.SUCCESS)
                .build();
        log.info(gson.toJson(msgStart));
        
        ResponseModel response = service.register(model);
        HttpStatus status = response.getStatusCode() != null ? response.getStatusCode() : HttpStatus.NOT_FOUND;
        
        LoggingResponseModel msgEnd = LoggingResponseModel.builder()
                .message("End Add register")
                .messageTypeId(MessageTypeConst.SUCCESS)
                .build();
        log.info(gson.toJson(msgEnd));
        
        return new ResponseEntity<ResponseModel>(response, status);
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseModel>login(@Valid @RequestBody UserModel model) {
        
    	LoggingResponseModel msgStart = LoggingResponseModel.builder()
                .message("Start add Login")
                .messageTypeId(MessageTypeConst.SUCCESS)
                .build();
        log.info(gson.toJson(msgStart));
        
        ResponseModel response = service.login(model);
        HttpStatus status = response.getStatusCode() != null ? response.getStatusCode() : HttpStatus.NOT_FOUND;
        
        LoggingResponseModel msgEnd = LoggingResponseModel.builder()
                .message("End Add login")
                .messageTypeId(MessageTypeConst.SUCCESS)
                .build();
        log.info(gson.toJson(msgEnd));
        
        return new ResponseEntity<ResponseModel>(response, status);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ResponseModel>forgotPassword(@Valid @RequestBody UserModelDto userModelDto) {
        
    	LoggingResponseModel msgStart = LoggingResponseModel.builder()
                .message("Start add forgotPassword")
                .messageTypeId(MessageTypeConst.SUCCESS)
                .build();
        log.info(gson.toJson(msgStart));
        
        ResponseModel response = service.forgotPassword(userModelDto);
        HttpStatus status = response.getStatusCode() != null ? response.getStatusCode() : HttpStatus.NOT_FOUND;
        
        LoggingResponseModel msgEnd = LoggingResponseModel.builder()
                .message("End Add forgotPassword")
                .messageTypeId(MessageTypeConst.SUCCESS)
                .build();
        log.info(gson.toJson(msgEnd));
        
        return new ResponseEntity<ResponseModel>(response, status);
    }
    @PostMapping("/change-password")
    public ResponseEntity<ResponseModel>changePassword(@Valid @RequestBody ChangePassword changePassword) {
        
    	LoggingResponseModel msgStart = LoggingResponseModel.builder()
                .message("Start add changePassword")
                .messageTypeId(MessageTypeConst.SUCCESS)
                .build();
        log.info(gson.toJson(msgStart));
        
        ResponseModel response = service.changePassword(changePassword);
        HttpStatus status = response.getStatusCode() != null ? response.getStatusCode() : HttpStatus.NOT_FOUND;
        
        LoggingResponseModel msgEnd = LoggingResponseModel.builder()
                .message("End Add changePassword")
                .messageTypeId(MessageTypeConst.SUCCESS)
                .build();
        log.info(gson.toJson(msgEnd));
        
        return new ResponseEntity<ResponseModel>(response, status);
    }

    @PutMapping("/update-profile")
    public ResponseEntity<ResponseModel>updateProfile(@Valid @RequestBody UserModel userModel) {
        
    	LoggingResponseModel msgStart = LoggingResponseModel.builder()
                .message("Start add updateProfile")
                .messageTypeId(MessageTypeConst.SUCCESS)
                .build();
        log.info(gson.toJson(msgStart));
        
        ResponseModel response = service.updateProfile(userModel);
        HttpStatus status = response.getStatusCode() != null ? response.getStatusCode() : HttpStatus.NOT_FOUND;
        
        LoggingResponseModel msgEnd = LoggingResponseModel.builder()
                .message("End Add updateProfile")
                .messageTypeId(MessageTypeConst.SUCCESS)
                .build();
        log.info(gson.toJson(msgEnd));
        
        return new ResponseEntity<ResponseModel>(response, status);
    }
}


