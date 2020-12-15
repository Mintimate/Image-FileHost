package cn.mintimate.filecloudplus.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class FileType implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    private String fileType;

    private String fileTypeDetail;


}
