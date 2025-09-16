package com.multiGenesys.mt_resources.service.impl;

import com.multiGenesys.common.ApiResponse;
import com.multiGenesys.mapper.MapperUtil;
import com.multiGenesys.mt_resources.dao.request.ResourceRequestDto;
import com.multiGenesys.mt_resources.dao.response.ResourceResponseDto;
import com.multiGenesys.mt_resources.entity.Resources;
import com.multiGenesys.mt_resources.repository.ResourceRepository;
import com.multiGenesys.mt_resources.service.ResourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {

      private final ResourceRepository resourceRepository;
      private final MapperUtil mapperUtil;

      @Override
      public ResponseEntity<ApiResponse<ResourceResponseDto>> createResource(ResourceRequestDto dto) {
            ApiResponse<ResourceResponseDto> response = new ApiResponse<>();

            Resources resources = mapperUtil.toEntity(dto, Resources.class);
            Resources savedResource = resourceRepository.save(resources);
            ResourceResponseDto responseDto = mapperUtil.toDto(savedResource, ResourceResponseDto.class);

            response.responseMethod(HttpStatus.CREATED.value(), "Resource created successfully", responseDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
      }

      @Override
      public ResponseEntity<ApiResponse<ResourceResponseDto>> getResourceById(Long id) {
            ApiResponse<ResourceResponseDto> response = new ApiResponse<>();

            Optional<Resources> byId = resourceRepository.findById(id);
            if (byId.isEmpty()) {
                  response.responseMethod(HttpStatus.NOT_FOUND.value(), "Resource not found", null);
                  return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            ResourceResponseDto responseDto = mapperUtil.toDto(byId.get(), ResourceResponseDto.class);
            response.responseMethod(HttpStatus.OK.value(), "Resource fetched successfully", responseDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
      }

      @Override
      public ResponseEntity<ApiResponse<List<ResourceResponseDto>>> getAllResources(int page, int size) {

            ApiResponse<List<ResourceResponseDto>> response = new ApiResponse<>();

            Pageable pageable = PageRequest.of(page, size);

            Page<Resources> all = resourceRepository.findAll(pageable);

            List<ResourceResponseDto> dtos = all.stream()
                       .map(resource -> mapperUtil.toDto(resource, ResourceResponseDto.class))
                       .toList();

            if (dtos.isEmpty()) {
                  response.responseMethod(HttpStatus.NOT_FOUND.value(), "No resources found", null);
                  return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            response.responseMethod(HttpStatus.OK.value(), "Resources fetched successfully", dtos);
            return new ResponseEntity<>(response, HttpStatus.OK);
      }

      @Override
      public ResponseEntity<ApiResponse<ResourceResponseDto>> updateResource(ResourceRequestDto dto, Long id) {
            return null;
      }

      @Override
      public ResponseEntity<ApiResponse<Void>> deleteResource(Long id) {
            return null;
      }
}
