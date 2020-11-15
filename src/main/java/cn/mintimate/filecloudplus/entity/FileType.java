package cn.mintimate.filecloudplus.entity;

import java.io.Serializable;
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

      private String fileType;


}
