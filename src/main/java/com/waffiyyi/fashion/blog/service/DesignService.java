package com.waffiyyi.fashion.blog.service;

import com.waffiyyi.fashion.blog.DTOs.DesignDTO;
import com.waffiyyi.fashion.blog.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface DesignService {
   DesignDTO saveDesign(DesignDTO designDTO, User user);
   DesignDTO updateDesign(DesignDTO designDTO, Long designId, User user);

   DesignDTO findDesignById(Long designId);

   Page<DesignDTO> getAllDesign(int page, int size);
   void deleteDesign(Long designId, User user);

   String toggleLikeDesign(Long designId, User user);
}
