package com.fetters.picture.model.event;

import com.fetters.picture.model.entity.User;
import lombok.Getter;

/**
 * 删除空间事件
 */
@Getter
public class SpaceDeletedEvent {
    private final Long spaceId;
    private final User loginUser;

    public SpaceDeletedEvent(Long spaceId, User loginUser) {
        this.spaceId = spaceId;
        this.loginUser = loginUser;
    }
}
   