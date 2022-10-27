//package com.switchfully.eurder.api;
//
//import com.switchfully.eurder.domain.customer.Feature;
//import com.switchfully.eurder.service.security.SecurityService;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/login")
//@CrossOrigin
//public class SecurityController {
//
//    private final SecurityService securityService;
//
//    public SecurityController(SecurityService securityService) {
//        this.securityService = securityService;
//    }
//
//    @GetMapping
//    public void login(@RequestHeader String authorization) {
//        securityService.validateAuthorization(authorization, Feature.DEFAULT);
//    }
//}
