package com.cs.analyzefood.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;


@Configuration
public class SchedulerConfig {

    @Autowired
    @Qualifier("quartzTaskATrigger")
    private CronTriggerFactoryBean quartzTaskATrigger;

    @Autowired
    @Qualifier("quartzTaskBTrigger")
    private CronTriggerFactoryBean quartzTaskBTrigger;


    //Quartz中的job自动注入spring容器托管的对象
    @Bean
    public AutowiringSpringBeanJobFactory autoWiringSpringBeanJobFactory() {
        return new AutowiringSpringBeanJobFactory();
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
        scheduler.setJobFactory(autoWiringSpringBeanJobFactory());//配置Spring注入的Job类
        //设置CronTriggerFactoryBean，设定任务Trigger
        scheduler.setTriggers(
                quartzTaskATrigger.getObject(),
                quartzTaskBTrigger.getObject()
        );
        return scheduler;
    }

}
