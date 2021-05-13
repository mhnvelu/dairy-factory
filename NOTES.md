## Spring REST docs
- It generates documentation snippets from controller tests.
- AsciiDoctor assembles those snippets into a single document index.adoc
- Need to configure Spring MockMvc to work with Spring REST Docs.

## Jackson
### Common Jackson annotations:
- @JsonProperty - Allows to set the property name
- @JsonFormat - Gives control over how property is serialized (Useful for Dates)
- @JsonUnwrapped - Allows to flatten a property
- @JsonView - Allows to configure virtual view of objects
- @JsonManagedReference, @JsonBackReference - for mapping embedded items
- @JsonIdentityInfo - Allows to specify a property to determine object identity
- @JsonFilter - Used to specify programmatic property filter

### Serialization annotations:
- @JsonAnyGetter - Takes the map property and serializes the key to property names, values to 
values 
- @JsonGetter - Allows to identify the methods as a 'getter', which will be serialized into property
- @JsonPropertyOrder - Allows to set order of properties in serialized json output
- @JsonRawValue - Serializes the string value of the property as it is.
- @JsonValue - Used to mark the method for the serialization output. Will throw exception if more
 than one method is annotated
- @JsonRootName - Creates the root element for the object
- @JsonSerialize - Allows to specify the custom serializer

### Deserialization annotations:
- @JsonCreater - Used to identify the object constructor to use, and property annotations
- @JacksonInject - Allows to inject values into properties during during deserialization
- @JsonAnySetter - Converts the Json object into map object, where property names are keys of map
- @JsonSetter - Identifies a Java method to use as a setter for the identified json property
- JsonDeserialize - Allows to specify custom deserializer

### Other annotations:
- @JsonIgnoreProperties - Class level annotation used to tell Jackson to ignore one or more 
property 
- @JsonIgnore - Field level annotation used to tell Jackson to ignore one or more 
property 
- @JsonIgnoreType - Class level annotation used to ignore class
- @JsonInclude - Class level annotation used to control how null values are presented in 
serialization
- @JsonAutoDetect - Jackson will use reflection in serialization. It allows to tune which 
properties/methods are deducted

### Json Testing with Spring Boot
- Annotate the Test class with `@JsonTest`. This will inject the Spring Boot configured 
ObjectMapper.
- If we need different property naming strategy like kebab case, follow below steps:
    - create application-kebab.properties in resources directory under /test
    - set spring.jackson.property-naming-strategy=KEBAB_CASE
    - Annotate the test class with @ActiveProfiles("kebab")
 - If we set explicitly the property name using @JsonProperty, then this will override any naming
  strategy.
  
## Maven BOM(Bill Of Materials)
- Provides a common place to maintain all the dependencies so that all our microservices can 
inherit.
- Spring Parent POM is very similar to BOM. It provides us a set of dependencies and properties.
It doesn't set common dependencies, set common plugins, set common plugin configurations. But the
 BOM does.
 - In our application POM, we inherit from Spring Parent POM by explicitly mentioning them.
 - BOM Configurations:
    - Set common maven properties
    - Set common maven plugins and configurations
    - Set dependency versions
    - Set common dependencies
    - Set common build profiles
    - Set just any inheritable property that is common