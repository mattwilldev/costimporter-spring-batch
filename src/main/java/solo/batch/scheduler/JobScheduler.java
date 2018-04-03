package solo.batch.scheduler;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Matthew Williams
 */
@Component
public class JobScheduler {

    @Autowired
    @Qualifier("importCostDataJob")
    private Job importJob;

    @Autowired
    private JobLauncher jobLauncher;


    @Scheduled(cron = "0 * * ? * *")    // Every minute
    public void runJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        JobParametersBuilder builder = new JobParametersBuilder();
        builder.addDate("date", new Date());
        jobLauncher.run(importJob, builder.toJobParameters());
    }

}
