package cn.mintimate.filecloudplus.dao;

import cn.mintimate.filecloudplus.entity.FileType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Mintimate
 * @since 2020-10-27
 */
public interface FileTypeMapper extends BaseMapper<FileType> {
    List <FileType> selectAllFileType();

}
