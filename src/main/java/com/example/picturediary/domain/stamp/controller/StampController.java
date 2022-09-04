package com.example.picturediary.domain.stamp.controller;

import com.example.picturediary.common.response.CommonResponse;
import com.example.picturediary.domain.diary.response.SingleDiaryWithStampResponse;
import com.example.picturediary.domain.stamp.request.AddStampRequest;
import com.example.picturediary.domain.stamp.service.StampService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/stamp")
public class StampController
{
    private final StampService stampService;

    @Autowired
    public StampController(StampService stampService)
    {
        this.stampService = stampService;
    }

    @ApiOperation("일기에 도장 추가")
    @PostMapping
    public ResponseEntity<CommonResponse> addStamp(
        @Valid AddStampRequest addStampRequest,
        @AuthenticationPrincipal @ApiIgnore UserDetails user)
    {
        stampService.addStamp(addStampRequest, user);
        return new ResponseEntity<>(new CommonResponse("도장 찍기 성공"), HttpStatus.OK);
    }
}
