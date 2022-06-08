package com.example.demo.Controller;

import com.example.demo.Entity.Isend;
import com.example.demo.Entity.Pic;
import com.example.demo.Service.PicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ocr.v20181119.OcrClient;
import com.tencentcloudapi.ocr.v20181119.models.*;
import sun.misc.BASE64Encoder;

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

    public static String encodeBase64File(String path) throws Exception {
        File file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return new BASE64Encoder().encode(buffer);
    }

    public String check(String type, String file) {
        // 1特征，2文字
        //        String path = "./src/main/resources/static/check.py";
        //        String[] cmdArr = new String[]{"python", path, type, file};
        //        cmdArr[3] = new File(cmdArr[3]).getAbsolutePath();
        ////        System.out.println(type+"   "+cmdArr[3]);
        //        Process process = null;
        //        try {
        //            process = Runtime.getRuntime().exec(cmdArr);
        //            InputStream is = process.getInputStream();
        //            DataInputStream dis = new DataInputStream(is);
        //            String str = dis.readLine();
        //            return str;
        //        } catch (IOException e) {
        //            e.printStackTrace();
        //        }
        //        return "ERROR";
        try {
            String base64 = encodeBase64File(file);
            // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
            // 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
            Credential cred = new Credential("AKIDIeu4iGJLxUwLgaNAuaClEycAd1Laoz5Q", "TrDWmzALSXcemw5AA70nWQH8veM8862w");
            // 实例化一个http选项，可选的，没有特殊需求可以跳过
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("ocr.tencentcloudapi.com");
            // 实例化一个client选项，可选的，没有特殊需求可以跳过
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            // 实例化要请求产品的client对象,clientProfile是可选的
            OcrClient client = new OcrClient(cred, "ap-beijing", clientProfile);
            if (type.equals("1")) {
                // 实例化一个请求对象,每个接口都会对应一个request对象
                RecognizeHealthCodeOCRRequest req = new RecognizeHealthCodeOCRRequest();
                req.setImageBase64(base64);
                // 返回的resp是一个RecognizeHealthCodeOCRResponse的实例，与请求对象对应
                RecognizeHealthCodeOCRResponse resp = client.RecognizeHealthCodeOCR(req);
                // 输出json格式的字符串回包
                if (resp.getColor().equals("绿色")) {
                    return "1";
                }
//                System.out.println(RecognizeHealthCodeOCRResponse.toJsonString(resp));
            } else if (type.equals("2")) {
                // 实例化一个请求对象,每个接口都会对应一个request对象
                RecognizeTravelCardOCRRequest req = new RecognizeTravelCardOCRRequest();
                req.setImageBase64(base64);
                // 返回的resp是一个RecognizeTravelCardOCRResponse的实例，与请求对象对应
                RecognizeTravelCardOCRResponse resp = client.RecognizeTravelCardOCR(req);
                // 输出json格式的字符串回包
                if (resp.getRiskArea().length==0) {
                    return "1";
                }
//                System.out.println(RecognizeTravelCardOCRResponse.toJsonString(resp));
            }else {
                GeneralFastOCRRequest req = new GeneralFastOCRRequest();
                req.setImageBase64(base64);
                // 返回的resp是一个GeneralFastOCRResponse的实例，与请求对象对应
                GeneralFastOCRResponse resp = client.GeneralFastOCR(req);
                // 输出json格式的字符串回包
                for (TextDetection i:resp.getTextDetections()){
                if (i.getDetectedText().contains(type)) {
//                    System.out.println(i.toString());
                    return "1";
                }

            }}

        } catch (Exception e) {
            System.out.println(e.toString());
            return "0";
        }
        return "0";
    }


    @ResponseBody
    @PostMapping("/add")
    public String add(@RequestParam("userid") String userid, @RequestParam("taskid") String
            taskid, @RequestParam("type") String type, @RequestParam("file") MultipartFile file) {
        System.out.println("YESYES!" + userid + "     taskid:" + taskid + "     type:" + type);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "对不起，我们出了错误！";
    }

    @ResponseBody
    @GetMapping("/getPic")
    public List<Isend> getTask(@RequestParam("id") int id) {
        List<Isend> result = picService.getPics(id);
        return result;
    }

    @ResponseBody
    @GetMapping("/getPicUnPass")
    public List<Isend> getPicUnPass(@RequestParam("id") int id) {
        List<Isend> result = picService.getPicUnPass(id);
        return result;
    }

    @ResponseBody
    @GetMapping("/getPicUnFinish")
    public List<Isend> getPicUnFinish(@RequestParam("id") int id) {
        List<Isend> result = picService.getPicUnFinish(id);
        return result;
    }

    @ResponseBody
    @GetMapping("/getPicPass")
    public List<Isend> getPicPass(@RequestParam("id") int id) {
        List<Isend> result = picService.getPicPass(id);
        return result;
    }

    @ResponseBody
    @GetMapping("/getPicD")
    public Pic getPicD(@RequestParam("id") int id) {
        Pic result = picService.getPicDetail(id);
        return result;
    }

    @ResponseBody
    @GetMapping("/alterR")
    public String alterR(@RequestParam("picid") int picid, @RequestParam("result") String picresult) {
        int result = picService.alterR(picid, picresult);
        if (result > 0) {
            return "success";
        } else {
            return "failed";
        }

        @ResponseBody
        @GetMapping("/second")
        public Pic second (@RequestParam("userid") String userid,@RequestParam("taskid") int taskid){
            return picService.second(userid,taskid);
        }


    }
}
