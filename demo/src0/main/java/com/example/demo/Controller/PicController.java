package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;


@Controller
@CrossOrigin
@RequestMapping("/pic")
public class PicController {
    private String pos;
    private String neg;
    private String filetype;

    public String check(String type, String file) {
        // TODO: 根据任务号获取正例，负例和类型
        // 1特征，2文字
        filetype = String.valueOf("1");
        String path = "./src/main/resources/static/check.py";
        String[] cmdArr = new String[]{"python", path, type, file};
        cmdArr[3] = new File(cmdArr[3]).getAbsolutePath();
//        System.out.println(type+"   "+cmdArr[3]);
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(cmdArr);
            InputStream is = process.getInputStream();
            DataInputStream dis = new DataInputStream(is);
            String str = dis.readLine();
            return str;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ERROR";
    }

    @ResponseBody
    @PostMapping("/add")
    public String add(@RequestParam("type") String type, @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "文件为空";
        }
        // 2获取文件名
        String fileName = file.getOriginalFilename();
        // 3获取后缀
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        // 4路径
        String filePath = "./src/main/resources/static/pics/";
        File dest = new File(filePath + fileName);
        // 5判断目录是否存在
        if (!dest.getParentFile().exists()) {
            // 不存在，创建
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(new File(dest.getAbsolutePath()));
//            check(dest,taskid);
            String result = check(type, filePath + fileName);
            System.out.println(result);
            if (result.equals("1")) {
                return "提交成功！您的判定结果为通过！";
            } else {
                return "提交成功！您的判定结果为失败！";
            }
        } catch (IllegalStateException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "对不起，我们出了错误！";
    }
}
