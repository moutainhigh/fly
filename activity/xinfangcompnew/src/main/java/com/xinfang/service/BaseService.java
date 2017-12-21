package com.xinfang.service;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseService {
	//注入为我们自动配置好的服务
		@Autowired
		private RuntimeService runtimeService;//它负责启动一个流程定义的新实例，也可以用来获取和保存流程变量，也能查询流程实例和执行 在Activiti中，每当一个流程定义被启动一次之后，都会生成一个相应的流程对象实例。RuntimeService提供了启动流程、查询流程实例、设置获取流程实例变量等功能。此外它还提供了对流程部署，流程定义和流程实例的存取服务
		@Autowired
		private TaskService taskService;//查询分配给用户或组的任务 在Activiti中业务流程定义中的每一个执行节点被称为一个Task，对流程中的数据存取，状态变更等操作均需要在Task中完成。TaskService提供了对用户Task 和Form相关的操作。它提供了运行时任务查询、领取、完成、删除以及变量设置等功能
		@Autowired
		RepositoryService repositoryService;//提供了管理和控制发布 包和流程定义的操作 Activiti中每一个不同版本的业务流程的定义都需要使用一些定义文件，部署文件和支持数据(例如BPMN2.0 XML文件，表单定义文件，流程定义图像文件等)，这些文件都存储在Activiti内建的Repository中。RepositoryService提供了对 repository的存取服务。
		@Autowired
		IdentityService identityService;//它可以管理（创建，更新，删除，查询...）群组和用户 Activiti中内置了用户以及组管理的功能，必须使用这些用户和组的信息才能获取到相应的Task。IdentityService提供了对Activiti 系统中的用户和组的管理功能
		@Autowired
		FormService formService;//这个服务提供了启动表单和任务表单两个概念。 启动表单会在流程实例启动之前展示给 用户， 任务表单会在用户完成任务时展示
		@Autowired
		HistoryService historyService;//用于获取正在运行或已经完成的流程实例的信息，与RuntimeService中获取的流程信息不同，历史信息包含已经持久化存储的永久信息，并已经被针对查询优化
		@Autowired
		ManagementService managementService;//提供了对Activiti流程引擎的管理和维护功能，这些功能不在工作流驱动的应用程序中使用，主要用于Activiti系统的日常维护。 
		
		@Autowired
		UserService userService;//
		
		
		
		

}
