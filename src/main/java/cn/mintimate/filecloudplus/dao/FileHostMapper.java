package cn.mintimate.filecloudplus.dao;

import cn.mintimate.filecloudplus.entity.FileHost;
import cn.mintimate.filecloudplus.entity.ImageHost;
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
public interface FileHostMapper extends BaseMapper<FileHost> {
    List<ImageHost> selectAll();
    List<ImageHost> selectAllByType(String file_type);
    List<ImageHost> selectAllByDetail(String file_type_detail);
    List<ImageHost> selectAllByTypeDetail(String file_type,String file_type_detail);
    List<ImageHost> selectFile(int index,int limit);
    List<ImageHost> selectFileByType(int index,int limit,String file_type);
    List<ImageHost> selectFileByTypeDetail(int index,int limit,String file_type_detail);


}
