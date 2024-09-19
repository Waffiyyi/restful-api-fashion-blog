package com.waffiyyi.fashion.blog.controller;

import com.waffiyyi.fashion.blog.DTOs.DesignDTO;
import com.waffiyyi.fashion.blog.service.DesignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/designs")
@RequiredArgsConstructor
public class DesignController {
    private final DesignService designService;



    @PostMapping
    public ResponseEntity<DesignDTO> createDesign(@RequestBody DesignDTO designDTO){
        DesignDTO createDesign = designService.saveDesign(designDTO);
        return  ResponseEntity.status(HttpStatus.CREATED).body(createDesign);
    }
    @GetMapping("/{designId}")
    public ResponseEntity<DesignDTO> getDesignById(@PathVariable Long designId){
        DesignDTO designDTO = designService.findDesignById(designId);
        return ResponseEntity.ok(designDTO);
    }
    @DeleteMapping("/{designId}")
    public ResponseEntity<String> deleteDesign(@PathVariable Long designId){
        designService.deleteDesign(designId);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/{designId}/like")
    public ResponseEntity<String> likeDesign(@PathVariable Long designId){
        designService.likeDesign(designId);
        return ResponseEntity.ok().body("Successfully Liked");
    }

    @PostMapping("/{designId}/unlike")
    public ResponseEntity<String> unlikeDesign(@PathVariable Long designId) {
        designService.unlikeDesign(designId);
        return ResponseEntity.ok().body("Successfully Unliked");
    }


}
