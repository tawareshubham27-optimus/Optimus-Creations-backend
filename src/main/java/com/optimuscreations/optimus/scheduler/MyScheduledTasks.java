package com.optimuscreations.optimus.scheduler;

import com.optimuscreations.optimus.service.ProductService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MyScheduledTasks {

    private final ProductService productService;

    MyScheduledTasks(ProductService productService) {
        this.productService = productService;
    }
    
    // Cron expression: second, minute, hour, day of month, month, day of week
    @Scheduled(cron = "0 */15 * * * *")  // Every day at 9:00 AM
    public void dailyTask() {
        System.out.println("Product inventory check executed at"+System.currentTimeMillis()+ "\n"+ productService.getAllProducts());
    }
}
