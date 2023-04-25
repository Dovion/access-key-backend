package com.example.accesskeybackend.template.controller;

import com.example.accesskeybackend.template.service.AccessKeyWebService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/web")
@AllArgsConstructor
public class AccessKeyWebController {

    private final AccessKeyWebService accessKeyWebService;

    @PostMapping("/checkIpv6Support")
    public boolean isSupportIpV6(@RequestParam String siteUrl) {
        return accessKeyWebService.isSupportIpV6(siteUrl);
    }
}
