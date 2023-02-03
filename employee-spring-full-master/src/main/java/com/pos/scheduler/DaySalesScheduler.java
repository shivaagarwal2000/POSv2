package com.pos.scheduler;

import com.pos.dto.DaySalesDto;
import com.pos.service.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@EnableScheduling
@Service
public class DaySalesScheduler {
    @Autowired
    private DaySalesDto daySalesDto;
    @Scheduled(cron = "0 0 8 * * *") //TODO: move it to the properties file
    public void getPreviousDaySalesEntry() throws ApiException {
        System.out.println("scheduled");
        daySalesDto.add();
    }
}
