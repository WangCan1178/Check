package com.example.demo.Controller;

import com.example.demo.Dao.MessageDao;
import com.example.demo.Entity.Isend;
import com.example.demo.Entity.Message;
import com.example.demo.Service.MessService;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@CrossOrigin
@RequestMapping("/pic")
public class MessageController {
    private MessService messService;

    // 根据用户id获取消息列表
    @ResponseBody
    @GetMapping("/getMess")
    public List<Message> getMess (@RequestParam("userid") String userid){
        List<Message> result = messService.getMess(userid);
        // TODO: 可以在这里对你们获得的消息进行处理，比如消息，url的拼接
        // String mess = "您有未完成的任务"+result[i].getTitle()+"即将截止！请及时填写！"
        return result;
    }

    // 传入任务号，给未完成的群发提醒消息
    @ResponseBody
    @GetMapping("/sendMess")
    public void getMess (@RequestParam("tid") int tid){
        messService.sendMess(tid);
    }

}
