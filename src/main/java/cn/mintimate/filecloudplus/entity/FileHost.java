package cn.mintimate.filecloudplus.entity;

import com.baomidou.mybatisplus.annotation.*;

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
public class FileHost implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    private String fileType;

    private String fileTypeDetail;

    private String fileName;

    @TableField(fill = FieldFill.INSERT)
    private Date createDate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateDate;

    private Integer downloadCount;

    private String path;

    private Double fileSize;

    private String uploadUser;

    @TableLogic
    private Integer deleted;


}
