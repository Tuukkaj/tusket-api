package fi.tuukka.tusketapi.api.time;

import fi.tuukka.tusketapi.api.time.dto.CreateTimeRequest;
import fi.tuukka.tusketapi.api.time.dto.PatchTimeRequest;
import fi.tuukka.tusketapi.api.time.dto.TimeDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/times")
@RequiredArgsConstructor
public class TimeController {

  private final TimeService service;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public TimeDto create(@RequestBody @Valid CreateTimeRequest request) {
    return service.create(request);
  }

  @PatchMapping("/{id}")
  public TimeDto patch(
      @PathVariable Long id,
      @RequestBody @Valid PatchTimeRequest request) {
    return service.patch(id, request);
  }


  @GetMapping("/{id}")
  public TimeDto getOne(@PathVariable Long id) {
    return service.findById(id);
  }

  @GetMapping
  public Page<TimeDto> getAll(@RequestParam(required = false) Pageable page) {
    return service.getAll(page);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable @Valid Long id) {
    service.delete(id);
  }
}
