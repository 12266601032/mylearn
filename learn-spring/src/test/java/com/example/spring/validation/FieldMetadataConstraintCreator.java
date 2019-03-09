package com.example.spring.validation;

import com.example.spring.validation.POJOMetadata.RuleMetadata;
import org.hibernate.validator.cfg.ConstraintDef;
import org.hibernate.validator.cfg.defs.EmailDef;
import org.hibernate.validator.cfg.defs.LengthDef;
import org.hibernate.validator.cfg.defs.NotNullDef;

/**
 * Date:     2018年10月31日 14:05 <br/>
 *
 * @author lcc
 * @see
 * @since
 */
public class FieldMetadataConstraintCreator {


  public ConstraintDef<?, ?> createConstraint(RuleMetadata metadata) {
    ConstraintDef<?, ?> def = null;
    switch (metadata.getRuleName()) {
      case "require":
        def = new NotNullDef()
            .message(metadata.getRejectMessage());
        break;
      case "maxLength":
        def = new LengthDef().max(Integer.valueOf(metadata.getValue()))
            .message(metadata.getRejectMessage());
        break;
      case "minLength":
        def = new LengthDef().min(Integer.valueOf(metadata.getValue()))
            .message(metadata.getRejectMessage());
        break;
      case "email":
        def = new EmailDef().message(metadata.getRejectMessage());
        break;
    }
    return def;
  }

}
