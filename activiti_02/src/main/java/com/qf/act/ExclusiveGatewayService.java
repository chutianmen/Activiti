package com.qf.act;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;

import java.util.HashMap;
import java.util.Map;

public class ExclusiveGatewayService {

    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    //add
    public  void add(){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("qf6.bpmn")
                .name("wwProcess")
                .deploy();
        System.out.println("部署ID"+deploy.getId());
    }
    //start:学员登录系统，开始请假,首先启动请假流程
    public void start(){
        RuntimeService runtimeService = processEngine.getRuntimeService();
        //可以在启动的时候传递变量
        Map<String, Object> variables = new HashMap<>();
        variables.put("hello","请假");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("wwProcess",variables);
        System.out.println(processInstance.getProcessDefinitionId());
    }

    //完成学员任务
    public void completTask1(){
        //得到任务service
        TaskService taskService = processEngine.getTaskService();
        Map<String, Object> variables = new HashMap<>();
        variables.put("days",1);
        taskService.complete("202505",variables);
    }

    //班主任审批
    public void completTask2(){
        //得到任务service
        TaskService taskService = processEngine.getTaskService();
        String taskId = "205004";
        //得到请假添加
        Integer days = (Integer)taskService.getVariable(taskId, "days");
        System.out.println(days);
        Map<String, Object> variables = new HashMap<>();
        variables.put("days",days);//班主任设置请假天数，交给连线的执行表达式去判断。
        taskService.complete(taskId,variables);
    }

    //系主任审批
    public void completTask3(){
        //得到任务service
        TaskService taskService = processEngine.getTaskService();
        taskService.complete("12502");
    }

    public static void main(String[] args) {
        ExclusiveGatewayService stuService = new ExclusiveGatewayService();
//        stuService.add();
//        stuService.start();
//        stuService.completTask1();
//        stuService.completTask2();
//        stuService.completTask3();
//        stuService.teaCom();
    }

}
