package com.multiGenesys.mt_resources.service;

import com.multiGenesys.common.ApiResponse;
import com.multiGenesys.mt_resources.dao.request.ResourceRequestDto;
import com.multiGenesys.mt_resources.dao.response.ResourceResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ResourceService {

      ResponseEntity<ApiResponse<ResourceResponseDto>> createResource(ResourceRequestDto dto);

      ResponseEntity<ApiResponse<ResourceResponseDto>> getResourceById(Long id);

      ResponseEntity<ApiResponse<List<ResourceResponseDto>>> getAllResources(int page, int size);

      ResponseEntity<ApiResponse<ResourceResponseDto>> updateResource(ResourceRequestDto dto, Long id);

      ResponseEntity<ApiResponse<Void>> deleteResource(Long id);
}

