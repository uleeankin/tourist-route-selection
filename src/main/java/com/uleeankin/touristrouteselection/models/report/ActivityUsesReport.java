package com.uleeankin.touristroutebeta.models.report;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ActivityUsesReport {

    private String activityName;

    private Double usesPercentage;

}
