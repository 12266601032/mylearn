package com.example.spring.validation;

import java.util.List;
import lombok.Data;

/**
 * Date:     2018年10月31日 13:51 <br/>
 *
 * @author lcc
 * @see
 * @since
 */
@Data
public class POJOMetadata {

  private String className;
  private List<FieldMetadata> fieldMetadataList;


  @Data
  public static class FieldMetadata {

    private String fieldName;
    private List<RuleMetadata> ruleMetadataList;
  }

  @Data
  public static class RuleMetadata {

    private String ruleName;
    private String rejectMessage;
    private String value;
    private String groupName;

  }

}
