package fi.tuukka.tusketapi.api.time;

import static fi.tuukka.tusketapi.api.time.TimeStatus.IN_PROGRESS;
import static fi.tuukka.tusketapi.api.time.TimeStatus.PAUSED;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import fi.tuukka.tusketapi.api.time.dto.CreateTimeRequest;
import fi.tuukka.tusketapi.api.time.dto.PatchTimeRequest;
import fi.tuukka.tusketapi.api.time.dto.TimeDto;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class TimeService {

  private final TimeRepository repository;

  public TimeDto create(CreateTimeRequest request) {
    var time = Time.builder()
        .userId(request.getUserId())
        .categoryId(request.getCategoryId())
        .description(request.getDescription())
        .startTime(Instant.now())
        .status(IN_PROGRESS)
        .pauses(List.of())
        .build();

    return repository.save(time).toDto();
  }

  public TimeDto findById(Long id) {
    return repository.findById(id)
        .map(Time::toDto)
        .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
            "Entry not found with given id"));
  }

  public Page<TimeDto> getAll(Pageable page) {
    page = Optional.ofNullable(page)
        .orElse(PageRequest.of(0, 50));
    return repository.findAll(page).map(Time::toDto);
  }

  public void delete(Long id) {
    repository.deleteById(id);
  }

  public TimeDto patch(Long id, PatchTimeRequest request) {
    var time = repository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Time not found with given id"));

    switch (request.getStatus()) {
      case ENDED -> {
        if (time.getEndTime() != null) {
          throw new ResponseStatusException(BAD_REQUEST,
              "Cannot stop timer when it has been already stopped");
        }
        time.setEndTime(Instant.now());

        // TODO calculate duration
      }
      case PAUSED -> {
        if (time.getStatus() != IN_PROGRESS) {
          throw new ResponseStatusException(BAD_REQUEST, "Timer must be in progress");
        }

        time.getPauses().stream()
            .filter(tp -> tp.getPause() != null && tp.getRestart() == null)
            .findFirst()
            .ifPresent(tp -> {
              throw new ResponseStatusException(BAD_REQUEST, "Time has been already paused");
            });

        time.getPauses().add(TimePause.builder()
            .pause(Instant.now())
            .build());
      }
      case IN_PROGRESS -> {
        if (time.getStatus() != PAUSED) {
          throw new ResponseStatusException(BAD_REQUEST, "Timer is not paused");
        }

        var pause = time.getPauses().stream()
            .filter(tp -> tp.getPause() != null && tp.getRestart() == null)
            .findFirst()
            .orElseThrow(
                () -> new ResponseStatusException(BAD_REQUEST, "Timer has not been paused"));

        pause.setRestart(Instant.now());
      }
      default -> throw new ResponseStatusException(BAD_REQUEST, "Status must be given on patch");
    }

    time.setStatus(request.getStatus());

    return repository.save(time).toDto();
  }
}
