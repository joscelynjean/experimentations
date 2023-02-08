package ca.montreal.sai.camunda.monitoring;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.event.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;


/**
 * Monitoring class for Camunda, based on groovy scripts from Camunda-Monitoring github project.
 * @see https://github.com/StephenOTT/Camunda-Monitoring
 * 
 * @author Joscelyn Jean
 */
@Configuration
@EnableScheduling
public class CamundaMonitoringBeansConfig {

	@Autowired
	MeterRegistry registry;
	
	@Autowired
	ProcessEngine processEngine;

	List<Tag> commonTags;

	String activeIncidentsMetricName = "camunda_active_incidents";
	AtomicLong activeIncidents;

	String activeUserTasksMetricName = "camunda_active_user_tasks";
	AtomicLong activeUserTasks;

	String activeMessageEventSubscriptionsMetricName = "camunda_active_message_event_subscriptions";
	AtomicLong activeMessageEventSubscriptions;

	String activeSignalEventSubscriptionsMetricName = "camunda_active_signal_event_subscriptions";
	AtomicLong activeSignalEventSubscriptions;

	String activeCompensateEventSubscriptionsMetricName = "camunda_active_compensate_event_subscriptions";
	AtomicLong activeCompensateEventSubscriptions;

	String activeConditionalEventSubscriptionsMetricName = "camunda_active_conditional_event_subscriptions";
	AtomicLong activeConditionalEventSubscriptions;

	String executableJobsMetricName = "camunda_executable_jobs";
	AtomicLong executableJobs;

	String executableTimerJobsMetricName = "camunda_executable_timer_jobs";
	AtomicLong executableTimerJobs;

	String timerJobsMetricName = "camunda_timer_jobs";
	AtomicLong timerJobs;

	String messageJobsMetricName = "camunda_message_jobs";
	AtomicLong messageJobs;

	String userCountMetricName = "camunda_user_count";
	AtomicLong userCount;

	String tenantCountMetricName = "camunda_tenant_count";
	AtomicLong tenantCount;

	String activeProcessInstancesMetricName = "camunda_active_process_instances";
	AtomicLong activeProcessInstances;

	String completedProcessInstancesMetricName = "camunda_completed_process_instances";
	AtomicLong completedProcessInstances;

	String activeProcessDefinitionsMetricName = "camunda_active_process_definitions";
	AtomicLong activeProcessDefinitions;

	String deploymentsMetricName = "camunda_deployments";
	AtomicLong deployments;

	String activeExternalTasksMetricName = "camunda_active_external_tasks";
	AtomicLong activeExternalTasks;

	String activeLockedExternalTasksMetricName = "camunda_active_locked_external_tasks";
	AtomicLong activeLockedExternalTasks;

	String activeNotLockedExternalTasksMetricName = "camunda_active_not_locked_external_tasks";
	AtomicLong activeNotLockedExternalTasks;


	@PostConstruct
	void setup(){
		this.commonTags = Arrays.asList(Tag.of("engineName", processEngine.getName()));
		activeIncidents = registry.gauge(activeIncidentsMetricName, commonTags, new AtomicLong(0));

		activeUserTasks = registry.gauge(activeUserTasksMetricName, commonTags, new AtomicLong(0));

		activeMessageEventSubscriptions = registry.gauge(activeMessageEventSubscriptionsMetricName, commonTags, new AtomicLong(0));

		activeSignalEventSubscriptions = registry.gauge(activeSignalEventSubscriptionsMetricName, commonTags, new AtomicLong(0));

		activeCompensateEventSubscriptions = registry.gauge(activeCompensateEventSubscriptionsMetricName, commonTags, new AtomicLong(0));

		activeConditionalEventSubscriptions = registry.gauge(activeConditionalEventSubscriptionsMetricName, commonTags, new AtomicLong(0));

		executableJobs = registry.gauge(executableJobsMetricName, commonTags, new AtomicLong(0));

		executableTimerJobs = registry.gauge(executableTimerJobsMetricName, commonTags, new AtomicLong(0));

		timerJobs = registry.gauge(timerJobsMetricName, commonTags, new AtomicLong(0));

		messageJobs = registry.gauge(messageJobsMetricName, commonTags, new AtomicLong(0));

		userCount = registry.gauge(userCountMetricName, commonTags, new AtomicLong(0));

		tenantCount = registry.gauge(tenantCountMetricName, commonTags, new AtomicLong(0));

		activeProcessInstances = registry.gauge(activeProcessInstancesMetricName, commonTags, new AtomicLong(0));

		completedProcessInstances = registry.gauge(completedProcessInstancesMetricName, commonTags, new AtomicLong(0));

		activeProcessDefinitions = registry.gauge(activeProcessDefinitionsMetricName, commonTags, new AtomicLong(0));

		deployments = registry.gauge(deploymentsMetricName, commonTags, new AtomicLong(0));

		activeExternalTasks = registry.gauge(activeExternalTasksMetricName, commonTags, new AtomicLong(0));

		activeLockedExternalTasks = registry.gauge(activeLockedExternalTasksMetricName, commonTags, new AtomicLong(0));

		activeNotLockedExternalTasks = registry.gauge(activeNotLockedExternalTasksMetricName, commonTags, new AtomicLong(0));
	}

