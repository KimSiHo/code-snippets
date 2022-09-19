package me.bigmonkey.structure.common.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/l7check")
@RequiredArgsConstructor
public class L7CheckApi {

    private final ApplicationHealthIndicator healthIndicator;

    @GetMapping
    public ResponseEntity<Void> health() {
        final HttpStatus responseStatus =
            Status.UP == healthIndicator.health().getStatus() ? HttpStatus.OK : HttpStatus.SERVICE_UNAVAILABLE;

        return ResponseEntity.status(responseStatus).build();
    }

    @PutMapping(path = "/up")
    public void up() {
        final Health up = Health.up().build();
        healthIndicator.setHealth(up);
    }

    @PutMapping(path = "/down")
    public void down() {
        final Health down = Health.down().build();
        healthIndicator.setHealth(down);
    }
}
