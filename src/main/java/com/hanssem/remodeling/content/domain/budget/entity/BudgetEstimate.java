package com.hanssem.remodeling.content.domain.budget.entity;

import com.hanssem.remodeling.content.domain.SystemEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Table(name = "tb_budget_estimate_m")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@DynamicInsert
public class BudgetEstimate extends SystemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BUDGET_ESTIMATE_ID")
    private Long id;

    @Column(name = "ONLINE_CUSTOMER_NO")
    private Long userId;

    @Column(name = "PNTP_VALUE")
    private int ptypValue;

    @Column(name = "ROOM_COUNT")
    private int roomCount;

    @Column(name = "BATH_COUNT")
    private int bathCount;

    @Column(name = "TOTAL_ESTIMATE_AMOUNT")
    private int totalEstimateAmount;

    @Column(name = "CONSULT_REQUEST_YN")
    private String consultYn;

    @Builder.Default
    @OneToMany(mappedBy = "budgetEstimate", cascade = CascadeType.ALL)
    private List<BudgetEstimateInfo> budgetEstimateInfoList = new ArrayList<>();

    // 상담신청여부 수정 메서드
    public void changeConsultYn(String consultYn) {
        this.consultYn = consultYn;
    }
}
