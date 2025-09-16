package com.multiGenesys.mt_resources.controller;

import com.multiGenesys.common.ApiResponse;
import com.multiGenesys.mt_resources.dao.request.ResourceRequestDto;
import com.multiGenesys.mt_resources.dao.response.ResourceResponseDto;
import com.multiGenesys.mt_resources.service.ResourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/resources")
@Slf4j
@RequiredArgsConstructor
public class ResourceController {

      private final ResourceService resourceService;

      @PostMapping
      ResponseEntity<?> createResources(@RequestBody ResourceRequestDto dto){
          return resourceService.createResource(dto);
      }

      @GetMapping("/{id}")
      ResponseEntity<?> getResourceById(@PathVariable Long id){
          return resourceService.getResourceById(id);
      }

      @GetMapping
      ResponseEntity<?> getAllResource(int page, int size){
            return resourceService.getAllResources(page, size);
      }

      @PutMapping("/{id}")
      ResponseEntity<?> updateResource(@RequestBody ResourceRequestDto dto, @PathVariable Long id){
            return resourceService.updateResource(dto,id);
      }

      @DeleteMapping("{id}")
      ResponseEntity<?> deletResource(@PathVariable Long id){
            return resourceService.deleteResource(id);
      }

}
