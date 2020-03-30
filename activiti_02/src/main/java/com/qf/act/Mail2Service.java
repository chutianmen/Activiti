package com.qf.act;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;

import java.util.HashMap;
import java.util.Map;

public class Mail2Service {

    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    //add
    public  void add(){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("qf12.bpmn")
                .name("mail2Process")
                .deploy();

        System.out.println("部署ID"+deploy.getId());
    }
    //start:学员登录系统，开始请假,首先启动请假流程
    public void start(){
        RuntimeService runtimeService = processEngine.getRuntimeService();
        //可以在启动的时候传递变量
        Map<String, Object> variables = new HashMap<>();
        variables.put("hello","请假");
        variables.put("content","请领导审批！");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("mail2Process",variables);
        System.out.println(processInstance.getProcessDefinitionId());
    }

    //完成请假任务
    public void completTask1(){
        //得到任务service
        TaskService taskService = processEngine.getTaskService();
        Map<String, Object> variables = new HashMap<>();
        variables.put("days",1);
        taskService.complete("265010",variables);
    }

    //完成审批任务
    public void completTask2(){
        //得到任务service
        TaskService taskService = processEngine.getTaskService();
        taskService.complete("267506");
    }


    public static void main(String[] args) {
        Mail2Service stuService = new Mail2Service();
//        stuService.add();
//        stuService.start();
//        stuService.completTask1();
//        stuService.completTask2();
    }

}
