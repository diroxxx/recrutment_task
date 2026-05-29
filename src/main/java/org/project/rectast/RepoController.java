package org.project.rectast;

import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RepoController {
    private final RepoService repoService;

    @GetMapping("/repos/{username}")
    public ResponseEntity<?> getRepoInfo(@PathVariable @NotBlank String username) {

        if (!repoService.isUserExist(username)) {
            throw new NotFoundException("User not found");
        }

        List<RepoResponseDto> repoResponseDtos = new ArrayList<>();

        List<RepoInfoDto> repoInfos = repoService.getRepoName(username);

        if (repoInfos.isEmpty()) {
            return ResponseEntity.ok(repoResponseDtos);
        }

        repoInfos.forEach(repoInfo -> {
            List<BranchInfoDto> branches = repoService.getBranches(username, repoInfo.name());

            RepoResponseDto repoResponseDto = new RepoResponseDto(username, repoInfo.name(), branches);

            repoResponseDtos.add(repoResponseDto);
                });

        return ResponseEntity.ok(repoResponseDtos);
    }
}
