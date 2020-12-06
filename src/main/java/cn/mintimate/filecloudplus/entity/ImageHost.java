package cn.mintimate.filecloudplus.entity;

import com.baomidou.mybatisplus.annotation.*;

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
public class ImageHost implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private String id;

    private String imageName;

    @TableField(fill = FieldFill.INSERT)
    private Date createDate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateDate;

    private Integer downloadCount;

    private String path;

    private String imageType;

    private String uploadUser;

    @TableLogic
    private Integer deleted;


}
