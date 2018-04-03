package solo.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author Matthew Williams
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "ir_media_cost_cpa")
public class IrMediaCostCpa {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "record_id")
    private String recordId;

    @Column(name = "oid")
    private String oid;

    @Column(name = "action_id")
    private String actionId;

    @Column(name = "ad_id")
    private String adId;

    @Column(name = "publisher_id")
    private String publisherId;

    @Column(name = "publisher_name")
    private String publisherName;

    @Column(name = "commission")
    private BigDecimal commission;

    @Column(name = "client_cost")
    private BigDecimal clientCost;

    @Column(name = "sale_amount")
    private BigDecimal saleAmount;
}

