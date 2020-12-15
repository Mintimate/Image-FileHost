package cn.mintimate.filecloudplus.service.impl;

import cn.mintimate.filecloudplus.entity.FileType;
import cn.mintimate.filecloudplus.dao.FileTypeMapper;
import cn.mintimate.filecloudplus.service.FileTypeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Mintimate
 * @since 2020-10-27
 */
@Service
public class FileTypeServiceImpl extends ServiceImpl<FileTypeMapper, FileType> implements FileTypeService {
    @Resource
    FileTypeMapper mapper;

    @Override
    public List<String> getDetailByType(String type) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("file_type",type);
        List <String> list=new ArrayList<>();
        List <FileType> fileTypes= mapper.selectList(wrapper);
        for (FileType fileType:fileTypes) {
            list.add(fileType.getFileTypeDetail());
        }

        return list;
    }

    @Override
    public List<String> getType() {
        List<FileType> fileTypes = mapper.selectAllFileType();
        List<String> list = new ArrayList<>();
        for (FileType filetype: fileTypes) {
            list.add(filetype.getFileType());
        }
        return list;
    }
}
