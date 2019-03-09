package com.example.spring.validation;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.spring.validation.POJOMetadata.FieldMetadata;
import com.example.spring.validation.POJOMetadata.RuleMetadata;
import java.lang.annotation.ElementType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.hibernate.validator.cfg.ConstraintMapping;
import org.hibernate.validator.cfg.context.PropertyConstraintMappingContext;
import org.hibernate.validator.cfg.context.TypeConstraintMappingContext;
import org.hibernate.validator.internal.cfg.context.DefaultConstraintMapping;
import org.junit.Before;
import org.junit.Test;

/**
 * Date:     2018年10月30日 14:44 <br/>
 *
 * @author lcc
 * @see
 * @since
 */
public class BeanValidationDemo {

  private JSONObject metadata;

  @Before
  public void setUp() throws Exception {
    metadata = JSONObject.parseObject("{\n"
        + "  \"menu\": {},\n"
        + "  \"apiMeta\": {\n"
        + "    \"voClassName\": \"com.example.spring.validation.BeanValidationDemo$User\"\n"
        + "  },\n"
        + "  \"dbConnectionMeta\": {\n"
        + "    \"url\": \"\",\n"
        + "    \"username\": \"\",\n"
        + "    \"password\": \"\",\n"
        + "    \"tableName\": \"\",\n"
        + "    \"description\": \"\",\n"
        + "    \"type\": \"\",\n"
        + "    \"relateTable\": \"\",\n"
        + "    \"relateKey\": \"\"\n"
        + "  },\n"
        + "  \"editable\": true,\n"
        + "  \"searchable\": true,\n"
        + "  \"detailView\": true,\n"
        + "  \"fieldsGroups\": [],\n"
        + "  \"searchMeta\": {\n"
        + "    \"commonGroup\": [\n"
        + "      \"name\"\n"
        + "    ],\n"
        + "    \"expandGroup\": []\n"
        + "  },\n"
        + "  \"buttonsMeta\": [],\n"
        + "  \"tableMeta\": {\n"
        + "    \"pageable\": true,\n"
        + "    \"addSelectionCol\": false,\n"
        + "    \"addSerialCol\": false,\n"
        + "    \"addOptionCol\": true,\n"
        + "    \"toolbarIndexes\": [],\n"
        + "    \"colOptionIndexes\": []\n"
        + "  },\n"
        + "  \"fieldsMataList\": [\n"
        + "    {\n"
        + "      \"id\": null,\n"
        + "      \"index\": null,\n"
        + "      \"show\": true,\n"
        + "      \"groups\": [],\n"
        + "      \"dictKey\": \"\",\n"
        + "      \"dbProps\": {\n"
        + "        \"vcode\": \"name\",\n"
        + "        \"code\": \"name\",\n"
        + "        \"dataType\": \"varchar\",\n"
        + "        \"length\": 1000,\n"
        + "        \"precision\": 0,\n"
        + "        \"nonNull\": true,\n"
        + "        \"primaryKey\": false,\n"
        + "        \"description\": \"名字\"\n"
        + "      },\n"
        + "      \"table\": {\n"
        + "        \"title\": \"\",\n"
        + "        \"align\": \"center\",\n"
        + "        \"headerAlign\": \"center\",\n"
        + "        \"show\": true,\n"
        + "        \"fixed\": false,\n"
        + "        \"sortable\": false,\n"
        + "        \"width\": 100\n"
        + "      },\n"
        + "      \"form\": {\n"
        + "        \"editable\": true,\n"
        + "        \"placeholder\": \"\",\n"
        + "        \"inputType\": \"\",\n"
        + "        \"defaultVal\": \"\",\n"
        + "        \"rules\": [\n"
        + "          {\n"
        + "            \"value\": \"\",\n"
        + "            \"name\": \"require\",\n"
        + "            \"rejectMessage\": \"该字段不能为空\",\n"
        + "            \"validationGroups\": [\n"
        + "              \"anyGroup\"\n"
        + "            ]\n"
        + "          },\n"
        + "          {\n"
        + "            \"value\": \"100\",\n"
        + "            \"name\": \"maxLength\",\n"
        + "            \"rejectMessage\": \"字段长度不能超过100\",\n"
        + "            \"validationGroups\": [\n"
        + "              \"anyGroup\"\n"
        + "            ]\n"
        + "          },\n"
        + "          {\n"
        + "            \"value\": \"10\",\n"
        + "            \"name\": \"minLength\",\n"
        + "            \"rejectMessage\": \"字段长度不能少于10\",\n"
        + "            \"validationGroups\": [\n"
        + "              \"anyGroup\"\n"
        + "            ]\n"
        + "          },\n"
        + "          {\n"
        + "            \"value\": \"\",\n"
        + "            \"name\": \"email\",\n"
        + "            \"rejectMessage\": \"请输入正确的邮箱\",\n"
        + "            \"validationGroups\": [\n"
        + "              \"anyGroup\"\n"
        + "            ]\n"
        + "          }\n"
        + "        ]\n"
        + "      },\n"
        + "      \"search\": {\n"
        + "        \"searchable\": true,\n"
        + "        \"opts\": [\n"
        + "          {\n"
        + "            \"name\": \"等于\",\n"
        + "            \"value\": \"eq\"\n"
        + "          }\n"
        + "        ]\n"
        + "      }\n"
        + "    }\n"
        + "  ]\n"
        + "}");
  }


