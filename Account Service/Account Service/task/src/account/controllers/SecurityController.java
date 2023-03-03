package account.controllers;

import account.dto.EventDto;
import account.services.EventServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/security")
@Tag(name = "Security", description = "Security API")
public class SecurityController {

    private final EventServiceImpl eventService;

    @Autowired
    public SecurityController(EventServiceImpl eventService) {
        this.eventService = eventService;
    }

    @Operation(summary = "Get Security Events", description = "Get all security events", tags = {
            "Security" })
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping("/events")
    public ResponseEntity<List<EventDto>> findAll() {
        return ResponseEntity.ok(eventService.findAll());
    }

}
