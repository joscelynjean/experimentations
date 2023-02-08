# Used to build
FROM maven:3-openjdk-11 as builder

# Copy all sources
COPY . /volume

# Build application
RUN cd /volume && mvn clean install

# Use the compiled JAR to build the image and run the applicaiton
FROM openjdk:11

# Copy the JAR
COPY --from=builder /volume/camunda-engine/target/camunda-engine-1.0.0.jar camunda-engine-1.0.0.jar

ENTRYPOINT ["java", "-Dpolyglot.engine.WarnInterpreterOnly=false", "-jar", "/camunda-engine-1.0.0.jar"]
