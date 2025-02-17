package tortoisemonitor.demo.domain.activity_log;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tortoisemonitor.demo.domain.tortoise.Tortoise;
import tortoisemonitor.demo.domain.tortoise.TortoiseService;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityLogRequest {


    private UUID tortoiseId;

    @NotNull(message = "Activity type is required")
    private ActivityType activityType;

    @NotNull(message = "Start time is required")
    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @Size(max = 500, message = "Notes must be less than 500 characters")
    @NotNull
    private String notes;



    public void toActivityLog(ActivityLog activityLog, TortoiseService tortoiseService) {
        activityLog.setActivityType(activityType);
        activityLog.setStartTime(startTime);
        activityLog.setEndTime(endTime);
        activityLog.setNotes(notes);
        if (tortoiseId != null) {
            activityLog.setTortoise(tortoiseService.getTortoiseById(this.tortoiseId));
        } else if (!tortoiseService.getAllTortoises().isEmpty()){
            activityLog.setTortoise(tortoiseService.getAllTortoises().getLast());
        }
    }
}
