package cn.mintimate.filecloudplus.service;

import cn.mintimate.filecloudplus.entity.ImageHost;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mintimate
 * @since 2020-10-27
 */
public interface ImageHostService extends IService<ImageHost> {
    int getPages();
    int getPages(String image_type);
    List<ImageHost> FindImages(int page);
    List<ImageHost> FindImages(int page,String image_type);
}
