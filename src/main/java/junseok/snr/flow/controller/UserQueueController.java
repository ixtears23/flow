package junseok.snr.flow.controller;

import junseok.snr.flow.dto.AllowUserResponse;
import junseok.snr.flow.dto.AllowedUserResponse;
import junseok.snr.flow.dto.RankNumberResponse;
import junseok.snr.flow.dto.RegisterUserResponse;
import junseok.snr.flow.service.UserQueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/queue")
@RequiredArgsConstructor
public class UserQueueController {
    private final UserQueueService userQueueService;

    // 등록 할 수 있는 API path
    @PostMapping("")
    public Mono<RegisterUserResponse> registerUser(@RequestParam(name = "queue", defaultValue = "default") String queue,
                                                   @RequestParam(name = "user_id") Long userId) {
        return userQueueService.registerWaitQueue(queue, userId)
                .map(RegisterUserResponse::new);
    }

    @PostMapping("/allow")
    public Mono<AllowUserResponse> allowUser(@RequestParam(name = "queue", defaultValue = "default") String queue,
                                             @RequestParam(name = "count") Long count) {
        return userQueueService.allowUser(queue, count)
                .map(allowed -> new AllowUserResponse(count, allowed));
    }

    @GetMapping("/allowed")
    public Mono<AllowedUserResponse> isAllowedUser(@RequestParam(name = "queue", defaultValue = "default") String queue,
                                                 @RequestParam(name = "user_id") Long userId) {
        return userQueueService.isAllowed(queue, userId)
                .map(AllowedUserResponse::new);
    }

    @GetMapping("/rank")
    public Mono<RankNumberResponse> getRank(@RequestParam(name = "queue", defaultValue = "default") String queue,
                                            @RequestParam(name = "user_id") Long userId) {
        return userQueueService.getRank(queue, userId)
                .map(RankNumberResponse::new);
    }
}
