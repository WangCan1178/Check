package com.example.demo.Controller;

import com.example.demo.Entity.Isend;
import com.example.demo.Entity.Pic;
import com.example.demo.Service.PicService;
import com.example.demo.Service.UserService;
import org.python.modules._sre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

//import com.tencentcloudapi.common.Credential;
//import com.tencentcloudapi.common.profile.ClientProfile;
//import com.tencentcloudapi.common.profile.HttpProfile;
//import com.tencentcloudapi.common.exception.TencentCloudSDKException;
//import com.tencentcloudapi.ocr.v20181119.OcrClient;
//import com.tencentcloudapi.ocr.v20181119.models.*;

@Controller
@CrossOrigin
@RequestMapping("/pic")
public class PicController {
    private String pos;
    private String neg;
    private String filetype;
    private PicService picService;

    public PicService getPicService() {
        return picService;
    }

    @Autowired
    public void setUserService(PicService picService) {
        this.picService = picService;
    }

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

//    @ResponseBody
//    @PostMapping("/add")
//    public String add(@RequestParam("userid") String userid, @RequestParam("taskid") String taskid, @RequestParam("type") String type, @RequestParam("file") MultipartFile file)
//    {
//
//            try {
//                // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
//                // 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
//                Credential cred = new Credential("SecretId", "SecretKey");
//                // 实例化一个http选项，可选的，没有特殊需求可以跳过
//                HttpProfile httpProfile = new HttpProfile();
//                httpProfile.setEndpoint("ocr.tencentcloudapi.com");
//                // 实例化一个client选项，可选的，没有特殊需求可以跳过
//                ClientProfile clientProfile = new ClientProfile();
//                clientProfile.setHttpProfile(httpProfile);
//                // 实例化要请求产品的client对象,clientProfile是可选的
//                OcrClient client = new OcrClient(cred, "", clientProfile);
//                // 实例化一个请求对象,每个接口都会对应一个request对象
//                RecognizeTravelCardOCRRequest req = new RecognizeTravelCardOCRRequest();
//
//                // 返回的resp是一个RecognizeTravelCardOCRResponse的实例，与请求对象对应
//                RecognizeTravelCardOCRResponse resp = client.RecognizeTravelCardOCR(req);
//                // 输出json格式的字符串回包
//                System.out.println(RecognizeTravelCardOCRResponse.toJsonString(resp));
//            } catch (TencentCloudSDKException e) {
//                System.out.println(e.toString());
//            }
//        }


        @ResponseBody
        @PostMapping("/add")
        public String add (@RequestParam("userid") String userid, @RequestParam("taskid") String
        taskid, @RequestParam("type") String type, @RequestParam("file") MultipartFile file){
            System.out.println("YESYES!" + userid + taskid + type + file.toString());
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
                String photo = dest.getAbsolutePath();
                file.transferTo(new File(dest.getAbsolutePath()));
                String result = check(type, filePath + fileName);
//            System.out.println(result);
                picService.alterPic(Integer.parseInt(taskid), userid, photo, result);
                if (result.equals("1")) {
                    return "提交成功！您的判定结果为通过！";
                } else {
                    return "提交成功！您的判定结果为失败！";
                }
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
            return "对不起，我们出了错误！";
        }

        @ResponseBody
        @GetMapping("/getPic")
        public List<Pic> getTask ( @RequestParam("id") int id){
            List<Pic> result = picService.getPics(id);
            return result;
        }

        @ResponseBody
        @GetMapping("/getPicUnPass")
        public List<Pic> getPicUnPass ( @RequestParam("id") int id){
            List<Pic> result = picService.getPicUnPass(id);
            return result;
        }

        @ResponseBody
        @GetMapping("/getPicUnFinish")
        public List<Pic> getPicUnFinish ( @RequestParam("id") int id){
            List<Pic> result = picService.getPicUnFinish(id);
            return result;
        }

        @ResponseBody
        @GetMapping("/getPicPass")
        public List<Pic> getPicPass ( @RequestParam("id") int id){
            List<Pic> result = picService.getPicPass(id);
            return result;
        }

        @ResponseBody
        @GetMapping("/getPicD")
        public Pic getPicD ( @RequestParam("id") int id){
            Pic result = picService.getPicDetail(id);
            return result;
        }

        @ResponseBody
        @GetMapping("/alterR")
        public String alterR ( @RequestParam("picid") int picid, @RequestParam("result") String picresult){
            int result = picService.alterR(picid, picresult);
            if (result > 0) {
                return "success";
            } else {
                return "failed";
            }
        }
    }
