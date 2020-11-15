package cn.mintimate.filecloudplus.service.impl;

import cn.mintimate.filecloudplus.entity.FileHost;
import cn.mintimate.filecloudplus.dao.FileHostMapper;
import cn.mintimate.filecloudplus.entity.ImageHost;
import cn.mintimate.filecloudplus.service.FileHostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class FileHostServiceImpl extends ServiceImpl<FileHostMapper, FileHost> implements FileHostService {
    @Autowired
    FileHostMapper mapper;

    // 一页展示几个文件
    private final int LIMIT = 8;

    @Override
    public int getPages(String file_type) {
        int count = mapper.selectAllByType(file_type).size();
        int page = 0;
        if (count % LIMIT == 0) {
            page = count / LIMIT;
        } else {
            page = count / LIMIT + 1;
        }
        return page;
    }

    @Override
    public int getPages(String file_type, String file_type_detail) {
        int count = mapper.selectAllByDetail(file_type_detail).size();
        int page = 0;
        if (count % LIMIT == 0) {
            page = count / LIMIT;
        } else {
            page = count / LIMIT + 1;
        }
        return page;
    }

    @Override
    public List<ImageHost> FindFiles(int page, String file_type) {
        int index = (page - 1) * LIMIT;
        return mapper.selectFileByType(index,LIMIT,file_type);
    }

    @Override
    public List<ImageHost> FindFiles(int page, String file_type, String file_type_detail) {
        int index = (page - 1) * LIMIT;
        return mapper.selectFileByTypeDetail(index,LIMIT,file_type_detail);
    }
}
