package com.example.demo.Service;

import com.example.demo.Dao.TaskDao;
import com.example.demo.Entity.Isend;
import com.example.demo.Entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import java.util.Date;
//import java.sql.Date;
import java.util.List;

@Service
public class TaskService {
    private TaskDao taskDao;

    public TaskDao getTaskDao() {
        return taskDao;
    }

    @Autowired
    public void setTaskDao(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public List<Isend> getTask(int id, String userid){
        return taskDao.getTask(id,userid);
    }
    public List<Isend> getTask3(int id, String userid){
        return taskDao.getTask(id,userid);
    }

    public List<Task> getTask2(int id){
        return taskDao.getTask2(id);
    }

    public int insertTask(String title, String photo,String des, String recognition, int groupid, Date publishtime){
        return taskDao.insertTask(title, photo,des, recognition, groupid, publishtime);
    }

//    public int insertTaskJKM(String title, int groupid,String description, Date publishtime,Date endtime){
//
//    }
    public int insertTaskJKM(String title, int groupid,String description, Date publishtime,Date endtime){
        Task task =  taskDao.insertTaskJKM(title,groupid,description,publishtime,endtime);
        System.out.println(task.getTaskid());
        return 1;
    }

    public int endTask(Date endtime,int taskid){
        return taskDao.endTask(endtime, taskid);
    }

    public int deleteTask(int taskid){
        return taskDao.deleteTask(taskid);
    }

    public Task getTaskInfo(int id){
        return taskDao.getTaskInfo(id);
    }

    public String getTaskTitle(int id){
        return taskDao.getTaskTitle(id);
    }
}
