package com.smartCaa.smartCaa.DTOS;

import com.smartCaa.smartCaa.models.ProjectReference;

import java.util.List;

public record CalculatorReturnDto(ProjectReference result, List<ProjectReference> items) {
}
