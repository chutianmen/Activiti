package com.qf.act;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;

public class ParallelGatewayService {
    //得到流程引擎对象
    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    //部署流程
    public void deploy(){//3
        //通过流程引擎对象得到部署的服务层对象
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //调用部署能力
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("qf7.bpmn")
                .name("员工请假流程")
                .deploy();
        System.out.println("部署ID"+deploy.getId());
    }

    //
    public void  start(){
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance ygProcess = runtimeService.startProcessInstanceByKey("allProcess");
        System.out.println(ygProcess.getProcessDefinitionId());
    }
    //学生完成请假任务
    public void stuCom(){
        TaskService taskService = processEngine.getTaskService();
        String taskId="212504";//60003
        taskService.setVariable(taskId,"days",200);
        taskService.setVariable(taskId,"reason","回家...");
        taskService.complete(taskId);
    }

    //班长审批
    public void banzhangCom(){
        TaskService taskService = processEngine.getTaskService();
        String taskId="215007";//
        Integer days = (Integer) taskService.getVariable(taskId, "days");
        String reason = (String)taskService.getVariable(taskId, "reason");
        System.out.println("请假天数"+days);
        System.out.println("请假原因"+reason);
        //这个请假的原因需要继续传递。需要传递给主管完成任务后的执行表达式。
//        taskService.setVariable(taskId,"days",days);//必须传
        taskService.complete(taskId);
    }

    //老师审批
    public void teacherCom(){
        TaskService taskService = processEngine.getTaskService();
        String taskId="215010";//
        Integer days = (Integer) taskService.getVariable(taskId, "days");
        String reason = (String)taskService.getVariable(taskId, "reason");
        System.out.println("请假天数"+days);
        System.out.println("请假原因"+reason);
        //这个请假的原因需要继续传递。需要传递给主管完成任务后的执行表达式。
//        taskService.setVariable(taskId,"days",days);//必须传
        taskService.complete(taskId);
    }

    //校长审批
    public void xiaozhangCom(){
        TaskService taskService = processEngine.getTaskService();
        String taskId="220003";//
        Integer days = (Integer) taskService.getVariable(taskId, "days");
        System.out.println("请假天数"+days);
        taskService.complete(taskId);
    }

    public static void main(String[] args) {
        ParallelGatewayService yuanGongService = new ParallelGatewayService();
        yuanGongService.deploy();
//        yuanGongService.start();
//        yuanGongService.stuCom();
//        yuanGongService.banzhangCom();
//        yuanGongService.teacherCom();
//        yuanGongService.xiaozhangCom();
    }

}