	@Scheduled(fixedRate = 60000L)
	void getActiveIncidents(){
		activeIncidents.set(processEngine.getRuntimeService().createIncidentQuery().count());
	}

	@Scheduled(fixedRate = 60000L)
	void getActiveUserTasks(){
		activeUserTasks.set(processEngine.getTaskService().createTaskQuery().active().count());
	}

	@Scheduled(fixedRate = 60000L)
	void getActiveMessageEventSubscriptions(){
		activeMessageEventSubscriptions.set(processEngine.getRuntimeService().createEventSubscriptionQuery().eventType(EventType.MESSAGE.name()).count());
	}

	@Scheduled(fixedRate = 60000L)
	void getActiveSignalEventSubscriptions(){
		activeSignalEventSubscriptions.set(processEngine.getRuntimeService().createEventSubscriptionQuery().eventType(EventType.SIGNAL.name()).count());
	}

	@Scheduled(fixedRate = 60000L)
	void getActiveCompensateEventSubscriptions(){
		activeCompensateEventSubscriptions.set(processEngine.getRuntimeService().createEventSubscriptionQuery().eventType(EventType.COMPENSATE.name()).count());
	}

	@Scheduled(fixedRate = 60000L)
	void getActiveConditionalEventSubscriptions(){
		activeConditionalEventSubscriptions.set(processEngine.getRuntimeService().createEventSubscriptionQuery().eventType(EventType.CONDITONAL.name()).count());
	}

	@Scheduled(fixedRate = 60000L)
	void getExecutableJobs(){
		executableJobs.set(processEngine.getManagementService().createJobQuery().executable().count());
	}

	@Scheduled(fixedRate = 60000L)
	void getExecutableTimerJobs(){
		executableTimerJobs.set(processEngine.getManagementService().createJobQuery().executable().timers().count());
	}

	@Scheduled(fixedRate = 60000L)
	void getTimerJobs(){
		timerJobs.set(processEngine.getManagementService().createJobQuery().timers().count());
	}

	@Scheduled(fixedRate = 60000L)
	void getMessageJobs(){
		messageJobs.set(processEngine.getManagementService().createJobQuery().messages().count());
	}

	@Scheduled(fixedRate = 36000000L) // Once an hour
	void getUserCount(){
		userCount.set(processEngine.getIdentityService().createUserQuery().count());
	}

	@Scheduled(fixedRate = 36000000L) // Once an hour
	void getTenantCount(){
		tenantCount.set(processEngine.getIdentityService().createTenantQuery().count());
	}

	@Scheduled(fixedRate = 60000L)
	void getActiveProcessInstances(){
		activeProcessInstances.set(processEngine.getRuntimeService().createProcessInstanceQuery().active().count());
	}

	@Scheduled(fixedRate = 36000000L) // Once an hour
	void getCompletedProcessInstances(){
		completedProcessInstances.set(processEngine.getHistoryService().createHistoricProcessInstanceQuery().completed().count());
	}

	@Scheduled(fixedRate = 60000L)
	void getActiveProcessDefinitions(){
		activeProcessDefinitions.set(processEngine.getRepositoryService().createProcessDefinitionQuery().active().count());
	}

	@Scheduled(fixedRate = 60000L)
	void getDeployments(){
		deployments.set(processEngine.getRepositoryService().createDeploymentQuery().count());
	}

	@Scheduled(fixedRate = 60000L)
	void getActiveExternalTasks(){
		activeExternalTasks.set(processEngine.getExternalTaskService().createExternalTaskQuery().active().count());
	}

	@Scheduled(fixedRate = 60000L)
	void getActiveLockedExternalTasks(){
		activeLockedExternalTasks.set(processEngine.getExternalTaskService().createExternalTaskQuery().active().locked().count());
	}

	@Scheduled(fixedRate = 60000L)
	void getActiveNotLockedExternalTasks(){
		activeNotLockedExternalTasks.set(processEngine.getExternalTaskService().createExternalTaskQuery().active().notLocked().count());
	}
	
	
	
}
