package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import sun.nio.ch.ThreadPool;

@SpringBootApplication
@EnableScheduling
public class SimulationServiceApplication {
    public static void main(String[] args){
        SpringApplication.run(SimulationServiceApplication.class, args);
    }

    @Bean
    public AsyncTaskExecutor taskExecutor(){
        //it is both a executor and a scheduler because when the task is scheduled, it will also be exectued
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(10);
        return scheduler;
    }
}