  @Test
  public void name() {
    ConstraintMapping constraintMapping = new DefaultConstraintMapping();
    configureConstraintMapping(constraintMapping);
    ValidatorFactory validatorFactory = Validation
        .byProvider(HibernateValidator.class)
        .configure()
        .ignoreXmlConfiguration()
        .addMapping(constraintMapping)
        .addProperty(HibernateValidatorConfiguration.FAIL_FAST, "true")
        .buildValidatorFactory();
    Validator validator = validatorFactory.getValidator();
    User user = new User();
    Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
    printViolation(constraintViolations);
    user = new User();
    user.setName("1");
    constraintViolations = validator.validate(user);
    printViolation(constraintViolations);


  }

  private void printViolation(Set<ConstraintViolation<User>> constraintViolations) {
    for (ConstraintViolation<User> constraintViolation : constraintViolations) {
      System.out.println(constraintViolation.getMessage());
    }
  }

  private void configureConstraintMapping(ConstraintMapping constraintMapping) {
    List<FieldMetadata> fieldMetadataList = metadata.getJSONArray("fieldsMataList")
        .stream()
        .map(this::toJSONObject)
        .collect(Collectors
            .toMap(e -> e.getJSONObject("dbProps").getString("vcode"), e -> e))
        .entrySet()
        .stream()
        .map(this::jsonToFieldMetadata)
        .collect(Collectors.toList());
    POJOMetadata pojoMetadata = Optional.ofNullable(metadata.getJSONObject("apiMeta"))
        .map(e -> e.getString("voClassName"))
        .map(e -> {
          POJOMetadata tempMeta = new POJOMetadata();
          tempMeta.setClassName(e);
          return tempMeta;
        })
        .get();
    pojoMetadata.setFieldMetadataList(fieldMetadataList);
    new ConstraintMappingConfigurer()
        .addPojoMetadata(pojoMetadata)
        .configure(constraintMapping);
  }

  private FieldMetadata jsonToFieldMetadata(Entry<String, JSONObject> entry) {
    FieldMetadata fieldMetadata = new FieldMetadata();
    fieldMetadata.setFieldName(entry.getKey());
    List<RuleMetadata> ruleMetadataList = entry.getValue()
        .getJSONObject("form")
        .getJSONArray("rules")
        .stream()
        .map(this::toJSONObject)
        .map(e -> {
          RuleMetadata ruleMetadata = new RuleMetadata();
          ruleMetadata.setRuleName(e.getString("name"));
          ruleMetadata.setRejectMessage(e.getString("rejectMessage"));
          ruleMetadata.setValue(e.getString("value"));
          return ruleMetadata;
        })
        .collect(Collectors.toList());
    fieldMetadata.setRuleMetadataList(ruleMetadataList);
    return fieldMetadata;
  }

  public static class User {

    private String name;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
  }

  private JSONObject toJSONObject(Object object) {
    if (object instanceof JSONObject) {
      return (JSONObject) object;
    }
    return (JSONObject) JSON.toJSON(object);
  }


  @Slf4j
  public static class ConstraintMappingConfigurer {

    private List<POJOMetadata> pojoMetadataList = new ArrayList<>();

    private FieldMetadataConstraintCreator creator = new FieldMetadataConstraintCreator();


    public void configure(ConstraintMapping constraintMapping) {
      for (POJOMetadata pojoMetadata : pojoMetadataList) {
        TypeConstraintMappingContext<?> type;
        try {
          type = constraintMapping
              .type(Class.forName(pojoMetadata.getClassName()));
        } catch (ClassNotFoundException e) {
          log.warn("class {} not found. error message is:{}.", pojoMetadata.getClassName(),
              e.getMessage());
          continue;
        }
        for (FieldMetadata fieldMetadata : pojoMetadata.getFieldMetadataList()) {
          PropertyConstraintMappingContext property = type
              .property(fieldMetadata.getFieldName(), ElementType.FIELD);
          for (RuleMetadata ruleMetadata : fieldMetadata.getRuleMetadataList()) {
            property.constraint(creator.createConstraint(ruleMetadata));
          }
        }
      }

    }

    public ConstraintMappingConfigurer addPojoMetadata(
        POJOMetadata metadata) {
      this.pojoMetadataList.add(metadata);
      return this;
    }
  }

}
