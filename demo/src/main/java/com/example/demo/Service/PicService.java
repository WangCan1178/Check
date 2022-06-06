package com.example.demo.Service;

import com.example.demo.Dao.PicDao;
import com.example.demo.Entity.Isend;
import com.example.demo.Entity.Pic;
//import com.example.demo.Entity.Picture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PicService {
    private PicDao picDao;

    public PicDao getPicDao() {
        return picDao;
    }

    @Autowired
    public void setPicDao(PicDao picDao) {
        this.picDao = picDao;
    }

    public List<Isend> getPics(int taskid){
        return picDao.getPics(taskid);
    }

    public List<Isend> getPicUnPass(int taskid){
        return picDao.getPicUnPass(taskid);
    }

    public List<Isend> getPicUnFinish(int taskid){
        return picDao.getPicUnFinish(taskid);
    }

    public List<Isend> getPicPass(int taskid){
        return picDao.getPicPass(taskid);
    }


    public int alterPic(int taskid,String userid,String photo,String result){
        return picDao.alterPic(taskid, userid, photo,result);
    }

    public Pic getPicDetail(int picid){
        return picDao.getPicDetail(picid);
    }

    public int alterR(int taskid,String result){
        return picDao.alterR(taskid,result);
    }

    public int getUserTask(String userid){
        return picDao.getUserTask(userid);
    }

}
