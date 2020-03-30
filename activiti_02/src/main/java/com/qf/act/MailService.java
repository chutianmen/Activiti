package com.qf.act;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;

import java.util.HashMap;
import java.util.Map;

public class MailService {

    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    //add
    public  void add(){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("qf11.bpmn")
                .name("mailProcess")
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
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("mailProcess",variables);
        System.out.println(processInstance.getProcessDefinitionId());
    }

    //完成学员任务
    public void completTask1(){
        //得到任务service
        TaskService taskService = processEngine.getTaskService();
        Map<String, Object> variables = new HashMap<>();
        variables.put("days",1);
        taskService.complete("20003",variables);
    }


    public static void main(String[] args) {
        MailService stuService = new MailService();
        stuService.add();
        stuService.start();
//        stuService.completTask1();
    }

}
