package com.cs.analyzefood.config;

import com.cs.analyzefood.job.FoodLogJob;
import com.cs.analyzefood.job.StatisticsJob;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

@Configuration
public class TaskSetting {


    @Value("${scheduler.cron}")
    private String cron;

    @Bean(name = "quartzTaskA")
    public JobDetailFactoryBean jobDetailAFactoryBean() {
        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        factory.setJobClass(StatisticsJob.class);
        factory.setGroup("quartzTaskGroup");
        factory.setName("quartzTaskAJob");
        factory.setDurability(false);

        return factory;
    }

    @Bean(name = "quartzTaskATrigger")
    public CronTriggerFactoryBean cronTriggerAFactoryBean() {

        CronTriggerFactoryBean stFactory = new CronTriggerFactoryBean();

        stFactory.setJobDetail(jobDetailAFactoryBean().getObject());
        stFactory.setStartDelay(1000);
        stFactory.setName("quartzTaskATrigger");
        stFactory.setGroup("quartzTaskGroup");
        stFactory.setCronExpression(cron);

        return stFactory;
    }

    @Bean(name = "quartzTaskB")
    public JobDetailFactoryBean jobDetailBFactoryBean() {
        //生成JobDetail
        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        factory.setJobClass(FoodLogJob.class);
        factory.setGroup("quartzTaskGroup");
        factory.setName("quartzTaskBJob");
        factory.setDurability(false);

        return factory;
    }

    @Bean(name = "quartzTaskBTrigger")
    public CronTriggerFactoryBean cronTriggerBFactoryBean() {

        CronTriggerFactoryBean stFactory = new CronTriggerFactoryBean();

        stFactory.setJobDetail(jobDetailBFactoryBean().getObject());
        stFactory.setStartDelay(1000);
        stFactory.setName("quartzTaskBTrigger");
        stFactory.setGroup("quartzTaskGroup");
        stFactory.setCronExpression(cron);

        return stFactory;
    }


}
