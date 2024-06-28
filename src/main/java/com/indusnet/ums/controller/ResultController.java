package com.indusnet.ums.controller;

import com.indusnet.ums.dto.ResultResponse;
import com.indusnet.ums.model.Result;
import com.indusnet.ums.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/results")
public class ResultController {
    @Autowired
    private ResultService resultService;

    @PostMapping
    public ResultResponse submitResult(@RequestBody Result result) {
        return resultService.submitResult(result);
    }
}