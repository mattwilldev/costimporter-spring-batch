package solo.processor;

import org.springframework.batch.item.ItemProcessor;
import solo.domain.IrMediaCostCpa;
import solo.domain.MediaCostCpa;

/**
 * @author Matthew Williams
 */
public class MediaCostCpaProcessor implements ItemProcessor<MediaCostCpa, IrMediaCostCpa> {

    @Override
    public IrMediaCostCpa process(MediaCostCpa mediaCostCpa) throws Exception {

        IrMediaCostCpa irMediaCostCpa = new IrMediaCostCpa();

        irMediaCostCpa.setRecordId(mediaCostCpa.getRecordId());
        irMediaCostCpa.setActionId(mediaCostCpa.getActionId());
        irMediaCostCpa.setOid(mediaCostCpa.getOid());
        irMediaCostCpa.setAdId(mediaCostCpa.getAdId());
        irMediaCostCpa.setPublisherId(mediaCostCpa.getPublisherId());
        irMediaCostCpa.setPublisherName(mediaCostCpa.getPublisherName());
        irMediaCostCpa.setCommission(mediaCostCpa.getCommission());
        irMediaCostCpa.setClientCost(mediaCostCpa.getClientCost());
        irMediaCostCpa.setSaleAmount(mediaCostCpa.getSaleAmount());

        return irMediaCostCpa;
    }
}
