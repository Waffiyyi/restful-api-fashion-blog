package com.waffiyyi.fashion.blog.service;

import com.waffiyyi.fashion.blog.DTOs.DesignDTO;
import com.waffiyyi.fashion.blog.entities.Design;

public interface DesignService {
  DesignDTO saveDesign(DesignDTO designDTO);

  DesignDTO findDesignById(Long designId);

  void deleteDesign(Long designId);

  void likeDesign(Long designId);

  void unlikeDesign(Long designId);
}
