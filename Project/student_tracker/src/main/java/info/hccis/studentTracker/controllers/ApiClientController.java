package info.hccis.studentTracker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/studenttracker/client")
public class ApiClientController {

    @GetMapping("/rest")
    public String restClient() {
        // points to templates/studenttracker/client/restClient.html
        return "studenttracker/client/restClient";
    }

    @GetMapping("/soap")
    public String soapClient() {
        // points to templates/studenttracker/client/soapClient.html
        return "studenttracker/client/soapClient";
    }
}

