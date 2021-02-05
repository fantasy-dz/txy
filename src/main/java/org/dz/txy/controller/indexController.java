package org.dz.txy.controller;

import org.dz.txy.entity.DataVO;
import org.dz.txy.utils.ExcelUtils;
import org.dz.txy.utils.LinuxCommand;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @RequestMapping("/test")
    @ResponseBody
    public String test(@RequestBody DataVO data) {
        System.out.println(data.toString());
        return data.toString();
    }

    @PostMapping("/uploadExcel")
    @ResponseBody
    public String uploadExcel(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }
        String[] title = {"COMPON_CODE", "COMPON_VERSION", "COMPON_NAME",
                "COMPON_UNIT", "GL_CODE",
                "COMPON_SPEC", "COMPON_SIZE", "COMPON_SPEC1", "null"};
        String filename = file.getOriginalFilename();
        InputStream in = file.getInputStream();
//        List<Map<String, Object>> list = ExcelUtils.parsExcel(in, filename, 1, 0, new int[]{3, 4, 5, 8, 9, 10, 11, 12, 13, 15, 16, 17, 18, 19, 20, 21, 22}, title);
//        list.stream().forEach(s -> System.out.println(s));
        List<String[]> list = ExcelUtils.parsExcel(in, filename, 1, 0, new int[]{3, 4, 5, 7, 8, 9, 10, 11, 12, 14, 15, 16, 17, 18, 19, 20, 21});
        List<String> insertList = new ArrayList<>();
        int infoId = 1;
        String endInfo = "\'20210129\',\'20210129\'";
        for (String[] str : list) {
            String tempStr = "\'" + infoId + "\',";
            for (int i = 0; i < str.length; i++) {
                tempStr += "'" + str[i] + "',";
            }
            tempStr += endInfo;
//            String newStr = tempStr.substring(0, tempStr.lastIndexOf(","));
            insertList.add(tempStr);
            infoId++;
        }
        String resultStr = insertList.stream().map(s -> s = "(" + s + ")").reduce((x, y) -> x + "," + y).get();
        System.out.println(resultStr);
        return "";
    }
}

