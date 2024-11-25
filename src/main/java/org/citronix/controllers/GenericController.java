package org.citronix.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.citronix.services.GenericService;
import org.citronix.utils.StringUtil;
import org.citronix.utils.response.ApiResponse;
import org.citronix.utils.response.ResponseUtil;
import org.citronix.utils.validation.groups.OnCreate;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

public abstract class GenericController<T, REQ, RES> {
    protected GenericService<T, REQ, RES> service;

    protected GenericController(GenericService<T, REQ, RES> service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<RES>> save(@RequestBody @Validated(OnCreate.class) REQ requestDto, HttpServletRequest request) {
        RES createdEntity = service.save(requestDto);
        return ResponseEntity.ok(ResponseUtil.success(createdEntity, StringUtil.extractBaseName(createdEntity.getClass().getSimpleName()) + " created successfully", request.getRequestURI()));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<RES>>> findAll(HttpServletRequest request) {
        List<RES> entities = service.findAll();
        return ResponseEntity.ok(ResponseUtil.success(entities, StringUtil.extractBaseName(entities.get(0).getClass().getSimpleName()) + " fetched successfully", request.getRequestURI()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RES>> getById(@PathVariable String id, HttpServletRequest request) {
        RES entity = service.findById(id);
        return ResponseEntity.ok(ResponseUtil.success(entity, StringUtil.extractBaseName(entity.getClass().getSimpleName()) + " fetched successfully", request.getRequestURI()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<RES>> update(@PathVariable String id, @RequestBody @Valid REQ requestDto, HttpServletRequest request) {
        RES updatedEntity = service.update(id, requestDto);
        return ResponseEntity.ok(ResponseUtil.success(updatedEntity, StringUtil.extractBaseName(updatedEntity.getClass().getSimpleName()) + " updated successfully", request.getRequestURI()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id, HttpServletRequest request) {
        service.delete(id);
        return ResponseEntity.ok(ResponseUtil.success(null, "Entity deleted successfully", request.getRequestURI()));
    }
}