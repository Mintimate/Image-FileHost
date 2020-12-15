package cn.mintimate.filecloudplus.service;

import cn.mintimate.filecloudplus.entity.UserAdmin;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mintimate
 * @since 2020-10-27
 */
public interface UserAdminService extends IService<UserAdmin> {
    boolean findUser(String username,String mail);
    boolean addUser(String username,String password,String mail);
}
