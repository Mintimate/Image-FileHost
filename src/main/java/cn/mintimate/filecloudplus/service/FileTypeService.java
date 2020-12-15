package cn.mintimate.filecloudplus.service;

import cn.mintimate.filecloudplus.entity.FileType;
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
public interface FileTypeService extends IService<FileType> {
    List <String> getDetailByType(String type);
    List <String> getType();
}
