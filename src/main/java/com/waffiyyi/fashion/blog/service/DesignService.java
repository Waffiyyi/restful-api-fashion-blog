package com.waffiyyi.fashion.blog.service;

import com.waffiyyi.fashion.blog.DTOs.DesignDTO;
import com.waffiyyi.fashion.blog.entities.Design;
import com.waffiyyi.fashion.blog.entities.User;

import java.util.List;

public interface DesignService {
  DesignDTO saveDesign(DesignDTO designDTO, User user);
  DesignDTO updateDesign(DesignDTO designDTO, Long designId, User user);

  DesignDTO findDesignById(Long designId);

  void deleteDesign(Long designId, User user);

  String toggleLikeDesign(Long designId, User user);
}
