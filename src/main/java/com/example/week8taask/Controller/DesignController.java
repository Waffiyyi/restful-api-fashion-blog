package com.example.week8taask.Controller;

import com.example.week8taask.DTOs.DesignDTO;
import com.example.week8taask.Services.DesignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/designs")

public class DesignController {
    private DesignService designService;
    @Autowired
    public DesignController(DesignService designService){
     this.designService = designService;
    }


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
       // return ResponseEntity.noContent().build();
        return ResponseEntity.ok().body("Successfully Deleted");
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
