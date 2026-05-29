package org.project.rectast;

import lombok.Data;

import java.util.List;
import java.util.Set;

public record RepoResponseDto ( String login, String name, List<BranchInfoDto> branchesInfo) {}





