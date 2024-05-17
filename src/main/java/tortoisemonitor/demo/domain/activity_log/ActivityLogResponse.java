package tortoisemonitor.demo.domain.activity_log;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ActivityLogResponse {

    private UUID tortoiseId;

    @NotNull(message = "Activity type is required")
    private ActivityType activityType;

    @NotNull(message = "Start time is required")
    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @Size(max = 500, message = "Notes must be less than 500 characters")
    private String notes;

    public void fromActivityLog(ActivityLog activityLog){
        this.tortoiseId = activityLog.getTortoise().getUuid();
        this.activityType = activityLog.getActivityType();
        this.startTime = activityLog.getStartTime();
        this.endTime = activityLog.getEndTime();
        this.notes = activityLog.getNotes();
    }
}
