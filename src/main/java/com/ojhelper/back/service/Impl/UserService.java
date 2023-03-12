package com.ojhelper.back.service.Impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ojhelper.back.entity.User;
import com.ojhelper.back.mapper.UserMapper;
import com.ojhelper.back.service.IUserService;
import com.ojhelper.back.utils.FileUploadUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class UserService implements IUserService {

    @Resource
    UserMapper userMapper;

    @Override
    public boolean saveBatch(Collection<User> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<User> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean updateBatchById(Collection<User> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdate(User entity) {
        return false;
    }

    @Override
    public User getOne(Wrapper<User> queryWrapper, boolean throwEx) {
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }

    @Override
    public Map<String, Object> getMap(Wrapper<User> queryWrapper) {
        return null;
    }

    @Override
    public <V> V getObj(Wrapper<User> queryWrapper, Function<? super Object, V> mapper) {
        return null;
    }

    @Override
    public BaseMapper<User> getBaseMapper() {
        return null;
    }

    @Override
    public Class<User> getEntityClass() {
        return null;
    }

    @Override
    public boolean uploadAvatar(MultipartFile file, String fileName, String path) {
        try {
            String avatarName = FileUploadUtils.builder()
                    .setAllowedExtension(new String[] {"jpg","jpeg","png"})
                    .setMaxSize(10)
                    .setPathName(path)
                    .upload(file,fileName);
            User user = new User();
            // WebMvcConfigurerImpl.addResourceHandler 配置了路径对应图片
            user.setAvatar("/avatars/" + avatarName);
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username",fileName);
            userMapper.update(user,queryWrapper);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
