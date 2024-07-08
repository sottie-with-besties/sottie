package com.sottie.app.certification.adapter;

import com.sottie.app.certification.application.CertificationService;
import com.sottie.app.certification.model.Certification;
import lombok.RequiredArgsConstructor;
import net.minidev.json.parser.JSONParser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.*;

@RestController
@RequiredArgsConstructor
class GetCertificationController {

    private final CertificationService certificationService;

    @PostMapping("/sottie/certifications")
    public Certification getCertifications (@RequestBody String certifications) throws Exception {
        System.out.println(certifications);

        String accessToken = certificationService.getToken();
        Certification certificationsInfo = certificationService.getCertificationsInfo(certifications, accessToken);
        return certificationsInfo;
    }
}
