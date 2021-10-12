package com.ljc555.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ljc555.entity.HousePicture;

/**
 * Created by 瓦力.
 */
public interface HousePictureRepository extends CrudRepository<HousePicture, Long> {
    List<HousePicture> findAllByHouseId(Long id);
}
