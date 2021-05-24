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
    
## Spring State Machine
###State Machine
- State Machine can be defined as anything with a set of known states
- A 'State Machine' can be:
    - if-then-else statements
    - switch statements
- State Machine consists of:
    - Finite set of states
    - Set of Inputs
    - Initial state
    - Final state
    - Transition Function
- Common State Machine Use Cases
    - Message (Event) based applications
    - Events get published based on State changes
    - UI applications with Actions triggered by Use - Caps Lock On/Off
    - Application behaviour changes based on known states
- State Machine Terminology
    - States: The specific state of a State Machine. It is Finite and Predefined values. Mostly 
    declared as ENUM.
    - Events: Something that happens to the system - may or may not change the state.
    - Actions: The response of the State Machine to events. Can be changing the variables, 
    calling methods or changing to different state.
        - Transitions: Type of action which changes the state.
    - Guards: Boolean conditions which allow/disallow certain things happen.
    - Extended State: State Machine variables(in addition to state)
### Why use State Machine?
- State Machines help define consistent behaviour for finite of states.
- Application logic is defined for specific state or state transitions.
- Application logic becomes more modular and more precisely defined.
- Long block of if, then, else if conditions are difficult to code, debug and maintain.
- Helps to avoid spaghetti code for complex conditions.

##Using Sagas with Spring
### ACID Transactions
- Atomicity - All operations are completed successfully or DB is returned to previous state.
- Consistency - Operations do not violate system integrity constraints.
- Isolated - Results are independent of concurrent transactions.
- Durable - Results are made persistent in case of system failure.

### Distributed Transactions
- With microservices, often multiple services are involved in what is considered as Transaction.
- Java EE - Java Transaction API (JTA)
  - Enables distributed transactions in Java Environment.
  - Supported by Spring.
  - Transactions are managed across nodes by a Transaction Manager
  - Java Centric.
- Two Phase Commit - 2PC
  - Happens in 2 phases - Voting and Commit
  - Coordinator asks each node if proposed transaction is okay
    - If all Respond okay, then
      - Commit message is sent
      - Each node commits work and sends acknowledgement to coordinator
    - If any node responds no, then
      - Rollback message is sent
      - Each node rollbacks and sends acknowledgement to coordinator
  - Problems with 2PC
    - Does not scale - expensive
    - Blocking Protocol - the various steps block and wait for other to complete
    - Performance is limited to the speed of the slowest node
    - Coordinator is a Single Point Of Failure
    - Technology lock-in - can be difficult to mix technology stacks
### Challenges with Microservices
- A transaction for a Microservices Architecture often spans across multiple microservices.
- Each service have its own DB - could be SQL/NoSQL DB.
- Should be technology agnostic - services can be Java, NodeJs, Python, .NET, etc.
- How to coordinate the transaction across multiple microservices?
### Need for Sagas
#### CAP(Consistency, Availability, Partition Tolerance) Theorem
- Consistency - Every read will have most recent write.
- Availability - Each read will get a response, but without the guarantee data is most recent write.
- Partition Tolerance - System Continues in lieu of communications errors or delays.

CAP Theorem states a Distributed System can only maintain 2 of 3.
![CAP Theorem](CAP-Theorem.png)

#### BASE - An ACID Alternative
- BASE - Basically Available, Soft State, Eventually Consistent.
- Opposite of ACID.
- Basically Available - Build System to support partial failures. Loss of some functionality Vs 
Total System loss.
- Soft State - Transactions cascade across nodes, it can be inconsistent for a period of time.
- Eventually Consistent - When processing is complete, system will be consistent.

#### Feral Concurrency Control
- It is application level mechanism for maintaining DB integrity.
- Relational DB can enforce variety of constraints - such as foreign key constraints.
- Not available within distributed system.
- Its upto application to enforce the constraints.

#### Introducing Sagas
- Originally it was addressing Long Lived Transactions (LLT) within a single DB.
- LLTs hold on to DB resources for an extended period of time.
- Sagas proposed breaking long complex transaction into a smaller more atomic transactions.
- Concept of compensating transactions to correct partial executions.

#### Sagas
- Sagas are simply a series of steps to complete a business process.
- Sagas coordinate the invocation of microservices via messages or requests.
- Sagas become a Transactional model.
- Each step of the Saga can be considered as a request.
- Each step of the Saga has a compensating transaction.
  - Semantically undo the effect of request.
  - Might not restore to exact previous state - but effectively the same.
  
#### Sagas Steps
- Each step should be a message or event to be consumed by a microservice.
- Steps are asynchronous.
- Within microservice, it's normal to use traditional database transactions.
- Each message(request) should be idempotent.
  - Meaning if same message/event is sent, there is no adverse effect on the system state.
- Each step has a compensating transaction to undo the actions.

##### Compensating Transactions
- Compensating Transactions:
  - Effectively become Feral Concurrency Control.
  - Are the mechanism used to maintain system integrity.
  - Should be idempotent.
  - Cannot abort - need to ensure proper execution.
  - Not the same as a rollback to the exact previous state.
    - Implements business logic for a failure event.

#### Sagas are ACD
- Atomic - All transactions are executed or compensated.
- Consistency 
  - Referential integrity  within a service by the local DB.
  - Referential integrity across services by the application - 'Feral Concurrency Control'.
- Durability - Persisted by DB of each microservice.

#### Sagas & Eventually Consistent
- BASE - Basically Available, Soft State, Eventually Consistent.
- During execution of Saga, System is in Soft State.
- Eventually Consistent - system will be consistent at the conclusion of Saga.
  - Consistency achieved by normal execution of Saga.
  - In the event of error, consistency achieved via compensating transactions.
   