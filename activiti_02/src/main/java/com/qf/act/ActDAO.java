package com.qf.act;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;

public class ActDAO {
    //生成工作流需要的表
    public void initTable(){
        //流程引擎的配置对象，这种方式不需要配置文件
        ProcessEngineConfiguration engineConfiguration = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
        engineConfiguration.setJdbcDriver("org.gjt.mm.mysql.Driver");
        engineConfiguration.setJdbcUrl("jdbc:mysql:///qf?characterEncoding=utf-8");
        engineConfiguration.setJdbcUsername("root");
        engineConfiguration.setJdbcPassword("");
        //设置表的更新
        engineConfiguration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        //上面的代码就是加载配置,然后需要得到工作流的核心对象
        ProcessEngine processEngine = engineConfiguration.buildProcessEngine();
        //后续将使用ProcessEngine来操作工作流的表
    }
    public void initTable2(){
        //加载配置
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
        //得到引擎对象
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
    }

    public static void main(String[] args) {
        ActDAO actDAO = new ActDAO();
//        actDAO.initTable();
        actDAO.initTable2();
    }

}
