package com.example.demo.Service;

import com.example.demo.Dao.PicDao;
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

    public int insertPic(String url,String result, int taskid, String userid){
        return insertPic(url,result, taskid, userid);
    }

    public List<Pic> getPics(int taskid){
        return picDao.getPics(taskid);
    }

    public int alterResult(int picid,String result){
        return picDao.alterResult(picid,result);
    }

    public int alterPic(int picid,String url,String result){
        return picDao.alterPic(picid, url,result);
    }

    public int deletePic(int picid){
        return picDao.deletePic(picid);
    }
}
