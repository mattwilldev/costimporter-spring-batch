package solo.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Matthew Williams
 */
@Configuration
@EnableBatchProcessing
@EnableScheduling
public class BatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private JobNotificationListener listener;

    @Autowired
    private Step importCpaDataStep;

    @Bean
    public Job importCostDataJob() {
        return jobBuilderFactory.get("importCostDataJob")
                .listener(listener)
                .incrementer(new RunIdIncrementer())
                .start(splitFlow())
                .build()
                .build();
    }

    @Bean
    public Flow splitFlow() {
        return new FlowBuilder<SimpleFlow>("importCostDataJobSplitFlow")
                .split(new SimpleAsyncTaskExecutor("cost_importer_batch_import"))
                .add(importCpaDataFlow())   // Parallel execution
                .build();
    }

    @Bean
    public Flow importCpaDataFlow() {
        return new FlowBuilder<SimpleFlow>("importCpaDataFlow")
                .start(importCpaDataStep)
                .build();
    }
}
