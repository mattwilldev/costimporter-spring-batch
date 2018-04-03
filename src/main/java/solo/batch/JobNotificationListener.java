package solo.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import solo.repository.IrMediaCostCpaRepository;

/**
 * @author Matthew Williams
 */
@Slf4j
@Component
public class JobNotificationListener extends JobExecutionListenerSupport {

    @Autowired
    private IrMediaCostCpaRepository irMediaCostCpaRepository;

    @Override
    public void beforeJob(JobExecution jobExecution) {

    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (BatchStatus.COMPLETED.equals(jobExecution.getStatus())) {

            log.info("!!! JOB FINISHED! Time to verify the results");
            log.info(String.format("Synced %d records.", irMediaCostCpaRepository.findAll().size()));
        }
    }
}
