package com.example.accesskeybackend.template.service;

import com.example.accesskeybackend.template.PatternUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
@Log4j2
public class AccessKeyWebService {

    public boolean isSupportIpV6(String siteUrl) {
        URI uri;
        Process process;
        try {
            String site = siteUrl;
            if (!site.contains("http://") || !site.contains("https://")) {
                site = "http://" + site;
            }
            uri = new URI(site.replaceAll("\\s+", ""));
            String host = uri.getHost();
            String[] cmd = {"bash", "ip-search.sh", host};
            process = Runtime.getRuntime().exec(cmd);
        } catch (URISyntaxException | IOException | NullPointerException e) {
            throw new com.example.accesskeybackend.exception.IllegalArgumentException(
                    String.format("Bad siteUrl: %s", siteUrl));
        }

        String result = new BufferedReader(new InputStreamReader(process.getInputStream())).lines()
                                                                                           .parallel()
                                                                                           .collect(
                                                                                                   Collectors.joining());
        Matcher matcher = PatternUtil.IP_V6_FORMAT.matcher(result);
        var ipList = new HashSet<String>();
        while (matcher.find()) {
            ipList.add(matcher.group());
        }
        if (ipList.size() > 0) {
            return true;
        }
        return false;

    }


}