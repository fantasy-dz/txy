package org.dz.txy.controller;

import org.dz.txy.utils.LinuxCommand;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

import static java.util.stream.Collectors.toList;

@Controller
public class indexController {
    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/startServer")
    @ResponseBody
    public String startServer() {
        String result = LinuxCommand.execShell("~/mcworld/", "startMc.sh");
        return result;
    }

    @RequestMapping("/stopServer")
    @ResponseBody
    public String stopServer() {
        String result = LinuxCommand.execShell("~/mcworld/", "stopMc.sh");
        return result;
    }
}

