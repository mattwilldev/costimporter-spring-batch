package solo.batch;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Component;
import solo.domain.IrMediaCostCpa;
import solo.domain.MediaCostCpa;
import solo.processor.MediaCostCpaProcessor;
import solo.repository.IrMediaCostCpaRepository;
import solo.repository.MediaCostCpaRepository;

import javax.xml.bind.ValidationException;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Matthew Williams
 */
@Component
public class CpaCostDataJobConfig {

    @Autowired
    private StepBuilderFactory steps;

    @Autowired
    private MediaCostCpaRepository mediaCostCpaRepository;

    @Autowired
    private IrMediaCostCpaRepository irMediaCostCpaRepository;

    @Bean
    public Step importCpaDataStep() {
        return steps.get("importCpaDataStep")
                .<MediaCostCpa, IrMediaCostCpa> chunk(10)
                .reader(cpaDataReader())
                .processor(cpaDataProcessor())
                .writer(cpaDataWriter())
                .faultTolerant()
//                .skipLimit(10)    // Skip Logic
//                .skip(Exception.class)
//                .noSkip(ResourceNotFoundException.class)
//                .retryLimit(3)  // Retry Logic
//                .retry(DeadlockLoserDataAccessException.class)
//                .noRollback(ValidationException.class)    // Rollback
                .build();
    }

    @Bean
    public ItemStreamReader<MediaCostCpa> cpaDataReader() {
        RepositoryItemReader<MediaCostCpa> reader = new RepositoryItemReader<>();
        reader.setRepository(mediaCostCpaRepository);
        reader.setMethodName("findAll");
        Map<String, Sort.Direction> sort = new HashMap<>();
        sort.put("id", Sort.Direction.ASC);
        reader.setSort(sort);
        return reader;
    }

    @Bean
    public ItemProcessor<MediaCostCpa, IrMediaCostCpa> cpaDataProcessor() {
        return new MediaCostCpaProcessor();
    }

    @Bean
    public ItemWriter<IrMediaCostCpa> cpaDataWriter() {
        RepositoryItemWriter<IrMediaCostCpa> writer = new RepositoryItemWriter<>();
        writer.setRepository(irMediaCostCpaRepository);
        writer.setMethodName("save");
        return writer;
    }
}
