package com.example.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ConsumerDemo {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.1.131:9092");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "group-1");
        //session.timeout.ms：消费者在被认为死亡之前可以与服务器断开连接的时间，默认是3s 。
        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        //消费者是否自动提交偏移量，默认值是true,避免出现重复数据和数据丢失，可以把它设为 false。
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        //auto.offset.reset:消费者在读取一个没有偏移量的分区或者偏移量无效的情况下的处理
        //earliest：在偏移量无效的情况下，消费者将从起始位置读取分区的记录。
        //latest：在偏移量无效的情况下，消费者将从最新位置读取分区的记录
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        // max.partition.fetch.bytes：服务器从每个分区里返回给消费者的最大字节数
        //fetch.max.wait.ms:消费者等待时间，默认是500。
        // fetch.min.bytes:消费者从服务器获取记录的最小字节数。
        // client.id：该参数可以是任意的字符串，服务器会用它来识别消息的来源。
        // max.poll.records:用于控制单次调用 call （） 方住能够返回的记录数量
        //receive.buffer.bytes和send.buffer.bytes：指定了 TCP socket 接收和发送数据包的缓冲区大小，默认值为-1

        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);
        List<String> topicList = new ArrayList<String>();
        topicList.add("abc");
        kafkaConsumer.subscribe(topicList);
        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(3000));
//            records.forEach(System.out::println);
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset = %d, value = %s", record.offset(), record.value());
                System.out.println("=====================>");
            }
        }
    }
}
