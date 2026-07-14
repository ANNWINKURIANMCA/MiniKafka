# ==========================
# Stage 1 - Build
# ==========================
FROM eclipse-temurin:25-jdk AS builder

WORKDIR /app

COPY src ./src

RUN mkdir out

RUN javac -d out \
src/Main.java \
src/broker/Broker.java \
src/network/ClientHandler.java \
src/common/Message.java \
src/storage/LogStorage.java \
src/storage/OffsetManager.java \
src/storage/Partition.java \
src/storage/PartitionManager.java \
src/storage/ConsumerGroupManager.java \
src/storage/TopicManager.java \
src/producer/Producer.java \
src/consumer/Consumer.java

# ==========================
# Stage 2 - Runtime
# ==========================
FROM eclipse-temurin:25-jre

WORKDIR /app

COPY --from=builder /app/out ./out

RUN mkdir data

EXPOSE 9092

CMD ["java","-cp","out","Main"]