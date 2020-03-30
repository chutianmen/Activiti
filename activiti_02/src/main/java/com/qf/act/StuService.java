package com.qf.act;

import com.qf.dto.StudentDTO;
import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;

import java.util.HashMap;
import java.util.Map;

public class StuService {

    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    //add
    public  void deploy(){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("qf4.bpmn")
                .name("stuProcess")
                .deploy();
        System.out.println("部署ID:"+deploy.getId());
    }

    //start:学员登录系统，开始请假,首先启动请假流程
    public void start(){
        RuntimeService runtimeService = processEngine.getRuntimeService();
        //可以在启动的时候传递变量
        Map<String, Object> variables = new HashMap<>();
        variables.put("hello","请假");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("stuProcess",variables);
        System.out.println(processInstance.getProcessDefinitionId());
    }

    //完成学员的请假任务，添加请假的条件，天数，2，原因：
    public void stuComplete(){
        TaskService taskService = processEngine.getTaskService();
        String taskId = "127505";
        //获取启动的时候传递的参数
        String hello = (String) taskService.getVariable(taskId, "hello");
        System.out.println("hello:" + hello);
        //请你提交请假原因
        taskService.setVariable(taskId,"days",3);
        taskService.setVariable(taskId,"reson","家长逼着回家相亲");
        //传员工对象
        StudentDTO dto = new StudentDTO(1,"张三");
        taskService.setVariable(taskId,"stu",dto);
        taskService.complete(taskId);
    }

    public void stuComplete2(){
        TaskService taskService = processEngine.getTaskService();
        String taskId = "120005";
        Map<String, Object> variables = new HashMap<>();
        variables.put("days",3);
        variables.put("reson","家长逼着回家相亲");
        StudentDTO dto = new StudentDTO(2,"张三");
        variables.put("stu",dto);
        taskService.complete(taskId,variables);
    }

    public void teaCom(){
        TaskService taskService = processEngine.getTaskService();
        String taskId = "122507";
        //得到请假原因
        Integer days = (Integer) taskService.getVariable(taskId, "days");
        System.out.println("请假天数:"+days);
        String reson = (String)taskService.getVariable(taskId, "reson");
        System.out.println("请假原因:"+reson);
        StudentDTO stu = (StudentDTO)taskService.getVariable(taskId, "stu");
        //学员信息
        System.out.println("学生ID:"+stu.getStuID()+"\t 学生姓名："+stu.getStuName());
        taskService.complete(taskId);
    }

    public static void main(String[] args) {
        StuService stuService = new StuService();
//        stuService.deploy();
//        stuService.start();
        stuService.stuComplete();
//        stuService.stuComplete2();
//        stuService.teaCom();
    }

}
