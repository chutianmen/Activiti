package com.qf.act;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

public class WeiXiuService {
    //得到流程引擎对象
    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    //部署流程
    public void deploy(){//3
        //通过流程引擎对象得到部署的服务层对象
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //调用部署能力
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("qj2.bpmn")
                .name("维修审批流程")
                .deploy();
        System.out.println("部署ID："+deploy.getId());
    }
    //部署方案二
    public void deploy2(){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("qj2.zip");
//        InputStream fil = new FileInputStream("");
        ZipInputStream zipInputStream = new ZipInputStream(in);
        Deployment deploy = repositoryService.createDeployment()
                .addZipInputStream(zipInputStream)
                .name("请假审批")
                .deploy();
        System.out.println("部署ID："+deploy.getId());
    }

    //开始维修
    public void start(){//3
        //需要得到运行时的服务层对象
        RuntimeService runtimeService = processEngine.getRuntimeService();
        //根据key来启动流程
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcess_1");
        //得到流程信息
        System.out.println("实例ID:"+processInstance.getId());
        System.out.println("流程定义ID："+processInstance.getProcessDefinitionId());
        TaskService taskService = processEngine.getTaskService();
        String processInstanceId = processInstance.getProcessInstanceId();
        System.out.println("processInstanceId：" +processInstanceId);
        String taskId = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult().getId();
        System.out.println("TASK ID：" +taskId);
    }

    //查询任务 技术老师登录系统
    public void find(){
        //得到任务的服务层对象
        TaskService taskService = processEngine.getTaskService();
        List<Task> taskList = taskService.createTaskQuery()
                .taskAssignee("技术老师")//查询条件
                .list();
        for (Task task : taskList) {
            System.out.println("任务ID："+task.getId());
            System.out.println("任务名称："+task.getName());
            System.out.println("执行者："+task.getAssignee());
            System.out.println("流程定义："+task.getProcessDefinitionId());
        }
    }

    //完成任务
    public void complete(){//4
        //得到任务服务层的对象
        TaskService taskService = processEngine.getTaskService();
        taskService.complete("90008");
    }


    public static void main(String[] args) {
        WeiXiuService weiXiuDAO = new WeiXiuService();
        weiXiuDAO.deploy();
//        weiXiuDAO.deploy2();
        weiXiuDAO.start();
//        weiXiuDAO.find();
//        weiXiuDAO.complete();
    }

}
