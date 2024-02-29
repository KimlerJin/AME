package com.ame.service;

import com.ame.dao.BaseEntityDao;
import com.ame.dao.TagDao;
import com.ame.entity.TagEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagService extends AbstractBaseEntityService<TagEntity> implements ITagService {

    @Autowired
    private TagDao tagDao;

    @Override
    protected BaseEntityDao<TagEntity> getDao() {
        return tagDao;
    }




}
