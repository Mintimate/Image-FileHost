package cn.mintimate.filecloudplus.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author Mintimate
 * @since 2020-10-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserIp implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String userIp;

    @TableField(fill = FieldFill.INSERT)
    private Date createDate;

    private String targetClassify;

    private String targetId;


}
