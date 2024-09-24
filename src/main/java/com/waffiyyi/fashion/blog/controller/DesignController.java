package com.waffiyyi.fashion.blog.controller;

import com.waffiyyi.fashion.blog.DTOs.DesignDTO;
import com.waffiyyi.fashion.blog.entities.User;
import com.waffiyyi.fashion.blog.service.DesignService;
import com.waffiyyi.fashion.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/designs")
@RequiredArgsConstructor
public class DesignController {
  private final DesignService designService;
  private final UserService userService;


  @PostMapping
  public ResponseEntity<DesignDTO> createDesign(@RequestBody DesignDTO designDTO,
                                                @RequestHeader("Authorization")
                                                String jwt) {
    User user = userService.findUserByJWTToken(jwt);
    DesignDTO createDesign = designService.saveDesign(designDTO, user);
    return ResponseEntity.status(HttpStatus.CREATED).body(createDesign);
  }

  @GetMapping("/{designId}")
  public ResponseEntity<DesignDTO> getDesignById(@PathVariable Long designId) {
    DesignDTO designDTO = designService.findDesignById(designId);
    return ResponseEntity.ok(designDTO);
  }

  @DeleteMapping("/{designId}")
  public ResponseEntity<String> deleteDesign(@PathVariable Long designId,
                                             @RequestHeader("Authorization")
                                             String jwt) {
    User user = userService.findUserByJWTToken(jwt);
    designService.deleteDesign(designId, user);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{designId}/toggle-like")
  public ResponseEntity<String> toggleLikeDesign(@PathVariable Long designId,
                                                  @RequestHeader("Authorization")
                                                  String jwt) {
    User user = userService.findUserByJWTToken(jwt);
    return new ResponseEntity<>(designService.toggleLikeDesign(designId, user),
                                HttpStatus.OK);
  }

}
