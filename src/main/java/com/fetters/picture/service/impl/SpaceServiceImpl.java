package com.fetters.picture.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fetters.picture.exception.BusinessException;
import com.fetters.picture.exception.ErrorCode;
import com.fetters.picture.exception.ThrowUtils;
import com.fetters.picture.mapper.SpaceMapper;
import com.fetters.picture.model.dto.space.SpaceAddRequest;
import com.fetters.picture.model.dto.space.SpaceQueryRequest;
import com.fetters.picture.model.entity.Space;
import com.fetters.picture.model.entity.User;
import com.fetters.picture.model.enums.SpaceLevelEnum;
import com.fetters.picture.model.vo.SpaceVO;
import com.fetters.picture.model.vo.UserVO;
import com.fetters.picture.service.SpaceService;
import com.fetters.picture.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author 99716
 * @description 针对表【space(空间)】的数据库操作Service实现
 * @createDate 2025-06-13 11:55:30
 */
@Service
public class SpaceServiceImpl extends ServiceImpl<SpaceMapper, Space>
        implements SpaceService {

    @Resource
    private UserService userService;

    @Resource
    private TransactionTemplate transactionTemplate;

    Map<Long, Object> userLockMap = new ConcurrentHashMap<>();

    @Override
    public long addSpace(SpaceAddRequest spaceAddRequest, User loginUser) {
        Space space = new Space();
        BeanUtils.copyProperties(spaceAddRequest, space);
        // 填充参数默认值
        if (StrUtil.isBlank(spaceAddRequest.getSpaceName())) {
            space.setSpaceName("默认空间");
        }
        if (spaceAddRequest.getSpaceLevel() == null) {
            space.setSpaceLevel(SpaceLevelEnum.COMMON.getValue());
        }
        // 根据空间级别填充容量和大小
        this.fillSpaceBySpaceLevel(space);

        // 校验空间对象参数
        this.validSpace(space, true);

        // 校验权限，非管理员只能创建普通级别空间
        Long userId = loginUser.getId();
        space.setUserId(userId);
        if (SpaceLevelEnum.COMMON.getValue() != space.getSpaceLevel() && !userService.isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "无权限创建指定级别的空间");
        }

        // 控制同一用户只能创建一个私有空间
        Object lock = userLockMap.computeIfAbsent(userId, key -> new Object());
        synchronized (lock) {
            Long newSpaceId = transactionTemplate.execute(status -> {
                // 判断是否已有空间
                boolean exists = this.lambdaQuery().eq(Space::getUserId, userId).exists();
                if (exists) {
                    throw new BusinessException(ErrorCode.OPERATION_ERROR, "每个用户仅能有一个私有空间");
                }
                boolean save = this.save(space);
                if (!save) {
                    throw new BusinessException(ErrorCode.OPERATION_ERROR, "创建空间失败");
                }
                return space.getId();
            });
            return newSpaceId;
        }
    }

    @Override
    public QueryWrapper<Space> getQueryWrapper(SpaceQueryRequest spaceQueryRequest) {
        QueryWrapper<Space> queryWrapper = new QueryWrapper<>();
        if (spaceQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        // 从对象中取值
        Long id = spaceQueryRequest.getId();
        Long userId = spaceQueryRequest.getUserId();
        String spaceName = spaceQueryRequest.getSpaceName();
        Integer spaceLevel = spaceQueryRequest.getSpaceLevel();
        String sortField = spaceQueryRequest.getSortField();
        String sortOrder = spaceQueryRequest.getSortOrder();

        // 添加条件
        queryWrapper.eq(ObjUtil.isNotEmpty(id), "id", id);
        queryWrapper.eq(ObjUtil.isNotEmpty(userId), "userId", userId);
        queryWrapper.like(StrUtil.isNotBlank(spaceName), "spaceName", spaceName);
        queryWrapper.eq(ObjUtil.isNotEmpty(spaceLevel), "spaceLevel", spaceLevel);
        queryWrapper.orderBy(StrUtil.isNotEmpty(sortField), sortOrder.equals("ascend"), sortField);
        return queryWrapper;
    }

    @Override
    public SpaceVO getSpaceVO(Space space, HttpServletRequest request) {
        // 对象转封装类
        SpaceVO spaceVO = SpaceVO.objToVo(space);
        // 关联查询用户信息
        Long userId = spaceVO.getUserId();
        if (userId != null && userId > 0) {
            User user = userService.getById(userId);
            UserVO userVO = userService.getUserVO(user);
            spaceVO.setUser(userVO);
        }
        return spaceVO;
    }

    @Override
    public Page<SpaceVO> getSpaceVOPage(Page<Space> spacePage, HttpServletRequest request) {
        List<Space> spaceaList = spacePage.getRecords();
        Page<SpaceVO> spaceaVOPage = new Page<>(spacePage.getCurrent(), spacePage.getSize(), spacePage.getTotal());
        if (CollUtil.isEmpty(spaceaList)) {
            return spaceaVOPage;
        }
        // 对象列表 => 封装对象列表
        List<SpaceVO> spaceaVOList = spaceaList.stream().map(SpaceVO::objToVo).collect(Collectors.toList());
        // 1. 关联查询用户信息
        Set<Long> userIdSet = spaceaList.stream().map(Space::getUserId).collect(Collectors.toSet());
        Map<Long, List<User>> userIdUserListMap = userService.listByIds(userIdSet).stream()
                .collect(Collectors.groupingBy(User::getId));
        // 2. 填充信息
        spaceaVOList.forEach(spaceaVO -> {
            Long userId = spaceaVO.getUserId();
            User user = null;
            if (userIdUserListMap.containsKey(userId)) {
                user = userIdUserListMap.get(userId).get(0);
            }
            spaceaVO.setUser(userService.getUserVO(user));
        });
        spaceaVOPage.setRecords(spaceaVOList);
        return spaceaVOPage;
    }

    @Override
    public void validSpace(Space space, boolean add) {
        ThrowUtils.throwIf(space == null, ErrorCode.PARAMS_ERROR);
        // 从对象中取值
        String spaceName = space.getSpaceName();
        Integer spaceLevel = space.getSpaceLevel();
        SpaceLevelEnum spaceLevelEnum = SpaceLevelEnum.getEnumByValue(spaceLevel);

        // 新增操作需要非空字段
        if (add) {
            if (StrUtil.isBlank(spaceName)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "空间名称不能为空");
            }
            if (spaceLevel == null) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "空间级别不能为空");
            }
        }

        if (StrUtil.isNotBlank(spaceName) && spaceName.length() > 30) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "空间名称过长");
        }
        if (spaceLevel != null && spaceLevelEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "空间级别不存在");
        }
    }

    @Override
    public void fillSpaceBySpaceLevel(Space space) {
        Integer spaceLevel = space.getSpaceLevel();
        SpaceLevelEnum spaceLevelEnum = SpaceLevelEnum.getEnumByValue(spaceLevel);
        if (spaceLevelEnum != null) {
            long maxCount = spaceLevelEnum.getMaxCount();
            long maxSize = spaceLevelEnum.getMaxSize();
            // 如果管理员已经设置了最大数量和最大大小，则不进行填充
            if (space.getMaxCount() == null) {
                space.setMaxCount(maxCount);
            }
            if (space.getMaxSize() == null) {
                space.setMaxSize(maxSize);
            }
        }
    }
}




