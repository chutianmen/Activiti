package com.qf.act;

import org.activiti.engine.*;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;

import java.text.ParseException;
import java.util.List;

public class CrudService {
    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    //add
    public  void add(){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("qf3.bpmn")
                .name("请假")
                .deploy();
        System.out.println("部署ID："+deploy.getId());
    }

    public void del(){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        repositoryService.deleteDeployment("100001",true);

    }

    public void findDef(){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        List<ProcessDefinition> definitionList = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey("qjProcess")//按照key查询
                .list();
        for (ProcessDefinition processDefinition : definitionList) {
            System.out.println("流程信息:"+processDefinition.getId()+"\t"+processDefinition.getDeploymentId()+"\t"+processDefinition.getVersion());
        }
    }
    public void findDefId(){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId("qjProcess:3:105004")
                .singleResult();
        System.out.println("流程信息:"+processDefinition.getId()+"\t"+processDefinition.getDeploymentId()+"\t"+processDefinition.getVersion());
    }

    //start
    public void start(){
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("qjProcess");
        System.out.println(processInstance.getProcessDefinitionId());
    }

    public void findHis(){
        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()
                .list();
        for (HistoricTaskInstance ht : list) {
            System.out.println(ht.getId()+"\t"+ht.getName()+"\t"+ht.getStartTime()+"\t"+ht.getEndTime());
        }
    }
    //按照时间查询
    public void findHisTime() throws ParseException {
        String start = "2018-09-01";
        String end = "2218-09-30";
        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()
                .taskCreatedAfter(DateUtils.stringToDate(start))
                .taskCreatedBefore(DateUtils.stringToDate(end))
                .list();
        for (HistoricTaskInstance ht : list) {
            System.out.println(ht.getId()+"\t"+ht.getName()+"\t"+ht.getStartTime()+"\t"+ht.getEndTime());
        }
    }

    public void findExe(){
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId("107501")
                .singleResult();
        if(processInstance!=null){
            System.out.println("流程在运行中");
        }else{
            System.out.println("流程没有运行");
        }
    }

    public static void main(String[] args) {
        CrudService crudService = new CrudService();
//        crudService.add();
//        crudService.start();
//        crudService.del();
//        crudService.findDef();
//        crudService.findDefId();
//        crudService.findHis();
//        try {
//            crudService.findHisTime();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        crudService.findExe();
    }

}
