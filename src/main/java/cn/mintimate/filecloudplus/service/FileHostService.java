package cn.mintimate.filecloudplus.service;

import cn.mintimate.filecloudplus.entity.FileHost;
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
public interface FileHostService extends IService<FileHost> {
    int getPages(String file_type);
    int getPages(String file_type,String file_type_detail);
    List<ImageHost> FindFiles(int page, String file_type);
    List<ImageHost> FindFiles(int page,String file_type,String file_type_detail);

}
