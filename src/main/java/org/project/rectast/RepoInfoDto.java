package org.project.rectast;

import lombok.Getter;
import lombok.Setter;


public record RepoInfoDto(String name, OwnerLoginDto owner) {
}
