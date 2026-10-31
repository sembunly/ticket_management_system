package com.bunli.user.dto.response;

import com.bunli.common.dto.Metadata;
import com.bunli.common.dto.PaginationResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record RolePaginationResponse(
                @JsonProperty("items") List<RoleResponse> responses,
                @JsonProperty("page") PaginationResponse paginationResponse,
                @JsonProperty("metadata") Metadata metadata) {
}
