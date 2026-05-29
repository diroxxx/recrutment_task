package org.project.rectast;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RepoService {

    private final RestTemplate restTemplate;

    public List<RepoInfoDto> getRepoName(String username) {

        RepoInfoDto[] repos = restTemplate.getForObject(
                "https://api.github.com/users/{username}/repos",
                RepoInfoDto[].class,
                username
        );

        if (repos == null || repos.length == 0) {
            return List.of();
        }

        return Arrays.asList(repos);

    }

    public List<BranchInfoDto> getBranches(String username, String repoName) {
        BranchInfoDto[] branches = restTemplate.getForObject(
                "https://api.github.com/repos/{username}/{repoName}/branches",
                BranchInfoDto[].class,
                username,
                repoName
        );

        if (branches == null || branches.length == 0) {
            return List.of();
        }

        return Arrays.asList(branches);
    }

    public boolean isUserExist(String username) {
        try {
            restTemplate.getForEntity("https://api.github.com/users/{username}", Object.class, username);
            return true;
        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                return false;
            }
            throw ex;
        }
    }
}


