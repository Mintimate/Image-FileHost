package cn.mintimate.filecloudplus.service.impl;

import cn.mintimate.filecloudplus.entity.UserAdmin;
import cn.mintimate.filecloudplus.dao.UserAdminMapper;
import cn.mintimate.filecloudplus.service.UserAdminService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
public class UserAdminServiceImpl extends ServiceImpl<UserAdminMapper, UserAdmin> implements UserAdminService {

    @Resource
    UserAdminMapper mapper;

    @Override
    public boolean findUser(String username, String mail) {
        boolean flag=false;
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("username",username);
        QueryWrapper wrapper1=new QueryWrapper();
        wrapper1.eq("user_email",mail);
        if((mapper.selectList(wrapper1).size()==0)
                &&(mapper.selectList(wrapper).size()==0)){
            flag=true;
        }
        return flag;
    }

    @Override
    public boolean addUser(String username, String password, String mail) {
        boolean flag=false;

        return flag;
    }
}
