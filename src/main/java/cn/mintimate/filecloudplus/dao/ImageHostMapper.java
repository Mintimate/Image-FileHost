package cn.mintimate.filecloudplus.dao;

import cn.mintimate.filecloudplus.entity.ImageHost;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Mintimate
 * @since 2020-10-27
 */
@Mapper
public interface ImageHostMapper extends BaseMapper<ImageHost> {
    List<ImageHost> selectAll();
    List<ImageHost> selectAllByType(String image_type);
    List<ImageHost> selectImage(int index,int limit);
    List<ImageHost> selectImageByType(int index,int limit,String image_type);

}
