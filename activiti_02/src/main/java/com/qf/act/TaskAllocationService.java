package com.qf.act;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;

import java.util.HashMap;
import java.util.Map;

public class TaskAllocationService {
    //得到流程引擎对象
    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    //部署流程
    public void deploy(){//3
        //通过流程引擎对象得到部署的服务层对象
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //调用部署能力
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("qf8.bpmn")
                .name("员工请假流程")
                .deploy();
        System.out.println("部署ID"+deploy.getId());
    }

    //员工登录后，就可以来启动流程，申请请假。
    public void  start(){
        RuntimeService runtimeService = processEngine.getRuntimeService();
        //从登录的信息中，获取用户名。传递给请假任务的执行者。
        Map<String, Object> variables = new HashMap<>();
        variables.put("username","德鹏");
        ProcessInstance ygProcess = runtimeService.startProcessInstanceByKey("wlProcess",variables);
        System.out.println(ygProcess.getProcessDefinitionId());
    }

    public void  start2(){
        RuntimeService runtimeService = processEngine.getRuntimeService();
        //从登录的信息中，获取用户名。传递给请假任务的执行者。
        Map<String, Object> variables = new HashMap<>();
        variables.put("username","德鹏2");
        ProcessInstance ygProcess = runtimeService.startProcessInstanceById("wlProcess:1:107504",variables);
        System.out.println(ygProcess.getProcessDefinitionId());
    }
    //员工完成请假任务
    public void ygCom(){
        TaskService taskService = processEngine.getTaskService();
        String taskId="227505";//60003
        taskService.setVariable(taskId,"days",3);
        taskService.setVariable(taskId,"reason","也要回家...");
        //提交给谁做审批。根据员工查询自己的主管是谁。
        String leader = "兴宇";
        taskService.setVariable(taskId,"leader",leader);
        taskService.complete(taskId);
    }
    //主管审批
    public void zgCom(){
        TaskService taskService = processEngine.getTaskService();
        String taskId="230005";//
        Integer days = (Integer) taskService.getVariable(taskId, "days");
        String reason = (String)taskService.getVariable(taskId, "reason");
        System.out.println("请假天数"+days);
        System.out.println("请假原因"+reason);
        //这个请假的原因需要继续传递。需要传递给主管完成任务后的执行表达式。
//        taskService.setVariable(taskId,"days",days);//必须传
        taskService.complete(taskId);
    }

    //CEO审批
//    public void ceoCom(){
//        TaskService taskService = processEngine.getTaskService();
//        String taskId="60005";//
//        Integer days = (Integer) taskService.getVariable(taskId, "days");
//        System.out.println("请假天数"+days);
//        taskService.complete(taskId);
//    }

    public static void main(String[] args) {
        TaskAllocationService yuanGongService = new TaskAllocationService();
//        yuanGongService.deploy();
//        yuanGongService.start();
//        yuanGongService.ygCom();
        yuanGongService.zgCom();
    }

}
