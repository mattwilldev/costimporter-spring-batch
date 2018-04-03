package solo.processor;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.data.domain.Sort;
import solo.domain.MediaCostCpa;
import solo.repository.MediaCostCpaRepository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Matthew Williams
 */
public class CpaItemStreamReader extends RepositoryItemReader<MediaCostCpa> {

    private static final String READ_CURSOR = "read.cursor";
    private Long readCursor = 0L;

    private MediaCostCpaRepository repository;

    public CpaItemStreamReader() {
        super();
        setMethodName("findByIdGreaterThan");
        Map<String, Sort.Direction> sort = new HashMap<>();
        sort.put("id", Sort.Direction.ASC);
        setSort(sort);
    }

    public void setRepository(MediaCostCpaRepository repository) {
        super.setRepository(repository);
        this.repository = repository;
    }

    @Override
    protected List<MediaCostCpa> doPageRead() throws Exception {
        setArguments(Collections.singletonList(readCursor));
        return super.doPageRead();
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        super.open(executionContext);
        if (executionContext.containsKey(getExecutionContextKey(READ_CURSOR))) {
            readCursor = (long) executionContext.getInt(getExecutionContextKey(READ_CURSOR));
        }
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        super.update(executionContext);
        if (isSaveState()) {
            List<MediaCostCpa> mediaCostCpa = repository.findAll(
                    new Sort(new Sort.Order(Sort.Direction.DESC,"id")));

            Long lastestId = mediaCostCpa.isEmpty() ? 0L : mediaCostCpa.get(0).getId();
            executionContext.putInt(getExecutionContextKey(READ_CURSOR), lastestId.intValue());
        }
    }
}
