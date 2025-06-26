package com.fetters.picture.listener;

import com.fetters.picture.model.event.SpaceDeletedEvent;
import com.fetters.picture.service.PictureService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class PictureEventListener {

    @Resource
    private PictureService pictureService;

    @EventListener
    public void handleSpaceDeletedEvent(SpaceDeletedEvent event) {
        pictureService.deletePicturesBySpaceId(event.getSpaceId(), event.getLoginUser());
    }
}
   